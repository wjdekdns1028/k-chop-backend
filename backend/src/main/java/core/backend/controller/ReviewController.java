package core.backend.controller;

import java.security.Principal;
import java.util.*;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.dto.review.ReviewDto;
import core.backend.dto.review.ReviewFormRequest;
import core.backend.dto.review.ReviewUpdateRequest;
import core.backend.service.FoodService;
import core.backend.service.MemberService;
import core.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final MemberService memberService;

    //TODO(민우) : service단에서 에러발생->controller단에서 처리 혹은 메세지 어떻게?

//    /users/{userId}/reviews // TODO(민우) :
    @GetMapping("/{userId}")
    public Map<String, List<ReviewDto>> getReviews(@PathVariable Long userId) {
        List<ReviewDto> reviews = reviewService.getReviewsByUserId(userId);
        return Map.of("reviews", reviews);
    }

    @PostMapping
    public Map<String, String> addReview(@Valid @RequestBody ReviewFormRequest request,
                                         BindingResult bindingResult, Principal principal) {
        Member member = memberService.getUser(request.getUserId());
        if (!member.getName().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "작성 권한이 없습니다.");

        Food food = foodService.getFood(request.getFoodId());
        reviewService.createReview(food, member);

        int reviewCount = reviewService.getReviewsByUserId(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return Map.of("message", "후기 작성 완료");
    }

    @PutMapping("/{reviewId}")
    public Map<String, String> updateReview(@PathVariable Long reviewId,
                                            @Valid @RequestBody ReviewUpdateRequest request,
                                            BindingResult bindingResult,
                                            Principal principal) {
        Review review = reviewService.getReviewByReviewId(reviewId);
        Member member = review.getMember();
        if (!member.getName().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");

        reviewService.updateReview(reviewId, request.getContent());
        return Map.of("message", "후기 수정 완료");
    }

    @DeleteMapping("/{reviewId}")
    public Map<String, String> deleteReview(@PathVariable Long reviewId, Principal principal) {
        reviewService.deleteReview(reviewId);

        Review review = reviewService.getReviewByReviewId(reviewId);
        Member member = review.getMember();
        if (!member.getName().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");

        int reviewCount = reviewService.getReviewsByUserId(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return Map.of("message", "후기 삭제 완료");
    }
}
