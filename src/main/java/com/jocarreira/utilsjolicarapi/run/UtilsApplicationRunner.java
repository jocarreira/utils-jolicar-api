package com.jocarreira.utilsjolicarapi.run;

import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import com.jocarreira.utilsjolicarapi.service.HtmlToJsonService;
import com.jocarreira.utilsjolicarapi.service.WebDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class UtilsApplicationRunner implements ApplicationRunner {

    @Autowired
    private HtmlToJsonService htmlToJsonService;

    @Autowired
    private WebDriverService webDriverService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ItemMenu itemMenu = new ItemMenu(563, "Financeiro", "");
        //htmlToJsonService.createMenuItensJson(itemMenu);
    }
}
