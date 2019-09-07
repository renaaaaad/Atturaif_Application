package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage_Activity extends AppCompatActivity {

    LinearLayout bookingtab;
    LinearLayout eventtab;
    LinearLayout shoptab;
    LinearLayout startARtab;
    LinearLayout maptab;
    LinearLayout vrtab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_);

        //

        bookingtab=(LinearLayout)findViewById(R.id.bookingtab_button);

        bookingtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Booking_Activity.class));

            }
        });

        //
        eventtab=(LinearLayout)findViewById(R.id.eventtab_button);

        eventtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EventsPage_Activity.class));

            }
        });

        //
        shoptab=(LinearLayout)findViewById(R.id.shoptab_button);

        shoptab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),Booking_Activity.class));

            }
        });

        //
        startARtab=(LinearLayout)findViewById(R.id.startARtab_button);

        startARtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),Booking_Activity.class));

            }
        });

        //

        maptab=(LinearLayout)findViewById(R.id.maptab_button);

        maptab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),Booking_Activity.class));

            }
        });


        //

        vrtab=(LinearLayout)findViewById(R.id.vrtab_button);

        vrtab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),Booking_Activity.class));

            }
        });

        //

    }






}
