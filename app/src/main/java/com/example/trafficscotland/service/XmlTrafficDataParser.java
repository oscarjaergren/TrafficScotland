/*
 Developer: Oscar Jargren
 Student ID: S1805227
*/

package com.example.trafficscotland.service;

import com.example.trafficscotland.DateHelper;
import com.example.trafficscotland.Models.TrafficData;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class XmlTrafficDataParser {

    private final ArrayList<TrafficData> trafficData = new ArrayList<>();

    private String text;

    String title = "";
    String description = "";
    String link = "";
    String georss = "";
    String author = "";
    String comments = "";
    String pubDate = "";

    Date startDate ;
    Date endDate ;

    Long roadWorkLength;

    String shortStartDate = "";
    String shortEndDate = "";



    public ArrayList<TrafficData> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase("title")) {
                            title = text;
                        }
                        if (tagName.equalsIgnoreCase("link")) {
                            link = text;
                        }
                        if (tagName.equals("description")) {
                            description = text;
                            parser.next();

                            Date[] startAndEndDates = getDates(description);

                            if (startAndEndDates != null) {


                                 startDate = startAndEndDates[0];
                                 endDate = startAndEndDates[1];

                                roadWorkLength = DateHelper.numberOfDays(startDate, endDate);
                            }
                            else{
                                startDate = new Date();
                                endDate = new Date();
                            }
                        }
                        if (tagName.equalsIgnoreCase("point")) {
                            georss = text;
                        }
                        if (tagName.equalsIgnoreCase("author")) {
                            author = text;
                        }
                        if (tagName.equalsIgnoreCase("comments")) {
                            comments = text;
                        }
                        if (tagName.equalsIgnoreCase("pubDate")) {
                            pubDate = text;
                        }

                        if (tagName.equalsIgnoreCase("item")) {
                            TrafficData data = new TrafficData(title, description, link, georss,
                                    author, comments, pubDate, startDate,
                                    endDate, shortStartDate,shortEndDate,roadWorkLength);
                            trafficData.add(data);

                            ResetData();
                        }

                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return trafficData;
    }

    public Date[] getDates(String date) throws StringIndexOutOfBoundsException {
        if (!date.contains("Start Date: ") || !date.contains("End Date: ")) {
            return null;
        }

        else {
            String startDateIndex = date.substring(date.indexOf("Start Date: "), date.indexOf(':'));
            String data1 = date.substring(startDateIndex.length() + 2, date.indexOf('<'));
            String leftOverString = date.substring(date.indexOf('>'));

            String endDateIndex = leftOverString.substring(leftOverString.indexOf("End Date: "), date.indexOf(':'));
            String data2;
            if (date.contains("<br />Delay")) {
                data2 = leftOverString.substring(endDateIndex.length() + 2, leftOverString.indexOf('<'));
            } else {
                data2 = leftOverString.substring(endDateIndex.length() + 2);
            }
            Date[] results = new Date[2];

            SimpleDateFormat sdf = new SimpleDateFormat("E, d MMMM yyyy - HH:mm");
            Date startDate = null;
            try {
                startDate = sdf.parse(data1);
                Date endDate  = sdf.parse(data2);

                results[0] = startDate;
                results[1] = endDate;

            } catch (ParseException e) {
                e.printStackTrace();
            }


            return results;
        }

    }

    private void ResetData() {
        //Reset the Data
        title = null;
        description = null;
        link = null;
        georss = null;
        author = null;
        comments = null;
        pubDate = null;
    }
}

