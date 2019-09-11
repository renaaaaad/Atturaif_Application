package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.Objectes.Open_Days;
import project.graduation.atturaif_application.R;

public class shopdaysitemAdapter extends RecyclerView.Adapter<shopdaysitemAdapter.shopdayViewHolder> {

    private ArrayList<Open_Days> open_days;
    Context context;




    public  shopdaysitemAdapter(Context c,ArrayList<Open_Days> op){
        context=c;

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

        if (MySharedPreference.getString(context, Constant.Keys.APP_LANGUAGE, "en").equals("ar")) {
            Open_Days op=open_days.get(position);
            holder.day.setText(replaceArabicDays(op.getDay()));
            holder.close.setText(replaceArabicNumbers(op.getCloseAt()));
            holder.open.setText(replaceArabicNumbers(op.getOpenAt()));

        } // if  ar
        else {
            Open_Days op=open_days.get(position);
            holder.day.setText(op.getDay());
            holder.close.setText(op.getCloseAt());
            holder.open.setText(op.getOpenAt());
        }



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



    public String replaceArabicDays(String original) {
        return original.toString().replaceAll("Saturday","السبت")
                .replaceAll("saturday","السبت")
                .replaceAll("Sunday","الاحد")
                .replaceAll("sunday","الاحد")
                .replaceAll("Monday","الاثنين")
                .replaceAll("monday","الاثنين")
                .replaceAll("Tuesday","الثلاثاء")
                .replaceAll("tuesday","الثلاثاء")
                .replaceAll("Wednesday","الاربعاء")
                .replaceAll("wednesday","الاربعاء")
                .replaceAll("Thursday","الخميس")
                .replaceAll("thursday","الخميس")
                .replaceAll("Friday","الجمعه")
                .replaceAll("friday","الجمعه");
    }

    public String replaceArabicNumbers(String original) {
        return original.toString().replaceAll("1","١")
                .replaceAll("2","٢")
                .replaceAll("3","٣")
                .replaceAll("4","٤")
                .replaceAll("5","٥")
                .replaceAll("6","٦")
                .replaceAll("7","٧")
                .replaceAll("8","٨")
                .replaceAll("9","٩")
                .replaceAll("0","٠");
    }
}
