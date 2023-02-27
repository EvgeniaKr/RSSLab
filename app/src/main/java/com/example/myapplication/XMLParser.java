package com.example.myapplication;

import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;
import java.io.StringReader;
import java.util.List;

public class XMLParser {
    private ArrayList<InfoRSS> infoRSS;

    public XMLParser(){
        infoRSS = new ArrayList<>();
    }

    public ArrayList<InfoRSS> getUsers(){
        return  infoRSS;
    }
    public static ArrayList<String> links = new ArrayList<String>();
    public boolean parse(String xmlData){
        boolean status = true;
        InfoRSS currentUser = null;
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("item".equalsIgnoreCase(tagName)){
                            inEntry = true;
                            currentUser = new InfoRSS();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if("item".equalsIgnoreCase(tagName)){
                                infoRSS.add(currentUser);
                                inEntry = false;
                            } else if("title".equalsIgnoreCase(tagName)){
                                currentUser.setTitle(textValue);
                            } else if("description".equalsIgnoreCase(tagName)){
                                //currentUser.setDescription(textValue);
                            } else if("link".equalsIgnoreCase(tagName)){
                                links.add(textValue);
                                currentUser.setLink(textValue);
                            }
                        }
                        break;
                    default:
                }

                eventType = xpp.next();
            }
        }
        catch (Exception e){
            status = false;
            e.printStackTrace();
        }
        return  status;
    }
}
