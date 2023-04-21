package com.jocarreira.utilsjolicarapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class HtmlToJsonService {

    private static String rootPathDest = "C:/MEUS_PROJETOS/Html2React2/MENUS/";

    private static String urlArquivoJson = "C:/MEUS_PROJETOS/Html2React2/arquivo.json";

    private static String menuFinanceiro = "Financeiro";
    private static int idMenuFinanceiro = 563;

    private static String labelMenuFinanceiro = "\u003eFinanceiro";

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ItemMenu> menu = objectMapper.readValue(
                    new File(urlArquivoJson),
                    new TypeReference<List<ItemMenu>>(){}
            );
            ItemMenu root = null;
            for (ItemMenu item : menu) {
                //if (item.getLabel().equals(labelMenuFinanceiro)
                //        || item.getId() == idMenuFinanceiro) {
                if (item.getLabel().equals(labelMenuFinanceiro)) {
                    root = item;
                    break;
                } else if (item.getSubitems() != null
                        && item.getSubitems().size() > 0) {
                    ItemMenu subroot = findMenuItem(item.getSubitems(), labelMenuFinanceiro, idMenuFinanceiro);
                    if (subroot != null) {
                        root = subroot;
                        break;
                    }
                }
            }
            // cria um builder Gson com a opção de pretty printing
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            // cria uma instância de Gson com as opções configuradas
            Gson gson = builder.create();
            String jsonFinal = gson.toJson(root);
            FileUtil.makeDirectory(rootPathDest, menuFinanceiro);
            FileUtil.writeToFile(rootPathDest +
                    "/" + menuFinanceiro +
                    "/" + menuFinanceiro + ".json", jsonFinal);

        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

    public void createMenuItensJson(String urlArquivoJson, ItemMenu itemMenu) {
        String labelRootMenu = "\u003e" + itemMenu.getLabel();
        int idRootMenu = itemMenu.getId();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ItemMenu> menu = objectMapper.readValue(
                    new File(urlArquivoJson),
                    new TypeReference<List<ItemMenu>>(){}
            );
            ItemMenu root = null;
            for (ItemMenu item : menu) {
                //if (item.getLabel().equals(labelMenuFinanceiro)
                //        || item.getId() == idMenuFinanceiro) {
                if (item.getLabel().equals(labelRootMenu)) {
                    root = item;
                    break;
                } else if (item.getSubitems() != null
                        && item.getSubitems().size() > 0) {
                    ItemMenu subroot = findMenuItem(item.getSubitems(), labelRootMenu, idRootMenu);
                    if (subroot != null) {
                        root = subroot;
                        break;
                    }
                }
            }
            // cria um builder Gson com a opção de pretty printing
            GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
            // cria uma instância de Gson com as opções configuradas
            Gson gson = builder.create();
            String jsonFinal = gson.toJson(root);
            FileUtil.makeDirectory(rootPathDest, menuFinanceiro);
            FileUtil.writeToFile(rootPathDest +
                    "/" + menuFinanceiro +
                    "/" + menuFinanceiro + ".json", jsonFinal);

        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

    private static ItemMenu findMenuItem(List<ItemMenu> menu, String labelElement, int idElement) {
        for (ItemMenu item : menu) {
            if (item.getLabel().equals(labelMenuFinanceiro)) {
                System.out.println("> Encontrado ITEM Label : " + item.getLabel() + " -> Id: " + item.getId());
                return item;
            } else if (item.hasSubitems()) {
                ItemMenu subroot = findMenuItem(item.getSubitems(), labelElement, idElement);
                if (subroot != null) {
                    System.out.println("> Encontrado SUBITEM Label : " + subroot.getLabel() + " -> Id: " + subroot.getId());
                    return subroot;
                }
            }
        }
        System.out.println("> MENU NÃO ENCONTRADO Label : " + menu.get(0).getLabel() + " -> Id: " + menu.get(0).getId());
        return null;
    }

    public static String convertHtmlToJson(String html) {
        Document doc = Jsoup.parse(html);
        Element root = doc.selectFirst("ul");
        List<ItemMenu> menu = new ArrayList<>();
        int id = 1;
        for (Element li : root.select("li")) {
            menu.add(getMenuItem(li, id++));
        }
        Gson gson = new Gson();
        return gson.toJson(menu);
    }

    private static ItemMenu getMenuItem(Element li, int id) {
        String label = li.selectFirst("a").text();
        String link = li.selectFirst("a").attr("href");
        ItemMenu menuItem = new ItemMenu(id, label, link);
        Element ul = li.selectFirst("ul");
        if (ul != null) {
            menuItem.setSubitems(getSubitems(ul, id));
        }
        return menuItem;
    }

    private static List<ItemMenu> getSubitems(Element ul, int id) {
        List<ItemMenu> subitems = new ArrayList<>();
        for (Element li : ul.select("li")) {
            subitems.add(getMenuItem(li, id++));
        }
        return subitems;
    }
}
