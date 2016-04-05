package edu.clemson.eoe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class Surveys extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_surveys, menu);
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

        return super.onOptionsItemSelected(item);
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
        private static final int[] symptoms_f = new int[12];
        static {
            symptoms_f[1]=R.string.S1_f;
            symptoms_f[2]=R.string.s2_f;
            symptoms_f[3]=R.string.s3_f;
            symptoms_f[4]=R.string.s4_f;
            symptoms_f[5]=R.string.s5_f;
            symptoms_f[6]=R.string.s6_f;
            symptoms_f[7]=R.string.s7_f;
            symptoms_f[8]=R.string.s8_f;
            symptoms_f[9]=R.string.s9_f;
            symptoms_f[10]=R.string.s10_f;
            symptoms_f[11]=R.string.s11_f;

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
            if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
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

        /**
         * OnCreate method for Symptoms layout
         * @param inflater
         * @param container
         * @param savedInstanceState
         * @return
         */
        public View onCreateSymptoms(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState){
            View rootView = inflater.inflate(R.layout.symptoms_survey, container, false);
            setRatingBarListener(rootView,R.id.s1_ratingBar,R.id.q1_res,freqResponse);
            setRatingBarListener(rootView,R.id.s2_ratingBar,R.id.q2_res,severeResponse);
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
                    //Camera to capture food item
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            });

            populateSpinner(rootView, R.id.f1_spinner, R.array.f1_a);
            populateSpinner(rootView, R.id.f2_spinner, R.array.f2_a);
            return rootView;
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
            View rootView = inflater.inflate(R.layout.symptoms_survey, container, false);
            setRatingBarListener(rootView, R.id.s1_ratingBar, R.id.q1_res, freqResponse);
            setRatingBarListener(rootView, R.id.s2_ratingBar, R.id.q2_res, severeResponse);
            return rootView;
        }
//--------------onCreate-Methods-Terminate-------------------//
//---------------Methods-Begin-------------------//
        /**
         * sets the response to the textview {@q_res} from the above declared static {@hashmap}
         * when user changes the rating in ratingbar {@q}
         * @param v
         * @param q
         * @param q_res
         * @param hashMap
         */
        public void setRatingBarListener(View v,int q, int q_res, final HashMap hashMap){
            final RatingBar q1 = (RatingBar) v.findViewById(q);
            final TextView q1_res = (TextView) v.findViewById(q_res);

            q1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    //Toast.makeText(getContext(), "Changed ratings to " + rating, Toast.LENGTH_SHORT).show();
                    q1_res.setText(hashMap.get(rating).toString());
                    q1_res.setVisibility(View.VISIBLE);
                }
            });
        }

        /**
         * Populates the spinner of {@spinId} with {@stingArrayId}
         * @param view
         * @param spinId
         * @param stringArrayId
         */
        public void populateSpinner(View view,int spinId,int stringArrayId){
            Spinner spinner = (Spinner) view.findViewById(spinId);//R.id.f1_spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                    stringArrayId, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);


        }

//---------------Methods-Terminate-------------------//
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
