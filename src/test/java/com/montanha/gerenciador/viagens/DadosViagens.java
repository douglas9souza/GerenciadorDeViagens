package com.montanha.gerenciador.viagens;

import com.montanha.gerenciador.commons.Base;
import com.montanha.gerenciador.commons.GeradorDeDadosRandomicos;

import com.montanha.gerenciador.commons.Login;
import io.restassured.http.ContentType;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;

public class DadosViagens extends Base {
    GeradorDeDadosRandomicos dados = new GeradorDeDadosRandomicos();
    String tokenAdmin = new Login().authenticationAdmin();

    public Map dadosParaCadastroDeViagens() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("acompanhante", dados.gerarAcompanhante());
        parametros.put("dataPartida", dados.gerarDataDePartida());
        parametros.put("dataRetorno", dados.gerarDataDeRetorno());
        parametros.put("localDeDestino", dados.gerarLocalDeDestino());
        parametros.put("regiao", dados.gerarRegiao());

        return parametros;
    }

    public Map dadosParaEditarViagens() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("acompanhante", dados.gerarAcompanhante());
        parametros.put("dataPartida", dados.gerarDataDePartida());
        parametros.put("dataRetorno", dados.gerarDataDeRetorno());
        parametros.put("localDeDestino", dados.gerarLocalDeDestino());
        parametros.put("regiao", dados.gerarRegiao());

        return parametros;
    }

    public String gerarId(){
        Integer id =
                given()
                        .header("Authorization", tokenAdmin)
                        .body(dadosParaCadastroDeViagens())
                        .contentType(ContentType.JSON)
                        .when()
                        .post(buildUrl("/v1/viagens"))
                        .then()
                        .extract().path("data.id");

        return id.toString();
    }
}
