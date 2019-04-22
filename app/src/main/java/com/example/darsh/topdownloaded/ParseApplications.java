package com.example.darsh.topdownloaded;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplications {
    private static final String TAG = "ParseApplications";

    ArrayList<FeedEntry> application ;

    public ParseApplications() {
        this.application = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApplication() {
        return application;
    }

    public boolean pares(String xmlData){
        boolean status = true;
        FeedEntry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:

                        if ("entry".equalsIgnoreCase(tagName)){
                          inEntry = true;
                          currentRecord = new FeedEntry();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
              //          Log.d(TAG, "parsing ending for tag : " + tagName);
                        if(inEntry){
                            if ("entry".equalsIgnoreCase(tagName)){
                                application.add(currentRecord);
                                inEntry = false;
                            }
                            else if("name".equalsIgnoreCase(tagName)){
                                currentRecord.setName(textValue);
                            }
                            else if("artist".equalsIgnoreCase(tagName)){
                                currentRecord.setArtist(textValue);
                            }
                            else if("releaseDate".equalsIgnoreCase(tagName)){
                                currentRecord.setReleseDate(textValue);
                            }
                            else if("summary".equalsIgnoreCase(tagName)){
                                currentRecord.setSummary(textValue);
                            }
                            else if("image".equalsIgnoreCase(tagName)){
                                currentRecord.setImgUrl(textValue);
                            }
                        }
                        break;

                        default:
                }
                eventType = xpp.next();
            }
//            for (FeedEntry app:application){
//                Log.d(TAG, "**************************");
//                Log.d(TAG, app.toString());
//            }

        }catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return status;
    }
}
