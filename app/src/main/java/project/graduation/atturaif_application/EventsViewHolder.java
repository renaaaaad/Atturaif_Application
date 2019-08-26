package project.graduation.atturaif_application;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class EventsViewHolder extends RecyclerView.ViewHolder {

        View mview;

        public EventsViewHolder(View itemView){
            super(itemView);
            mview=itemView;
        }


    public void setDetail(Context ctx, String name, String time, String image) {
//            TextView nameE=(TextView)mview.findViewById(R.id.mEventName);
//            TextView timeE=(TextView)mview.findViewById(R.id.mEventTime);
//
//            nameE.setText(name);
//            timeE.setText(time);
//
//            ImageView imgE=(ImageView)mview.findViewById(R.id.EventImage);
//            Picasso.with(ctx).load(image).into(imgE);
        }

}
