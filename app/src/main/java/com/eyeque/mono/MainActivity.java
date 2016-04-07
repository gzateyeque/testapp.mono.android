package com.eyeque.mono;
/**
 * Created by georgez on 2/1/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.media.MediaPlayer;
import android.net.Uri;
import java.text.DecimalFormat;
import java.io.IOException;


public class MainActivity extends Activity {
    private PatternView patternView;
    private Pattern pattern;

    private static int minVal;
    private static int maxVal;
    private int prevStopValue = maxVal;
    private static int longPressStep = 1;
    private boolean closerOrFurther = true;
    private boolean toggleEye = true;

    // Long press event
    private boolean inLongPressMode = false;
    private final long REPEAT_DELAY = 50;
    private Handler repeatUpdateHandler = new Handler();

    // Inter Activity Parameters
    private static int subjectId;
    private static int deviceId;
    private static int serverId;

    // Tag for log message
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("MyActivity", "onCreate");
        setContentView(R.layout.activity_main);
        subjectId = getIntent().getIntExtra("subjectId", 0);
        deviceId = getIntent().getIntExtra("deviceId", 0);
        serverId = getIntent().getIntExtra("serverId", 0);

        switch (deviceId) {
            case 2:
                minVal = Constants.MINVAL_DEVICE_3;
                maxVal = Constants.MAXVAL_DEVICE_3;
                longPressStep = 5;
                break;
            default:
                minVal = Constants.MINVAL_DEVICE_1;
                maxVal = Constants.MAXVAL_DEVICE_1;
                longPressStep = 1;
                break;
        }


        class RepetitiveUpdater implements Runnable {

            @Override
            public void run() {
                if (inLongPressMode) {
                    if (closerOrFurther)
                        closerLongPressedAction();
                    else
                        furtherLongPressedAction();
                    repeatUpdateHandler.postDelayed(new RepetitiveUpdater(), REPEAT_DELAY);
                }
            }
        }

        /**
         * Widgets requires modification during interaction
         */
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final TextView atv = (TextView) findViewById(R.id.angleText);
        final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final Pattern pattern = patternView.getPatternInstance();
        AccormAnimation animation = new AccormAnimation(patternView, 120);
        animation.setDuration(100);
        animation.setRepeatCount(Animation.INFINITE);
        patternView.startAnimation(animation);

        final TextView etv = (TextView) findViewById(R.id.eyeText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        alignSeekBar.setMax(maxVal);
        prevStopValue = maxVal;
        alignSeekBar.setProgress(maxVal);

        patternView.setDeviceId((int) deviceId);
        patternView.start();
        dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
        atv.setText("Angle: " + String.valueOf(patternView.getAngle()) + (char) 0x00B0);
        DecimalFormat precision = new DecimalFormat("#.##");
        Double i2= Double.valueOf(precision.format(pattern.getPowerValue()));
        tv.setText("Power: " + String.valueOf(i2));
        if (pattern.getWhichEye())
            etv.setText("Right Eye");
        else
            etv.setText("Left Eye");

        /**
         * Add callback handler to change the pattern in pattern view
         */

        final MediaPlayer mp = new MediaPlayer();
        Button contButton = (Button) findViewById(R.id.contButton);
        contButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Next Button clicked.");

                if (prevStopValue == maxVal)
                    return;

                if(mp.isPlaying())
                {
                    mp.stop();
                }
                try {
                    mp.reset();
                    int patternIndex = patternView.getPatternInstance().getPattenIndex();

                    switch (patternIndex) {
                        case 0:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m0));
                            break;

                        case 1:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m1));
                            break;
                        case 2:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m2));
                            break;
                        case 3:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m3));
                            break;
                        case 4:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m4));
                            break;
                        case 5:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m5));
                            break;
                        case 6:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m6));
                            break;
                        case 7:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m7));
                            break;
                        case 8:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m8));
                            break;
                        default:
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.m0));
                            break;
                    }

                    mp.prepare();
                    mp.start();

                    if (patternIndex == 2 && deviceId == 0) {
                        try {
                            Thread.sleep(3000);                 //1000 milliseconds is one second.
                        } catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        if(mp.isPlaying())
                        {
                            mp.stop();
                        }
                        try {
                            mp.reset();
                            mp.setDataSource(getApplicationContext(),
                                    Uri.parse("android.resource://com.eyeque.mono/" + R.raw.turn90));
                            mp.prepare();
                            mp.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if ((deviceId != 2 && patternIndex == 5) || (deviceId == 2 && patternIndex == 8)) {
                        try {
                            Thread.sleep(2000);                 //1000 milliseconds is one second.
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }
                        if(mp.isPlaying())
                        {
                            mp.stop();
                        }
                        try {
                            mp.reset();
                            if (!pattern.getWhichEye() && pattern.isAllPatternComplete())
                                mp.setDataSource(getApplicationContext(),
                                        Uri.parse("android.resource://com.eyeque.mono/" + R.raw.calc));
                            else
                                if (pattern.getWhichEye())
                                    mp.setDataSource(getApplicationContext(),
                                            Uri.parse("android.resource://com.eyeque.mono/" + R.raw.switchl));
                                else
                                    mp.setDataSource(getApplicationContext(),
                                            Uri.parse("android.resource://com.eyeque.mono/" + R.raw.switchr));
                            mp.prepare();
                            mp.start();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                patternView.nextPattern();
                dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                atv.setText("Angle: " + String.valueOf(pattern.getAngle()) + (char) 0x00B0);
                DecimalFormat precision = new DecimalFormat("#.##");
                Double i2= Double.valueOf(precision.format(pattern.getPowerValue()));
                tv.setText("Power: " + String.valueOf(i2));
                if (pattern.getWhichEye())
                    etv.setText("Right Eye");
                else
                    etv.setText("Left Eye");
                Log.d(TAG, String.valueOf(pattern.getDistance()));
                prevStopValue = maxVal;
                // currStopValue = maxVal;
                alignSeekBar.setProgress(maxVal);
            }
        });

        Button closerButton = (Button) findViewById(R.id.closerButton);
        closerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Closer Button clicked.");
                int lineSpace = pattern.getDistance();
                if (lineSpace >= minVal + 1) {
                    patternView.closerDraw(1);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = lineSpace - minVal;
                    alignSeekBar.setProgress(lineSpace - minVal);
                }
            }
        });

        closerButton.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                inLongPressMode = true;
                closerOrFurther = true;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return true;
            }
        });

        closerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && inLongPressMode) {
                    inLongPressMode = false;
                }
                return false;
            }
        });


        Button furtherButton = (Button) findViewById(R.id.furtherButton);
        furtherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("MyActivity", "Further Button clicked.");
                int lineSpace = pattern.getDistance();
                if (lineSpace <= minVal + maxVal - 1) {
                    patternView.furtherDraw(1);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = lineSpace - minVal;
                    alignSeekBar.setProgress(lineSpace - minVal);
                }

            }
        });

        furtherButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                inLongPressMode = true;
                closerOrFurther = false;
                repeatUpdateHandler.post(new RepetitiveUpdater());
                return true;
            }
        });

        furtherButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && inLongPressMode) {
                    inLongPressMode = false;
                }
                return false;
            }
        });

        // alignSeekBar.setOnSeekBarChangeListener(this);
        alignSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // TODO Auto-generated method stub;
                if (progress < prevStopValue)
                    patternView.closerDraw(prevStopValue - progress);
                else
                    patternView.furtherDraw(progress - prevStopValue);
                dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                DecimalFormat precision = new DecimalFormat("#.##");
                Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                tv.setText("Power: " + String.valueOf(i2));
                prevStopValue = progress;
            }
        });

        Button resultButton = (Button) findViewById(R.id.resultButton);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (pattern.isAllPatternsComplete()) {
                double[] results = pattern.calculateResults();
                Intent resultIntent = new Intent(getBaseContext(), ResultActivity.class);
                resultIntent.putExtra("subjectId", subjectId);
                resultIntent.putExtra("deviceId", deviceId);
                resultIntent.putExtra("serverId", serverId);
                resultIntent.putExtra("ODS", results[0]);
                resultIntent.putExtra("ODC", results[1]);
                resultIntent.putExtra("ODA", (results[2]));
                resultIntent.putExtra("ODE", (results[3]));
                resultIntent.putExtra("OSS", results[4]);
                resultIntent.putExtra("OSC", results[5]);
                resultIntent.putExtra("OSA", (results[6]));
                resultIntent.putExtra("OSE", (results[7]));

                double[] patternCalcAngleList = pattern.getPatternCalcAngleList();
                double[] leftPowerList = pattern.getLeftPowerValueList();
                double[] rightPowerList = pattern.getRightPowerValueList();
                int[] leftDistList = pattern.getLeftDistValueList();
                int[] rightDistList = pattern.getRightDistValueList();

                resultIntent.putExtra("Angle-1", patternCalcAngleList[0]);
                resultIntent.putExtra("Angle-2", patternCalcAngleList[1]);
                resultIntent.putExtra("Angle-3", patternCalcAngleList[2]);
                resultIntent.putExtra("Angle-4", patternCalcAngleList[3]);
                resultIntent.putExtra("Angle-5", patternCalcAngleList[4]);
                resultIntent.putExtra("Angle-6", patternCalcAngleList[5]);

                if (deviceId == 2) {
                    resultIntent.putExtra("Angle-7", patternCalcAngleList[6]);
                    resultIntent.putExtra("Angle-8", patternCalcAngleList[7]);
                    resultIntent.putExtra("Angle-9", patternCalcAngleList[8]);
                }

                resultIntent.putExtra("L-Power-1", leftPowerList[0]);
                resultIntent.putExtra("L-Power-2", leftPowerList[1]);
                resultIntent.putExtra("L-Power-3", leftPowerList[2]);
                resultIntent.putExtra("L-Power-4", leftPowerList[3]);
                resultIntent.putExtra("L-Power-5", leftPowerList[4]);
                resultIntent.putExtra("L-Power-6", leftPowerList[5]);

                if (deviceId == 2) {
                    resultIntent.putExtra("L-Power-7", leftPowerList[6]);
                    resultIntent.putExtra("L-Power-8", leftPowerList[7]);
                    resultIntent.putExtra("L-Power-9", leftPowerList[8]);
                }

                resultIntent.putExtra("R-Power-1", rightPowerList[0]);
                resultIntent.putExtra("R-Power-2", rightPowerList[1]);
                resultIntent.putExtra("R-Power-3", rightPowerList[2]);
                resultIntent.putExtra("R-Power-4", rightPowerList[3]);
                resultIntent.putExtra("R-Power-5", rightPowerList[4]);
                resultIntent.putExtra("R-Power-6", rightPowerList[5]);

                if (deviceId == 2) {
                    resultIntent.putExtra("R-Power-7", rightPowerList[6]);
                    resultIntent.putExtra("R-Power-8", rightPowerList[7]);
                    resultIntent.putExtra("R-Power-9", rightPowerList[8]);
                }

                resultIntent.putExtra("L-Dist-1", leftDistList[0]);
                resultIntent.putExtra("L-Dist-2", leftDistList[1]);
                resultIntent.putExtra("L-Dist-3", leftDistList[2]);
                resultIntent.putExtra("L-Dist-4", leftDistList[3]);
                resultIntent.putExtra("L-Dist-5", leftDistList[4]);
                resultIntent.putExtra("L-Dist-6", leftDistList[5]);

                if (deviceId == 2) {
                    resultIntent.putExtra("L-Dist-7", leftDistList[6]);
                    resultIntent.putExtra("L-Dist-8", leftDistList[7]);
                    resultIntent.putExtra("L-Dist-9", leftDistList[8]);
                }

                resultIntent.putExtra("R-Dist-1", rightDistList[0]);
                resultIntent.putExtra("R-Dist-2", rightDistList[1]);
                resultIntent.putExtra("R-Dist-3", rightDistList[2]);
                resultIntent.putExtra("R-Dist-4", rightDistList[3]);
                resultIntent.putExtra("R-Dist-5", rightDistList[4]);
                resultIntent.putExtra("R-Dist-6", rightDistList[5]);

                if (deviceId == 2) {
                    resultIntent.putExtra("R-Dist-7", rightDistList[6]);
                    resultIntent.putExtra("R-Dist-8", rightDistList[7]);
                    resultIntent.putExtra("R-Dist-9", rightDistList[8]);
                }

                Log.i("MA-OD Spherical:  ", Double.toString(results[0]));
                Log.i("MA-OD Cylindrical:  ", Double.toString(results[1]));
                Log.i("MA-OD Axis:  ", Double.toString(results[2]));
                Log.i("MA-OD SE:  ", Double.toString(results[3]));
                Log.i("MA-OS Spherical:  ", Double.toString(results[4]));
                Log.i("MA-OS Cylindrical:  ", Double.toString(results[5]));
                Log.i("MA-OS Axis:  ", Double.toString(results[6]));
                Log.i("MA-OS SE:  ", Double.toString(results[7]));

                startActivity(resultIntent);
                finish();
            } else
                Toast.makeText(MainActivity.this,
                        "Need to complete all patterns", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void closerLongPressedAction() {
        final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final Pattern pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        if (pattern.getDistance() >=minVal+2) {
            patternView.closerDraw(longPressStep);
            dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
            DecimalFormat precision = new DecimalFormat("#.##");
            Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
            tv.setText("Power: " + String.valueOf(i2));
            prevStopValue = pattern.getDistance() - minVal;
            alignSeekBar.setProgress(pattern.getDistance() - minVal);
        }
    }

    public void furtherLongPressedAction() {
        final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final Pattern pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);
        if (pattern.getDistance() <=minVal+maxVal-2) {
            patternView.furtherDraw(longPressStep);
            dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
            DecimalFormat precision = new DecimalFormat("#.##");
            Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
            tv.setText("Power: " + String.valueOf(i2));
            prevStopValue = pattern.getDistance() - minVal;
            alignSeekBar.setProgress(pattern.getDistance() - minVal);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        final PatternView patternView = (PatternView) findViewById(R.id.drawView);
        final TextView tv = (TextView) findViewById(R.id.powerText);
        final Pattern pattern = patternView.getPatternInstance();
        final TextView dtv = (TextView) findViewById(R.id.distText);
        final SeekBar alignSeekBar = (SeekBar) findViewById(R.id.alignSeekBar);

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.d(TAG, "Volume Down Button is pressed");
                if (pattern.getDistance() >=minVal+2) {
                    patternView.closerDraw(longPressStep);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = pattern.getDistance() - minVal;
                    alignSeekBar.setProgress(pattern.getDistance() - minVal);
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.d(TAG, "Volume Up Button is pressed");
                if (pattern.getDistance() <=minVal+maxVal-2) {
                    patternView.furtherDraw(longPressStep);
                    dtv.setText("Distance: " + String.valueOf(pattern.getDistance()));
                    DecimalFormat precision = new DecimalFormat("#.##");
                    Double i2 = Double.valueOf(precision.format(pattern.getPowerValue()));
                    tv.setText("Power: " + String.valueOf(i2));
                    prevStopValue = pattern.getDistance() - minVal;
                    alignSeekBar.setProgress(pattern.getDistance() - minVal);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {}


    /*****************************************
     * DEFAULT Stub function with Menubar
     * ==================================
     *
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    ********/
}
