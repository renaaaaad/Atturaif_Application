package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.R;

public class Woking_days_Adapter extends RecyclerView.Adapter<Woking_days_Adapter.MyViewHolder> {
    Context context;
    public List<Open_Days> open_days_list;

    public Woking_days_Adapter(Context context, List<Open_Days> open_days) {
        this.context = context;
        this.open_days_list = open_days;
    }

    @NonNull
    @Override
    public Woking_days_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Woking_days_Adapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.woking_day_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Woking_days_Adapter.MyViewHolder holder, int position) {
        holder.name.setText(open_days_list.get(position).getDay());
        if (MySharedPreference.getString(context, Constant.Keys.APP_LANGUAGE, "en").equals("ar"))
            holder.time.setText(open_days_list.get(position).getOpenAt() + " " + "-" + " " + open_days_list.get(position).getCloseAt());
        else
            holder.time.setText(open_days_list.get(position).getOpenAt() + " " + "-" + " " + open_days_list.get(position).getCloseAt());

    }

    @Override
    public int getItemCount() {
        return open_days_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
        }
    }
} // class
