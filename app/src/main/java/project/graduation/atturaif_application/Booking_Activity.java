package project.graduation.atturaif_application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import project.graduation.atturaif_application.Adapters.Ticket_Adapter;
import project.graduation.atturaif_application.Adapters.Tour_Adapter;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Tour;
import project.graduation.atturaif_application.Objectes.Vistor_price;
import project.graduation.atturaif_application.phone_Authentication.Enter_phone_page;

public class Booking_Activity extends BasicActivity implements OnDateSelectedListener {
    Toolbar toolbar;
    LinearLayout tourType;
    MaterialCalendarView mcv;
    List<Open_Days> open_days;
    List<Tour> tours;
    Button Continue;
    Timer timer;
    public static CalendarDay current_date;
    public static boolean flag1 = false;
    public static boolean flag2 = false;
    public static boolean flag3 = false;
    RecyclerView recyclerView_tour;
    static Dialog dialog;
    TextView open_Time;
    LinearLayout progressbar;
    RecyclerView recyclerView;
    public static TextView noAvailableTickets, tourTypeText;
    List<Vistor_price> vistor_prices;
    Ticket_Adapter ticket_adapter;
    public static AlertDialog.Builder alertDialog;
    LinearLayout Networklayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_);
        progressbar = findViewById(R.id.progressbar);
        ProgressBar progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);
        Networklayout=findViewById(R.id.Networklayout);


        toolbar = findViewById(R.id.toolbar);
        tourType = findViewById(R.id.tourType);
        Continue = findViewById(R.id.Continue);
        tourTypeText = findViewById(R.id.tourTypeText);
        open_Time = findViewById(R.id.open_Time);
        recyclerView = findViewById(R.id.ticket);
        noAvailableTickets = findViewById(R.id.noAvailableTickets);
        tours = new ArrayList<>();
        vistor_prices = new ArrayList<>();
        open_days = new ArrayList<>();
        mcv = findViewById(R.id.teacher_info_calendarView);
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
        }, 5000);


        if(haveNetwork()) {

            // custom the calender
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            mcv.state().edit()
                    .setFirstDayOfWeek(DayOfWeek.of(Calendar.SATURDAY))
                    .setMinimumDate(CalendarDay.from(year, month, 1))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit();

            // getting the data from the database
            getData();

            mcv.setOnDateChangedListener(this);
            mcv.setOnDateChangedListener(this);
            mcv.setOnDateChangedListener(this);
            mcv.setOnDateChangedListener(this);
            int current_year = Calendar.getInstance().get(Calendar.YEAR);
            int current_month = Calendar.getInstance().get(Calendar.MONTH) + 1;
            int current_day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            current_date = CalendarDay.from(current_year, current_month, current_day);
            mcv.setDateSelected(CalendarDay.from(current_year, current_month, current_day), true);
            mcv.getSelectedDate();
            open_Time.setText(R.string.you_cant_book_today);

            // get tour type data
            getTourType();

            // sit tickets
            setTeckits();

            tourType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createPopUpWindow(tours);
                } //onClick
            });

            Continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                    final String text = true ? FORMATTER.format(mcv.getSelectedDate().getDate()) : "No Selection";
                    MySharedPreference.putString(getApplicationContext(), Constant.Keys.BOOKING_DATE, text);
                    MySharedPreference.putString(getApplicationContext(), Constant.Keys.TOUT_TYPE, tourTypeText.getText().toString());
                    startActivity(new Intent(Booking_Activity.this, Enter_phone_page.class));
                }//onClick

            }); //onClick


        }//end if statment for netwoek checking

        else if(!haveNetwork())
        {
            Networklayout.setVisibility(View.VISIBLE);
            Toast.makeText(Booking_Activity.this,"Network connection is not available!",Toast.LENGTH_SHORT).show();
        }
    } //onCreate


    private void createPopUpWindow(List<Tour> tours) {
        dialog = new Dialog(Booking_Activity.this);
        alertDialog = new AlertDialog.Builder(Booking_Activity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view2 = inflater.inflate(R.layout.popup_window_tour, null);
        recyclerView_tour = view2.findViewById(R.id.recycleView);
        Tour_Adapter tour_adapter = new Tour_Adapter(getApplicationContext(), tours);
        recyclerView_tour.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView_tour.setLayoutManager(mLayoutManager);
        recyclerView_tour.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tour.setAdapter(tour_adapter);
        recyclerView_tour.setNestedScrollingEnabled(true);
        alertDialog.setCustomTitle(view2);
        dialog = alertDialog.create();
        dialog.show();

    } // createPopUpWindow

    private void getTourType() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("tours").child("Available_tours");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        String dayEN = child.child("dayEN").getValue(String.class);
                        String dayAR = child.child("dayAR").getValue(String.class);
                        String time = child.child("time").getValue(String.class);
                        Long guide = child.child("guide").getValue(Long.class);
                        int duration = child.child("duration").getValue(Integer.class);
                        String typeEN = child.child("typeEN").getValue(String.class);
                        String typeAR = child.child("typeAR").getValue(String.class);

                        Tour tour = new Tour(dayEN, dayAR, time, Long.toString(guide), typeAR, typeEN, duration);
                        tours.add(tour);
                    } catch (Exception e) {
                        String dayEN = child.child("dayEN").getValue(String.class);
                        String dayAR = child.child("dayAR").getValue(String.class);
                        String time = child.child("time").getValue(String.class);
                        String guide = child.child("guide").getValue(String.class);
                        int duration = child.child("duration").getValue(Integer.class);
                        String typeEN = child.child("typeEN").getValue(String.class);
                        String typeAR = child.child("typeAR").getValue(String.class);

                        Tour tour = new Tour(dayEN, dayAR, time, guide, typeAR, typeEN, duration);
                        tours.add(tour);
                    } // catch

                }// for
                flag2 = true;
            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                tourTypeText.setText(R.string.no_tours_avilible);
            } //onCancelled
        });
    } //getTourType

    private void setTeckits() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("price");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String type = child.getKey();
                    int price = child.child("price").getValue(Integer.class);
                    int discount = child.child("discount").getValue(Integer.class);
                    Vistor_price vistor_price = new Vistor_price(type, price, discount);
                    vistor_prices.add(vistor_price);
                }// for
                if (!vistor_prices.isEmpty()) {
                    ticket_adapter = new Ticket_Adapter(getApplicationContext(), vistor_prices);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(ticket_adapter);
                } // if
                else { // if the list is empty
                    noAvailableTickets.setVisibility(View.VISIBLE);
                } // else
                flag3 = true;
            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                noAvailableTickets.setVisibility(View.VISIBLE);

            } //onCancelled


        });

    } // setTickets

    private void getData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("open_hours");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String day = child.getKey();

                    if (day == null)
                        return;

                    String open_At = child.child("open").getValue(String.class);
                    String close_At = child.child("close").getValue(String.class);
                    Open_Days open_days_obj = new Open_Days(day, open_At, close_At);
                    open_days.add(open_days_obj);
                } // for
                flag1 = true;
            } //onDataChange

            @Override
            public void onCancelled(DatabaseError error) {
                open_Time.setText(R.string.museum_close);
            } //onCancelled
        });
    } // get data


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
        if (calendarDay.equals(current_date)) {
            open_Time.setText(R.string.you_cant_book_today);
            return;
        }
        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        final String text = b ? FORMATTER.format(calendarDay.getDate()) : "No Selection";

        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            String first_three_letters = text.substring(0, 4);
            Open_Days open_days1 = findDate(first_three_letters);
            if (open_days1 == null) {
                open_Time.setText(R.string.museum_close);
                return;
            } // if
            open_Time.setText(getString(R.string.opens_at) + "   " + open_days1.getOpenAt() + " - " + open_days1.getCloseAt());
        } else {
            String first_three_letters = text.substring(0, 3);
            Open_Days open_days1 = findDate(first_three_letters);
            if (open_days1 == null) {
                open_Time.setText(R.string.museum_close);
                return;
            } // if
            open_Time.setText(getString(R.string.opens_at) + "   " + open_days1.getOpenAt() + " - " + open_days1.getCloseAt());

        } //else


    } //onDateSelected

    private Open_Days findDate(String first_three_letters) {
        Open_Days open_days2 = new Open_Days();
        switch (first_three_letters) {
            case "Sun":
                open_days2 = search("Sunday");

                break;
            case "Mon":

                open_days2 = search("Monday");
                break;
            case "Tue":

                open_days2 = search("Tuesday");
                break;
            case "Sat":

                open_days2 = search("Saturday");
                break;
            case "Fri":

                open_days2 = search("Friday");
                break;
            case "Wed":

                open_days2 = search("Wednesday");
                break;
            case "Thu":

                open_days2 = search("Thursday");
                break;
            case "الأح":
                open_days2 = search("Sunday");
                break;
            case "الاث":
                open_days2 = search("Monday");


                break;
            case "الثل":
                open_days2 = search("Tuesday");
                break;
            case "الأر":
                open_days2 = search("Wednesday");

                break;
            case "الخم":
                open_days2 = search("Thursday");

                break;
            case "الجم":
                open_days2 = search("Friday");

                break;
            case "السب":
                open_days2 = search("Saturday");


                break;
        }// switch
        return open_days2;
    } //findDate

    private Open_Days search(String day) {
        for (int i = 0; i < open_days.size(); i++) {
            if (open_days.get(i).getDay().equals(day))
                return open_days.get(i);
            else
                continue;
        }// for
        return null;
    } //Open_Days

    public static void setTourType(String type) {
        dialog.dismiss();
        tourTypeText.setText(type);
    } //setTourType

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

} //class
