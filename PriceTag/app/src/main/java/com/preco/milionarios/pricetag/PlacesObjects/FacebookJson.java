package com.preco.milionarios.pricetag.PlacesObjects;

import java.util.List;

public class FacebookJson {

    private List<FacebookPlaces> data;
    private FacebookPaging paging;


    public void addAll(List<FacebookPlaces> data) {
        this.data.addAll(data);
    }

    public List<FacebookPlaces> getData() {
        return data;
    }

    public void setData(List<FacebookPlaces> data) {
        this.data = data;
    }

    public FacebookPaging getPaging() {
        return paging;
    }

    public void setPaging(FacebookPaging paging) {
        this.paging = paging;
    }


}
