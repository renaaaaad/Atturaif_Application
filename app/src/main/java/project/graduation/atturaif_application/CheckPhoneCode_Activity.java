package project.graduation.atturaif_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CheckPhoneCode_Activity extends AppCompatActivity {
    /////fadia

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_phone_code_);


        mAuth=FirebaseAuth.getInstance();




    } //onCreate

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser == null) {
            Intent authIntent= new Intent(CheckPhoneCode_Activity.this, CheckNumber_Activity.class);
            startActivity(authIntent);
            finish();
        }
    }




} // class
