package com.eyeque.mono;

/**
 * Created by georgez on 3/2/16.
 */
import android.provider.BaseColumns;

public class Constants {

    public final static String BuildNumber = "0.8.7";

    public static final double PI = 3.141592653589793d;

    // Restful API Call Base URL Address
    public static String RestfulBaseURL = null;
    public final static String LocalRestfulBaseURL = "http://192.168.110.122:8080";
    public final static String AwsEc2RestfulBaseURL = "http://54.191.245.62:8080";
    // public final static String LocalRestfulBaseURL = "http://192.168.110.85:8080";
    public final static String AccessToken = "e46cghc52pqd8kvgqmv8ovsi1ufcfetg";
    // public final static String AccessToken = "472h0onmgk3o18bn8kj629m8s2tke6k0";

    public final static int NETCONN_TIMEOUT_VALUE = 5000;

    // Line distance scale parameters
    public static final int MINVAL_DEVICE_1 = 270;
    public static final int MINVAL_DEVICE_3 = 200;
    public static final int MAXVAL_DEVICE_1 = 60;
    public static final int MAXVAL_DEVICE_3 = 260;

    // Database parameters
    public static final String DB_NAME = "mono.db";
    public static final int DB_VERSION = 1;
    public static final String TEST_SUBJECT_TABLE = "test_subject";
    public static final String TEST_RESULT_TABLE = "test_result";
    public static final String TEST_MEASUREMENT_TABLE = "test_measurement";


    // Columns for test_subject table
    public static final String TEST_SUBJECT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_SUBJECT_VERSION_COLUMN  = "version";
    public static final String TEST_SUBJECT_FIRST_NAME_COLUMN  = "first_name";
    public static final String TEST_SUBJECT_LAST_NAME_COLUMN  = "last_name";
    public static final String TEST_SUBJECT_EMAIL_COLUMN  = "email";
    public static final String TEST_SUBJECT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_SUBJECT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_SUBJECT_STATUS  = "status";

    // Columns for test_result table
    public static final String TEST_RESULT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_RESULT_VERSION_COLUMN  = "version";
    public static final String TEST_RESULT_SUBJECT_ID_COLUMN  = "subject_id";
    public static final String TEST_RESULT_DEVICE_ID_COLUMN  = "device_id";
    public static final String TEST_RESULT_DEVICE_NAME_COLUMN  = "device_name";
    public static final String TEST_RESULT_BEAM_SPILTTER_COLUMN  = "beam_splitter";
    public static final String TEST_RESULT_SPH_OD_COLUMN  = "sphod";
    public static final String TEST_RESULT_SPH_OS_COLUMN  = "sphos";
    public static final String TEST_RESULT_CYL_OD_COLUMN  = "cylod";
    public static final String TEST_RESULT_CYL_OS_COLUMN  = "cylos";
    public static final String TEST_RESULT_AXIS_OD_COLUMN  = "axisod";
    public static final String TEST_RESULT_AXIS_OS_COLUMN  = "axisos";
    public static final String TEST_RESULT_SE_OD_COLUMN  = "seod";
    public static final String TEST_RESULT_SE_OS_COLUMN  = "seos";
    public static final String TEST_RESULT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_RESULT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_RESULT_STATUS  = "status";

    // Columns for test_measurement table
    public static final String TEST_MEASUREMENT_ID_COLUMN  = BaseColumns._ID;
    public static final String TEST_MEASUREMENT_VERSION_COLUMN  = "version";
    public static final String TEST_MEASUREMENT_SUBJECT_ID_COLUMN  = "subject_id";
    public static final String TEST_MEASUREMENT_TEST_ID_COLUMN  = "test_id";  // Reference test_result ID
    public static final String TEST_MEASUREMENT_ANGLE_COLUMN  = "angle";
    public static final String TEST_MEASUREMENT_DISTANCE_COLUMN  = "distance";
    public static final String TEST_MEASUREMENT_POWER_COLUMN  = "power";
    public static final String TEST_MEASUREMENT_RIGHT_EYE_COLUMN  = "right_eye";
    public static final String TEST_MEASUREMENT_CREATED_AT_COLUMN  = "created_at";
    public static final String TEST_MEASUREMENT_LAST_SYNCED_AT_COLUMN  = "last_synced_at";
    public static final String TEST_MEASUREMENT_STATUS  = "status";

    /*
    public void setRestfulBaseURL(int number) {
        if (number > 0)
            RestfulBaseURL = AwsEc2RestfulBaseURL;
        else
            RestfulBaseURL = LocalRestfulBaseURL;
    }
    */

}
