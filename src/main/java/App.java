import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        AudioCutter audioCutter = new AudioCutter();
        Scanner inputScanner = new Scanner(System.in);
        String clientId = "";
        String clientSecret = "";
        String clientCode = "";
        String playlistId = "";

        try {
            System.out.print("Insert Client ID: ");
            clientId = inputScanner.next();
            System.out.println("");
            System.out.print("Insert Client Secret: ");
            clientSecret = inputScanner.next();
            System.out.println("");

            audioCutter.authorizeApp(clientId);

            System.out.print("\nInsert Client Code: ");
            clientCode = inputScanner.next();
            System.out.println("");

            audioCutter.getApiAccessToken(clientId, clientSecret, clientCode);

            System.out.print("\nInsert Playlist ID: ");
            playlistId = inputScanner.next();
            System.out.println("");

            audioCutter.getPlaylistData(audioCutter.getAccessToken(), playlistId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
