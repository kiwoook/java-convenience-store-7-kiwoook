package store.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import store.domain.vo.ProductName;
import store.dto.ProductDto;
import store.dto.PromotionDto;
import store.exception.InvalidFileFormatException;

class FileHandlerTest {

    private final FileHandler fileHandler = new FileHandler();

    @Test
    @DisplayName("프로모션 DTO 변환 테스트")
    void test2() {
        // given
        String[] line = {"탄산2+1", "2", "1", "2024-01-01", "2024-12-31"};

        PromotionDto expect = new PromotionDto("탄산2+1", 2,
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

        String[] line = {"콜라", "1000", "10", "탄산2+1"};

        ProductName productName = new ProductName("콜라");
        ProductDto expect = new ProductDto(productName, 1000, 10, "탄산2+1");

        // when
        ProductDto result = fileHandler.toProductDto(line);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @Test
    @DisplayName("readFile 동작 테스트")
    void test4() throws IOException {
        fileHandler.readProductFile();
        fileHandler.readPromotionFile();
    }

    @Test
    @DisplayName("제품 입력 검증")
    void test5() {
        // given
        String line = "제품,1000,10,탄산2+1";
        ProductDto expect = new ProductDto(new ProductName("제품"), 1000, 10, "탄산2+1");

        // when
        ProductDto result = fileHandler.toDto(line, 4, fileHandler::toProductDto);

        // then
        assertThat(result).isEqualTo(expect);
    }

    @ParameterizedTest
    @DisplayName("제품 포맷을 지키지 않으면 에러를 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {",,,,", "제품,1000,10,", ",제품,1000,10,2+1", ",1000,10,2+1", "제품,1000,10,2+1,"})
    void test6(String line) {
        assertThrows(InvalidFileFormatException.class, () -> {
            fileHandler.toDto(line, 4, fileHandler::toProductDto);
        });
    }

    @ParameterizedTest
    @DisplayName("프로덕션 포맷을 지키지 않으면 에러를 발생한다.")
    @NullAndEmptySource
    @ValueSource(strings = {",1,1,2024-11-01,2024-11-30", "반짝할인,,1,2024-11-01,2024-11-30", "반짝할인,1,,2024-11-01,2024-11-30",
            "반짝할인,1,1,,2024-11-30", "반짝할인,1,1,2024-11-01, ", "반짝할인,1,1,2024_11_01,2024_11_30", "반짝할인,1,1,2024/11/01,2024/11/30"
    })
    void test7(String line) {
        assertThrows(InvalidFileFormatException.class, () -> {
            fileHandler.toDto(line, 5, fileHandler::toPromotionDto);
        });
    }

}
