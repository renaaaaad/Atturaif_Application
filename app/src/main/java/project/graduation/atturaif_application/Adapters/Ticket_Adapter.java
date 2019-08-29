package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.R;
import project.graduation.atturaif_application.Objectes.Vistor_price;

public class Ticket_Adapter extends RecyclerView.Adapter<Ticket_Adapter.MyViewHolder> {
    Context context;
    List<Vistor_price> vistor_prices;

    public Ticket_Adapter(Context context, List<Vistor_price> vistor_prices) {
        this.context = context;
        this.vistor_prices = vistor_prices;
    } //Ticket_Adapter

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ticket_item, parent, false);
        return new MyViewHolder(itemView);
    } //MyViewHolder

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Vistor_price vistor_price = vistor_prices.get(position);
        holder.visitor_type.setText(vistor_price.getType());
        double price = vistor_price.getPrice();
        double descount = vistor_price.getDiscount();
        if (descount == 0)
            holder.price.setText(Double.toString(price) + " " + context.getString(R.string.s_r));
        else {
            descount = descount / 100;
            price = price * descount;
            holder.price.setText(Double.toString(price) + " " + context.getString(R.string.s_r));
        } // else

        final double finalPrice = price;
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = holder.numberOfTickets.getText().toString();
                int finalValue = Integer.parseInt(value);
                if (finalValue == 50)
                    return;
                finalValue = finalValue + 1;
                holder.numberOfTickets.setText(Integer.toString(finalValue));
                // to save the data in shared preference
                float price_shared = MySharedPreference.getFolat(context, Constant.Keys.User_PRICE, 0);
                MySharedPreference.putFloat(context, Constant.Keys.User_PRICE, price_shared + (float) finalPrice);
            } //onClick
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = holder.numberOfTickets.getText().toString();
                int finalValue = Integer.parseInt(value);
                if (finalValue == 0)
                    return;
                finalValue = finalValue - 1;
                holder.numberOfTickets.setText(Integer.toString(finalValue));
                // to save the data in shared preference
                float price_shared = MySharedPreference.getFolat(context, Constant.Keys.User_PRICE, 0);
                MySharedPreference.putFloat(context, Constant.Keys.User_PRICE, price_shared - (float) finalPrice);
            } //onClick
        });

    } //onBindViewHolder

    @Override
    public int getItemCount() {
        return vistor_prices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView visitor_type, price;
        ImageButton add, remove;
        EditText numberOfTickets;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            visitor_type = itemView.findViewById(R.id.visitor_type);
            price = itemView.findViewById(R.id.price);
            add = itemView.findViewById(R.id.add);
            remove = itemView.findViewById(R.id.remove);
            numberOfTickets = itemView.findViewById(R.id.numberOfTickets);
        }
    } //MyViewHolder
} //class
