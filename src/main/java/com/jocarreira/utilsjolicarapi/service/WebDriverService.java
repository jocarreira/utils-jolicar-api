package com.jocarreira.utilsjolicarapi.service;

import com.jocarreira.utilsjolicarapi.model.ItemMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebDriverService {

    private ChromeDriverService service;
    public WebDriver driver;

    private String urlBase = "http://admroyaltrudel.veplex.com.br/";

    private String rootPathDest = "C:/MEUS_PROJETOS/Html2React2/MENU/";

    private void getService() {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("C:/DRIVER/chromedriver_win32_111/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public WebDriver executarLogin(String nomeElementoUsuario, String usuario, String nomeElementoSenha, String senha) {
        getService();
        driver = new RemoteWebDriver(service.getUrl(), new ChromeOptions());
        // Acessa a página de login
        try {
            driver.get(urlBase + "/login.php");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Preenche o campo de usuário
        WebElement campoUsuario = driver.findElement(By.name(nomeElementoUsuario));
        campoUsuario.sendKeys(usuario);

        // Preenche o campo de senha
        WebElement campoSenha = driver.findElement(By.name(nomeElementoSenha));
        campoSenha.sendKeys(senha);

        // Executa o método javascript loginSubmit()
        WebElement botaoLogin = driver.findElement(By.xpath("//input[@type='submit']"));
        botaoLogin.click();

        // Espera a página carregar
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return driver;
    }

    public void getDataFromLink(WebDriver ldriver, String urlBase, ItemMenu itemMenu) {
        if (!itemMenu.getLink().trim().isEmpty()) {
            try {
                String subDir = itemMenu.getSubDir();
                FileUtil.makeDirectory(rootPathDest, subDir);
                ldriver.get(urlBase + itemMenu.getLink());
                Map<String, List<Map<String, String>>> dados = acessaDadosFormularios(ldriver, urlBase, itemMenu.getLink());
                String nomeFormulario = "";
                if (dados.size() > 0) {
                    StringBuilder sb = new StringBuilder("");
                    for (var form : dados.entrySet()) {
                        nomeFormulario = form.getKey().toString();
                        System.out.println("Formulário: " + form.getKey());
                        for (var elemento : form.getValue()) {
                            sb.append("\tTipo: " + elemento.get("Tipo").toString() + "\n");
                            sb.append("\tNome: " + elemento.get("Nome").toString() + "\n");
                            sb.append("\tValor: " + elemento.get("Valor").toString() + "\n");
                        }
                    }
                    System.out.println(">>> Acessando link " + urlBase + itemMenu.getLink() + " com sucesso !");
                    FileUtil.writeToFile(rootPathDest + subDir + "/" + nomeFormulario + ".txt", sb.toString());
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        // Preenche o campo de usuário
        //WebElement campoUsuario = driver.findElement(By.name("f_ds_apelido"));
        //campoUsuario.sendKeys(usuario);
    }

    private  Map<String, List<Map<String, String>>> acessaDadosFormularios(WebDriver driver, String urlBase, String link) {
        Map<String, List<Map<String, String>>> dadosFormularios = new LinkedHashMap<>();
        driver.get(urlBase + link);

        //dadosFormularios = getDadosFormularios(driver, "form");
        dadosFormularios = getDadosFormulariosCenter(driver, "center");

        return dadosFormularios;
    }

    //JEFERSON
    private  Map<String, List<Map<String, String>>> getDadosFormulariosCenter(WebDriver driver, String nomeElementoRoot) {
        Map<String, List<Map<String, String>>> dadosFormularios = new LinkedHashMap<>();
        List<WebElement> center = driver.findElements(By.tagName(nomeElementoRoot));
        List<Map<String, String>> dadosElementos = new ArrayList<>();
        for (int i = 0; i < center.size(); i++) {
            WebElement elementCenter = center.get(i);
            List<WebElement> forms = elementCenter.findElements(By.tagName("form"));
            for (int j = 0; j < forms.size(); j++) {
                WebElement form = forms.get(i);
                Map<String, String> dadosElemento = new LinkedHashMap<>();
                String nomeFormulario = form.getAttribute("name");
                String actionFormulario = form.getAttribute("action" );
                dadosElemento.put("name_form", nomeFormulario);
                dadosElemento.put("action_form", actionFormulario);
                dadosElementos.add(dadosElemento);
            }
            List<WebElement> table = elementCenter.findElements(By.tagName("table"));
        }
        return dadosFormularios;
    }

    //JEFERSON
    private  Map<String, List<Map<String, String>>> getDadosFormularios(WebDriver driver, String nomeElementoRoot) {
        Map<String, List<Map<String, String>>> dadosFormularios = new LinkedHashMap<>();
        List<WebElement> formularios = driver.findElements(By.tagName(nomeElementoRoot));
        for (int i = 0; i < formularios.size(); i++) {
            WebElement formulario = formularios.get(i);
            //String nomeFormulario = "Formulário " + (i+1);
            String nomeFormulario = formulario.getAttribute("name");
            String actionFormulario = formulario.getAttribute("action" );  // JEFERSON

            List<Map<String, String>> dadosElementos = new ArrayList<>();
            List<WebElement> elementos = formulario.findElements(By.cssSelector("input[type='text'], textarea, input[type='button']"));
            for (WebElement elemento : elementos) {
                Map<String, String> dadosElemento = new LinkedHashMap<>();
                String tipoElemento = elemento.getTagName();
                String nomeElemento = elemento.getAttribute("name");
                String valorElemento = elemento.getAttribute("value");
                dadosElemento.put("Tipo", tipoElemento);
                dadosElemento.put("Nome", nomeElemento);
                dadosElemento.put("Valor", valorElemento);
                dadosElementos.add(dadosElemento);
            }
            dadosFormularios.put(nomeFormulario, dadosElementos);
        }
        return dadosFormularios;
    }

}
