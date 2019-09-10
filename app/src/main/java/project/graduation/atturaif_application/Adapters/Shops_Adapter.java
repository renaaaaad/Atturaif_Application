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
        shope_splash_name shope = shops.get(position);
        holder.name.setText(shope.getName());
        Picasso.with(context).load(shope.getImage()).into(holder.imageE);


    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        ImageView imageE;
        shopListner shopListner;

        public MyViewHolder(@NonNull View itemView, shopListner shopListner) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imageE = itemView.findViewById(R.id.shopimage);
            this.shopListner=shopListner;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            shopListner.onShopClick(getAdapterPosition());
        }
    }
} // class
