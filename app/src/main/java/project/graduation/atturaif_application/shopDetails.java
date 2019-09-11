package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_Des;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_NAME;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_URL;

public class shopDetails extends BasicActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        String name= intent.getStringExtra(EXTRA_NAME);
//        String Opentime=intent.getStringExtra(EXTRA_OPENTIME);
//        String Closetime=intent.getStringExtra(EXTRA_CLOSETIME);
        String des=intent.getStringExtra(EXTRA_Des);
        String image=intent.getStringExtra(EXTRA_URL);

        ImageView imageView=findViewById(R.id.shopphoto);
        TextView Sname=findViewById(R.id.shopname);
//        TextView SOpentime=findViewById(R.id.shopopen);
//        TextView Sclosetime=findViewById(R.id.shopclose);
        TextView Sdes=findViewById(R.id.shopdesc);

        Picasso.with(this).load(image).into(imageView);

        Sname.setText(name);
//        Sclosetime.setText(Closetime);
//        SOpentime.setText(Opentime);
        Sdes.setText(des);

    }
}
