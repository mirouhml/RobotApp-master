package edmt.dev.androidgridlayout;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class SearchForObject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_for_object);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.hidden_objects);
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
        Intent intent = new Intent(SearchForObject.this, SearchForFruits.class);
        startActivity(intent);
    }

    public void clickOnVegetables(View view){
        Intent intent = new Intent(SearchForObject.this, SearchForVegetables.class);
        startActivity(intent);
    }

    public void clickOnAnimals(View view){
        Intent intent = new Intent(SearchForObject.this, SearchForAnimals.class);
        startActivity(intent);
    }
}
