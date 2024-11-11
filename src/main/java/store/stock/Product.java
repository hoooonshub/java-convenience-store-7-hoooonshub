package store.stock;

public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    String status() {
        return String.format("- %s %,d원 ", name, price);
    }

    boolean is(String name) {
        return this.name.equals(name);
    }
}
