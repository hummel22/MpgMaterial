package com.impulsecontrol.mpgmaterial;

import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by droid on 6/26/16.
 */
public class GasNode implements Serializable {
    public Date date ;
    public Double price_per_gallon = -1.0;
    public Double gallons = -1.0;
    public Integer mileage = -1;
    public Boolean full_tank = false;
    public Double prius_mileage = -1.0;
    public Double prius_mpg = -1.0;
    public Double prius_ave_speed = -1.0;
    public Integer _id = -1;

    public Double mpg;  //Calcualted on Load
}


