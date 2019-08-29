package project.graduation.atturaif_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CheckNumber_Activity extends AppCompatActivity {
    Button sendCode;
    EditText phoneNumber;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number_);
        sendCode = findViewById(R.id.sendCode);
        phoneNumber = findViewById(R.id.phoneNumber);
        phone = "+966" + phoneNumber.getText().toString();
        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckNumber_Activity.this, CheckPhoneCode_Activity.class);
                intent.putExtra("phone", phoneNumber.getText().toString());
                startActivity(intent);
                finish();
            }
        }); // onClick
    } //onCreate
} // class
