package project.graduation.atturaif_application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Save_Ticket extends AppCompatActivity {
    Toolbar toolbar;
    TextView price_total, date;
    static Dialog dialogPre;

    private int STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save__ticket);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = findViewById(R.id.date);
        price_total = findViewById(R.id.price_total);
        float price = MySharedPreference.getFolat(getApplicationContext(), Constant.Keys.User_PRICE, 0);
        price_total.setText(Float.toString(price) + " " + getApplicationContext().getString(R.string.s_r));
        date.setText(MySharedPreference.getString(getApplicationContext(), Constant.Keys.BOOKING_DATE, ""));
        MySharedPreference.putFloat(getApplicationContext(), Constant.Keys.User_PRICE, 0);

        StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.your_state_progress_bar_id);

        LinearLayout layoutAR=findViewById(R.id.enlayout_ar);
        LinearLayout layoutEN=findViewById(R.id.enlayout_en);

        if(MySharedPreference.getString(getApplicationContext(),Constant.Keys.APP_LANGUAGE,"en").equals("ar")) {
            layoutEN.setVisibility(View.GONE);
            layoutAR.setVisibility(View.VISIBLE);
        }


    }
    //android:background="#8b7d6b"
    //*******writeExternalStorage********//

    public void writeExternalStorage(View view) {
        String state;

        if (!(ContextCompat.checkSelfPermission(Save_Ticket.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            requestStoragePermission();

        }



        state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File Root = Environment.getExternalStorageDirectory();
            File Dir = new File(Root.getAbsolutePath() + "/Downloads");
            if (!Dir.exists()) {

                Dir.mkdir();
            }
            File file = new File(Dir, "Ticket Information.txt");
            String Message ="Date:"+ date.getText().toString()+"\n"+"Price:"+ price_total.getText().toString();


            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(Message.getBytes());
                fileOutputStream.close();
                Toast.makeText(getApplicationContext(), "Ticket saved in Storage/Downloads", Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "SD card not found", Toast.LENGTH_LONG).show();
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)){


            dialogPre=new Dialog(Save_Ticket.this);
            dialogPre.setContentView(R.layout.premissionlayout);
            Button btn_yes,btn_no;
            dialogPre.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            btn_no=dialogPre.findViewById(R.id.btn_no);
            btn_yes=dialogPre.findViewById(R.id.btn_yes);

            dialogPre.setCancelable(false);
            dialogPre.show();


            btn_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(Save_Ticket.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                }
            });
            btn_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogPre.dismiss();

                }
            });

        }

        else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(MySharedPreference.getString(getApplicationContext(),Constant.Keys.APP_LANGUAGE,"en").equals("ar")) {
                    Toast.makeText(this, "تم تمكين الوصول", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
             }
                dialogPre.dismiss();


            } else {


                if(MySharedPreference.getString(getApplicationContext(),Constant.Keys.APP_LANGUAGE,"en").equals("ar")) {
                    Toast.makeText(this, "تم رفض الوصول", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                }
                dialogPre.dismiss();
            }
        }
    }


    @SuppressLint("ResourceType")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                final Dialog dialog=new Dialog(Save_Ticket.this);
                dialog.setContentView(R.layout.message_goback);
                dialog.setCancelable(false);
                dialog.show();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btn_yes,btn_no;

                btn_no=dialog.findViewById(R.id.btn_no);
                btn_yes=dialog.findViewById(R.id.btn_yes);


                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(Save_Ticket.this, HomePage_Activity.class));
                        MySharedPreference.putFloat(getApplicationContext(), Constant.Keys.User_PRICE, 0);

                    }
                });
                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });




            default:
                return super.onOptionsItemSelected(item);
        } // switch
    } // onOptionsItemSelected


}
