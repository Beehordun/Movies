package com.example.biodun.mdb;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.biodun.mdb.Data.MovieContract;
import com.example.biodun.mdb.Data.MovieData;
import com.example.biodun.mdb.Data.MovieDbHelper;
import com.example.biodun.mdb.Fragments.OverviewFragment;
import com.example.biodun.mdb.Fragments.ReviewFragment;
import com.example.biodun.mdb.Fragments.TrailerFragment;
import com.example.biodun.mdb.Utils.ImageUtil;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class DetailsPage extends AppCompatActivity {


    private Cursor mCursor;
    private static final String TAG = DetailsPage.class.getSimpleName();
    private static MovieData movies;
    @BindView(R.id.collapse_toolbar)CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.viewpager) ViewPager viewpager;
    @BindView(R.id.tabs) TabLayout tabs;
    @BindView(R.id.imageName) ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_page);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);


        if( getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
        getSupportActionBar().setTitle("");
        displayUi();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), "Overview");
        adapter.addFragment(new TrailerFragment(), "Trailers");
        adapter.addFragment(new ReviewFragment(), "Reviews");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }


    public void displayUi() {

        Intent intent = getIntent();
        Bundle extras =intent.getExtras();
        if(extras!=null){
            movies=extras.getParcelable("movies");
            //movies = intent.getParcelableExtra("movies");
            String imageUrl = ImageUtil.getSmallImageUrl(movies.getImageUrl());

            Picasso.with(imageView.getContext()).load(imageUrl).placeholder(R.drawable.poster1).into(imageView);
        }




    }

public static MovieData getMovies(){

    return movies;
}




    public int getId(){

        return movies.getId();
    }


    public void onAddFavorite(View view){
        if(!ifDataExist()){
            insertMoviesIntoDb();

        }
        else{
            showDialog();

        }

    }

    public void insertMoviesIntoDb(){

        ContentValues contentValues = new ContentValues();


        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_ID, movies.getId());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_TITLE, movies.getTitle());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_OVERVIEW,movies.getOverview());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_DATE,movies.getReleaseDate());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_POSTER,movies.getImageUrl());
        contentValues.put(MovieContract.MoviesEntry.COLUMN_MOVIE_RATING,movies.getRatings());


        Uri uri = getContentResolver().insert(MovieContract.MoviesEntry.MOVIE_CONTENT_URI, contentValues);


        if(uri != null) {
            Toast.makeText(getBaseContext(), "Url "+uri.toString() +" Successfully added!", Toast.LENGTH_LONG).show();
        }



    }

    public void deleteMovies(){

        int id = movies.getId();


        String stringId = Integer.toString(id);
        Uri uri = MovieContract.MoviesEntry.MOVIE_CONTENT_URI;
        uri = uri.buildUpon().appendPath(stringId).build();


        int delete=getContentResolver().delete(uri, null, null);
        if (delete!=0){

            Toast.makeText(DetailsPage.this,"Deleted",Toast.LENGTH_SHORT).show();

        }




    }

    public boolean ifDataExist(){
        MovieDbHelper movieDbHelper=new MovieDbHelper(getBaseContext());
        SQLiteDatabase db=movieDbHelper.getReadableDatabase();
        Cursor cursor=null;
        String queryCheck="SELECT " + MovieContract.MoviesEntry.COLUMN_MOVIE_ID+
                          " FROM "+ MovieContract.MoviesEntry.TABLE_NAME+" WHERE "
                          + MovieContract.MoviesEntry.COLUMN_MOVIE_ID + "="
                          +Integer.toString(movies.getId());
        cursor=db.rawQuery(queryCheck,null);
        boolean exists=(cursor.getCount()>0);
        cursor.close();
        return exists;
    }
    public void showDialog(){

        AlertDialog.Builder alertDialog=new AlertDialog.Builder(DetailsPage.this);
        alertDialog.setTitle("Delete Favorite");
        alertDialog.setMessage("Do you really want to delete from favorites?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteMovies();
                new AsyncTask<Void,Void,ArrayList<MovieData>>(){
                    @Override
                    protected ArrayList<MovieData> doInBackground(Void... voids) {

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

                    @Override
                    protected void onPostExecute(ArrayList<MovieData> movieDatas) {
                        MainActivity.mAdapter.swapMovie(movieDatas);
                        MainActivity.mAdapter.notifyDataSetChanged();
                        super.onPostExecute(movieDatas);
                    }
                }.execute();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DetailsPage.this,"Favorite movie is intact!",Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }



}
