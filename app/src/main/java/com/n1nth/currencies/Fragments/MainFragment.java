package com.n1nth.currencies.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.n1nth.currencies.Data.AsyncDownloader;
import com.n1nth.currencies.Data.Valute;
import com.n1nth.currencies.NetworkManager;
import com.n1nth.currencies.R;

import java.util.List;


public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {
    public static final String SERVER_URL = "https://www.cbr.ru/scripts/XML_daily.asp";

    private Spinner mCurrencyFrom;
    private Spinner mCurrencyTo;
    private EditText mCurrencyAmount;
    private TextView mCurrencyResult;
    private Button mConvert;
    private ProgressBar mProgressBar;
    private TextView mDontNetworkView;
    private TextView mErrorView;
    private List mValutes;

    private int valuteFrom;
    private int valuteTo;
    private double inputValue;


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
        mProgressBar = (ProgressBar) getView().findViewById(R.id.progress_bar);
        mDontNetworkView = (TextView) getView().findViewById(R.id.dont_network);
        mErrorView = (TextView) getView().findViewById(R.id.parser_exception);


        LoaderManager mLoaderManager = getLoaderManager();

        if (NetworkManager.isNetworkAvailable(getActivity())) {
            // делаем спокойно запрос
            mLoaderManager.initLoader(1, null, this);

        } else {
            mProgressBar.setVisibility(View.GONE);
            mDontNetworkView.setVisibility(View.VISIBLE);
        }


    }




    @Override
    public Loader<List> onCreateLoader(int i, Bundle bundle) {
        return new AsyncDownloader(this.getActivity(), SERVER_URL);
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List list) {

        if (list == null) {
            mProgressBar.setVisibility(View.GONE);
            mErrorView.setVisibility(View.VISIBLE);
        }


        else {
            Valute valuteRUB = new Valute();
            valuteRUB.setId("R00000");
            valuteRUB.setNumCode(810);
            valuteRUB.setCharCode("RUB");
            valuteRUB.setNominal(1);
            valuteRUB.setName("Российский рубль");
            valuteRUB.setValue(1.0000);
            list.add(valuteRUB);

            mValutes = list;

            mProgressBar.setVisibility(View.GONE);
            mCurrencyFrom.setVisibility(View.VISIBLE);
            mCurrencyTo.setVisibility(View.VISIBLE);
            mCurrencyAmount.setVisibility(View.VISIBLE);
            mCurrencyResult.setVisibility(View.VISIBLE);
            mConvert.setVisibility(View.VISIBLE);


            /* ------------------------------------------------------------------------------------------------------ */
            String[] arrayCharCode = new String[mValutes.size()];
            for (int i = 0; i < mValutes.size(); i++) {
                arrayCharCode[i] = ((Valute) mValutes.get(i)).getCharCode();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, arrayCharCode);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mCurrencyFrom.setAdapter(adapter);
            mCurrencyTo.setAdapter(adapter);

            mCurrencyFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    valuteFrom = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            mCurrencyTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    valuteTo = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            /* ------------------------------------------------------------------------------------------------------ */
            mConvert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputStr = String.valueOf(mCurrencyAmount.getText());

                    if (!inputStr.isEmpty()) {
                        inputValue = Double.parseDouble(inputStr);

                        if (inputValue != 0) {
                            double result = convert(valuteFrom, valuteTo, inputValue);
                            mCurrencyResult.setText(String.valueOf(result));
                        } else
                            Toast.makeText(getActivity(), "Input amount, please!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Input amount, please!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }


    @Override
    public void onLoaderReset(Loader<List> loader) {

    }





    private double convert(int valuteFrom, int valuteTo, double inputValue) {
        double valuteFromRUB = ((Valute) mValutes.get(valuteFrom)).getValue() / ((Valute) mValutes.get(valuteFrom)).getNominal();
        double valuteToRUB = ((Valute) mValutes.get(valuteTo)).getValue() / ((Valute) mValutes.get(valuteTo)).getNominal();

        double sumInputRub = valuteFromRUB * inputValue;

        return sumInputRub / valuteToRUB;

    }



}
