package project.graduation.atturaif_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import java.util.Locale;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set App language method
        HandleDefaultLanguage();

    }//end onCreate


    private void HandleDefaultLanguage(){
        setAppLocal(MySharedPreference.getString(this,Constant.Keys.APP_LANGUAGE,getSystemLocalLanguage()));
    }//end HandleDefaultLanguage

    private void setAppLocal(String language) {
        //save language in SharedPref
        MySharedPreference.putString(this,Constant.Keys.APP_LANGUAGE,language);

        //set App language
        Locale local = new Locale(language);
        Locale.setDefault(local);
        Configuration config = new Configuration();
        config.locale = local;
        getResources().updateConfiguration(config, this.getResources().getDisplayMetrics());

    }//end setAppLocal

    private String getSystemLocalLanguage() {
        Locale local;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            local = getResources().getSystem().getConfiguration().getLocales().get(0); }//end if
        else {
            local = getResources().getSystem().getConfiguration().locale; }//end else
        return local.getLanguage();
    }//end getSystemLocalLanguage



}//end class