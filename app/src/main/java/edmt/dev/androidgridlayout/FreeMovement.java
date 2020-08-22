package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.harrysoft.androidbluetoothserial.BluetoothManager;
import com.harrysoft.androidbluetoothserial.BluetoothSerialDevice;
import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FreeMovement extends AppCompatActivity {
    ImageView blackLocation;
    ImageView redLocation;
    ImageView yellowLocation;
    ImageView blueLocation;
    ImageView purpleLocation;
    ImageView pinkLocation;
    ImageView greenLocation;
    ImageView whiteLocation;
    ImageView grayLocation;
    int location;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter;
    final LoadingDialogue dialogue = new LoadingDialogue(FreeMovement.this);
    private SimpleBluetoothDeviceInterface deviceInterface;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_movement);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.free_movement);
        dialogue.startLoadingDialogue(R.layout.loading_dialogue);
        bluetoothManager = BluetoothManager.getInstance();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //checking whether the bluetooth is on or not
        if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
        //showing the loading view
        new AsyncTask<Void, Void, Void>() {
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

        blackLocation = findViewById(R.id.circleBlackView);
        redLocation = findViewById(R.id.circleRedView);
        yellowLocation = findViewById(R.id.circleYellowView);
        blueLocation = findViewById(R.id.circleBlueView);
        purpleLocation = findViewById(R.id.circlePurpleView);
        pinkLocation = findViewById(R.id.circlePinkView);
        greenLocation = findViewById(R.id.circleGreenView);
        whiteLocation = findViewById(R.id.circleWhiteView);
        grayLocation = findViewById(R.id.circleGrayView);
        location = -1;
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

    public void clickRight(View view) {
        deviceInterface.sendMessage("0");
    }

    public void clickLeft(View view) {
        deviceInterface.sendMessage("0");
    }

    public void clickUp(View view) {
        deviceInterface.sendMessage("1");
    }

    public void clickDown(View view) {
        deviceInterface.sendMessage("1");
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
        Log.e("Bluetooth error", error.toString());
    }

    public void onOk(View view) {
        dialogue.dismissLoadingDIalogue();
        finish();
    }

    private void setLocation() {
        switch (location) {
            case 0: {
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
            case 1: {
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
            case 2: {
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
            case 3: {
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
            case 4: {
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
            case 5: {
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
            case 6: {
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
            case 7: {
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
            case 8: {
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
            default: {
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
}