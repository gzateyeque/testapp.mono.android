package com.eyeque.mono;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by georgez on 3/21/16.
 */
public class AccormAnimation extends Animation {
    private PatternView circle;

    private float oldAngle;
    private int deviceId = 0;
    // private float ii = 2.25f;
    private float ii = 100f;

    public AccormAnimation(PatternView circle, int newAngle) {
        this.oldAngle = circle.getAniRadius();
        deviceId = circle.getDeviceId();

        if (deviceId == 2)
            ii = 100f;
        else
            ii = 50f;
        this.circle = circle;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation transformation) {
        float angle = oldAngle + ((ii - oldAngle) * interpolatedTime);

        /*
        ii += 1f;
        if (ii >= 100)
            ii = 1f;
        */
        int deviceId = circle.getDeviceId();

        if (deviceId == 2) {
            ii -= 0.5f;
            if (ii <= 0)
                ii = 100f;
        } else {
            ii -= 0.25f;
            if (ii <= 0)
                ii = 50f;
        }

        circle.setAniRadius(ii);

        long seconds = System.currentTimeMillis() ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZZZZ");
        try {
            Date gmt = formatter.parse("2016-01-19T18:23:20+0000");
            long millisecondsSinceEpoch0 = gmt.getTime();
            long elapsedTime = seconds - millisecondsSinceEpoch0;
            float radiansToDraw =  (float) ((2 * Constants.PI)* (float) 0.4) * elapsedTime;
            double rColor= 0.5*(0.4+ii/50)*(1.0-1.0* (Math.cos((double) (2 * Constants.PI * (radiansToDraw + ii * ((1 - ii / 150) * 0.01 + 0.04))))));

            // Log.i("***BLUE COLOR***", Float.toString(ii) +" " + String.format("%.2f", rColor));
            circle.setAniColor((int) rColor);
            circle.requestLayout();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

}
