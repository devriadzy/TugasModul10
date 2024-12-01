import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input username dan password
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        // URL login
        String loginUrl = "https://akademik.stmik-im.ac.id/api/login";

        // Melakukan login dan mendapatkan token API key
        String apiKey = LoginService.getApiKey(username, password, loginUrl);

        // Cek jika login berhasil
        if (apiKey != null) {
            System.out.println("Login berhasil!");

            // Menu pilihan untuk menampilkan KRS atau OrtuDns
            while (true) {
                System.out.println("\nPilih salah satu opsi:");
                System.out.println("1. Tampilkan KRS");
                System.out.println("2. Tampilkan OrtuDns");
                System.out.println("0. Keluar");
                System.out.print("Pilihan: ");
                int pilihan = scanner.nextInt();

                switch (pilihan) {
                    case 0:
                        System.out.println("Keluar...");
                        scanner.close();
                        return; // Keluar dari program

                    // Get KRS
                    case 1:
                        String krsUrl = "https://akademik.stmik-im.ac.id/api/list/VkrsAList";
                        String krsResponse = ApiUtils.executeGetRequest(krsUrl);
                        System.out.println("Data KRS: " + krsResponse);
                        break;

                    // Get KHS/OrtuDns
                    case 2:
                        String ortuDnsUrl = "https://akademik.stmik-im.ac.id/api/list/OrtuDns";
                        String ortuDnsResponse = ApiUtils.executeGetRequest(ortuDnsUrl);
                        System.out.println("Data OrtuDns: " + ortuDnsResponse);
                        break;

                    default:
                        System.out.println("Pilihan tidak valid, coba lagi.");
                }
            }
        } else {
            System.out.println("Login gagal. Periksa username dan password Anda.");
        }

        scanner.close();
    }
}
