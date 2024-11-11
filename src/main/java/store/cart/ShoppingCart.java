package store.cart;

import store.bill.Bills;
import store.read.Reader;
import store.stock.Stock;

import java.util.Map;

public class ShoppingCart {
    Bills bills;

    public ShoppingCart() {
        bills = new Bills();
    }

    public Bills buy(Stock stock) {
        while (true) {
            try {
                Map<String, Integer> productsToBuy = Reader.readProductToBuy();
                productsToBuy.forEach((product, quantity) -> bills.add(stock.sell(product, quantity)));

                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return bills;
    }
}
