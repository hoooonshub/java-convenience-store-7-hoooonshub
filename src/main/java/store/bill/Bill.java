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

    public int calculateMembership() {
        if (freeOfferCount == 0) {
            return (int)(totalBuyCount * product.getPrice() * 0.3);
        }

        return (int)((totalBuyCount - (freeOfferCount * promotionBundleCount)) * product.getPrice() * 0.3);
    }

    public int getTotalBuy() {
        return totalBuyCount;
    }

    public int getPromotionDiscount() {
        return freeOfferCount * product.getPrice();
    }

    public int getBuyAmount() {
        return totalBuyCount * product.getPrice();
    }

    public String receiptTopFormat() {
        return String.format("%-10s %10d %,10d\n", product.getName(), totalBuyCount, totalBuyCount * product.getPrice());
    }

    public String receiptMiddleFormat() {
        if (freeOfferCount == 0) {
            return "";
        }

        return String.format("%-10s %10d\n", product.getName(), freeOfferCount);
    }
}
