package com.eyeque.mono;
/**
 * Created by georgez on 2/9/16.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Map;
import java.util.HashMap;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.RetryPolicy;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonObjectRequest;
import android.widget.Toast;
import java.util.UUID;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONArray;

public class ResultActivity extends Activity {

    private static int subjectId;
    private static int deviceId;
    private static int serverId;
    private static double odSph;
    private static double odCyl;
    private static double odAxis;
    private static double odSe;
    private static double osSph;
    private static double osCyl;
    private static double osAxis;
    private static double osSe;
    private static double[] angleList = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static double[] leftPowerList = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static double[] rightPowerList = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private static int[] rightDistList = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static int[] leftDistList = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static String mStr;
    private static boolean netLinkStatus = true;
    boolean isUploadComplete = false;
    private static int numMeasurement;

    // Tag for log message
    private static final String TAG = ResultActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        subjectId = getIntent().getIntExtra("subjectId", 0);
        deviceId = getIntent().getIntExtra("deviceId", 0);
        serverId = getIntent().getIntExtra("serverId", 0);

        if (deviceId == 2)
            numMeasurement = 9;
        else
            numMeasurement = 6;

        odSph = getIntent().getDoubleExtra("ODS", 0.00);
        odCyl = getIntent().getDoubleExtra("ODC", 0.00);
        odAxis = getIntent().getDoubleExtra("ODA", 0.00);
        odSe = getIntent().getDoubleExtra("ODE", 0.00);
        osSph = getIntent().getDoubleExtra("OSS", 0.00);
        osCyl = getIntent().getDoubleExtra("OSC", 0.00);
        osAxis = getIntent().getDoubleExtra("OSA", 0.00);
        osSe = getIntent().getDoubleExtra("OSE", 0.00);

        Button uploadButton = (Button) findViewById(R.id.uploadButton);
        final TextView odSphTextView = (TextView) findViewById(R.id.odSphTextView);
        final TextView odCylTextView = (TextView) findViewById(R.id.odCylTextView);
        final TextView odAxisTextView = (TextView) findViewById(R.id.odAxisTextView);
        final TextView osSphTextView = (TextView) findViewById(R.id.osSphTextView);
        final TextView osCylTextView = (TextView) findViewById(R.id.osCylTextView);
        final TextView osAxisTextView = (TextView) findViewById(R.id.osAxisTextView);

        odSphTextView.setText(String.format("%.2f", odSph));
        odCylTextView.setText(String.format("%.2f", odCyl));
        odAxisTextView.setText(Integer.toString((int) odAxis));
        osSphTextView.setText(String.format("%.2f", osSph));
        osCylTextView.setText(String.format("%.2f", osCyl));
        osAxisTextView.setText(Integer.toString((int) osAxis));

        angleList[0] = getIntent().getDoubleExtra("Angle-1", 0.00);
        angleList[1] = getIntent().getDoubleExtra("Angle-2", 0.00);
        angleList[2] = getIntent().getDoubleExtra("Angle-3", 0.00);
        angleList[3] = getIntent().getDoubleExtra("Angle-4", 0.00);
        angleList[4] = getIntent().getDoubleExtra("Angle-5", 0.00);
        angleList[5] = getIntent().getDoubleExtra("Angle-6", 0.00);

        if (deviceId == 2) {
            angleList[6] = getIntent().getDoubleExtra("Angle-7", 0.00);
            angleList[7] = getIntent().getDoubleExtra("Angle-8", 0.00);
            angleList[8] = getIntent().getDoubleExtra("Angle-9", 0.00);
        }

        leftPowerList[0] = getIntent().getDoubleExtra("L-Power-1", 0.00);
        leftPowerList[1] = getIntent().getDoubleExtra("L-Power-2", 0.00);
        leftPowerList[2] = getIntent().getDoubleExtra("L-Power-3", 0.00);
        leftPowerList[3] = getIntent().getDoubleExtra("L-Power-4", 0.00);
        leftPowerList[4] = getIntent().getDoubleExtra("L-Power-5", 0.00);
        leftPowerList[5] = getIntent().getDoubleExtra("L-Power-6", 0.00);

        if (deviceId == 2) {
            leftPowerList[6] = getIntent().getDoubleExtra("L-Power-7", 0.00);
            leftPowerList[7] = getIntent().getDoubleExtra("L-Power-8", 0.00);
            leftPowerList[8] = getIntent().getDoubleExtra("L-Power-9", 0.00);
        }

        rightPowerList[0] = getIntent().getDoubleExtra("R-Power-1", 0.00);
        rightPowerList[1] = getIntent().getDoubleExtra("R-Power-2", 0.00);
        rightPowerList[2] = getIntent().getDoubleExtra("R-Power-3", 0.00);
        rightPowerList[3] = getIntent().getDoubleExtra("R-Power-4", 0.00);
        rightPowerList[4] = getIntent().getDoubleExtra("R-Power-5", 0.00);
        rightPowerList[5] = getIntent().getDoubleExtra("R-Power-6", 0.00);

        if (deviceId == 2) {
            rightPowerList[6] = getIntent().getDoubleExtra("R-Power-7", 0.00);
            rightPowerList[7] = getIntent().getDoubleExtra("R-Power-8", 0.00);
            rightPowerList[8] = getIntent().getDoubleExtra("R-Power-9", 0.00);
        }

        leftDistList[0] = getIntent().getIntExtra("L-Dist-1", 0);
        leftDistList[1] = getIntent().getIntExtra("L-Dist-2", 1);
        leftDistList[2] = getIntent().getIntExtra("L-Dist-3", 2);
        leftDistList[3] = getIntent().getIntExtra("L-Dist-4", 3);
        leftDistList[4] = getIntent().getIntExtra("L-Dist-5", 4);
        leftDistList[5] = getIntent().getIntExtra("L-Dist-6", 5);

        if (deviceId == 2) {
            leftDistList[6] = getIntent().getIntExtra("L-Dist-7", 6);
            leftDistList[7] = getIntent().getIntExtra("L-Dist-8", 7);
            leftDistList[8] = getIntent().getIntExtra("L-Dist-9", 8);
        }

        rightDistList[0] = getIntent().getIntExtra("R-Dist-1", 0);
        rightDistList[1] = getIntent().getIntExtra("R-Dist-2", 1);
        rightDistList[2] = getIntent().getIntExtra("R-Dist-3", 2);
        rightDistList[3] = getIntent().getIntExtra("R-Dist-4", 3);
        rightDistList[4] = getIntent().getIntExtra("R-Dist-5", 4);
        rightDistList[5] = getIntent().getIntExtra("R-Dist-6", 5);

        if (deviceId == 2) {
            rightDistList[6] = getIntent().getIntExtra("R-Dist-7", 6);
            rightDistList[7] = getIntent().getIntExtra("R-Dist-8", 7);
            rightDistList[8] = getIntent().getIntExtra("R-Dist-9", 8);
        }

        final TextView l1TextView = (TextView) findViewById(R.id.l1TextView);
        final TextView l2TextView = (TextView) findViewById(R.id.l2TextView);
        final TextView l3TextView = (TextView) findViewById(R.id.l3TextView);
        final TextView l4TextView = (TextView) findViewById(R.id.l4TextView);
        final TextView l5TextView = (TextView) findViewById(R.id.l5TextView);
        final TextView l6TextView = (TextView) findViewById(R.id.l6TextView);
        final TextView l7TextView = (TextView) findViewById(R.id.l7TextView);
        final TextView l8TextView = (TextView) findViewById(R.id.l8TextView);
        final TextView l9TextView = (TextView) findViewById(R.id.l9TextView);

        mStr = String.format("%.2f", leftPowerList[0]) + " " + String.format("%.1f", angleList[0]) + (char) 0x00B0;
        l1TextView.setText(mStr);
        mStr = String.format("%.2f", leftPowerList[1]) + " " + String.format("%.1f", angleList[1]) + (char) 0x00B0;
        l2TextView.setText(mStr);
        mStr = String.format("%.2f", leftPowerList[2]) + " " + String.format("%.1f", angleList[2]) + (char) 0x00B0;
        l3TextView.setText(mStr);
        mStr = String.format("%.2f", leftPowerList[3]) + " " + String.format("%.1f", angleList[3]) + (char) 0x00B0;
        l4TextView.setText(mStr);
        mStr = String.format("%.2f", leftPowerList[4]) + " " + String.format("%.1f", angleList[4]) + (char) 0x00B0;
        l5TextView.setText(mStr);
        mStr = String.format("%.2f", leftPowerList[5]) + " " + String.format("%.1f", angleList[5]) + (char) 0x00B0;
        l6TextView.setText(mStr);

        if (deviceId == 2) {
            mStr = String.format("%.2f", leftPowerList[6]) + " " + String.format("%.1f", angleList[6]) + (char) 0x00B0;
            l7TextView.setText(mStr);
            mStr = String.format("%.2f", leftPowerList[7]) + " " + String.format("%.1f", angleList[7]) + (char) 0x00B0;
            l8TextView.setText(mStr);
            mStr = String.format("%.2f", leftPowerList[8]) + " " + String.format("%.1f", angleList[8]) + (char) 0x00B0;
            l9TextView.setText(mStr);
        }

        final TextView r1TextView = (TextView) findViewById(R.id.r1TextView);
        final TextView r2TextView = (TextView) findViewById(R.id.r2TextView);
        final TextView r3TextView = (TextView) findViewById(R.id.r3TextView);
        final TextView r4TextView = (TextView) findViewById(R.id.r4TextView);
        final TextView r5TextView = (TextView) findViewById(R.id.r5TextView);
        final TextView r6TextView = (TextView) findViewById(R.id.r6TextView);
        final TextView r7TextView = (TextView) findViewById(R.id.r7TextView);
        final TextView r8TextView = (TextView) findViewById(R.id.r8TextView);
        final TextView r9TextView = (TextView) findViewById(R.id.r9TextView);

        mStr = String.format("%.2f", rightPowerList[0]) + " " + String.format("%.1f", angleList[0]) + (char) 0x00B0;
        r1TextView.setText(mStr);
        mStr = String.format("%.2f", rightPowerList[1]) + " " + String.format("%.1f", angleList[1]) + (char) 0x00B0;
        r2TextView.setText(mStr);
        mStr = String.format("%.2f", rightPowerList[2]) + " " + String.format("%.1f", angleList[2]) + (char) 0x00B0;
        r3TextView.setText(mStr);
        mStr = String.format("%.2f", rightPowerList[3]) + " " + String.format("%.1f", angleList[3]) + (char) 0x00B0;
        r4TextView.setText(mStr);
        mStr = String.format("%.2f", rightPowerList[4]) + " " + String.format("%.1f", angleList[4]) + (char) 0x00B0;
        r5TextView.setText(mStr);
        mStr = String.format("%.2f", rightPowerList[5]) + " " + String.format("%.1f", angleList[5]) + (char) 0x00B0;
        r6TextView.setText(mStr);

        if (deviceId == 2) {
            mStr = String.format("%.2f", rightPowerList[6]) + " " + String.format("%.1f", angleList[6]) + (char) 0x00B0;
            r7TextView.setText(mStr);
            mStr = String.format("%.2f", rightPowerList[7]) + " " + String.format("%.1f", angleList[7]) + (char) 0x00B0;
            r8TextView.setText(mStr);
            mStr = String.format("%.2f", rightPowerList[8]) + " " + String.format("%.1f", angleList[8]) + (char) 0x00B0;
            r9TextView.setText(mStr);
        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUploadComplete) {
                    NetConnection conn = new NetConnection();
                    if (conn.isConnected(getApplicationContext())) {
                        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                        String url;
                        // if (serverId == 0)
                            // url = Constants.RestfulBaseURL + "/eyecloud/api/testresults";
                        // else
                            url = Constants.RestfulBaseURL + "/eyecloud/api/testresults?access_token=" + Constants.AccessToken;
                        // String url = Constants.RestfulBaseURL + "/eyecloud/api/testresults";
                        // String urlGet = Constants.RestfulBaseURL + "/eyecloud/api/testresults?access_token=" + Constants.AccessToken;

                        final UUID idOne = UUID.randomUUID();


                        final JSONObject params = new JSONObject();
                        try {

                            params.put("axisOS", Integer.toString((int) osAxis));
                            params.put("axisOD", Integer.toString((int) odAxis));
                            params.put("cylOD", String.format("%.2f", odCyl));
                            params.put("cylOS", String.format("%.2f", osCyl));
                            params.put("sphOD", String.format("%.2f", odSph));
                            params.put("sphOS", String.format("%.2f", osSph));
                            params.put("sphEOD", String.format("%.2f", odSe));
                            params.put("sphEOS", String.format("%.2f", osSe));
                            params.put("bCalcByPhone", "true");
                            params.put("deviceID", "null");
                            params.put("testType", "Full Refraction");
                            if (deviceId == 2)
                                params.put("deviceName", "Device 3");
                            else
                                params.put("deviceName", "Device 1");
                            params.put("phoneType", "Galaxy 6");
                            params.put("accomodationPatternName", "G5");
                            params.put("beamSplitter", "false");
                            params.put("originalTestResultID", idOne);
                            params.put("subjectID", String.valueOf(subjectId));

                            // if (serverId > 0) {
                                JSONArray mDataArr = new JSONArray();
                                for (int i = 0; i < numMeasurement; i++) {
                                    JSONObject mDataObj = new JSONObject();
                                    mDataObj.put("angle", String.format("%.1f", angleList[i]));
                                    mDataObj.put("distance", Integer.toString(rightDistList[i]));
                                    String mIdStr = "R-" + Integer.toString(i);
                                    mDataObj.put("mId", mIdStr);
                                    // mDataObj.put("rightEye", "true");
                                    mDataObj.put("power", String.format("%.2f", rightPowerList[i]));
                                    mDataObj.put("subjectID", String.valueOf(subjectId));
                                    mDataObj.put("testID", String.valueOf(idOne));
                                    mDataArr.put(mDataObj);
                                }
                                for (int i = 0; i < numMeasurement; i++) {
                                    JSONObject mDataObj = new JSONObject();
                                    mDataObj.put("angle", String.format("%.1f", angleList[i]));
                                    mDataObj.put("distance", Integer.toString(leftDistList[i]));
                                    String mIdStr = "L-" + Integer.toString(i);
                                    mDataObj.put("mId", mIdStr);
                                    // mDataObj.put("rightEye", "true");
                                    mDataObj.put("power", String.format("%.2f", leftPowerList[i]));
                                    mDataObj.put("subjectID", String.valueOf(subjectId));
                                    mDataObj.put("testID", String.valueOf(idOne));
                                    mDataArr.put(mDataObj);
                                }
                                params.put("measurements", mDataArr);
                            // }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.i(TAG, response);
                                isUploadComplete = true;
                                Toast.makeText(ResultActivity.this,
                                        "Uploaded to Server Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ResultActivity.this,
                                        "Can't connect to the *** server", Toast.LENGTH_SHORT).show();
                                Log.d("Error.Response", error.toString());
                            }
                        }) {
                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                Log.i("$$$---JSON---$$$", params.toString());
                                return params.toString().getBytes();
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json";
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("Content-Type", "application/json;charset=UTF-8");
                                return headers;
                            }
                        };
                        RetryPolicy policy = new DefaultRetryPolicy(Constants.NETCONN_TIMEOUT_VALUE, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                        postRequest.setRetryPolicy(policy);
                        queue.add(postRequest);

                        /*
                        if (serverId == 0) {
                            // Upload Measurement Data
                            // for (int i = 0; i < 6; i++ ) {
                            // Add code
                            // }
                            url = Constants.RestfulBaseURL + "/eyecloud/api/measurements";
                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[0]));
                                    params.put("distance", Integer.toString(rightDistList[0]));
                                    params.put("mId", "test-R-1");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[0]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[1]));
                                    params.put("distance", Integer.toString(rightDistList[1]));
                                    params.put("mId", "test-R-2");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[1]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[2]));
                                    params.put("distance", Integer.toString(rightDistList[2]));
                                    params.put("mId", "test-R-3");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[2]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[3]));
                                    params.put("distance", Integer.toString(rightDistList[3]));
                                    params.put("mId", "test-R-4");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[3]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[4]));
                                    params.put("distance", Integer.toString(rightDistList[4]));
                                    params.put("mId", "test-R-5");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[4]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[5]));
                                    params.put("distance", Integer.toString(rightDistList[5]));
                                    params.put("mId", "test-R-6");
                                    params.put("rightEye", "true");
                                    params.put("power", String.format("%.2f", rightPowerList[5]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[0]));
                                    params.put("distance", Integer.toString(leftDistList[0]));
                                    params.put("mId", "test-L-1");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[0]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[1]));
                                    params.put("distance", Integer.toString(leftDistList[1]));
                                    params.put("mId", "test-L-2");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[1]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[2]));
                                    params.put("distance", Integer.toString(leftDistList[2]));
                                    params.put("mId", "test-L-3");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[2]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[3]));
                                    params.put("distance", Integer.toString(leftDistList[3]));
                                    params.put("mId", "test-L-4");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[3]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[4]));
                                    params.put("distance", Integer.toString(leftDistList[4]));
                                    params.put("mId", "test-L-5");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[4]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                            postRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // response
                                            // isUploadComplete = true;
                                            // Toast.makeText(ResultActivity.this,
                                            // "Uploaded Measurement to Server Successfully", Toast.LENGTH_SHORT).show();
                                            Log.d("Response", response);
                                        }
                                    }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Toast.makeText(ResultActivity.this,
                                            "Can't connect to the server", Toast.LENGTH_SHORT).show();
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("angle", String.format("%.1f", angleList[5]));
                                    params.put("distance", Integer.toString(leftDistList[5]));
                                    params.put("mId", "test-L-6");
                                    params.put("rightEye", "false");
                                    params.put("power", String.format("%.2f", leftPowerList[5]));
                                    params.put("subjectID", String.valueOf(subjectId));
                                    params.put("testID", String.valueOf(idOne));
                                    return params;
                                }
                            };
                            queue.add(postRequest);

                        }
                        */

                    } else
                        Toast.makeText(ResultActivity.this,
                                "Can't connect to the server", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ResultActivity.this,
                            "Already Uploaded", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
