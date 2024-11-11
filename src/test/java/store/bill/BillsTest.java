package store.bill;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.stock.Product;

class BillsTest {

    @Test
    void 멤버십할인_계산() {
        Bills bills = new Bills();
        bills.add(new Bill(new Product("콜라", 1000), 10));
        bills.add(new Bill(new Product("사이다", 1500), 5));

        int discount = bills.calculateMembership();
        int cokeDiscount = (int)(1000 * 10 * 0.3);
        int sodaDiscount = (int)(1500 * 5 * 0.3);

        Assertions.assertThat(discount).isEqualTo(cokeDiscount + sodaDiscount);
    }
}