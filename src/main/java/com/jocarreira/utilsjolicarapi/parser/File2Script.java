package com.jocarreira.utilsjolicarapi.parser;

import com.jocarreira.utilsjolicarapi.service.FileUtil;
import com.jocarreira.utilsjolicarapi.service.HtmlToJsonService;
import com.jocarreira.utilsjolicarapi.service.HtmlToReactConverter;

public class File2Script {

    public void converteHtml2React(String fileNameSource, String fileNameDest) {
        String html = FileUtil.readFromFile(fileNameSource);
        String convert = HtmlToReactConverter.convert(html);
        FileUtil.writeToFile(fileNameDest, convert);
    }

    public void converteHtml2Json(String fileNameSource, String fileNameDest) {
        String html = FileUtil.readFromFile(fileNameSource);
//        String html = "<ul id=\"Menu-id-2\">\n" +
//                "\t<li>\n" +
//                "\t\t<a title=\"Cliente\">\n" +
//                "\t\t\t<span class=\"subind\">&gt;</span>Cliente</a>\n" +
//                "\t\t<ul id=\"Menu-id-3\">\n" +
//                "\t\t\t<li>\n" +
//                "\t\t\t\t<a target=\"conteudo\" title=\"Listagem de Acréscimo\" href=\"/sel_acrescimo.php\" onclick=\"javascript:Menu.hideAll();\">Acréscimo</a>\n" +
//                "\t\t\t</li>\n" +
//                "\t\t\t<li>\n" +
//                "\t\t\t\t<a target=\"conteudo\" title=\"Manutenção de Atividades\" href=\"/sel_atividade.php\" onclick=\"javascript:Menu.hideAll();\">Atividade</a>\n" +
//                "\t\t\t</li>\n" +
//                "\t\t\t<li>\n" +
//                "\t\t\t\t<a target=\"conteudo\" title=\"Listagem de Canal\" href=\"/sel_canal.php\" onclick=\"javascript:Menu.hideAll();\">Canal</a>\n" +
//                "\t\t\t</li>\n" +
//                "\t\t\t<li>\n" +
//                "\t\t\t\t<a target=\"conteudo\" title=\"Manutenção de Situação de Cliente\" href=\"/sel_cliente_situacao.php\" onclick=\"javascript:Menu.hideAll();\">Situação de Cliente</a>\n" +
//                "\t\t\t</li>\n" +
//                "\t\t</ul>\n" +
//                "\t</li>\n" +
//                "</lu>";
        //String convert = HtmlToJson.convertHtmlToListJson(html);
        String convert = null;
        try {
            //convert = HtmlToJson.htmlToList(html);
            convert = HtmlToJsonService.convertHtmlToJson(html);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FileUtil.writeToFile(fileNameDest, convert);
    }
}
