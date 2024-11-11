package store.stock;

import store.bill.Bill;
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

    public String takeStatus(StringBuilder formatted) {
        products.forEach((product, detail) -> {
            if (detail.hasPromotion()) {
                formatted.append(product.status()).append(detail.promotionStatus());
            }
            formatted.append(product.status()).append(detail.regularStatus());
        });
        return formatted.toString();
    }

    public Bill take(String name, int quantity) {
        Product product = findProduct(name);

        if (products.get(product).isOnPromotion()) {
            return new Bill(product,
                    products.get(product).takeStock(name, quantity),
                    products.get(product).getBundleCount());
        }

        int takenStock = products.get(product).takeRegularStock(quantity);
        return new Bill(product, takenStock);
    }

    private Product findProduct(String name) {
        return products.keySet().stream()
                .filter(product -> product.is(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }
}
