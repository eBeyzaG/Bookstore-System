package model;

import javax.enterprise.context.RequestScoped;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class Book {
    private int bookID;
    private String bookName;
    private String authorName;
    private String publisherName;

    public static List<Book> bookList = new ArrayList<>();
    private static int bookIDcounter = 0;

    public static String createStringFromList(List<Book> books){

        String str = "";

        for(Book b: books){
            str += b.getBookID() + "\t" + b.getBookName()+ "\t" + b.authorName + "\t" + b.publisherName + "\n";
        }
        return str;
    }

    public Book(){

    }

    public Book(int bookID, String bookName, String authorName, String publisherName){
        this.bookID = bookID;
        this.authorName = authorName;
        this.bookName = bookName;
        this.publisherName = publisherName;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getPublisherName() {
        return publisherName;
    }
}
