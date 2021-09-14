package br.com.daniel.testeelo7.Models;

import com.google.gson.annotations.SerializedName;

public class RawRepositorio {
    @SerializedName("download_url")
    private String link = "";

    public String getLink() {
        return link;
    }
}
