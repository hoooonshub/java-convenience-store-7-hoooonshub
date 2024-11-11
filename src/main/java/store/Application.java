package store;

import store.bill.Bills;
import store.cart.ShoppingCart;
import store.counter.Cashier;
import store.counter.Receipt;
import store.promotion.Promotion;
import store.read.Reader;
import store.stock.MenuBoard;
import store.stock.Stock;

import java.util.List;

public class Application {
    private static final String PROMOTION_FILE_PATH = "java-convenience-store-7-hoooonshub/src/main/resources/promotion.md";
    private static final String PRODUCT_FILE_PATH = "java-convenience-store-7-hoooonshub/src/main/resources/products.md";


    public static void main(String[] args) {
        List<Promotion> promotions = Reader.readPromotions(PROMOTION_FILE_PATH);
        Stock stock = Reader.readStock(PRODUCT_FILE_PATH, promotions);

        do {
            MenuBoard board = new MenuBoard();
            board.show(stock);

            ShoppingCart cart = new ShoppingCart();
            Bills bills = cart.shop(stock);

            Cashier cashier = new Cashier();
            Receipt receipt = cashier.checkout(bills);
            receipt.print();

        } while ("Y".equals(Reader.readKeepGoing()));
    }
}
