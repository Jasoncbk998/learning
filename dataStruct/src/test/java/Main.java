/**
 * @Classname Main
 * @Description TODO
 * @Date 2020/7/29 3:12 下午
 * @Created by jason
 */
class Book{
    String name;
    double price;

    public Book(String name,double price){
        this.name = name;
        this.price = price;
    }
    public void getInfo(){
        System.out.println("图书名称："+ name + "，价格：" + price);
    }

    public void setPrice(double price){
        this.price = price;
    }
}

public class Main{
    public static void main(String[] args){
        Book book = new Book("Java开发指南",66.6);
        book.getInfo();  //图书名称：Java开发指南，价格：66.6
        fun(book);
        book.getInfo();  //图书名称：Java开发指南，价格：99.9
    }

    public static void fun(Book temp){
        temp.setPrice(99.9);
    }
}