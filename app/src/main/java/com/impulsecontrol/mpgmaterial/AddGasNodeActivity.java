package com.impulsecontrol.mpgmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by droid on 6/27/16.
 */
public class AddGasNodeActivity extends AppCompatActivity {
    Integer tagIndex = -1;
    Integer originalMileage;
    Button doneButton;
    Button deleteButton;
    EditText mileage;
    EditText gallons;
    EditText gallonsPrice;
    EditText date;
    CheckBox fullTank;
    EditText prius_miles;
    EditText prius_mpg;
    EditText prius_speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gas_node);

        doneButton = (Button) findViewById(R.id.ButtonDone);
        deleteButton = (Button) findViewById(R.id.ButtonDelete);
        mileage = (EditText) findViewById(R.id.EditTextMileage);
        gallons = (EditText) findViewById(R.id.EditTextGallons);
        gallonsPrice = (EditText) findViewById(R.id.EditTextGallonPrice);
        date = (EditText) findViewById(R.id.EditTextDate);
        fullTank = (CheckBox) findViewById(R.id.CheckBoxFullTank);
        prius_miles = (EditText) findViewById(R.id.EditTextPriusMileage);
        prius_mpg = (EditText) findViewById(R.id.EditTextPriusMPG);
        prius_speed = (EditText) findViewById(R.id.EditTextPriusSpeed);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        date.setText(dateFormat.format(todayDate));

        Intent actIntent = getIntent();
        if(actIntent.hasExtra("gasnode"))
        {
            GasNode node = (GasNode) actIntent.getSerializableExtra("gasnode");
            if(node.date != null) date.setText(dateFormat.format(node.date));
            if(node.mileage != null) mileage.setText(Integer.toString(node.mileage));
            if(node.gallons != null) gallons.setText(Double.toString(node.gallons));
            if(node.price_per_gallon > -1.0) gallonsPrice.setText(Double.toString(node.price_per_gallon));
            if(node.full_tank != null) fullTank.setChecked(node.full_tank);
            if(node.prius_milage > -1.0) prius_miles.setText(Double.toString(node.prius_milage));
            if(node.prius_mpg > -1.0) prius_mpg.setText(Double.toString(node.prius_mpg));
            if(node.prius_ave_speed > -1.0) prius_speed.setText(Double.toString(node.prius_ave_speed));
            if(actIntent.hasExtra("viewIndex")) {
                tagIndex = actIntent.getIntExtra("viewIndex", -1);
            }
            originalMileage = node.mileage;
        }


        if(doneButton != null) {
            doneButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GasNode g = buildNode();
                    if(g == null) {
                        return;
                    }
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    bundle.putSerializable("gasnode", g);
                    intent.putExtras(bundle);
                    if(tagIndex > -1) {
                        intent.putExtra("viewIndex", tagIndex);
                        intent.putExtra("update", true);
                        intent.putExtra("original", originalMileage);
                    } else {
                        intent.putExtra("new", true);
                    }
                    setResult(1, intent);
                    finish();
                }
            });
        }

        if(deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(tagIndex == -1) {
                        Toast.makeText(AddGasNodeActivity.this, "Nothing to Delete!", Toast.LENGTH_SHORT).show();
                        return;
                    } else
                    {
                        GasNode g = buildNode();
                        if(g == null) {
                            return;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("gasnode", g);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        intent.putExtra("viewIndex", tagIndex);
                        intent.putExtra("delete", true);
                        setResult(1, intent);
                        finish();
                    }
                }
            });
        }

    };

    private GasNode buildNode(){
        GasNode g = new GasNode();

        if (mileage.getText().toString().equals("")) {
            Toast.makeText(AddGasNodeActivity.this, "Enter Mileage", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (gallons.getText().toString().equals("")) {
            Toast.makeText(AddGasNodeActivity.this, "Enter Gallons", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (gallonsPrice.getText().toString().equals("")) {
            Toast.makeText(AddGasNodeActivity.this, "Enter gallonsPrice", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (date.getText().toString().equals("")) {
            Toast.makeText(AddGasNodeActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (!prius_miles.getText().toString().equals("")) {
            g.prius_milage = Double.parseDouble(prius_miles.getText().toString());
        }
        if (!prius_mpg.getText().toString().equals("")) {
            g.prius_mpg = Double.parseDouble(prius_mpg.getText().toString());
        }
        if (!prius_speed.getText().toString().equals("")) {
            g.prius_ave_speed = Double.parseDouble(prius_speed.getText().toString());
        }
        g.mileage = Integer.parseInt(mileage.getText().toString());
        g.gallons = Double.parseDouble(gallons.getText().toString());
        g.price_per_gallon = Double.parseDouble(gallonsPrice.getText().toString());
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            g.date = formatter.parse(date.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(AddGasNodeActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
            return null;
        }
        g.full_tank = fullTank.isChecked();
        return g;
    }
}
