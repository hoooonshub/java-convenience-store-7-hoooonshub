package store.stock;

import store.promotion.Promotion;

public class ProductDetail {
    private Promotion promotion;
    private int regularQuantity;
    private int promotionalQuantity;

    public ProductDetail(Promotion promotion) {
        this.promotion = promotion;
        this.regularQuantity = 0;
        this.promotionalQuantity = 0;
    }

    ProductDetail add(Promotion promotion, int quantity) {
        if (promotion.none()) {
            this.regularQuantity += quantity;
            return this;
        }

        this.promotion = promotion;
        this.promotionalQuantity += quantity;
        return this;
    }

}
