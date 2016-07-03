package com.impulsecontrol.mpgmaterial;

import android.content.Context;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by kerrk on 7/2/16.
 */
public class GasView extends CardView {

    public GasNode node;
    Context mContext;

    public GasView(Context context) {
        super(context);
        mContext = context;
    }

    public GasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public GasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setNode(GasNode g) {
        node = g;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
                );
        params.setMargins(10,20,10,20);
        this.setLayoutParams(params);
        this.setRadius(0);
        this.setContentPadding(15, 15, 15, 15);
        this.setMaxCardElevation(15);
        this.setCardElevation(9);

        this.setPadding(15, 15, 15, 15);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String mpgs = "NA";
        if(node != null && node.mpg != null) {
            mpgs = GNC.DoubleToString(node.mpg, 2);
        }
        String gas_string = "Date: " + dateFormat.format(node.date) +
                "\nPrice Per Gallons: " + GNC.DoubleToString(node.price_per_gallon, 3) +
                "\nGalloons: " + GNC.DoubleToString(node.gallons, 3) +
                "\nMileage: " + Integer.toString(node.mileage) +
                "\nMpg: " + mpgs +
                "\nPrius Miles: " + ((node.prius_milage > -1) ? GNC.DoubleToString(node.prius_milage, 1): "NA") +
                " Prius MPG: " + ((node.prius_mpg > -1) ? GNC.DoubleToString(node.prius_mpg, 1): "NA") +
                " Prius Speed: " + ((node.prius_ave_speed > -1) ? GNC.DoubleToString(node.prius_ave_speed, 1): "NA");
        TextView text = new TextView(mContext);
        text.setText(gas_string);
        this.addView(text);
    }
}
