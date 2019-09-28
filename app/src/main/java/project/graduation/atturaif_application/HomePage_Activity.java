package project.graduation.atturaif_application;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import project.graduation.atturaif_application.Adapters.SliderAdapterExample;


public class HomePage_Activity extends BasicActivity implements View.OnClickListener {

    LinearLayout bookingtab_button, eventtab_button, shoptab_button, startARtab_button, vrtab_button, maptab_button;
    ImageButton more_button;
    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_);
        // set all the buttons
        bookingtab_button = findViewById(R.id.ticket);
        eventtab_button = findViewById(R.id.event);
        shoptab_button = findViewById(R.id.shop);
        startARtab_button = findViewById(R.id.AR);
        maptab_button = findViewById(R.id.map);
        more_button = findViewById(R.id.more_button);
        sliderView = findViewById(R.id.imageSlider);

        sliderView.setSliderAdapter(new SliderAdapterExample(getApplicationContext()));
        sliderView.startAutoCycle();
        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        //
        //set the listener
        bookingtab_button.setOnClickListener(this);
        eventtab_button.setOnClickListener(this);
        shoptab_button.setOnClickListener(this);
        startARtab_button.setOnClickListener(this);
        maptab_button.setOnClickListener(this);
        more_button.setOnClickListener(this);


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
            case R.id.more_button:

                PopupMenu popup = new PopupMenu(HomePage_Activity.this, more_button);
                popup.getMenuInflater().inflate(R.menu.home_page_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                    public boolean onMenuItemClick(MenuItem item) {
                        String select = item.getTitle().toString();
                        switch (select) {
                            case "Settings":
                                startActivity(new Intent(HomePage_Activity.this, SittingPage.class));
                                break;
                            case "الإعدادات":
                                startActivity(new Intent(HomePage_Activity.this, SittingPage.class));
                                break;
                            case "Email us":
                                sendEmail();
                                break;
                            case "راسلنا":
                                sendEmail();
                                break;
                            case "Call us":
                                call();
                                break;
                            case "إتصل بنا":
                                call();
                                break;
                            case "About us":
                                startActivity(new Intent(HomePage_Activity.this, Aboutus_Page.class));
                                break;
                            case "عن الطريف ":
                                startActivity(new Intent(HomePage_Activity.this, Aboutus_Page.class));
                                break;
                        }// switch

                        return true;
                    } //onMenuItemClick
                });

                popup.show();//showing popup menu
                break;

            case R.id.shop:
                startActivity(new Intent(HomePage_Activity.this, ShopsPage.class));

                break;
        }// switch
    } //onClick


    private void call() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contact_information").child("phone");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone = dataSnapshot.getValue(String.class);
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "03030303335", null)));
            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), R.string.error_message,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
            } //onCancelled
        });

    } // call

    private void sendEmail() {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("contact_information").child("Email");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Email = dataSnapshot.getValue(String.class);
                Intent mailIntent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:?subject=" + getString(R.string.Atturaif_Help_Message) + "&body=" + getString(R.string.body_message) + "&to=" + Email);
                mailIntent.setData(data);
                startActivity(Intent.createChooser(mailIntent, "Send mail..."));
            } //onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), R.string.error_message,
                        Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
            } //onCancelled
        });


    } // send email
} //class
