package edmt.dev.androidgridlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MentalCalculations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_calculations);
    }

    public void clickOnLevel1(View view) {
        Intent intent = new Intent(MentalCalculations.this, MentalCalculationsLevel1.class);
        startActivity(intent);
    }

    public void clickOnLevel2(View view) {
        Intent intent = new Intent(MentalCalculations.this, MentalCalculationsLevel2.class);
        startActivity(intent);
    }

    public void clickOnLevel3(View view) {
        Intent intent = new Intent(MentalCalculations.this, MentalCalculationsLevel3.class);
        startActivity(intent);
    }
}