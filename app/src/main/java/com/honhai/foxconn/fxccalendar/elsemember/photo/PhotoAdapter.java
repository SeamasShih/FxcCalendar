package com.honhai.foxconn.fxccalendar.elsemember.photo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.honhai.foxconn.fxccalendar.R;

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PhotoData> photos;
    private Context context;
    private RecyclerView recyclerView;
    private int chosen = -1;

    PhotoAdapter(Context context, RecyclerView recyclerView, ArrayList<PhotoData> photos) {
        this.photos = photos;
        this.context = context;
        this.recyclerView = recyclerView;
    }

    public PhotoData getChosenData() {
        if (chosen != -1)
            return photos.get(chosen);
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_photo_select_item, viewGroup, false);
        return new PhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        PhotoRecyclerViewItemImageView imageView = ((PhotoViewHolder) viewHolder).imageView;
        if (position == chosen)
            imageView.setSelect(true);
        else
            imageView.setSelect(false);

        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(context)
                .load(photos.get(position).getUrl())
                .apply(requestOptions)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosen == position)
                    return;
                PhotoViewHolder vh = (PhotoViewHolder) recyclerView.findViewHolderForAdapterPosition(chosen);
                if (vh != null)
                    vh.imageView.setSelect(false);
                chosen = position;
                ((PhotoRecyclerViewItemImageView) v).setSelect(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {
        PhotoRecyclerViewItemImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
