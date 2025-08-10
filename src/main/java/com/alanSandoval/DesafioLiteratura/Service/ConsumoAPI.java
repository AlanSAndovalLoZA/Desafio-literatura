package com.alanSandoval.DesafioLiteratura.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public JsonNode buscarLibroPorTitulo(String titulo) {
        try {
            String url = "https://gutendex.com/books?search=" + titulo.replace(" ", "%20");
            // System.out.println("URL solicitada: " + url);

            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Java HttpClient")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // System.out.println("Código HTTP: " + response.statusCode());
            // System.out.println("Respuesta cruda: " + response.body().substring(0, Math.min(500, response.body().length())));

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body());

            if (json == null || !json.has("results")) {
                System.out.println("Respuesta JSON no contiene 'results'");
                return null;
            }

            return json;
        } catch (Exception e) {
            System.out.println("Error al consumir la API: " + e.getMessage());
            return null;
        }
    }
}
