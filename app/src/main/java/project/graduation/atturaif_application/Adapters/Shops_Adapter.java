package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public Shops_Adapter(Context context, List<shope_splash_name> shops) {
        this.context = context;
        this.shops = shops;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Shops_Adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shops_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        shope_splash_name shope = shops.get(position);
        holder.name.setText(shope.getName());
        Picasso.with(context).load(shope.getImage()).into(holder.imageE);
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageE;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageE = itemView.findViewById(R.id.shopimage);

        }
    }
} // class
