package edu.clemson.eoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * DataBaseManager class file to edit delete update and query the database
 */
public class DataBaseManager {
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "EoE.db";
    public static final String TABLE_NAME_USERINFO = "userInfo";
    public static final String TABLE_NAME_FOODDIARY = "foodDiary";
    public static final String TABLE_NAME_SYMPTOMS = "symptoms";
    public static final String TABLE_NAME_QOL = "QOL";



    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    //creating table LIST
    private static final String SQL_CREATE_USERINFO =
            "CREATE TABLE " + TABLE_NAME_USERINFO + " (" +
                    "patientID" + " INTEGER PRIMARY KEY autoincrement not null," +
                    "name" + TEXT_TYPE + COMMA_SEP +
                    "birthDate" + " Date" + COMMA_SEP +
                    "gender " + TEXT_TYPE + COMMA_SEP +
                    "grade " + TEXT_TYPE + COMMA_SEP +
                    "lengthOfDia "+ TEXT_TYPE + COMMA_SEP +
                    "income "+ TEXT_TYPE + COMMA_SEP +
                    "fatherEdu " + TEXT_TYPE + COMMA_SEP +
                    "motherEdu " + TEXT_TYPE + COMMA_SEP +
                    "race " + TEXT_TYPE + COMMA_SEP +
                    "ethnicity " + TEXT_TYPE + COMMA_SEP +
                    "whoIsInput " + TEXT_TYPE +

                    " )";
    //creating table Unit
    private static final String SQL_CREATE_FOODDIARY =
            "CREATE TABLE " + TABLE_NAME_FOODDIARY + " (" +
                    "foodDairyID" + " INTEGER PRIMARY KEY autoincrement not null," +
                    "user_patientid" + " INTEGER " + COMMA_SEP +
                    "time "  + TEXT_TYPE +COMMA_SEP +
                    "whichMeal " +TEXT_TYPE + COMMA_SEP +
                    "wherel " +TEXT_TYPE + COMMA_SEP +
                    "who " +TEXT_TYPE + COMMA_SEP +
                    "feelBefore " +TEXT_TYPE + COMMA_SEP +
                    "feelAfter " +TEXT_TYPE + COMMA_SEP +
                    "allergic " +TEXT_TYPE + COMMA_SEP +
                    "Image " +TEXT_TYPE +  COMMA_SEP +
                     "whoIsInput " + TEXT_TYPE + COMMA_SEP +
                    "updateStatus INTEGER " + COMMA_SEP +
                    "FOREIGN KEY(user_patientid) REFERENCES " +
                    TABLE_NAME_USERINFO + "(patientID)" +


                    " )";
    //initialzing table category
    private static final String SQL_CREATE_SYMPTOMS =
            "CREATE TABLE " + TABLE_NAME_SYMPTOMS + " (" +
                    "peessid" + " INTEGER PRIMARY KEY autoincrement not null," +
                    "user_patientid" + " INTEGER " + COMMA_SEP +
                    "time " + " Date " +COMMA_SEP +
                    "q1 " +TEXT_TYPE + COMMA_SEP +
                    "q2 " +TEXT_TYPE + COMMA_SEP +
                    "q3 " +TEXT_TYPE + COMMA_SEP +
                    "q4 " +TEXT_TYPE + COMMA_SEP +
                    "q5 " +TEXT_TYPE + COMMA_SEP +
                    "q6 " +TEXT_TYPE + COMMA_SEP +
                    "q7 " +TEXT_TYPE + COMMA_SEP +
                    "q8 " +TEXT_TYPE + COMMA_SEP +
                    "q9 " +TEXT_TYPE + COMMA_SEP +
                    "q10 " +TEXT_TYPE + COMMA_SEP +
                    "q11 " +TEXT_TYPE + COMMA_SEP +
                    "q12 " +TEXT_TYPE + COMMA_SEP +
                    "q13 " +TEXT_TYPE + COMMA_SEP +
                    "q14 " +TEXT_TYPE + COMMA_SEP +
                    "q15 " +TEXT_TYPE + COMMA_SEP +
                    "q16 " +TEXT_TYPE + COMMA_SEP +
                    "q17 " +TEXT_TYPE + COMMA_SEP +
                    "q18 " +TEXT_TYPE + COMMA_SEP +
                    "q19 " +TEXT_TYPE + COMMA_SEP +
                    "q20 " +TEXT_TYPE + COMMA_SEP +
                    "FOREIGN KEY(user_patientid) REFERENCES " +
                    TABLE_NAME_USERINFO + "(patientID)" +




