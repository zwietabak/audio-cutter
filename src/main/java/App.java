import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        AudioCutter audioCutter = new AudioCutter();
        Scanner inputScanner = new Scanner(System.in);
        String clientId = "";
        String clientSecret = "";
        String clientCode = "";
        String playlistId = "";
        ArrayList<Song> songList = new ArrayList<Song>();
        int timeOffsetMs =0;


        //

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

            System.out.print("\nInsert Offset Time in MS: ");
            timeOffsetMs = Integer.parseInt(inputScanner.next());
            System.out.println("");

           songList = audioCutter.getPlaylistData(audioCutter.getAccessToken(), playlistId);
           int durationUsedMs = 0;
           for(Song song : songList){

               copyAudio("C:\\Users\\Loisel\\Documents\\_Studium\\2. Semester\\Se2\\AudioCutterNew\\audio-cutter\\src\\main\\resources\\Data.wav",
                       "C:\\Users\\Loisel\\Documents\\_Studium\\2. Semester\\Se2\\AudioCutterNew\\audio-cutter\\src\\main\\resources\\"+song.songName+".wav", durationUsedMs/1000,song.songDurationMs/1000, song.artistName );
               durationUsedMs += song.songDurationMs+timeOffsetMs;
           }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public static void copyAudio(String sourceFileName, String destinationFileName, int startSecond, int secondsToCopy, String artist) {
        AudioInputStream inputStream = null;
        AudioInputStream shortenedStream = null;
        try {
            File file = new File(sourceFileName);
            //Files.setAttribute(file.toPath(),"Artist",artist);
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);

            AudioFormat format = fileFormat.getFormat();
            inputStream = AudioSystem.getAudioInputStream(file);
            int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();
            inputStream.skip(startSecond * bytesPerSecond);
            long framesOfAudioToCopy = secondsToCopy * (int) format.getFrameRate();
            shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
            File destinationFile = new File(destinationFileName);

            AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (inputStream != null) try {
                inputStream.close();
            } catch (Exception e) {
                System.out.println(e);
            }
            if (shortenedStream != null) try {
                shortenedStream.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}
