package edu.clemson.eoe;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by santh on 3/29/2016.
 */
public class UpdateReceiver extends BroadcastReceiver {

    public static boolean connection =false;

    @Override
    public void onReceive(Context context, Intent intent) {


        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        if (isConnected) {
            Log.i("NET", "connecte" + isConnected);
            connection=true;
        }
        else{

            Log.i("NET", "not connecte" +isConnected);
            connection=false;
        }
    }


 }



