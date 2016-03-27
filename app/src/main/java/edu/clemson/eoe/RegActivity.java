package edu.clemson.eoe;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class RegActivity extends AppCompatActivity {

    Button dobButton;
    int year_DOB=0, month_DOB=0, day_DOB=0;
    EditText PatientName;
    RadioGroup gender;
    RadioButton radioSexButton;


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
        gender=(RadioGroup) findViewById(R.id.gender);

        //DOB button
        dobButton = (Button) findViewById(R.id.DOBbutton);
        dobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(1);
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

    //onRegister method to submit patient details
    public void OnRegister(View view) {
        Calendar calendar = Calendar.getInstance();

        if(PatientName.length()==0)
        {
            PatientName.requestFocus();
            PatientName.setError("FIELD CANNOT BE EMPTY");
        }

        else if(year_DOB==calendar.get(Calendar.YEAR)&& month_DOB==calendar.get(Calendar.MONTH)&& day_DOB==calendar.get(Calendar.DAY_OF_MONTH))
        {
            dobButton.requestFocus();
            Toast.makeText(getApplicationContext(), "Please Enter valid Date of Birth",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // get selected radio button from radioGroup
            int selectedId = gender.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            radioSexButton = (RadioButton) findViewById(selectedId);
            String genderName = radioSexButton.getText().toString();
            String date =month_DOB+"/"+day_DOB+"/"+year_DOB;
            DataBaseManager dbm =new DataBaseManager(getApplicationContext());
          dbm.open();
            dbm.insertPatient(PatientName.getText().toString(), genderName, date);
            dbm.close();
            Toast.makeText(getApplicationContext(), "Patient details inserted ",
                    Toast.LENGTH_SHORT).show();

        }




    }


}
