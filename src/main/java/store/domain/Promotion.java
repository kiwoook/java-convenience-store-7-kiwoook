package store.domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDate startDate;
    private LocalDate endDate;


    // 오늘 기간을 판정 후 아니라면 저장하지 않는다.
    public boolean isValidPromotion(LocalDate currentDate) {
        return currentDate.isAfter(startDate) && currentDate.isBefore(endDate);
    }


}
