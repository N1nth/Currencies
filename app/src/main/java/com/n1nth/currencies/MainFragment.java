package com.n1nth.currencies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {
    public static final String SERVER_URL = "https://www.cbr.ru/scripts/XML_daily.asp";

    private Spinner mCurrencyFrom;
    private Spinner mCurrencyTo;
    private EditText mCurrencyAmount;
    private TextView mCurrencyResult;
    private Button mConvert;
    private LoaderManager mLoaderManager;



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mLoaderManager = getLoaderManager();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();

        mCurrencyFrom = (Spinner) getView().findViewById(R.id.currency_from);
        mCurrencyTo = (Spinner) getView().findViewById(R.id.currency_to);
        mCurrencyAmount = (EditText) getView().findViewById(R.id.amount_input);
        mCurrencyResult = (TextView) getView().findViewById(R.id.convert_result);
        mConvert = (Button) getView().findViewById(R.id.convert_btn);


        mLoaderManager.initLoader(1, null, this);

//        if (mLoaderManager.getLoader(0) != null) {
//            mLoaderManager.initLoader(0, null, this);
//        }

    }



    @Override
    public Loader<List> onCreateLoader(int i, Bundle bundle) {
        return new AsyncDownloader(this.getActivity(), SERVER_URL);
    }


    @Override
    public void onLoadFinished(Loader<List> loader, List list) {

        Valute valuteTmp = (Valute) list.get(0);

        mCurrencyResult.setText(String.valueOf(valuteTmp.getName()));

    }


    @Override
    public void onLoaderReset(Loader<List> loader) {

    }



}
