package com.rebty.taskmanager.Classes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class WorkXML {

    static SimpleXML xml;

    public static String writeXML(ArrayList<Task> list) {
        xml = new SimpleXML("Tasks");
        for (int i = 0; i < list.size(); i++) {
            SimpleXML bufferSimpleXML = xml.createChild("Task");
            bufferSimpleXML.setAttr("header", list.get(i).header);
            bufferSimpleXML.setAttr("text", list.get(i).text);
            bufferSimpleXML.setAttr("iconId", Integer.toString(list.get(i).iconColor));
            bufferSimpleXML.setAttr("longTime", list.get(i).longTime + "");
        }
        String xmlOut = SimpleXML.saveXml(xml);
        return xmlOut;
    }

    static public ArrayList<Task> readXML(String string) {
        ArrayList<Task> list = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(string));
            Task a = null;
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG && !parser.getName().equals("Tasks")) {
                    a = new Task(parser.getAttributeValue(3), parser.getAttributeValue(1), Integer.parseInt(parser.getAttributeValue(2)), Long.parseLong(parser.getAttributeValue(0)));
                }
                if (parser.getEventType() == XmlPullParser.END_TAG && !parser.getName().equals("Tasks")) {
                    list.add(a);
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
