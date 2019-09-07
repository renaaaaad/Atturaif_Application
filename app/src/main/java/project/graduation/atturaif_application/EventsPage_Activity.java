package project.graduation.atturaif_application;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import project.graduation.atturaif_application.Adapters.EventsAdapter;
import project.graduation.atturaif_application.Objectes.Events;

import static project.graduation.atturaif_application.R.id;
import static project.graduation.atturaif_application.R.layout;

public class EventsPage_Activity extends BasicActivity implements OnDateSelectedListener {

    public static final String RESULT = "result";
    public static final String EVENT = "event";

    private MaterialCalendarView mCalendarView;

    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Events> eventList;
    EventsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_events_page_);

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


        recyclerView=(RecyclerView)findViewById(R.id.Eventrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList=new ArrayList<Events>();




    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        CalendarDay date = mCalendarView.getSelectedDate();
        final int day= date.getDay();
         final int month=date.getMonth();
        final int year=date.getYear();


//        Toast.makeText(EventsPage_Activity.this,""+day,Toast.LENGTH_SHORT).show();


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


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(EventsPage_Activity.this,"Opsss....Something is wrong",Toast.LENGTH_SHORT).show();
            }
        });




//        List dates = materialCalendarView.getSelectedDates();

//        Toast toast = Toast.makeText(context, ""+dates, duration);
//        toast.show();


    }
}
