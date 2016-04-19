package edu.clemson.eoe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class RegActivity extends AppCompatActivity {

    Button dobButton;
    int year_DOB, month_DOB, day_DOB;
    EditText patientName;
    RadioGroup gender;
    RadioButton radioSexButton;
    Spinner lenDisease;
    Spinner fathEducation;
    //EditText otherrace;
    Spinner mothEducation;
    Spinner famIncome;
    Spinner grade;
    Spinner race;
    Spinner ethnicity;
    SharedPreferences sharedPref;
    String raceName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPref = getSharedPreferences("myPref", 0);
        if (!sharedPref.getBoolean("my_first_time", true)) {
            Intent intent = new Intent(getApplicationContext(),Surveys.class);
            startActivity(intent);
            finish();
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Registration");

        //initializing DOB with today's values
        Calendar calendar = Calendar.getInstance();
        year_DOB= calendar.get(Calendar.YEAR);
        month_DOB= calendar.get(Calendar.MONTH)+1;
        day_DOB= calendar.get(Calendar.DAY_OF_MONTH);
        patientName =(EditText) findViewById(R.id.addtitle);
        lenDisease =(Spinner)findViewById(R.id.disease_Spinner);
        fathEducation =( Spinner) findViewById(R.id.fatheredu_Spinner);
        mothEducation =( Spinner) findViewById(R.id.motheredu_Spinner);
        famIncome =(Spinner)findViewById(R.id.familyInc_Spinner);
        gender=(RadioGroup) findViewById(R.id.gender);
        grade =(Spinner)findViewById(R.id.grade_Spinner);
        race =(Spinner)findViewById(R.id.Race_spinner);
        ethnicity =(Spinner)findViewById(R.id.ethinicity_Spinner);
        //otherrace =(EditText) findViewById(R.id.Reg_other);

        //DOB button
        dobButton = (Button) findViewById(R.id.DOBbutton);
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
                try {hideKeyboard(v);}catch (Exception e){}

            }

        });

        populateSpinner(R.id.Race_spinner, R.array.Race_a);
        populateSpinner(R.id.ethinicity_Spinner, R.array.ethnicity_a);
        populateSpinner(R.id.grade_Spinner,R.array.Grade_a);
        populateSpinner(R.id.disease_Spinner,R.array.disease_a);
        populateSpinner(R.id.familyInc_Spinner,R.array.householdIncome_a);
        populateSpinner(R.id.fatheredu_Spinner,R.array.fatherEdu_a);
        populateSpinner(R.id.motheredu_Spinner,R.array.motherEdu_a);
        onEditTextListener(R.id.Reg_other);

    }

    private void hideKeyboard(View v) {
        InputMethodManager inputManager =
                (InputMethodManager) getApplicationContext().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(
                this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void onEditTextListener(int reg_other) {
        final EditText editText = (EditText) findViewById(reg_other);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                raceName= String.valueOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void populateSpinner(final int spinId,int stringArrayId){
        //final View v = view;

        Spinner spinner = (Spinner) findViewById(spinId);//R.id.f1_spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                stringArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final String[] stringArray =getResources().getStringArray(stringArrayId);
        spinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {hideKeyboard(v);}catch (Exception e){}
                return false;
            }
        }) ;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                raceName = stringArray[position];
                if (spinId == R.id.Race_spinner) {
                    if (position == 5) {

                        findViewById(R.id.Reg_other_race).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.Reg_other_race).setVisibility(View.GONE);
                    }


                    Log.i("selected item", String.valueOf(position) + "and id is" + stringArray[position]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    /**
     * Creates a new dialog, here it is used to create a pop-up date picker dialog
     * @param id is 1 for the datepicker dialog
     * @return the datepicker dialog
     */
    @Override
    protected Dialog onCreateDialog(int id){
        if(id == 1){
            //Create a Datepicker Dialog on click of DOB button
            return new DatePickerDialog(this, dpListener, year_DOB,month_DOB-1,day_DOB );
        }
        return null;
    }
    //Assigns the selected date values to the declared variables
    protected DatePickerDialog.OnDateSetListener dpListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            year_DOB=year;
            month_DOB=monthOfYear+1; //By default month starts from January =0
            day_DOB=dayOfMonth;
            dobButton.setText("DOB : "+month_DOB+"/"+day_DOB+"/"+year_DOB);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reg, menu);

        return true;
    }
