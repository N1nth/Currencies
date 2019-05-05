package com.n1nth.currencies.Data;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public class AsyncDownloader extends AsyncTaskLoader<List> {
    private String mServerUrl;

    public AsyncDownloader(Context context, String url) {
        super(context);
        mServerUrl = url;
    }


    @Override
    public List loadInBackground() {

        XmlPullParser receivedData = tryDownloadingXmlData(mServerUrl);
        return tryParsingXmlData(receivedData);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }





    private XmlPullParser tryDownloadingXmlData(String url) {

        try {
            URL xmlUrl = new URL(url);
            XmlPullParser receivedData = XmlPullParserFactory.newInstance().newPullParser();
            receivedData.setInput(xmlUrl.openStream(), null);
            return receivedData;

        } catch (XmlPullParserException e) {
            Log.e("Downloader", "XmlPullParserException", e);
        } catch (IOException e) {
            Log.e("Downloader", "IO Exception", e);
        }

        return null;
    }


    private List tryParsingXmlData(XmlPullParser receivedData) {

        if (receivedData != null){
            try {
                return new ValuteXmlParser().processReceivedData(receivedData);
            } catch (XmlPullParserException e) {
                Log.e("ValuteXmlParser", "XmlPullParserException", e);
            } catch (IOException e) {
                Log.e("ValuteXmlParser", "IO Exception parsing XML", e);
            }
        }

        return null;
    }



}
