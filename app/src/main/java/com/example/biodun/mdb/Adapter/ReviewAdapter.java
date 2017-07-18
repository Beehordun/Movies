package com.example.biodun.mdb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.biodun.mdb.Data.ReviewData;
import com.example.biodun.mdb.DetailsPage;
import com.example.biodun.mdb.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Biodun on 5/12/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder> {
    private ArrayList<ReviewData> mData =new ArrayList<>();
    private Context mContext;


    public ReviewAdapter(Context context, ArrayList<ReviewData> data) {
        mContext=context;
        mData=data;

    }


    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.custom_review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewAdapterViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ReviewAdapterViewHolder holder, int position) {
        ReviewData data=mData.get(position);
        holder.reviewerText.setText(data.getAuthor());
        holder.reviewText.setText(data.getContent());
    }



    @Override
    public int getItemCount() {
        return mData.size();
     }
    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reviewer)
        TextView reviewerText;
        @BindView (R.id.review)
        TextView reviewText;


        public ReviewAdapterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this,view);


        }

}
}