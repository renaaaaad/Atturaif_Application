package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import java.util.Objects;

public class HomePage_Activity extends BasicActivity implements View.OnClickListener {

    LinearLayout bookingtab_button, eventtab_button, shoptab_button, startARtab_button, vrtab_button, maptab_button;
    ImageButton more_button;

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
        more_button = findViewById(R.id.more_button);

        //set the listener
        bookingtab_button.setOnClickListener(this);
        eventtab_button.setOnClickListener(this);
        shoptab_button.setOnClickListener(this);
        startARtab_button.setOnClickListener(this);
        vrtab_button.setOnClickListener(this);
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
                            case "Sitting":
                                startActivity(new Intent(HomePage_Activity.this, SittingPage.class));
                                break;
                            case "الإعدادات":
                                startActivity(new Intent(HomePage_Activity.this, SittingPage.class));
                                break;
                        }// switch

                        return true;
                    } //onMenuItemClick
                });

                popup.show();//showing popup menu
                break;
            case R.id.vrtab_button:
                startActivity(new Intent(HomePage_Activity.this, VR_page.class));

                break;
        }// switch
    } //onClick
} //class
