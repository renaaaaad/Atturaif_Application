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

import java.util.ArrayList;

import project.graduation.atturaif_application.Objectes.Events;
import project.graduation.atturaif_application.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder>{

    Context context;
    ArrayList<Events> events;

    public EventsAdapter(Context c, ArrayList<Events> e){

        context=c;
        events=e;

    }

    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.show_events_rowlayout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        String day=events.get(position).getDay();
//        String month=events.get(position).getMonth();
//
//        if(month)
        holder.nameE.setText(events.get(position).getEventname());
        holder.timeE.setText(events.get(position).getEventTime());
        Picasso.with(context).load(events.get(position).getImage()).into(holder.imageE);

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameE,timeE;
        ImageView imageE;


        public MyViewHolder(View itemview) {
            super(itemview);

            nameE=(TextView)itemview.findViewById(R.id.mEventName);
            timeE=(TextView)itemview.findViewById(R.id.mEventTime);
            imageE=(ImageView)itemview.findViewById(R.id.EventImage);


        }
    }
}
