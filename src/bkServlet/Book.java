package bkServlet;

public class Book {

	private Long id;

    private String title;
    private String author;
    private String language;
    private String published;
    private int price;
    private String sales;


    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getPublished() {
        return published;
    }
    
    public void setPublished(String published) {
        this.published = published;
    }
    
    public String getSales() {
        return sales;
    }
    
    public void setSales(String sales) {
        this.sales = sales;
    }

}
