package store.stock;

public class MenuBoard {
    private static final String INTRODUCTION = """
            \n안녕하세요. W편의점입니다.
            현재 보유하고 있는 상품입니다.\n
            """;
    private StringBuilder formatted;

    public MenuBoard() {
        formatted = new StringBuilder();
    }

    public void show(Stock stock) {
        System.out.println(INTRODUCTION + stock.takeStatus(formatted));
    }
}
