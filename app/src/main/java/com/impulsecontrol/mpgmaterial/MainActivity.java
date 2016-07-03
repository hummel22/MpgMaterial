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
    private GasDAO gasDAO;

    private Context mContext;
    Integer tagIndex = 0;
    TextView HUDTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        gasDAO = new GasDAO(mContext);
        HUDTextView = (TextView) findViewById(R.id.HUDTextView);


        for(GasNode g : gasDAO.getNodes()) {
            model.addGasNode(g);
        }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(intent != null && intent.hasExtra("gasnode")) {
            GasNode node = (GasNode) intent.getSerializableExtra("gasnode");
            if(intent.hasExtra("new")) {
                if (node != null) {
                    if(model.getIndexByMileage(node.mileage) > -1) {
                        Toast.makeText(MainActivity.this, "Duplicate Mileage!: " + Integer.toString(node.mileage), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    model.addGasNode(node);
                    addNodeView(node);
                    gasDAO.addNode(node);
                }
            }
            if(intent.hasExtra("viewIndex") && intent.hasExtra("delete")) {
                if(node != null) {
                    model.deleteGasNode(node);
                    gasDAO.deleteGasNode(node);
                    Integer indexTag = intent.getIntExtra("viewIndex", -1);
                    Toast.makeText(MainActivity.this, "Deleting: " + Integer.toString(indexTag), Toast.LENGTH_SHORT).show();
                    deleteNode(indexTag);
                }
            }
            if(intent.hasExtra("viewIndex") && intent.hasExtra("update")) {
                if(node != null) {
                    Integer indexTag = intent.getIntExtra("viewIndex", -1);
                    Integer oldMileage = intent.getIntExtra("original", -1);
                    GasNode oldNode = model.getGasNodeByMileage(oldMileage);
                    model.deleteGasNode(oldNode);
                    gasDAO.deleteGasNode(oldNode);
                    deleteNode(indexTag);
                    model.addGasNode(node);
                    addNodeView(node);
                    gasDAO.addNode(node);
                    Toast.makeText(MainActivity.this, "Updating: " + Integer.toString(indexTag), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public void addNodes(List<GasNode> nodes) {
        LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
        for (GasNode g : nodes) {
            if (rLayout != null) {
                View v = buildCardView(g);
                rLayout.addView(v,0);
                if(model.getMostRecentMPG() != null) {
                    HUDTextView.setText("HUD MPG: " + Double.toString(model.getMostRecentMPG()));
                }
            }
        }
    }

    public void addNodeView(GasNode node) {
        LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
        if (rLayout != null) {
            View v = buildCardView(node);
            rLayout.addView(v,model.getModelSize() - model.getIndex(node) - 1);
            if(model.getMostRecentMPG() != null) {
                HUDTextView.setText("HUD MPG: " + Double.toString(model.getMostRecentMPG()));
            }        }
    }


    public View buildCardView(GasNode node) {
//        CardView cardView = new CardView(mContext);
//        LayoutInflater li =(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
//        cardView.addView(li.inflate(R.layout.cardview_layout, null));
        GasView gas = new GasView(this);
        gas.setTag(tagIndex++);
        gas.setNode(node);

        gas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                GasView gView = (GasView) v;
                Intent getNodeActivity = new Intent(MainActivity.this, AddGasNodeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("gasnode", gView.node);
                getNodeActivity.putExtra("viewIndex", (Integer)v.getTag());
                getNodeActivity.putExtras(bundle);
                startActivityForResult(getNodeActivity, 1);
            };
        });
        return gas;
    }

    public void deleteNode(Integer tag) {
        if(tag > -1) {
            LinearLayout rLayout = (LinearLayout) findViewById(R.id.layout_main);
            View vDelete =  rLayout.findViewWithTag(tag);
            rLayout.removeView(vDelete);
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
