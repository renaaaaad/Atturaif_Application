package project.graduation.atturaif_application.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.R;

public class shopdaysitemAdapter extends RecyclerView.Adapter<shopdaysitemAdapter.shopdayViewHolder> {

    private ArrayList<Open_Days> open_days;



    public  shopdaysitemAdapter(ArrayList<Open_Days> op){
        open_days=op;
    }
    @NonNull
    @Override
    public shopdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shopdays_item,parent,false);

        shopdayViewHolder evh= new shopdayViewHolder(view);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull shopdayViewHolder holder, int position) {

        Open_Days op=open_days.get(position);
        String dayy=op.getDay();
        holder.day.setText(op.getDay());
        holder.close.setText(op.getCloseAt());
        holder.open.setText(op.getOpenAt());
    }

    @Override
    public int getItemCount() {
        return open_days.size();
    }

    public static class shopdayViewHolder extends RecyclerView.ViewHolder{

        TextView day;
        TextView open;
        TextView close;

        public shopdayViewHolder(@NonNull View itemView) {
            super(itemView);

            day =  itemView.findViewById(R.id.shopday);
            open =  itemView.findViewById(R.id.shopopentime);
            close =  itemView.findViewById(R.id.shopclosetime);


        }
    }
}
