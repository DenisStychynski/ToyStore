import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class Program {

  List<String> readToys(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));

        } catch(IOException exception) {
            exception.printStackTrace();
        }
        return List.of();
    }

    void writeToys(String mapState) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("./toys.txt"), StandardCharsets.UTF_8));

            writer.write(mapState);
            writer.close();
        } catch(IOException exception) {
            exception.printStackTrace();
        }

    }

     public static BufferedWriter loteryLog() throws IOException{

             BufferedWriter bufferedWriter = new BufferedWriter(
                     new OutputStreamWriter(new FileOutputStream("./winners.txt",true), StandardCharsets.UTF_8)
             );
             bufferedWriter.write(LocalDateTime.now().toString()+"\n");
        return bufferedWriter;
     }

}