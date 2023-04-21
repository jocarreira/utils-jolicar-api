package com.jocarreira.utilsjolicarapi.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;

public class HtmlToJsonConverter {

    public static String convert(String html) {
        Document doc = Jsoup.parse(html);
        Elements forms = doc.getElementsByTag("form");
        Gson gson = new Gson();
        return gson.toJson(getFormElements(forms));
    }

    private static Object getFormElements(Elements forms) {
        Elements inputs = forms.select("input");
        Elements selects = forms.select("select");
        Elements textareas = forms.select("textarea");

        Object[] elements = new Object[inputs.size() + selects.size() + textareas.size()];
        int i = 0;

        for (Element input : inputs) {
            elements[i++] = getInputElement(input);
        }

        for (Element select : selects) {
            elements[i++] = getSelectElement(select);
        }

        for (Element textarea : textareas) {
            elements[i++] = getTextareaElement(textarea);
        }

        return elements;
    }

    private static Object getInputElement(Element input) {
        String type = input.attr("type");
        String name = input.attr("name");
        String value = input.attr("value");
        boolean checked = input.hasAttr("checked");

        // Adicione aqui outros atributos que deseja incluir no JSON
        return new Object[]{ "input", type, name, value, checked };
    }

    private static Object getSelectElement(Element select) {
        String name = select.attr("name");
        Elements options = select.select("option");

        String[] values = new String[options.size()];
        String[] labels = new String[options.size()];
        int i = 0;

        for (Element option : options) {
            values[i] = option.attr("value");
            labels[i] = option.text();
            i++;
        }

        // Adicione aqui outros atributos que deseja incluir no JSON
        return new Object[]{ "select", name, values, labels };
    }

    private static Object getTextareaElement(Element textarea) {
        String name = textarea.attr("name");
        String value = textarea.text();

        // Adicione aqui outros atributos que deseja incluir no JSON
        return new Object[]{ "textarea", name, value };
    }
}
