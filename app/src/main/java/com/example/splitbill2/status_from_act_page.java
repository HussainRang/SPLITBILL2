package com.example.splitbill2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class status_from_act_page extends AppCompatActivity {

    Intent in;
    int table_id;
    LinearLayout ll;
    String Group_Name;
    DataBaseHandler dbh;
    TextView Main_grp_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_from_act_page);

        in=getIntent();
        table_id = in.getIntExtra("Table_id",0);
        Group_Name = in.getStringExtra("Group_name");


        dbh = new DataBaseHandler(this);

        ll=findViewById(R.id.linear_layout_status);
        Main_grp_name = findViewById(R.id.Group_name_status_page);
        Main_grp_name.setText(Group_Name);
        Main_grp_name.setAllCaps(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

        load_status();
    }

    private void load_status()
    {
        ArrayList data = dbh.get_friend_table_name_pay_spent(table_id);
        System.out.println(data.size());
        int i=0;

        while(i<data.size())
        {
            LinearLayout ll_hor = new LinearLayout(this);
            LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ll_hor.setLayoutParams(params_lr_hor);
            ll_hor.setOrientation(LinearLayout.HORIZONTAL);

            TextView frnd_name = new TextView(this);
            frnd_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
            frnd_name.setText(""+(String)data.get(i));
            frnd_name.setGravity(Gravity.CENTER_HORIZONTAL);
            frnd_name.setTextSize(15);
            i++;

            TextView frnd_pay = new TextView(this);
            frnd_pay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            frnd_pay.setText(""+data.get(i));
            frnd_pay.setGravity(Gravity.CENTER_HORIZONTAL);
            frnd_pay.setTextSize(15);
            i++;

            TextView frnd_spent = new TextView(this);
            frnd_spent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            frnd_spent.setText(""+data.get(i));
            frnd_spent.setGravity(Gravity.CENTER_HORIZONTAL);
            frnd_spent.setTextSize(15);
            i++;

            ll_hor.addView(frnd_name);
            ll_hor.addView(frnd_pay);
            ll_hor.addView(frnd_spent);

            ll.addView(ll_hor);

        }
    }


    public void OK_from_status(View view) {
        finish();
    }
}