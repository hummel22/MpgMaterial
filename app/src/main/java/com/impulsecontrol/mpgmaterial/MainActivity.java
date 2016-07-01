package com.impulsecontrol.mpgmaterial;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {

    final private GasModel model = new GasModel();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();


        //TODO Replace with database get
        //<Replace>

        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        GasNode node0 = new GasNode();

        try {
            node0.date = formatter.parse("01/23/1990");
        } catch (ParseException e) {
            Toast.makeText(MainActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
            return;
        }
        node0.mileage = 100;
        node0.gallons = 10.0;
        node0.price_per_gallon = 2.03;
        node0.mpg = null;
        node0.full_tank = true;



        GasNode node1 = new GasNode();
        node1.date = node0.date;
        node1.mileage = 250;
        node1.gallons = 10.0;
        node1.price_per_gallon = 2.03;
        node1.mpg = null;
        node1.full_tank = true;
        model.addGasNode(node0);
        model.addGasNode(node1);
        //<Replace>
        //Database.loadNodes(model);
        addNodes(model.getGasNodes());

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
        GasNode node = (GasNode)intent.getSerializableExtra("gasnode");
        if(node != null) {
            model.addGasNode(node);
            addNodeView(node);
        }
    }


    public void addNodes(List<GasNode> nodes) {
        LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
        for (GasNode g : nodes) {
            if (rLayout != null) {
                View v = buildCardView(g);
                rLayout.addView(v,0);
            }
        }
    }

    public void addNodeView(GasNode node) {
        LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
        if (rLayout != null) {
            View v = buildCardView(node);
            rLayout.addView(v,model.getModelSize() - model.getIndex(node) - 1);
        }
    }


    public View buildCardView(GasNode node) {
//        CardView cardView = new CardView(mContext);
//        LayoutInflater li =(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
//        cardView.addView(li.inflate(R.layout.cardview_layout, null));
        TextView gas = new TextView(this);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String mpgs = "NA";
        if(node != null && node.mpg != null) {
            mpgs = Double.toString(node.mpg);
        }
        String gas_string = "Date: " + dateFormat.format(node.date) + " Price Per Gallons: " + Double.toString(node.price_per_gallon) + " Galloons: " + Double.toString(node.gallons)
                + " Mileage: " + Integer.toString(node.mileage) + " Mpg: " + mpgs;
        gas.setText(gas_string);
        return gas;
    }

    public void deleteNode(Integer index) {

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
