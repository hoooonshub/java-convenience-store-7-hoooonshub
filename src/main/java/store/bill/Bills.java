package store.bill;

import java.util.ArrayList;
import java.util.List;

public class Bills {
    private List<Bill> bills;

    public Bills() {
        this.bills = new ArrayList<>();
    }

    public void add(Bill bill) {
        this.bills.add(bill);
    }
}
