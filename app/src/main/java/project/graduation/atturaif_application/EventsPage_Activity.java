package project.graduation.atturaif_application;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.graduation.atturaif_application.Adapters.EventsAdapter;
import project.graduation.atturaif_application.Objectes.Events;

import static project.graduation.atturaif_application.R.id;
import static project.graduation.atturaif_application.R.layout;

public class EventsPage_Activity extends AppCompatActivity implements OnDateSelectedListener, EventsAdapter.onItemClickListner {



    public static final String EXTRA_URL = "imageurl";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_Des = "description";
    public static final String EXTRA_TIME = "time";

    private MaterialCalendarView mCalendarView;

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Events> eventList;
    EventsAdapter adapter;

    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_events_page_);

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mCalendarView = (MaterialCalendarView) findViewById(id.calendarView);

        //set current date
        mCalendarView.setSelectedDate(CalendarDay.today());

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH)+1;

        mCalendarView.state().edit().
                setFirstDayOfWeek(DayOfWeek.of(Calendar.SUNDAY)).
                setMinimumDate(CalendarDay.from(year, month, 1)).
                setCalendarDisplayMode(CalendarMode.MONTHS).
                commit();


        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnDateChangedListener(this);
        mCalendarView.setOnDateChangedListener(this);


        recyclerView=(RecyclerView)findViewById(id.Eventrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList=new ArrayList<Events>();


        reference= FirebaseDatabase.getInstance().getReference().child("Events");
        reference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                for(DataSnapshot ds:dataSnapshot.getChildren()){

                                                    final Events e=ds.getValue(Events.class);

                                                    mCalendarView.addDecorator(new DayViewDecorator() {
                                                        @Override
                                                        public boolean shouldDecorate(CalendarDay day) {
                                                            return (day.getYear() == e.getYear())&&(day.getDay() == e.getDay()) && (day.getMonth() == e.getMonth());
                                                        }

                                                        @Override
                                                        public void decorate(DayViewFacade view) {
//                                                            view.addSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), android.R.color.white)));
//                                                            view.setSelectionDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.event_decorator));
                                                            view.addSpan(new DotSpan(10, Color.rgb(191,144,84)));
                                                        }
                                                    });
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {

        CalendarDay date = mCalendarView.getSelectedDate();
        final int day= date.getDay();
         final int month=date.getMonth();
        final int year=date.getYear();





        reference= FirebaseDatabase.getInstance().getReference().child("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    Events e=ds.getValue(Events.class);
                    List dates = mCalendarView.getSelectedDates();

                    if(e.getYear()==(year)){
                        if(e.getMonth()==(month)){
                            if(e.getDay()==day){
                                eventList.add(e);

                            }

                        }

                    }
                }

                adapter=new EventsAdapter(EventsPage_Activity.this,eventList);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(EventsPage_Activity.this);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(EventsPage_Activity.this,"Opsss....Something is wrong",Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemClick(int position) {

        Intent intent=new Intent(this, eventDetails.class);
        Events clickeditem = eventList.get(position);

        intent.putExtra(EXTRA_URL,clickeditem.getImage());
        intent.putExtra(EXTRA_NAME,clickeditem.getEventnameEN());
        intent.putExtra(EXTRA_Des,clickeditem.getDescriptionEN());
        intent.putExtra(EXTRA_TIME,clickeditem.getEventTime());

        startActivity(intent);



    }
}
