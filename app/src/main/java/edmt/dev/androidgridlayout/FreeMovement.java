package edmt.dev.androidgridlayout;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FreeMovement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_movement);
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

    public void clickRight(View view){
        Toast.makeText(this,"Right",Toast.LENGTH_SHORT).show();
    }

    public void clickLeft(View view){
        Toast.makeText(this,"Left",Toast.LENGTH_SHORT).show();
    }

    public void clickUp(View view){
        Toast.makeText(this,"Up",Toast.LENGTH_SHORT).show();
    }

    public void clickDown(View view){
        Toast.makeText(this,"Down",Toast.LENGTH_SHORT).show();
    }
}