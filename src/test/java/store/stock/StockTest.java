package store.stock;

import org.junit.jupiter.api.Test;
import store.promotion.Promotion;
import store.read.Reader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class StockTest {
    private static final String PROMOTION_FILE_PATH = "src/main/resources/promotions.md";
    private static final String PRODUCT_FILE_PATH = "src/main/resources/products.md";

    List<Promotion> promotions = Reader.readPromotions(PROMOTION_FILE_PATH);
    Stock stock = Reader.readStock(PRODUCT_FILE_PATH, promotions);

    @Test
    void 상품_찾기_실패_없음() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> stock.sell("환타", 1));
    }

    @Test
    void 상품_찾기_성공() {
        assertThatNoException()
                .isThrownBy(() -> stock.sell("콜라", 1));
    }
}
