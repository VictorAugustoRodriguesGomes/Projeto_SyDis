package com.example.sydis;

public class Ocorrencia {
    public String nomeOcorrencia;
    public String descricaoOcorrencia;
    public String localOcorrencia;
    public String uUIDOcorrencia;
    public String userOcorrencia;
    public String urlImgOcorrencia;

    public Ocorrencia() {

    }

    public String getNomeOcorrencia() {
        return nomeOcorrencia;
    }

    public void setNomeOcorrencia(String nomeOcorrencia) {
        this.nomeOcorrencia = nomeOcorrencia;
    }

    public String getDescricaoOcorrencia() {
        return descricaoOcorrencia;
    }

    public void setDescricaoOcorrencia(String descricaoOcorrencia) {
        this.descricaoOcorrencia = descricaoOcorrencia;
    }

    public String getLocalOcorrencia() {
        return localOcorrencia;
    }

    public void setLocalOcorrencia(String localOcorrencia) {
        this.localOcorrencia = localOcorrencia;
    }

    public String getuUIDOcorrencia() {
        return uUIDOcorrencia;
    }

    public void setuUIDOcorrencia(String uUIDOcorrencia) {
        this.uUIDOcorrencia = uUIDOcorrencia;
    }

    public String getUserOcorrencia() {
        return userOcorrencia;
    }

    public void setUserOcorrencia(String userOcorrencia) {
        this.userOcorrencia = userOcorrencia;
    }

    public String getUrlImgOcorrencia() {
        return urlImgOcorrencia;

    }

    public void setUrlImgOcorrencia(String urlImgOcorrencia) {
        this.urlImgOcorrencia = urlImgOcorrencia;
    }

    @Override
    public String toString() {

        return nomeOcorrencia;
    }

}