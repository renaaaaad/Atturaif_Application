package project.graduation.atturaif_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

public class SittingPage extends BasicActivity {
    LinearLayout language;
    Switch notification;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_page);
        //default back button
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        language = findViewById(R.id.language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Language_page.class);
                startActivity(intent);
            }
        }); // onClick

        notification = findViewById(R.id.notification);
        notification.setChecked(MySharedPreference.getBoolean(getApplicationContext(), Constant.Keys.NOTIFICATION, true));
        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MySharedPreference.putBoolean(getApplicationContext(), Constant.Keys.NOTIFICATION, b);
            }
        });


    } //onCreate

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }
} //class
