package com.example.splitbill2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        show_expenses();
    }

    private void show_expenses()
    {
        ArrayList frnd_names = dbh.get_friends_name(table_id);
        ArrayList pay_spent = dbh.send_row_activity(table_id,row_id);

        int frnd_index=0;
        int pay_spent_index=0;

        while(frnd_index<frnd_names.size())
        {
            LinearLayout ll_hor = new LinearLayout(this);
            LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ll_hor.setLayoutParams(params_lr_hor);
            ll_hor.setOrientation(LinearLayout.HORIZONTAL);

            TextView frnd_name = new TextView(this);
            frnd_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
            frnd_name.setText(""+(String)frnd_names.get(frnd_index));
            frnd_name.setTextSize(15);

            TextView frnd_pay = new TextView(this);
            frnd_pay.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            frnd_pay.setText(""+pay_spent.get(pay_spent_index));
            frnd_pay.setTextSize(15);
            pay_spent_index++;

            TextView frnd_spent = new TextView(this);
            frnd_spent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            frnd_spent.setText(""+pay_spent.get(pay_spent_index));
            frnd_spent.setTextSize(15);
            pay_spent_index++;

            ll_hor.addView(frnd_name);
            ll_hor.addView(frnd_pay);
            ll_hor.addView(frnd_spent);

            ll.addView(ll_hor);

            frnd_index++;
        }
        
    }

    public void OK_PRESSED_SHOW_ACT_EXP(View view) {
        finish();
    }
}