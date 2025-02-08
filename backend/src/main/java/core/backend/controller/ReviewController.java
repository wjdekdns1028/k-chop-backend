package core.backend.controller;

import java.util.*;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.dto.ReviewFormRequest;
import core.backend.service.FoodService;
import core.backend.service.MemberService;
import core.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final MemberService memberService;

    @PostMapping
    public Map<String, String> addReview(@Valid @RequestBody ReviewFormRequest request, BindingResult bindingResult) {
        Food food = foodService.getFood(request.getFoodId()); //TODO: service단에서 에러발생->controller단에서 처리 혹은 메세지?
        Member member = memberService.getUser(request.getUserId());
        reviewService.createReview(food, member);
        return Map.of("message", "후기 작성 완료");
    }

}
