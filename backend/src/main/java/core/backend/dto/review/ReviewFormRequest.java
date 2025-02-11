package core.backend.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFormRequest {
    @JsonProperty("food_id")
    @NotNull(message = "음식이 선택되지 않았습니다.")
    @Min(1)
    private Long foodId;

    @JsonProperty("user_id")
    @NotNull(message = "회원이 선택되지 않았습니다.")
    @Min(1)
    private Long userId;

    @NotBlank(message = "빈 내용입니다.")
    private String content;
}
