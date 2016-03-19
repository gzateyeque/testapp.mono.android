package com.eyeque.mono;
/**
 * Created by georgez on 2/1/16.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.util.Log;

public class PatternView extends View {

    private static int deviceId;
    private static Pattern pattern = new Pattern(0, 0);
    // private static boolean rotateCV = false;

    // Tag for log message
    private static final String TAG = PatternView.class.getSimpleName();

    public PatternView(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);
        setMinimumHeight(100);
        setMinimumWidth(100);
        setBackgroundColor(Color.BLACK);
        invalidate();

        /* Instantiate a new Pattern object */
        // Pattern pattern = new Pattern(0, 0);
    }

    public void setDeviceId(int id) {
        deviceId = id;
        pattern.setDeviceId(id);
    }

    public void start() {
        pattern.start();
        // pattern.setDrawAxis();
        invalidate();
    }

    public void nextPattern() {
        pattern.nextPattern();
        // pattern.setDrawAxis();
        invalidate();
    }

    public void closerDraw(int step) {
        pattern.moveCloser(step);
        invalidate();
    }

    public void furtherDraw(int step) {
        pattern.moveFurther(step);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas cv) {

        // cv.drawColor(Color.WHITE);
        Paint p = new Paint();
        // Log.i("OnDraw", "drawType = " + String.valueOf(getDrawType()));

        // Draw Square
        if (deviceId == 2) {
            p.setColor(Color.WHITE);
            p.setStrokeWidth(10);
            int sqrHalfSize = 455;
            cv.drawRect(720 - sqrHalfSize, 520 - sqrHalfSize, 720 + sqrHalfSize, 520 + sqrHalfSize, p);
            p.setColor(Color.BLACK);
            p.setStrokeWidth(10);
            cv.drawRect(720 - sqrHalfSize + 10, 520 - sqrHalfSize + 10, 720 + sqrHalfSize - 10, 520 + sqrHalfSize - 10, p);
        }

        if (pattern.getPattenIndex() > 0) {
            int angle = pattern.getRotateAngle();
            Log.i("PV-INFO", String.valueOf(angle));
            cv.save();
            cv.rotate(angle, 720, 520);
        }
        else {
            cv.save();
            cv.rotate(0);
        }

        p.setColor(Color.RED);
        if (deviceId == 2) {
            p.setStrokeWidth(12);
            cv.drawLine(pattern.getRedStartX()+12, pattern.getRedStartY()+148,
                    pattern.getRedEndX()+12, pattern.getRedEndY(), p);
        }
        else
            p.setStrokeWidth(1);
        cv.drawLine(pattern.getRedStartX(), pattern.getRedStartY(),
                pattern.getRedEndX(), pattern.getRedEndY(), p);

        // Draw GREEN line
        p.setColor(Color.GREEN);
        if (deviceId == 2) {
            p.setStrokeWidth(12);
            cv.drawLine(pattern.getGreenStartX()-12, pattern.getGreenStartY(),
                    pattern.getGreenEndX()-12, pattern.getGreenEndY()-148, p);
        }
        else
            p.setStrokeWidth(1);
        cv.drawLine(pattern.getGreenStartX(), pattern.getGreenStartY(),
                pattern.getGreenEndX(), pattern.getGreenEndY(), p);


        cv.restore();
    }

    public int getAngle() {
        return pattern.getAngle();
    }
    public int getDistance() {
        return pattern.getDistance();
    }
    public Pattern getPatternInstance() {
        return pattern;
    }
}
