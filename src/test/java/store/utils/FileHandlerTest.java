package store.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.dto.ProductDto;
import store.dto.PromotionDto;

class FileHandlerTest {

    private final FileHandler fileHandler = new FileHandler();

    @Test
    @Disabled
    @DisplayName("동작 확인 테스트")
    void test1() throws IOException {
    }

    @Test
    @DisplayName("프로모션 DTO 변환 테스트")
    void test2() {
        // given
        String[] line = {"탄산2+1", "2", "1", "2024-01-01", "2024-12-31" };

        PromotionDto expect = new PromotionDto("탄산2+1", 2, 1,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31));

        // when
        PromotionDto result = fileHandler.toPromotionDto(line);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("제품 DTO 변환 테스트")
    void test3() {

        String[] line = {"콜라", "1000", "10", "탄산2+1" };

        ProductDto expect = new ProductDto("콜라", 1000, 10, "탄산2+1");

        // when
        ProductDto result = fileHandler.toProductDto(line);

        // then
        assertThat(result).isEqualTo(expect);
    }

}
