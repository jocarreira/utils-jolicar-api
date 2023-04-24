package com.jocarreira.utilsjolicarapi.controller;

import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import com.jocarreira.utilsjolicarapi.model.input.SubMenuFolder;
import com.jocarreira.utilsjolicarapi.service.HtmlToJsonService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/html2Json")
@Slf4j
public class MenuJsonCreatorController {

    @Autowired
    protected HtmlToJsonService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "Success", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = String.class)
            )
    })
    public String subMenuJsonCreate(@RequestBody SubMenuFolder subMenuFolder) {
        ItemMenu itemMenu = new ItemMenu(subMenuFolder.getId(), subMenuFolder.getSubMenuName(), "");
        String retorno = service.createMenuItensJson(itemMenu);
        return retorno;
    }
}
