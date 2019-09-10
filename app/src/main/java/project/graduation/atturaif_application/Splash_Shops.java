package project.graduation.atturaif_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.ArrayList;
import java.util.List;

import project.graduation.atturaif_application.Objectes.Events;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Shops;
import project.graduation.atturaif_application.Objectes.shope_splash_name;

public class Splash_Shops extends AppCompatActivity {
    ProgressBar progressbar;
    DatabaseReference reference;
    List<shope_splash_name> shops_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__shops);
        progressbar = findViewById(R.id.progressbar);
        shops_name = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference().child("Shops");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                        String nameAR = ds.child("shopnameAR").getValue(String.class);
                        String image = ds.child("image").getValue(String.class);
                        String id = ds.getKey();
                        final shope_splash_name e = new shope_splash_name(id, nameAR, image);
                        shops_name.add(e);
                    } else {
                        String nameAR = ds.child("shopnameEN").getValue(String.class);
                        String image = ds.child("image").getValue(String.class);
                        String id = ds.getKey();
                        final shope_splash_name e = new shope_splash_name(id, nameAR, image);
                        shops_name.add(e);
                    }
                } //for
                if (!shops_name.isEmpty()) {
                    ShopsPage.setShops(shops_name);
                    startActivity(new Intent(getApplicationContext(), ShopsPage.class));
                    finish();
                }
            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            } //onCancelled
        });

    } //onCreate
} // class
