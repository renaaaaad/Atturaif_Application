package project.graduation.atturaif_application;

import android.content.Intent;

import android.net.ConnectivityManager;

import android.net.NetworkInfo;

import android.os.Bundle;

import android.os.Handler;

import android.os.Looper;

import android.text.style.ForegroundColorSpan;

import android.view.View;

import android.widget.LinearLayout;

import android.widget.ProgressBar;

import android.widget.Toast;


import androidx.annotation.NonNull;

import androidx.appcompat.widget.Toolbar;

import androidx.core.content.ContextCompat;

import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import com.github.ybq.android.spinkit.sprite.Sprite;

import com.github.ybq.android.spinkit.style.CubeGrid;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import com.prolificinteractive.materialcalendarview.CalendarMode;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import com.prolificinteractive.materialcalendarview.DayViewFacade;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import org.threeten.bp.DayOfWeek;


import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Calendar;

import java.util.Date;

import java.util.List;

import java.util.Locale;

import java.util.Timer;

import java.util.TimerTask;


import project.graduation.atturaif_application.Adapters.EventsAdapter;

import project.graduation.atturaif_application.Objectes.Events;


import static project.graduation.atturaif_application.R.id;

import static project.graduation.atturaif_application.R.layout;


public class EventsPage_Activity extends BasicActivity implements
        OnDateSelectedListener, EventsAdapter.onItemClickListner {


    private MaterialCalendarView mCalendarView;

    DatabaseReference reference;

    DatabaseReference reference1;

    RecyclerView recyclerView;

    ArrayList<Events> eventList;

    EventsAdapter adapter;

    Toolbar toolbar;

    LinearLayout noeventlayout;

    Timer timer;

    LinearLayout progressbar;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(layout.activity_events_page_);

        progressbar = findViewById(R.id.progressbar);

        ProgressBar progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

        //check network connection


        toolbar = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);


        if (haveNetwork()) {


            noeventlayout = findViewById(id.linearlayoutnoevent);


            mCalendarView = (MaterialCalendarView)
                    findViewById(id.calendarView);


            //set current date

            mCalendarView.setSelectedDate(CalendarDay.today());


            int year = Calendar.getInstance().get(Calendar.YEAR);

            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;


            mCalendarView.state().edit().

                    setFirstDayOfWeek(DayOfWeek.of(Calendar.SUNDAY)).

                    setMinimumDate(CalendarDay.from(year, month, 1)).

                    setCalendarDisplayMode(CalendarMode.WEEKS).

                    commit();


            if (MySharedPreference.getBoolean(getApplicationContext(), Constant.Keys.EVENTS, false)) {
                progressbar.setVisibility(View.GONE);
                MySharedPreference.putBoolean(getApplicationContext(), Constant.Keys.EVENTS, false);
            } else {
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
                }, 4000);

            }


            mCalendarView.setOnDateChangedListener(this);

            mCalendarView.setOnDateChangedListener(this);

            mCalendarView.setOnDateChangedListener(this);

            mCalendarView.setOnDateChangedListener(this);

