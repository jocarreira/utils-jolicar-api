package com.jocarreira.utilsjolicarapi.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MenuJsonReaderService {

    //private static String urlBase = "http://localhost:9097/teste/";
    private String urlBase = "http://admroyaltrudel.veplex.com.br/";

    private String urlArquivoJson = "C:/MEUS_PROJETOS/Html2React2/arquivo.json";

    private String rootPathDest = "C:/MEUS_PROJETOS/Html2React2/MENU/";

    private WebDriver driver;

//    public static void main(String[] args) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            List<ItemMenu> menu = objectMapper.readValue(
//                    new File(urlArquivoJson),
//                    new TypeReference<List<ItemMenu>>(){}
//            );
//            for (ItemMenu item : menu) {
//                item.setSubDirs(item, "");
//            }
//            driver = WebDriverService.executarLogin(
//                    "f_ds_apelido"
//                    , "projetas"
//                    , "f_ds_senha"
//                    , "#Projetas23");
//            getLinksData(menu, urlBase);
//        } catch (IOException e) {
//            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
//        } finally {
//            driver.close();
//            driver.quit();
//        }
//    }

    public void createFormsDefinitionFromMenu(List<ItemMenu> menu) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            for (ItemMenu item : menu) {
                item.setSubDirs(item, "");
            }
            driver = new WebDriverService().executarLogin(
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

    public void getLinksData(List<ItemMenu> menu, String urlBase) {
        for (ItemMenu item : menu) {
            //getDataFromLink(item.getLink(), urlBase);
            new WebDriverService().getDataFromLink(driver, urlBase, item);
            if (item.getSubitems() != null && !item.getSubitems().isEmpty()) {
                getLinksData(item.getSubitems(), urlBase);
            }
        }
    }

    private void setSubDirs(ItemMenu item, String parentDir) {
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