//test
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent intent = new Intent(this,About.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   /* class Background extends AsyncTask<String,String,String> {
        protected void OnPreExecute()
        {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... params) {


            String reg_url = "https://people.cs.clemson.edu/~sravira/Delete/db_getdeletelist.php";
            //String login_url = "http://10.0.2.2/webapp/login.php";
            String method = params[0];
            if (method.equals("register")) {
                String patientName = params[1];
                String gender = params[2];
                String date = params[3];
                String grade = params[4];
                String Ethnicity = params[5];
                String race = params[6];
                String LenDisease = params[7];
                String famIncome = params[8];
                String FathEduc = params[9];
                String MotherEduc = params[10];

                try {
                    StaticData staticData = StaticData.getInstance();

                    String data = "patientName" + "=" + URLEncoder.encode(patientName, "UTF-8") + "&" +
                            "gender"+ "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                            "date" + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                            "grade" + "=" + URLEncoder.encode(grade, "UTF-8") + "&" +
                            "Ethnicity" + "=" + URLEncoder.encode(Ethnicity, "UTF-8") + "&" +
                           "race" + "=" + URLEncoder.encode(race, "UTF-8") + "&" +
                            "LenDisease" + "=" + URLEncoder.encode(LenDisease, "UTF-8") + "&" +
                            "famIncome" + "=" + URLEncoder.encode(famIncome, "UTF-8") + "&" +
                            "FathEduc" + "=" + URLEncoder.encode(FathEduc, "UTF-8") + "&" +
                           "MotherEduc" + "=" + URLEncoder.encode(MotherEduc, "UTF-8");

                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray =new JSONArray();
                    jsonObject.put("pn",patientName);
                    jsonObject.put("gen",gender);
                    jsonArray.put(jsonObject);

                    String url = new String("https://people.cs.clemson.edu/~sravira/Viewing/dummmy.php");
                    *//*Thread loc = staticData.SyncWithExternal(StaticData.insertPatient+"?"+data,data);*//*
                    Thread loc = staticData.SyncWithExternal(url,jsonArray.toString());
                    loc.start();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
        @Override
        protected void onPreExecute() {

        }

    }*/

        public void print(String a){
            System.out.println(a);
        }

    //onRegister method to submit patient details
    public int OnRegister(View view) {
        Calendar calendar = Calendar.getInstance();

        if(patientName.length()==0)
        {
            patientName.requestFocus();
            patientName.setError("We need your name");
            return 0;
        }


        Log.i("Entered","Year: "+year_DOB+"month:"+month_DOB+"day:"+day_DOB);
        Log.i("ValidateWith","day_of_Y"+calendar.get(Calendar.DAY_OF_YEAR)+"Year: "+calendar.get(Calendar.YEAR)+"month:"+calendar.get(Calendar.MONTH)+"day:"+calendar.get(Calendar.DAY_OF_MONTH));

        if(year_DOB >=calendar.get(Calendar.YEAR) ) {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year_DOB, month_DOB-1, day_DOB);
            Log.i("ValidateWith","day_of_Y"+calendar1.get(Calendar.DAY_OF_YEAR)+ "Year: " + calendar1.get(Calendar.YEAR) + "month:" + calendar1.get(Calendar.MONTH) + "day:" + calendar1.get(Calendar.DAY_OF_MONTH));
            if (calendar.get(Calendar.DAY_OF_YEAR) < calendar1.get(Calendar.DAY_OF_YEAR)) {
                dobButton.requestFocus();
                Toast.makeText(getApplicationContext(), "You might've forgot to enter valid Date of Birth",
                        Toast.LENGTH_SHORT).show();
                showDialog(1);
                return 0;
            }
        }
         {

            // get selected radio button from radioGroup
            String gradeName= grade.getSelectedItem().toString();
            String ethnicityans=ethnicity.getSelectedItem().toString();
            if(race.getSelectedItemPosition()!=5)
           raceName = race.getSelectedItem().toString();
            String lenName=lenDisease.getSelectedItem().toString();
            String famInc=famIncome.getSelectedItem().toString();
            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
            String genderName = radioSexButton.getText().toString();
            String date =year_DOB+"-"+month_DOB+"-"+day_DOB;


        if(isOnline()) {

    //try to add user to EXT database
    SyncUser syncUser = new SyncUser();
    String result = new String();
    try {
        String encodedURL =  (getResources().getString(R.string.php_syncUser)+"?pn="+patientName.getText().toString()+"&grade="+
                grade.getSelectedItem().toString()+"&gender="+genderName+"&ethnicity="+ethnicity.getSelectedItem().toString()+"&race="+
                raceName+"&date="+date+"&lenD="+lenDisease.getSelectedItem().toString()+"&fInc="+famIncome.getSelectedItem().toString()+"&mEdu="+
                mothEducation.getSelectedItem().toString()+"&fEdu="+fathEducation.getSelectedItem().toString()) ;
        encodedURL = encodedURL.replaceAll("\\s","+");


        Log.i("Result_url",encodedURL);
        //if successful result contains patientId of new user
        result = syncUser.execute(encodedURL).get();
        Log.i("Result_of_sync",result);

    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }//check if php communication was successful

    if(result.isEmpty() || result.equalsIgnoreCase("Things didn't go expected")){
        Toast.makeText(RegActivity.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
        //check if there is a conflict with EXT database users
    }else if(result.equalsIgnoreCase("failed")){
        Toast.makeText(getApplicationContext(),"Error at the server end, please contact administrator",Toast.LENGTH_SHORT);

    }else { //insert into internal database

        /**
         * Changes the FIRST_RUN variable to false
         */

        if (sharedPref.getBoolean("my_first_time", true)) {
            sharedPref.edit().putBoolean("my_first_time", false).commit();
           sharedPref.edit().putInt("patientID", Integer.parseInt(result.toString())).commit();
        }
        DataBaseManager dbm =new DataBaseManager(getApplicationContext());
        dbm.open();
        boolean resultinternal= dbm.insertPatient(Integer.parseInt(result.toString()),patientName.getText().toString(), genderName, date,
                gradeName,ethnicityans,raceName,lenName,famInc,fathEducation.getSelectedItem().toString(),mothEducation.getSelectedItem().toString());
        dbm.close();



        Toast.makeText(getApplicationContext(), "You are now registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),Surveys.class);
        startActivity(intent);
        finish();
        //db.close();
    }



}else{//if device has not network
    Toast.makeText(getApplicationContext(), "Network unavailable! Try agaian", Toast.LENGTH_SHORT).show();
}
    return 1;
            //finish();
         /*
            }*/

        }


    }

    /**
     * Syncs user details or more appropriately registers user on external database
     */
    public class SyncUser extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                String result = new String("");

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1){

                    char current = (char) data;
                    result += current;

                    data = reader.read();

                }
                Log.i("result",result);
                return(result);

                //returns php output


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Things didn't go expected"; //if any error occurs in above execution


        }
    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
