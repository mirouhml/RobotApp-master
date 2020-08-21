package edmt.dev.androidgridlayout;

import androidx.appcompat.app.AppCompatActivity;

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
import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;
import java.util.ArrayList;
import java.util.Random;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchForAnimals extends AppCompatActivity {
    final ArrayList<Thing> animals = new ArrayList<>();
    int[] map;
    int theAnimal;
    int previousAnimal = -1;
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

    TextView animalTextView;
    TextView message;
    private SimpleBluetoothDeviceInterface deviceInterface;
    int location;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    final LoadingDialogue dialogue = new LoadingDialogue(SearchForAnimals.this);
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_animals);
        setTitle(R.string.animals);
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
        animals.add(new Thing(getString(R.string.cat),"le",R.drawable.cat2));
        animals.add(new Thing(getString(R.string.dog),"le",R.drawable.dog));
        animals.add(new Thing(getString(R.string.chicken),"le",R.drawable.chicken));
        animals.add(new Thing(getString(R.string.duck),"le",R.drawable.duck));
        animals.add(new Thing(getString(R.string.dolphin),"le",R.drawable.dolphin));
        animals.add(new Thing(getString(R.string.elephant),"l'",R.drawable.elephant));
        animals.add(new Thing(getString(R.string.camel),"le",R.drawable.camel));
        animals.add(new Thing(getString(R.string.deer),"le",R.drawable.deer));
        animals.add(new Thing(getString(R.string.giragge),"la",R.drawable.giraffe));
        animals.add(new Thing(getString(R.string.lion),"le",R.drawable.lion));
        animals.add(new Thing(getString(R.string.sheep),"le",R.drawable.sheep));
        animals.add(new Thing(getString(R.string.rabbit),"le",R.drawable.rabbit));
        animals.add(new Thing(getString(R.string.horse),"le",R.drawable.horse));
        animals.add(new Thing(getString(R.string.monkey),"le",R.drawable.monkey));
        animals.add(new Thing(getString(R.string.swan),"le",R.drawable.swan));
        animals.add(new Thing(getString(R.string.turtle),"la",R.drawable.turtle));
        animals.add(new Thing(getString(R.string.squirrel),"l'",R.drawable.squirrel));
        animals.add(new Thing(getString(R.string.koala),"le",R.drawable.koala));
        animals.add(new Thing(getString(R.string.panda_bear),"le",R.drawable.panda_bear));
        animals.add(new Thing(getString(R.string.frog),"la",R.drawable.frog));
        animals.add(new Thing(getString(R.string.pig),"le",R.drawable.pig));
        animals.add(new Thing(getString(R.string.snake),"le",R.drawable.snake));
        animals.add(new Thing(getString(R.string.bee),"l'",R.drawable.bee));
        animals.add(new Thing(getString(R.string.crab),"le",R.drawable.crab));
        animals.add(new Thing(getString(R.string.flamingo),"le",R.drawable.flamingo));
        animals.add(new Thing(getString(R.string.hedgehog),"l'",R.drawable.hedgehog));
        animals.add(new Thing(getString(R.string.hippopotamus),"l'",R.drawable.hippopotamus));
        animals.add(new Thing(getString(R.string.jellyfish),"la",R.drawable.jellyfish));
        animals.add(new Thing(getString(R.string.kangaroo),"le",R.drawable.kangaroo));
        animals.add(new Thing(getString(R.string.llama),"le",R.drawable.llama));
        animals.add(new Thing(getString(R.string.octopus),"le",R.drawable.octopus));
        animals.add(new Thing(getString(R.string.owl),"l'",R.drawable.owl));
        animals.add(new Thing(getString(R.string.penguin),"le",R.drawable.penguin));
        animals.add(new Thing(getString(R.string.seahorse),"l'",R.drawable.seahorse));
        animals.add(new Thing(getString(R.string.seal),"le",R.drawable.seal));
        animals.add(new Thing(getString(R.string.shark),"le",R.drawable.shark));
        animals.add(new Thing(getString(R.string.whale),"la",R.drawable.whale));
        animals.add(new Thing(getString(R.string.ant),"la",R.drawable.ant));
        animals.add(new Thing(getString(R.string.anteater),"le",R.drawable.anteater));
        animals.add(new Thing(getString(R.string.arctic_fox),"le",R.drawable.arctic_fox));
        animals.add(new Thing(getString(R.string.bear),"l'",R.drawable.bear));
        animals.add(new Thing(getString(R.string.beetle),"le",R.drawable.beetle));
        animals.add(new Thing(getString(R.string.bison),"le",R.drawable.bison));
        animals.add(new Thing(getString(R.string.black_panther),"la",R.drawable.black_panther));
        animals.add(new Thing(getString(R.string.chameleon),"le",R.drawable.chameleon));
        animals.add(new Thing(getString(R.string.cheetah),"le",R.drawable.cheetah));
        animals.add(new Thing(getString(R.string.clown_fish),"le",R.drawable.clown_fish));
        animals.add(new Thing(getString(R.string.cougar),"le",R.drawable.cougar));
        animals.add(new Thing(getString(R.string.cow),"la",R.drawable.cow));
        animals.add(new Thing(getString(R.string.crocodile),"le",R.drawable.crocodile));
        animals.add(new Thing(getString(R.string.eagle),"l'",R.drawable.eagle));
        animals.add(new Thing(getString(R.string.emu),"l'",R.drawable.emu));
        animals.add(new Thing(getString(R.string.fox),"le",R.drawable.fox));
        animals.add(new Thing(getString(R.string.goat),"la",R.drawable.goat));
        animals.add(new Thing(getString(R.string.gorilla),"le",R.drawable.gorilla));
        animals.add(new Thing(getString(R.string.hamster),"le",R.drawable.hamster));
        animals.add(new Thing(getString(R.string.jaguar),"le",R.drawable.jaguar));
        animals.add(new Thing(getString(R.string.ladybug),"la",R.drawable.ladybug));
        animals.add(new Thing(getString(R.string.maki),"le",R.drawable.lemur));
        animals.add(new Thing(getString(R.string.leopard),"le",R.drawable.leopard));
        animals.add(new Thing(getString(R.string.lobster),"l'",R.drawable.lobster));
        animals.add(new Thing(getString(R.string.mouse),"la",R.drawable.mouse));
        animals.add(new Thing(getString(R.string.orca),"l'",R.drawable.orca));
        animals.add(new Thing(getString(R.string.ostrich),"l'",R.drawable.ostrich));
        animals.add(new Thing(getString(R.string.otter),"la",R.drawable.otter));
        animals.add(new Thing(getString(R.string.peacock),"le",R.drawable.peacock));
        animals.add(new Thing(getString(R.string.pigeon),"le",R.drawable.pigeon));
        animals.add(new Thing(getString(R.string.polar_bear),"l'",R.drawable.polar_bear));
        animals.add(new Thing(getString(R.string.prawn),"la",R.drawable.prawn));
        animals.add(new Thing(getString(R.string.puffer_fish),"le",R.drawable.puffer_fish));
        animals.add(new Thing(getString(R.string.raccoon),"le",R.drawable.raccoon));
        animals.add(new Thing(getString(R.string.rat),"le",R.drawable.rat));
        animals.add(new Thing(getString(R.string.red_panda),"le",R.drawable.red_panda));
        animals.add(new Thing(getString(R.string.reindeer),"le",R.drawable.reindeer));
        animals.add(new Thing(getString(R.string.rhinoceros),"le",R.drawable.rhinoceros));
        animals.add(new Thing(getString(R.string.salmon),"le",R.drawable.salmon));
        animals.add(new Thing(getString(R.string.scorpion),"le",R.drawable.scorpion));
        animals.add(new Thing(getString(R.string.skunk),"la",R.drawable.skunk));
        animals.add(new Thing(getString(R.string.sloth),"la",R.drawable.sloth));
        animals.add(new Thing(getString(R.string.snail),"l'",R.drawable.snail));
        animals.add(new Thing(getString(R.string.spider),"l'",R.drawable.spider));
        animals.add(new Thing(getString(R.string.starfish),"l'",R.drawable.starfish));
        animals.add(new Thing(getString(R.string.tiger),"le",R.drawable.tiger));
        animals.add(new Thing(getString(R.string.tortoise),"la",R.drawable.tortoise));
        animals.add(new Thing(getString(R.string.toucan),"le",R.drawable.toucan));
        animals.add(new Thing(getString(R.string.turkey),"la",R.drawable.turkey));
        animals.add(new Thing(getString(R.string.walrus),"le",R.drawable.walrus));
        animals.add(new Thing(getString(R.string.wolf),"le",R.drawable.wolf));
        animals.add(new Thing(getString(R.string.yak),"le",R.drawable.yak));
        animals.add(new Thing(getString(R.string.zebra),"le",R.drawable.zebra));








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

        animalTextView = findViewById(R.id.animal);
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

    private void hideAnimal() {
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
        if (map[location] == theAnimal)
        {
            dialogue.startLoadingDialogue(R.layout.success);
        }

        else
            message.setVisibility(View.VISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void generateMap(){
        hideAnimal();
        showMystery();
        map = new int[]{-1,-1,-1,
                -1,-1,-1,
                -1,-1,-1};
        while(true){
            theAnimal = new Random().nextInt(animals.size());
            if (theAnimal != previousAnimal) {
                previousAnimal = theAnimal;
                break;
            }
        }
        map[0] = theAnimal;
        for (int j = 1; j < 3; j++) {
            map[j] = new Random().nextInt(animals.size());
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
                animalTextView.setText("Find the "+animals.get(theAnimal).getNAme().toLowerCase());
                break;
            }
            case "French":{
                String str = animals.get(theAnimal).getGender();
                if (str.equals("l'"))
                    animalTextView.setText("Trouver "+str+animals.get(theAnimal).getNAme().toLowerCase());
                else
                    animalTextView.setText("Trouver "+str+" "+animals.get(theAnimal).getNAme().toLowerCase());
                break;
            }
            case "Arabic":{
                if (animals.get(theAnimal).getNAme().contains(" "))
                    animalTextView.setText("جِد "+animals.get(theAnimal).getNAme().toLowerCase());
                else
                    animalTextView.setText("جِد ال"+animals.get(theAnimal).getNAme().toLowerCase());
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
        Thing fruit = animals.get(map[j]);
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

    public void onOk(View view){
        dialogue.dismissLoadingDIalogue();
        finish();
    }
}