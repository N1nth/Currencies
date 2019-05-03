package com.n1nth.currencies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class MainFragment extends Fragment {
    private Spinner currencyFrom;
    private Spinner currencyTo;
    private EditText currencyAmount;
    private TextView currencyResult;
    private Button convert;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }







    public static final String SERVER_URL = "https://www.cbr.ru/scripts/XML_daily.asp";


    @Override
    public void onStart() {
        super.onStart();

        currencyFrom = (Spinner) getView().findViewById(R.id.currency_from);
        currencyTo = (Spinner) getView().findViewById(R.id.currency_to);
        currencyAmount = (EditText) getView().findViewById(R.id.amount_input);
        currencyResult = (TextView) getView().findViewById(R.id.convert_result);
        convert = (Button) getView().findViewById(R.id.convert_btn);


        AsyncDownloader asyncDownloader = new AsyncDownloader();
        asyncDownloader.execute();




    }






    private class AsyncDownloader extends AsyncTask<Object, String, Integer> {

        @Override
        protected Integer doInBackground(Object... objects) {

            XmlPullParser receivedData = new Downloader().tryDownloadingXmlData(SERVER_URL);

            int recordsFound = new XmlParser().tryParsingXmlData(receivedData);


            return recordsFound;
        }







        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }







}
