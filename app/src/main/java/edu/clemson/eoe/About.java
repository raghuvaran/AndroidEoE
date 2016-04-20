package edu.clemson.eoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

public class About extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
        setupActionBar();
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        }
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public static class MyPreferenceFragment extends PreferenceFragment {
        PopupWindow popUp;
        LinearLayout layout;
        TextView tv;
        LinearLayout.LayoutParams params;
        ScrollView scroll;
        LinearLayout mainLayout;
        Button but;
        boolean click = true;
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.about);

            Preference myPref = (Preference) findPreference("Contact_key");
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    //open browser or intent here
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, "sravira@clemson.edu");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    intent.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                    startActivity(Intent.createChooser(intent, "Send Email"));
                    return true;
                }
            });

            Preference summary = (Preference) findPreference("Summary_key");

            summary.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {


                    final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                    dlgAlert.setMessage(getResources().getString(R.string.about_app));
                    dlgAlert.setTitle("Who needs this app?");
                    dlgAlert.setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                        /*popUp.showAtLocation(mainLayout, Gravity.CENTER, 100, 100);
                        popUp.update(50, 50, -1, -1);*/

                    return true;
                }
            });

            Preference acknowledgment = (Preference) findPreference("Acknowledgement_key");
            acknowledgment.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                    dlgAlert.setMessage(getResources().getString(R.string.ack_app));
                    dlgAlert.setTitle("Acknowledgements");
                    dlgAlert.setPositiveButton("THANKS", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    return true;
                }
            });

        }
    }
    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
