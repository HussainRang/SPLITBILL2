package com.example.splitbill2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Array;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SplitBill";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MAIN_TABLE = "CREATE TABLE IF NOT EXISTS SplitBillMain (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tripName TEXT," +
                "num_mem INT," +
                "Img_num INT)";
        db.execSQL(CREATE_MAIN_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS SplitBillMain");

        // Create tables again
        onCreate(db);
    }

    //To add entry in MAIN TABLE
    public void new_entry_MainTable(String TripName,int Members,int img_num)
    {
        // Instance of SQLiteDatabase use Database in writeable format
        SQLiteDatabase db = this.getWritableDatabase();

        // To put column values
        ContentValues ct = new ContentValues();

        ct.put("tripName",TripName);
        ct.put("num_mem",Members);
        ct.put("Img_num",img_num);
        //Table name
        String Table_Name = "SplitBillMain";
        //inserting values
        db.insert(Table_Name,null,ct);

        db.close();
    }

    public ArrayList get_all()
    {
        ArrayList arr = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cur = db.rawQuery("SELECT id,tripName,num_mem,Img_num FROM SplitBillMain",null);

        if(cur.moveToFirst())
        {
            do{
                arr.add(cur.getInt(0));
                arr.add(cur.getString(1));
                arr.add(cur.getInt(2));
                arr.add(cur.getInt(3));

            }while(cur.moveToNext());
        }

        cur.close();
        db.close();

        return arr;

    }

    // to get the max id of MAIN TABLE
    public int get_last_entered_id_main()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor id_cursor = db.rawQuery("SELECT max(id) FROM SplitBillMain", null);
        int final_id=0 ;

        if(id_cursor.moveToFirst())
        {
            do{
                final_id = id_cursor.getInt((0));
            }while(id_cursor.moveToNext());
        }

        db.close();
        return final_id;
    }

    public void delete_row(int id)
    {
        delete_table_friends(id);
        delete_table_activities(id);

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("SplitBillMain","id="+id,null);

        db.close();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //      FOR FRIENDS TABLES      //


    //To add entry in MAIN TABLE
    public void new_entry_Friends_table(int id,ArrayList arr) {
        // Instance of SQLiteDatabase use Database in writeable format
        SQLiteDatabase db = this.getWritableDatabase();

        //Table name
        String Table_Name = "friends_"+id;

        // To put column values

        for (int i = 0; i < arr.size(); i++)
        {
            ContentValues ct = new ContentValues();

            ct.put("friend_Name", (String) arr.get(i));
            ct.put("Paid",0);
            ct.put("Spent",0);

            //Inserting Values
            db.insert(Table_Name,null,ct);
        }

        db.close();
    }


    public ArrayList get_friends_details(int id)
    {
        ArrayList arr = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor details = db.rawQuery("Select friend_Name,Paid,Spent from friends_"+id, null);

        details.moveToFirst();
        while(!details.isAfterLast()) {

            String name = details.getString((0));
            int paid = details.getInt((1));
            int spent = details.getInt((2));

            arr.add(name);
            arr.add(paid);
            arr.add(spent);

            details.moveToNext();
        }
        db.close();
        return arr;
    }

    public ArrayList get_friends_name(int id)
    {
        ArrayList arr = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor details = db.rawQuery("Select friend_Name from friends_"+id, null);

        int i=0;
        if(details.moveToFirst())
            do{
                arr.add(details.getString((i)));
                i++;
            }while(details.moveToNext());

        db.close();
        return arr;
    }

    public void Update_friends_row(int table_id,int id,int paid,int spent,String friend_name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE friends_"+ table_id +
                " SET Paid = (Paid+"+paid+")," +
                "    Spent = (Spent+"+spent +") "+
                "WHERE" +
                "    id= "+id;

        db.execSQL(query);
        db.close();
    }

    public ArrayList get_friend_table_name_pay_spent(int table_id)
    {
        ArrayList arr = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor data = db.rawQuery("Select friend_Name,Paid,Spent from friends_"+table_id,null);


        if(data.moveToFirst())
            do{
                arr.add(data.getString(0));
                arr.add(data.getInt(1));
                arr.add(data.getInt(2));

            }while(data.moveToNext());

            data.close();
            return arr;
    }

    public void inc_to_giver(int give_id,int settle_amt,int table_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE friends_"+table_id+
                        " SET Paid = (Paid+"+settle_amt+") "+
                "WHERE id="+give_id;

        db.execSQL(query);
        db.close();
    }

    public void dec_from_taker(int take_id,int settle_amt,int table_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE friends_"+table_id+
                " SET Spent = (Spent-"+settle_amt+") "+
                "WHERE id="+take_id;

        db.execSQL(query);
        db.close();
    }

    // create FRIEND table as friends_id
    public void create_table_for_friends(int id,ArrayList arr)
    {
        String CREATE_FRIENDS_TABLE = "CREATE TABLE IF NOT EXISTS friends_"+id+" (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "friend_Name TEXT," +
                "Paid INT,"+
                "Spent INT)";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_FRIENDS_TABLE);
        new_entry_Friends_table(id,arr);

        db.close();
    }

    public void delete_table_friends(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS friends_"+id);
        db.close();
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //      FOR ACTIVITIES TABLES      //

    // TO create query for creating activity tables
    private String get_create_query_Activity(int id,ArrayList arr)
    {
        String s = "CREATE TABLE IF NOT EXISTS activities_"+id
                +" ( id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "activity_name TEXT," +
                "dtTm DATETIME";

        for(int i=0;i<arr.size();i++)
        {
            s = s+","+arr.get(i)+"_pay INT,"+arr.get(i)+"_spent INT";
        }

        s=s+")";

        return s;
    }

    //Creating activity tables
    public void create_table_for_activities(int id,ArrayList arr)
    {
        String CREATE_ACTIVITY_TABLE = get_create_query_Activity(id,arr);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(CREATE_ACTIVITY_TABLE);

        db.close();
    }

    public void add_activity_to_activity_table(int table_id, ArrayList arr, ArrayList<EditText> ed_text, String Activity_name)
    {
        // Instance of SQLiteDatabase use Database in writeable format
        SQLiteDatabase db = this.getWritableDatabase();

        //arr-->friend_names    ed_text-->editTexts

        //Table name
        String Table_Name = "activities_"+table_id;

        // To put column values
        int ed_text_id=0;

       // String query = "Insert into "+Table_Name+"(activity_name,dtTm ";



        ContentValues cv = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        cv.put("dtTm", dateFormat.format(date));
        cv.put("activity_name",Activity_name);

        int index_edtxt = 0;
        for(int i=0;i< arr.size();i++)
        {
            cv.put(arr.get(i)+"_pay",Integer.parseInt(ed_text.get(index_edtxt).getText().toString()));
            index_edtxt++;
            cv.put(arr.get(i)+"_spent",Integer.parseInt(ed_text.get(index_edtxt).getText().toString()));
            index_edtxt++;
        }

        db.insert(Table_Name,null,cv);
        db.close();
    }

    public int get_max_id_activity_table(int table_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor id_cursor = db.rawQuery("SELECT max(id) FROM activities_"+table_id, null);
        int final_id=0 ;

        if(id_cursor.moveToFirst())
            do{
                final_id = id_cursor.getInt((0));
            }while(id_cursor.moveToNext());

        db.close();
        return final_id;
    }

    public String getDate(int table_id,int max_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor id_cursor = db.rawQuery("SELECT DATE(dtTm) FROM activities_"+table_id+" WHERE id="+max_id, null);
        String date_time="";

        if(id_cursor.moveToFirst())
            do{
                date_time = (String)id_cursor.getString((0));
            }while(id_cursor.moveToNext());

        db.close();
        return date_time;
    }

    public String getTime(int table_id,int max_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor id_cursor = db.rawQuery("SELECT TIME(dtTm) FROM activities_"+table_id+" WHERE id="+max_id, null);
        String date_time="";

        if(id_cursor.moveToFirst())
            do{
                date_time = (String)id_cursor.getString((0));
            }while(id_cursor.moveToNext());

        db.close();
        return date_time;
    }

    public ArrayList send_row_activity(int table_id,int row_id,int num_frnd)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor id_cursor = db.rawQuery("SELECT * FROM activities_"+table_id+" WHERE id="+row_id, null);


        ArrayList arr=new ArrayList();

        int i=3;
        int last = 2+2*num_frnd;
        if(id_cursor.moveToFirst()) {
            do {
                while (i <= last) {
                    arr.add(id_cursor.getInt(i));
                    i++;
                }

            } while (id_cursor.moveToNext());
        }

        db.close();
        return arr;
    }

    public void delete_table_activities(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS activities_"+id);
        db.close();
    }

    public ArrayList get_Actname_date_time(int table_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList arr = new ArrayList();

        Cursor data = db.rawQuery("SELECT id,activity_name,TIME(dtTm),DATE(dtTm) from activities_"+table_id,null);

        if(data.moveToFirst())
        {
            do{
                arr.add(data.getInt(0));
                arr.add(data.getString(1));
                arr.add(data.getString(2));
                arr.add(data.getString(3));

            }while(data.moveToNext());
        }

        return arr;
    }
}
