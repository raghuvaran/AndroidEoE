package edu.clemson.eoe;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import java.util.Calendar;

/**
 *
 * Preference settings for Item .Includes Sorting Items alphabetically and by category and hiding purchased items.
 */
public class Settings extends AppCompatPreferenceActivity  {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pref_with_actionbar);
        //android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(com.example.santh.alarmtest.R.id.toolbar);
        //setSupportActionBar(toolbar);


        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsItemFragment())
                .commit();

    }

    /*protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar bar;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            LinearLayout root = (LinearLayout) findViewById(android.R.id.list).getParent().getParent().getParent();
            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.pref_with_actionbar, root, false);
            root.addView(bar, 0); // insert at top
        } else {
            ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
            ListView content = (ListView) root.getChildAt(0);

            root.removeAllViews();

            bar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.pref_with_actionbar, root, false);


            int height;
            TypedValue tv = new TypedValue();
            if (getTheme().resolveAttribute(R.attr.actionBarSize, tv, true)) {
                height = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }else{
                height = bar.getHeight();
            }

            content.setPadding(0, height, 0, 0);

            root.addView(content);
            root.addView(bar);
        }

        bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }*/



    /**
     * Fragement Activity class for setting preferences and generating toasts
     */
    public static class SettingsItemFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        final int BreakFastTimer_ID=1;
        final int LunchTImer_ID = 2;
        final int DinnerTImer_ID=3;
        final int SymptomsTimer_ID=4;
        final int QolTimer_ID=5;
        Preference Reminder1;
        Preference Reminder2;
        Preference Reminder3;
        Preference ReminderSymptoms;
        Preference ReminderQol;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);

            onSharedPreferenceChanged(null, "");
            //Instantiating all preferences
            CheckBoxPreference checkBoxPreference=(CheckBoxPreference)findPreference("Reminder 1");
            CheckBoxPreference checkBoxPreference1=(CheckBoxPreference)findPreference("Reminder 2");
            CheckBoxPreference checkBoxPreference2=(CheckBoxPreference)findPreference("Reminder 3");
            CheckBoxPreference SymptomsCheck=(CheckBoxPreference)findPreference("Reminder Symptoms");
            CheckBoxPreference QolCheck=(CheckBoxPreference)findPreference("Reminder QOL");
            //Preference Reminder1=(Preference) findPreference("Reminder1_Key");

             Reminder1=(Preference) findPreference("Reminder1_Key");
            /**
             * on timer change event  for Breakfast
             */
            Reminder1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String time =preference.getSummary().toString();
                    Long alarmtime= Long.valueOf(newValue.toString());
                    // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

                    //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
                    scheduleNotification(getNotification(preference.getTitle().toString()), time,BreakFastTimer_ID);
                    return  true;
                }

            });


            Reminder2=(Preference) findPreference("Reminder2_Key");
            /**
             * on timer change event  for Lunch
             */
            Reminder2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String time =preference.getSummary().toString();
                    Long alarmtime= Long.valueOf(newValue.toString());
                    // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

                    //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
                    scheduleNotification(getNotification(preference.getTitle().toString()), time,LunchTImer_ID);
                    return  true;
                }

            });

            Reminder3=(Preference) findPreference("Reminder3_Key");
            /**
             * on timer change event  for DInner
             */
            Reminder3.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String time =preference.getSummary().toString();
                    Long alarmtime= Long.valueOf(newValue.toString());
                    // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

                    //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
                    scheduleNotification(getNotification(preference.getTitle().toString()), time,DinnerTImer_ID);
                    return  true;
                }

            });

            ReminderSymptoms =(Preference) findPreference("Symptoms_Key");
            /**
             * on timer change event  for Symptoms
             */
            ReminderSymptoms.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String time =preference.getSummary().toString();
                    Long alarmtime= Long.valueOf(newValue.toString());
                    // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

                    //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
                    scheduleNotification(getNotification(preference.getTitle().toString()), time,SymptomsTimer_ID);
                    return  true;
                }

            });

            ReminderQol =(Preference) findPreference("PedsQL_Key");
            /**
             * on timer change event  for Qol
             */
            ReminderQol.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String time =preference.getSummary().toString();
                    Long alarmtime= Long.valueOf(newValue.toString());
                    // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

                    //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
                    scheduleNotification(getNotification(preference.getTitle().toString()), time,QolTimer_ID);
                    return  true;
                }

            });

            /**
             * Diable or enable timer based on Checkbox checked
             */

            if(checkBoxPreference.isChecked()==true)
            {
                Reminder1.setEnabled(true);
                setBreakFastReminder();

            }
            else
            {
                Reminder1.setEnabled(false);
                cancelBreakfastReminder();

            }
            if(checkBoxPreference1.isChecked()==true)
            {
                Reminder2.setEnabled(true);
                setLunchReminder();

            }
            else
            {
                Reminder2.setEnabled(false);
                cancelLunchReminder();
            }
            if(checkBoxPreference2.isChecked()==true)
            {
                Reminder3.setEnabled(true);
                setDinnerReminder();

            }
            else
            {
                Reminder3.setEnabled(false);
                cancelDinnerReminder();
            }

            if(SymptomsCheck.isChecked()==true)
            {
                ReminderSymptoms.setEnabled(true);
                setSymptomsReminder();
            }
            else
            {
                ReminderSymptoms.setEnabled(false);
                cancelSymptomsReminder();
            }

            if(QolCheck.isChecked()==true)
            {
                ReminderQol.setEnabled(true);
                setQolReminder();
            }
            else
            {
                ReminderQol.setEnabled(false);
                cancelQolReminder();
            }



           /* Reminder1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
            {
               public boolean onPreferenceChange(Preference preference, Object newValue) {
                   String alarmtime=newValue.toString();
                   //scheduleNotification(getNotification("5 second delay"), alarmtime);
                   return  true;
               }


                });*/
            /**
             * Disable or enable the Reminders based on checkbox changed
             */

            checkBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        Reminder1.setEnabled(true);
                        setBreakFastReminder();
                        Toast.makeText(getActivity(), "Reminder 1  is enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Reminder1.setEnabled(false);
                        cancelBreakfastReminder();
                        Toast.makeText(getActivity(), "Reminder 1 is disabled",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
            checkBoxPreference1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        Reminder2.setEnabled(true);
                        setLunchReminder();
                        Toast.makeText(getActivity(), "Reminder 2 is enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Reminder2.setEnabled(false);
                        cancelLunchReminder();
                        Toast.makeText(getActivity(), "Reminder 2 is disabled",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            checkBoxPreference2.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        Reminder3.setEnabled(true);
                        setDinnerReminder();
                        Toast.makeText(getActivity(), "Reminder 3  is enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Reminder3.setEnabled(false);
                        cancelDinnerReminder();
                        Toast.makeText(getActivity(), "Reminder 3 is disabled",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            SymptomsCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        ReminderSymptoms.setEnabled(true);
                        setSymptomsReminder();
                        Toast.makeText(getActivity(), "Reminder Symptoms  is enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ReminderSymptoms.setEnabled(false);
                        cancelSymptomsReminder();
                        Toast.makeText(getActivity(), "Reminder Symptoms is disabled",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });

            QolCheck.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        ReminderQol.setEnabled(true);
                        setQolReminder();
                        Toast.makeText(getActivity(), "Reminder Qol  is enabled",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        ReminderQol.setEnabled(false);
                        cancelQolReminder();
                        Toast.makeText(getActivity(), "Reminder Qol is disabled",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });



        }

        /**
         * MEthods to cancel REMINDERS
         */
        public void cancelBreakfastReminder()
        {
            Intent intent = new Intent(this.getActivity(), NotificationPublisher.class);
            PendingIntent sender = PendingIntent.getBroadcast(this.getActivity(), BreakFastTimer_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }
        public void cancelLunchReminder()
        {
            Intent intent = new Intent(this.getActivity(), NotificationPublisher.class);
            PendingIntent sender = PendingIntent.getBroadcast(this.getActivity(), LunchTImer_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }

        public void cancelDinnerReminder()
        {
            Intent intent = new Intent(this.getActivity(), NotificationPublisher.class);
            PendingIntent sender = PendingIntent.getBroadcast(this.getActivity(), DinnerTImer_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }

        public void cancelSymptomsReminder()
        {
            Intent intent = new Intent(this.getActivity(), NotificationPublisher.class);
            PendingIntent sender = PendingIntent.getBroadcast(this.getActivity(), SymptomsTimer_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }

        public void cancelQolReminder()
        {
            Intent intent = new Intent(this.getActivity(), NotificationPublisher.class);
            PendingIntent sender = PendingIntent.getBroadcast(this.getActivity(), QolTimer_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        }

        /**
         * Methods to set alarm
         */
        public void setBreakFastReminder()
        {
            String time =Reminder1.getSummary().toString();
           // Long alarmtime= Long.valueOf(newValue.toString());
            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

            //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
            scheduleNotification(getNotification(Reminder1.getTitle().toString()), time, BreakFastTimer_ID);
        }
        public void setLunchReminder()
        {
            String time =Reminder2.getSummary().toString();
            // Long alarmtime= Long.valueOf(newValue.toString());
            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

            //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
            scheduleNotification(getNotification(Reminder2.getTitle().toString()), time,LunchTImer_ID);
        }
        public void setDinnerReminder()
        {
            String time =Reminder3.getSummary().toString();
            // Long alarmtime= Long.valueOf(newValue.toString());
            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

            //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
            scheduleNotification(getNotification(Reminder3.getTitle().toString()), time, DinnerTImer_ID);
        }

        public void setSymptomsReminder()
        {
            String time =ReminderSymptoms.getSummary().toString();
            // Long alarmtime= Long.valueOf(newValue.toString());
            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

            //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
            scheduleNotification(getNotification(ReminderSymptoms.getTitle().toString()), time, SymptomsTimer_ID);
        }

        public void setQolReminder()
        {
            String time =ReminderQol.getSummary().toString();
            // Long alarmtime= Long.valueOf(newValue.toString());
            // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.);

            //Long alarm1=prefs.getLong("Reminder1_Key", 0L);
            scheduleNotification(getNotification(ReminderQol.getTitle().toString()), time, QolTimer_ID);
        }


        public  void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        }

        public void onResume() {
            super.onResume();
            // Set up a listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
        }

        @Override
        public void onPause() {
            super.onPause();
            // Unregister the listener whenever a key changes
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
        }

        /**
         * Method to schedule notification at specified time
         * @param notification
         * @param delay
         * @param NotificationID
         */
        private void scheduleNotification(Notification notification, String delay,int NotificationID) {

            String[] time = delay.split ( ":" );
            String [] AMPM=delay.split(" ");
            int hour = Integer.parseInt(time[0].trim());
            String [] minute=time[1].split(" ");

            int min = Integer.parseInt(minute[0].trim());
            String APM=AMPM[1].trim();
            int setap;
            if(APM.equals("PM"))
            {
                setap=1;
            }
            else
            {
                setap=0;
            }

            Intent notificationIntent = new Intent(this.getActivity(), NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, NotificationID);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getActivity(), NotificationID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            long _alarm = 0;
            Calendar now = Calendar.getInstance();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.AM_PM, setap);
           // calendar.add(Calendar.DAY_OF_MONTH, 1);
            if(calendar.getTimeInMillis() <= now.getTimeInMillis())
                _alarm = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
            else
                _alarm = calendar.getTimeInMillis();


          //
            //long futureInMillis = SystemClock.elapsedRealtime() + delay;
            AlarmManager alarmManager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _alarm,AlarmManager.INTERVAL_DAY, pendingIntent);
        }


        /**
         * Build notification content
         * @param content
         * @return
         */
        private Notification getNotification(String content) {
            Notification.Builder builder = new Notification.Builder(this.getActivity());
            builder.setContentTitle("Food Diary -EoE");
            builder.setContentText(content);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            Intent resultIntent = new Intent(this.getActivity(), Settings.class);
            // The stack builder object will contain an artificial back stack for the
            // started Activity.
            // This ensures that navigating backward from the Activity leads out of
            // your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.getActivity());
            // Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(Settings.class);
            // Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(alarmSound);
            builder.setWhen(0);
            builder.setContentIntent(resultPendingIntent);

            return builder.build();

        }


    }




    /**
     *
     * onBackPressed Method to redirect to correct intent activity
     */


}
