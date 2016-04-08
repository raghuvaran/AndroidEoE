package edu.clemson.eoe;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by santh on 4/6/2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx;
    AlertDialog alertDialog;
    BackgroundTask(Context ctx)
    {
        this.ctx=ctx;
    }


    @Override
    protected String doInBackground(String... params) {
        String reg_url = "https://people.cs.clemson.edu/~sravira/EoE/insertpatient.php";
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
                URL url = new URL(reg_url);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("POST");
                c.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");
                //httpURLConnection.setDoOutput(true);
                //httpURLConnection.setDoInput(true);
                //OutputStream OS = httpURLConnection.getOutputStream();
                //BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("patientName", "UTF-8") + "=" + URLEncoder.encode(patientName, "UTF-8") + "&" +
                        URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                        URLEncoder.encode("Grade", "UTF-8") + "=" + URLEncoder.encode(Grade, "UTF-8") + "&" +
                        URLEncoder.encode("Ethnicity", "UTF-8") + "=" + URLEncoder.encode(Ethnicity, "UTF-8") + "&" +
                        URLEncoder.encode("Race", "UTF-8") + "=" + URLEncoder.encode(Race, "UTF-8") + "&" +
                        URLEncoder.encode("LenDisease", "UTF-8") + "=" + URLEncoder.encode(LenDisease, "UTF-8") + "&" +
                        URLEncoder.encode("FamIncome", "UTF-8") + "=" + URLEncoder.encode(FamIncome, "UTF-8") + "&" +
                        URLEncoder.encode("FathEduc", "UTF-8") + "=" + URLEncoder.encode(FathEduc, "UTF-8") + "&" +
                        URLEncoder.encode("MotherEduc", "UTF-8") + "=" + URLEncoder.encode(MotherEduc, "UTF-8");
                c.setRequestProperty("Content-Length", "" +
                        Integer.toString(data.getBytes().length));
                c.setRequestProperty("Content-Language", "en-US");
                //c.setRequestProperty("Content-length", "0");
                c.setUseCaches(false);
                c.setAllowUserInteraction(false);
                c.setConnectTimeout(10000);
                c.setReadTimeout(10000);
                c.connect();
                //Create JSONObject here
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("ID", "25");
                jsonParam.put("description", "Real");
                jsonParam.put("enable", "true");

                //Send request
                DataOutputStream wr = new DataOutputStream(
                        c.getOutputStream ());
                wr.writeBytes(data);
                wr.flush();
                wr.close ();

                int status = c.getResponseCode();
                if(status==200 || status==201)
                {
                    return "Registration Success...";
                }

               // / bufferedWriter.write(data);
                //bufferedWriter.flush();
                //bufferedWriter.close();
                //OS.close();
                //InputStream IS = httpURLConnection.getInputStream();
                //IS.close();
                //httpURLConnection.connect();
                //httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
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
        if(result.equals("Registration Success..."))
        {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Information....");
    }

}
