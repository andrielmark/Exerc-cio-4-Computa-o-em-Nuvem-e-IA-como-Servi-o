import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ComputerVision {

    private static final String subscriptionKey = "SUA_CHAVE_DE_API"; // Substitua pela sua chave de API
    private static final String endpoint = "SEU_ENDPOINT"; // Substitua pelo seu endpoint

    public static void main(String[] args) {
        String imageUrl = "URL_DA_IMAGEM"; // URL da imagem que você deseja analisar
        analyzeImage(imageUrl);
    }

    private static void analyzeImage(String imageUrl) {
        try {
            String uri = endpoint + "/vision/v3.2/analyze?visualFeatures=Description";

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(uri);
            post.setHeader("Content-Type", "application/json");
            post.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            String jsonBody = String.format("{\"url\": \"%s\"}", imageUrl);
            post.setEntity(new StringEntity(jsonBody));

            HttpResponse response = httpClient.execute(post);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }

            System.out.println("Resposta do serviço: " + jsonResponse.toString());

            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
