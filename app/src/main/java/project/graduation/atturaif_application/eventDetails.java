package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_Des;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_NAME;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_TIME;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_URL;

public class eventDetails extends AppCompatActivity {

    Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        String name= intent.getStringExtra(EXTRA_NAME);
        String time=intent.getStringExtra(EXTRA_TIME);
        String des=intent.getStringExtra(EXTRA_Des);
        String image=intent.getStringExtra(EXTRA_URL);

        ImageView imageView=(ImageView)findViewById(R.id.eventPhoto);
        TextView Ename=(TextView)findViewById(R.id.eventname);
        TextView Etime=(TextView)findViewById(R.id.EventTime);
        TextView Edes=(TextView)findViewById(R.id.eventdesc);


        Picasso.with(this).load(image).into(imageView);
        Ename.setText(name);
        Etime.setText(time);
        Edes.setText(des);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.eventshare_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent intent=getIntent();
        String eventname= intent.getStringExtra(EXTRA_NAME);
        String eventtime=intent.getStringExtra(EXTRA_TIME);
        String eventdes=intent.getStringExtra(EXTRA_Des);

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
}
