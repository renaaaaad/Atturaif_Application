package project.graduation.atturaif_application;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.kofigyan.stateprogressbar.StateProgressBar;

public class Payment extends BasicActivity {

    String[] descriptionDataEN = {"Book Ticket", "View Ticket", "Payment","Save Ticket"};
    String[] descriptionDataAR = {"حجز تذكرة", "معاينة التذكرة", "الدفع","حفظ التذكرة"};
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

        if (MySharedPreference.getString(getApplicationContext(),
                Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {

            stateProgressBar.setStateDescriptionData(descriptionDataAR);


        }else{

            stateProgressBar.setStateDescriptionData(descriptionDataEN);
        }

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

                final Dialog dialog=new Dialog(Payment.this);
                dialog.setContentView(R.layout.message_goback);
                dialog.setCancelable(false);
                dialog.show();

                Button btn_yes,btn_no;

                btn_no=dialog.findViewById(R.id.btn_no);
                btn_yes=dialog.findViewById(R.id.btn_yes);


                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Payment.this, HomePage_Activity.class));
                        MySharedPreference.putFloat(getApplicationContext(), Constant.Keys.User_PRICE, 0);

                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });




            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected

} // class
