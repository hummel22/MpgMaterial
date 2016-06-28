package com.impulsecontrol.mpgmaterial;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final GasModel model = new GasModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent getNodeActivity = new Intent(MainActivity.this, AddGasNodeActivity.class);
                    startActivityForResult(getNodeActivity, 1);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resulCode, Intent intent) {
        Log.d("Tag", "Result Returned");
        GasNode node = (GasNode)intent.getSerializableExtra("gasnode");
        if(node != null) {
            model.addGasNode(node);
        }
        LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
        if(rLayout != null) {
            TextView gas = new TextView(this);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String mpgs = "NA";
            if(node.mpg != null) {
                mpgs = Double.toString(node.mpg);
            }
            String gas_string = "Date: " + dateFormat.format(node.date) + " Price Per Gallons: " + Double.toString(node.price_per_gallon) + " Galloons: " + Double.toString(node.gallons)
                    + " Mileage: " + Integer.toString(node.mileage) + " Mpg: " + mpgs;
            gas.setText(gas_string);
            rLayout.addView(gas);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
