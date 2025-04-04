import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;
import org.junit.platform.commons.util.StringUtils;

public class CepTest {

    private String getViaCep(String cep) throws Exception {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new Exception("Erro na chamada: " + statusCode);
            }
            return EntityUtils.toString(response.getEntity());
        }
    }


    @Test
    void testDeveRetornarErroQuandoCEPConterLetras() {
        String cep = "ABC12AG";
        assertThrows(Exception.class, () -> {
            getViaCep(cep);
        });
    }

    @Test
    void testDeveRetornarErroQuandoCEPForVazio() {
        String cep = "";
        assertThrows(Exception.class, () -> {
            getViaCep(cep);
        });
    }

    @Test
    void testCEPInvalidoDeveRetornarErro() {
        String cep = "12345-67890";
        assertThrows(Exception.class, () -> {
            getViaCep(cep);
        });
    }

    @Test
    void testCEPInexistenteDeveRetornarErro() throws Exception {
        String cep = "99999-999";
        String response = getViaCep(cep);
        JSONObject jsonResponse = new JSONObject(response);
        assertTrue(jsonResponse.has("erro"), "A resposta deve conter erro para um CEP inexistente.");
    }


    @Test
    void testDeveRetornarComSucessoEnderecoValido() throws Exception {
        String estado = "RS";
        String cidade = "Porto Alegre";
        String endereco = "Cristiano Fischer";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "Deve retornar 200 ok.");
            String result = EntityUtils.toString(response.getEntity());
            assertTrue(result.contains("Cristiano Fischer"), "Deve contar o endereco.");
        }
    }

    @Test
    void testComEstadoInvalidoNaoDeveRetornarNada() throws Exception {
        String estado = "ZZ";
        String cidade = "Sao Paulo";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "Deve dar 200 ok.");
            String result = EntityUtils.toString(response.getEntity()).replace("]", "").replace("[", "");
            assertTrue(StringUtils.isBlank(result), "Não deve retornar nada.");
        }
    }

    @Test
    void testComEstadovazioDeveRetornarErro() throws Exception {
        String estado = "";
        String cidade = "Sao Paulo";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve dar 400 bad request.");
        }
    }

    @Test
    void testCidadeComAcentoDeveRetornarOk() throws Exception {
        String estado = "SP";
        String cidade = "São Paulo";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "Deve retornar 200 OK");
            String result = EntityUtils.toString(response.getEntity());
            assertTrue(result.contains("São Paulo"), "Deve contar a cidade.");

        }
    }

    @Test
    void testRuaQueNaoExisteNaoDeveRetornarNada() throws Exception {
        String estado = "SP";
        String cidade = "Sao Paulo";
        String endereco = "asdasdasdas";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "Deve retornar 200 OK");
            String result = EntityUtils.toString(response.getEntity()).replace("]", "").replace("[", "");
            assertTrue(StringUtils.isBlank(result), "Não deve retornar nada.");
        }
    }

    @Test
    void testCidadeQueNaoExisteNaoDeveRetornarNada() throws Exception {
        String estado = "SP";
        String cidade = "asdasdasdas";
        String endereco = "hjkhjkhj";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(200, statusCode, "Deve retornar 200 OK");
            String result = EntityUtils.toString(response.getEntity()).replace("]", "").replace("[", "");
            assertTrue(StringUtils.isBlank(result), "Não deve retornar nada.");
        }
    }

    @Test
    void testRuaVaziaDeveRetornarErro() throws Exception {
        String estado = "SP";
        String cidade = "São Paulo";
        String endereco = "";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve retornar 400 Bad Request");

        }
    }

    @Test
    void testCidadeVaziaDeveRetornarErro() throws Exception {
        String estado = "SP";
        String cidade = "";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve retornar 400 Bad Request");
        }
    }

    @Test
    void testeEstadoComNumeroDeveRetornarErro() throws Exception {
        String estado = "11";
        String cidade = "asasda";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve retornar 400 Bad Request");
        }
    }

    @Test
    void testeCidadeComNumeroDeveRetornarErro() throws Exception {
        String estado = "SP";
        String cidade = "11";
        String endereco = "Avenida Paulista";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve retornar 400 Bad Request");
        }
    }

    @Test
    void testeEnderecoComNumeroDeveRetornarErro() throws Exception {
        String estado = "SP";
        String cidade = "São Paulo";
        String endereco = "11";
        String url = "https://viacep.com.br/ws/" + estado + "/" + cidade.replace(" ", "%20") + "/" + endereco.replace(" ", "%20") + "/json/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            assertEquals(400, statusCode, "Deve retornar 400 Bad Request");
        }
    }
}
