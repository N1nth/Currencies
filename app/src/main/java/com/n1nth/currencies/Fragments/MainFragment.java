package com.n1nth.currencies.Fragments;


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

import com.n1nth.currencies.Data.Valute;
import com.n1nth.currencies.R;
import com.n1nth.currencies.Data.Valute;

import java.util.List;


public class MainFragment extends Fragment {

    private Spinner mCurrencyFrom;
    private Spinner mCurrencyTo;
    private EditText mCurrencyAmount;
    private TextView mCurrencyResult;
    private Button mConvert;
    private List mValutes;


    public void setmValutes(List mValutes) {
        this.mValutes = mValutes;
    }



    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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


        Valute valuteTmp = (Valute) mValutes.get(0);
        mCurrencyResult.setText(String.valueOf(valuteTmp.getName()));



    }





}
