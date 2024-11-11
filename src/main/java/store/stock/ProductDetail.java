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

    public boolean hasPromotion() {
        return !promotion.none();
    }

    public String promotionStatus() {
        if (promotionalQuantity == 0) {
            return String.format("재고 없음 %s\n", promotion.getName());
        }
        return String.format("%d개 %s\n", promotionalQuantity, promotion.getName());
    }

    public String regularStatus() {
        if (regularQuantity == 0) {
            return String.format("재고 없음\n");
        }
        return String.format("%d개\n", regularQuantity);
    }

}