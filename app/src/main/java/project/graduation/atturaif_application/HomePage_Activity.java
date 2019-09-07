package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage_Activity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout bookingtab_button, eventtab_button, shoptab_button, startARtab_button, vrtab_button, maptab_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_);
        // set all the buttons
        bookingtab_button = findViewById(R.id.ticket);
        eventtab_button = findViewById(R.id.event);
        shoptab_button = findViewById(R.id.shop);
        startARtab_button = findViewById(R.id.AR);
        vrtab_button = findViewById(R.id.vrtab_button);
        maptab_button = findViewById(R.id.map);


        //set the listener
        bookingtab_button.setOnClickListener(this);
        eventtab_button.setOnClickListener(this);
        shoptab_button.setOnClickListener(this);
        startARtab_button.setOnClickListener(this);
        vrtab_button.setOnClickListener(this);
        maptab_button.setOnClickListener(this);

    } // onCreate


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ticket:
                startActivity(new Intent(HomePage_Activity.this, Booking_Activity.class));
                break;
            case R.id.event:
                startActivity(new Intent(HomePage_Activity.this, EventsPage_Activity.class));
                break;
        }// switch
    } //onClick
} //class
