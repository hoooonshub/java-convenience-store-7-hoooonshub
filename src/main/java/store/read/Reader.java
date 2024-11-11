package store.read;

import store.promotion.Promotion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
}
