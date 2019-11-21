package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.Objectes.Shops;
import project.graduation.atturaif_application.Objectes.shope_splash_name;
import project.graduation.atturaif_application.R;

public class Shops_Adapter extends RecyclerView.Adapter<Shops_Adapter.MyViewHolder> {
    Context context;
    List<shope_splash_name> shops;
    shopListner mshopListener;

    public interface shopListner{
        void onShopClick(int position);
    }

    public Shops_Adapter(Context context, List<shope_splash_name> shops,shopListner mshopListener) {
        this.context = context;
        this.shops = shops;
        this.mshopListener=mshopListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.shops_item, parent, false);
        return new MyViewHolder(view,mshopListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int num=0;
        shope_splash_name shope = shops.get(position);
        holder.name.setText(shope.getName());

        String dess=shope.getDes();

        if(shope.getDes().length()>30) {
            dess = shope.getDes().substring(0, 30);
        }
        holder.des.setText(dess+" ....");
        Picasso.with(context).load(shope.getImage()).into(holder.imageE);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,des;
        ImageView imageE;
        shopListner shopListner;
        Button btnMore;

        public MyViewHolder(@NonNull View itemView, final shopListner shopListner) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            des=itemView.findViewById(R.id.des);
            imageE = itemView.findViewById(R.id.shopimage);
            btnMore=itemView.findViewById((R.id.btnMore));
            this.shopListner=shopListner;

            if (MySharedPreference.getString(context.getApplicationContext(), Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
           btnMore.setText("المزيد");
            }
            else {
                btnMore.setText("More");

            }


                btnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(shopListner != null){
                        int position=getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            shopListner.onShopClick(position);
                        }
                    }
                }
            });
//            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

        }
    }

} // class
