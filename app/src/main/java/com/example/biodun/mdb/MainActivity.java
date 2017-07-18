package com.example.biodun.mdb;


import android.content.Intent;

import android.content.res.Configuration;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.biodun.mdb.Adapter.MovieAdapter;
import com.example.biodun.mdb.Data.MovieContract;
import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.Data.ReviewData;
import com.example.biodun.mdb.Data.TrailerData;

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


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterHandler, LoaderManager.LoaderCallbacks<ArrayList<MovieData>> {


    private RecyclerView mRecyclerView;
    private GridLayoutManager gridLayoutManager;
    public static MovieAdapter mAdapter;
    private int span;
    public static ArrayList<MovieData> movies=new ArrayList<>();
    public static ArrayList<ReviewData> reviews=new ArrayList<>();
    public static  ArrayList<TrailerData> trailers=new ArrayList<>();
    public static String preference="popular";
    private static final String MOVIE_KEY="movies";
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int TASK_LOADER_ID = 0;
    private Cursor mCursor;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView=(RecyclerView) findViewById(R.id.rv);
        detectScreenOrientation();
        gridLayoutManager=new GridLayoutManager(this,span);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter=new MovieAdapter(this,movies,this);
        mRecyclerView.setAdapter(mAdapter);
        text=(TextView) findViewById(R.id.internet);
        if(!NetworkUtil.isNetworkAvailable(this)){

            text.setVisibility(View.VISIBLE);

        }
        if(savedInstanceState==null|| !savedInstanceState.containsKey(MOVIE_KEY) )
        {
            if(NetworkUtil.isNetworkAvailable(this)){
                fetchMovieData(preference);


            }



        }


       

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        if(id==R.id.popularity){
            preference="popular";
            destroyActiveLoader();
            if(NetworkUtil.isNetworkAvailable(this)){
                movies.clear();
                fetchMovieData(preference);


            }


        }
        else if (id==R.id.topRated){
            preference="top_rated";
            destroyActiveLoader();
            if(NetworkUtil.isNetworkAvailable(this)){
                movies.clear();
                fetchMovieData(preference);



            }

        }
        else if(id==R.id.favorite){
            text.setVisibility(View.INVISIBLE);
            movies.clear();
            getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this).forceLoad();
            preference="favorite";
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();
    


    }

    @Override
    public void onClick(MovieData data) {

        Intent intent =new Intent(MainActivity.this,DetailsPage.class);

        intent.putExtra("movies",data);

        startActivity(intent);


    }


    public void detectScreenOrientation(){
        int orientation=this.getResources().getConfiguration().orientation ;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){
            span=2;


        }
        else if (orientation==Configuration.ORIENTATION_LANDSCAPE)
            span=3;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(movies!=null){

            outState.putParcelableArrayList(MOVIE_KEY,movies);
            outState.putString("pref",preference);
        }

        super.onSaveInstanceState(outState);
    }


    public  void fetchMovieData(String preference) {


        OkHttpClient client = new OkHttpClient();
        String url = BuildUrl.getMovieUrl(preference);
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
                    try {
                        ArrayList<MovieData> movie;
                        movie= ApiParsers.getMovieDataFromJson(responses);
                        movies.addAll(movie);
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mAdapter.notifyDataSetChanged();

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });




    }


    @Override
    public Loader<ArrayList<MovieData>> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<ArrayList<MovieData>>(this) {


            @Override
            public ArrayList<MovieData> loadInBackground() {


                try {
                    ArrayList<MovieData> movies=new ArrayList<>();
                    mCursor= getContentResolver().query(MovieContract.MoviesEntry.MOVIE_CONTENT_URI,
                            null,
                            null,
                            null,
                            MovieContract.MoviesEntry._ID);
                   while(mCursor.moveToNext()){
                       String title=mCursor.getString(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE));
                       String overview=mCursor.getString(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW));
                       String date=mCursor.getString(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_DATE));
                       String poster=mCursor.getString(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_POSTER));
                       int id=mCursor.getInt(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_ID));
                       double rating=mCursor.getDouble(mCursor.getColumnIndex(MovieContract.MoviesEntry.COLUMN_MOVIE_RATING));
                       MovieData data=new MovieData(title,poster,overview,rating,date,id);
                       movies.add(data);

                   }

                    return movies;

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }

            }


        };

    }



    @Override
    public void onLoadFinished(Loader<ArrayList<MovieData>> loader, ArrayList<MovieData> data) {
        mAdapter.swapMovie(data);
         movies.clear();
        movies.addAll(data);
        mAdapter.notifyDataSetChanged();

    }

    public void destroyActiveLoader(){

        getSupportLoaderManager().destroyLoader(TASK_LOADER_ID);
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<MovieData>> loader) {
        movies.clear();
        mAdapter.swapMovie(movies);
    }
public  void restartLoader(){
    getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
}


}
