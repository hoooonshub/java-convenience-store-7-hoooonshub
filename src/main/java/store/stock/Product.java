package store.stock;

import java.util.Objects;

public class Product {
    private final String name;
    private final int price;

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    String status() {
        return String.format("- %s %,d원 ", name, price);
    }

    boolean is(String name) {
        return this.name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Product p)) {
            return false;
        }

        return name.equals(p.name) && price == p.price;
    }
}
