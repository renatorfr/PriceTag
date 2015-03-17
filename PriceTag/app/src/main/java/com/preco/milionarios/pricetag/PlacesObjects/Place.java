package com.preco.milionarios.pricetag.PlacesObjects;

import java.util.List;

/**
 * Created by dunha on 16/03/2015.
 */
public class Place {
    private List<String> html_attributions;
    private String next_page_token;
    private List<PlaceResult> results;
    private String status;


    public List<String> getHtml_attributions() {
        return html_attributions;
    }

    public void setHtml_attributions(List<String> html_attributions) {
        this.html_attributions = html_attributions;
    }

    public String getNext_page_token() {
        return next_page_token;
    }

    public void setNext_page_token(String next_page_token) {
        this.next_page_token = next_page_token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlaceResult> getResult() {
        return results;
    }

    public void setResult(List<PlaceResult> result) {
        this.results = result;
    }
}
