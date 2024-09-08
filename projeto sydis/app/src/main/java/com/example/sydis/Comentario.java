package com.example.sydis;

public class Comentario {
    public String comentComentario;
    public String userComentario;
    public String dataComentario;
    public String idComentario;

    public Comentario() {

    }

    public String getComentComentario() {
        return comentComentario;
    }

    public void setComentComentario(String comentComentario) {
        this.comentComentario = comentComentario;
    }

    public String getUserComentario() {
        return userComentario;
    }

    public void setUserComentario(String userComentario) {
        this.userComentario = userComentario;
    }

    public String getDataComentario() {
        return dataComentario;
    }

    public void setDataComentario(String dataComentario) {
        this.dataComentario = dataComentario;
    }

    public String getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(String idComentario) {
        this.idComentario = idComentario;
    }

    @Override
    public String toString() {
        return comentComentario;
    }

}