package com.example.splitbill2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String trip_name;
    private int num_mem;
    private int btn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                trip_name = data.getStringExtra("group_name");
                num_mem = data.getIntExtra("Members", 0);
                btn_id = data.getIntExtra("ID", 0);
                Toast.makeText(this, "GOT" + trip_name + "  " + num_mem + "  " + btn_id, Toast.LENGTH_SHORT).show();
                add_frame_main_interface();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Something happened", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public DataBaseHandler dbh = new DataBaseHandler(this);

    public void Main_Add_Button_pressed(View view) {

        //change intent to add activity name and add members page
        Toast.makeText(this, "Add Button Pressed", Toast.LENGTH_SHORT).show();

        Intent addMember = new Intent(this, Add_Members_Activity.class);
        addMember.putExtra("ABC", "abc");
        startActivityForResult(addMember, 1);
        //add_frame_main_interface();

        Toast.makeText(this, "Going to new Activity", Toast.LENGTH_SHORT).show();




    }

    public void add_frame_main_interface() {

        LinearLayout ll = findViewById(R.id.ll_addfr_main_interface);

        //creating frame dynamically
        FrameLayout fr = new FrameLayout(this);
        //layout params for frames
        FrameLayout.LayoutParams params_fr = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        fr.setLayoutParams(params_fr);




        // creating horizontal linear layout dynamically
        LinearLayout lr_hor = new LinearLayout(this);
        //setting layout parameters
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_hor.setLayoutParams(params_lr_hor);
        //setting orientation as horizontal
        lr_hor.setOrientation(LinearLayout.HORIZONTAL);


        //add image view

        // adding vertical linear layout dynamically
        LinearLayout lr_ver = new LinearLayout(this);
        //setting layout params
        LinearLayout.LayoutParams params_lr_ver = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,6
        );
        lr_ver.setLayoutParams(params_lr_ver);
        //setting orientation as vertical
        lr_ver.setOrientation(LinearLayout.VERTICAL);


        //adding text view to add GROUP NAME
        TextView tv1 = new TextView(this);
        //tv1.setText("TripName");
        tv1.setText(trip_name);
        LinearLayout.LayoutParams params_tv1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tv1.setLayoutParams(params_tv1);
        //Text Size
        tv1.setTextSize(12);
        tv1.setTextColor(Color.BLACK);
        //textview Orientation
        tv1.setGravity(Gravity.LEFT);


        //TextView to add number of members in group
        TextView tv2 = new TextView(this);
        //tv2.setText("MEMBERS");
        tv2.setText("" + num_mem + " Members");
        LinearLayout.LayoutParams params_tv2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        tv2.setLayoutParams(params_tv2);
        //setting text size
        tv2.setTextSize(6);
        //textView orientation
        tv2.setGravity(Gravity.LEFT);


        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        btn.setText("-->");
        btn.setGravity(Gravity.RIGHT);
        btn.setId(btn_id);
        btn.setOnClickListener(this);


        //Adding views to vertical linear layout
        lr_ver.addView(tv1);

        lr_ver.addView(tv2);
        //Adding views to horizontal linear layout
        lr_hor.addView(lr_ver);
        lr_hor.addView(btn);
        //adding views to frame
        fr.addView(lr_hor);
        //adding views to scrollview

        ll.addView(fr);
        Toast.makeText(getApplicationContext(), "Frame Added", Toast.LENGTH_SHORT).show();

    }


    public void changing_intent(int id) {
        Toast.makeText(this, "CHANGED: "+id, Toast.LENGTH_SHORT).show();
        Intent addActivity = new Intent(this,Activities_page.class);
        addActivity.putExtra("ID",id);
        startActivity(addActivity);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        Toast.makeText(this, "ID: "+id, Toast.LENGTH_SHORT).show();
        changing_intent(id);
    }
}

