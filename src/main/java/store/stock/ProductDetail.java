package store.stock;

import camp.nextstep.edu.missionutils.DateTimes;
import store.promotion.Promotion;
import store.read.Reader;

import java.util.List;

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

    public boolean isOnPromotion() {
        if (promotion.none()) {
            return false;
        }

        return promotion.isActive(DateTimes.now());
    }

    public List<Integer> takeStock(String productName, int neededQuantity) {
        if (regularQuantity + promotionalQuantity < neededQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        if (neededQuantity <= promotionalQuantity) {
            return takeOnlyPromotionStock(productName, neededQuantity);
        }

        return takeMixStock(productName, neededQuantity);
    }

    private List<Integer> takeOnlyPromotionStock(String productName, int neededQuantity) {
        if (promotion.canTakeFree(neededQuantity) && wantTakeFree(productName, promotion.bundleCount() - promotion.nonPromotionBuyCount(neededQuantity))) {
            int promotionBuyCount = (promotion.getSetCount(neededQuantity) + 1) * promotion.bundleCount();
            int nonPromotionBuyCount = 0;
            int totalBuy = promotionBuyCount + nonPromotionBuyCount;

            promotionalQuantity -= totalBuy;
            return List.of(totalBuy, promotion.freeOffer(neededQuantity));
        }

        int promotionBuyCount = promotion.getSetCount(neededQuantity) * promotion.bundleCount();
        int nonPromotionBuyCount = promotion.nonPromotionBuyCount(neededQuantity);
        int totalBuy = promotionBuyCount + nonPromotionBuyCount;

        promotionalQuantity -= totalBuy;
        return List.of(totalBuy, promotion.freeOffer(neededQuantity));
    }

    private boolean wantTakeFree(String name, int freeQuantity) {
        return "Y".equals(Reader.readAgreeReceivingFree(name, freeQuantity));
    }

    private List<Integer> takeMixStock(String productName, int neededQuantity) {
        int promotionSet = promotionalQuantity / promotion.bundleCount();
        int promotionBuyCount = promotionSet * promotion.bundleCount();
        int nonPromotionBuyCount = neededQuantity - (promotionSet * getBundleCount());

        if (agreePayingForRegularPrice(productName, nonPromotionBuyCount)) {
            int totalBuy = promotionBuyCount + nonPromotionBuyCount;

            regularQuantity -= (neededQuantity - promotionalQuantity);
            promotionalQuantity = 0;
            return List.of(totalBuy, promotion.freeOffer(promotionalQuantity));
        }

        nonPromotionBuyCount = 0;
        int totalBuy = promotionBuyCount + nonPromotionBuyCount;

        promotionalQuantity -= totalBuy;
        return List.of(totalBuy, promotion.freeOffer(promotionalQuantity));
    }

    private int getBundleCount() {
        return promotion.bundleCount();
    }

    private boolean agreePayingForRegularPrice(String productName, int regularPayQuantity) {
        return "Y".equals(Reader.readAgreeRegularPrice(productName, regularPayQuantity));
    }

    public int takeRegularStock(int neededQuantity) {
        if (regularQuantity < neededQuantity) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }

        regularQuantity -= neededQuantity;
        return neededQuantity;
    }
}
