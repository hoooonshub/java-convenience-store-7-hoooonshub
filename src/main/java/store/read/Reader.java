package store.read;

import camp.nextstep.edu.missionutils.Console;
import store.promotion.Promotion;
import store.stock.Product;
import store.stock.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Reader {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<Promotion> readPromotions(String filePath) {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion("null"));

        parseFile(filePath, details -> {
            String name = details[0];
            int buy = Integer.parseInt(details[1]);
            int get = Integer.parseInt(details[2]);
            LocalDateTime startDate = LocalDate.parse(details[3], formatter).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(details[4], formatter).atTime(LocalTime.MAX);
            promotions.add(new Promotion(name, buy, get, startDate, endDate));
        });

        return promotions;
    }

    public static Stock readStock(String filePath, List<Promotion> promotions) {
        Stock stock = new Stock();

        parseFile(filePath, details -> {
            String name = details[0];
            int price = Integer.parseInt(details[1]);
            int quantity = Integer.parseInt(details[2]);
            String promotionName = details[3];

            Promotion promotion = promotions.stream()
                    .filter(promo -> promo.is(promotionName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요."));

            stock.register(new Product(name, price), quantity, promotion);
        });

        return stock;
    }

    private static void parseFile(String filePath, Consumer<String[]> consumer) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // 첫 줄 (title) 건너 뛰기

            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");
                consumer.accept(details);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static Map<String, Integer> readProductToBuy() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String input = Console.readLine();

        Map<String, Integer> products = new HashMap<>();
        String[] eachProduct = input.split(",");

        for (String product : eachProduct) {
            validateEnclosedWithSquareBrackets(product);
            String[] productAndCount = product.substring(1, product.length() - 1).split("-");
            parseProduct(products, productAndCount);
        }

        return products;
    }

    private static void validateEnclosedWithSquareBrackets(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void parseProduct(Map<String, Integer> products, String[] productAndCount) {
        try {
            products.put(productAndCount[0], Integer.parseInt(productAndCount[1]));
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    public static String readKeepGoing() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");

        return inputYOrN();
    }

    private static String inputYOrN() {
        String input;
        do {
            input = Console.readLine();
            if ("Y".equals(input) || "N".equals(input)) {
                return input;
            }
            System.out.println("[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.");
        } while (true);
    }
}
