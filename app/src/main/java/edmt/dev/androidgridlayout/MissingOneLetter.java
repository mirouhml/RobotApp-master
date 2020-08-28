package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    final String[] frenchAlpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "É", "È", "Ç", "À", "Ô", "Î", "Â", "Ï", "Œ"};
    final String[] englishAlpha = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    String c;
    String firstHalf;
    String secondHalf;
    final String[] arabicAlpha = {"غ", "ظ", "ض", "ذ", "خ", "ث", "ت", "ش", "ر", "ق", "ص", "ف", "ع", "س", "ن", "م", "ل", "ك", "ي", "ط", "ح", "ز", "و", "ه", "د", "ج", "ب", "أ"};

    TextView message;
    int location;
    List<Thing> words;
    Thing word;

    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    SimpleBluetoothDeviceInterface deviceInterface;
    final LoadingDialogue dialogue = new LoadingDialogue(MissingOneLetter.this);
    String[] map;
    String[] lettersUsed;
    private CreateVariables variables;
    private boolean stopped = false;

    TextView firstHalfTV;
    TextView secondHalfTV;
    TextView wordTV;
    TextView splitTV;
    String language;
    EditText letterET;
    int index = 0;

    @SuppressLint({"StaticFieldLeak", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missing_one_letter);
        variables = new CreateVariables(this);
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
        message = findViewById(R.id.operation_answer);
        message.setVisibility(View.GONE);
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
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        language = gameSettings.getString("Language", "DEFAULT");
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
        if (language.equals("Arabic")) {
            lettersUsed = arabicAlpha;
            Button choose = findViewById(R.id.choose);
            Button choose2 = findViewById(R.id.choose2);
            Button next = findViewById(R.id.next);
            Button next2 = findViewById(R.id.next2);
            choose.setVisibility(View.GONE);
            next.setVisibility(View.GONE);
            choose2.setVisibility(View.VISIBLE);
            next2.setVisibility(View.VISIBLE);
        } else if (language.equals("French")) {
            lettersUsed = frenchAlpha;
        } else {
            lettersUsed = englishAlpha;
        }

        map = new String[]{"-1", "-1", "-1",
                "-1", "-1", "-1",
                "-1", "-1", "-1"};
        word = words.get(index);
        ImageView hint = findViewById(R.id.hint);
        hint.setImageResource(word.getImageResourceId());
        index++;
        String[] stringArray = word.getNAme().split("");
        c = " ";
        int splitCharacter = 0;
        while (c.equals(" ")) {
            splitCharacter = new Random().nextInt(stringArray.length - 1);
            c = stringArray[splitCharacter + 1];
        }
        Log.d("MissingOneLetter", "removed letter: " + c);
        map[0] = c;
        for (int j = 1; j < 3; j++) {
            map[j] = randomLetter().toLowerCase();
            if (map[j].equals(c))
                while (map[j].equals(c))
                    map[j] = randomLetter();
            Log.e("Error", map[j]);
            for (int k = 0; k < j; k++) {
                if (map[j].equals(map[k])) {
                    j--; //if a[i] is a duplicate of a[j], then run the outer loop on i again
                    break;
                }
            }
        }
        shuffleArray(map);
        for (int j = 0; j < map.length; j++) {
            if (!map[j].equals("-1"))
                setTile(j);
        }

        firstHalf = word.getNAme().substring(0, splitCharacter);
        secondHalf = word.getNAme().substring(splitCharacter + 1);
        Log.d("MissingOneLetter", "firstHalf: " + firstHalf + " secondHalf: " + secondHalf);
        //Toast.makeText(this, ""+firstHalf+"+"+c+"+"+secondHalf, Toast.LENGTH_SHORT).show();
        assert language != null;
        if (language.equals("Arabic")) {
            int position;
            char[] characters = {'غ', 'ظ', 'ض', 'خ', 'ث', 'ت', 'ش', 'ق', 'ص', 'ف', 'ع', 'س', 'ن', 'م', 'ل', 'ك', 'ي', 'ط', 'ح', 'ه', 'ج', 'ب'};
            if (firstHalf.length() > 0) {
                position = firstHalf.length() - 1;
                char lastCharacter = firstHalf.charAt(position);
                for (char chara : characters) {
                    if (chara == lastCharacter) {
                        firstHalf = firstHalf + "ـ";
                        break;
                    }
                }
            }
            if (secondHalf.length() > 0) {
                for (char chara : characters) {
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
                variables.getBlackText().setText(map[j].toUpperCase());
                break;
            }
            case 1:{
                variables.getRedText().setText(map[j].toUpperCase());
                break;
            }
            case 2:{
                variables.getYellowText().setText(map[j].toUpperCase());
                break;
            }
            case 3:{
                variables.getBlueText().setText(map[j].toUpperCase());
                break;
            }
            case 4:{
                variables.getPurpleText().setText(map[j].toUpperCase());
                break;
            }
            case 5:{
                variables.getPinkText().setText(map[j].toUpperCase());
                break;
            }
            case 6:{
                variables.getGreenText().setText(map[j].toUpperCase());
                break;
            }
            case 7:{
                variables.getWhiteText().setText(map[j].toUpperCase());
                break;
            }
            case 8:{
                variables.getGrayText().setText(map[j].toUpperCase());
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
        if (c.equalsIgnoreCase(map[location].toLowerCase())) {
            firstHalfTV.setVisibility(View.GONE);
            secondHalfTV.setVisibility(View.GONE);
            splitTV.setVisibility(View.GONE);
            wordTV.setVisibility(View.VISIBLE);
            wordTV.setText(word.getNAme());
            dialogue.startLoadingDialogue(R.layout.success);
        } else
            message.setVisibility(View.VISIBLE);
    }

    public void clickNext(View view) {
        refresh();
        message.setVisibility(View.GONE);
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
        //Toast.makeText(this, "Sent a message! Message was: " + message, Toast.LENGTH_LONG).show(); // Replace context with your context instance.
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
        firstHalfTV.setVisibility(View.VISIBLE);
        secondHalfTV.setVisibility(View.VISIBLE);
        splitTV.setVisibility(View.VISIBLE);
        wordTV.setVisibility(View.GONE);
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






    private void addWords() {
        words.add(new Thing(getString(R.string.cat), "le", R.drawable.cat2));
        words.add(new Thing(getString(R.string.dog), "le", R.drawable.dog));
        words.add(new Thing(getString(R.string.chicken), "le", R.drawable.chicken));
        words.add(new Thing(getString(R.string.duck), "le", R.drawable.duck));
        words.add(new Thing(getString(R.string.dolphin), "le", R.drawable.dolphin));
        words.add(new Thing(getString(R.string.elephant), "l'", R.drawable.elephant));
        words.add(new Thing(getString(R.string.camel), "le", R.drawable.camel));
        words.add(new Thing(getString(R.string.deer), "le", R.drawable.deer));
        words.add(new Thing(getString(R.string.giragge), "la", R.drawable.giraffe));
        words.add(new Thing(getString(R.string.lion), "le", R.drawable.lion));
        words.add(new Thing(getString(R.string.sheep), "le", R.drawable.sheep));
        words.add(new Thing(getString(R.string.rabbit), "le", R.drawable.rabbit));
        words.add(new Thing(getString(R.string.horse), "le", R.drawable.horse));
        words.add(new Thing(getString(R.string.monkey), "le", R.drawable.monkey));
        words.add(new Thing(getString(R.string.swan), "le", R.drawable.swan));
        words.add(new Thing(getString(R.string.turtle), "la", R.drawable.turtle));
        words.add(new Thing(getString(R.string.squirrel), "l'", R.drawable.squirrel));
        words.add(new Thing(getString(R.string.koala), "le", R.drawable.koala));
        words.add(new Thing(getString(R.string.panda_bear), "le", R.drawable.panda_bear));
        words.add(new Thing(getString(R.string.frog), "la", R.drawable.frog));
        words.add(new Thing(getString(R.string.pig), "le", R.drawable.pig));
        words.add(new Thing(getString(R.string.snake), "le", R.drawable.snake));
        words.add(new Thing(getString(R.string.bee), "l'", R.drawable.bee));
        words.add(new Thing(getString(R.string.crab), "le", R.drawable.crab));
        words.add(new Thing(getString(R.string.flamingo), "le", R.drawable.flamingo));
        words.add(new Thing(getString(R.string.hedgehog), "l'", R.drawable.hedgehog));
        words.add(new Thing(getString(R.string.hippopotamus), "l'", R.drawable.hippopotamus));
        words.add(new Thing(getString(R.string.jellyfish), "la", R.drawable.jellyfish));
        words.add(new Thing(getString(R.string.kangaroo), "le", R.drawable.kangaroo));
        words.add(new Thing(getString(R.string.llama), "le", R.drawable.llama));
        words.add(new Thing(getString(R.string.octopus), "le", R.drawable.octopus));
        words.add(new Thing(getString(R.string.owl), "l'", R.drawable.owl));
        words.add(new Thing(getString(R.string.penguin), "le", R.drawable.penguin));
        words.add(new Thing(getString(R.string.seahorse), "l'", R.drawable.seahorse));
        words.add(new Thing(getString(R.string.seal), "le", R.drawable.seal));
        words.add(new Thing(getString(R.string.shark), "le", R.drawable.shark));
        words.add(new Thing(getString(R.string.whale), "la", R.drawable.whale));
        words.add(new Thing(getString(R.string.ant), "la", R.drawable.ant));
        words.add(new Thing(getString(R.string.anteater), "le", R.drawable.anteater));
        words.add(new Thing(getString(R.string.arctic_fox), "le", R.drawable.arctic_fox));
        words.add(new Thing(getString(R.string.bear), "l'", R.drawable.bear));
        words.add(new Thing(getString(R.string.beetle), "le", R.drawable.beetle));
        words.add(new Thing(getString(R.string.bison), "le", R.drawable.bison));
        words.add(new Thing(getString(R.string.black_panther), "la", R.drawable.black_panther));
        words.add(new Thing(getString(R.string.chameleon), "le", R.drawable.chameleon));
        words.add(new Thing(getString(R.string.cheetah), "le", R.drawable.cheetah));
        words.add(new Thing(getString(R.string.clown_fish), "le", R.drawable.clown_fish));
        words.add(new Thing(getString(R.string.cougar), "le", R.drawable.cougar));
        words.add(new Thing(getString(R.string.cow), "la", R.drawable.cow));
        words.add(new Thing(getString(R.string.crocodile), "le", R.drawable.crocodile));
        words.add(new Thing(getString(R.string.eagle), "l'", R.drawable.eagle));
        words.add(new Thing(getString(R.string.emu), "l'", R.drawable.emu));
        words.add(new Thing(getString(R.string.fox), "le", R.drawable.fox));
        words.add(new Thing(getString(R.string.goat), "la", R.drawable.goat));
        words.add(new Thing(getString(R.string.gorilla), "le", R.drawable.gorilla));
        words.add(new Thing(getString(R.string.hamster), "le", R.drawable.hamster));
        words.add(new Thing(getString(R.string.jaguar), "le", R.drawable.jaguar));
        words.add(new Thing(getString(R.string.ladybug), "la", R.drawable.ladybug));
        words.add(new Thing(getString(R.string.maki), "le", R.drawable.lemur));
        words.add(new Thing(getString(R.string.leopard), "le", R.drawable.leopard));
        words.add(new Thing(getString(R.string.lobster), "l'", R.drawable.lobster));
        words.add(new Thing(getString(R.string.mouse), "la", R.drawable.mouse));
        words.add(new Thing(getString(R.string.orca), "l'", R.drawable.orca));
        words.add(new Thing(getString(R.string.ostrich), "l'", R.drawable.ostrich));
        words.add(new Thing(getString(R.string.otter), "la", R.drawable.otter));
        words.add(new Thing(getString(R.string.peacock), "le", R.drawable.peacock));
        words.add(new Thing(getString(R.string.pigeon), "le", R.drawable.pigeon));
        words.add(new Thing(getString(R.string.polar_bear), "l'", R.drawable.polar_bear));
        words.add(new Thing(getString(R.string.prawn), "la", R.drawable.prawn));
        words.add(new Thing(getString(R.string.puffer_fish), "le", R.drawable.puffer_fish));
        words.add(new Thing(getString(R.string.raccoon), "le", R.drawable.raccoon));
        words.add(new Thing(getString(R.string.rat), "le", R.drawable.rat));
        words.add(new Thing(getString(R.string.red_panda), "le", R.drawable.red_panda));
        words.add(new Thing(getString(R.string.reindeer), "le", R.drawable.reindeer));
        words.add(new Thing(getString(R.string.rhinoceros), "le", R.drawable.rhinoceros));
        words.add(new Thing(getString(R.string.salmon), "le", R.drawable.salmon));
        words.add(new Thing(getString(R.string.scorpion), "le", R.drawable.scorpion));
        words.add(new Thing(getString(R.string.skunk), "la", R.drawable.skunk));
        words.add(new Thing(getString(R.string.sloth), "la", R.drawable.sloth));
        words.add(new Thing(getString(R.string.snail), "l'", R.drawable.snail));
        words.add(new Thing(getString(R.string.spider), "l'", R.drawable.spider));
        words.add(new Thing(getString(R.string.starfish), "l'", R.drawable.starfish));
        words.add(new Thing(getString(R.string.tiger), "le", R.drawable.tiger));
        words.add(new Thing(getString(R.string.tortoise), "la", R.drawable.tortoise));
        words.add(new Thing(getString(R.string.toucan), "le", R.drawable.toucan));
        words.add(new Thing(getString(R.string.turkey), "la", R.drawable.turkey));
        words.add(new Thing(getString(R.string.walrus), "le", R.drawable.walrus));
        words.add(new Thing(getString(R.string.wolf), "le", R.drawable.wolf));
        words.add(new Thing(getString(R.string.yak), "le", R.drawable.yak));
        words.add(new Thing(getString(R.string.zebra), "le", R.drawable.zebra));
        words.add(new Thing(getString(R.string.apple), "la", R.drawable.apple));
        words.add(new Thing(getString(R.string.banana), "la", R.drawable.banana));
        words.add(new Thing(getString(R.string.berries), "la", R.drawable.berries));
        words.add(new Thing(getString(R.string.cherry), "la", R.drawable.cherry));
        words.add(new Thing(getString(R.string.grapes), "le", R.drawable.grapes));
        words.add(new Thing(getString(R.string.coconut), "la", R.drawable.coconut));
        words.add(new Thing(getString(R.string.kiwi), "le", R.drawable.kiwi));
        words.add(new Thing(getString(R.string.lemon), "le", R.drawable.lemon));
        words.add(new Thing(getString(R.string.melon), "le", R.drawable.melon));
        words.add(new Thing(getString(R.string.orange), "l'", R.drawable.orange));
        words.add(new Thing(getString(R.string.pineapple), "l'", R.drawable.pineapple));
        words.add(new Thing(getString(R.string.strawberry), "la", R.drawable.strawberry));
        words.add(new Thing(getString(R.string.watermelon), "la", R.drawable.watermelon));
        words.add(new Thing(getString(R.string.pumpkin), "la", R.drawable.pumpkin));
        words.add(new Thing(getString(R.string.tomato), "la", R.drawable.tomato));
        words.add(new Thing(getString(R.string.grenade), "la", R.drawable.pomegranate));
        words.add(new Thing(getString(R.string.apricot), "l'", R.drawable.apricot));
        words.add(new Thing(getString(R.string.avocado), "l'", R.drawable.avocado));
        words.add(new Thing(getString(R.string.blueberry), "la", R.drawable.blueberry));
        words.add(new Thing(getString(R.string.chestnut), "la", R.drawable.chestnut));
        words.add(new Thing(getString(R.string.fig), "la", R.drawable.fig));
        words.add(new Thing(getString(R.string.gooseberry), "la", R.drawable.gooseberry));
        words.add(new Thing(getString(R.string.grapefruit), "le", R.drawable.grapefruit));
        words.add(new Thing(getString(R.string.guava), "la", R.drawable.guava));
        words.add(new Thing(getString(R.string.kumquat), "le", R.drawable.kumquat));
        words.add(new Thing(getString(R.string.lime), "le", R.drawable.lime));
        words.add(new Thing(getString(R.string.mango), "la", R.drawable.mango));
        words.add(new Thing(getString(R.string.mangosteen), "le", R.drawable.mangosteen));
        words.add(new Thing(getString(R.string.papaya), "la", R.drawable.papaya));
        words.add(new Thing(getString(R.string.granadilla), "la", R.drawable.passion_fruit_1));
        words.add(new Thing(getString(R.string.peacj), "la", R.drawable.peach));
        words.add(new Thing(getString(R.string.pear), "la", R.drawable.pear));
        words.add(new Thing(getString(R.string.persimmon), "le", R.drawable.persimmon));
        words.add(new Thing(getString(R.string.plum), "la", R.drawable.plum));
        words.add(new Thing(getString(R.string.quince), "le", R.drawable.quince));
        words.add(new Thing(getString(R.string.rambutan), "le", R.drawable.rambutan));
        words.add(new Thing(getString(R.string.raspberry), "la", R.drawable.raspberry));
        words.add(new Thing(getString(R.string.date), "la", R.drawable.date));
        words.add(new Thing(getString(R.string.carrot), "la", R.drawable.carrot));
        words.add(new Thing(getString(R.string.bell_pepper), "le", R.drawable.bell_pepper));
        words.add(new Thing(getString(R.string.corn), "le", R.drawable.corn));
        words.add(new Thing(getString(R.string.cucumber), "le", R.drawable.cucumber));
        words.add(new Thing(getString(R.string.eggplant), "l'", R.drawable.eggplant));
        words.add(new Thing(getString(R.string.garlic), "l'", R.drawable.garlic));
        words.add(new Thing(getString(R.string.peas), "les", R.drawable.peas));
        words.add(new Thing(getString(R.string.potato), "la", R.drawable.potato));
        words.add(new Thing(getString(R.string.radish), "le", R.drawable.radish));
        words.add(new Thing(getString(R.string.lettuce), "la", R.drawable.lettuce));
        words.add(new Thing(getString(R.string.broccoli), "le", R.drawable.broccoli));
        words.add(new Thing(getString(R.string.beet), "le", R.drawable.beet));
        words.add(new Thing(getString(R.string.cabbage), "le", R.drawable.cabbage));
        words.add(new Thing(getString(R.string.cauliflower), "le", R.drawable.cauliflower));
        words.add(new Thing(getString(R.string.chili_pepper), "le", R.drawable.chili_pepper));
        words.add(new Thing(getString(R.string.ginger), "le", R.drawable.ginger));
        words.add(new Thing(getString(R.string.horseradish), "le", R.drawable.horseradish));
        words.add(new Thing(getString(R.string.olives), "l'", R.drawable.olives));
        words.add(new Thing(getString(R.string.spinach), "l'", R.drawable.spinach));
        words.add(new Thing(getString(R.string.turnip), "le", R.drawable.turnip));
        words.add(new Thing(getString(R.string.zucchini), "la", R.drawable.zucchini));
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