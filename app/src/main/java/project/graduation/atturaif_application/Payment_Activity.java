package project.graduation.atturaif_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.braintreepayments.cardform.view.CardForm;

import static project.graduation.atturaif_application.Booking_Activity.alertDialog;

public class Payment_Activity extends AppCompatActivity {
    TextView price_total, type, date;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        price_total = findViewById(R.id.price_total);
        type = findViewById(R.id.type);
        date = findViewById(R.id.date);
        float price = MySharedPreference.getFolat(getApplicationContext(), Constant.Keys.User_PRICE, 0);
        price_total.setText(Float.toString(price) + " " + getApplicationContext().getString(R.string.s_r));
        type.setText(MySharedPreference.getString(getApplicationContext(), Constant.Keys.TOUT_TYPE, "Guided"));
        date.setText(MySharedPreference.getString(getApplicationContext(), Constant.Keys.BOOKING_DATE, ""));
        MySharedPreference.putFloat(getApplicationContext(), Constant.Keys.User_PRICE, 0);
        CardForm cardForm = findViewById(R.id.card_form);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .cardholderName(CardForm.FIELD_REQUIRED)
                .postalCodeRequired(false)
                .mobileNumberRequired(false)
                .setup(Payment_Activity.this);

    } //onCreate

    // this method to show dialog when the user clicks back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Payment_Activity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view2 = inflater.inflate(R.layout.message_goback, null);
                alertDialog.setCustomTitle(view2);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Payment_Activity.this, MainActivity.class));
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

} // class
