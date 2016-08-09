package edu.clemson.eoe;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SendData extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_SYMPTOMS = "http://rchowda.people.clemson.edu/eoe_php/insertSymptoms.php";
    private static final String ACTION_FOODDIARY = "http://rchowda.people.clemson.edu/eoe_php/insertfoodDiary.php";
    private static final String ACTION_QOL = "http://rchowda.people.clemson.edu/eoe_php/insertQol.php";
    private static final String ACTION_UT="http://rchowda.people.clemson.edu/eoe_php/insertUT.php";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "edu.clemson.eoe.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "edu.clemson.eoe.extra.PARAM2";
    public static boolean isIntentServiceRunning = false;

    public SendData() {
        super("SendData");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionSymptoms(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SendData.class);
        intent.setAction(ACTION_SYMPTOMS);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, SendData.class);
       // intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(!isIntentServiceRunning) {
            isIntentServiceRunning = true;
        }
        Log.i("Intent",""+isIntentServiceRunning);
        if (intent != null) {
           // final String action = intent.getStringExtra("KEY");


                try {
                    Log.i("Sync Started","Sync");
                    onSymptomsSync(ACTION_SYMPTOMS);
                    onFoodDiarySync(ACTION_FOODDIARY);
                    onQOLSync(ACTION_QOL);
                    onUTSync(ACTION_UT);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            catch (NullPointerException e)
            {
                e.printStackTrace();
            }




            }
        }


    private void onSymptomsSync(String url) throws ExecutionException, InterruptedException, JSONException,NullPointerException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncSymptomsStatus();
            dbm.close();


            if (status.equals("DB Sync neededn")) {
               // String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertSymptoms.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONSymptomsfromSQLite();
                dbmSync.close();
                JSONArray response = send(url, Jsondata);
                if (response.toString().equals(null)) {
                   Log.i("Response",response.toString());
                    // Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
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
            Log.i("no Internet","no");
            //Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }

    private void onQOLSync(String url) throws ExecutionException, InterruptedException, JSONException,NullPointerException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncQolStatus();

            dbm.close();


            if (status.equals("DB Sync neededn")) {
                //String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertQol.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONQolfromSQLite();
                dbmSync.close();
                JSONArray response = send(url, Jsondata);
                if (response.toString().equals(null)) {
                   // Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }

    protected JSONArray send(String url ,String data) {

            String ret = null;
            JSONArray  foodDiary;

            try {

                // Create the SSL connection

                Log.d("database", "Doing: " + url);
                HttpURLConnection c = null;
                URL u = new URL(url);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();

                // String data = "Jsondata" + "=" + URLEncoder.encode(params[1], "UTF-8");

                c.setRequestMethod("POST");

                c.setRequestProperty("Content-Type",
                        "application/json; charset=UTF-8");
                c.setDoOutput(true);
                c.setDoInput(true);

                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(data.getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
               // c.setConnectTimeout(10000);
              //  c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(data);
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

    public void onFoodDiarySync(String url) throws ExecutionException, InterruptedException, JSONException,NullPointerException {


        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncStatus();

            dbm.close();


            if (status.equals("DB Sync neededn")) {
             //   String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertfoodDiary.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONfromSQLite();
                dbmSync.close();

                    JSONArray response = send(url, Jsondata);

                if (response.toString().equals(null)) {
                    //Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
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
                        ImageUpload(ExtId,internalphotopath);
                        //new ImageUploader(ExtId).execute(internalphotopath);

                    }
                }
            }
        }
        else
        {
            //Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }
    }

    protected String ImageUpload (int id,String inturl) {
            DataBaseManager db = new DataBaseManager(getApplicationContext());
            db.open();
            //All of my server code is located in DBAdapter, go to the method uploadFile to see how
            //to finish uploading an image
            String result=db.uploadFile(inturl, id);
            db.close();
            return result;

        }

    private void onUTSync(String url) throws ExecutionException, InterruptedException, JSONException,NullPointerException {
        if (isOnline()) {
            DataBaseManager dbm = new DataBaseManager(this);
            dbm.open();
            String status = dbm.getSyncUTStatus();

            dbm.close();
           // SyncUT syncut = new SyncUT();

            if (status.equals("DB Sync neededn")) {
               // String url = "https://people.cs.clemson.edu/~sravira/Viewing/insertUT.php";
                DataBaseManager dbmSync = new DataBaseManager(this);
                dbmSync.open();
                String Jsondata = dbmSync.composeJSONUTfromSQLite();
                dbmSync.close();
                JSONArray response = send(url, Jsondata);
                if (response.toString().equals(null)) {
                    //Toast.makeText(Surveys.this, "Unable to establish connection. Try again!", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(Surveys.this, "Please enable internet", Toast.LENGTH_SHORT).show();
        }

    }




    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void onDestroy ()
    {
        isIntentServiceRunning=false;
        Log.i("Intent",""+isIntentServiceRunning);
    }
}
