package com.example.splitbill2;

import static android.widget.TextView.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Activities_page extends AppCompatActivity implements View.OnClickListener {


    Intent in;
    private int btn_num;
    private int id;
    String activity_name;
    int max_id;
    String Date;
    String Time;
    String Group_name;
    DataBaseHandler dbh = new DataBaseHandler(this);
    TextView Main_group_name;
    LinearLayout ll_main;
    int border_true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_page);

        in = getIntent();
        id = in.getIntExtra("ID",0);
        Group_name = in.getStringExtra("Group_name");

        Main_group_name = findViewById(R.id.Grp_name_act_page);
        Main_group_name.setText(Group_name);
        Main_group_name.setAllCaps(true);

        ll_main = findViewById(R.id.itf2_add_frames);
        border_true=-1;
        ArrayList arrlst = dbh.get_Actname_date_time(id);
        int i=0;

        while(i<arrlst.size())
        {

            max_id=(Integer)arrlst.get(i);
            i++;
            activity_name=(String)arrlst.get(i);
            i++;
            Time = (String)arrlst.get(i);
            i++;
            Date = (String)arrlst.get(i);
            i++;
            btn_num=(Integer)arrlst.get(i);
            i++;
            border_true++;
            Add_frames_of_expense(border_true);


        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            if(resultCode==RESULT_OK)
            {
                //get id, Activity name,time and date
                activity_name = data.getStringExtra("Activity_name");
                Date = data.getStringExtra("Date");
                Time = data.getStringExtra("Time");
                max_id = data.getIntExtra("Max_id",0);
                btn_num = data.getIntExtra("Btn_num",0);
                border_true++;
                Add_frames_of_expense(border_true);
            }
            if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this,"Something happened",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void DONE_PRESSED_ACTIVITY(View v)
    {
        finish();
    }

    public void Add_Expense_CLICKED(View view)
    {
        try {
            Toast.makeText(this, "ID: "+id+" Activity page", Toast.LENGTH_SHORT).show();
            in = new Intent(this, add_activity.class);
            in.putExtra("Table_id", id);
            startActivityForResult(in, 2);
        }catch(Exception e)
        {
            Toast.makeText(this, "Exception: "+e, Toast.LENGTH_SHORT).show();
        }
    }


    public void Add_frames_of_expense(int border_true) {
        float wt = 1;


        FrameLayout fr = new FrameLayout(this);
        FrameLayout.LayoutParams params_fr = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        //fr.setId(max_id);
        fr.setLayoutParams(params_fr);

        //image view
        ImageView imgView = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT);
        imgView.setLayoutParams(lp);
        String uri = "@drawable/acct_"+btn_num;  // where myresource (without the extension) is the file
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imgView.setImageDrawable(res);


        LinearLayout lr_hor = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_hor.setLayoutParams(params_lr_hor);
        lr_hor.setOrientation(LinearLayout.HORIZONTAL);


        LinearLayout ll_ver = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_ver = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1
        );
        ll_ver.setLayoutParams(params_lr_ver);
        ll_ver.setOrientation(LinearLayout.VERTICAL);

        LinearLayout lr_hor_child = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_hor_chlid = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_hor_child.setLayoutParams(params_lr_hor_chlid);
        lr_hor_child.setOrientation(LinearLayout.HORIZONTAL);


        Space sp0 = new Space(this);
        sp0.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, wt
        ));

        TextView tv_activity = new TextView(this);
        tv_activity.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_activity.setText(activity_name);
        tv_activity.setTextSize(30);
        tv_activity. setTypeface(null, Typeface. BOLD_ITALIC);
        tv_activity.setTextColor(Color.parseColor("#000000"));
        tv_activity.setTag((String)("Activity_" + max_id));
        Toast.makeText(this, "TAG: Activity_"+max_id, Toast.LENGTH_SHORT).show();




        TextView tv_date = new TextView(this);
        tv_date.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_date.setTextColor(Color.parseColor("#000000"));
        tv_date.setText(Date);
        tv_date.setTextSize(15);

        Space sp2 = new Space(this);
        sp2.setLayoutParams(new LinearLayout.LayoutParams(
                25, LinearLayout.LayoutParams.MATCH_PARENT
        ));

        TextView tv_time = new TextView(this);
        tv_time.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_time.setTextColor(Color.parseColor("#000000"));
        tv_time.setText(Time);
        tv_time.setTextSize(15);

        View border = new View(this);
        LinearLayout.LayoutParams params_border = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 10
        );
        params_border.gravity = Gravity.BOTTOM;
        border.setLayoutParams(params_border);
        border.setBackgroundColor(Color.parseColor("#000000"));


        Space sp4 = new Space(this);
        sp4.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 20
        ));



        Button show_act_details = new Button(this);
        LinearLayout.LayoutParams params_show_act_details = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
        params_show_act_details.topMargin=33;
        show_act_details.setLayoutParams(params_show_act_details);
        show_act_details.setText("GO");
        show_act_details.setGravity(Gravity.RIGHT);
        show_act_details.setGravity(Gravity.CENTER_HORIZONTAL);


        //show_act_details.setBackgroundResource(R.drawable.select_btn);
        show_act_details.setBackgroundColor(Color.parseColor("#00e700"));
        show_act_details.setTextColor(Color.parseColor("#FFFFFF"));
        show_act_details.setTextSize(15);
        show_act_details.setId(max_id);
        show_act_details.setOnClickListener(this);




        lr_hor.addView(imgView);
        lr_hor.addView(sp0);

        ll_ver.addView(tv_activity);

        lr_hor_child.addView(tv_date);
        lr_hor_child.addView(sp2);
        lr_hor_child.addView(tv_time);
        ll_ver.addView(lr_hor_child);
        lr_hor.addView(ll_ver);


        lr_hor.addView(show_act_details);

        fr.addView(lr_hor);
        if(border_true!=0)
        fr.addView((border));

        ll_main.addView(fr);
    }


    @Override
    public void onClick(View view) {
        int row_id = view.getId();
        Toast.makeText(this, "Button ID: "+row_id, Toast.LENGTH_SHORT).show();

        TextView act = (TextView) ll_main.findViewWithTag((String)("Activity_" + row_id));
        String act_name = act.getText().toString().trim();
        Toast.makeText(this, "Activity name: "+act_name, Toast.LENGTH_SHORT).show();


        Intent show_activity_expense = new Intent(this,show_act_expense.class);
        show_activity_expense.putExtra("Activity_name",act_name);
        show_activity_expense.putExtra("RowId",row_id);
        show_activity_expense.putExtra("Table_id",id);
        startActivity(show_activity_expense);

    }

    public void status_pressed_activity(View view) {
        Intent status = new Intent(this,status_from_act_page.class);
        status.putExtra("Table_id",id);
        status.putExtra("Group_name",Group_name);
        startActivity(status);
    }

    public void Bills_pressed_activity(View view) {

        Intent Bills = new Intent(this,Bills_from_act_page.class);
        Bills.putExtra("Table_id",id);
        Bills.putExtra("Group_name",Group_name);
        startActivity(Bills);

    }
}