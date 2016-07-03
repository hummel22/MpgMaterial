package com.impulsecontrol.mpgmaterial;

import java.text.DecimalFormat;

/**
 * Created by kerrk on 7/3/16.
 */
public class GNC {
    static public String DoubleToString(Double d, Integer dec){
        if(d == (long)(double)d) {
            return Long.toString((long)(double)d);
        }
        else {
            DecimalFormat df = new DecimalFormat(".##");
            return df.format(d);
        }
    }
}
