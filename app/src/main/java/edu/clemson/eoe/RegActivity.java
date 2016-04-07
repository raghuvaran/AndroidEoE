package edu.clemson.eoe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class RegActivity extends AppCompatActivity {

    Button dobButton;
    int year_DOB=0, month_DOB=0, day_DOB=0;
    EditText patientName;
    RadioGroup gender;
    RadioButton radioSexButton;
    EditText lenDisease;
    EditText fathEducation;
    EditText mothEducation;
    EditText famIncome;
    Spinner grade;
    Spinner race;
    Spinner ethnicity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initializing DOB with today's values
        Calendar calendar = Calendar.getInstance();
        year_DOB= calendar.get(Calendar.YEAR);
        month_DOB= calendar.get(Calendar.MONTH);
        day_DOB= calendar.get(Calendar.DAY_OF_MONTH);
        patientName =(EditText) findViewById(R.id.addtitle);
        lenDisease =(EditText) findViewById(R.id.disease);
        fathEducation =(EditText) findViewById(R.id.fatheredu);
        mothEducation =(EditText) findViewById(R.id.motheredu);
        famIncome =(EditText) findViewById(R.id.familyInc);
        gender=(RadioGroup) findViewById(R.id.gender);
        grade =(Spinner)findViewById(R.id.grade_Spinner);
        race =(Spinner)findViewById(R.id.Race_spinner);
        ethnicity =(Spinner)findViewById(R.id.ethinicity_Spinner);
        //DOB button
        dobButton = (Button) findViewById(R.id.DOBbutton);
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
            }
        });

        populateSpinner(R.id.Race_spinner, R.array.Race_a);

        populateSpinner(R.id.ethinicity_Spinner, R.array.ethnicity_a);
        populateSpinner(R.id.grade_Spinner,R.array.Grade_a);

    }

    public void populateSpinner(int spinId,int stringArrayId){
        Spinner spinner = (Spinner) findViewById(spinId);//R.id.f1_spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                stringArrayId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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
            return new DatePickerDialog(this, dpListener, year_DOB,month_DOB,day_DOB );
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

  /*  public void onRegister(View v){
        Intent intent = new Intent(this, SurveySelect.class);
        startActivity(intent);
    }*/

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
        if (id == R.id.action_settings) {
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



    //onRegister method to submit patient details
    public void OnRegister(View view) {
        Calendar calendar = Calendar.getInstance();

        if(patientName.length()==0)
        {
            patientName.requestFocus();
            patientName.setError("FIELD CANNOT BE EMPTY");
        }
        else if(famIncome.length()==0)
        {
            famIncome.requestFocus();
            famIncome.setError("FIELD CANNOT BE EMPTY");
        }
        else if(lenDisease.length()==0)
        {
            lenDisease.requestFocus();
            lenDisease.setError("FIELD CANNOT BE EMPTY");
        }
        else if(fathEducation.length()==0)
        {
            fathEducation.requestFocus();
            fathEducation.setError("FIELD CANNOT BE EMPTY");
        }
        else if(mothEducation.length()==0)
        {
            mothEducation.requestFocus();
            mothEducation.setError("FIELD CANNOT BE EMPTY");
        }

        else if(year_DOB>=calendar.get(Calendar.YEAR)&& month_DOB>=calendar.get(Calendar.MONTH )+1 && day_DOB >=calendar.get(Calendar.DAY_OF_MONTH))
        {
            dobButton.requestFocus();
            Toast.makeText(getApplicationContext(), "Please Enter valid Date of Birth",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // get selected radio button from radioGroup
            String gradeName= grade.getSelectedItem().toString();
            String ethnicityans=ethnicity.getSelectedItem().toString();
            String raceName = race.getSelectedItem().toString();
            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
            String genderName = radioSexButton.getText().toString();
            String date =year_DOB+"-"+month_DOB+"-"+day_DOB;


if(isOnline()) {
    /*new Background().execute(method, patientName.getText().toString(), genderName, date,
            gradeName, ethnicityans, raceName, lenDisease.getText().toString(), famIncome.getText().toString(),
            fathEducation.getText().toString(), mothEducation.getText().toString());*/
    //try to add user to EXT database
    SyncUser syncUser = new SyncUser();
    String result = new String();
    try {
        Log.i("Result_url",getResources().getString(R.string.php_syncUser)+"?pn="+patientName.getText().toString()+"&grade="+
                grade.getSelectedItem().toString()+"&gender="+genderName+"&ethnicity="+ethnicity.getSelectedItem().toString()+"&race="+
                race.getSelectedItem().toString()+"&date="+date+"&lenD="+lenDisease.getText().toString()+"&fInc="+famIncome.getText().toString()+"&mEdu="+
                mothEducation.getText().toString()+"&fEdu="+fathEducation.getText().toString());
        //if successful result contains userid of new user
        result = syncUser.execute(getResources().getString(R.string.php_syncUser)+"?pn="+patientName.getText().toString()+"&grade="+
                grade.getSelectedItem().toString()+"&gender="+genderName+"&ethnicity="+ethnicity.getSelectedItem().toString()+"&race="+
                race.getSelectedItem().toString()+"&date="+date+"&lenD="+lenDisease.getText().toString()+"&fInc="+famIncome.getText().toString()+"&mEdu="+
                mothEducation.getText().toString()+"&fEdu="+fathEducation.getText().toString()).get();
        Log.i("Result_of_sync",result);
        Intent intent = new Intent(getApplicationContext(),Surveys.class);
        startActivity(intent);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    }//check if php communication was successful
    if(result.isEmpty() || result.equalsIgnoreCase("Things didn't go expected")){
        Toast.makeText(RegActivity.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
        //check if there is a conflict with EXT database users
    }



}

            //finish();
         /*   DataBaseManager dbm =new DataBaseManager(getApplicationContext());
            dbm.open();
           boolean result= dbm.insertPatient(patientName.getText().toString(), genderName, date, gradeName,ethnicityans,raceName,lenDisease.getText().toString(),famIncome.getText().toString(),fathEducation.getText().toString(),mothEducation.getText().toString());
            dbm.close();
            if(result){

                Toast.makeText(getApplicationContext(), "Patient details inserted ",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Surveys.class);
                startActivity(intent);
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
