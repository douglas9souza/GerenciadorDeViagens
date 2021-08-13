package com.montanha.gerenciador.testes;

import com.montanha.gerenciador.dados.DadosViagens;
import com.montanha.gerenciador.utils.Base;
import com.montanha.gerenciador.utils.Login;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.apache.http.HttpStatus;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class ViagensTest extends Base {
    String tokenAdmin = new Login().authenticationAdmin();
    String tokenUser = new Login().authenticationUser();
    DadosViagens dadosViagens = new DadosViagens();

    String idGerado = dadosViagens.gerarId();
    String idExcluido;

    @Test
    public void testeCadastrarViagemComSucesso() {

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
                .statusCode(HttpStatus.SC_CREATED)
                .body("data", is(notNullValue()))
                .body("data.id[0]", greaterThan(0));
    }

    @Test
    public void testeObterViagensCadastradas() {
        given()
                .header("Authorization", tokenUser)
                .when()
                .get(buildUrl("/v1/viagens"))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void testeAtualizarDadosDeViagemCadastrada() {

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
    public void testeDeletarViagemCadastrada() {

        given()
                .header("Authorization", tokenAdmin)
                .when()
                .delete(buildUrl("/v1/viagens/" + idGerado))
                .then()
                .log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        idExcluido = idGerado;

        testeValidarViagemDeletada();

    }

    @Test
    public void testeValidarViagemDeletada(){
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

