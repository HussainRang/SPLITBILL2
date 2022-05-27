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

public class show_act_expense extends AppCompatActivity {

    Intent in;
    private int row_id;
    private int table_id;
    private String activity_name;
    private DataBaseHandler dbh;
    private LinearLayout ll;
    private TextView act_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_act_expense);

        in = getIntent();
        table_id = in.getIntExtra("Table_id",0);
        row_id = in.getIntExtra("RowId",0);
        activity_name = in.getStringExtra("Activity_name");

        dbh = new DataBaseHandler(this);

        ll = findViewById(R.id.to_add_name_paid_spent);
        act_name = findViewById(R.id.show_act_expense_act_show);
    }

    @Override
    protected void onStart() {
        super.onStart();


        act_name.setText(activity_name);
        act_name.setAllCaps(true);
        show_expenses();
    }

    private void show_expenses()
    {
        ArrayList frnd_names = dbh.get_friends_name(table_id);
        ArrayList pay_spent = dbh.send_row_activity(table_id,row_id,frnd_names.size());

        int frnd_index=0;
        int pay_spent_index=0;

        while(frnd_index<frnd_names.size())
        {
            LinearLayout ll_hor = new LinearLayout(this);
            LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ll_hor.setLayoutParams(params_lr_hor);
            ll_hor.setOrientation(LinearLayout.HORIZONTAL);


            TextView frnd_name = new TextView(this);
            frnd_name.setText((String)frnd_names.get(frnd_index));
            LinearLayout.LayoutParams params_tv1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1
            );
            frnd_name.setLayoutParams(params_tv1);
            frnd_name.setGravity(Gravity.CENTER_HORIZONTAL);
            frnd_name.setTextSize(20);
            frnd_index++;


            TextView paid = new TextView(this);
            paid.setText(""+pay_spent.get(pay_spent_index));
            LinearLayout.LayoutParams params_paid = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1
            );
            paid.setLayoutParams(params_paid);
            paid.setGravity(Gravity.CENTER_HORIZONTAL);
            paid.setTextSize(20);
            pay_spent_index++;


            TextView spent = new TextView(this);
            spent.setText(""+pay_spent.get(pay_spent_index));
            LinearLayout.LayoutParams params_spent = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1
            );
            spent.setLayoutParams(params_spent);
            spent.setGravity(Gravity.CENTER_HORIZONTAL);
            spent.setTextSize(20);
            pay_spent_index++;;


            ll_hor.addView(frnd_name);
            ll_hor.addView(paid);
            ll_hor.addView(spent);

            ll.addView(ll_hor);
        }
        
    }

    public void OK_PRESSED_SHOW_ACT_EXP(View view) {
        finish();
    }
}