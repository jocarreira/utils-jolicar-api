package com.jocarreira.utilsjolicarapi.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class MeuTesteSelenium {

    private static ChromeDriverService service;
    private WebDriver driver;

    public static void main(String[] args) throws MalformedURLException {

        //WebDriver driver = new RemoteWebDriver(
        //        new URL("http://127.0.0.1:9515"),
        //        new ChromeOptions());
        //driver.get("http://www.google.com");
        //driver.quit();

        //WebDriverManager.chromedriver().setup();
        //WebDriver driver = new ChromeDriver();
        //driver.get("https://www.google.com");
        //driver.quit();

        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/DRIVER/chromedriver_win32_111/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WebDriver driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        driver.get("http://www.google.com");
        driver.quit();
    }

}
