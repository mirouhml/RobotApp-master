package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchForVegetables extends AppCompatActivity {
    final ArrayList<Thing> vegetables = new ArrayList<>();
    int[] map;
    int theVegetable;
    int previousVegetable = -1;
    private CreateVariables variables;


    TextView vegetableTextView;
    TextView message;
    private boolean stopped = false;

    int location;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    SimpleBluetoothDeviceInterface deviceInterface;
    final LoadingDialogue dialogue = new LoadingDialogue(SearchForVegetables.this);

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_vegetables);
        variables = new CreateVariables(this);
        setTitle(R.string.vegetables);
        dialogue.startLoadingDialogue(R.layout.loading_dialogue);
        bluetoothManager = BluetoothManager.getInstance();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //checking whether the bluetooth is on or not
        if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
        //showing the loading view
        new Thread(() -> {
            // TODO Auto-generated method stub
            try {
                Thread.sleep(1800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectDevice();
        }).start();

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vegetables.add(new Thing(getString(R.string.carrot), "la", R.drawable.carrot));
        vegetables.add(new Thing(getString(R.string.bell_pepper), "le", R.drawable.bell_pepper));
        vegetables.add(new Thing(getString(R.string.corn), "le", R.drawable.corn));
        vegetables.add(new Thing(getString(R.string.cucumber), "le", R.drawable.cucumber));
        vegetables.add(new Thing(getString(R.string.eggplant), "l'", R.drawable.eggplant));
        vegetables.add(new Thing(getString(R.string.garlic), "l'", R.drawable.garlic));
        vegetables.add(new Thing(getString(R.string.peas), "les", R.drawable.peas));
        vegetables.add(new Thing(getString(R.string.potato), "la", R.drawable.potato));
        vegetables.add(new Thing(getString(R.string.radish), "le", R.drawable.radish));
        vegetables.add(new Thing(getString(R.string.lettuce), "la", R.drawable.lettuce));
        vegetables.add(new Thing(getString(R.string.broccoli), "le", R.drawable.broccoli));
        vegetables.add(new Thing(getString(R.string.beet), "le", R.drawable.beet));
        vegetables.add(new Thing(getString(R.string.cabbage), "le", R.drawable.cabbage));
        vegetables.add(new Thing(getString(R.string.cauliflower), "le", R.drawable.cauliflower));
        vegetables.add(new Thing(getString(R.string.chili_pepper), "le", R.drawable.chili_pepper));
        vegetables.add(new Thing(getString(R.string.ginger), "le", R.drawable.ginger));
        vegetables.add(new Thing(getString(R.string.horseradish), "le", R.drawable.horseradish));
        vegetables.add(new Thing(getString(R.string.olives), "l'", R.drawable.olives));
        vegetables.add(new Thing(getString(R.string.spinach), "l'", R.drawable.spinach));
        vegetables.add(new Thing(getString(R.string.turnip), "le", R.drawable.turnip));
        vegetables.add(new Thing(getString(R.string.zucchini), "la", R.drawable.zucchini));

        vegetableTextView = findViewById(R.id.vegetable);
        message = findViewById(R.id.operation_answer);
        message.setVisibility(View.INVISIBLE);
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
        location = -1;
        generateMap();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// app icon in action bar clicked; go home
            Intent intent = new Intent(this, SearchForObject.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideVegetables() {
        variables.getBlackImageView().setVisibility(View.INVISIBLE);
        variables.getRedImageView().setVisibility(View.INVISIBLE);
        variables.getYellowImageView().setVisibility(View.INVISIBLE);
        variables.getBlueImageView().setVisibility(View.INVISIBLE);
        variables.getPurpleImageView().setVisibility(View.INVISIBLE);
        variables.getPinkImageView().setVisibility(View.INVISIBLE);
        variables.getGreenImageView().setVisibility(View.INVISIBLE);
        variables.getWhiteImageView().setVisibility(View.INVISIBLE);
        variables.getGrayImageView().setVisibility(View.INVISIBLE);
    }




    public void clickNext(View view) {
        generateMap();
        message.setVisibility(View.INVISIBLE);
        if (dialogue.isOn){
            dialogue.dismissLoadingDIalogue();
        }
    }

    public void clickChoose(View view) {
        //if (answer!=null) result = Integer.parseInt(answer.getText().toString());
        if(location == -1){
            Toast.makeText(this, "Please place the robot on the map", Toast.LENGTH_SHORT).show();
            return;
        }
        if (map[location] == theVegetable)
        {
            dialogue.startLoadingDialogue(R.layout.success);
        }

        else
            message.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void generateMap(){
        hideVegetables();
        showMystery();
        map = new int[]{-1,-1,-1,
                -1,-1,-1,
                -1,-1,-1};
        while(true){
            theVegetable = new Random().nextInt(vegetables.size());
            if (theVegetable != previousVegetable) {
                previousVegetable = theVegetable;
                break;
            }
        }
        map[0] = theVegetable;
        for (int j = 1; j < 3; j++) {
            map[j] = new Random().nextInt(vegetables.size());
            for (int k = 0; k < j; k++) {
                if (map[j] == map[k]) {
                    j--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
        shuffleArray(map);
        for(int j=0; j<map.length;j++){
            if (map[j]!=-1)
                setTile(j);
        }
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        String language = gameSettings.getString("Language","DEFAULT");
        assert language != null;
        switch(language){
            case "English":{
                vegetableTextView.setText("Find the "+vegetables.get(theVegetable).getNAme().toLowerCase());
                break;
            }
            case "French":{
                String str = vegetables.get(theVegetable).getGender();
                if (str.equals("l'"))
                    vegetableTextView.setText("Trouver "+str+vegetables.get(theVegetable).getNAme().toLowerCase());
                else
                    vegetableTextView.setText("Trouver "+str+" "+vegetables.get(theVegetable).getNAme().toLowerCase());
                break;
            }
            case "Arabic":{
                if (vegetables.get(theVegetable).getNAme().contains(" "))
                    vegetableTextView.setText("جِد "+vegetables.get(theVegetable).getNAme().toLowerCase());
                else
                    vegetableTextView.setText("جِد ال"+vegetables.get(theVegetable).getNAme().toLowerCase());
                Button choose = findViewById(R.id.choose);
                Button choose2 = findViewById(R.id.choose2);
                Button next = findViewById(R.id.next);
                Button next2 = findViewById(R.id.next2);
                choose.setVisibility(View.GONE);
                next.setVisibility(View.GONE);
                choose2.setVisibility(View.VISIBLE);
                next2.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setTile(int j){
        Thing fruit = vegetables.get(map[j]);
        switch (j) {
            case 0: {
                variables.getBlackImageView().setVisibility(View.VISIBLE);
                variables.getBlackImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 1: {
                variables.getRedImageView().setVisibility(View.VISIBLE);
                variables.getRedImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 2: {
                variables.getYellowImageView().setVisibility(View.VISIBLE);
                variables.getYellowImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 3: {
                variables.getBlueImageView().setVisibility(View.VISIBLE);
                variables.getBlueImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 4: {
                variables.getPurpleImageView().setVisibility(View.VISIBLE);
                variables.getPurpleImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 5: {
                variables.getPinkImageView().setVisibility(View.VISIBLE);
                variables.getPinkImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 6: {
                variables.getGreenImageView().setVisibility(View.VISIBLE);
                variables.getGreenImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 7: {
                variables.getWhiteImageView().setVisibility(View.VISIBLE);
                variables.getWhiteImageView().setImageResource(fruit.getImageResourceId());
                break;
            }
            case 8: {
                variables.getGrayImageView().setVisibility(View.VISIBLE);
                variables.getGrayImageView().setImageResource(fruit.getImageResourceId());
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

    private void setLocation(){
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

                variables.getBlackMystery().setVisibility(View.INVISIBLE);
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

                variables.getRedMystery().setVisibility(View.INVISIBLE);
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

                variables.getYellowMystery().setVisibility(View.INVISIBLE);
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

                variables.getBlueMystery().setVisibility(View.INVISIBLE);
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

                variables.getPurpleMystery().setVisibility(View.INVISIBLE);
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

                variables.getPinkMystery().setVisibility(View.INVISIBLE);
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

                variables.getGreenMystery().setVisibility(View.INVISIBLE);
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

                variables.getWhiteMystery().setVisibility(View.INVISIBLE);
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

                variables.getGrayMystery().setVisibility(View.INVISIBLE);
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

    private void showMystery() {
        variables.getBlackMystery().setVisibility(View.VISIBLE);
        variables.getRedMystery().setVisibility(View.VISIBLE);
        variables.getYellowMystery().setVisibility(View.VISIBLE);
        variables.getBlueMystery().setVisibility(View.VISIBLE);
        variables.getPurpleMystery().setVisibility(View.VISIBLE);
        variables.getPinkMystery().setVisibility(View.VISIBLE);
        variables.getGreenMystery().setVisibility(View.VISIBLE);
        variables.getWhiteMystery().setVisibility(View.VISIBLE);
        variables.getGrayMystery().setVisibility(View.VISIBLE);
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
        //Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show();// Replace context with your context instance.
    }

    private void onMessageReceived(String message) {
        // We received a message! Handle it here.
        location = Integer.parseInt(message);
        setLocation();
    }

    private void onError(Throwable error) {
        // Handle the error
        dialogue.dismissLoadingDIalogue();
        dialogue.startLoadingDialogue(R.layout.connect_robot);
    }

    public void onOk(View view) {
        dialogue.dismissLoadingDIalogue();
        finish();
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
}