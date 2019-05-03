package com.n1nth.currencies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;


public class AsyncDownloader extends AsyncTaskLoader<List> {
    private String mServerUrl;

    public AsyncDownloader(Context context, String url) {
        super(context);
        mServerUrl = url;
    }


    @Override
    public List loadInBackground() {
        XmlPullParser receivedData = new Downloader().tryDownloadingXmlData(mServerUrl);
        XmlParser parser = new XmlParser();
        return parser.tryParsingXmlData(receivedData);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
