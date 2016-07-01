package com.impulsecontrol.mpgmaterial;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_gas_node);

        final Button doneButton = (Button) findViewById(R.id.ButtonDone);

        EditText date = (EditText) findViewById(R.id.EditTextDate);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date todayDate = new Date();
        date.setText(dateFormat.format(todayDate));

        if(doneButton != null) {
            doneButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GasNode g = new GasNode();
                    EditText mileage = (EditText) findViewById(R.id.EditTextMileage);
                    EditText gallons = (EditText) findViewById(R.id.EditTextGallons);
                    EditText gallonsPrice = (EditText) findViewById(R.id.EditTextGallonPrice);
                    EditText date = (EditText) findViewById(R.id.EditTextDate);
                    CheckBox fullTank = (CheckBox) findViewById(R.id.CheckBoxFullTank);
                    if (mileage.getText().toString().equals("")) {
                        Toast.makeText(AddGasNodeActivity.this, "Enter Mileage", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (gallons.getText().toString().equals("")) {
                        Toast.makeText(AddGasNodeActivity.this, "Enter Gallons", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (gallonsPrice.getText().toString().equals("")) {
                        Toast.makeText(AddGasNodeActivity.this, "Enter gallonsPrice", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (date.getText().toString().equals("")) {
                        Toast.makeText(AddGasNodeActivity.this, "Enter date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    g.mileage = Integer.parseInt(mileage.getText().toString());
                    g.gallons = Double.parseDouble(gallons.getText().toString());
                    g.price_per_gallon = Double.parseDouble(gallonsPrice.getText().toString());
                    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        g.date = formatter.parse(date.getText().toString());
                    } catch (ParseException e) {
                        Toast.makeText(AddGasNodeActivity.this, "Bad date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    g.full_tank = fullTank.isChecked();
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent();
                    bundle.putSerializable("gasnode", g);
                    intent.putExtras(bundle);
                    setResult(1, intent);
                    finish();
                }
            });
        }

    };
}
