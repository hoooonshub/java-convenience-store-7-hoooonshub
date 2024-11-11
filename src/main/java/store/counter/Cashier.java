package store.counter;

import store.bill.Bills;
import store.read.Reader;

public class Cashier {

    public Receipt checkout(Bills bills) {
        int discount = applyMembership(bills);

        Receipt receipt = new Receipt();
        receipt.format(bills, discount);

        return receipt;
    }

    private int applyMembership(Bills bills) {
        if ("Y".equals(Reader.readApplyMembership())) {
            return bills.calculateMembership();
        }

        return 0;
    }
}
