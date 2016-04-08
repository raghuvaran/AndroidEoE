package edu.clemson.eoe;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/**
 * Acessing PHP files using Static data Class .All Php files are accessed using this class.
 *
 */
public class StaticData {
    ///Holds the currently active profile the user has selected
    static Context context;
    private static String activeProfile;
    ///Holds an instance of the class (used as singleton)
    private static final StaticData hold = new StaticData();

    private int synced = 0;//7 is all externally gathered, -1 is network error, 8 is synced


    public JSONArray lists;
    public JSONArray Items;
    public JSONArray units;
    public JSONArray catgeories;
    public  JSONArray deleteid;
    public  JSONArray deleteunit;
    public JSONArray deeletecategory;
  /*  public JSONArray geni;
    public JSONArray species;
    public JSONArray cultivar;
    public JSONArray beacons;*/
    public String updateTime;

  /*  public String[] lastSearch;
    public String[] lastLocalPlant;
    public String[] lastLocalLocation;
    public String[] pinLocation;*/

    private static final String viewPHP = "https://people.cs.clemson.edu/~sravira/Viewing/";
    public static final String getLists = viewPHP + "db_getalllists.php";
    public static final String getItems =    viewPHP + "db_getallitems.php";
    public static final String getUnits =    viewPHP + "db_getallunits.php";
    public static final String getCategories =  viewPHP + "db_getallcategories.php";
    public static final String getdeletelist="http://people.cs.clemson.edu/~sravira/Delete/db_getdeletelist.php";
    public static final String getdeleteunit="http://people.cs.clemson.edu/~sravira/Delete/db_getdeleteunit.php";
    public static final String getdeletecategory="http://people.cs.clemson.edu/~sravira/Delete/db_getdeletecategory.php";
    public static final String insertPatient="https://people.cs.clemson.edu/~sravira/Viewing/insertpatient.php";
    public static final String fb="https://www.facebook.com";

//    public static  final String aid=android.provider.Settings.Secure.getString(context.getContentResolver(),
  //  android.provider.Settings.Secure.ANDROID_ID);
    /*public static final String getGeni =      viewPHP + "GetGenus.php";
    public static final String getSpecies =   viewPHP + "GetSpecies.php";
    public static final String getCultivar =  viewPHP + "GetCultivar.php";
    public static final String getBeacons =  viewPHP + "GetBeacons.php";

    public static final String getPlantImage = viewPHP + "GetImageEchoTwo.php?ID=";*/
    public int isSynced(){ return synced; }
    public void resetSync(){synced = 0;}

    public static StaticData getInstance(){return hold;}

    /**
     * Makes and runs a Networker Thread using some link.
     *
     *
     * */
    public Networker SyncWithExternal(String link,String data){
        Networker thread = new Networker(link,data);
        return thread;
    }

    /**
     * Class for a Networker Thread
     *
     *  Used to communicate to external database php scripts.
     *
     *  Will set all the JsonArray objects inside staticData instance.
     *
     *
     *
     *
     *
     * */
    private class Networker extends Thread {
        String link;
        String datas;
        public Networker(Object parameter ,Object data) {
            link = parameter.toString();
            datas=data.toString();
        }
        /**
         * Goes through and syncs each internal database object.
         *
         * Catches it's own HTTPClient exceptions.
         * */
        public void run() {
            try {

                String ret = null;
                // Create the SSL connection

                Log.d("database", "Doing: " + this.link );
                HttpURLConnection c = null;
                URL u = new URL(this.link);
                String result = new String("");
                c = (HttpURLConnection) u.openConnection();


              /*  *//*SSLContext sc;
                sc = SSLContext.getInstance("TLS");
                sc.init(null, null, new java.security.SecureRandom());
                c.setSSLSocketFactory(sc.getSocketFactory());

                // Use this if you need SSL authentication
                String userpass = "user" + ":" + "password";
                String basicAuth = "Basic " + Base64.encodeToString(userpass.getBytes(), Base64.DEFAULT);
                c.setRequestProperty("Authorization", basicAuth);*//*
                //c.setFixedLengthStreamingMode(this.datas.getBytes().length);
               *//* OutputStream wrs = c.getOutputStream();
                wrs.write(this.datas.getBytes());
                wrs.flush();
                wrs.close();*/
                //int responseCode=conn.getResponseCode();
                c.setRequestMethod("POST");
               //* c.setRequestProperty("USER-AGENT", "Mozilla/5.0");*//*
              //  c.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                c.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                c.setDoOutput(true);
                c.setDoInput(true);
                //androidid idnew=new androidid();
                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(this.datas.getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                //c.setConnectTimeout(10000);
               // c.setReadTimeout(10000);
                //Send request

                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream());
                wr.writeBytes(this.datas);
                wr.flush();
                wr.close ();
                c.connect();

                int status = c.getResponseCode();

                if(status==200 || status==201) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) sb.append(line + "\n");
                    br.close();
                    ret = sb.toString();
                }

                String actual = ret.toString().replace("{\"success\":true,\"results\":", "");
                actual = actual.replace("]}", "]");
                Log.d("database", actual);

            }catch (Exception e){
                synced = -1;//Networking is not available
                Log.d("database", "Network send error: " + e.toString());
            }
        }//end run

        /**
         * Sends data to the specified URL

         * @param urlString is the php script URL
         * @return the reply from the php post execution
         */
      /*  protected String UploadToPhp (String acknowledge, String urlString){
            URL url;
            HttpURLConnection urlConnection = null;



            try {
                //Get URL from strings.xml
                url = new URL(urlString);
                //Open HTTP connection
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setReadTimeout( 10000 *//*milliseconds*//* );
                urlConnection.setConnectTimeout(15000 *//* milliseconds *//* );
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.setFixedLengthStreamingMode(acknowledge.getBytes().length);
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                urlConnection.setRequestProperty("X-Requested-With", "XMLHttpRequest");



                urlConnection.connect();

                OutputStream os = new BufferedOutputStream(urlConnection.getOutputStream());

                os.write(acknowledge.getBytes());
                //clean up
                os.flush();
                StringBuffer response = null;
                //InputStream is = urlConnection.getInputStream();
                int responseCode = urlConnection.getResponseCode();
                System.out.println("responseCode" + responseCode);
                switch (responseCode) {
                    case 200:
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String inputLine;
                        response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        Log.i("Json_replyFromEXTdb", response.toString());
                        return response.toString();

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }


            }
            return null;

        }*/

    }//end Networker
/*
    private void updateInternal(String sjson){
        try {
            JSONArray jsonArray = new JSONArray(sjson);
        } catch (JSONException e){
            Log.d("database", "Json Parse error "+ e.toString());
        }
    }
*/
}
