package com.example.splitbill2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class add_activity extends AppCompatActivity {

    private int btn_num;
    Button act_0;
    Button act_1;
    Button act_2;
    Button act_3;
    Button act_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        act_0 = findViewById(R.id.btn_0_status);
        act_1 = findViewById(R.id.btn_1_status);
        act_2 = findViewById(R.id.btn_2_status);
        act_3 = findViewById(R.id.btn_3_status);
        act_4 = findViewById(R.id.btn_4_status);



        Context context;
        context=this;
        try {
            activity_add = getIntent();
            table_id = activity_add.getIntExtra("Table_id", 0);
            Toast.makeText(context, "ID:"+table_id+" Add activity", Toast.LENGTH_SHORT).show();
            act_name =(EditText) findViewById(R.id.Activity_name);


        }catch (Exception e) {
            Toast.makeText(this, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*try {
            friends_name = dbh.get_friends_name(table_id);
            Toast.makeText(this, "" + friends_name.get(0), Toast.LENGTH_SHORT).show();
        }catch(Exception e)
        {
            Toast.makeText(this, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }*/
        btn_num=0;
        get_edit();



    }

    EditText act_name ;
    Intent activity_add;
    private int table_id;
    private ArrayList friends_name;
    private ArrayList<EditText> arr_et;
    public DataBaseHandler dbh = new DataBaseHandler(this);


    public void get_edit()
    {

        arr_et = new ArrayList<EditText>();

        friends_name = dbh.get_friends_name(table_id);

        try {

            EditText act_name = findViewById(R.id.Activity_name);
        }catch(Exception e)
        {
            Toast.makeText(this, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }


        LinearLayout ll = findViewById(R.id.ll_add_activity_main);

        for(int i=0;i<friends_name.size();i++)
        {
            LinearLayout ll_hor = new LinearLayout(this);
            ll_hor.setOrientation(LinearLayout.HORIZONTAL);
            ll_hor.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            TextView tv_name = new TextView(this);
            tv_name.setText(""+friends_name.get(i));
            tv_name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            //ll.addView(tv_name);

            EditText et_paid = new EditText(this);
            et_paid.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            et_paid.setText("0");
            et_paid.setTextColor(Color.parseColor("#000000"));
            et_paid.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
            et_paid.setInputType(InputType.TYPE_CLASS_NUMBER);
            //ll.addView(et_paid);
            arr_et.add(et_paid);

            EditText et_spent = new EditText(this);
            et_spent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            et_spent.setText("0");
            et_spent.setTextColor(Color.parseColor("#000000"));
            et_spent.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
            et_spent.setInputType(InputType.TYPE_CLASS_NUMBER);
            //ll_hor.addView(et_paid);
            arr_et.add(et_spent);

            ll_hor.addView(tv_name);
            ll_hor.addView(et_paid);
            ll_hor.addView(et_spent);
            ll.addView(ll_hor);
        }
    }

    public Boolean check_true()
    {

        
        for(int i=0;i<arr_et.size();i++)
            if(arr_et.get(i).getText().toString().trim().equals(""))
                return false;

        if(act_name.getText().toString().trim().equals(""))
            return false;

        return true;
    }

    private Boolean check_pay_spent(ArrayList<EditText> arr)
    {
        int p=0;
        int s=0;

        for(int i=0;i<arr.size();i++)
        {
            if(i%2==0)
            {
                p = p + Integer.parseInt(arr.get(i).getText().toString());
            }
            else
            {
                s = s + Integer.parseInt(arr.get(i).getText().toString());
            }
        }

        if(p==s)
            return true;
        else
            return false;
    }


    public void DONE_PRESSED(View view)
    {
        if(check_true() && check_pay_spent(arr_et))
        {
            //Update the friends table
            int i=0;
            int id=0;
            while(i<arr_et.size())
            {
                int paid = Integer.parseInt(arr_et.get(i).getText().toString());
                i=i+1;
                int spent = Integer.parseInt(arr_et.get(i).getText().toString());
                i=i+1;

                dbh.Update_friends_row(table_id,id+1,paid,spent, (String) friends_name.get(id));
                id = id+1;
            }

            //enter the activity in activity table
            dbh.add_activity_to_activity_table(table_id,friends_name,arr_et,act_name.getText().toString().trim(),btn_num);

            try {
                //send activity name(act_name) final id of activity table time and date
                int max_id = dbh.get_max_id_activity_table(table_id);
                Toast.makeText(this, dbh.getDate(table_id,max_id)+"   "+dbh.getTime(table_id,max_id), Toast.LENGTH_SHORT).show();
                Intent result_intent = new Intent();
                result_intent.putExtra("Activity_name", act_name.getText().toString().trim());
                result_intent.putExtra("Max_id", max_id);
                result_intent.putExtra("Date", dbh.getDate(table_id, max_id));
                result_intent.putExtra("Time", dbh.getTime(table_id, max_id));
                result_intent.putExtra("Btn_num",btn_num);
                setResult(RESULT_OK, result_intent);
                finish();
            }catch(Exception e) {
                Toast.makeText(this, "Exception"+e, Toast.LENGTH_SHORT).show();
            }
            }

        else
        {
            Toast.makeText(this, "Please Enter correct enteries", Toast.LENGTH_SHORT).show();
        }
    }


    public void Btn_0_pressed(View view) {

        btn_num=0;
        act_0.setBackgroundColor(Color.parseColor("#4361ee"));
        act_1.setBackgroundColor(Color.parseColor("#028090"));
        act_2.setBackgroundColor(Color.parseColor("#028090"));
        act_3.setBackgroundColor(Color.parseColor("#028090"));
        act_4.setBackgroundColor(Color.parseColor("#028090"));
    }

    public void Btn_1_pressed(View view) {

        btn_num=1;
        act_0.setBackgroundColor(Color.parseColor("#028090"));
        act_1.setBackgroundColor(Color.parseColor("#4361ee"));
        act_2.setBackgroundColor(Color.parseColor("#028090"));
        act_3.setBackgroundColor(Color.parseColor("#028090"));
        act_4.setBackgroundColor(Color.parseColor("#028090"));

    }

    public void Btn_2_pressed(View view) {

        btn_num=2;
        act_0.setBackgroundColor(Color.parseColor("#028090"));
        act_1.setBackgroundColor(Color.parseColor("#028090"));
        act_2.setBackgroundColor(Color.parseColor("#4361ee"));
        act_3.setBackgroundColor(Color.parseColor("#028090"));
        act_4.setBackgroundColor(Color.parseColor("#028090"));

    }

    public void Btn_3_pressed(View view) {

        btn_num=3;
        act_0.setBackgroundColor(Color.parseColor("#028090"));
        act_1.setBackgroundColor(Color.parseColor("#028090"));
        act_2.setBackgroundColor(Color.parseColor("#028090"));
        act_3.setBackgroundColor(Color.parseColor("#4361ee"));
        act_4.setBackgroundColor(Color.parseColor("#028090"));

    }

    public void Btn_4_pressed(View view) {

        btn_num=4;
        act_0.setBackgroundColor(Color.parseColor("#028090"));
        act_1.setBackgroundColor(Color.parseColor("#028090"));
        act_2.setBackgroundColor(Color.parseColor("#028090"));
        act_3.setBackgroundColor(Color.parseColor("#028090"));
        act_4.setBackgroundColor(Color.parseColor("#4361ee"));

    }
}