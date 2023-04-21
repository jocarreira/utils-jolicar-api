package com.jocarreira.utilsjolicarapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MenuJsonReader {

    //private static String urlBase = "http://localhost:9097/teste/";
    private static String urlBase = "http://admroyaltrudel.veplex.com.br/";

    private static String urlArquivoJson = "C:/MEUS_PROJETOS/Html2React2/arquivo.json";

    private static String rootPathDest = "C:/MEUS_PROJETOS/Html2React2/MENU/";

    private static WebDriver driver;

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<ItemMenu> menu = objectMapper.readValue(
                    new File(urlArquivoJson),
                    new TypeReference<List<ItemMenu>>(){}
            );
            for (ItemMenu item : menu) {
                item.setSubDirs(item, "");
            }
            driver = WebDriverUtil.executarLogin(
                    "f_ds_apelido"
                    , "projetas"
                    , "f_ds_senha"
                    , "#Projetas23");
            getLinksData(menu, urlBase);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        } finally {
            driver.close();
            driver.quit();
        }
    }

    public static void createFormsDefinitionFromMenu(List<ItemMenu> menu) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (ItemMenu item : menu) {
                item.setSubDirs(item, "");
            }
            driver = WebDriverUtil.executarLogin(
                    "f_ds_apelido"
                    , "projetas"
                    , "f_ds_senha"
                    , "#Projetas23");
            getLinksData(menu, urlBase);
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        } finally {
            driver.close();
            driver.quit();
        }
    }

    public static void getLinksData(List<ItemMenu> menu, String urlBase) {
        for (ItemMenu item : menu) {
            //getDataFromLink(item.getLink(), urlBase);
            WebDriverUtil.getDataFromLink(driver, urlBase, item);
            if (item.getSubitems() != null && !item.getSubitems().isEmpty()) {
                getLinksData(item.getSubitems(), urlBase);
            }
        }
    }

    private static void setSubDirs(ItemMenu item, String parentDir) {
        if (item.getLink() != null && !item.getLink().isEmpty()) {
            // Se o item tiver um link definido, definir o subDir como o diretório do link
            item.setSubDir(parentDir + item.getLink());
        }

        if (item.getSubitems() != null) {
            // Se o item tiver submenus, chamar a função recursivamente para cada um deles
            for (ItemMenu subitem : item.getSubitems()) {
                setSubDirs(subitem, parentDir + item.getLink());
            }
        }
    }

}
