package com.montanha.gerenciador.commons;

import java.util.Random;

public class GeradorDeDadosRandomicos {
    Random dadoRandomico = new Random();

    public String gerarAcompanhante() {
        String[] listaNomes = {"Douglas", "Franciele", "João", "Maria", "José", "Diego", "Rafael"};

        return listaNomes[dadoRandomico.nextInt(listaNomes.length)];
    }

    public String gerarLocalDeDestino() {
        String[] listaLocais = {"Porto Alegre", "Salvador", "Porto Seguro", "Gramado", "Rio de Janeiro"};

        return listaLocais[dadoRandomico.nextInt(listaLocais.length)];
    }

    public String gerarRegiao() {
        String[] listaRegioes = {"Sul", "Norte", "Leste", "Oeste", "Nordeste"};

        return listaRegioes[dadoRandomico.nextInt(listaRegioes.length)];
    }

    public String gerarDataDePartida() {
        String[] listaDataPartida = {"2021-10-01", "2021-10-02", "2021-10-03", "2021-10-04", "2021-10-05"};

        return listaDataPartida[dadoRandomico.nextInt(listaDataPartida.length)];
    }

    public String gerarDataDeRetorno() {
        String[] listaDataRetorno = {"2021-11-01", "2021-11-02", "2021-11-03", "2021-11-04", "2021-11-05"};

        return listaDataRetorno[dadoRandomico.nextInt(listaDataRetorno.length)];
    }

}
