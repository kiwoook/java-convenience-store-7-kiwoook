package store.dto;

import java.time.LocalDate;

public record PromotionDto(String name, int buy, LocalDate startDate, LocalDate endDate) {

    @Override
    public String toString() {
        return "PromotionDto{" +
                "name='" + name + '\'' +
                ", buy=" + buy +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
