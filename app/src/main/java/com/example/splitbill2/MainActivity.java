package com.example.splitbill2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = findViewById(R.id.ll_addfr_main_interface);
        dbh =  new DataBaseHandler(this);


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
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lr_hor.setLayoutParams(params_lr_hor);
        //setting orientation as horizontal
        lr_hor.setOrientation(LinearLayout.HORIZONTAL);
        lr_hor.setTag("ll_hor_"+btn_id);


        //add image view
        ImageView imgView = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.MATCH_PARENT);
        imgView.setLayoutParams(lp);
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
        tv1.setLayoutParams(params_tv1);
        //Text Size
        tv1.setTextSize(12);
        tv1.setTextColor(Color.BLACK);
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
        tv2.setTextSize(6);
        //textView orientation
        tv2.setGravity(Gravity.LEFT);
        tv2.setTag("Mem_"+btn_id);


        Button btn = new Button(this);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        btn.setText("-->");
        btn.setGravity(Gravity.RIGHT);
        btn.setId(btn_id);
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
        rem_btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        rem_btn.setText("DEL");
        rem_btn.setGravity(Gravity.RIGHT);
        rem_btn.setTag("remove_"+btn_id);
        rem_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tg = (String) v.getTag();
                int i=7;
                int id=0;
                while(i!=tg.length())
                {
                    id = id*10+(tg.charAt(i)-'0');
                    i++;
                }
                Toast.makeText(MainActivity.this, "ID: "+id+" BTN_ID: "+btn_id, Toast.LENGTH_SHORT).show();

                create_Dialoue(id);

            }
        });
        //btn.setOnClickListener(this);


        //Adding views to vertical linear layout
        lr_ver.addView(tv1);

        lr_ver.addView(tv2);
        //Adding views to horizontal linear layout
        lr_hor.addView(imgView);
        lr_hor.addView(lr_ver);
        lr_hor.addView(btn);
        lr_hor.addView(rem_btn);
        //adding views to frame
        fr.addView(lr_hor);
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
        Button go_btn = (Button) ll.findViewWithTag("btn_" + id);
        LinearLayout horizontal = (LinearLayout) ll.findViewWithTag("ll_hor_" + id);
        FrameLayout fr = (FrameLayout) ll.findViewWithTag("fr_" + id);

        dbh.delete_row(id);

        ((ViewGroup) num_members.getParent()).removeView(num_members);
        ((ViewGroup) trip.getParent()).removeView(trip);
        ((ViewGroup) vertical.getParent()).removeView(vertical);
        ((ViewGroup) rem_btn.getParent()).removeView(rem_btn);
        ((ViewGroup) go_btn.getParent()).removeView(go_btn);
        ((ViewGroup) imv.getParent()).removeView(imv);
        ((ViewGroup) horizontal.getParent()).removeView(horizontal);
        ((ViewGroup) fr.getParent()).removeView(fr);

    }

}

