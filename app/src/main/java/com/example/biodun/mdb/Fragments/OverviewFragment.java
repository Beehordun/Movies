package com.example.biodun.mdb.Fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.DetailsPage;
import com.example.biodun.mdb.MainActivity;
import com.example.biodun.mdb.R;
import com.example.biodun.mdb.Utils.BuildUrl;
import com.example.biodun.mdb.Utils.ImageUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    private MovieData movies =new MovieData() ;

    @BindView(R.id.overText) TextView overviewText;
    @BindView(R.id.name) TextView titleText;
    @BindView(R.id.date) TextView dateText;
    @BindView(R.id.textRating) TextView ratingText;
    @BindView(R.id.poster) ImageView image;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

           }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


              if(this.getArguments()!=null){
              Bundle bundle=this.getArguments();
              movies=bundle.getParcelable("movies");
          }

        DetailsPage activity=(DetailsPage) getActivity();
        movies=activity.getMovies();
        String overview= movies.getOverview();
        String name=movies.getTitle();
        String date=movies.getReleaseDate();
        double rating=movies.getRatings();
        String url=movies.getImageUrl();
        String imageUrl= ImageUtil.getSmallImageUrl(url);
        String ratings=Double.toString(rating);

        View v= inflater.inflate(R.layout.fragment_overview, container, false);

            ButterKnife.bind(this,v);
        Picasso.with(getContext()).load(imageUrl).placeholder(R.drawable.poster1).into(image);
        overviewText.setText(overview);
        titleText.setText(name);
        dateText.setText(date);
        ratingText.setText(ratings);


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }



}
