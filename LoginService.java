import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LoginService {
    // Token yang akan digunakan untuk semua permintaan
    private static String apiToken = null;

    // Fungsi untuk mendapatkan token API key (login hanya sekali)
    public static String getApiKey(String username, String password, String loginUrl) {
        if (apiToken == null) {
            try {
                // Membuat objek URL untuk login
                URL url = new URL(loginUrl);
                HttpURLConnection connection = createPostConnection(url, username, password);

                // Membaca response dari server
                String response = getResponse(connection);

                // Mengambil nilai "JWT" dari respons JSON dan menyimpan token
                apiToken = extractTokenFromResponse(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return apiToken;
    }

    // Fungsi untuk membuat koneksi POST dan mengirim data login
    private static HttpURLConnection createPostConnection(URL url, String username, String password) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Data untuk login
        String postData = "username=" + username + "&password=" + password;

        try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
            wr.write(postData.getBytes(StandardCharsets.UTF_8));
        }
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

    // Fungsi untuk mengekstrak token JWT dari response JSON
    private static String extractTokenFromResponse(String jsonResponse) {
        int startIndex = jsonResponse.indexOf("\"JWT\":\"") + 7;
        int endIndex = jsonResponse.indexOf("\"}", startIndex);
        return jsonResponse.substring(startIndex, endIndex);
    }
}
