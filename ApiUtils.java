import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiUtils {

    // Fungsi untuk melakukan request GET dengan token API key
    public static String executeGetRequest(String apiUrl) {
        try {
            // Ambil token dari LoginService (Token akan digunakan hanya sekali)
            String apiKey = LoginService.getApiKey("", "", "https://akademik.stmik-im.ac.id/api/login");
            if (apiKey == null) {
                return "Token tidak tersedia!";
            }

            // Membuat objek URL
            URL url = new URL(apiUrl);
            HttpURLConnection connection = createGetConnection(url, apiKey);

            // Membaca response dari server
            return getResponse(connection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Fungsi untuk membuat koneksi GET dengan token API key
    private static HttpURLConnection createGetConnection(URL url, String apiKey) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Authorization", apiKey);
        return connection;
    }

    // Fungsi untuk membaca response dari koneksi
    private static String getResponse(HttpURLConnection connection) throws Exception {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
