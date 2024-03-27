package model;

public class Copy {

    private int id;
    private Book book;
    private String barcode;
    private String index;
    private Location location;
    private boolean available;

    public Copy() {}

    public Copy(int id, Book book, String barcode, String index, Location location, boolean available) {
        this.id = id;
        this.book = new Book(book);
        this.barcode = barcode;
        this.index = index;
        this.location = location;
        this.available = available;
    }

    public Copy(Copy copy) {
        this.id = copy.getId();
        this.book = new Book(copy.getBook());
        this.barcode = getBarcode();
        this.index = copy.getIndex();
        this.location = copy.getLocation();
        this.available = copy.isAvailable();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        String av;
        if(available) av = "available";
        else av = "not available";

        return "Copy{" +
                "id=" + id +
                ", book=" + book +
                ", barcode='" + barcode + '\'' +
                ", index='" + index + '\'' +
                ", location=" + location +
                ", available=" + av +
                '}';
    }
}
