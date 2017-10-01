package com.rijo.weatherbugdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rijo.weatherbugdemo.R;
import com.rijo.weatherbugdemo.model.Images;

/**
 * Created by rijogeorge on 9/30/17.
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {
    private static final String IMAGE_BASE_URL="https://s3.amazonaws.com/sc.va.util.weatherbug.com/interviewdata/mobilecodingchallenge/";
    private Images images;
    private Context context;
    private ActivityCallBaak activityCallBaak;
    private boolean portrait;
    public ImageRecyclerAdapter(Images images, Context context, ActivityCallBaak activityCallBaak,boolean portrait) {
        this.images=images;
        this.context=context;
        this.activityCallBaak=activityCallBaak;
        this.portrait=portrait;
    }

    @Override
    public ImageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if(portrait)
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_row_portrait, parent, false);
        else
            itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_row_landscape, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageRecyclerAdapter.ViewHolder holder, int position) {

        Glide.with(context)
                .load(IMAGE_BASE_URL+images.getImages().get(position).getFilename())
                .into(holder.imageView);
        holder.tittleTV.setText(images.getImages().get(position).getTitle());
        holder.descriptionTV.setText(images.getImages().get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return images.getImages().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tittleTV,descriptionTV;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tittleTV=(TextView)itemView.findViewById(R.id.titleTV);
            descriptionTV=(TextView)itemView.findViewById(R.id.descriptionTV);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos=getAdapterPosition();
                    activityCallBaak.onItemSelected(pos);
                }
            });
        }
    }

    public interface ActivityCallBaak{
        void onItemSelected(int position);
    }
}
