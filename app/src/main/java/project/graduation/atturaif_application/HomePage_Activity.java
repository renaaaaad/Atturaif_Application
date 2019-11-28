package project.graduation.atturaif_application;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;
import com.unity3d.player.UnityPlayerActivity;


public class HomePage_Activity extends BasicActivity implements View.OnClickListener {

    LinearLayout bookingtab_button, eventtab_button, shoptab_button, startARtab_button, vrtab_button, maptab_button;
    ImageButton more_button;
    SliderView sliderView;
    ImageView slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_);
        // set all the buttons
        bookingtab_button = findViewById(R.id.ticket);
        eventtab_button = findViewById(R.id.event);
        shoptab_button = findViewById(R.id.shop);
        more_button = findViewById(R.id.more_button);
        vrtab_button = findViewById(R.id.VR);
        slider = findViewById(R.id.slider);

        //
        //set the listener
        bookingtab_button.setOnClickListener(this);
        eventtab_button.setOnClickListener(this);
        shoptab_button.setOnClickListener(this);
        more_button.setOnClickListener(this);
        vrtab_button.setOnClickListener(this);


        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar"))
            Glide.with(this)
                    .load(R.drawable.ar_ar)
                    .into(slider);
        else
            Glide.with(this)
                    .load(R.drawable.ar_en)
                    .into(slider);
        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                startActivity(i);
            }
        });

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
            case R.id.VR:
                startActivity(new Intent(HomePage_Activity.this, VR_page.class));


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

        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;

        }

        if (!(have_WIFI || have_MobileData)) {


            if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                Toast toast = Toast.makeText(getApplicationContext(), "لا يوجد إتصال في الانترنت !", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextSize(27);
                text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Network connection is not available!", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextSize(27);
                text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                toast.show();

            }

        } else {


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
        }
    } // call

    private void sendEmail() {

        boolean have_WIFI = false;
        boolean have_MobileData = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();

        for (NetworkInfo info : networkInfos) {
            if (info.getTypeName().equalsIgnoreCase("WIFI"))
                if (info.isConnected())
                    have_WIFI = true;

            if (info.getTypeName().equalsIgnoreCase("MOBILE"))
                if (info.isConnected())
                    have_MobileData = true;

        }

        if (!(have_WIFI || have_MobileData)) {

            if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                Toast toast = Toast.makeText(getApplicationContext(), "لا يوجد إتصال في الانترنت !", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextSize(27);
                text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Network connection is not available!", Toast.LENGTH_LONG);
                View view = toast.getView();
                TextView text = (TextView) view.findViewById(android.R.id.message);
                text.setTextSize(27);
                text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                toast.show();

                // Toast.makeText(HomePage_Activity.this, R.string.connection,Toast.LENGTH_SHORT).show();
            }
        } else {


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

                    if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                        Toast toast = Toast.makeText(getApplicationContext(), "هناك بعض المشاكل ، يرجى المحاولة مرة أخرى لاحقًا", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextSize(23);
                        text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "There is some problems, please try again later", Toast.LENGTH_LONG);
                        View view = toast.getView();
                        TextView text = (TextView) view.findViewById(android.R.id.message);
                        text.setTextSize(23);
                        text.setTypeface(Typeface.createFromAsset(getAssets(), "arabtype.ttf"));
                        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                        toast.show();

                        // Toast.makeText(HomePage_Activity.this, R.string.connection,Toast.LENGTH_SHORT).show();
                    }


                    startActivity(new Intent(getApplicationContext(), HomePage_Activity.class));
                } //onCancelled
            });

        }
    } // send email


} //class