                    " )";
    //initializing tbale item
    private static final String SQL_CREATE_QOL =
            "CREATE TABLE " + TABLE_NAME_QOL + " (" +
                    "pedsQlid" + " INTEGER PRIMARY KEY autoincrement not null," +
                    "user_patientid" + " INTEGER " + COMMA_SEP +
                    "time " + " Date " +COMMA_SEP +
                    "s1q1 " +" INTEGER " + COMMA_SEP +
                    "s1q2 " +" INTEGER " + COMMA_SEP +
                    "s1q3 " +" INTEGER " + COMMA_SEP +
                    "s1q4 " +" INTEGER " + COMMA_SEP +
                    "s1q5 " +" INTEGER " + COMMA_SEP +
                    "s1q6 " +" INTEGER " + COMMA_SEP +
                    "s2q1 " +" INTEGER " + COMMA_SEP +
                    "s2q2 " +" INTEGER " + COMMA_SEP +
                    "s2q3 " +" INTEGER " + COMMA_SEP +
                    "s2q4 " +" INTEGER " + COMMA_SEP +
                    "t1q1 " +" INTEGER " + COMMA_SEP +
                    "t1q2 " +" INTEGER " + COMMA_SEP +
                    "t1q3 " +" INTEGER " + COMMA_SEP +
                    "t1q4 " +" INTEGER " + COMMA_SEP +
                    "t1q5 " +" INTEGER " + COMMA_SEP +
                    "w1q1 " +" INTEGER " + COMMA_SEP +
                    "w2q2 " +" INTEGER " + COMMA_SEP +
                    "w3q3 " +" INTEGER " + COMMA_SEP +
                    "w4q4 " +" INTEGER " + COMMA_SEP +
                    "w5q5 " +" INTEGER " + COMMA_SEP +
                    "w6q6 " +" INTEGER " + COMMA_SEP +
                    "FOREIGN KEY(user_patientid) REFERENCES " +
                    TABLE_NAME_USERINFO + "(patientID)" +


                    " )";

    private static final String SQL_DELETE_ENTRIES_SYMPTOMS =
            "DROP TABLE IF EXISTS " + TABLE_NAME_SYMPTOMS;

    private static final String SQL_DELETE_ENTRIES_USERINFO =
            "DROP TABLE IF EXISTS " + TABLE_NAME_USERINFO;

    private static final String SQL_DELETE_ENTRIES_FOODDIARY =
            "DROP TABLE IF EXISTS " + TABLE_NAME_FOODDIARY;

    private static final String SQL_DELETE_ENTRIES_QOL =
            "DROP TABLE IF EXISTS " + TABLE_NAME_QOL;



    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    public DataBaseManager(Context ctx) {
        context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public static String DB_FILEPATH ;
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_USERINFO);
            db.execSQL(SQL_CREATE_FOODDIARY);
            db.execSQL(SQL_CREATE_QOL);
            db.execSQL(SQL_CREATE_SYMPTOMS);

            db.execSQL("PRAGMA foreign_keys=ON;");
        }

        /**
         * Upgrade Database version drop all tables and recreate
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // This database is only a cache for online data, so its upgrade policy is
           // to simply to discard the data and start over
            db.execSQL(SQL_DELETE_ENTRIES_USERINFO);
            db.execSQL(SQL_DELETE_ENTRIES_SYMPTOMS);

            db.execSQL(SQL_DELETE_ENTRIES_QOL);
            db.execSQL(SQL_DELETE_ENTRIES_FOODDIARY);

            onCreate(db);
        }

        /**
         * onDowngrade Method
         *
         * @param db
         * @param oldVersion
         * @param newVersion
         */
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        /**
         * onConfigure for setting foeign key constraints
         *
         * @param database
         */
        public void onConfigure(SQLiteDatabase database) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                database.setForeignKeyConstraintsEnabled(true);
            } else {
                database.execSQL("PRAGMA foreign_keys=ON");
            }
        }
    }

    public DataBaseManager open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        db.beginTransactionNonExclusive();
        return this;
    }

    public void close() {
        db.setTransactionSuccessful();
        db.endTransaction();
        DBHelper.close();


    }

