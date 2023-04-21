package com.jocarreira.utilsjolicarapi.run;

import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import com.jocarreira.utilsjolicarapi.service.HtmlToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


public class MenuJsonCreatorRun {

    //@Value("${url.arquivo.json}")
    private static String urlArquivoJsonStatic;

    @Value("${url.arquivo.json}")
    public String urlArquivoJson;


    @Autowired
    protected HtmlToJsonService service;
    private static HtmlToJsonService serviceStatic;

    static {
        // Faz a cópia do valor do campo de instância para o campo estático
        urlArquivoJsonStatic = new MenuJsonCreatorRun().urlArquivoJson;
        serviceStatic = new MenuJsonCreatorRun().service;
    }

//    @PostConstruct
//    public void init() {
//        serviceStatic = service;
//    }



    private static String menuFinanceiro = "Financeiro";
    private static int idMenuFinanceiro = 563;

    public static void main(String[] args) {
        ItemMenu itemMenu = new ItemMenu(idMenuFinanceiro, menuFinanceiro, "");
        serviceStatic.createMenuItensJson(urlArquivoJsonStatic, itemMenu);
    }


}
