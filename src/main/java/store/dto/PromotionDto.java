package store.dto;

import java.time.LocalDate;

public record PromotionDto(String name, long buy, long get, LocalDate startDate, LocalDate endDate) {

}