//Method to insert pateint details into userinfo table
    public boolean insertPatient(int PatientID,String PatientName,String genderName,String Date,String Gradename,
                                 String ethnicityans,String Racename, String lenDisease,String FamIncome,
                                 String FathEducation,String MothEducation) {

        // mDbHelper = new DBHelper.FeedReaderDbHelper(this);
        // Gets the data repository in write mode

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(DBHelper.COLUMN_NAME_LISTID, id);
        values.put("patientID",PatientID);
        values.put("name", PatientName);
        values.put("birthDate", Date);
        values.put("gender", genderName);
        values.put("grade",Gradename);
        values.put("ethnicity",ethnicityans);
        values.put("race",Racename);
        values.put("lengthOfDia",lenDisease);
        values.put("income",FamIncome);
        values.put("fatherEdu",FathEducation);
        values.put("motherEdu",MothEducation);

        //    values.put("UpdateStatus",0);
        // ContentValues initialValues = new ContentValues();
        //initialValues.put("date_created", dateFormat.format(date));
        // values.put(DBHelper.COLUMN_NAME_CONTENT, content);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TABLE_NAME_USERINFO,
                null,
                values);

        if (newRowId == -1)
            return false;
        else
            return true;
        //
        //

    }



    public String uploadFile(final String source, int itemID) {
        String ret="0";

        String fileName = source;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        //Separators for the post data
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1024 * 1024;
        File sourceFile = new File(source);

        try {
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            //The php script
            URL url = new URL("https://people.cs.clemson.edu/~sravira/Viewing/saveImage.php");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);

            //Boundary
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            //$_Post['id']
            dos.writeBytes("Content-Disposition: form-data; name=\"id\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(itemID + "");
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            //$_FILE['uploaded_file']
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            //Boundary
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            int serverResponseCode = conn.getResponseCode();
            //Good return
            if (serverResponseCode == 200 || serverResponseCode==201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = br.readLine();
                //The php script either returns a url or 0 if the upload failed
                ret=line;
            }

            fileInputStream.close();
            dos.flush();
            dos.close();

            //Return the result to our activity
            return ret;

        } catch (MalformedURLException ex) {
            return ret;
        } catch (final Exception e) {
            return ret;
        }
    }
    private SQLiteStatement FoodDiaryStatement = null;
    public boolean addFoodDiary(int patientID,String DateTime,String meal,
                             String where,String who ,String feelBefore,
                             String feelAfter,String allergic,String image,String inputPerson){

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(DBHelper.COLUMN_NAME_LISTID, id);
        values.put("user_patientid", patientID);
        values.put("time", DateTime);
        values.put("whichMeal", meal);
        values.put("wherel",where);
        values.put("who",who);
        values.put("feelBefore", feelBefore);
        values.put("feelAfter", feelAfter);
        values.put("allergic", allergic);
        values.put("Image", image);
        values.put("updateStatus",0);
        values.put("whoIsInput",inputPerson);
       // values.put("motherEdu", MothEducation);

        //    values.put("UpdateStatus",0);
        // ContentValues initialValues = new ContentValues();
        //initialValues.put("date_created", dateFormat.format(date));
        // values.put(DBHelper.COLUMN_NAME_CONTENT, content);

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                TABLE_NAME_FOODDIARY,
                null,
                values);

        return newRowId != -1;
        //
        //
    }

    /**
     * Get list of Users from SQLite DB as Array List
     * @return
     */
    public ArrayList<HashMap<String, String>> getAllDiaries() {
        ArrayList<HashMap<String, String>> DiaryList;
        DiaryList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM foodDiary";
      //  SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("foodDairyID",cursor.getString(0));
                map.put("user_patientid", cursor.getString(1));
                map.put("time", cursor.getString(2));
                map.put("whichMeal", cursor.getString(3));
                map.put("wherel", cursor.getString(4));
                map.put("who", cursor.getString(5));
                map.put("time", cursor.getString(6));
                map.put("feelBefore", cursor.getString(7));
                map.put("feelAfter", cursor.getString(8));
                map.put("allergic", cursor.getString(9));
                map.put("Image", cursor.getString(10));
                DiaryList.add(map);
            } while (cursor.moveToNext());
        }
      //  database.close();
        return DiaryList;
    }

    /**
     * Compose JSON out of SQLite records
     * @return
     */
    public String composeJSONfromSQLite(){
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM foodDiary where updateStatus = 0";
      //  SQLiteDatabase database = this.getWritableDatabase();
        JSONArray resultSet     = new JSONArray();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                    try {
                      int  Column_type = cursor.getType(i);
                        if(Column_type==4)
                        {
                            byte[] byteArray = cursor.getBlob(i);

                          //  Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                           String img=  Base64.encodeToString(byteArray,Base64.DEFAULT);
                            rowObject.put(cursor.getColumnName(i), img);
                            Log.d("TAG_NAME", img);
                        }
                        else if (cursor.getString(i) != null) {
                            Log.d("TAG_NAME", cursor.getString(i));

                            rowObject.put(cursor.getColumnName(i), cursor.getString(i));
                        }
                        else{rowObject.put(cursor.getColumnName(i), "");}
                    } catch (Exception e) {
                        Log.d("TAG_NAME", e.getMessage());
                    }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet.toString();
    }


    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync neededn";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM foodDiary where updateStatus = 0";
       // SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        count = cursor.getCount();
        //database.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(int id, int status){
       // SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update foodDiary set updateStatus = "+status+" where foodDairyID="+id +"";
        Log.d("query", updateQuery);
        db.execSQL(updateQuery);
        //database.close();
    }






}
