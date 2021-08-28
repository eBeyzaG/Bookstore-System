import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Utils {

    public static String filePath = "logs.txt";

    public static String readLogFile(){
        String logs="";
    try {
        File logFile = new File(filePath);
        Scanner scanner = new Scanner(logFile);
        while (scanner.hasNextLine()){
            logs+=scanner.nextLine();
        }
        scanner.close();

    }catch (Exception ex){
        ex.printStackTrace();
    }

        return logs;
    }

    public static void addLog(String log){
        FileWriter fileWriter;
        try{
            fileWriter = new FileWriter(filePath);
            fileWriter.append(log);

            fileWriter.close();
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }

    }


}
