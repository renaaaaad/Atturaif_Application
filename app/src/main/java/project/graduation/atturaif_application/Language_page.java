package project.graduation.atturaif_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;


public class Language_page extends BasicActivity implements View.OnClickListener {
    CheckedTextView Arabic, English;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_page);
        Arabic = findViewById(R.id.arabic);
        English = findViewById(R.id.english);
        Arabic.setOnClickListener(this);
        English.setOnClickListener(this);
        toolbar = findViewById(R.id.toolbar);
        //toggle views
        Arabic.toggle();
        English.toggle();

        //default back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //check current language
        checkCurrentLanguage();
    }//end onCreate()

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;

    }//end onSupportNavigateUp()

    private void checkCurrentLanguage() {
        String Current_lang = MySharedPreference.getString(this, Constant.Keys.APP_LANGUAGE, "en");
        if (Current_lang.equals("ar")) {
            Arabic.setCheckMarkDrawable(R.drawable.check);
            Arabic.setChecked(true);
        }//end if
        else {
            English.setCheckMarkDrawable(R.drawable.check);
            English.setChecked(true);
        }//end else
    }//end checkCurrentLanguage


    private void setLanguage(String language) {
        MySharedPreference.putString(this, Constant.Keys.APP_LANGUAGE, language);
        Intent intent = new Intent(getApplicationContext(), Language_page.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //  intent.putExtra("language","language");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }//end setLanguage

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arabic:
                Arabic.setCheckMarkDrawable(R.drawable.check);
                Arabic.setChecked(true);
                English.setCheckMarkDrawable(null);
                English.setChecked(false);
                setLanguage("ar");
                break;
            case R.id.english:

                English.setCheckMarkDrawable(R.drawable.check);
                English.setChecked(true);
                Arabic.setCheckMarkDrawable(null);
                Arabic.setChecked(false);
                //set App language
                setLanguage("en");
                break;
        }// switch
    }// on click


}//end class
