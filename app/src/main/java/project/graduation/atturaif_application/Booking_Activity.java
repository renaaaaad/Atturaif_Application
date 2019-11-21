package project.graduation.atturaif_application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import project.graduation.atturaif_application.Adapters.Ticket_Adapter;
import project.graduation.atturaif_application.Adapters.Tour_Adapter;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Tour;
import project.graduation.atturaif_application.Objectes.Vistor_price;

import static project.graduation.atturaif_application.Constant.Keys.BOOKING_DATE;

public class Booking_Activity extends BasicActivity implements OnDateSelectedListener {


    Toolbar toolbar;
    LinearLayout tourType;
    MaterialCalendarView mcv;
    List<Open_Days> open_days;
    Open_Days open;
    List<Tour> tours;
    DatabaseReference reference;
    EditText numberOfTickets;
    Context context;

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
    public static TextView noAvailableTickets;
    List<Vistor_price> vistor_prices;
    Ticket_Adapter ticket_adapter;
    public static AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_);
        progressbar = findViewById(R.id.progressbar);
        ProgressBar progressBar = findViewById(R.id.spin_kit);
        Sprite doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

        LinearLayout layoutAR=findViewById(R.id.enlayout_ar);
        LinearLayout layoutEN=findViewById(R.id.enlayout_en);




        if(MySharedPreference.getString(getApplicationContext(),Constant.Keys.APP_LANGUAGE,"en").equals("ar")) {
            layoutEN.setVisibility(View.GONE);
            layoutAR.setVisibility(View.VISIBLE);

        }








            toolbar = findViewById(R.id.toolbar);
        tourType = findViewById(R.id.tourType);
        Continue = findViewById(R.id.Continue);
        //
        open_Time = findViewById(R.id.open_Time);

        recyclerView = findViewById(R.id.ticket);
        noAvailableTickets = findViewById(R.id.noAvailableTickets);
        tours = new ArrayList<>();
        vistor_prices = new ArrayList<>();
        //
        open_days = new ArrayList<>();
        //

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
            //
            open_Time.setText(R.string.you_cant_book_today);
            recyclerView.setVisibility(LinearLayout.GONE);
            Continue.setVisibility(LinearLayout.GONE);

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
                    //MySharedPreference.getFolat(context, Constant.Keys.User_PRICE, 0
                    if (MySharedPreference.getFolat(context,Constant.Keys.User_PRICE,0)!=0) {
                        final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                        final String text = true ? FORMATTER.format(mcv.getSelectedDate().getDate()) : "No Selection";
                        MySharedPreference.putString(getApplicationContext(), BOOKING_DATE, text);
                        startActivity(new Intent(Booking_Activity.this, Payment.class));

                    } else {
                        Toast.makeText(Booking_Activity.this, "Please complete the form", Toast.LENGTH_LONG).show();


                        // Toast.makeText(Booking_Activity.this, "Please complete the form", Toast.LENGTH_LONG).show();
                    }
                }//onClick

            }); //onClick


        }//end if statment for netwoek checking

        else if(!haveNetwork())
        {

            Intent intent = new Intent();
            intent.setClass(Booking_Activity.this,InternetChecking.class);
            intent.putExtra("Uniqid","Booking_Activity");
            startActivity(intent);
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
                    String type="";
                    if(MySharedPreference.getString(getApplicationContext(),Constant.Keys.APP_LANGUAGE,"en").equals("ar")){
                       type  = child.child("name_ar").getValue(String.class);
                    }
                    else
                        type  = child.child("name_en").getValue(String.class);

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

            DatabaseReference myRef = database.getReference("open_hours").child("ar");
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

        }

        else{
            DatabaseReference myRef = database.getReference("open_hours").child("en");
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

        }

    } // get data




    protected void onRestart() {
        super.onRestart();
        progressbar.setVisibility(View.GONE);
    }

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

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay calendarDay, boolean selected) {

        if (calendarDay.equals(current_date)) {
            open_Time.setText(R.string.you_cant_book_today);
            recyclerView.setVisibility(LinearLayout.GONE);
            Continue.setVisibility(LinearLayout.GONE);
            return;
        }

        CalendarDay selectedDate = mcv.getSelectedDate();

        final int Sday = selectedDate.getDay();

        final int Smonth = selectedDate.getMonth();

        final int Syear = selectedDate.getYear();

        if(current_date.getYear()>Syear){
            open_Time.setText(R.string.you_cant_book_today);
            recyclerView.setVisibility(LinearLayout.GONE);
            Continue.setVisibility(LinearLayout.GONE);
            return;
        }
        else {
            if (current_date.getMonth() > Smonth) {
                open_Time.setText(R.string.you_cant_book_today);
                recyclerView.setVisibility(LinearLayout.GONE);
                Continue.setVisibility(LinearLayout.GONE);
                return;
            }else {
                if (current_date.getDay() > Sday) {
                    open_Time.setText(R.string.you_cant_book_today);
                    recyclerView.setVisibility(LinearLayout.GONE);
                    Continue.setVisibility(LinearLayout.GONE);
                    return;
                }
            }
        }


        open=new Open_Days();

        String dateString = String.format("%d-%d-%d", Syear, Smonth, Sday);
        Date date1 = null;
        try {
            date1= new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Then get the day of week from the Date based on specific locale.

        final String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date1);


        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {

            final String dayOfWeekAR=ArdayOfWeek(dayOfWeek);
            boolean done=false;
            for(int i=0;i<open_days.size();i++){
                Open_Days o= open_days.get(i);
                if(o.getDay().equals(dayOfWeekAR)){
                    open_Time.setText(getString(R.string.opens_at) + "  " + o.getOpenAt() + " الى " + o.getCloseAt());
                    recyclerView.setVisibility(View.VISIBLE);
                    Continue.setVisibility(View.VISIBLE);
                    done=true;
                    break;
                }

            }
            if(done==false)
                open_Time.setText(R.string.you_cant_book_today);
        }
        else{

            boolean done=false;

            for(int i=0;i<open_days.size();i++){
                Open_Days o= open_days.get(i);
                if(o.getDay().equals(dayOfWeek)){
                    open_Time.setText(getString(R.string.opens_at) + "   " + o.getOpenAt() + " to " + o.getCloseAt());
                    recyclerView.setVisibility(View.VISIBLE);
                    Continue.setVisibility(View.VISIBLE);
                    done=true;
                }

            }
            if(done==false)
                open_Time.setText(R.string.you_cant_book_today);
            return;
            }

        }







    private String ArdayOfWeek(String dayOfWeek) {

        String dayAR=null;

        switch (dayOfWeek) {
            case "Sunday":
                dayAR = "الأحد";

                break;
            case "Monday":
                dayAR = "الاثنين";
                break;
            case "Tuesday":
                dayAR = "الثلاثاء";
                break;
            case "Saturday":

                dayAR = "السبت";
                break;
            case "Friday":

                dayAR = "الجمعة";
                break;
            case "Wednesday":

                dayAR = "الأربعاء";
                break;
            case "Thursday":
                        dayAR = "الخميس";
                break;

        }// switch
        return dayAR;
    } //findDate


} //class
