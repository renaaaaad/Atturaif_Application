package project.graduation.atturaif_application;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import project.graduation.atturaif_application.Adapters.Shops_Adapter;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.Objectes.Shops;
import project.graduation.atturaif_application.Objectes.shope_splash_name;

public class ShopsPage extends BasicActivity implements Shops_Adapter.shopListner {


    List<shope_splash_name> shops_name;
    Timer timer;
    LinearLayout progressbar;
    DatabaseReference reference;
    DatabaseReference reference2;

    ArrayList<Shops> shoplist;
    List<Open_Days> dayslist;

    public static List<shope_splash_name> shops;
    RecyclerView recyclerView;
    Shops_Adapter adapter;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_page);
        toolbar = findViewById(R.id.toolbar1);
//        progressbar = findViewById(R.id.progressbar);


//        ProgressBar progressBar = findViewById(R.id.spin_kit);
//        Sprite doubleBounce = new CubeGrid();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressbar.setVisibility(View.GONE);
//                    }
//                });
//            } //run
//        }, 4000);

        //default back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        if(haveNetwork()) {

            shops_name = new ArrayList<>();
            reference = FirebaseDatabase.getInstance().getReference().child("Shops");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
                            String nameAR = ds.child("shopnameAR").getValue(String.class);
                            String image = ds.child("image").getValue(String.class);
                            String desAR=ds.child("DescriptionAR").getValue(String.class);
                            String id = ds.getKey();
                            final shope_splash_name e = new shope_splash_name(id, nameAR, image,desAR);
                            shops_name.add(e);
                        } else {
                            String nameAR = ds.child("shopnameEN").getValue(String.class);
                            String image = ds.child("image").getValue(String.class);
                            String desEN=ds.child("DescriptionEN").getValue(String.class);
                            String id = ds.getKey();
                            final shope_splash_name e = new shope_splash_name(id, nameAR, image,desEN);
                            shops_name.add(e);
                        }
                    } //for
                    if (!shops_name.isEmpty()) {
                        ShopsPage.setShops(shops_name);
                        listShops();

                    }
                } //onDataChange

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ShopsPage.this, "Opsss....Something is wrong", Toast.LENGTH_SHORT).show();

                } //onCancelled
            });


            shoplist = new ArrayList<>();

            reference = FirebaseDatabase.getInstance().getReference().child("Shops");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        String key = ds.getKey();
                        String nameAR = ds.child("shopnameAR").getValue(String.class);
                        String nameEN = ds.child("shopnameEN").getValue(String.class);

                        String image = ds.child("image").getValue(String.class);

                        String DesAR = ds.child("DescriptionAR").getValue(String.class);
                        String DesEN = ds.child("DescriptionEN").getValue(String.class);

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
                                Toast.makeText(ShopsPage.this, "Opsss....Something is wrong", Toast.LENGTH_SHORT).show();

                            }
                        });

                        final Shops s = new Shops(key, nameEN, nameAR, image, DesAR, DesEN, dayslist);

                        shoplist.add(s);

                    }

                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        else if(!haveNetwork())
        {
            Intent intent = new Intent();
            intent.setClass(ShopsPage.this,InternetChecking.class);
            intent.putExtra("Uniqid","ShopsPage");
            startActivity(intent);
        }

    } //onCreate

    private void listShops() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new Shops_Adapter(getApplicationContext(), shops, this);
        recyclerView.setHasFixedSize(true);

        final LinearLayoutManager linearLayoutManager= new LinearLayoutManager
                (this,LinearLayoutManager.HORIZONTAL,false);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        // snapping the scroll items
//        final SnapHelper snapHelper=new GravitySnapHelper(Gravity.START);
        final SnapHelper snapHelper = new LinearSnapHelper();//
        snapHelper.attachToRecyclerView(recyclerView);

        //set timer for default item
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //do something after 1ms
                RecyclerView.ViewHolder viewHolderDefault= recyclerView.
                        findViewHolderForAdapterPosition(0);

                LinearLayout shopparentDefault=viewHolderDefault.itemView.
                        findViewById(R.id.shopparent);

                shopparentDefault.animate().scaleY(1).scaleX(1).setDuration(350).
                     setInterpolator(new AccelerateInterpolator()).start();

                LinearLayout shopbtnDefa=viewHolderDefault.itemView.
        findViewById(R.id.btnshopparent);
                shopbtnDefa.animate().alpha(1).setDuration(300).start();
            }
        },100);


            //set anmite scroll
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerview, int newState) {
                super.onScrollStateChanged(recyclerview, newState);

                if(newState == recyclerview.SCROLL_STATE_IDLE){

                    View view=snapHelper.findSnapView(linearLayoutManager);
                    int pos= linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder=
                            recyclerView.findViewHolderForAdapterPosition(pos);

                    LinearLayout shopparent=viewHolder.itemView.findViewById(R.id.shopparent);

                    shopparent.animate().scaleY(1).scaleX(1).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout shopbtn=viewHolder.itemView.findViewById(R.id.btnshopparent);
                    shopbtn.animate().alpha(1).setDuration(300).start();
                }
                else {

                    View view=snapHelper.findSnapView(linearLayoutManager);
                    int pos= linearLayoutManager.getPosition(view);

                    RecyclerView.ViewHolder viewHolder=
                            recyclerView.findViewHolderForAdapterPosition(pos);

                    LinearLayout shopparent=viewHolder.itemView.findViewById(R.id.shopparent);

                    shopparent.animate().scaleY(0.7f).scaleX(0.7f).setDuration(350).setInterpolator(new AccelerateInterpolator()).start();

                    LinearLayout shopbtn=viewHolder.itemView.findViewById(R.id.btnshopparent);
                    shopbtn.animate().alpha(0).setDuration(300).start();

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    public static void setShops(List<shope_splash_name> shops_list) {
        shops = shops_list;
    }


    @Override
    public void onShopClick(int position) {

        Intent intent = new Intent(this, shopDetails.class);
        Shops clickeditem = shoplist.get(position);

        if (MySharedPreference.getString(getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {

            intent.putExtra(Constant.Keys.SHOP_URL, clickeditem.getImage());
            intent.putExtra(Constant.Keys.SHOP_NAME, clickeditem.getNameAr());
            intent.putExtra(Constant.Keys.SHOP_Des, clickeditem.getDescriptionAR());
            intent.putExtra(Constant.Keys.SHOP_ID, clickeditem.getId());

            startActivity(intent);
        } else {
            intent.putExtra(Constant.Keys.SHOP_URL, clickeditem.getImage());
            intent.putExtra(Constant.Keys.SHOP_NAME, clickeditem.getNameEn());
            intent.putExtra(Constant.Keys.SHOP_Des, clickeditem.getDescriptionEN());
            intent.putExtra(Constant.Keys.SHOP_ID, clickeditem.getId());
            startActivity(intent);
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        progressbar.setVisibility(View.GONE);
    }

    private boolean haveNetwork(){

        boolean have_WIFI=false;
        boolean have_MobileData=false;

        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfos=connectivityManager.getAllNetworkInfo();

        for(NetworkInfo info:networkInfos)
        {
            if(info.getTypeName().equalsIgnoreCase("WIFI"))
                if(info.isConnected())
                    have_WIFI=true;

            if(info.getTypeName().equalsIgnoreCase("MOBILE"))
                if(info.isConnected())
                    have_MobileData=true;

        }

        return have_WIFI || have_MobileData;
    }
} // class
