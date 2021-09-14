package br.com.daniel.testeelo7.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Repositorio {

    @SerializedName("name")
    private String Nome = "";

    @SerializedName("private")
    private boolean privado;

    @SerializedName("description")
    private String Descricao = "";

    @SerializedName("language")
    private String Linguagem = "";

    @SerializedName("stargazers_count")
    private String Estrelas = "";

    @SerializedName("forks")
    private String Forks = "";

    @SerializedName("full_name")
    private String NomeCompleto = "";

    public String getNome() {
        return Nome;
    }

    public boolean isPrivado() {
        return privado;
    }

    public String getDescricao() {
        return Descricao;
    }

    public String getLinguagem() {
        return Linguagem;
    }

    public String getForks() {
        return Forks;
    }

    public String getNomeCompleto(){
        return NomeCompleto;
    }

    public String getEstrelas(){
        return Estrelas;
    }

}
