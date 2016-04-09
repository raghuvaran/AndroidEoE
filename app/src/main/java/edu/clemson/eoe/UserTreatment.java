package edu.clemson.eoe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UserTreatment extends AppCompatActivity {

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
        setContentView(R.layout.activity_user_treatment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final View view = this.getWindow().getDecorView();

        onRadioChange(view,1,R.id.ut_1,0,0);
        onRadioChange(view,2,R.id.ut_2,R.id.ut_3_q,R.id.ut_3,R.id.ut_4_q,R.id.ut_4);
        onRadioChange(view,3,R.id.ut_3,R.id.ut_5_q,R.id.ut_5);
        onRadioChange(view, 5, R.id.ut_5, R.id.ut_6_q, R.id.ut_6_holder);
        onEditTextListener(view, 6, R.id.ut_6);
        onRadioChange(view,4, R.id.ut_4, R.id.ut_7_q, R.id.ut_7,R.id.ut_8_q, R.id.ut_8);
        onRadioChange(view,7, R.id.ut_7, 0, 0);
        onRadioChange(view,8,R.id.ut_8,R.id.ut_9_q,R.id.ut_9);
        onRadioChange(view,9,R.id.ut_9,R.id.ut_10_q,R.id.ut_10_holder);
        onEditTextListener(view,10,R.id.ut_10);

    }


    public void onRadioChange(final View view,int id, int radio, int next_question, int next_question_options){
        final View v = view;
        final int q_id = id;
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(radio);
        final int next_q = next_question;
        final int next_q_o = next_question_options;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
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
        int counter = 0;
        for(String i : ut_response){
            Log.i("ut_response","ut"+counter+++i);
        }
    }
}
