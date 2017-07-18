package com.example.biodun.mdb.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.R;
import com.example.biodun.mdb.Utils.ImageUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biodun on 4/22/2017.
 */

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.AdapterViewHolder>{
    public static final String TAG = MovieAdapter.class.getSimpleName();

    private ArrayList<MovieData> mData =new ArrayList<>();
    private Context mContext;
    private final MovieAdapterHandler movieAdapterClickHandler;

    public interface MovieAdapterHandler{

        void onClick(MovieData data);
    }


    public MovieAdapter(Context context,ArrayList<MovieData> data,MovieAdapterHandler handler) {
        mContext=context;
        mData=data;
        movieAdapterClickHandler=handler;
    }




    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.custom_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {

        MovieData data=mData.get(position);
        holder.textView.setText(data.getTitle());
        String url= ImageUtil.getSmallImageUrl(data.getImageUrl());
        Picasso.with(holder.imageView.getContext()).load(url).placeholder(R.drawable.poster1).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public ArrayList<MovieData> swapMovie(ArrayList<MovieData> movies) {

        if (mData.equals(movies)) {
            return null;
        }
        ArrayList<MovieData>  temp = mData;
        this.mData = movies;
        if (movies != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }




    public class AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            @BindView (R.id.mainImageView) ImageView imageView;
            @BindView (R.id.mainTextView) TextView textView;



        public AdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            view.setOnClickListener(this);

        }


    @Override
    public void onClick(View view) {
        int adapterPosition = getAdapterPosition();
        MovieData holdMovieData=mData.get(adapterPosition);
        movieAdapterClickHandler.onClick(holdMovieData);

    }
}




}
