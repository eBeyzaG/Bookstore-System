import model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static int bookCount = 0;

    public static void writeBookToFile(){
        try {
            System.out.println("hi");
            FileOutputStream fos = new FileOutputStream("books.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(Book b :Book.bookList){
                oos.writeObject(b);
            }
            oos.close();
            fos.close();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    public static void readBooksFromFile(){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
        fis = new FileInputStream("books.dat");
        ois = new ObjectInputStream(fis);


        while(true){
            Book.bookList.add((Book) ois.readObject());
        }

        }
        catch (Exception ex){
            System.out.println(ex);
            try{
            ois.close();
            fis.close();}catch (Exception e){
                System.out.println(e);
            }
        }
    }

}
