package com.jocarreira.utilsjolicarapi.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import java.util.List;
public class ItemMenu {

    private int id;
    private String label;

    private String link;
    private List<ItemMenu> subitems;

    private String subDir;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<ItemMenu> getSubitems() {
        return subitems;
    }

    public void setSubitems(List<ItemMenu> subitems) {
        this.subitems = subitems;
    }

    public String getSubDir() {
        return subDir;
    }

    public void setSubDir(String subDir) {
        this.subDir = subDir;
    }

    public ItemMenu(int id, String label, String link) {
        this.id = id;
        this.label = label;
        this.link = link;
    }

    public void setSubDirs(ItemMenu item, String parentDir) {
        if (item.getLink() != null && !item.getLink().isEmpty()) {
            // Se o item tiver um link definido, definir o subDir como o diretório do link
            item.setSubDir(parentDir);
        }
        if (item.getSubitems() != null) {
            // Se o item tiver submenus, chamar a função recursivamente para cada um deles
            for (ItemMenu subitem : item.getSubitems()) {
                String sSubDir = StringUtils.stripAccents(
                        item.getLabel().replaceAll(">", "") );
                if (item.getLink() != null && !item.getLink().isEmpty()) {
                    sSubDir = sSubDir + item.getLink().replaceAll(".php", "");
                }
                sSubDir = CaseUtils.toCamelCase(sSubDir, false, ' '); // Transforma em CamelCase
                sSubDir = parentDir + "/" + sSubDir;
                setSubDirs(subitem, sSubDir);
            }
        }
    }

    public boolean hasSubitems() {
        return this.getSubitems() == null ? false : this.getSubitems().size() > 0;
    }
}
