package parciales.services;

<<<<<<< Updated upstream
public class GetApi {
    
}
=======
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import parciales.model.Criptomoneda;

//Clase para obtener la información de la API
public class GetApi {

    public static final Logger logger = LogManager.getLogger(GetApi.class);
    static String apiUrl = "https://api.coinlore.net/api/tickers/";

    public List<Criptomoneda> getApi() {
        try {
            logger.info("Iniciando llamada a la API de criptomonedas...");
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            logger.info("Enviando solicitud HTTP a: {}", apiUrl);
            // Enviar solicitud y obtener respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Analizar la respuesta JSON
            Gson gson = new Gson();
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);

            // Extraer la lista de criptomonedas
            Criptomoneda[] criptomonedasArray = gson.fromJson(
                    jsonResponse.getAsJsonArray("data"), Criptomoneda[].class);

            // Convertir el array a una lista
            List<Criptomoneda> criptomonedas = Arrays.asList(criptomonedasArray);

            logger.info("API consultada exitosamente. Se obtuvieron {} criptomonedas", criptomonedas.size());
            return criptomonedas;



        } catch (Exception e) {
            logger.error("Error al consultar la API de criptomonedas: {}", e.getMessage());
            throw new RuntimeException("Ocurrió un error al obtener datos de la API: " + e.getMessage());
        }
    }
}
>>>>>>> Stashed changes
