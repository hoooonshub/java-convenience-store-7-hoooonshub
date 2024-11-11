package store.stock;

import store.promotion.Promotion;

import java.util.HashMap;
import java.util.Map;

public class Stock {
    private final Map<Product, ProductDetail> products;

    public Stock() {
        this.products = new HashMap<>();
    }

    public void register(Product product, int quantity, Promotion promotion) {
        products.computeIfAbsent(product, key -> new ProductDetail(promotion))
                .add(promotion, quantity);
    }
}
