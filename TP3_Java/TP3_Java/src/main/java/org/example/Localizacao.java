package org.example;

import java.util.Date;

public class Localizacao {
    private int idLocalizacao;
    private Date dataHoraRegistro;

    public Localizacao() {}
    public Localizacao(int idLocalizacao, Date dataHoraRegistro) {
        this.idLocalizacao = idLocalizacao;
        this.dataHoraRegistro = dataHoraRegistro;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "idLocalizacao=" + idLocalizacao +
                ", dataHoraRegistro=" + dataHoraRegistro +
                '}';
    }


}

