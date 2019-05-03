package com.n1nth.currencies;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;

public class Downloader {


    public XmlPullParser tryDownloadingXmlData(String url) {

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


}
