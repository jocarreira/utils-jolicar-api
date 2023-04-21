package com.jocarreira.utilsjolicarapi.service;

import java.io.*;

public class FileUtil {

    public static String readFromFile(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeToFile(String fileName, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void makeDirectory(String rootPath, String subPath) {
        File diretorioRaiz = new File(rootPath);
        File subdiretorio = new File(rootPath + "/" + subPath);

        // Verifica se o diretório raiz já existe
//        if (!diretorioRaiz.exists()) {
//            // Se não existir, cria o diretório raiz
//            diretorioRaiz.mkdirs();
//            System.out.println("Diretório raiz criado em " + rootPath);
//        }

        // Verifica se o subdiretório já existe
        if (!subdiretorio.exists()) {
            // Se não existir, cria o subdiretório dentro do diretório raiz
            try {
                subdiretorio.mkdirs();
                System.out.println("Subdiretório " + subdiretorio.getAbsolutePath() + " criado com sucesso/ ");
            } catch (SecurityException se) {
                System.out.println("Não foi possível criar o subdiretório devido a permissões insuficientes");
            }
        }
    }
}
