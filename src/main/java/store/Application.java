package store;

import store.promotion.Promotion;
import store.read.Reader;
import store.stock.Stock;

import java.util.List;

public class Application {
    private static final String PROMOTION_FILE_PATH = "java-convenience-store-7-hoooonshub/src/main/resources/promotion.md";
    private static final String PRODUCT_FILE_PATH = "java-convenience-store-7-hoooonshub/src/main/resources/products.md";


    public static void main(String[] args) {
        List<Promotion> promotions = Reader.readPromotions(PROMOTION_FILE_PATH);
        Stock stock = Reader.readStock(PRODUCT_FILE_PATH, promotions);

        do {

        } while ("Y".equals(Reader.readKeepGoing()));
    }
}
