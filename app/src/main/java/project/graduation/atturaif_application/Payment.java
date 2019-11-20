package project.graduation.atturaif_application;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kofigyan.stateprogressbar.StateProgressBar;

import project.graduation.atturaif_application.Adapters.Ticket_Adapter;
import project.graduation.atturaif_application.Objectes.Vistor_price;

import static project.graduation.atturaif_application.Booking_Activity.noAvailableTickets;

public class Payment extends BasicActivity {


    StateProgressBar stateProgressBar;
    TextView price_total, type, date;
    TextView me;
    Toolbar toolbar;
    Button Checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        price_total = findViewById(R.id.price_total);
        date = findViewById(R.id.date);
        float price = MySharedPreference.getFolat(getApplicationContext(), Constant.Keys.User_PRICE, 0);
        price_total.setText(Float.toString(price) + " " + getApplicationContext().getString(R.string.s_r));
        date.setText(MySharedPreference.getString(getApplicationContext(), Constant.Keys.BOOKING_DATE, ""));



        stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);

        stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);



        Checkout = findViewById(R.id.button2);

        Checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Payment.this, Billing_Contact.class));
            }
        });
    } //onCreate


    // this method to show dialog when the user clicks back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Payment.this);
                LayoutInflater inflater = getLayoutInflater();
                View view2 = inflater.inflate(R.layout.message_goback, null);
                alertDialog.setCustomTitle(view2);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Payment.this, Splash_page.class));
                        MySharedPreference.putFloat(getApplicationContext(), Constant.Keys.User_PRICE, 0);

                    } // yes button
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    } // no button
                }); // onclick

                alertDialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

    private void setTeckits() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("price");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String type="";
                    if (MySharedPreference.getString(getApplicationContext(),
                            Constant.Keys.APP_LANGUAGE, "en").equals("ar")){
                        type  = child.child("name_ar").getValue(String.class);

                    }

                    else
                        type  = child.child("name_en").getValue(String.class);

                    int price = child.child("price").getValue(Integer.class);
                    int discount = child.child("discount").getValue(Integer.class);
                    Vistor_price vistor_price = new Vistor_price(type, price, discount);
                }// for

            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                noAvailableTickets.setVisibility(View.VISIBLE);

            } //onCancelled


        });

    } // setTickets

} // class
