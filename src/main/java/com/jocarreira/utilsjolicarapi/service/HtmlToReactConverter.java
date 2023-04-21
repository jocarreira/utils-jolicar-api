package com.jocarreira.utilsjolicarapi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlToReactConverter {

    public static String convert(String html) {
        String regex = "<lu>(.*?)</lu>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String list = matcher.group(1);
            String reactList = convertList(list);
            matcher.appendReplacement(buffer, reactList);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String convertList(String list) {
        String regex = "<li>(.*?)</li>";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(list);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String item = matcher.group(1);
            String reactItem = "<li>" + item + "</li>";
            matcher.appendReplacement(buffer, reactItem);
        }
        matcher.appendTail(buffer);
        return "<ul>" + buffer.toString() + "</ul>";
    }
}
