package com.impulsecontrol.mpgmaterial;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by droid on 6/26/16.
 */
public class GasNode implements Serializable {
    public Date date;
    public Double price_per_gallon;
    public Double gallons;
    public Integer mileage;
    public Boolean full_tank;
    public Double prius_milage;
    public Double prius_mpg;
    public Double prius_ave_speed;

    public Double mpg;
}


