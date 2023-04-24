package com.jocarreira.utilsjolicarapi.service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.JSONArray;
import org.json.JSONObject;

public class HtmlParser {

    public static String extractComponents(String html) {
        Document doc = Jsoup.parse(html);
        JSONObject obj = new JSONObject();
        obj.put("tag", doc.tagName());
        obj.put("attrs", extractAttributes(doc));
        obj.put("children", extractChildren(doc));
        return obj.toString();
    }

    private static JSONArray extractChildren(Element elem) {
        JSONArray arr = new JSONArray();
        Elements children = elem.children();
        for (Element child : children) {
            JSONObject childObj = new JSONObject();
            childObj.put("tag", child.tagName());
            childObj.put("attrs", extractAttributes(child));
            if (child.children().size() > 0) {
                childObj.put("children", extractChildren(child));
            }
            arr.put(childObj);
        }
        return arr;
    }

    private static JSONObject extractAttributes(Element elem) {
        JSONObject attrs = new JSONObject();
        for (org.jsoup.nodes.Attribute attr : elem.attributes()) {
            attrs.put(attr.getKey(), attr.getValue());
        }
        return attrs;
    }

}