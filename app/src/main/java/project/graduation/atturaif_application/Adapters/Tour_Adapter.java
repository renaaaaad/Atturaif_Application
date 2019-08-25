package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.graduation.atturaif_application.Booking_Activity;
import project.graduation.atturaif_application.Objectes.Tour;
import project.graduation.atturaif_application.R;

public class Tour_Adapter extends RecyclerView.Adapter<Tour_Adapter.MyViewHolder> {
    Context context;
    List<Tour> tours;

    public Tour_Adapter(Context context, List<Tour> tours) {
        this.context = context;
        this.tours = tours;
    } //Tour_Adapter

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_item, parent, false);
        return new Tour_Adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Tour tour = tours.get(position);
        holder.title.setText(tour.getType());
        holder.guide.setText(tour.getGuide());
        holder.time.setText(tour.getTime());
        holder.tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking_Activity.setTourType(tour.getType());
            } // on click
        }); // tour click
    } //onBindViewHolder

    @Override
    public int getItemCount() {
        return tours.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, guide, time;
        LinearLayout tour;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            guide = itemView.findViewById(R.id.time);
            time = itemView.findViewById(R.id.guide);
            tour = itemView.findViewById(R.id.tour);
        }
    } //MyViewHolder
} // class
