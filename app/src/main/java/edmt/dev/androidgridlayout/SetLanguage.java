package edmt.dev.androidgridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.Locale;

public class SetLanguage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_language);
        SharedPreferences gameSettings = getSharedPreferences("MyGamePreferences", MODE_PRIVATE);
        String language = gameSettings.getString("Language","DEFAULT");
        if (language!=null)
        switch (language){
            case "English":{
                setLocale("en");
                break;
            }
            case "French":{
                setLocale("fr");
                break;
            }
            case "Arabic":{
                setLocale("ar");
                break;
            }
            default:{
                Intent refresh = new Intent(this, HomePage.class);
                startActivity(refresh);
                finish();
            }
        }
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, HomePage.class);
        startActivity(refresh);
        finish();
    }
}