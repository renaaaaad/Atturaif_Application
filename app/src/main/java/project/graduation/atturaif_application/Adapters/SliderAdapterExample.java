package project.graduation.atturaif_application.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import project.graduation.atturaif_application.Constant;
import project.graduation.atturaif_application.MySharedPreference;
import project.graduation.atturaif_application.R;
import project.graduation.atturaif_application.VR_page;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

        switch (position) {

            case 0:
                if (MySharedPreference.getString(context, Constant.Keys.APP_LANGUAGE, "en").equals("ar"))
                    Glide.with(viewHolder.itemView)
                            .load(R.drawable.vr_tour)
                            .into(viewHolder.imageViewBackground);
                else
                    Glide.with(viewHolder.itemView)
                            .load(R.drawable.vr_tour)
                            .into(viewHolder.imageViewBackground);
                viewHolder.imageViewBackground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, VR_page.class));
                    }
                });
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.vr_tour)
                        .into(viewHolder.imageViewBackground);
                break;


        } // switch

    }//onBindViewHolder

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 2;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView;
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);

        }
    }
}
