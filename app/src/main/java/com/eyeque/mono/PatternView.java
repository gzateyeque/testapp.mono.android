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
import android.graphics.RectF;
import java.util.Calendar;

public class PatternView extends View {

    private static int deviceId;
    private static Pattern pattern = new Pattern(0, 0);
    // private static boolean rotateCV = false;

    private static final int START_ANGLE_POINT = 90;
    private final Paint paint;
    private float aniRadius;
    private int aniColor;

    // Tag for log message
    private static final String TAG = PatternView.class.getSimpleName();

    public PatternView(Context cxt, AttributeSet attrs) {
        super(cxt, attrs);
        setMinimumHeight(100);
        setMinimumWidth(100);
        setBackgroundColor(Color.BLACK);
        invalidate();

        // Try animation
        final int strokeWidth = 10;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        //Circle color
        paint.setColor(Color.RED);

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

        // Draw Accormodation Pattern
        p.setColor(Color.BLUE);  // p.setColor(getAniColor());
        p.setStyle(Paint.Style.STROKE);
        if (deviceId != 2) {
            p.setStrokeWidth(5);
            if (getAniRadius() > 20)
                cv.drawCircle(720, 520, getAniRadius()-20, p);
            cv.drawCircle(720, 520, getAniRadius(), p);
        }
        else {
            p.setStrokeWidth(24);
            cv.drawCircle(855, 520, getAniRadius(), p);
        }

        p.setColor(Color.RED);
        if (deviceId == 2) {
            p.setStrokeWidth(12);
            cv.drawLine(pattern.getRedStartX()+12, pattern.getRedStartY()+148,
                    pattern.getRedEndX() + 12, pattern.getRedEndY(), p);
        } else
            p.setStrokeWidth(1);
        cv.drawLine(pattern.getRedStartX(), pattern.getRedStartY(),
                pattern.getRedEndX(), pattern.getRedEndY(), p);

        // Draw GREEN line
        p.setColor(Color.GREEN);
        if (deviceId == 2) {
            p.setStrokeWidth(12);
            cv.drawLine(pattern.getGreenStartX() - 12, pattern.getGreenStartY(),
                    pattern.getGreenEndX() - 12, pattern.getGreenEndY() - 148, p);
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

    public float getAniRadius() {
        return aniRadius;
    }
    public void setAniRadius(float radius) {
        this.aniRadius = radius;
    }

    public int getAniColor() {
        return aniColor;
    }
    public void setAniColor(int value) {
        this.aniColor = value;
    }


    public int getDistance() {
        return pattern.getDistance();
    }
    public int getDeviceId() { return deviceId; }
    public Pattern getPatternInstance() {
        return pattern;
    }
}
