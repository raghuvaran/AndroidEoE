package edu.clemson.eoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * DataBaseManager class file to edit delete update and query the database
 */
public class DataBaseManager {
    public static final int DATABASE_VERSION = 1;
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
                    "time " + " Date " +COMMA_SEP +
                    "whichMeal " +TEXT_TYPE + COMMA_SEP +
                    "wherel " +TEXT_TYPE + COMMA_SEP +
                    "who " +TEXT_TYPE + COMMA_SEP +
                    "feelBefore " +TEXT_TYPE + COMMA_SEP +
                    "feelAfter " +TEXT_TYPE + COMMA_SEP +
                    "allergic " +TEXT_TYPE + COMMA_SEP +
                    "Image " +TEXT_TYPE + COMMA_SEP +
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
    public boolean insertPatient(String PatientName,String genderName,String Date) {

        // mDbHelper = new DBHelper.FeedReaderDbHelper(this);
        // Gets the data repository in write mode

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        //values.put(DBHelper.COLUMN_NAME_LISTID, id);
        values.put("name", PatientName);
        values.put("birthDate", Date);
        values.put("gender", genderName);


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



}
