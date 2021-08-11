package com.montanha.gerenciador.viagens;

import com.montanha.gerenciador.commons.Base;
import com.montanha.gerenciador.commons.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.apache.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


public class ViagensTest extends Base {
    String tokenAdmin = new Login().authenticationAdmin();
    String tokenUser = new Login().authenticationUser();
    DadosViagens dadosViagens = new DadosViagens();

    @Test
    public void testCadastrarViagemComSucesso() {

        Map dados = dadosViagens.dadosParaCadastroDeViagens();

        given()
                .header("Authorization", tokenAdmin)
                .body(dados)
                .contentType(ContentType.JSON)
                .when()
                .post(buildUrl("/v1/viagens"))
                .then()
                .log().all()
                .assertThat()
//                .body("data.acompanhante", containsString("Douglas"))
//                .body("data.regiao", containsString("Nordeste"))
//                .body("data.localDeDestino", containsString("Salvador"))
//                .body("data.dataPartida", containsString("2022-01-02"))
//                .body("data.dataRetorno", containsString("2022-01-11"))
                .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void testObterViagensCadastradas() {
        given()
                .header("Authorization", tokenUser)
                .when()
                .get(buildUrl("/v1/viagens"))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
//                .body(
//                "data.id[0]", equalTo(1),
//                "data.acompanhante[0]", equalTo("Douglas"),
//                "data.regiao[0]", equalTo("Nordeste"),
//                "data.localDeDestino[0]", equalTo("Salvador")
//                )
        ;
    }

    @Test
    public void testAtualizarDadosDeViagemCadastrada() {

        String idEditar = dadosViagens.gerarId();

        Map dadosEditados = dadosViagens.dadosParaEditarViagens();

        given()
                .header("Authorization", tokenAdmin)
                .body(dadosEditados)
                .contentType(ContentType.JSON)
                .when()
                .put(buildUrl("/v1/viagens/" + idEditar))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testDeletarViagemCadastrada() {
        String idExcluir = dadosViagens.gerarId();

        given()
                .header("Authorization", tokenAdmin)
                .when()
                .delete(buildUrl("/v1/viagens/" + idExcluir))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}
