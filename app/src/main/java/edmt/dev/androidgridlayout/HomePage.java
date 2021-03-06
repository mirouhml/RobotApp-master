package edmt.dev.androidgridlayout;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class HomePage extends AppCompatActivity {
    private SharedPreferences gameSettings;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Locale locl = getResources().getConfiguration().locale;
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "This device doesn't have Bluetooth.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
        gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        prefEditor = gameSettings.edit();
        String language = gameSettings.getString("Language","DEFAULT");
        assert language != null;
        if(language.equals("DEFAULT"))
            switch (Locale.getDefault().getLanguage()){
                case "en":{
                    prefEditor.putString("Language", "English");
                    prefEditor.apply();
                    break;
                }
                case "fr":{
                    prefEditor.putString("Language", "French");
                    prefEditor.apply();
                    break;
                }
                case "ar":{
                    prefEditor.putString("Language", "Arabic");
                    prefEditor.apply();
                    break;
                }
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        prefEditor = gameSettings.edit();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case R.id.english:
                setLocale("en");
                prefEditor.putString("Language", "English");
                prefEditor.apply();
                break;
            case R.id.french:
                setLocale("fr");
                prefEditor.putString("Language", "French");
                prefEditor.apply();
                break;
            case R.id.arabic:
                setLocale("ar");
                prefEditor.putString("Language", "Arabic");
                prefEditor.apply();
                break;
        }
        Intent refresh = new Intent(this, HomePage.class);
        startActivity(refresh);
        finish();
        return super.onOptionsItemSelected(item);
    }


    public void clickOnMentalCalculation(View view){
        Intent intent = new Intent(HomePage.this, MentalCalculations.class);
        startActivity(intent);
    }

    public void clickOnFreeMovement(View view){
        Intent intent = new Intent(HomePage.this, FreeMovement.class);
        startActivity(intent);
    }

    public void clickOnHiddenObjects(View view){
        Intent intent = new Intent(HomePage.this, SearchForObject.class);
        startActivity(intent);
    }

    public void clickOnLearn(View view){
        Intent intent = new Intent(HomePage.this, Learn.class);
        startActivity(intent);
    }

    public void setLocale(String lang) { //call this in onCreate()
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public void clickOnWordGame(View view) {
        Intent intent = new Intent(HomePage.this, MissingOneLetter.class);
        startActivity(intent);
    }
}