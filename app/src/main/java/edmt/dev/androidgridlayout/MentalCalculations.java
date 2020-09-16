package edmt.dev.androidgridlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MentalCalculations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_calculations);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void clickOnLevel0(View view) {
        Intent intent = new Intent(MentalCalculations.this, MentalCalculationsLevel0.class);
        startActivity(intent);
    }
}