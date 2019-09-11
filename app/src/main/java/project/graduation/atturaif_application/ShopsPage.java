package project.graduation.atturaif_application;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import project.graduation.atturaif_application.Adapters.Shops_Adapter;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Shops;
import project.graduation.atturaif_application.Objectes.shope_splash_name;

public class ShopsPage extends BasicActivity implements Shops_Adapter.shopListner {

    public static final String EXTRA_URL = "imageurl";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_Des = "description";
    public static final String EXTRA_ID="id";


    DatabaseReference reference;
    DatabaseReference reference2;

    ArrayList<Shops> shoplist;
    List<Open_Days> dayslist;

    public static List<shope_splash_name> shops;
    RecyclerView recyclerView;
    Shops_Adapter adapter;
    Toolbar toolbar;
    ProgressBar progressbar;
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_page);
        toolbar = findViewById(R.id.toolbar1);
        progressbar = findViewById(R.id.progressbar);

        //default back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Shops_Adapter(getApplicationContext(), shops,this);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        shoplist=new ArrayList<>();

            reference = FirebaseDatabase.getInstance().getReference().child("Shops");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String key=ds.getKey();
                        String nameAR = ds.child("shopnameAR").getValue(String.class);
                        String nameEN = ds.child("shopnameEN").getValue(String.class);

                        String image = ds.child("image").getValue(String.class);

                        String DesAR =ds.child("DescriptionAR").getValue(String.class);
                        String DesEN=ds.child("DescriptionEN").getValue(String.class);

                        reference2 = FirebaseDatabase.getInstance().getReference().child("Shops").child("days");
                        reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {

                                    Open_Days e = ds2.getValue(Open_Days.class);

                                    dayslist.add(e);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        final Shops s=new Shops(key,nameEN, nameAR, image, DesAR, DesEN, dayslist) ;

                               shoplist.add(s);

                                }

                            }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    } //onCreate




    public static void setShops(List<shope_splash_name> shops_list) {
        shops = shops_list;
    }


    @Override
    public void onShopClick(int position) {

        Intent intent = new Intent(this, shopDetails.class);
        Shops clickeditem = shoplist.get(position);

        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {

            intent.putExtra(EXTRA_URL, clickeditem.getImage());
            intent.putExtra(EXTRA_NAME, clickeditem.getNameAr());
            intent.putExtra(EXTRA_Des, clickeditem.getDescriptionAR());
            intent.putExtra(EXTRA_ID, clickeditem.getId());

            startActivity(intent);
        }

        else{
            intent.putExtra(EXTRA_URL, clickeditem.getImage());
            intent.putExtra(EXTRA_NAME, clickeditem.getNameEn());
            intent.putExtra(EXTRA_Des, clickeditem.getDescriptionEN());
            intent.putExtra(EXTRA_ID, clickeditem.getId());
            startActivity(intent);
        }

    }
} // class
