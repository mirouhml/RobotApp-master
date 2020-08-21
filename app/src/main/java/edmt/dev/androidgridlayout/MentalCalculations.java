package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import java.util.Random;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MentalCalculations extends AppCompatActivity {
    int solution;
    EditText answer;
    TextView num1;
    TextView num2;
    TextView oper;
    ImageView blackLocation;
    ImageView redLocation;
    ImageView yellowLocation;
    ImageView blueLocation;
    ImageView purpleLocation;
    ImageView pinkLocation;
    ImageView greenLocation;
    ImageView whiteLocation;
    ImageView grayLocation;
    TextView blackText;
    TextView redText;
    TextView yellowText;
    TextView blueText;
    TextView purpleText;
    TextView pinkText;
    TextView greenText;
    TextView whiteText;
    TextView grayText;
    TextView message;
    String operator;
    int number1;
    int number2;
    int[] map;
    int location;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    SimpleBluetoothDeviceInterface deviceInterface;
    final LoadingDialogue dialogue = new LoadingDialogue(MentalCalculations.this);
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mental_calculations);
        setTitle(R.string.mental_calculations);
        dialogue.startLoadingDialogue(R.layout.loading_dialogue);
        bluetoothManager = BluetoothManager.getInstance();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //checking whether the bluetooth is on or not
        if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
        //showing the loading view
        new AsyncTask<Void, Void, Void>(){
            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... params) {
                // **Code**
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                connectDevice();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute();

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        blackText = findViewById(R.id.blackViewText);
        redText = findViewById(R.id.redViewText);
        yellowText = findViewById(R.id.yellowViewText);
        blueText = findViewById(R.id.blueViewText);
        purpleText = findViewById(R.id.purpleViewText);
        pinkText = findViewById(R.id.pinkViewText);
        greenText = findViewById(R.id.greenViewText);
        whiteText = findViewById(R.id.whiteViewText);
        grayText = findViewById(R.id.grayViewText);
        answer = findViewById(R.id.result);
        num1 = findViewById(R.id.first_number);
        num2 = findViewById(R.id.second_number);
        oper = findViewById(R.id.operator);
        blackLocation = findViewById(R.id.circleBlackView);
        redLocation = findViewById(R.id.circleRedView);
        yellowLocation = findViewById(R.id.circleYellowView);
        blueLocation = findViewById(R.id.circleBlueView);
        purpleLocation = findViewById(R.id.circlePurpleView);
        pinkLocation = findViewById(R.id.circlePinkView);
        greenLocation = findViewById(R.id.circleGreenView);
        whiteLocation = findViewById(R.id.circleWhiteView);
        grayLocation = findViewById(R.id.circleGrayView);
        message = findViewById(R.id.operation_answer);
        message.setVisibility(View.INVISIBLE);
        location = -1;
        setEquation();
        generateMap();
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
        message.setVisibility(View.INVISIBLE);
        if (dialogue.isOn){
            dialogue.dismissLoadingDIalogue();
        }
    }

    public void clickChoose(View view){
        //if (answer!=null) result = Integer.parseInt(answer.getText().toString());
        if(location == -1){
            Toast.makeText(this, "Please place the robot on the map", Toast.LENGTH_SHORT).show();
            return;
        }
        if (map[location] == solution)
        {
            dialogue.startLoadingDialogue(R.layout.success);
        }

        else
            message.setVisibility(View.VISIBLE);
    }

    public void clickRight(View view){ deviceInterface.sendMessage("1"); }

    public void clickLeft(View view){
        deviceInterface.sendMessage("1");
    }

    public void clickUp(View view){
        deviceInterface.sendMessage("0");
    }

    public void clickDown(View view){
        deviceInterface.sendMessage("0");
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
        map = new int[]{-1,-1,-1,
                -1,-1,-1,
                -1,-1,-1};
        for (int j = 0; j < 2; j++) {
            map[j] = randomSolution();
            for (int k = 0; k < j; k++) {
                if (map[j] == map[k]) {
                    j--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
        map[2] = solution;
        shuffleArray(map);
        for(int j=0; j<map.length;j++){
            if (map[j]!=-1)
                setTile(j);
        }
    }

    private void setTile(int j){
        switch (j){
            case 0:{
                blackText.setText(""+map[j]);
                break;
            }
            case 1:{
                redText.setText(""+map[j]);
                break;
            }
            case 2:{
                yellowText.setText(""+map[j]);
                break;
            }
            case 3:{
                blueText.setText(""+map[j]);
                break;
            }
            case 4:{
                purpleText.setText(""+map[j]);
                break;
            }
            case 5:{
                pinkText.setText(""+map[j]);
                break;
            }
            case 6:{
                greenText.setText(""+map[j]);
                break;
            }
            case 7:{
                whiteText.setText(""+map[j]);
                break;
            }
            case 8:{
                grayText.setText(""+map[j]);
                break;
            }
        }
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
        deviceInterface = connectedDevice.toSimpleDeviceInterface();
        Toast.makeText(this, "Connected to the robot.", Toast.LENGTH_LONG).show();
        deviceInterface.sendMessage("Connected.");
        // Listen to bluetooth events
        deviceInterface.setListeners(this::onMessageReceived, this::onMessageSent, this::onError);
        dialogue.dismissLoadingDIalogue();
    }

    private void onMessageSent(String message) {
        // We sent a message! Handle it here.
        Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        location = Integer.parseInt(message);
        setLocation();
    }
    @SuppressLint("StaticFieldLeak")
    private void onError(Throwable error) {
        // Handle the error
        dialogue.dismissLoadingDIalogue();
        dialogue.startLoadingDialogue(R.layout.connect_robot);
        //showing the loading view
    }

    private void refresh() {
        blackText.setText("");
        redText.setText("");
        yellowText.setText("");
        blueText.setText("");
        purpleText.setText("");
        pinkText.setText("");
        greenText.setText("");
        whiteText.setText("");
        grayText.setText("");
        setEquation();
        generateMap();
    }
    private void setLocation(){
        switch (location){
            case 0:{
                blackLocation.setVisibility(View.VISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 1:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.VISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 2:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.VISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 3:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.VISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 4:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.VISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 5:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.VISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 6:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.VISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 7:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.VISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
            case 8:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.VISIBLE);
                break;
            }
            default:{
                blackLocation.setVisibility(View.INVISIBLE);
                redLocation.setVisibility(View.INVISIBLE);
                yellowLocation.setVisibility(View.INVISIBLE);
                blueLocation.setVisibility(View.INVISIBLE);
                purpleLocation.setVisibility(View.INVISIBLE);
                pinkLocation.setVisibility(View.INVISIBLE);
                greenLocation.setVisibility(View.INVISIBLE);
                whiteLocation.setVisibility(View.INVISIBLE);
                grayLocation.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        bluetoothManager.close();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        bluetoothManager = BluetoothManager.getInstance();
        connectDevice();
    }

    public void onOk(View view){
        dialogue.dismissLoadingDIalogue();
        finish();
    }
}