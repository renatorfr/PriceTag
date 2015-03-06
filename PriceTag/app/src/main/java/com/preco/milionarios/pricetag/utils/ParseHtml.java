package com.preco.milionarios.pricetag.utils;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class ParseHtml {

    public static String getString(String site) {

        Document doc;
        try {

            // need http protocol
            doc = Jsoup.connect(site).get();

            // get page title
            Elements descr = doc.getElementsByTag("h1");
            for (Element d : descr) return descr.text().substring(0, descr.text().indexOf("GTIN"));
            return "num deu";


        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}