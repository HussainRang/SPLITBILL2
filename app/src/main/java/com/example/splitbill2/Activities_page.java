package com.example.splitbill2;

import static android.widget.TextView.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Activities_page extends AppCompatActivity implements View.OnClickListener {


    Intent in;

    private int id;
    String activity_name;
    int max_id;
    String Date;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_page);

        in = getIntent();
        id = in.getIntExtra("ID",0);
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

                Add_frames_of_expense();
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


    public void Add_frames_of_expense() {
        float wt = 1;
        LinearLayout ll_main = findViewById(R.id.itf2_add_frames);

        FrameLayout fr = new FrameLayout(this);
        FrameLayout.LayoutParams params_fr = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        fr.setId(max_id);
        fr.setLayoutParams(params_fr);

        LinearLayout lr_hor = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_hor.setLayoutParams(params_lr_hor);
        lr_hor.setOrientation(LinearLayout.HORIZONTAL);

        Space sp0 = new Space(this);
        sp0.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, wt
        ));

        TextView tv_activity = new TextView(this);
        tv_activity.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_activity.setText(activity_name);
        tv_activity.setTextSize(20);
        tv_activity.setTag(String.valueOf("Activity_" + max_id));

        Space sp1 = new Space(this);
        sp1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, wt
        ));

        TextView tv_date = new TextView(this);
        tv_date.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_date.setText(Date);
        tv_date.setTextSize(15);

        Space sp2 = new Space(this);
        sp2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, wt
        ));

        TextView tv_time = new TextView(this);
        tv_time.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1
        ));
        tv_time.setText(Time);
        tv_time.setTextSize(15);

        Space sp3 = new Space(this);
        sp3.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, wt
        ));

        Space sp4 = new Space(this);
        sp4.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 20
        ));

        Button show_act_details = new Button(this);
        show_act_details.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        show_act_details.setText("-->");
        show_act_details.setGravity(Gravity.RIGHT);
        show_act_details.setId(max_id);
        show_act_details.setOnClickListener(this);

        lr_hor.addView(sp0);
        lr_hor.addView(tv_activity);
        lr_hor.addView(sp1);
        lr_hor.addView(tv_date);
        lr_hor.addView(sp2);
        lr_hor.addView(tv_time);
        lr_hor.addView(sp3);
        lr_hor.addView(show_act_details);

        fr.addView(lr_hor);

        ll_main.addView(fr);
    }


    @Override
    public void onClick(View view) {
        int row_id = view.getId();
        Toast.makeText(this, "Button ID: "+row_id, Toast.LENGTH_SHORT).show();
        TextView act = (TextView) view.findViewWithTag("Activity_"+row_id);
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
        startActivity(status);
    }
}