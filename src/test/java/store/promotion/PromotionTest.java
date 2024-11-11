package store.promotion;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PromotionTest {

    @Test
    void 행사_기간_맞음() {
        LocalDateTime startTime = LocalDate.of(2024, 1, 1).atStartOfDay();
        LocalDateTime endTime = LocalDate.of(2024, 12, 31).atTime(LocalTime.MAX);
        Promotion promotion = new Promotion("testPromotion", 1, 1, startTime, endTime);

        LocalDateTime timeInPromotion = LocalDateTime.of(2024, 7, 31, 12, 30);

        assertThat(promotion.isActive(timeInPromotion)).isTrue();
    }

    @Test
    void 행사_기간_아님() {
        LocalDateTime startTime = LocalDate.of(2024, 1, 1).atStartOfDay();
        LocalDateTime endTime = LocalDate.of(2024, 12, 31).atTime(LocalTime.MAX);
        Promotion promotion = new Promotion("testPromotion", 1, 1, startTime, endTime);

        LocalDateTime timeInNotPromotion = LocalDateTime.of(2025, 7, 31, 12, 30);

        assertThat(promotion.isActive(timeInNotPromotion)).isFalse();
    }
}
