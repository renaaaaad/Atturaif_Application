package project.graduation.atturaif_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CheckNumber_Activity extends AppCompatActivity {
//fadia
private EditText phoneNumber;
    private EditText code;
    private Button sendCode;
    String phone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number_);


        phoneNumber =(EditText) findViewById(R.id.phoneNumber);
        code =(EditText) findViewById(R.id.code);
        sendCode=(Button) findViewById(R.id.sendCode);
        //to do action
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone="+966" + phoneNumber.getText().toString();
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                       phone,
                        60,
                        TimeUnit.SECONDS,
                        CheckNumber_Activity.this,
                        mCallbacks

                );
            }
        });

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        };

    } //onCreate

    //fadia

} // class
