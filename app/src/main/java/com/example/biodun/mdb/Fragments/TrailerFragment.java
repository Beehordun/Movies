package com.example.biodun.mdb.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.biodun.mdb.Adapter.TrailerAdapter;
import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.Data.TrailerData;
import com.example.biodun.mdb.DetailsPage;
import com.example.biodun.mdb.R;
import com.example.biodun.mdb.Utils.ApiParsers;
import com.example.biodun.mdb.Utils.BuildUrl;
import com.example.biodun.mdb.Utils.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment implements TrailerAdapter.TrailerAdapterClickHandler {

    private LinearLayoutManager linearLayoutManager;
    private TrailerAdapter mAdapter;
    private  ArrayList<TrailerData> trailers=new ArrayList<>();

    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private MovieData movie;
    private TextView text;

    public TrailerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater  inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_trailer, container, false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.rvTrailer);
        fab=(FloatingActionButton) view.findViewById(R.id.fab);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        text=(TextView) view.findViewById(R.id.offTrailer);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter=new TrailerAdapter(getActivity(),trailers,this);
        mRecyclerView.setAdapter(mAdapter);
        DetailsPage activity=(DetailsPage) getActivity();
        int id=activity.getId();
        movie=activity.getMovies();
        if(NetworkUtil.isNetworkAvailable(getActivity())){

                fetchTrailerData(id);
        }
        else{
            text.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {
                shareTrailerUrl();
            }
        });

        return  view;
    }


    @Override
    public void onClick(TrailerData data) {

        openTrailer(data.getKey());

    }

    public  void  fetchTrailerData(int id){


        OkHttpClient client=new OkHttpClient();
        String url= BuildUrl.getTrailerUrl(id);
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                else{
                    try {
                        String responseData = response.body().string();


                            ArrayList<TrailerData> trailer;
                            trailer= ApiParsers.getTrailerDataFromJson(responseData);
                            trailers.addAll(trailer);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                            }
                        });


                    } catch (Exception e) {

                    }

                }
            }
        });







    }

 public void openTrailer(String key){
     Intent youtubeApp=new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+key));
     Intent web=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+key));
     try{

         if (youtubeApp.resolveActivity(getActivity().getPackageManager()) != null) {
             startActivity(youtubeApp);
         }
     }catch(ActivityNotFoundException e){
         if (web.resolveActivity(getActivity().getPackageManager()) != null) {
             startActivity(web);
         }
     }


 }

    public void shareTrailerUrl(){



         String message="Check out this awesome movie:"+movie.getTitle()
                         +"@" + trailerUrl();
        Intent intents=new Intent(Intent.ACTION_SEND);
        intents.setType("text/plain");
        intents.putExtra(Intent.EXTRA_TEXT,message);
        Intent chooseIntent=Intent.createChooser(intents,"Share");
        startActivity(chooseIntent);



    }
    public String getFirstTrailerKey(){
        TrailerData firstTrailer=trailers.get(0);
        String firstKey=firstTrailer.getKey();
        return firstKey;
    }

    public String trailerUrl(){
      String baseUrl=  "http://www.youtube.com/watch?v=";
        String key=getFirstTrailerKey();
        String fullUrl=baseUrl+key;
        return fullUrl;

    }

}
