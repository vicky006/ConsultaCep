import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner entrada = new Scanner(System.in);
        List<JsonObject> consultas = new ArrayList<>(); //usando JsonObject para que o JSON saia em formato de linhas uma embaixo da outra e não em uma única linha.


        System.out.println("\nBem vindo ao sistema de consulta CEP!\n" +
                "Digite abaixo o CEP de consulta conforme o exemplo: 00001001");
        String cep = entrada.nextLine();

        while (!cep.equalsIgnoreCase("sair")) {
            String retorno = "https://viacep.com.br/ws/" + cep + "/json/";
            if (cep.equalsIgnoreCase("sair")) {
                break;
            }
        HttpClient clientViaCep = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(retorno)).build();
        HttpResponse<String> response = clientViaCep
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();
        System.out.println(json);
        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
        consultas.add(obj);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter buscados = new FileWriter("CepsBuscados.json");
        buscados.write(gson.toJson(consultas));
        buscados.close();

            System.out.println("Digite outro CEP para continuar a buscar ou 'sair' para encerrar.");
            cep = entrada.nextLine();
        }
        System.out.println("Consulta finalizada.");

    }
}