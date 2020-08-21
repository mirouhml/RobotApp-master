package edmt.dev.androidgridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MissingOneLetter extends AppCompatActivity {
    boolean firstLetter;
    List<String> words;
    String word;
    int theWord;
    String c;
    String firstHalf;
    String secondHalf;
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
    int location;
    String [] map;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    SimpleBluetoothDeviceInterface deviceInterface;
    final LoadingDialogue dialogue = new LoadingDialogue(MissingOneLetter.this);
    String [] lettersUsed;
    final String [] englishAlpha = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    final String [] arabicAlpha = {"غ","ظ","ض","ذ","خ","ث","ت","ش","ر","ق","ص","ف","ع","س","ن","م","ل","ك","ي","ط","ح","ز","و","ه","د","ج","ب","أ"};
    
    TextView firstHalfTV;
    TextView secondHalfTV;
    TextView wordTV;
    TextView splitTV;
    String language;
    EditText letterET;
    int index = 0;
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_one_letter);
        words = new ArrayList<>();
        setTitle(R.string.words_game);
        addWords();
        Collections.shuffle(words);
        firstHalfTV = findViewById(R.id.first_half);
        secondHalfTV = findViewById(R.id.second_half);
        wordTV = findViewById(R.id.word);
        splitTV = findViewById(R.id.split);
        letterET = findViewById(R.id.letter);
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
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        language = gameSettings.getString("Language","DEFAULT");
        refresh();

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

    private void generateMap() {

        assert language != null;
        if(language.equals("Arabic")){
            lettersUsed = arabicAlpha;
            Button choose = findViewById(R.id.choose);
            Button choose2 = findViewById(R.id.choose2);
            Button next = findViewById(R.id.next);
            Button next2 = findViewById(R.id.next2);
            choose.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            choose2.setVisibility(View.VISIBLE);
            next2.setVisibility(View.VISIBLE);
        }
        else{
            lettersUsed = englishAlpha;
        }

        map = new String[]{"-1","-1","-1",
                "-1","-1","-1",
                "-1","-1","-1"};
        word = words.get(index);
        index++;
        String [] stringArray = word.split("");
        c=" ";
        int splitCharacter =0;
        while (c.equals(" ")){
            splitCharacter = new Random().nextInt(stringArray.length-1);
            c = stringArray[splitCharacter+1];
        }
        map[0]=c;
        for (int j = 1; j < 3; j++) {
            map[j] = randomLetter().toLowerCase();
            if (map[j].equals(c))
                while(map[j].equals(c))
                    map[j] = randomLetter();
            Log.e("Error",map[j]);
            for (int k = 0; k < j; k++) {
                if (map[j].equals(map[k])) {
                    j--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
        shuffleArray(map);
        for(int j=0; j<map.length;j++){
            if(!map[j].equals("-1"))
                setTile(j);
        }

        firstHalf = word.substring(0,splitCharacter);
        secondHalf = word.substring(splitCharacter+1);
        //Toast.makeText(this, ""+firstHalf+"+"+c+"+"+secondHalf, Toast.LENGTH_SHORT).show();
        assert language != null;
        if(language.equals("Arabic")){
            int position;
            char [] characters = {'غ','ظ','ض','خ','ث','ت','ش','ق','ص','ف','ع','س','ن','م','ل','ك','ي','ط','ح','ه','ج','ب'};
            if (firstHalf.length() > 0){
                position = firstHalf.length()-1;
                char lastCharacter = firstHalf.charAt(position);
                for (char chara : characters ){
                    if (chara == lastCharacter){
                        firstHalf = firstHalf + "ـ";
                        break;
                    }
                }
            }
            if (secondHalf.length() > 0){
                for (char chara : characters){
                    if (String.valueOf(chara).equals(c)){
                        if(secondHalf.charAt(0) != ' ')
                            secondHalf = "ـ" + secondHalf;
                        break;
                    }
                }
            }
            firstHalfTV.setText(secondHalf);
            secondHalfTV.setText(firstHalf);
        }
        else{
            firstHalfTV.setText(firstHalf);
            secondHalfTV.setText(secondHalf);
        }
    }

    String randomLetter(){
        return lettersUsed[new Random().nextInt(lettersUsed.length)];
    }
    private void setTile(int j){
        switch (j){
            case 0:{
                blackText.setText(map[j].toUpperCase());
                break;
            }
            case 1:{
                redText.setText(map[j].toUpperCase());
                break;
            }
            case 2:{
                yellowText.setText(map[j].toUpperCase());
                break;
            }
            case 3:{
                blueText.setText(map[j].toUpperCase());
                break;
            }
            case 4:{
                purpleText.setText(map[j].toUpperCase());
                break;
            }
            case 5:{
                pinkText.setText(map[j].toUpperCase());
                break;
            }
            case 6:{
                greenText.setText(map[j].toUpperCase());
                break;
            }
            case 7:{
                whiteText.setText(map[j].toUpperCase());
                break;
            }
            case 8:{
                grayText.setText(map[j].toUpperCase());
                break;
            }
        }
    }

    private void shuffleArray(String [] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public void clickChoose(View view) {
        if(location == -1){
            Toast.makeText(this, "Please place the robot on the map", Toast.LENGTH_SHORT).show();
            return;
        }
        //Toast.makeText(this, ""+location+ " "+ map[location], Toast.LENGTH_SHORT).show();
        //String result = firstHalf.toLowerCase() + map[location] + secondHalf;
        if (c.toLowerCase().equals(map[location].toLowerCase())){
            firstHalfTV.setVisibility(View.GONE);
            secondHalfTV.setVisibility(View.GONE);
            splitTV.setVisibility(View.GONE);
            wordTV.setVisibility(View.VISIBLE);
            wordTV.setText(word);
            dialogue.startLoadingDialogue(R.layout.success);
        }
        else
            message.setVisibility(View.VISIBLE);
    }

    public void clickNext(View view) {
        refresh();
        message.setVisibility(View.INVISIBLE);
        if (dialogue.isOn){
            dialogue.dismissLoadingDIalogue();
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
        firstHalfTV.setVisibility(View.VISIBLE);
        secondHalfTV.setVisibility(View.VISIBLE);
        splitTV.setVisibility(View.VISIBLE);
        wordTV.setVisibility(View.GONE);
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




    private void addWords() {
        words.add(getString(R.string.cat));
        words.add(getString(R.string.dog));
        words.add(getString(R.string.chicken));
        words.add(getString(R.string.duck));
        words.add(getString(R.string.dolphin));
        words.add(getString(R.string.elephant));
        words.add(getString(R.string.camel));
        words.add(getString(R.string.deer));
        words.add(getString(R.string.giragge));
        words.add(getString(R.string.lion));
        words.add(getString(R.string.sheep));
        words.add(getString(R.string.rabbit));
        words.add(getString(R.string.horse));
        words.add(getString(R.string.monkey));
        words.add(getString(R.string.swan));
        words.add(getString(R.string.turtle));
        words.add(getString(R.string.squirrel));
        words.add(getString(R.string.koala));
        words.add(getString(R.string.panda_bear));
        words.add(getString(R.string.frog));
        words.add(getString(R.string.pig));
        words.add(getString(R.string.snake));
        words.add(getString(R.string.bee));
        words.add(getString(R.string.crab));
        words.add(getString(R.string.flamingo));
        words.add(getString(R.string.hedgehog));
        words.add(getString(R.string.hippopotamus));
        words.add(getString(R.string.jellyfish));
        words.add(getString(R.string.kangaroo));
        words.add(getString(R.string.llama));
        words.add(getString(R.string.octopus));
        words.add(getString(R.string.owl));
        words.add(getString(R.string.penguin));
        words.add(getString(R.string.seahorse));
        words.add(getString(R.string.seal));
        words.add(getString(R.string.shark));
        words.add(getString(R.string.whale));
        words.add(getString(R.string.ant));
        words.add(getString(R.string.anteater));
        words.add(getString(R.string.arctic_fox));
        words.add(getString(R.string.bear));
        words.add(getString(R.string.beetle));
        words.add(getString(R.string.bison));
        words.add(getString(R.string.black_panther));
        words.add(getString(R.string.chameleon));
        words.add(getString(R.string.cheetah));
        words.add(getString(R.string.clown_fish));
        words.add(getString(R.string.cougar));
        words.add(getString(R.string.cow));
        words.add(getString(R.string.crocodile));
        words.add(getString(R.string.eagle));
        words.add(getString(R.string.emu));
        words.add(getString(R.string.fox));
        words.add(getString(R.string.goat));
        words.add(getString(R.string.gorilla));
        words.add(getString(R.string.hamster));
        words.add(getString(R.string.jaguar));
        words.add(getString(R.string.ladybug));
        words.add(getString(R.string.maki));
        words.add(getString(R.string.leopard));
        words.add(getString(R.string.lobster));
        words.add(getString(R.string.mouse));
        words.add(getString(R.string.orca));
        words.add(getString(R.string.ostrich));
        words.add(getString(R.string.otter));
        words.add(getString(R.string.peacock));
        words.add(getString(R.string.pigeon));
        words.add(getString(R.string.polar_bear));
        words.add(getString(R.string.prawn));
        words.add(getString(R.string.puffer_fish));
        words.add(getString(R.string.raccoon));
        words.add(getString(R.string.rat));
        words.add(getString(R.string.red_panda));
        words.add(getString(R.string.reindeer));
        words.add(getString(R.string.rhinoceros));
        words.add(getString(R.string.salmon));
        words.add(getString(R.string.scorpion));
        words.add(getString(R.string.skunk));
        words.add(getString(R.string.sloth));
        words.add(getString(R.string.snail));
        words.add(getString(R.string.spider));
        words.add(getString(R.string.starfish));
        words.add(getString(R.string.tiger));
        words.add(getString(R.string.tortoise));
        words.add(getString(R.string.toucan));
        words.add(getString(R.string.turkey));
        words.add(getString(R.string.walrus));
        words.add(getString(R.string.wolf));
        words.add(getString(R.string.yak));
        words.add(getString(R.string.zebra));
        words.add(getString(R.string.apple));
        words.add(getString(R.string.banana));
        words.add(getString(R.string.berries));
        words.add(getString(R.string.cherry));
        words.add(getString(R.string.grapes));
        words.add(getString(R.string.coconut));
        words.add(getString(R.string.kiwi));
        words.add(getString(R.string.lemon));
        words.add(getString(R.string.melon));
        words.add(getString(R.string.orange));
        words.add(getString(R.string.pineapple));
        words.add(getString(R.string.strawberry));
        words.add(getString(R.string.watermelon));
        words.add(getString(R.string.pumpkin));
        words.add(getString(R.string.tomato));
        words.add(getString(R.string.grenade));
        words.add(getString(R.string.apricot));
        words.add(getString(R.string.avocado));
        words.add(getString(R.string.blueberry));
        words.add(getString(R.string.chestnut));
        words.add(getString(R.string.fig));
        words.add(getString(R.string.gooseberry));
        words.add(getString(R.string.grapefruit));
        words.add(getString(R.string.guava));
        words.add(getString(R.string.kumquat));
        words.add(getString(R.string.lime));
        words.add(getString(R.string.mango));
        words.add(getString(R.string.mangosteen));
        words.add(getString(R.string.papaya));
        words.add(getString(R.string.granadilla));
        words.add(getString(R.string.peacj));
        words.add(getString(R.string.pear));
        words.add(getString(R.string.persimmon));
        words.add(getString(R.string.plum));
        words.add(getString(R.string.quince));
        words.add(getString(R.string.rambutan));
        words.add(getString(R.string.raspberry));
        words.add(getString(R.string.date));
        words.add(getString(R.string.carrot));
        words.add(getString(R.string.bell_pepper));
        words.add(getString(R.string.corn));
        words.add(getString(R.string.cucumber));
        words.add(getString(R.string.eggplant));
        words.add(getString(R.string.garlic));
        words.add(getString(R.string.peas));
        words.add(getString(R.string.potato));
        words.add(getString(R.string.radish));
        words.add(getString(R.string.lettuce));
        words.add(getString(R.string.broccoli));
        words.add(getString(R.string.beet));
        words.add(getString(R.string.cabbage));
        words.add(getString(R.string.cauliflower));
        words.add(getString(R.string.chili_pepper));
        words.add(getString(R.string.ginger));
        words.add(getString(R.string.horseradish));
        words.add(getString(R.string.olives));
        words.add(getString(R.string.spinach));
        words.add(getString(R.string.turnip));
        words.add(getString(R.string.zucchini));
    }

    public void onOk(View view){
        dialogue.dismissLoadingDIalogue();
        finish();
    }
}