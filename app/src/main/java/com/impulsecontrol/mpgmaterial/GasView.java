package com.impulsecontrol.mpgmaterial;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kerrk on 7/2/16.
 */
public class GasView extends TextView {

    public GasNode node;

    public GasView(Context context) {
        super(context);
    }

    public GasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
