package project.graduation.atturaif_application;



import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

public class MyFirebaseInstanceIdService  {

    private static final String TAG = "MyFirebaseIIDService";


    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token in logcat
        Log.e(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}