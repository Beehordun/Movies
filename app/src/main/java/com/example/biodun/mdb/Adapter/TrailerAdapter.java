package com.example.biodun.mdb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.Data.ReviewData;
import com.example.biodun.mdb.Data.TrailerData;
import com.example.biodun.mdb.R;
import com.example.biodun.mdb.Utils.BuildUrl;
import com.example.biodun.mdb.Utils.ImageUtil;
import com.example.biodun.mdb.Utils.NetworkUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biodun on 5/13/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder>{


    private Context mContext;
    private ArrayList<TrailerData> mData=new ArrayList<>();
    private final TrailerAdapterClickHandler clickHandler;


    public interface TrailerAdapterClickHandler{

        void onClick(TrailerData data);
    }


    public TrailerAdapter(Context context,ArrayList<TrailerData> data,TrailerAdapterClickHandler handler) {
        mContext=context;
        mData=data;
        clickHandler=handler;
    }

    @Override
    public TrailerAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.custom_trailer;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new TrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapterViewHolder holder, int position) {
        TrailerData data=mData.get(position);
        holder.trailerName.setText(data.getName());
        String url= BuildUrl.getTrailerImage(data.getKey());
        Picasso.with(holder.trailerImage.getContext()).load(url)
                .placeholder(R.drawable.video2)
                .into(holder.trailerImage);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       @BindView(R.id.trailerImage) ImageView trailerImage;
       @BindView(R.id.trailerName) TextView trailerName;

        public TrailerAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            TrailerData holdMovieData=mData.get(adapterPosition);
            clickHandler.onClick(holdMovieData);
        }
    }

}

