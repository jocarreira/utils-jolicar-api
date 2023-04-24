package com.jocarreira.utilsjolicarapi.run;

import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import com.jocarreira.utilsjolicarapi.service.HtmlToJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuJsonCreatorRun {

    @Autowired
    protected HtmlToJsonService service;
    private static HtmlToJsonService serviceStatic;

    private static String menuFinanceiro = "Financeiro";
    private static int idMenuFinanceiro = 563;

    public static void run() {
        ItemMenu itemMenu = new ItemMenu(idMenuFinanceiro, menuFinanceiro, "");
        serviceStatic.createMenuItensJson(itemMenu);
    }


}
