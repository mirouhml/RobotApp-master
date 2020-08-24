package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
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

public class SearchForFruits extends AppCompatActivity {
    final ArrayList<Thing> fruits = new ArrayList<>();
    int[] map;
    int theFruit;
    int previousFruit = -1;
    ImageView blackImageView;
    ImageView redImageView;
    ImageView yellowImageView;
    ImageView blueImageView;
    ImageView purpleImageView;
    ImageView pinkImageView;
    ImageView greenImageView;
    ImageView whiteImageView;
    ImageView grayImageView;

    ImageView blackLocation;
    ImageView redLocation;
    ImageView yellowLocation;
    ImageView blueLocation;
    ImageView purpleLocation;
    ImageView pinkLocation;
    ImageView greenLocation;
    ImageView whiteLocation;
    ImageView grayLocation;

    ImageView blackMystery;
    ImageView redMystery;
    ImageView yellowMystery;
    ImageView blueMystery;
    ImageView purpleMystery;
    ImageView pinkMystery;
    ImageView greenMystery;
    ImageView whiteMystery;
    ImageView grayMystery;
    
    TextView fruitTextView;
    TextView message;

    int location;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    SimpleBluetoothDeviceInterface deviceInterface;
    final LoadingDialogue dialogue = new LoadingDialogue(SearchForFruits.this);
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_fruits);
        setTitle(R.string.fruits);
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
                    Thread.sleep(1800);
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
        fruits.add(new Thing(getString(R.string.apple),"la",R.drawable.apple));
        fruits.add(new Thing(getString(R.string.banana),"la",R.drawable.banana));
        fruits.add(new Thing(getString(R.string.berries),"la",R.drawable.berries));
        fruits.add(new Thing(getString(R.string.cherry),"la",R.drawable.cherry));
        fruits.add(new Thing(getString(R.string.grapes),"le",R.drawable.grapes));
        fruits.add(new Thing(getString(R.string.coconut),"la",R.drawable.coconut));
        fruits.add(new Thing(getString(R.string.kiwi),"le",R.drawable.kiwi));
        fruits.add(new Thing(getString(R.string.lemon),"le",R.drawable.lemon));
        fruits.add(new Thing(getString(R.string.melon),"le",R.drawable.melon));
        fruits.add(new Thing(getString(R.string.orange),"l'",R.drawable.orange));
        fruits.add(new Thing(getString(R.string.pineapple),"l'",R.drawable.pineapple));
        fruits.add(new Thing(getString(R.string.strawberry),"la",R.drawable.strawberry));
        fruits.add(new Thing(getString(R.string.watermelon),"la",R.drawable.watermelon));
        fruits.add(new Thing(getString(R.string.pumpkin),"la",R.drawable.pumpkin));
        fruits.add(new Thing(getString(R.string.tomato),"la",R.drawable.tomato));
        fruits.add(new Thing(getString(R.string.grenade),"la",R.drawable.pomegranate));
        fruits.add(new Thing(getString(R.string.apricot),"l'",R.drawable.apricot));
        fruits.add(new Thing(getString(R.string.avocado),"l'",R.drawable.avocado));
        fruits.add(new Thing(getString(R.string.blueberry),"la",R.drawable.blueberry));
        fruits.add(new Thing(getString(R.string.chestnut),"la",R.drawable.chestnut));
        fruits.add(new Thing(getString(R.string.fig),"la",R.drawable.fig));
        fruits.add(new Thing(getString(R.string.gooseberry),"la",R.drawable.gooseberry));
        fruits.add(new Thing(getString(R.string.grapefruit),"le",R.drawable.grapefruit));
        fruits.add(new Thing(getString(R.string.guava),"la",R.drawable.guava));
        fruits.add(new Thing(getString(R.string.kumquat),"le",R.drawable.kumquat));
        fruits.add(new Thing(getString(R.string.lime),"le",R.drawable.lime));
        fruits.add(new Thing(getString(R.string.mango),"la",R.drawable.mango));
        fruits.add(new Thing(getString(R.string.mangosteen),"le",R.drawable.mangosteen));
        fruits.add(new Thing(getString(R.string.papaya),"la",R.drawable.papaya));
        fruits.add(new Thing(getString(R.string.granadilla),"la",R.drawable.passion_fruit_1));
        fruits.add(new Thing(getString(R.string.peacj),"la",R.drawable.peach));
        fruits.add(new Thing(getString(R.string.pear),"la",R.drawable.pear));
        fruits.add(new Thing(getString(R.string.persimmon),"le",R.drawable.persimmon));
        fruits.add(new Thing(getString(R.string.plum),"la",R.drawable.plum));
        fruits.add(new Thing(getString(R.string.quince),"le",R.drawable.quince));
        fruits.add(new Thing(getString(R.string.rambutan),"le",R.drawable.rambutan));
        fruits.add(new Thing(getString(R.string.raspberry),"la",R.drawable.raspberry));
        fruits.add(new Thing(getString(R.string.date),"la",R.drawable.date));

        blackImageView = findViewById(R.id.imageBlackView);
        redImageView = findViewById(R.id.imageRedView);
        yellowImageView = findViewById(R.id.imageYellowView);
        blueImageView = findViewById(R.id.imageBlueView);
        purpleImageView = findViewById(R.id.imagePurpleView);
        pinkImageView = findViewById(R.id.imagePinkView);
        greenImageView = findViewById(R.id.imageGreenView);
        whiteImageView = findViewById(R.id.imageWhiteView);
        grayImageView = findViewById(R.id.imageGrayView);


        blackLocation = findViewById(R.id.circleBlackView);
        redLocation = findViewById(R.id.circleRedView);
        yellowLocation = findViewById(R.id.circleYellowView);
        blueLocation = findViewById(R.id.circleBlueView);
        purpleLocation = findViewById(R.id.circlePurpleView);
        pinkLocation = findViewById(R.id.circlePinkView);
        greenLocation = findViewById(R.id.circleGreenView);
        whiteLocation = findViewById(R.id.circleWhiteView);
        grayLocation = findViewById(R.id.circleGrayView);

        blackMystery = findViewById(R.id.blackMystery);
        redMystery = findViewById(R.id.redMystery);
        yellowMystery = findViewById(R.id.yellowMystery);
        blueMystery = findViewById(R.id.blueMystery);
        purpleMystery = findViewById(R.id.purpleMystery);
        pinkMystery = findViewById(R.id.pinkMystery);
        greenMystery = findViewById(R.id.greenMystery);
        whiteMystery = findViewById(R.id.whiteMystery);
        grayMystery = findViewById(R.id.grayMystery);
        
        fruitTextView = findViewById(R.id.fruit);
        message = findViewById(R.id.operation_answer);
        message.setVisibility(View.INVISIBLE);

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
    private void hideFruits() {
        blackImageView.setVisibility(View.INVISIBLE);
        redImageView.setVisibility(View.INVISIBLE);
        yellowImageView.setVisibility(View.INVISIBLE);
        blueImageView.setVisibility(View.INVISIBLE);
        purpleImageView.setVisibility(View.INVISIBLE);
        pinkImageView.setVisibility(View.INVISIBLE);
        greenImageView.setVisibility(View.INVISIBLE);
        whiteImageView.setVisibility(View.INVISIBLE);
        grayImageView.setVisibility(View.INVISIBLE);
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
        if (map[location] == theFruit)
        {
            dialogue.startLoadingDialogue(R.layout.success);
        }

        else
            message.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void generateMap(){
        hideFruits();
        showMystery();
        map = new int[]{-1,-1,-1,
                -1,-1,-1,
                -1,-1,-1};
        while(true){
            theFruit = new Random().nextInt(fruits.size());
            if (theFruit != previousFruit) {
                previousFruit = theFruit;
                break;
            }
        }
        map[0] = theFruit;
        for (int j = 1; j < 3; j++) {
            map[j] = new Random().nextInt(fruits.size());
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
                fruitTextView.setText("Find the "+fruits.get(theFruit).getNAme().toLowerCase());
                break;
            }
            case "French":{
                String str = fruits.get(theFruit).getGender();
                if (str.equals("l'"))
                    fruitTextView.setText("Trouver "+str+fruits.get(theFruit).getNAme().toLowerCase());
                else
                    fruitTextView.setText("Trouver "+str+" "+fruits.get(theFruit).getNAme().toLowerCase());
                break;
            }
            case "Arabic":{
                if (fruits.get(theFruit).getNAme().contains(" "))
                    fruitTextView.setText("جِد "+fruits.get(theFruit).getNAme().toLowerCase());
                else
                    fruitTextView.setText("جِد ال"+fruits.get(theFruit).getNAme().toLowerCase());
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
        Thing fruit = fruits.get(map[j]);
        switch (j){
            case 0:{
                blackImageView.setVisibility(View.VISIBLE);
                blackImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 1:{
                redImageView.setVisibility(View.VISIBLE);
                redImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 2:{
                yellowImageView.setVisibility(View.VISIBLE);
                yellowImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 3:{
                blueImageView.setVisibility(View.VISIBLE);
                blueImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 4:{
                purpleImageView.setVisibility(View.VISIBLE);
                purpleImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 5:{
                pinkImageView.setVisibility(View.VISIBLE);
                pinkImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 6:{
                greenImageView.setVisibility(View.VISIBLE);
                greenImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 7:{
                whiteImageView.setVisibility(View.VISIBLE);
                whiteImageView.setImageResource(fruit.getImageResourceId());
                break;
            }
            case 8:{
                grayImageView.setVisibility(View.VISIBLE);
                grayImageView.setImageResource(fruit.getImageResourceId());
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

                blackMystery.setVisibility(View.INVISIBLE);
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

                redMystery.setVisibility(View.INVISIBLE);
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

                yellowMystery.setVisibility(View.INVISIBLE);
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

                blueMystery.setVisibility(View.INVISIBLE);
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

                purpleMystery.setVisibility(View.INVISIBLE);
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

                pinkMystery.setVisibility(View.INVISIBLE);
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

                greenMystery.setVisibility(View.INVISIBLE);
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

                whiteMystery.setVisibility(View.INVISIBLE);
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

                grayMystery.setVisibility(View.INVISIBLE);
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

    private void showMystery(){
        blackMystery.setVisibility(View.VISIBLE);
        redMystery.setVisibility(View.VISIBLE);
        yellowMystery.setVisibility(View.VISIBLE);
        blueMystery.setVisibility(View.VISIBLE);
        purpleMystery.setVisibility(View.VISIBLE);
        pinkMystery.setVisibility(View.VISIBLE);
        greenMystery.setVisibility(View.VISIBLE);
        whiteMystery.setVisibility(View.VISIBLE);
        grayMystery.setVisibility(View.VISIBLE);
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
    }
}