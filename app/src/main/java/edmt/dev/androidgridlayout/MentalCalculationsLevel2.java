package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MentalCalculationsLevel2 extends AppCompatActivity {
    private final LoadingDialogue dialogue = new LoadingDialogue(MentalCalculationsLevel2.this);
    private int solution;
    private TextView num1;
    private TextView num2;
    private TextView oper;
    private TextView message;
    private String operator;
    private int number1;
    private int number2;
    private int[] map;
    private int location;
    private BluetoothManager bluetoothManager;
    private SimpleBluetoothDeviceInterface deviceInterface;
    private CreateVariables variables;
    private boolean stopped = false;

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_calculations_level2);
        variables = new CreateVariables(this);
        setTitle(R.string.mental_calculations);
        dialogue.startLoadingDialogue(R.layout.loading_dialogue);
        bluetoothManager = BluetoothManager.getInstance();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //checking whether the bluetooth is on or not
        if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
        //showing the loading view
        new Thread(() -> {
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectDevice();
        }).start();

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        num1 = findViewById(R.id.first_number);
        num2 = findViewById(R.id.second_number);
        oper = findViewById(R.id.operator);
        message = findViewById(R.id.operation_answer);
        message.setVisibility(View.INVISIBLE);
        location = -1;
        ImageView up;
        ImageView down;
        ImageView right;
        ImageView left;
        up = findViewById(R.id.arrow_up);
        down = findViewById(R.id.arrow_down);
        right = findViewById(R.id.arrow_right);
        left = findViewById(R.id.arrow_left);
        up.setOnTouchListener(((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                deviceInterface.sendMessage("1");
            else if (event.getAction() == MotionEvent.ACTION_UP)
                deviceInterface.sendMessage("S");
            return false;
        }));
        down.setOnTouchListener(((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                deviceInterface.sendMessage("2");
            else if (event.getAction() == MotionEvent.ACTION_UP)
                deviceInterface.sendMessage("S");
            return false;
        }));
        right.setOnTouchListener(((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                deviceInterface.sendMessage("4");
            else if (event.getAction() == MotionEvent.ACTION_UP)
                deviceInterface.sendMessage("S");
            return false;
        }));
        left.setOnTouchListener(((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
                deviceInterface.sendMessage("3");
            else if (event.getAction() == MotionEvent.ACTION_UP)
                deviceInterface.sendMessage("S");
            return false;
        }));
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

    public void clickNext(View view) {
        refresh();
        message.setVisibility(View.INVISIBLE);
        if (dialogue.isOn) {
            dialogue.dismissLoadingDIalogue();
        }
    }

    public void clickChoose(View view) {
        //if (answer!=null) result = Integer.parseInt(answer.getText().toString());
        if (location == -1) {
            Toast.makeText(this, "Please place the robot on the map", Toast.LENGTH_SHORT).show();
            return;
        }
        if (map[location] == solution) {
            dialogue.startLoadingDialogue(R.layout.success);
        } else
            message.setVisibility(View.VISIBLE);
    }

    private int randomPlus() {
        return new Random().nextInt((50 - 20) + 1) + 20;//new Random().nextInt((max - min) + 1) + min;
    }

    private int randomMinus() {
        return new Random().nextInt((30 - 1) + 1) + 1;//new Random().nextInt((max - min) + 1) + min;

    }


    private String randomOperator() {
        String[] arr = {"+", "-"};
        Random random = new Random();
        // randomly selects an index from the arr
        int select = random.nextInt(arr.length);
        return arr[select];
    }

    private int randomSolution() {
        int sol = 0;
        switch (operator) {
            case "+": {
                int min;
                if (number1 > number2)
                    min = number1 + 1;
                else
                    min = number2 + 1;
                int max = solution + 5;
                sol = new Random().nextInt((max - min) + 1) + min;
                return sol;
            }
            case "-": {
                if (number1 == number2 && number1 < 4) {
                    sol = new Random().nextInt((4 - 1) + 1) + 1;
                } else {
                    int max = number1;
                    int min = solution;
                    sol = new Random().nextInt((max - min) + 1) + min;
                    while (sol == solution || sol < 0) {
                        sol = new Random().nextInt((max - min) + 1) + min;
                    }
                }
                break;
            }
            default: {
                break;
            }
        }
        return sol;
    }

    @SuppressLint("SetTextI18n")
    private void setEquation() {

        operator = randomOperator();
        oper.setText(operator);
        switch (operator) {
            case "+": {
                number1 = randomPlus();
                number2 = randomPlus();
                num1.setText("" + number1);
                num2.setText("" + number2);
                solution = number1 + number2;
                break;
            }
            case "-": {
                number1 = randomMinus();
                number2 = randomMinus();
                while (number1 < number2) {
                    number1 = randomMinus();
                    number2 = randomMinus();
                }
                num1.setText("" + number1);
                num2.setText("" + number2);
                solution = number1 - number2;
                break;
            }
            default:
                Toast.makeText(this, "Error switch", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void generateMap() {
        map = new int[]{-1, -1, -1,
                -1, -1, -1,
                -1, -1, -1};
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
        for (int j = 0; j < map.length; j++) {
            if (map[j] != -1)
                setTile(j);
        }
    }

    private void setTile(int j) {
        switch (j) {
            case 0: {
                variables.getBlackText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 1: {
                variables.getRedText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 2: {
                variables.getYellowText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 3: {
                variables.getBlueText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 4: {
                variables.getPurpleText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 5: {
                variables.getPinkText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 6: {
                variables.getGreenText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 7: {
                variables.getWhiteText().setText(getString(R.string.number, map[j]));
                break;
            }
            case 8: {
                variables.getGrayText().setText(getString(R.string.number, map[j]));
                break;
            }
        }
    }

    private void shuffleArray(int[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
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
        //Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
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
        variables.getBlackText().setText("");
        variables.getRedText().setText("");
        variables.getYellowText().setText("");
        variables.getBlueText().setText("");
        variables.getPurpleText().setText("");
        variables.getPinkText().setText("");
        variables.getGreenText().setText("");
        variables.getWhiteText().setText("");
        variables.getGrayText().setText("");
        setEquation();
        generateMap();
    }

    private void setLocation() {
        switch (location) {
            case 0: {
                variables.getBlackLocation().setVisibility(View.VISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 1: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.VISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 2: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.VISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 3: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.VISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 4: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.VISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 5: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.VISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 6: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.VISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 7: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.VISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
            case 8: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.VISIBLE);
                break;
            }
            default: {
                variables.getBlackLocation().setVisibility(View.INVISIBLE);
                variables.getRedLocation().setVisibility(View.INVISIBLE);
                variables.getYellowLocation().setVisibility(View.INVISIBLE);
                variables.getBlueLocation().setVisibility(View.INVISIBLE);
                variables.getPurpleLocation().setVisibility(View.INVISIBLE);
                variables.getPinkLocation().setVisibility(View.INVISIBLE);
                variables.getGreenLocation().setVisibility(View.INVISIBLE);
                variables.getWhiteLocation().setVisibility(View.INVISIBLE);
                variables.getGrayLocation().setVisibility(View.INVISIBLE);
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetoothManager.close();
        stopped = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stopped) {
            finish();
            startActivity(getIntent());
        }
    }

    public void onOk(View view) {
        dialogue.dismissLoadingDIalogue();
        finish();
    }
}