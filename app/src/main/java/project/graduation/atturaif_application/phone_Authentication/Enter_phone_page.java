package project.graduation.atturaif_application.phone_Authentication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import project.graduation.atturaif_application.R;

public class Enter_phone_page extends AppCompatActivity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.enter_phone);

        editText=findViewById(R.id.editTextPhone);
        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number= editText.getText().toString();
                if(number.isEmpty() || number.length()<9)
                {
                    editText.setError("Valid number is required ...");
                    editText.requestFocus();
                    return;
                }
                String phonenumber="+966"+number;
                Intent intent =new Intent(Enter_phone_page.this, Enter_code_page.class);
                intent.putExtra("phonenumber",phonenumber);
                startActivity(intent);
            }
        });
    }


}
