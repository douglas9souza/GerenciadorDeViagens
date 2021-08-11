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

    String idGerado = dadosViagens.gerarId();
    String idExcluido = idGerado;

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
                .statusCode(HttpStatus.SC_CREATED)
                .body("data", is(notNullValue()))
                .body("data.id[0]", greaterThan(0));
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

        Map dadosEditados = dadosViagens.dadosParaEditarViagens();

        given()
                .header("Authorization", tokenAdmin)
                .body(dadosEditados)
                .contentType(ContentType.JSON)
                .when()
                .put(buildUrl("/v1/viagens/" + idGerado))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    public void testDeletarViagemCadastrada() {

        given()
                .header("Authorization", tokenAdmin)
                .when()
                .delete(buildUrl("/v1/viagens/" + idGerado))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        testValidarViagemDeletada();

    }

    @Test
    public void testValidarViagemDeletada(){
        given()
                .header("Authorization", tokenUser)
                .when()
                .get(buildUrl("/v1/viagens/" + idExcluido))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);

    }
}