//

            recyclerView = findViewById(id.Eventrecycleview);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            eventList = new ArrayList<Events>();


            reference =
                    FirebaseDatabase.getInstance().getReference().child("Events");

            reference.addValueEventListener(new ValueEventListener() {

                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    for (DataSnapshot ds : dataSnapshot.getChildren()) {


                        final Events e = ds.getValue(Events.class);


                        mCalendarView.addDecorator(new DayViewDecorator() {

                            @Override

                            public boolean shouldDecorate(CalendarDay day) {

                                return (day.getYear() == e.getYear())
                                        && (day.getDay() == e.getDay()) && (day.getMonth() == e.getMonth());

                            }


                            @Override

                            public void decorate(DayViewFacade view) {

                                view.addSpan(new
                                        ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(),
                                        android.R.color.white)));


                                view.setSelectionDrawable(ContextCompat.getDrawable(getApplicationContext(),
                                        R.drawable.event_decorator));

                            }

                        });

                    }


                }


                @Override

                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(EventsPage_Activity.this,
                            "Opsss....Something is wrong", Toast.LENGTH_SHORT).show();


                }

            });


            CalendarDay date2 = mCalendarView.getCurrentDate();

            String currentDate = new SimpleDateFormat("dd-MM-yyyy",
                    Locale.getDefault()).format(new Date());


            String substring = currentDate.substring(0, 2);


            final int Currentday = Integer.parseInt(substring);


            final int Currentmonth = date2.getMonth();

            final int Currentyear = date2.getYear();


            reference1 =
                    FirebaseDatabase.getInstance().getReference().child("Events");

            reference1.addValueEventListener(new ValueEventListener() {

                @Override

                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    eventList.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {


                        Events e = ds.getValue(Events.class);


                        if (e.getYear() == (Currentyear) &&
                                e.getMonth() == (Currentmonth) && e.getDay() == Currentday) {

                            eventList.add(e);


                            noeventlayout.setVisibility(LinearLayout.GONE);

                        }


                    }

                    recyclerView.setVisibility(View.VISIBLE);

                    adapter = new
                            EventsAdapter(EventsPage_Activity.this, eventList);

                    recyclerView.setAdapter(adapter);


                }


                @Override

                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(EventsPage_Activity.this,
                            "Opsss....Something is wrong", Toast.LENGTH_SHORT).show();


                }

            });


        }//end network condition


        else if (!haveNetwork()) {

            Intent intent = new Intent();

            intent.setClass(EventsPage_Activity.this, InternetChecking.class);

            intent.putExtra("Uniqid", "EventsPage_Activity");

            startActivity(intent);

        }


    }//onCreat end


    @Override

    public void onDateSelected(@NonNull MaterialCalendarView
                                       materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {


        CalendarDay date = mCalendarView.getSelectedDate();

        final int day = date.getDay();

        final int month = date.getMonth();

        final int year = date.getYear();
        reference =
                FirebaseDatabase.getInstance().getReference().child("Events");

        reference.addValueEventListener(new ValueEventListener() {

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                eventList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    Events e = ds.getValue(Events.class);


                    List dates = mCalendarView.getSelectedDates();


                    if (e.getYear() == (year) && e.getMonth() ==
                            (month) && e.getDay() == day) {

                        eventList.add(e);


                        noeventlayout.setVisibility(LinearLayout.GONE);


                    }


                    if (eventList.isEmpty()) {

                        noeventlayout.setVisibility(LinearLayout.VISIBLE);
                    }


                }

                recyclerView.setVisibility(View.VISIBLE);

                adapter = new EventsAdapter(EventsPage_Activity.this,
                        eventList);

                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(EventsPage_Activity.this);


            }


            @Override

            public void onCancelled(@NonNull DatabaseError databaseError) {


                Toast.makeText(EventsPage_Activity.this,
                        "Opsss....Something is wrong", Toast.LENGTH_SHORT).show();

            }

        });

    }


    @Override

    public void onItemClick(int position) {


        Intent intent = new Intent(this, eventDetails.class);

        Events clickeditem = eventList.get(position);


        if (MySharedPreference.getString(getApplicationContext(),
                Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {


            intent.putExtra(Constant.Keys.EVENT_URL, clickeditem.getImage());

            intent.putExtra(Constant.Keys.EVENT_NAME,
                    clickeditem.getEventnameAR());

            intent.putExtra(Constant.Keys.EVENT_Des,
                    clickeditem.getDescriptionAR());

            intent.putExtra(Constant.Keys.EVENT_TIME,
                    clickeditem.getEventTime());

            startActivity(intent);

        } else {

            intent.putExtra(Constant.Keys.EVENT_URL, clickeditem.getImage());

            intent.putExtra(Constant.Keys.EVENT_NAME,
                    clickeditem.getEventnameEN());

            intent.putExtra(Constant.Keys.EVENT_Des,
                    clickeditem.getDescriptionEN());

            intent.putExtra(Constant.Keys.EVENT_TIME,
                    clickeditem.getEventTime());

            startActivity(intent);

        }


    }


    private boolean haveNetwork() {


        boolean have_WIFI = false;

        boolean have_MobileData = false;


        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);


        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();


        for (NetworkInfo info : networkInfos) {

            if (info.getTypeName().equalsIgnoreCase("WIFI"))

                if (info.isConnected())

                    have_WIFI = true;


            if (info.getTypeName().equalsIgnoreCase("MOBILE"))

                if (info.isConnected())

                    have_MobileData = true;


        }


        return have_WIFI || have_MobileData;

    }


}
