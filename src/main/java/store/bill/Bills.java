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

    public int calculateMembership() {
        int discount =  bills.stream()
                .mapToInt(Bill::calculateMembership)
                .sum();

        return Math.min(discount, 8000);
    }

    public StringBuilder getTopReceipt() {
        StringBuilder receiptFormatted = new StringBuilder();
        bills.forEach(bill -> receiptFormatted.append(bill.receiptTopFormat()));
        return receiptFormatted;
    }

    public StringBuilder getMiddleReceipt() {
        StringBuilder receiptFormatted = new StringBuilder();
        bills.forEach(bill -> receiptFormatted.append(bill.receiptMiddleFormat()));
        return receiptFormatted;
    }

    public int getTotalBuyCount() {
        return bills.stream()
                .mapToInt(Bill::getTotalBuy)
                .sum();
    }

    public int getTotalBuyAmount() {
        return bills.stream()
                .mapToInt(Bill::getBuyAmount)
                .sum();
    }

    public int getPromotionDiscount() {
        return bills.stream()
                .mapToInt(Bill::getPromotionDiscount)
                .sum();
    }
}
