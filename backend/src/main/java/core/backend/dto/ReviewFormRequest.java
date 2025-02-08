package core.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewFormRequest {
    @JsonProperty("food_id")
    @NotBlank(message = "음식이 선택되지 않았습니다.")
    private Long foodId;

    @JsonProperty("user_id")
    @NotBlank(message = "회원이 선택되지 않았습니다.")
    private Long userId;

    @NotBlank(message = "빈 내용입니다.")
    private String content;
}
