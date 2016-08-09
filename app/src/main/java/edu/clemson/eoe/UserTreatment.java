package edu.clemson.eoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UserTreatment extends AppCompatActivity {

    int patientID;

    public static String[] ut_response = new String[11];
    static{
        ut_response[0]= "dummy";
        ut_response[1]=null;
        ut_response[2]=null;
        ut_response[3]=null;
        ut_response[4]=null;
        ut_response[5]=null;
        ut_response[6]=null;
        ut_response[7]=null;
        ut_response[8]=null;
        ut_response[9]=null;
        ut_response[10]=null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView;

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DataBaseManager dbm =new DataBaseManager(getApplicationContext());
        dbm.open();
        Cursor time=dbm.getUTtime();

        if(time.moveToFirst()) {

            String recenttime = time.getString(time
                    .getColumnIndex("time"));
            dbm.close();
            Calendar UTcalendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateandTime = sdf.format(new Date());

            try {
                UTcalendar.setTime(sdf.parse(recenttime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            UTcalendar.add(Calendar.DATE, 120);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String output = sdf1.format(UTcalendar.getTime());
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

                setContentView(R.layout.symtons_survey_na);

                TextView diff;
                diff = (TextView) findViewById(R.id.symtoms_survey_avail);
                diff.setText("Survey will be available in " + elapsedDays + " Days ");

                //Add a new layout xml here
            } else {
                Log.i("Date", "before");


                setContentView(R.layout.activity_user_treatment);
                SharedPreferences sharedPref = getSharedPreferences("myPref", 0);
                patientID = sharedPref.getInt("patientID", 0);
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                final View view = this.getWindow().getDecorView();

                onRadioChange(view, 1, R.id.ut_1, 0, 0);//5
                onRadioChange(view, 2, R.id.ut_2, R.id.ut_3_q, R.id.ut_3, R.id.ut_4_q, R.id.ut_4);//7
                onRadioChange(view, 3, R.id.ut_3, R.id.ut_5_q, R.id.ut_5);//5
                onRadioChange(view, 5, R.id.ut_5, R.id.ut_6_q, R.id.ut_6_holder);
                onEditTextListener(view, 6, R.id.ut_6);
                onRadioChange(view, 4, R.id.ut_4, R.id.ut_7_q, R.id.ut_7, R.id.ut_8_q, R.id.ut_8);
                onRadioChange(view, 7, R.id.ut_7, 0, 0);
                onRadioChange(view, 8, R.id.ut_8, R.id.ut_9_q, R.id.ut_9);
                onRadioChange(view, 9, R.id.ut_9, R.id.ut_10_q, R.id.ut_10_holder);
                onEditTextListener(view, 10, R.id.ut_10);
            }
        }
        else
        {
            dbm.close();
            setContentView(R.layout.activity_user_treatment);
            SharedPreferences sharedPref = getSharedPreferences("myPref", 0);
            patientID = sharedPref.getInt("patientID", 0);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            final View view = this.getWindow().getDecorView();

            onRadioChange(view, 1, R.id.ut_1, 0, 0);//5
            onRadioChange(view, 2, R.id.ut_2, R.id.ut_3_q, R.id.ut_3, R.id.ut_4_q, R.id.ut_4);//7
            onRadioChange(view, 3, R.id.ut_3, R.id.ut_5_q, R.id.ut_5);//5
            onRadioChange(view, 5, R.id.ut_5, R.id.ut_6_q, R.id.ut_6_holder);
            onEditTextListener(view, 6, R.id.ut_6);
            onRadioChange(view, 4, R.id.ut_4, R.id.ut_7_q, R.id.ut_7, R.id.ut_8_q, R.id.ut_8);
            onRadioChange(view, 7, R.id.ut_7, 0, 0);
            onRadioChange(view, 8, R.id.ut_8, R.id.ut_9_q, R.id.ut_9);
            onRadioChange(view, 9, R.id.ut_9, R.id.ut_10_q, R.id.ut_10_holder);
            onEditTextListener(view, 10, R.id.ut_10);
        }

    }

//5
    public void onRadioChange(final View view,int id, int radio, int next_question, int next_question_options){
        final View v = view;
        final int q_id = id;
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(radio);
        final int next_q = next_question;
        final int next_q_o = next_question_options;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("checkid",String.valueOf(checkedId));
                ut_response[q_id] = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                if (next_q != 0) {
                    View view1 = v.findViewById(next_q);
                    View view2 = v.findViewById(next_q_o);
                    if (((RadioButton) v.findViewById(checkedId)).getText().toString().equalsIgnoreCase("yes") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().equalsIgnoreCase("symptoms") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().contains("Allergy") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().contains("Six")) {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                    } else {
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        if(view2 instanceof RadioGroup){
                            ((RadioButton)((RadioGroup) view2).getChildAt(0)).setChecked(true);
                            /*(RadioButton)((RadioButton) view2).setChecked();*/
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
                ut_response[q_id] = String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
//7
    public void onRadioChange(final View view,int id, int radio, int next_question, int next_question_options, int next_question1, int next_question_options1){
        final View v = view;
        final int q_id = id;
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(radio);
        final int next_q = next_question;
        final int next_q_o = next_question_options;
        final int next_q1 = next_question1;
        final int next_q_o1 = next_question_options1;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ut_response[q_id] = ((RadioButton) v.findViewById(checkedId)).getText().toString();
                if (next_q != 0) {
                    View view1 = v.findViewById(next_q);
                    View view2 = v.findViewById(next_q_o);
                    View view3 = v.findViewById(next_q1);
                    View view4 = v.findViewById(next_q_o1);
                    if (((RadioButton) v.findViewById(checkedId)).getText().toString().equalsIgnoreCase("yes") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().equalsIgnoreCase("symptoms") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().contains("Allergy") |
                            ((RadioButton) v.findViewById(checkedId)).getText().toString().contains("Six")) {
                        view1.setVisibility(View.VISIBLE);
                        view2.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.VISIBLE);
                        view4.setVisibility(View.VISIBLE);
                    } else {
                        view1.setVisibility(View.GONE);
                        view2.setVisibility(View.GONE);
                        view3.setVisibility(View.GONE);
                        view4.setVisibility(View.GONE);
                    }


                }


            }
        });
    }
    public void onUserTreatmentSubmit(View view){
        int counter=0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        for(String i : ut_response){
            Log.i("ut_response","ut"+counter+++i);
        }
        DataBaseManager dbm =new DataBaseManager(this);
        dbm.open();
        boolean result=dbm.addUserTreatment(patientID, currentDateandTime,ut_response );
        dbm.close();
        if(result) {

            Toast.makeText(getApplicationContext(), "User Treatment details Inserted ",
                    Toast.LENGTH_SHORT).show();

            Intent mServiceIntent = new Intent(this, SendData.class);
            ///mServiceIntent.putExtra("KEY","https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php");
            startService(mServiceIntent);
            finish();

        }
        else
        {
            Toast.makeText(getApplicationContext(), "Internal database error ",
                    Toast.LENGTH_SHORT).show();
        }



    }
}
