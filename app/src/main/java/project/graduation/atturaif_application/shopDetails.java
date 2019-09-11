package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import project.graduation.atturaif_application.Adapters.shopdaysitemAdapter;
import project.graduation.atturaif_application.Objectes.Open_Days;

import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_Des;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_NAME;
import static project.graduation.atturaif_application.EventsPage_Activity.EXTRA_URL;
import static project.graduation.atturaif_application.ShopsPage.EXTRA_ID;

public class shopDetails extends BasicActivity {

    Toolbar toolbar;
    DatabaseReference reference;
    RecyclerView recyclerView;
    shopdaysitemAdapter madapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Open_Days> daylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        recyclerView=findViewById(R.id.ShopDaysrecycleview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        daylist=new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent=getIntent();
        String name= intent.getStringExtra(EXTRA_NAME);
        String id=intent.getStringExtra(EXTRA_ID);
        String des=intent.getStringExtra(EXTRA_Des);
        String image=intent.getStringExtra(EXTRA_URL);

        reference = FirebaseDatabase.getInstance().getReference("Shops").child(id).child("days");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Open_Days e1;

                    try {
                        String day=ds.getKey();
                        String open = ds.child("openTime").getValue(String.class);
                        String close = ds.child("closeTime").getValue(String.class);

                         e1 = new Open_Days(day, open,close);
                        daylist.add(e1);
                    } catch (Exception e) {
                        String day=ds.getKey();
                        String open = ds.child("openTime").getValue(String.class);
                        String close = ds.child("closeTime").getValue(String.class);

                         e1= new Open_Days(day, open,close);

                        daylist.add(e1);
                    } // catch




                }
                madapter=new shopdaysitemAdapter(daylist);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(madapter);
                }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





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
