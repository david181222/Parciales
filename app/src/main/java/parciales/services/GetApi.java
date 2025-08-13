package parciales.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import parciales.model.Criptomoneda;

public class GetApi {
    static String apiUrl = "https://api.coinlore.net/api/tickers/";

    public List<Criptomoneda> getApi() {
        try {
            // Crear cliente HTTP
            HttpClient client = HttpClient.newHttpClient();

            // Crear solicitud HTTP
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

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

            return criptomonedas;



        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
            return null;
        }
    }
}