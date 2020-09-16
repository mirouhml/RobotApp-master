package edmt.dev.androidgridlayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Learn extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.textHiddenObjects);
        textView.setText(getString(R.string.learning));
        setTitle(R.string.learning);
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

    public void clickOnFruits(View view){
        Intent intent = new Intent(Learn.this, LearnFruits.class);
        startActivity(intent);
    }

    public void clickOnVegetables(View view) {
        Intent intent = new Intent(Learn.this, LearnVegetables.class);
        startActivity(intent);
    }

    public void clickOnAnimals(View view) {
        Intent intent = new Intent(Learn.this, LearnAnimals.class);
        startActivity(intent);
    }

    public void clickOnNumbers(View view) {
        Intent intent = new Intent(Learn.this, LearnNumbers.class);
        startActivity(intent);
    }

    public void clickOnAlphabet(View view) {
        Intent intent = new Intent(Learn.this, LearnAlphabet.class);
        startActivity(intent);
    }
}