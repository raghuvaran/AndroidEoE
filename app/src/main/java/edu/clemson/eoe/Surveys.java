package edu.clemson.eoe;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Surveys extends AppCompatActivity {



     int patientID;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;
    private static final int MY_ACTION_INTENT_IMAGE_CAPTURE = 201;
    public static String mCurrentPhotoPath;
   //final TextView diff=(TextView)findViewById(R.id.dateDiff);;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private static final int[] symptoms_f_response = new int[12];
    static {
        symptoms_f_response[0]=1;
        symptoms_f_response[1]=0;
        symptoms_f_response[2]=0;
        symptoms_f_response[3]=0;
        symptoms_f_response[4]=0;
        symptoms_f_response[5]=0;
        symptoms_f_response[6]=0;
        symptoms_f_response[7]=0;
        symptoms_f_response[8]=0;
        symptoms_f_response[9]=0;
        symptoms_f_response[10]=0;
        symptoms_f_response[11]=0;
    }
    private static final int[] symptoms_s_response = new int[10];
    static {
        symptoms_s_response[1]=0;
        symptoms_s_response[2]=0;
        symptoms_s_response[3]=0;
        symptoms_s_response[4]=0;
        symptoms_s_response[5]=0;
        symptoms_s_response[6]=0;
        symptoms_s_response[7]=0;
        symptoms_s_response[8]=0;
        symptoms_s_response[9]=0;
    }
    private static final String[] fd_response = new String[8];
    static {
        fd_response[0]="0";
        fd_response[1]=null;   //where
        fd_response[2]=null;   //which
        fd_response[3]=null;   //who
        fd_response[4]=null;   //feel before
        fd_response[5]=null;   //feel after
        fd_response[6]=null;   //worry
        fd_response[7]=null;   //inputPerson
    }

    private static final int[] qol_response = new int[38];
    static{
        int counter = 1;
         //initialize all the array elements with null
        qol_response[0] = 1;
        qol_response[1] = -1;
        qol_response[2] = -1;
        qol_response[3] = -1;
        qol_response[4] = -1;
        qol_response[5] = -1;
        qol_response[6] = -1;
        qol_response[7] = -1;
        qol_response[8] = -1;
        qol_response[9] = -1;
        qol_response[10] = -1;
        qol_response[11] = -1;
        qol_response[12] = -1;
        qol_response[13] = -1;
        qol_response[14] = -1;
        qol_response[15] = -1;
        qol_response[16] = -1;
        qol_response[17] = -1;
        qol_response[18] = -1;
        qol_response[19] = -1;
        qol_response[20] = -1;
        qol_response[21] = -1;
        qol_response[22] = -1;
        qol_response[23] = -1;
        qol_response[24] = -1;
        qol_response[25] = -1;
        qol_response[26] = -1;
        qol_response[27] = -1;
        qol_response[28] = -1;
        qol_response[29] = -1;
        qol_response[30] = -1;
        qol_response[31] = -1;
        qol_response[32] = -1;
        qol_response[33] = -1;
        qol_response[34] = -1;
        qol_response[35] = -1;
        qol_response[36] = -1;
        qol_response[37] = -1;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPref = getSharedPreferences("myPref", 0);
       patientID = sharedPref.getInt("patientID", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
       // diff=(TextView)findViewById(R.id.dateDiff);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_surveys, menu);
        menu.add(2,11,1,"User Treatment").setIntent(new Intent(getApplicationContext(), UserTreatment.class));
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
            Intent settingsIntent = new Intent(this, Settings.class);
            settingsIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            settingsIntent.putExtra("List", "main");
            startActivity(settingsIntent);
            return true;
        }
     /*   else if(id==R.id.Sync){
            try {
                onFoodDiarySync();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else if(id==R.id.SyncSymptoms){
            try {
                onSymptomsSync();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else if(id==R.id.SyncQOL){
            try {
                onQOLSync();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else if(id==R.id.SyncUT){
            try {
                onUTSync();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/




        return super.onOptionsItemSelected(item);
    }

    private void onUTSync() throws ExecutionException, InterruptedException, JSONException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncUTStatus();
            Toast.makeText(getApplicationContext(), dbm.getSyncUTStatus(),
                    Toast.LENGTH_SHORT).show();
            dbm.close();
            SyncUT syncut = new SyncUT();

            if (status.equals("DB Sync neededn")) {
                String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertUT.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONUTfromSQLite();
                dbmSync.close();
                JSONArray response = syncut.execute(url, Jsondata).get();
                if (response.toString().equals(null)) {
                    Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
                    //check if there is a conflict with EXT database users
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject oneObject = response.getJSONObject(i);
                        int id, updatestatus,ExtId;
                        id = oneObject.getInt("UTID");
                        updatestatus = oneObject.getInt("status");

                        DataBaseManager dbmupdate = new DataBaseManager(this);
                        dbmupdate.open();
                        dbmupdate.updateSyncUT(id, updatestatus);
                        dbmupdate.close();


                    }
                }
            }
        }
        else
        {
            Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void onQOLSync() throws ExecutionException, InterruptedException, JSONException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncQolStatus();
            Toast.makeText(getApplicationContext(), dbm.getSyncQolStatus(),
                    Toast.LENGTH_SHORT).show();
            dbm.close();
            SyncQol syncQol = new SyncQol();

            if (status.equals("DB Sync neededn")) {
                String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertQol.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONQolfromSQLite();
                dbmSync.close();
                JSONArray response = syncQol.execute(url, Jsondata).get();
                if (response.toString().equals(null)) {
                    Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
                    //check if there is a conflict with EXT database users
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject oneObject = response.getJSONObject(i);
                        int id, updatestatus,ExtId;
                        id = oneObject.getInt("QolID");
                        updatestatus = oneObject.getInt("status");

                        DataBaseManager dbmupdate = new DataBaseManager(this);
                        dbmupdate.open();
                        dbmupdate.updateSyncQol(id, updatestatus);
                        dbmupdate.close();


                    }
                }
            }
        }
        else
        {
            Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }
    private void onSymptomsSync() throws ExecutionException, InterruptedException, JSONException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncSymptomsStatus();
            Toast.makeText(getApplicationContext(), dbm.getSyncSymptomsStatus(),
                    Toast.LENGTH_SHORT).show();
            dbm.close();
            SyncSymptoms syncSymptoms = new SyncSymptoms();

            if (status.equals("DB Sync neededn")) {
                String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONSymptomsfromSQLite();
                dbmSync.close();
                JSONArray response = syncSymptoms.execute(url, Jsondata).get();
                if (response.toString().equals(null)) {
                    Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
                    //check if there is a conflict with EXT database users
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject oneObject = response.getJSONObject(i);
                        int id, updatestatus,ExtId;
                        id = oneObject.getInt("symptomID");
                        updatestatus = oneObject.getInt("status");

                        DataBaseManager dbmupdate = new DataBaseManager(this);
                        dbmupdate.open();
                        dbmupdate.updateSyncSymptomStatus(id, updatestatus);
                        dbmupdate.close();


                    }
                }
            }
        }
        else
        {
            Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final int CAMERA_REQUEST = 1888;
        private ImageView imageView1;
        //Frequency question responses
        private static final HashMap<Float,String> freqResponse = new HashMap<Float, String>();
        static {
            freqResponse.put(1.0f,"Never");
            freqResponse.put(2.0f,"Almost never");
            freqResponse.put(3.0f,"Sometimes");
            freqResponse.put(4.0f,"Often");
            freqResponse.put(5.0f,"Almost always");
        }
        //Severity questions responses
        private static final HashMap<Float,String> severeResponse = new HashMap<Float, String>();
        static {
            severeResponse.put(1.0f,"Not bad at all");
            severeResponse.put(2.0f,"A little bad");
            severeResponse.put(3.0f,"Kind of bad");
            severeResponse.put(4.0f,"Bad");
            severeResponse.put(5.0f,"Very bad");
        }



        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        /**
         * Camera functionality
         * @param requestCode
         * @param resultCode
         * @param data
         */
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            if (requestCode == MY_ACTION_INTENT_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                Bitmap photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
                // bytedata = getBitmapAsByteArray(photo);
                imageView1.setImageBitmap(photo);
            }}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            switch (getArguments().getInt(ARG_SECTION_NUMBER))  {
                case 1: return onCreateSymptoms(inflater,container,savedInstanceState);
/*
                    LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.symptoms_survey_linearLayout);


                    for(int i=1; i<3; i++){
                        TextView question = new TextView(getContext());
                        question.setTextAppearance(getContext(),android.R.style.TextAppearance_Medium);
                        question.setId(1234+i);
                        question.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        question.setText(R.id.s2_q+"i");
                        linearLayout.addView(question);
                    }*/

                case 2: return onCreateFoodDiary(inflater,container,savedInstanceState);

                case 3: return onCreateQOL(inflater,container,savedInstanceState);


                default:
                    rootView = inflater.inflate(R.layout.fragment_surveys, container, false);
                    TextView textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
                    return rootView;

            }
        }

