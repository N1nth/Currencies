package com.n1nth.currencies.Activities;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n1nth.currencies.Data.AsyncDownloader;
import com.n1nth.currencies.Fragments.DontNetworkFragment;
import com.n1nth.currencies.Fragments.MainFragment;
import com.n1nth.currencies.Fragments.ParserExceptionFragment;
import com.n1nth.currencies.NetworkManager;
import com.n1nth.currencies.Fragments.ProgressFragment;
import com.n1nth.currencies.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List> {
    public static final String SERVER_URL = "https://www.cbr.ru/scripts/XML_daily.asp";
    private LoaderManager mLoaderManager;
    private MainFragment mMainFragment;
    private ProgressFragment mProgressFragment;
    private DontNetworkFragment mDontNetworkFragment;
    private ParserExceptionFragment mParserExceptionFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFragment = new MainFragment();
        mProgressFragment = new ProgressFragment();
        mDontNetworkFragment = new DontNetworkFragment();
        mParserExceptionFragment = new ParserExceptionFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, mProgressFragment)
                .commit();



        mLoaderManager = getSupportLoaderManager();

    }


    @Override
    protected void onResume() {
        super.onResume();


//        if (mLoaderManager.getLoader(0) != null) {
//            mLoaderManager.initLoader(0, null, this);
//        }




        if (NetworkManager.isNetworkAvailable(this)) {
            // делаем спокойно запрос
            mLoaderManager.initLoader(1, null, this);

        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mDontNetworkFragment)
                    .commit();
        }


    }








    @Override
    public Loader<List> onCreateLoader(int i, Bundle bundle) {
        return new AsyncDownloader(this, SERVER_URL);
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List list) {


        if (list != null) {

            mMainFragment.setmValutes(list);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mMainFragment)
                    .commit();

        }
         else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, mParserExceptionFragment)
                    .commit();
        }




    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

    }




}
