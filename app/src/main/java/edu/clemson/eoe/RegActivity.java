package edu.clemson.eoe;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class RegActivity extends AppCompatActivity {

    Button dobButton;
    int year_DOB=0, month_DOB=0, day_DOB=0;
    EditText PatientName;
    RadioGroup gender;
    RadioButton radioSexButton;
    EditText lenDisease;
    EditText FathEducation;
    EditText MothEducation;
    EditText FamIncome;
    Spinner Grade;
    Spinner Race;
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
        PatientName =(EditText) findViewById(R.id.addtitle);
        lenDisease =(EditText) findViewById(R.id.disease);
        FathEducation =(EditText) findViewById(R.id.fatheredu);
        MothEducation =(EditText) findViewById(R.id.motheredu);
        FamIncome =(EditText) findViewById(R.id.familyInc);
        gender=(RadioGroup) findViewById(R.id.gender);
        Grade =(Spinner)findViewById(R.id.grade_Spinner);
        Race =(Spinner)findViewById(R.id.Race_spinner);
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
    class Background extends AsyncTask<String,String,String> {
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
                String Grade = params[4];
                String Ethnicity = params[5];
                String Race = params[6];
                String LenDisease = params[7];
                String FamIncome = params[8];
                String FathEduc = params[9];
                String MotherEduc = params[10];

                try {
                    StaticData staticData = StaticData.getInstance();

                    String data = "patientName" + "=" + URLEncoder.encode(patientName, "UTF-8") + "&" +
                            "gender"+ "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                            "date" + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                            "Grade" + "=" + URLEncoder.encode(Grade, "UTF-8") + "&" +
                            "Ethnicity" + "=" + URLEncoder.encode(Ethnicity, "UTF-8") + "&" +
                           "Race" + "=" + URLEncoder.encode(Race, "UTF-8") + "&" +
                            "LenDisease" + "=" + URLEncoder.encode(LenDisease, "UTF-8") + "&" +
                            "FamIncome" + "=" + URLEncoder.encode(FamIncome, "UTF-8") + "&" +
                            "FathEduc" + "=" + URLEncoder.encode(FathEduc, "UTF-8") + "&" +
                           "MotherEduc" + "=" + URLEncoder.encode(MotherEduc, "UTF-8");

                    JSONObject jsonObject = new JSONObject();
                    JSONArray jsonArray =new JSONArray();
                    jsonObject.put("pn",patientName);
                    jsonObject.put("gen",gender);
                    jsonArray.put(jsonObject);

                    String url = new String("https://people.cs.clemson.edu/~sravira/Viewing/dummmy.php");
                    /*Thread loc = staticData.SyncWithExternal(StaticData.insertPatient+"?"+data,data);*/
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

    }



    //onRegister method to submit patient details
    public void OnRegister(View view) {
        Calendar calendar = Calendar.getInstance();

        if(PatientName.length()==0)
        {
            PatientName.requestFocus();
            PatientName.setError("FIELD CANNOT BE EMPTY");
        }
        else if(FamIncome.length()==0)
        {
            FamIncome.requestFocus();
            FamIncome.setError("FIELD CANNOT BE EMPTY");
        }
        else if(lenDisease.length()==0)
        {
            lenDisease.requestFocus();
            lenDisease.setError("FIELD CANNOT BE EMPTY");
        }
        else if(FathEducation.length()==0)
        {
            FathEducation.requestFocus();
            FathEducation.setError("FIELD CANNOT BE EMPTY");
        }
        else if(MothEducation.length()==0)
        {
            MothEducation.requestFocus();
            MothEducation.setError("FIELD CANNOT BE EMPTY");
        }

        else if(year_DOB>=calendar.get(Calendar.YEAR)&& month_DOB>=calendar.get(Calendar.MONTH )+1 && day_DOB >=calendar.get(Calendar.DAY_OF_MONTH))
        {
            dobButton.requestFocus();
            Toast.makeText(getApplicationContext(), "Please Enter valid Date of Birth",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // get selected radio button from radioGroup
            int selectedId = gender.getCheckedRadioButtonId();
            String Gradename=Grade.getSelectedItem().toString();
            String ethnicityans=ethnicity.getSelectedItem().toString();
            String Racename =Race.getSelectedItem().toString();
            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(selectedId);
            String genderName = radioSexButton.getText().toString();
            String date =month_DOB+"/"+day_DOB+"/"+year_DOB;

            String method="register";

if(isOnline()) {
    new Background().execute(method, PatientName.getText().toString(), genderName, date,
            Gradename, ethnicityans, Racename, lenDisease.getText().toString(), FamIncome.getText().toString(),
            FathEducation.getText().toString(), MothEducation.getText().toString());
}

            //finish();
         /*   DataBaseManager dbm =new DataBaseManager(getApplicationContext());
            dbm.open();
           boolean result= dbm.insertPatient(PatientName.getText().toString(), genderName, date, Gradename,ethnicityans,Racename,lenDisease.getText().toString(),FamIncome.getText().toString(),FathEducation.getText().toString(),MothEducation.getText().toString());
            dbm.close();
            if(result){

                Toast.makeText(getApplicationContext(), "Patient details inserted ",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Surveys.class);
                startActivity(intent);
            }*/

        }


    }
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
