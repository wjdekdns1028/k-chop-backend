package core.backend.controller;

import java.util.*;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.dto.review.ReviewFormRequest;
import core.backend.dto.review.ReviewUpdateRequest;
import core.backend.service.FoodService;
import core.backend.service.MemberService;
import core.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{reviewId}")
    public Map<String, String> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewUpdateRequest request, BindingResult bindingResult) {
        reviewService.updateReview(reviewId, request.getContent());
        return Map.of("message", "후기 수정 완료");
    }

    @DeleteMapping("/{reviewId}")
    public Map<String, String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return Map.of("message", "후기 삭제 완료");
    }
}
