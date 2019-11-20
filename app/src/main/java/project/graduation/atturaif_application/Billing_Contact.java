package project.graduation.atturaif_application;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class Billing_Contact extends AppCompatActivity  {
    //ProgressBar progressBar;
    Toolbar toolbar;
    Button buy;
    CardForm cardForm;
    AlertDialog.Builder alertBuilder;

    StateProgressBar stateProgressBar;
    private static final String TAG = "Billing_Contact";
    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    private FirebaseAuth.AuthStateListener mAuthListener;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing__contact);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);
        // stateProgressBar.setStateDescriptionData(descriptionData);
        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnBuy);

        stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);





        // progressBar = (ProgressBar) findViewById(R.id.progressbar);


        //firebase uthentication
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();







        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        //****payment****//

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(Billing_Contact.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(Billing_Contact.this);




                    if (MySharedPreference.getString(getApplicationContext(),
                            Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {

                        alertBuilder.setTitle("تأكيد عملية الشراء");
                        alertBuilder.setMessage("رقم البطاقة:" + cardForm.getCardNumber() + "\n" +
                                "تاريخ انتهاء البطاقة:" + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                                "الرقم السري للبطاقة:" + cardForm.getCvv() + "\n" +
                                "الرمز البريدي:" + cardForm.getPostalCode() + "\n" +
                                "رقم الهاتف:" + cardForm.getMobileNumber());
                        alertBuilder.setPositiveButton("تأكيد", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(Billing_Contact.this, Save_Ticket.class));
                            }
                        });
                        alertBuilder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                    }
                    else{

                        alertBuilder.setTitle("Confirm before purchase");
                        alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                                "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                                "Card CVV: " + cardForm.getCvv() + "\n" +
                                "Postal code: " + cardForm.getPostalCode() + "\n" +
                                "Phone number: " + cardForm.getMobileNumber());
                        alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                startActivity(new Intent(Billing_Contact.this, Save_Ticket.class));
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                    }

                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {

                    if (MySharedPreference.getString(getApplicationContext(),
                            Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                        Toast.makeText(Billing_Contact.this, "الرجاء تأكد من ملء النموذج", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(Billing_Contact.this, "Please complete the form", Toast.LENGTH_LONG).show();

                    }
                }
            }
        });
        //*******finished payment*********//
    }//onCreate
    // this method to show dialog when the user clicks back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Billing_Contact.this);
                LayoutInflater inflater = getLayoutInflater();
                View view2 = inflater.inflate(R.layout.message_goback, null);
                alertDialog.setCustomTitle(view2);
                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Billing_Contact.this, Splash_page.class));
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

}//Billing class