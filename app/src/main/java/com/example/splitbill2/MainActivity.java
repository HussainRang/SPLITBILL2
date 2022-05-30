package com.example.splitbill2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private String trip_name;
    private int num_mem;
    private int btn_id;
    //private ArrayList<TextView> act_name_lst;
    LinearLayout ll;
    DataBaseHandler dbh;
    private int img_num;
    int border_true_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.ll_addfr_main_interface);
        dbh =  new DataBaseHandler(this);
        border_true_main=-1;

        ArrayList arrlst = dbh.get_all();
        int i=0;
        while(i<arrlst.size())
        {
            btn_id=(Integer)arrlst.get(i);
            i++;
            trip_name=(String)arrlst.get(i);
            i++;
            num_mem = (Integer)arrlst.get(i);
            i++;
            img_num = (Integer)arrlst.get(i);
            i++;
            border_true_main++;
            add_frame_main_interface();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                trip_name = data.getStringExtra("group_name");
                num_mem = data.getIntExtra("Members", 0);
                btn_id = data.getIntExtra("ID", 0);
                img_num = data.getIntExtra("Img_num",0);
                Toast.makeText(this, "GOT" + trip_name + "  " + num_mem + "  " + btn_id, Toast.LENGTH_SHORT).show();
                border_true_main++;
                add_frame_main_interface();
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Something happened", Toast.LENGTH_SHORT).show();
            }
        }
    }







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



        //creating frame dynamically
        FrameLayout fr = new FrameLayout(this);
        //layout params for frames
        FrameLayout.LayoutParams params_fr = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        fr.setLayoutParams(params_fr);

        fr.setTag("fr_"+btn_id);



        // creating horizontal linear layout dynamically
        LinearLayout lr_hor = new LinearLayout(this);
        //setting layout parameters
        LinearLayout.LayoutParams params_lr_hor = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1
        );
        params_lr_hor.gravity=Gravity.TOP;
        lr_hor.setLayoutParams(params_lr_hor);
        //setting orientation as horizontal
        lr_hor.setOrientation(LinearLayout.HORIZONTAL);
        lr_hor.setTag("ll_hor_"+btn_id);


        //add image view
        ImageView imgView = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, LinearLayout.LayoutParams.MATCH_PARENT);
        imgView.setLayoutParams(lp);
        lp.gravity = Gravity.TOP;
        String uri = "@drawable/tripimg_"+img_num;  // where myresource (without the extension) is the file
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        imgView.setImageDrawable(res);
        imgView.setTag("img_"+btn_id);

        // adding vertical linear layout dynamically
        LinearLayout lr_ver = new LinearLayout(this);
        //setting layout params
        LinearLayout.LayoutParams params_lr_ver = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,6
        );
        lr_ver.setLayoutParams(params_lr_ver);
        //setting orientation as vertical
        lr_ver.setOrientation(LinearLayout.VERTICAL);
        lr_ver.setTag("lr_ver_"+btn_id);


        //adding text view to add GROUP NAME
        TextView tv1 = new TextView(this);
        //tv1.setText("TripName");
        tv1.setText(trip_name);

        LinearLayout.LayoutParams params_tv1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params_tv1.setMargins(0, 20, 0, 0);
        tv1.setLayoutParams(params_tv1);
        //Text Size
        tv1.setTextSize(25);
        tv1. setTypeface(null, Typeface. BOLD_ITALIC);
        tv1.setTextColor(Color.parseColor("#000000"));
        //textview Orientation
        tv1.setGravity(Gravity.LEFT);
        tv1.setTag("Trip_name"+btn_id);
        Toast.makeText(this, "Trip_name"+btn_id, Toast.LENGTH_SHORT).show();

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
        tv2.setTextSize(15);
        //textView orientation
        tv2.setGravity(Gravity.LEFT);
        tv2.setTag("Mem_"+btn_id);

        LinearLayout lr_ver_2 = new LinearLayout(this);
        LinearLayout.LayoutParams params_lr_ver_2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_ver_2.setLayoutParams(params_lr_ver_2);
        lr_ver_2.setOrientation(LinearLayout.VERTICAL);
        lr_ver_2.setTag("lr_ver_2"+btn_id);


        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(200,100,1));
        btn.setText("GO");
        //btn.setBackgroundResource(R.drawable.select_btn);

        btn.setBackgroundColor(Color.parseColor("#00e700"));
        btn.setTextColor(Color.parseColor("#FFFFFF"));
        btn.setTextSize(15);
        btn.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);

        btn.setId(btn_id);
        //btn.setBackgroundResource(R.drawable.select_btn);
        btn.setTag("btn_"+btn_id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                String trp;
                TextView tv= (TextView)ll.findViewWithTag("Trip_name"+id);
                trp = tv.getText().toString().trim();
                Toast.makeText(MainActivity.this, "ID: "+id+"   "+trp, Toast.LENGTH_SHORT).show();
                changing_intent(id,trp);
            }
        });


        Button rem_btn = new Button(this);
        rem_btn.setLayoutParams(new LinearLayout.LayoutParams(200,100,1));
        rem_btn.setText("REM.");
        rem_btn.setTag("remove_"+btn_id);
        rem_btn.setBackgroundColor(Color.parseColor("#FF0000"));
        rem_btn.setTextColor(Color.parseColor("#FFFFFF"));
        rem_btn.setTextSize(15);

        //rem_btn.setBackgroundResource(R.drawable.remove_btn);
        rem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tg = (String) v.getTag();
                int i=7;
                int id=0;
                while(i!=tg.length())
                {       //23   remove_203
                    id = id*10+(tg.charAt(i)-'0');    //0*10+2=2 2*10+0=20 20*10+3=203
                    i++;
                }
                Toast.makeText(MainActivity.this, "ID: "+id+" BTN_ID: "+btn_id, Toast.LENGTH_SHORT).show();

                create_Dialoue(id);

            }
        });


        Space sp1 = new Space(this);
        sp1.setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.MATCH_PARENT));
        sp1.setTag("SP1_"+btn_id);

        Space sp2 = new Space(this);
        sp2.setLayoutParams(new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.MATCH_PARENT));
        sp2.setTag("SP2_"+btn_id);

        Space sp_top = new Space(this);
        sp_top.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 30));
        sp_top.setTag("SPTop_"+btn_id);

        Space between_btns = new Space(this);
        between_btns.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        between_btns.setTag("SPbetbtn_"+btn_id);

        View border = new View(this);
        border.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                10
        ));
        if(border_true_main>0)
            border.setBackgroundColor(Color.parseColor("#000000"));
        else
            border.setBackgroundColor(Color.parseColor("#E8F9FD"));

        border.setTag("Border_"+btn_id);




        //Adding views to vertical linear layout
        lr_ver.addView(tv1);
        lr_ver.addView(tv2);

        lr_ver_2.addView(btn);
        lr_ver_2.addView(between_btns);
        lr_ver_2.addView(rem_btn);
        //Adding views to horizontal linear layout
        lr_hor.addView(imgView);
        lr_hor.addView(sp1);
        lr_hor.addView(lr_ver);
        lr_hor.addView(sp2);
        lr_hor.addView(lr_ver_2);
        //adding views to frame
        fr.addView(lr_hor);


        fr.addView(border);

        fr.addView(sp_top);
        //adding views to scrollview

        ll.addView(fr);
        Toast.makeText(getApplicationContext(), "Frame Added", Toast.LENGTH_SHORT).show();

    }


    public void changing_intent(int id,String trp) {
        Toast.makeText(this, "CHANGED: "+id, Toast.LENGTH_SHORT).show();
        Intent addActivity = new Intent(this,Activities_page.class);
        addActivity.putExtra("Group_name",trp);
        addActivity.putExtra("ID",id);
        startActivity(addActivity);

    }

    private void create_Dialoue(int id)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                delete_frame(id);
                Toast.makeText(MainActivity.this, "Delete Successful!!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Delete Cancelled!!!!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void delete_frame(int id) {

        TextView num_members = (TextView) ll.findViewWithTag("Mem_" + id);
        TextView trip = (TextView) ll.findViewWithTag("Trip_name" + id);
        ImageView imv = (ImageView)ll.findViewWithTag("img_"+id);
        LinearLayout vertical = (LinearLayout) ll.findViewWithTag("lr_ver_" + id);
        Button rem_btn = (Button) ll.findViewWithTag("remove_" + id);
        Space bet_btn = (Space) ll.findViewWithTag("SPbetbtn_"+id);
        Button go_btn = (Button) ll.findViewWithTag("btn_" + id);
        LinearLayout vertical2 = (LinearLayout)ll.findViewWithTag("lr_ver_2"+id);
        Space sp1 = (Space)ll.findViewWithTag("SP1_"+id);
        Space sp2 = (Space)ll.findViewWithTag("SP2_"+id);
        View bdr = (View)ll.findViewWithTag("Border_"+id);
        Space top = (Space)ll.findViewWithTag("SPTop_"+id);
        LinearLayout horizontal = (LinearLayout) ll.findViewWithTag("ll_hor_" + id);
        FrameLayout fr = (FrameLayout) ll.findViewWithTag("fr_" + id);

        dbh.delete_row(id);

        ((ViewGroup) num_members.getParent()).removeView(num_members);
        ((ViewGroup) trip.getParent()).removeView(trip);
        ((ViewGroup) vertical.getParent()).removeView(vertical);
        ((ViewGroup) rem_btn.getParent()).removeView(rem_btn);
        ((ViewGroup) bet_btn.getParent()).removeView(bet_btn);
        ((ViewGroup) go_btn.getParent()).removeView(go_btn);
        ((ViewGroup) vertical2.getParent()).removeView(vertical2);
        ((ViewGroup) sp1.getParent()).removeView(sp1);
        ((ViewGroup) sp2.getParent()).removeView(sp2);
        ((ViewGroup) imv.getParent()).removeView(imv);
        ((ViewGroup) horizontal.getParent()).removeView(horizontal);
        ((ViewGroup) bdr.getParent()).removeView(bdr);
        ((ViewGroup) top.getParent()).removeView(top);
        ((ViewGroup) fr.getParent()).removeView(fr);

    }

}

