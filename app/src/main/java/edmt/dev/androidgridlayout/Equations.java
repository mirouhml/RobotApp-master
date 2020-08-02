package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import java.util.Random;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Equations extends AppCompatActivity {
    int solution;
    EditText answer;
    TextView num1;
    TextView num2;
    TextView oper;
    BluetoothManager bluetoothManager;
    Button choose;
    int location;
    String operator;
    int number1;
    int number2;
    int[] map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equations);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        answer = findViewById(R.id.result);
        choose = findViewById(R.id.choose);
        num1 = findViewById(R.id.first_number);
        num2 = findViewById(R.id.second_number);
        oper = findViewById(R.id.operator);
        setEquation();
        generateMap();
        bluetoothManager = BluetoothManager.getInstance();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "This device doesn't have Bluetooth.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            Toast.makeText(this, "Bluetooth isn't enabled, please enable it.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        }
        connectDevice();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            Intent intent = new Intent(this, HomePage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickNext(View view){
        refresh();
    }

    public void clickChoose(View view){
        //if (answer!=null) result = Integer.parseInt(answer.getText().toString());
        if (map[location] == solution)
            Toast.makeText(this,"CORRECT",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"WRONG",Toast.LENGTH_SHORT).show();
    }

    public void clickRight(View view){ Toast.makeText(this,"Right",Toast.LENGTH_SHORT).show(); }

    public void clickLeft(View view){
        Toast.makeText(this,"Left",Toast.LENGTH_SHORT).show();
    }

    public void clickUp(View view){
        Toast.makeText(this,"Up",Toast.LENGTH_SHORT).show();
    }

    public void clickDown(View view){
        Toast.makeText(this,"Down",Toast.LENGTH_SHORT).show();
    }

    int random(){
        return new Random().nextInt((100 - 1) + 1) + 1;//new Random().nextInt((max - min) + 1) + min;
    }

    int randomMulti(){
        return new Random().nextInt((10 - 1) + 1) + 1;
    }

    String randomOperator() {
            String [] arr = {"+", "-", "x"};
            Random random = new Random();
            // randomly selects an index from the arr
            int select = random.nextInt(arr.length);
            return arr[select];
    }

    int randomSolution(){
        int sol = 0;
        switch (operator){
            case "+":{
                int min;
                if (number1 > number2)
                    min = number1 + 1;
                else
                    min = number2 + 1;
                int max = solution + 5;
                sol = new Random().nextInt((max - min) + 1) + min;
                return sol;
            }
            case "-":{
                if (number1==number2 && number1<4){
                    sol = new Random().nextInt((4 - 1) + 1) + 1;
                }
                else {
                    int max = number1;
                    int min = solution;
                    sol = new Random().nextInt((max - min) + 1) + min;
                    while (sol == solution || sol < 0) {
                        sol = new Random().nextInt((max - min) + 1) + min;
                    }
                }
                break;
            }
            case "x":{
                int max = solution + 3;
                int min = solution - 3;
                sol = new Random().nextInt((max - min) + 1) + min;
                while (sol == solution||sol<0) {
                    sol = new Random().nextInt((max - min) + 1) + min;
                }
                break;
            }
            default:{
                break;
            }
        }
        return sol;
    }

    @SuppressLint("SetTextI18n")
    private void setEquation(){
        number1 = random();
        number2 = random();
        operator = randomOperator();
        oper.setText(operator);
        switch(operator){
            case "+":{
                num1.setText(""+number1);
                num2.setText(""+number2);
                solution = number1 + number2;
                break;
            }
            case "-":{
                while (number1 < number2){
                    number1 = random();
                    number2 = random();
                }
                num1.setText(""+number1);
                num2.setText(""+number2);
                solution = number1 - number2;
                break;
            }
            case "x":{
                number1 = randomMulti();
                number2 = randomMulti();
                num1.setText(""+number1);
                num2.setText(""+number2);
                solution = number1 * number2;
                break;
            }
            default:
                Toast.makeText(this,"Error switch",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void generateMap(){
        map = new int[]{-1,-1,-1,-1,
                -1,-1,-1,-1,
                -1,-1,-1,-1,
                -1,-1,-1,-1};
        for (int j = 0; j < 3; j++) {
            map[j] = randomSolution();
            for (int k = 0; k < j; k++) {
                if (map[j] == map[k]) {
                    j--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
        map[4] = solution;
        shuffleArray(map);
        TextView message = findViewById(R.id.msg);
        message.setText(map[0]+"  "+map[1]+"  "+map[2]+"  "+map[3]+"\n"+
                        map[4]+"  "+map[5]+"  "+map[6]+"  "+map[7]+"\n"+
                        map[8]+"  "+map[9]+"  "+map[10]+"  "+map[11]+"\n"+
                        map[12]+"  "+map[13]+"  "+map[14]+"  "+map[15]+"\n");
    }

    private void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SuppressLint("CheckResult")
    private void connectDevice() {
        //D/My Bluetooth App: Device name: HC-05
        //D/My Bluetooth App: Device MAC Address: 00:18:E4:00:55:A4
        String mac = "00:18:E4:00:55:A4";
        bluetoothManager.openSerialDevice(mac)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onConnected, this::onError);
    }

    private void onConnected(BluetoothSerialDevice connectedDevice) {
        // You are now connected to this device!
        // Here you may want to retain an instance to your device:
        SimpleBluetoothDeviceInterface deviceInterface = connectedDevice.toSimpleDeviceInterface();
        Toast.makeText(this, "Connected to the robot.", Toast.LENGTH_LONG).show();
        deviceInterface.sendMessage("Connected.");
        // Listen to bluetooth events
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
    }

    private void onMessageSent(String message) {
        // We sent a message! Handle it here.
        Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        Toast.makeText(this, "Message received.", Toast.LENGTH_LONG).show();
        location = Integer.parseInt(message);
        //choose.setEnabled(true);
    }

    private void onError(Throwable error) {
        // Handle the error
    }

    private void refresh() {
        setEquation();
        generateMap();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetoothManager.close();
    }


}