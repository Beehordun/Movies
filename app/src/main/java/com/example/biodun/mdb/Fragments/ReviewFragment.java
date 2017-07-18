package com.example.biodun.mdb.Fragments;



import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.biodun.mdb.Adapter.ReviewAdapter;
import com.example.biodun.mdb.Data.ReviewData;
import com.example.biodun.mdb.DetailsPage;
import com.example.biodun.mdb.R;
import com.example.biodun.mdb.Utils.ApiParsers;
import com.example.biodun.mdb.Utils.BuildUrl;
import com.example.biodun.mdb.Utils.NetworkUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private  ReviewAdapter mAdapter;
    public ArrayList<ReviewData> reviews=new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private TextView text;
  RecyclerView mRecyclerView;
    public ReviewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_review, container, false);

        text=(TextView) v.findViewById(R.id.offReview);
         mRecyclerView=(RecyclerView) v.findViewById(R.id.rvReview);
         mAdapter=new ReviewAdapter(getActivity(),reviews);
        linearLayoutManager=new LinearLayoutManager(getActivity());
         mRecyclerView.setLayoutManager(linearLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        DetailsPage act=(DetailsPage) getActivity();
        int id=act.getId();
        if(NetworkUtil.isNetworkAvailable(getActivity())){
            fetchReviewData(id);
        }
        else{
            text.setVisibility(View.VISIBLE);
        }

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (reviews!=null){

            outState.putParcelableArrayList("reviews",reviews);
        }
    }

    public  void fetchReviewData(int id) {
        OkHttpClient client = new OkHttpClient();
        String url = BuildUrl.getReviewUrl(id);
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
                } else {
                    String responses = response.body().string();
                    try { ArrayList<ReviewData> data= ApiParsers.getReviewDataFromJson(responses);
                        reviews.addAll(data);
                        if (getActivity()!=null){
                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
