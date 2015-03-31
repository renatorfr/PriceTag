package com.preco.milionarios.pricetag.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseHtml {

    public static String getString(String site, String tag) {
        String resposta = "";
        Document doc;
        try {
            // need http protocol
            doc = Jsoup.connect(site).get();
            // get page title
            Elements descr = doc.getElementsByTag(tag);
            for (Element d : descr)
                resposta = resposta + descr.text().substring(0, descr.text().indexOf("GTIN"));
            if (resposta == null) return "Sem resposta";
            return resposta;

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}