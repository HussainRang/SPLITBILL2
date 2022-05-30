package com.example.splitbill2;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;

import java.util.ArrayList;

public class Add_Members_Activity extends AppCompatActivity {

    DataBaseHandler dbh;
    EditText grpName;
    Intent AddMember;
    int img_num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_members);

        AddMember = getIntent();
        String random = AddMember.getStringExtra("ABC");
        //dbh = AddMember.getParcelableExtra("Database_Handler");

        grpName=(EditText) findViewById(R.id.grp_Name);
        dbh = new DataBaseHandler(this);
    }


    private ArrayList arrlst = new ArrayList();
    private ArrayList<EditText> et_lst = new ArrayList();
    private int id = 0;


    public void ADD_MEMBER_button_click(View view)
    {
        // if the EditText is empty then do not get new EditText
        if(et_lst.size()!=0 && et_lst.get(et_lst.size() - 1).getText().toString().trim().equals(""))
        {
            Toast.makeText(this, "Enter name in last EditText", Toast.LENGTH_SHORT).show();
        }

        else
        {
            addMembers();
        }
    }


    public void addMembers()
    {
        //Taking Linear Layout by id
        LinearLayout ll_main = findViewById(R.id.Linear_layout_add_edit_text);

        //Creating Frame
        FrameLayout fr = new FrameLayout(this);
        //layout params for frames
        FrameLayout.LayoutParams params_fr = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        fr.setLayoutParams(params_fr);
        //frames cannot be clicked
        fr.setClickable(false);
        fr.setId(id);
        id++;

        LinearLayout ll_hor = new LinearLayout(this);
        //setting layout parameters
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll_hor.setLayoutParams(params_lr_hor);
        //setting orientation as horizontal
        ll_hor.setOrientation(LinearLayout.HORIZONTAL);
        ll_hor.setId(id);
        id++;

        //Creating edit text
        EditText et = new EditText(this);
        et.setGravity(Gravity.LEFT);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        et.setLayoutParams(p);
        et.setHint("Member Name");
        et.setTextColor(Color.parseColor("#000000"));
        et.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        //Adding EditText to et_lst
        et_lst.add(et);
        et.setId(id);
        id++;


        //Creating Space
        Space sp = new Space(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT));
        sp.getLayoutParams().height = 15;
        sp.setId(id);
        id++;
        //Creating Space
        Space sp_ = new Space(this);
        sp_.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT));
        sp_.getLayoutParams().height = 15;
        sp_.setId(id);
        id++;

        //Creating Remove Button
        Button btn = new Button(this);
        //setting button text
        btn.setText("X");
        //Setting remove button id as 1+id of corresponding Edittext in the same frame
        btn.setId(id);
        id++;
        //Setting background colour as red for button
        btn.setBackgroundColor(Color.parseColor("#FF0000"));
        btn.setTextColor(Color.parseColor("#000000"));
        btn.setTextSize(20);
        btn. setTypeface(null, Typeface. BOLD);
        //Setting orientation to right
        btn.setGravity(Gravity.CENTER);
        //setting layout params
        btn.setLayoutParams(new
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        //Setting OnClickListner Function
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Function to remove view

                //Taking the id of the remove button
                int id = v.getId();
                id = id -1 ;
                Space sp = findViewById(id);
                id = id -1 ;
                Space sp_ = findViewById(id);
                id = id -1 ;
                EditText et = findViewById(id);
                id = id -1 ;
                LinearLayout ll_hor = findViewById(id);
                id = id -1 ;
                FrameLayout fr = findViewById(id);

                et_lst.remove(et);

                //LinearLayout ll_main = findViewById(R.id.Linear_layout_add_edit_text);
                ((ViewGroup) v.getParent()).removeView(v);
                ((ViewGroup) et.getParent()).removeView(et);
                ((ViewGroup) ll_hor.getParent()).removeView(ll_hor);
                ((ViewGroup) fr.getParent()).removeView(fr);
                ((ViewGroup) sp.getParent()).removeView(fr);
                ((ViewGroup) sp_.getParent()).removeView(fr);

                //Toast.makeText(Add_Members_Activity.this, "Et_lst size: "+et_lst.size(), Toast.LENGTH_SHORT).show();

            }
        });


        /*Testing
        String test = et.getText().toString();
        TextView tv = new TextView(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(test);
        */

        //Adding to horizontal linear layout
        ll_hor.addView(et);
        ll_hor.addView(btn);
        //Adding to frame
        fr.addView(ll_hor);
        //Adding them to linear layout
        ll_main.addView(sp);
        ll_main.addView(fr);
        ll_main.addView(sp_);

        //Toast.makeText(this, "EditView added Successfully!!!", Toast.LENGTH_SHORT).show();

    }

    public Boolean check()
    {
        for(int i=0;i<et_lst.size();i=i+1)
        {
            if(et_lst.get(i).getText().toString().trim().equals(""))
            {
                return false;
            }
        }
        return true;
    }

    public void DONE_CLICKED(View view) {
        if(et_lst.size()!=0 && check() && !grpName.getText().toString().trim().equals(""))
        {
            //Enter friends name in arrlst and return
            arrlst=new ArrayList();
            for(int i=0;i< et_lst.size();i++)
                arrlst.add(et_lst.get(i).getText().toString().trim());


            //Enter a row in main_table and store the id
            img_num = (int) (Math.random()*3);
            dbh.new_entry_MainTable(grpName.getText().toString().trim(), arrlst.size(),img_num);
            //Toast.makeText(this,"Table Created Successfully",Toast.LENGTH_SHORT).show();
            int last_id = 0;
            //Reading last row form main_table to get_the ID
            try {
                last_id = dbh.get_last_entered_id_main();
                //Toast.makeText(this, "Last ID: " + last_id, Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Toast.makeText(this, "Something Happened", Toast.LENGTH_SHORT).show();
            }

            //For Creating friends_id table
            dbh.create_table_for_friends(last_id,arrlst);
            //Toast.makeText(this,"Friends table created Successfully!!",Toast.LENGTH_SHORT).show();


            //For Creating  Activities_id table
            try {
                dbh.create_table_for_activities(last_id, arrlst);
                Toast.makeText(this, "Activities table created Successfully!!", Toast.LENGTH_SHORT).show();
            }catch(Exception e)
            {
                Toast.makeText(this, "Exception: "+e, Toast.LENGTH_SHORT).show();
            }

            Intent result_intent = new Intent();
            result_intent.putExtra("group_name",grpName.getText().toString().trim());
            result_intent.putExtra("Members",et_lst.size());
            result_intent.putExtra("ID",last_id);
            result_intent.putExtra("Img_num",img_num);
            setResult(RESULT_OK,result_intent);
            finish();
        }

        else
        {
            Toast.makeText(this, "NAME NOT ENTERED SOMEWHERE!!!", Toast.LENGTH_SHORT).show();
        }
    }
}