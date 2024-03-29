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

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.Objectes.Events;
import project.graduation.atturaif_application.R;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    Context context;
    ArrayList<Events> events;
    //
    private onItemClickListner mlistner;


    //

    public interface onItemClickListner {
        void onItemClick(int position);
    }

    //
    public void setOnItemClickListener(onItemClickListner listner) {
        mlistner = listner;

    }


    public EventsAdapter(Context c, ArrayList<Events> e) {

        context = c;
        events = e;

    }

    @NonNull
    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.show_events_rowlayout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (MySharedPreference.getString(context, Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            holder.nameE.setText(events.get(position).getEventnameAR());
            holder.timeE.setText(replaceArabicNumbers(events.get(position).getEventTime()));
            Picasso.with(context).load(events.get(position).getImage()).into(holder.imageE);
        } // if  ar
        else {
            holder.nameE.setText(events.get(position).getEventnameEN());
            holder.timeE.setText(events.get(position).getEventTime());
            Picasso.with(context).load(events.get(position).getImage()).into(holder.imageE);
        }
    } //onBindViewHolder

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameE, timeE;
        ImageView imageE;


        public MyViewHolder(View itemview) {
            super(itemview);

            nameE = (TextView) itemview.findViewById(R.id.mEventName);
            timeE = (TextView) itemview.findViewById(R.id.mEventTime);
            imageE = (ImageView) itemview.findViewById(R.id.EventImage);

            //
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistner != null) {
                        int postion = getAdapterPosition();

                        if (postion != RecyclerView.NO_POSITION) {
                            mlistner.onItemClick(postion);
                            MySharedPreference.putBoolean(context, Constant.Keys.EVENTS, true);

                        }
                    }
                }
            });


        }
    }

    public String replaceArabicNumbers(String original) {
        return original.toString().replaceAll("1", "١")
                .replaceAll("2", "٢")
                .replaceAll("3", "٣")
                .replaceAll("4", "٤")
                .replaceAll("5", "٥")
                .replaceAll("6", "٦")
                .replaceAll("7", "٧")
                .replaceAll("8", "٨")
                .replaceAll("9", "٩")
                .replaceAll("0", "٠");
    }

} // class
