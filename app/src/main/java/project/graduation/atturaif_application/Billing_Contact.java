package project.graduation.atturaif_application;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.braintreepayments.cardform.view.CardForm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class Billing_Contact extends BasicActivity  {
    //ProgressBar progressBar;
    Toolbar toolbar;
    Button buy;
    CardForm cardForm;
    AlertDialog.Builder alertBuilder;
    String[] descriptionDataEN = {"Book Ticket", "View Ticket", "Payment","Save Ticket"};
    String[] descriptionDataAR = {"حجز تذكرة", "معاينة التذكرة", "الدفع","حفظ التذكرة"};

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                    final Dialog dialog=new Dialog(Billing_Contact.this);
                    dialog.setContentView(R.layout.confirmcard);
                    dialog.setCancelable(false);
                    dialog.show();

                    TextView card = dialog.findViewById(R.id.card);
                    TextView expdate = dialog.findViewById(R.id.expdate);
                    TextView ccv = dialog.findViewById(R.id.ccv);
                    TextView postcode = dialog.findViewById(R.id.postcode);
                    TextView phonenum = dialog.findViewById(R.id.phonenum);

                    if (MySharedPreference.getString(getApplicationContext(),
                            Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {


                        card.setText("رقم البطاقة: " + cardForm.getCardNumber());
                        expdate.setText(" تاريخ انتهاء البطاقة: " + cardForm.getExpirationDateEditText().getText().toString());
                        ccv.setText(" الرقم السري للبطاقة: " + cardForm.getCvv());
                        postcode.setText(" الرمز البريدي: " + cardForm.getPostalCode());
                        phonenum.setText(" رقم الهاتف: " + cardForm.getMobileNumber());

                    }
                    else {

                        card.setText("Card number: " + cardForm.getCardNumber());
                        expdate.setText("Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString());
                        ccv.setText("Card CVV: " + cardForm.getCvv());
                        postcode.setText("Postal code: " + cardForm.getPostalCode());
                        phonenum.setText("Phone number: " + cardForm.getMobileNumber());
                    }



                    Button btn_yes,btn_no;
                    btn_no=dialog.findViewById(R.id.btn_can);
                    btn_yes=dialog.findViewById(R.id.btn_con);

                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Billing_Contact.this, Save_Ticket.class));
                        }
                    });
                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();

                        }
                    });

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

                final Dialog dialog=new Dialog(Billing_Contact.this);
                dialog.setContentView(R.layout.message_goback);
                dialog.setCancelable(false);
                dialog.show();

                Button btn_yes,btn_no;

                btn_no=dialog.findViewById(R.id.btn_no);
                btn_yes=dialog.findViewById(R.id.btn_yes);


                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Billing_Contact.this, HomePage_Activity.class));

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





}//Billing class