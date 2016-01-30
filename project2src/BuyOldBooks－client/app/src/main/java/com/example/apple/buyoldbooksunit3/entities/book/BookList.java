/**
 * project2_Yidil_shuruil
 *
 * BookList:
 * This class serves as an external class and is used to manage Book such as get the information
 * of a book and insert a new book into the booklist.
 */
package com.example.apple.buyoldbooksunit3.entities.book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class BookList implements Serializable {
    private ArrayList<Book> bookList;            //An ArrayList that stores books

    /* Constructor with no-arg */
    public BookList(){
        bookList = new ArrayList<>();
    }


    /*
    * getBookList:
    * Get method of bookList
    */
    public ArrayList<Book> getBookList() {
        return bookList;
    }


    /*
     * getBookName:
     * Get the name of a particular book in book list
     */
    public String getBookName(int index){
        return bookList.get(index).getBookName();
    }

    /*
     * getBookSeller:
     * Get the seller's name of a particular book in book list
     */
    public String getBookSeller(int index){
        return bookList.get(index).getBookSeller();
    }

    /*
     * getBookPrice:
     * Get the price of a particular book in book list
     */
    public float getBookPrice(int index){return bookList.get(index).getBookPrice();}

    /*
     * getDepreciation:
     * Get the depreciation of a particular book in book list
     */
    public float getDepreciation(int index){return bookList.get(index).getBookDepreciation();}

    /*
     * getDescription:
     * Get the description of a particular book in book list
     */
    public String getDescription(int index){return bookList.get(index).getBookDescription();}

    /*
     * getpayment:
     * Get the payment of a particular book in book list
     */
    public String getpayment(int index){return bookList.get(index).getBookPayment();}

    /*
     * getexpress:
     * Get the express of a particular book in book list
     */
    public String getexpress(int index){return bookList.get(index).getBookExpress();}

    /*
     * addBook:
     * Add a new book into the book list
     */
    public void addBook(String bookName, float bookPrice, float bookDepreciation,
                        String bookDescription,String bookPayment,String bookExpress,
                        String bookSeller){
        Book book = new Book(bookName,bookPrice,bookDepreciation,bookDescription,bookPayment
                ,bookExpress,bookSeller);
        bookList.add(book);

    }

    /*
     * bookInfo;
     * This method is used to search the information of a particular book according to the book's
     * name and seller's name and return the information in a HashMap.
     */
    public HashMap bookInfo(String bookname, String sellername){
        HashMap info=new HashMap();
        for(int i=0;i<bookList.size();i++){
            System.out.println("hi");

            if(getBookName(i).equals(bookname) && getBookSeller(i).equals(sellername)){
                info.put("bookName",bookname);
                info.put("bookPrice",getBookPrice(i));
                info.put("bookDepreciation",getDepreciation(i));
                info.put("sellerName",sellername);
                info.put("bookDescription",getDescription(i));
                info.put("payment",getpayment(i));
                info.put("express",getexpress(i));
                break;

            }
        }
        return info;
    }


}
