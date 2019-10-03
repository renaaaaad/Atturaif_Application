package project.graduation.atturaif_application;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class InternetChecking extends BasicActivity {


    SwipeRefreshLayout swipeRefreshLayout;
    Button Retrybtn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_checking);

        swipeRefreshLayout=findViewById(R.id.Swip);
        toolbar=findViewById(R.id.toolbar);
        Retrybtn=findViewById(R.id.Retry);


        //default back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = this.getIntent();
        final String strdata;

        strdata = intent.getExtras().getString("Uniqid");

        String[] parts = strdata.split("_");

        String[] actName=parts[0].split("P");
        InternetChecking.this.setTitle(actName[0]);


        Retrybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(haveNetwork()){
                    Intent inn=activityName();
                    startActivity(inn);
                }
                else{
                    Toast.makeText(InternetChecking.this,"Network connection is not available!",Toast.LENGTH_SHORT).show();

                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefreshLayout.setRefreshing(false);
                        if(haveNetwork()){
                           Intent intt= activityName();
                            startActivity(intt);
                        }
                        else{
                    Toast.makeText(InternetChecking.this,"Network connection is not available!",Toast.LENGTH_SHORT).show();

                }
                    }
                },4000);

            }
        });


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

    private Intent activityName(){

        Intent intent = this.getIntent();
        final String strdata;
        Intent intent1 = null;

        /* Obtain previous activity name from Intent  */
        if(intent !=null)
        {
            strdata = intent.getExtras().getString("Uniqid");


            if(strdata.equals("Booking_Activity"))
            {
                 intent1=new Intent(InternetChecking.this,Booking_Activity.class);
            }
            if(strdata.equals("eventDetails"))
            {
                intent1=new Intent(InternetChecking.this,EventsPage_Activity.class);
            }
            if(strdata.equals("EventsPage_Activity"))
            {
                intent1=new Intent(InternetChecking.this,EventsPage_Activity.class);
            }
            if(strdata.equals("shopDetails"))
            {
                intent1=new Intent(InternetChecking.this,ShopsPage.class);

            }
            if(strdata.equals("ShopsPage"))
            {
                intent1=new Intent(InternetChecking.this,ShopsPage.class);

            }
        }
        else
        {
            strdata="InternetChecking";
            intent1=new Intent(InternetChecking.this,InternetChecking.class);

        }
        return intent1;
    }
}




