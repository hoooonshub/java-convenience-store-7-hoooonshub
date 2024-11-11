package store;

import store.promotion.Promotion;
import store.read.Reader;

import java.util.List;

public class Application {
    private static final String PROMOTION_FILE_PATH = "java-convenience-store-7-hoooonshub/src/main/resources/promotion.md";

    public static void main(String[] args) {
        List<Promotion> promotions = Reader.readPromotions(PROMOTION_FILE_PATH);
    }
}
