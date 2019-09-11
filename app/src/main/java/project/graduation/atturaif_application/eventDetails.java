package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;


public class eventDetails extends BasicActivity  {

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
        String name= intent.getStringExtra(Constant.Keys.EVENT_NAME);
        String time=intent.getStringExtra(Constant.Keys.EVENT_TIME);
        String des=intent.getStringExtra(Constant.Keys.EVENT_Des);
        String image=intent.getStringExtra(Constant.Keys.EVENT_URL);

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
}
