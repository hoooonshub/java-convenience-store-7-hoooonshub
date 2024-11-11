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
import java.util.regex.Pattern;

public class Reader {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String NON_PROMOTION = "null";
    private static final String PRODUCTS_DELIMITER = ",";
    private static final String PRODUCT_DELIMITER = "-";
    private static final Pattern START_AND_END_WITH_SQUARE_BRACKETS = Pattern.compile("^\\[.*]$");

    public static List<Promotion> readPromotions(String filePath) {
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion(NON_PROMOTION));

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
                String[] details = line.split(PRODUCTS_DELIMITER);
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
        String[] eachProduct = input.split(PRODUCTS_DELIMITER);

        for (String product : eachProduct) {
            validateEnclosedWithSquareBrackets(product);
            parseAndPutProduct(products, product);
        }
        return products;
    }

    private static void validateEnclosedWithSquareBrackets(String input) {
        if (!START_AND_END_WITH_SQUARE_BRACKETS.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
        }
    }

    private static void parseAndPutProduct(Map<String, Integer> products, String product) {
        String[] productAndCount = product.substring(1, product.length() - 1).split(PRODUCT_DELIMITER);

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

    public static String readAgreeReceivingFree(String productName, int quantity) {
        System.out.println("\n현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)".formatted(productName, quantity));

        return inputYOrN();
    }

    public static String readAgreeRegularPrice(String productName, int quantity) {
        System.out.println("\n현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)".formatted(productName, quantity));

        return inputYOrN();
    }

    public static String readApplyMembership() {
        System.out.println("\n멤버십 할인을 받으시겠습니까? (Y/N)");

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
