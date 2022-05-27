package com.example.splitbill2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class status_from_act_page extends AppCompatActivity {

    Intent in;
    int table_id;
    LinearLayout ll;
    DataBaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_from_act_page);

        in=getIntent();
        table_id = in.getIntExtra("Table_id",0);
        dbh = new DataBaseHandler(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        load_status();
    }

    private void load_status()
    {
        ArrayList data = dbh.get_friend_table_name_pay_spent(table_id);

    }
}