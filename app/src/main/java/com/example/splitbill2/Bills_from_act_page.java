package com.example.splitbill2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Bills_from_act_page extends AppCompatActivity implements View.OnClickListener{

    LinearLayout ll;
    Intent in;
    int Table_id;
    String Group_Name;
    TextView Main_Group_Name;
    DataBaseHandler dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_from_act_page);

        in = getIntent();
        Table_id = in.getIntExtra("Table_id",0);
        Group_Name = in.getStringExtra("Group_name");

        Main_Group_Name = findViewById(R.id.Group_name_Bills_page);
        Main_Group_Name.setText(Group_Name);
        Main_Group_Name.setAllCaps(true);

        dbh = new DataBaseHandler(this);
        ll = findViewById(R.id.linear_layout_Bills);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
        create_to_pay();
    }

    private void sort_by_diff(ArrayList<String> names,ArrayList<Integer> id,ArrayList<Integer> difference)
    {
        for(int i=0;i<difference.size();i++)
        {
            for(int j=1;j<difference.size();j++)
            {
                if(difference.get(j-1) > difference.get(j))
                {
                    String name1=names.get(j-1);
                    String name2 = names.get(j);
                    names.set(j,name1);
                    names.set(j-1,name2);

                    int id1=id.get(j-1);
                    int id2=id.get(j);
                    id.set(j,id1);
                    id.set(j-1,id2);

                    int diff1 = difference.get(j-1);
                    int diff2 = difference.get(j);
                    difference.set(j-1,diff2);
                    difference.set(j,diff1);
                }

                else
                    continue;
            }
        }
    }

    void create_to_pay() {

        ArrayList arr = dbh.get_friend_table_name_pay_spent(Table_id);

        //spent-paid --> i+1 - i
        ArrayList<String> names= new ArrayList<String>();
        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<Integer> difference = new ArrayList<Integer>();

        int i=0;
        int ids=0;
        while(i<arr.size())
        {
            id.add(ids+1);
            ids++;

            names.add((String)arr.get(i));
            i++;

            difference.add((Integer)arr.get(i+1) - (Integer)arr.get(i) );
            i=i+2;
        }

        Boolean ret=true;
        for(int m=0;m< difference.size();m++)
        {
            if(difference.get(m)!=0)
                ret=false;
        }

        if(ret==true)
        {
            TextView all_settled = new TextView(this);
            all_settled.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            all_settled.setText("YOU ARE ALL SETTELED !!!!");
            all_settled. setTypeface(null, Typeface. BOLD);
            all_settled.setTextColor(Color.parseColor("#000000"));
            all_settled.setTextSize(45);
            all_settled.setGravity(Gravity.CENTER);
            ll.addView(all_settled);


            Toast.makeText(this, "YOU ALL ARE SETTLED UP!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            sort_by_diff(names,id,difference);
            main_algo(names,id,difference);
        }

    }


    private void main_algo(ArrayList<String> names, ArrayList<Integer> id, ArrayList<Integer> diff)
    {
        int start=0;
        int last=diff.size()-1;
        int num_frames=0;
        while(start<last)
        {
            if( Math.abs(diff.get(start)) > Math.abs(diff.get(last)) )
            {
                String s = ""+names.get(last)+" gives "+Math.abs(diff.get(last))+" to "+names.get(start);
                make_frame(s,id.get(last),id.get(start),Math.abs(diff.get(last)),num_frames );
                num_frames++;
                diff.set(start,diff.get(start)+diff.get(last));
                diff.set(last,0);

                last--;

            }

            else if( Math.abs(diff.get(start)) < Math.abs(diff.get(last)) )
            {
                String s = ""+names.get(last)+" gives "+Math.abs(diff.get(start))+" to "+names.get(start);
                make_frame(s,id.get(last),id.get(start),Math.abs(diff.get(start)),num_frames);
                num_frames++;
                diff.set(start,0);
                diff.set(last,diff.get(last)+diff.get(start));

                start++;
            }

            else if(Math.abs(diff.get(start)) == Math.abs(diff.get(last)) && Math.abs(diff.get(start))!=0 && Math.abs(diff.get(last))!=0 )
            {
                String s = ""+names.get(last)+" gives "+Math.abs(diff.get(last))+" to "+names.get(start);
                make_frame(s,id.get(last),id.get(start),Math.abs(diff.get(last)),num_frames);
                num_frames++;
                diff.set(start,0);
                diff.set(last,0);

                last--;
                start++;
            }
        }
    }


    private void make_frame(String s,int id_gives,int id_takes,int settle_amt,int num_frames)
    {
        LinearLayout ll_hor = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        ll_hor.setLayoutParams(params_lr_hor);
        ll_hor.setOrientation(LinearLayout.HORIZONTAL);
        ll_hor.setTag("ll_hor_"+num_frames);

        TextView show_to_settle = new TextView(this);
        LinearLayout.LayoutParams params_tv1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,4
        );
        show_to_settle.setTextColor(Color.parseColor("#000000"));
        show_to_settle.setLayoutParams(params_tv1);
        show_to_settle.setText(s);
        show_to_settle.setTextSize(25);
        show_to_settle. setTypeface(null, Typeface. BOLD);
        show_to_settle.setTag("tv_"+num_frames);

        Button settle_up = new Button(this);
        LinearLayout.LayoutParams btn = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1
        );
        settle_up.setLayoutParams(btn);
        settle_up.setBackgroundColor(Color.parseColor("#00e700"));
        settle_up.setTextColor(Color.parseColor("#FFFFFF"));
        settle_up.setText("SETTLE UP");
        settle_up.setOnClickListener(this);
        settle_up.setTag(id_gives+"_"+id_takes+"_"+settle_amt+"_"+num_frames);

        Space sp = new Space(this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 25));
        sp.setTag("SP_"+num_frames);

        ll_hor.addView(show_to_settle);
        ll_hor.addView(settle_up);
        ll.addView(ll_hor);
        ll.addView(sp);
    }

    @Override
    public void onClick(View view) {
        String tag = (String) view.getTag();

        int give_id=0;
        int i=0;

        while(tag.charAt(i)!='_')
        {
            give_id=give_id*10 + (tag.charAt(i)-'0');
            i++;
        }
        i++;
        int take_id=0;
        while(tag.charAt(i)!='_')
        {
            take_id = take_id*10 + (tag.charAt(i)-'0');
            i++;
        }
        i++;
        int settle_amt=0;
        while(tag.charAt(i)!='_')
        {
            settle_amt = settle_amt*10 + (tag.charAt(i)-'0');
            i++;
        }
        i++;
        int fr_number=0;
        while(i!=tag.length())
        {
            fr_number = fr_number*10 + (tag.charAt(i)-'0');
            i++;
        }
        Toast.makeText(this, "Giver ID: "+give_id+"  Taker ID: "+take_id+" Settle Amount: "+settle_amt+" Fr_num: "+fr_number, Toast.LENGTH_SHORT).show();
        dbh.inc_to_giver(give_id,settle_amt,Table_id);
        dbh.dec_from_taker(take_id,settle_amt,Table_id);
        Toast.makeText(this, "FRIENDS TABLE UPDATED!!!!", Toast.LENGTH_SHORT).show();

        Button settle_btn = (Button) ll.findViewWithTag(""+give_id+"_"+take_id+"_"+settle_amt+"_"+fr_number);
        TextView info = (TextView) ll.findViewWithTag("tv_"+fr_number);
        LinearLayout horizontal = (LinearLayout) ll.findViewWithTag("ll_hor_"+fr_number);
        Space sp = (Space)ll.findViewWithTag("SP_"+fr_number);

        ((ViewGroup) settle_btn.getParent()).removeView(settle_btn);
        ((ViewGroup) info.getParent()).removeView(info);
        ((ViewGroup) horizontal.getParent()).removeView(horizontal);
        ((ViewGroup) sp.getParent()).removeView(sp);

        Toast.makeText(this, "SETTLE SUCCESSFUL!!!", Toast.LENGTH_SHORT).show();
    }

    public void Done_pressed_from_Bills(View view) {
        finish();
    }
}