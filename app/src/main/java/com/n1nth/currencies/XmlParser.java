package com.n1nth.currencies;


import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XmlParser {

    private Calendar calendar;
    private List<Valute> valutes;



    public Calendar getDate() {
        return calendar;
    }




    public int tryParsingXmlData(XmlPullParser receivedData) {

        if (receivedData != null){
            try {
                return processReceivedData(receivedData);
            } catch (XmlPullParserException e) {
                Log.e("XmlParser", "Ошибка при загрузке XML-документа", e);
            } catch (IOException e) {
                Log.e("XmlParser", "IO Exception parsing XML", e);
            }
        }

        return 0;
    }




    private int processReceivedData(XmlPullParser xpp) throws XmlPullParserException, IOException {
        String tmp = "";
        Valute valute = null;


        while (xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
            String name = "";


            // начало документа
            if (xpp.getEventType() == XmlPullParser.START_DOCUMENT) {
                valutes = new ArrayList<Valute>();
            }


            // парсим xml файл в список объектов Valute
            else if (xpp.getEventType() == XmlPullParser.START_TAG) {
                name = xpp.getName();


                if (name.equals("ValCurs")) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    Date date = null;
                    try {
                        date = sdf.parse(xpp.getAttributeValue(null, "Date"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    calendar = Calendar.getInstance();
                    calendar.setTime(date);

                }


                else if (name.equals("Valute")) {
                    valute = new Valute();
                    String id = xpp.getAttributeValue(null, "ID");
                    valute.setId(id);
                }


                else if (valute != null) {
                    switch (name) {
                        case "NumCode": {
                            String value = xpp.nextText();
                            valute.setNumCode(Integer.parseInt(value));
                            break;
                        }
                        case "CharCode": {
                            String value = xpp.nextText();
                            valute.setCharCode(value);
                            break;
                        }
                        case "Nominal": {
                            String value = xpp.nextText();
                            valute.setNominal(Integer.parseInt(value));
                            break;
                        }
                        case "Name": {
                            String value = xpp.nextText();
                            valute.setName(value);
                            break;
                        }
                        case "Value": {
                            String value = xpp.nextText();
                            value = value.replace(",", ".");
                            valute.setValue(Double.parseDouble(value));
                            break;
                        }
                    }

                }

            }


            else if (xpp.getEventType() == XmlPullParser.END_TAG) {
                name = xpp.getName();
                if (name.equals("Valute") && valute != null) {
                    valutes.add(valute);
                }
            }


            xpp.next();

        }


//        for (Valute v : valutes) {
//            System.out.print(v.getCharCode() + " ");
//            System.out.print(v.getId() + " ");
//            System.out.print(v.getName() + " ");
//            System.out.print(v.getNominal() + " ");
//            System.out.print(v.getNumCode() + " ");
//            System.out.println(v.getValue());
//
//        }
//        System.out.println(calendar.toString());





        return 0;
    }




}



