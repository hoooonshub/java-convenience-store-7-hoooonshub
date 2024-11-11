package store.bill;

import store.stock.Product;

import java.util.List;

public class Bill {
    private final Product product;
    private final int totalBuyCount;
    private final int freeOfferCount;
    private final int promotionBundleCount;

    public Bill(Product product, int totalBuy) {
        this.product = product;
        this.totalBuyCount = totalBuy;
        this.freeOfferCount = 0;
        this.promotionBundleCount = 0;
    }

    public Bill(Product product, List<Integer> productCounts, int bundleCount) {
        this.product = product;
        this.totalBuyCount = productCounts.get(0);
        this.freeOfferCount = productCounts.get(1);
        this.promotionBundleCount = bundleCount;
    }
}
