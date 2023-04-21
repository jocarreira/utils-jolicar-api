package com.jocarreira.utilsjolicarapi.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class EdgeTest {
    public static void main(String[] args) {
        // Define a localização do driver do Edge
        System.setProperty("webdriver.edge.driver", "C:/DRIVER/edgedriver_win64/msedgedriver.exe");

        // Inicializa o driver do Edge
        WebDriver driver = new EdgeDriver();

        // Abre o navegador e acessa o Google
        driver.get("https://www.google.com");

        // Fecha o navegador
        driver.quit();
    }
}
