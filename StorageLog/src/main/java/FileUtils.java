import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {



    public static String readLogFile(){
        String logs="";
    try {

        File logFile = new File("logs.txt");
        Scanner scanner = new Scanner(logFile);
        while (scanner.hasNextLine()){
            logs+=scanner.nextLine() + "<br/>";
        }
        scanner.close();

    }catch (Exception ex){
        return "No log history yet!";
    }

        return logs;
    }

    public static void addLog(String log){
        FileWriter fileWriter;
        try{

            fileWriter = new FileWriter("logs.txt", true);
            fileWriter.write(log);

            fileWriter.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }

    }


}
