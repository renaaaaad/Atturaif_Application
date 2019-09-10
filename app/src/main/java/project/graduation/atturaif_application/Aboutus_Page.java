package project.graduation.atturaif_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import project.graduation.atturaif_application.Objectes.Tour;

public class Aboutus_Page extends BasicActivity {
    TextView text;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Timer timer;
    ProgressBar progressbar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus__page);
        text = findViewById(R.id.text);
        progressbar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        progressbar.setVisibility(View.GONE);
                    }
                });
            } //run
        }, 3000);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("About_us");
        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            myRef.child("ar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    text.setText(dataSnapshot.getValue(String.class));
                } //onDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), R.string.error_message,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                } //onCancelled
            });
        } else {
            myRef.child("en").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    text.setText(dataSnapshot.getValue(String.class));
                } //onDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), R.string.error_message,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));

                } //onCancelled
            });
        } // else
    } //onCreate
} // class
