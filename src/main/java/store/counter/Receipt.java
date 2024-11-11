package store.counter;

import store.bill.Bills;

public class Receipt {
    StringBuilder formatted;

    public Receipt() {
        formatted = new StringBuilder();
    }

    public void format(Bills bills, int memberShipDiscount) {
        makeTopFormat(bills);
        makeMiddleFormat(bills);
        makeBottomFormat(bills, memberShipDiscount);
    }

    public void print() {
        System.out.println(formatted.toString());
    }

    private void makeTopFormat(Bills bills) {
        formatted.append("\n==============W 편의점================\n");
        formatted.append(bills.getTopReceipt());
    }

    private void makeMiddleFormat(Bills bills) {
        formatted.append("=============증	 정===============\n");
        formatted.append(bills.getMiddleReceipt());
    }

    private void makeBottomFormat(Bills bills, int memberShipDiscount) {
        formatted.append("====================================\n");

        int totalBuyCount = bills.getTotalBuyCount();
        int totalBuyAmount = bills.getTotalBuyAmount();
        int promotionDiscount = bills.getPromotionDiscount();
        int finalAmount = totalBuyAmount - promotionDiscount - memberShipDiscount;

        formatted.append(String.format("총구매액   %10d %,10d\n", totalBuyCount, totalBuyAmount));
        formatted.append(String.format("행사할인       \t    -%,-10d\n", promotionDiscount));
        formatted.append(String.format("멤버십할인      \t   -%,-10d\n", memberShipDiscount));
        formatted.append(String.format("내실돈       \t  %,10d\n", finalAmount));
    }
}
