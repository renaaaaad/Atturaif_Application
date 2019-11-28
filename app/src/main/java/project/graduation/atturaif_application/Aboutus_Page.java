package project.graduation.atturaif_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import project.graduation.atturaif_application.Adapters.Shops_Adapter;
import project.graduation.atturaif_application.Adapters.Woking_days_Adapter;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Tour;
import project.graduation.atturaif_application.Objectes.Work_Days;

public class Aboutus_Page extends BasicActivity {
    TextView text;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Timer timer;
    LinearLayout progressbar;
    List<Open_Days> dayslist;
    Woking_days_Adapter adapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus__page);
        text = findViewById(R.id.text);

        progressbar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);

        ProgressBar progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

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

        text.setText(R.string.about_us_dis);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("About_us");

        // getting the working days
        DatabaseReference myRef2 = database.getReference("open_hours");
        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            myRef2.child("ar").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dayslist = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String day = ds.getKey();
                        String open = ds.child("open").getValue(String.class);
                        String close = ds.child("close").getValue(String.class);
                        Open_Days work_days = new Open_Days(day, open, close);
                        dayslist.add(work_days);
                    }
                    listDays();
                } //onDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), R.string.error_message,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                } //onCancelled
            });
        } // if
        else {
            myRef2.child("en").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    dayslist = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String day = ds.getKey();
                        String open = ds.child("open").getValue(String.class);
                        String close = ds.child("close").getValue(String.class);
                        Open_Days work_days = new Open_Days(day, open, close);
                        dayslist.add(work_days);
                    }
                    listDays();
                } //onDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), R.string.error_message,
                            Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                } //onCancelled
            });
        }// else

    } //onCreate

    private void listDays() {
        List<Open_Days> new_list_dayes = new ArrayList<>();
        new_list_dayes = listDaysOrder(dayslist);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Woking_days_Adapter(getApplicationContext(), new_list_dayes);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private List<Open_Days> listDaysOrder(List<Open_Days> dayslist) {
        int count = 0;
        List<Open_Days> new_list_dayes2 = new ArrayList<>();
        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            while (!dayslist.get(count).getDay().equals("الأحد")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("الاثنين")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("الثلاثاء")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("الأربعاء")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("الخميس")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("الجمعة")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("السبت")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;
        } else {
            while (!dayslist.get(count).getDay().equals("Sunday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Monday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Tuesday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Wednesday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Thursday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Friday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;

            while (!dayslist.get(count).getDay().equals("Saturday")) {
                count = count + 1;
            }
            new_list_dayes2.add(dayslist.get(count));
            count = 0;
        }

        return new_list_dayes2;
    } //listDaysOrder
} // class
