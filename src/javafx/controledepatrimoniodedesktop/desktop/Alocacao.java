package javafx.controledepatrimoniodedesktop.desktop;

import java.io.Serializable;
import java.util.List;

public class Alocacao implements Serializable {

    private int id;
    private Desktop desktop;
    private Localizacao localizacao;
    private Usuario usuario;

    public Alocacao() {
    }

    public Alocacao(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Desktop getDesktop() {
        return desktop;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }



}
