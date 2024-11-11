package store.promotion;

import java.time.LocalDateTime;

public class Promotion {
    private final String name;
    private final int buyQuantity;
    private final int freeQuantity;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;

    public Promotion(String name) {
        this.name = name;
        this.buyQuantity = 0;
        this.freeQuantity = 0;
        this.startDate = null;
        this.endDate = null;
    }

    public Promotion(String name, int buy, int get, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.buyQuantity = buy;
        this.freeQuantity = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public boolean is(String name) {
        return this.name.equals(name);
    }

    public boolean none() {
        return "null".equals(this.name);
    }

    public boolean isActive(LocalDateTime now) {
        return ((now.isEqual(startDate)) || now.isAfter(startDate)) &&
                ((now.isEqual(endDate)) || now.isBefore(endDate));
    }

    public int getSetCount(int quantity) {
        return quantity / bundleCount();
    }

    public int nonPromotionBuyCount(int quantity) {
        return quantity % bundleCount();
    }

    public int bundleCount() {
        return buyQuantity + freeQuantity;
    }

    public boolean canTakeFree(int quantity) {
        return nonPromotionBuyCount(quantity) >= buyQuantity;
    }

    public int freeOffer(int promotionBuyQuantity) {
        return (promotionBuyQuantity / bundleCount()) * freeQuantity;
    }
}
