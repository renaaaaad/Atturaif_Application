package project.graduation.atturaif_application;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;


public class eventDetails extends BasicActivity  {

    Toolbar toolbar;
    Button bookingbtn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        toolbar = findViewById(R.id.toolbar);
        bookingbtn=findViewById(R.id.BookingTic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if(haveNetwork()) {
            Intent intent = getIntent();
            String name = intent.getStringExtra(Constant.Keys.EVENT_NAME);
            String time = intent.getStringExtra(Constant.Keys.EVENT_TIME);
            String des = intent.getStringExtra(Constant.Keys.EVENT_Des);
            String image = intent.getStringExtra(Constant.Keys.EVENT_URL);

            ImageView imageView = (ImageView) findViewById(R.id.eventPhoto);
            TextView Ename = (TextView) findViewById(R.id.eventname);
            TextView Etime = (TextView) findViewById(R.id.EventTime);
            TextView Edes = (TextView) findViewById(R.id.eventdesc);


            Picasso.with(this).load(image).into(imageView);
            Ename.setText(name);
            Etime.setText(time);
            Edes.setText(des);

        }
        else{
            Intent intent = new Intent();
            intent.setClass(eventDetails.this,InternetChecking.class);
            intent.putExtra("Uniqid","eventDetails");
            startActivity(intent);
        }


        bookingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(eventDetails.this, Booking_Activity.class));
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eventshare_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent=getIntent();
        String eventname= intent.getStringExtra(Constant.Keys.EVENT_NAME);
        String eventtime=intent.getStringExtra(Constant.Keys.EVENT_TIME);
        String eventdes=intent.getStringExtra(Constant.Keys.EVENT_Des);

        switch (item.getItemId()){

            case R.id.share_btn:
                Intent shareingintent=new Intent(Intent.ACTION_SEND);
                shareingintent.setType("text/plain");
                String sharebody= ""+eventdes+"";
                String sharesubject=eventname + " event at "+ eventtime;

                shareingintent.putExtra(Intent.EXTRA_TEXT,sharebody);
                shareingintent.putExtra(Intent.EXTRA_SUBJECT,sharesubject);

                startActivity(Intent.createChooser(shareingintent,"Share "));

                break;

        }
        return super.onOptionsItemSelected(item);
    }
    private boolean haveNetwork(){

        boolean have_WIFI=false;
        boolean have_MobileData=false;

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI=true;

            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData=true;

        }

        return have_WIFI || have_MobileData;
    }
}


