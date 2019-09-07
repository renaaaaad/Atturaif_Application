package project.graduation.atturaif_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Splash_page extends BasicActivity {
    LinearLayout background;
    Timer timer;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        HandleDefaultLanguage();
        background = findViewById(R.id.background);
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click = true;
                startActivity(new Intent(Splash_page.this, HomePage_Activity.class));
                finish();
            } //onClick
        }); //onClick

        // set timer
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (click)
                    return;
                startActivity(new Intent(Splash_page.this, HomePage_Activity.class));
                finish();
            } //run
        }, 2000);
    } //onCreate


    private void HandleDefaultLanguage() {
        setAppLocal(MySharedPreference.getString(this, Constant.Keys.APP_LANGUAGE, getSystemLocalLanguage()));
    }//end HandleDefaultLanguage

    private void setAppLocal(String language) {
        //save language in SharedPref
        MySharedPreference.putString(this, Constant.Keys.APP_LANGUAGE, language);

        //set App language
        Locale local = new Locale(language);
        Locale.setDefault(local);
        Configuration config = new Configuration();
        config.locale = local;
        getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

    }//end setAppLocal

    private String getSystemLocalLanguage() {
        Locale local;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            local = getResources().getSystem().getConfiguration().getLocales().get(0);
        }//end if
        else {
            local = getResources().getSystem().getConfiguration().locale;
        }//end else
        return local.getLanguage();
    }//end getSystemLocalLanguage


} // class