//--------------onCreate-Methods-Begin-------------------//

        public void setText(View v,String text){
            TextView textView = (TextView) v.findViewById(R.id.symtoms_survey_avail);
            textView.setText(text);
        }

        /**
         * OnCreate method for Symptoms layout
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */


        public View onCreateSymptoms(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState)  {

            View rootView;
            DataBaseManager dbm =new DataBaseManager(getContext());
            dbm.open();
            Cursor time=dbm.getSYmptomstime();
            if(time.moveToFirst()) {


                String recenttime = time.getString(time
                        .getColumnIndex("time"));
                dbm.close();
                Calendar symptomscalendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());

                try {
                    symptomscalendar.setTime(sdf.parse(recenttime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                symptomscalendar.add(Calendar.DATE, 7);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String output = sdf1.format(symptomscalendar.getTime());
                Date input = new Date(), currentdate = new Date();
                try {
                    input = sdf.parse(output);
                    currentdate = sdf.parse(currentDateandTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long different = input.getTime() - currentdate.getTime();
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;
                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;


                if (input.compareTo(currentdate) > 0) {

                    Log.i("Date", "after");
                    //rootView = inflater.inflate(R.layout.symptoms_survey, container, false);
                    rootView = inflater.inflate(R.layout.symtons_survey_na, container, false);
                    setText(rootView, "Survey will be available in  " + elapsedDays + " Days");
                  //   diff.setText(""+different+"Days");
                    //Add a new layout xml here
                } else {
                    Log.i("Date", "before");
                    //continue with old one


                    rootView = inflater.inflate(R.layout.symptoms_survey, container, false);
                    setRatingBarListener(rootView, 1, R.id.s1_f_ratingBar, R.id.s1_f_res, freqResponse, R.id.s1_s, R.id.s1_s_ratingBar, R.id.s1_s_res);
                    setRatingBarListener(rootView, 2, R.id.s2_f_ratingBar, R.id.s2_f_res, freqResponse, R.id.s2_s, R.id.s2_s_ratingBar, R.id.s2_s_res);
                    setRatingBarListener(rootView, 3, R.id.s3_f_ratingBar, R.id.s3_f_res, freqResponse, R.id.s3_s, R.id.s3_s_ratingBar, R.id.s3_s_res);
                    setRatingBarListener(rootView, 4, R.id.s4_f_ratingBar, R.id.s4_f_res, freqResponse, R.id.s4_s, R.id.s4_s_ratingBar, R.id.s4_s_res);
                    setRatingBarListener(rootView, 5, R.id.s5_f_ratingBar, R.id.s5_f_res, freqResponse, R.id.s5_s, R.id.s5_s_ratingBar, R.id.s5_s_res);
                    setRatingBarListener(rootView, 6, R.id.s6_f_ratingBar, R.id.s6_f_res, freqResponse, R.id.s6_s, R.id.s6_s_ratingBar, R.id.s6_s_res);
                    setRatingBarListener(rootView, 7, R.id.s7_f_ratingBar, R.id.s7_f_res, freqResponse, R.id.s7_s, R.id.s7_s_ratingBar, R.id.s7_s_res);
                    setRatingBarListener(rootView, 8, R.id.s8_f_ratingBar, R.id.s8_f_res, freqResponse, R.id.s8_s, R.id.s8_s_ratingBar, R.id.s8_s_res);
                    setRatingBarListener(rootView, 9, R.id.s9_f_ratingBar, R.id.s9_f_res, freqResponse, R.id.s9_s, R.id.s9_s_ratingBar, R.id.s9_s_res);
                    setRatingBarListener(rootView, 10, R.id.s10_f_ratingBar, R.id.s10_f_res, freqResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 11, R.id.s11_f_ratingBar, R.id.s11_f_res, freqResponse, 0, 0, 0);

                    setRatingBarListener(rootView, 1, R.id.s1_s_ratingBar, R.id.s1_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 2, R.id.s2_s_ratingBar, R.id.s2_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 3, R.id.s3_s_ratingBar, R.id.s3_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 4, R.id.s4_s_ratingBar, R.id.s4_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 5, R.id.s5_s_ratingBar, R.id.s5_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 6, R.id.s6_s_ratingBar, R.id.s6_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 7, R.id.s7_s_ratingBar, R.id.s7_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 8, R.id.s8_s_ratingBar, R.id.s8_s_res, severeResponse, 0, 0, 0);
                    setRatingBarListener(rootView, 9, R.id.s9_s_ratingBar, R.id.s9_s_res, severeResponse, 0, 0, 0);


                }
            }
            else
            {
                dbm.close();
                rootView = inflater.inflate(R.layout.symptoms_survey, container, false);
                setRatingBarListener(rootView, 1, R.id.s1_f_ratingBar, R.id.s1_f_res, freqResponse, R.id.s1_s, R.id.s1_s_ratingBar, R.id.s1_s_res);
                setRatingBarListener(rootView, 2, R.id.s2_f_ratingBar, R.id.s2_f_res, freqResponse, R.id.s2_s, R.id.s2_s_ratingBar, R.id.s2_s_res);
                setRatingBarListener(rootView, 3, R.id.s3_f_ratingBar, R.id.s3_f_res, freqResponse, R.id.s3_s, R.id.s3_s_ratingBar, R.id.s3_s_res);
                setRatingBarListener(rootView, 4, R.id.s4_f_ratingBar, R.id.s4_f_res, freqResponse, R.id.s4_s, R.id.s4_s_ratingBar, R.id.s4_s_res);
                setRatingBarListener(rootView, 5, R.id.s5_f_ratingBar, R.id.s5_f_res, freqResponse, R.id.s5_s, R.id.s5_s_ratingBar, R.id.s5_s_res);
                setRatingBarListener(rootView, 6, R.id.s6_f_ratingBar, R.id.s6_f_res, freqResponse, R.id.s6_s, R.id.s6_s_ratingBar, R.id.s6_s_res);
                setRatingBarListener(rootView, 7, R.id.s7_f_ratingBar, R.id.s7_f_res, freqResponse, R.id.s7_s, R.id.s7_s_ratingBar, R.id.s7_s_res);
                setRatingBarListener(rootView, 8, R.id.s8_f_ratingBar, R.id.s8_f_res, freqResponse, R.id.s8_s, R.id.s8_s_ratingBar, R.id.s8_s_res);
                setRatingBarListener(rootView, 9, R.id.s9_f_ratingBar, R.id.s9_f_res, freqResponse, R.id.s9_s, R.id.s9_s_ratingBar, R.id.s9_s_res);
                setRatingBarListener(rootView, 10, R.id.s10_f_ratingBar, R.id.s10_f_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 11, R.id.s11_f_ratingBar, R.id.s11_f_res, freqResponse, 0, 0, 0);

                setRatingBarListener(rootView, 1, R.id.s1_s_ratingBar, R.id.s1_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 2, R.id.s2_s_ratingBar, R.id.s2_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 3, R.id.s3_s_ratingBar, R.id.s3_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 4, R.id.s4_s_ratingBar, R.id.s4_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 5, R.id.s5_s_ratingBar, R.id.s5_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 6, R.id.s6_s_ratingBar, R.id.s6_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 7, R.id.s7_s_ratingBar, R.id.s7_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 8, R.id.s8_s_ratingBar, R.id.s8_s_res, severeResponse, 0, 0, 0);
                setRatingBarListener(rootView, 9, R.id.s9_s_ratingBar, R.id.s9_s_res, severeResponse, 0, 0, 0);
            }

      /*      TableLayout tableLayout = (TableLayout) rootView.findViewById(R.id.symptoms_survey_tableLayout);
            TextView textView = new TextView(getActivity());
            textView.setText(R.string.s3_f);
            //textView.setTextAppearance(android.R.style.TextAppearance_Medium);
            tableLayout.addView(textView);*/

            return rootView;
        }

        /**
         * onCreate method for Food diary layout
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        public View onCreateFoodDiary(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.food_diary, container, false);
            imageView1 = (ImageView)rootView.findViewById(R.id.camera_iv);

            imageView1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //First Request permission to write to external storage, this is so you can get you file
                    //from the camera after the picture has been taken.
                    int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //If it's granted go to method takePicture
                    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                        takePicture();
                        //Else ask the user for permission
                    } else
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    //Camera to capture food item
                    //Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    //startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });


            populateSpinner(rootView, 1, R.id.fd_where_spinner, R.array.fd_where_a);
            populateSpinner(rootView, 2, R.id.fd_which_spinner, R.array.fd_which_a);
            populateSpinner(rootView, 3, R.id.fd_who_spinner, R.array.fd_who_a);
            onEditTextListener(rootView, 3, R.id.fd_who_others);
            onRadioChange(rootView, 4, R.id.fd_fA);
            onRadioChange(rootView,5,R.id.fd_fB);
            onRadioChange(rootView,6,R.id.fd_worry);
            onRadioChange(rootView, 7, R.id.fd_whosInput);


            return rootView;
        }


        public void takePicture() {
            File photoFile = null;
            try {
                //First allocate space for the image
                photoFile = createImageFile();
            } catch (IOException ex) {

            }

            //If you were able to create a file
            if (photoFile != null) {
                //Call the camera intent to take a picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, MY_ACTION_INTENT_IMAGE_CAPTURE);
                }
            }
        }


        //This method creates a new file for you image to be stored at using a timestamp as a unique filename

        private File createImageFile() throws IOException {
            String FDtimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + FDtimeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            mCurrentPhotoPath = image.getAbsolutePath();
            return image;
        }




        /**
         * OnCreate method for Symptoms layout
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        public View onCreateQOL(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState){
            View rootView;
            DataBaseManager dbm =new DataBaseManager(getContext());
            dbm.open();
            Cursor time=dbm.getQOLtime();;
            String recenttime;
            if(time.moveToFirst()) {
                recenttime = time.getString(time
                        .getColumnIndex("time"));



                Calendar QOLcalendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentDateandTime = sdf.format(new Date());

                try {
                    QOLcalendar.setTime(sdf.parse(recenttime));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                QOLcalendar.add(Calendar.DATE, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String output = sdf1.format(QOLcalendar.getTime());
                Date input = new Date(), currentdate = new Date();
                try {
                    input = sdf.parse(output);
                    currentdate = sdf.parse(currentDateandTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long different = input.getTime() - currentdate.getTime();
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;
                long elapsedDays = different / daysInMilli;
                different = different % daysInMilli;



                if (input.compareTo(currentdate) > 0) {

                    Log.i("Date", "after");

                    rootView = inflater.inflate(R.layout.symtons_survey_na, container, false);
                    setText(rootView, "Survey will be available in  " + elapsedDays + " Days");
                    dbm.close();
                    return rootView;
                    // diff.setText(""+different+"Days");
                    //Add a new layout xml here
                } }

                dbm.close();
                rootView = inflater.inflate(R.layout.qol_survey, container, false);
                setRatingBarListener(rootView, 1, R.id.s1_q1_ratingBar, R.id.s1_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 2, R.id.s1_q2_ratingBar, R.id.s1_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 3, R.id.s1_q3_ratingBar, R.id.s1_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 4, R.id.s1_q4_ratingBar, R.id.s1_q4_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 5, R.id.s1_q5_ratingBar, R.id.s1_q5_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 6, R.id.s1_q6_ratingBar, R.id.s1_q6_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 7, R.id.s2_q1_ratingBar, R.id.s2_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 8, R.id.s2_q2_ratingBar, R.id.s2_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 9, R.id.s2_q3_ratingBar, R.id.s2_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 10, R.id.s2_q4_ratingBar, R.id.s2_q4_res, freqResponse, 0, 0, 0);

                setRatingBarListener(rootView, 11, R.id.s3_q1_ratingBar, R.id.s3_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 12, R.id.s3_q2_ratingBar, R.id.s3_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 13, R.id.s3_q3_ratingBar, R.id.s3_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 14, R.id.s3_q4_ratingBar, R.id.s3_q4_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 15, R.id.s3_q5_ratingBar, R.id.s3_q5_res, freqResponse, 0, 0, 0);

                setRatingBarListener(rootView, 16, R.id.s4_q1_ratingBar, R.id.s4_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 17, R.id.s4_q2_ratingBar, R.id.s4_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 18, R.id.s4_q3_ratingBar, R.id.s4_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 19, R.id.s4_q4_ratingBar, R.id.s4_q4_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 20, R.id.s4_q5_ratingBar, R.id.s4_q5_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 21, R.id.s4_q6_ratingBar, R.id.s4_q6_res, freqResponse, 0, 0, 0);

                setRatingBarListener(rootView, 22, R.id.s5_q1_ratingBar, R.id.s5_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 23, R.id.s5_q2_ratingBar, R.id.s5_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 24, R.id.s5_q3_ratingBar, R.id.s5_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 25, R.id.s5_q4_ratingBar, R.id.s5_q4_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 26, R.id.s5_q5_ratingBar, R.id.s5_q5_res, freqResponse, 0, 0, 0);

                onRadioChange(rootView, 27, R.id.s6_s7
                ,R.id.s6_q1_ratingBar,R.id.s6_q2_ratingBar,R.id.s6_q3_ratingBar,R.id.s6_q4_ratingBar,R.id.s7_q1_ratingBar,R.id.s7_q2_ratingBar,R.id.s7_q3_ratingBar
                ,R.id.s6_title,R.id.s7_title
                ,R.id.s6_q1,R.id.s6_q2,R.id.s6_q3,R.id.s6_q4,R.id.s7_q1,R.id.s7_q2,R.id.s7_q3
                ,R.id.s6_q1_res,R.id.s6_q2_res,R.id.s6_q3_res,R.id.s6_q4_res,R.id.s7_q1_res,R.id.s7_q2_res,R.id.s7_q3_res);

                setRatingBarListener(rootView, 28, R.id.s6_q1_ratingBar, R.id.s6_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 29, R.id.s6_q2_ratingBar, R.id.s6_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 30, R.id.s6_q3_ratingBar, R.id.s6_q3_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 31, R.id.s6_q4_ratingBar, R.id.s6_q4_res, freqResponse, 0, 0, 0);

                setRatingBarListener(rootView, 32, R.id.s7_q1_ratingBar, R.id.s7_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 33, R.id.s7_q2_ratingBar, R.id.s7_q2_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 34, R.id.s7_q3_ratingBar, R.id.s7_q3_res, freqResponse, 0, 0, 0);

                onRadioChange(rootView, 35, R.id.s8
                ,R.id.s8_title
                ,R.id.s8_q1,R.id.s8_q2
                ,R.id.s8_q1_res,R.id.s8_q2_res
                ,R.id.s8_q1_ratingBar,R.id.s8_q2_ratingBar);

                setRatingBarListener(rootView, 36, R.id.s8_q1_ratingBar, R.id.s8_q1_res, freqResponse, 0, 0, 0);
                setRatingBarListener(rootView, 37, R.id.s8_q2_ratingBar, R.id.s8_q2_res, freqResponse, 0, 0, 0);

            //setRatingBarListener(rootView, R.id.s1_s_ratingBar, R.id.s1_s_res, severeResponse);
            return rootView;
        }
//--------------onCreate-Methods-Terminate-------------------//
//---------------Methods-Begin-------------------//
        /**
         * sets the response to the textview {@q_result} from the above declared static {@hashmap}
         * when user changes the rating in ratingbar {@question}
         * @param v
         * @param question
         * @param q_result
         * @param hashMap
         * @param next_question
         * @param next_question_r
         */
        public void setRatingBarListener( View v,int id, int question, int q_result, final HashMap hashMap,int next_question, int next_question_r, int next_question_res){
            final int q_id = id;
            final RatingBar q1 = (RatingBar) v.findViewById(question);
            final TextView q1_res = (TextView) v.findViewById(q_result);
            final int next_q = next_question;
            final int next_q_r = next_question_r;
            final int next_q_res = next_question_res;
            final View view = v;

            q1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //Toast.makeText(getContext(), "Changed ratings to " + rating, Toast.LENGTH_SHORT).show();
                    q1_res.setText(hashMap.get(rating).toString());
                    q1_res.setVisibility(View.VISIBLE);


                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {  //If called from Symptoms view (1)
                        if (hashMap.get(4f).toString().equalsIgnoreCase("Often")) { //all frequency questions
                            symptoms_f_response[q_id] = (int) rating;
                        } else {
                            symptoms_s_response[q_id] = (int) rating;
                        }
                    }

                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {  //If called from Food diary view (2)
                        fd_response[q_id] = hashMap.get(rating).toString();
                    }

                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {  //If called from QoL view (3)
                        qol_response[q_id] = (int) rating - 1;

                    }
                    //Disable severity question
                    if (next_q != 0) {   //non-zero mode for frequency questions

                        final RatingBar q2 = (RatingBar) view.findViewById(next_q_r);
                        final TextView q2_q = (TextView) view.findViewById(next_q);
                        final TextView q2_res = (TextView) view.findViewById(next_q_res);
                        if (rating == 1f) {
                            q2.setVisibility(View.GONE);
                            q2_q.setVisibility(View.GONE);
                            q2_res.setVisibility(View.GONE);
                        } else {
                            q2.setVisibility(View.VISIBLE);
                            q2_q.setVisibility(View.VISIBLE);
                            q2_res.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });
        }

        /**
         * Populates the spinner of {@spinId} with {@stingArrayId}
         * @param view
         * @param spinId
         * @param stringArrayId
         */
        public void populateSpinner(View view,int id,int spinId,int stringArrayId){
            final View v = view;
            final int q_id = id;
            Spinner spinner = (Spinner) view.findViewById(spinId);//R.id.f1_spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    stringArrayId, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            final String[] stringArray =getResources().getStringArray(stringArrayId);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    fd_response[q_id] = stringArray[position];
                    if (q_id == 3) {
                        if (position == 3) {
                            v.findViewById(R.id.fd_who_others_holder).setVisibility(View.VISIBLE);
                        } else {
                            v.findViewById(R.id.fd_who_others_holder).setVisibility(View.GONE);
                        }

                    }

                    Log.i("selected item", String.valueOf(position) + "and id is" + stringArray[position]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        }

        public void onRadioChange(final View view,int id, int radio, final Integer... params){
            final View v = view;
            final int q_id = id;
            final RadioGroup radioGroup = (RadioGroup) view.findViewById(radio);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 2) {  //If called from Food diary view (2)
                        fd_response[q_id] = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                    }
                    if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {  //If called from Food diary view (3)
                        if(((RadioButton) v.findViewById(checkedId)).getText().toString().equalsIgnoreCase("yes")){
                            qol_response[q_id] = 1;
                            if(params.length>0){ //set all the views in params to visible
                                for(int viewId: params){
                                    View view1 = v.findViewById(viewId);
                                    view1.setVisibility(View.VISIBLE);
                                }
                            }
                        }else{
                            qol_response[q_id] = 0;
                            if(params.length>0){ //set all the views in params to visible
                                for(int viewId: params){
                                    View view1 = v.findViewById(viewId);
                                    view1.setVisibility(View.GONE);
                                }
                            }
                        }

                    }


                }
            });
        }

        public void onEditTextListener(View view,int id, int editTextId){
            final View v = view;
            final int q_id = id;
            final EditText editText = (EditText) view.findViewById(editTextId);

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    fd_response[q_id]= String.valueOf(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }



//---------------Methods-Terminate-------------------//
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    public void onSymptomsSubmit(View view){
        int counter = 0;

//        for(int i:symptoms_f_response) {
//           // Log.i("Symtoms response_f"+ (counter++), String.valueOf(i));
//            if(symptoms_f_response[i]==0)
//            {
//                counter++;
//
//            }
//
//        }

        for(int i=0;i<symptoms_f_response.length;i++)
        {
            if(symptoms_f_response[i]==0)
            {
                counter++;
                Log.i("Symtoms response_f"+i,String.valueOf(i));
            }
        }


       // counter = 0;
      /*  for(int i:symptoms_s_response)
        {
            Log.i("Symtoms response_s"+(counter++), String.valueOf(i));
        }*/

        /**
         * Database query goes below this block--
         * Eg. array structure
         * symptoms_f_response[1] -> question_1_frequency
         * symptoms_f_response[11] -> question_11_frequency
         * symptoms_s_response[9] -> question_9_severity
         */
        if(counter!=0) {
            Toast.makeText(getApplicationContext(), "Please answer all frequency questions ",
                    Toast.LENGTH_SHORT).show();
        }
        else {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            DataBaseManager dbm = new DataBaseManager(getApplicationContext());
            dbm.open();
            boolean result = dbm.addSymptoms(patientID, currentDateandTime, symptoms_f_response, symptoms_s_response);
            dbm.close();
            if (result) {

                Toast.makeText(getApplicationContext(), "Peess responses saved ",
                        Toast.LENGTH_SHORT).show();
                Intent callactivity =new Intent(getApplicationContext(),Surveys.class);
                startActivity(callactivity);
                Intent   mServiceIntent = new Intent(this, SendData.class);
                ///mServiceIntent.putExtra("KEY","https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php");
                startService(mServiceIntent);
            }


        }
    }

    public void onFoodDiarySubmit(View view){
        int counter = 0;
        for(int i=0;i<fd_response.length;i++)
        {
            if(fd_response[i]==null || mCurrentPhotoPath ==null)
            {
                counter++;
            }
        }
        if(counter!=0) {
            Toast.makeText(getApplicationContext(), "Please answer all Survey questions ",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            boolean result = dbm.addFoodDiary(patientID, currentDateandTime, fd_response[2], fd_response[1], fd_response[3], fd_response[4], fd_response[5], fd_response[6], fd_response[7], mCurrentPhotoPath);
            dbm.close();
            if (result) {

                Toast.makeText(getApplicationContext(), "Thank you for submitting your Food diary",
                        Toast.LENGTH_SHORT).show();

                Intent callactivity =new Intent(getApplicationContext(),Surveys.class);
                startActivity(callactivity);

                Intent   mServiceIntent = new Intent(this, SendData.class);
                ///mServiceIntent.putExtra("KEY","https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php");
                startService(mServiceIntent);

            }
        }
        for (String i:fd_response){
            Log.i("FoodDiary response","fd_response"+counter+" is "+i);
        }
        // TODO: 07-04-2016 add database queries here!
    }

    public void onQoLSubmit(View view){
        int counter = 0;
        for(int i=0;i<qol_response.length;i++)
        {
            if(qol_response[i]==-1 && i<27){counter++;}
            if(qol_response[i]==-1 && i>27 && i<35 && qol_response[27]==1 ) {counter++;}
            if(qol_response[i]==-1 && i>35 && qol_response[35]==1 ) {counter++;}
        }
        if(counter!=0) {
            Toast.makeText(getApplicationContext(), "Please answer all Survey questions ",
                    Toast.LENGTH_SHORT).show();


        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            DataBaseManager dbm = new DataBaseManager(getApplicationContext());
            dbm.open();
            boolean result = dbm.addQol(patientID, currentDateandTime, qol_response);
            dbm.close();
            if (result) {

                Toast.makeText(getApplicationContext(), "PedsQl response saved ",
                Toast.makeText(getApplicationContext(), "Thank you for giving QoL survey",
                        Toast.LENGTH_SHORT).show();

                Intent callActivity =new Intent(getApplicationContext(),Surveys.class);
                startActivity(callActivity);
                Intent   mServiceIntent = new Intent(this, SendData.class);
                ///mServiceIntent.putExtra("KEY","https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php");
                startService(mServiceIntent);
            }

        }

    }

    public void onFoodDiarySync() throws ExecutionException, InterruptedException, JSONException {

        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncStatus();
            Toast.makeText(getApplicationContext(), dbm.getSyncStatus(),
                    Toast.LENGTH_SHORT).show();
            dbm.close();
            SyncFoodDiary syncFoodDiary = new SyncFoodDiary();

            if (status.equals("DB Sync neededn")) {
                String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertfoodDiary.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONfromSQLite();
                dbmSync.close();
                JSONArray response = syncFoodDiary.execute(url, Jsondata).get();
                if (response.toString().equals(null)) {
                    Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
                    //check if there is a conflict with EXT database users
                } else {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject oneObject = response.getJSONObject(i);
                        int id, updatestatus,ExtId;
                        id = oneObject.getInt("foodDairyID");
                        updatestatus = oneObject.getInt("status");
                        ExtId=oneObject.getInt("DiaryID");
                        DataBaseManager dbmupdate = new DataBaseManager(this);
                        dbmupdate.open();
                        dbmupdate.updateSyncStatus(id, updatestatus);
                        dbmupdate.close();
                        DataBaseManager dbmgetphoto = new DataBaseManager(this);
                        dbmgetphoto.open();
                        Cursor res =dbmgetphoto.getPhotoPath(id);
                        res.moveToFirst();
                        String internalphotopath=res.getString(res
                                .getColumnIndex("Image"));
                        dbmgetphoto.close();

                        new ImageUploader(ExtId).execute(internalphotopath);

                    }
                }
            }
        }
        else
        {
            Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public class SyncFoodDiary extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(String... params) {

            String ret = null;
            JSONArray  foodDiary;

            try {

                // Create the SSL connection

                Log.d("database", "Doing: " + params[0]);
                HttpURLConnection c = null;
                URL u = new URL(params[0]);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();

               // String data = "Jsondata" + "=" + URLEncoder.encode(params[1], "UTF-8");

                c.setRequestMethod("POST");

                c.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                c.setDoOutput(true);
                c.setDoInput(true);

                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(params[1].getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();
                c.connect();

                int status = c.getResponseCode();

                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line + "\n");
                    br.close();
                    ret = sb.toString();

                    Log.i("result",ret);


                }
                String actual = ret.toString().replace("{\"success\":true,\"results\":", "");
                actual = actual.replace("]}", "]");
                foodDiary=new JSONArray(actual);
                return foodDiary;
                //Log.i("result",result);
               // return(result);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
            //returns php output


        }


    }


    public class SyncSymptoms extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(String... params) {

            String ret = null;
            JSONArray  foodDiary;

            try {

                // Create the SSL connection

                Log.d("database", "Doing: " + params[0]);
                HttpURLConnection c = null;
                URL u = new URL(params[0]);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();

                // String data = "Jsondata" + "=" + URLEncoder.encode(params[1], "UTF-8");

                c.setRequestMethod("POST");

                c.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                c.setDoOutput(true);
                c.setDoInput(true);

                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(params[1].getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();
                c.connect();

                int status = c.getResponseCode();

                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line + "\n");
                    br.close();
                    ret = sb.toString();

                    Log.i("result",ret);


                }
                String actual = ret.toString().replace("{\"success\":true,\"results\":", "");
                actual = actual.replace("]}", "]");
                foodDiary=new JSONArray(actual);
                return foodDiary;
                //Log.i("result",result);
                // return(result);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
            //returns php output


        }


    }


    public class SyncQol extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(String... params) {

            String ret = null;
            JSONArray  foodDiary;

            try {

                // Create the SSL connection

                Log.d("database", "Doing: " + params[0]);
                HttpURLConnection c = null;
                URL u = new URL(params[0]);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();

                // String data = "Jsondata" + "=" + URLEncoder.encode(params[1], "UTF-8");

                c.setRequestMethod("POST");

                c.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                c.setDoOutput(true);
                c.setDoInput(true);

                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(params[1].getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();
                c.connect();

                int status = c.getResponseCode();

                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line + "\n");
                    br.close();
                    ret = sb.toString();

                    Log.i("result",ret);


                }
                String actual = ret.toString().replace("{\"success\":true,\"results\":", "");
                actual = actual.replace("]}", "]");
                foodDiary=new JSONArray(actual);
                return foodDiary;
                //Log.i("result",result);
                // return(result);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
            //returns php output


        }


    }


    public class SyncUT extends AsyncTask<String, Void, JSONArray> {


        @Override
        protected JSONArray doInBackground(String... params) {

            String ret = null;
            JSONArray  foodDiary;

            try {

                // Create the SSL connection

                Log.d("database", "Doing: " + params[0]);
                HttpURLConnection c = null;
                URL u = new URL(params[0]);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();

                // String data = "Jsondata" + "=" + URLEncoder.encode(params[1], "UTF-8");

                c.setRequestMethod("POST");

                c.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                c.setDoOutput(true);
                c.setDoInput(true);

                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(params[1].getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(params[1]);
                wr.flush();
                wr.close();
                c.connect();

                int status = c.getResponseCode();

                if (status == 200 || status == 201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line + "\n");
                    br.close();
                    ret = sb.toString();

                    Log.i("result",ret);


                }
                String actual = ret.toString().replace("{\"success\":true,\"results\":", "");
                actual = actual.replace("]}", "]");
                foodDiary=new JSONArray(actual);
                return foodDiary;
                //Log.i("result",result);
                // return(result);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
            //returns php output


        }


    }

    //An async task to upload an image
    private class ImageUploader extends AsyncTask<String, Void, String> {

        private int itemID;

        public ImageUploader(int itemID){
            this.itemID = itemID;
        }

        protected String doInBackground(String... params) {
            DataBaseManager db = new DataBaseManager(getApplicationContext());
            db.open();
            //All of my server code is located in DBAdapter, go to the method uploadFile to see how
            //to finish uploading an image
            String result=db.uploadFile(params[0], itemID);
            db.close();
            return result;

        }

        protected void onPostExecute(String url) {
            super.onPostExecute(url);
            //If the image loads successfully the php script will return the filename
            if(!url.equals("0")){
                DataBaseManager db = new DataBaseManager(getApplicationContext());
                //Add it to your local db

                //And then tell the user that the file was successfully uploaded
                //In a real application you would want to check for Internet access before doing this
                //and save the image for later upload if there was no access
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();

                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "File Upload Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "Food Diary";
                case 0:
                    return "Symptoms";
                case 2:
                    return "QoL";
            }
            return null;
        }
    }


}
