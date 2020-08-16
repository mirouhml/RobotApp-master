package edmt.dev.androidgridlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {
    GridLayout mainGrid;
    BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(this, "This device doesn't have Bluetooth.", Toast.LENGTH_LONG).show(); // Replace context with your context instance.
            finish();
        } else if (!mBluetoothAdapter.isEnabled()) {
            // Bluetooth is not enabled :)
            mBluetoothAdapter.enable();
        }
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
}