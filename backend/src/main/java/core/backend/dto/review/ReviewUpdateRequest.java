package core.backend.dto.review;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRequest {
    @NotBlank(message = "빈 내용입니다.")
    private String content;
}
