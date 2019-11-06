package project.graduation.atturaif_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class SittingPage extends BasicActivity {
    LinearLayout language;
    Toolbar toolbar;
    ImageView nextArrow;

    //setAutoMirrored(true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_page);
        //default back button
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nextArrow = findViewById(R.id.nextArrow);
        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.next_arrow_ar)
                    .into(nextArrow);        }
        language = findViewById(R.id.language);
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Language_page.class);
                startActivity(intent);
            }
        }); // onClick



    } //onCreate

    @Override
    protected void onRestart() {
        super.onRestart();
        startActivity(getIntent());
    }
} //class
