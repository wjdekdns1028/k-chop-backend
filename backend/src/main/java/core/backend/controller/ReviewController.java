package core.backend.controller;

import java.net.URI;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final MemberService memberService;

    @GetMapping("/users")
    public ResponseEntity<?> getReviews() {
        return ResponseEntity.ok().body(reviewService.getReviews());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getReviews(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(reviewService.getReviewsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewFormRequest request) {

        Member member = memberService.getUser(request.getUserId());
        Food food = foodService.getFoodDetail(request.getFoodId());
        reviewService.createReview(food, member, request.getContent());

        int reviewCount = reviewService.getReviewsByUserId(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return ResponseEntity.created(URI.create("/reviews/users/" + member.getId()))
                .body(Map.of("message", "후기 작성 완료"));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable Long reviewId,
                                            @Valid @RequestBody ReviewUpdateRequest request) {
        Review review = reviewService.getReviewByReviewId(reviewId);
        reviewService.updateReview(reviewId, request.getContent());

        return ResponseEntity.ok().body(Map.of("message","후기 수정 완료"));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewByReviewId(reviewId);
        Member member = review.getMember();
        reviewService.deleteReview(reviewId);

        int reviewCount = reviewService.getReviewsByUserId(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return ResponseEntity.ok().body(Map.of("message","후기 삭제 완료"));
    }
}