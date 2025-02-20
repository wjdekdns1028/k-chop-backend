package core.backend.controller;

import java.net.URI;
import java.util.*;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.domain.ReviewLike;
import core.backend.dto.review.ReviewFormRequest;
import core.backend.dto.review.ReviewUpdateRequest;
import core.backend.dto.review.ReviewVoteRequest;
import core.backend.exception.CustomException;
import core.backend.exception.ErrorCode;
import core.backend.service.FoodService;
import core.backend.service.MemberService;
import core.backend.service.ReviewLikeService;
import core.backend.service.ReviewService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final FoodService foodService;
    private final MemberService memberService;
    private final ReviewLikeService reviewLikeService;

    @GetMapping("/users")
    public ResponseEntity<?> getReviews() {
        return ResponseEntity.ok().body(reviewService.getReviews());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getReviews(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(reviewService.getReviewsByUser(userId));
    }

    @PostMapping
    public ResponseEntity<?> addReview(@Valid @RequestBody ReviewFormRequest request) {
        request.validate(); //요청데이터 확인
        
        Member member = memberService.getUser(request.getUserId());
        Food food = foodService.findFoodByID(request.getFoodId());

        //food가 정상적으로 조회되지 않으면 예외
        if(food == null){
            log.error("음식 조회 실패: foodID={}", request.getFoodId());
            throw new CustomException(ErrorCode.FOOD_NOT_FOUND);
        }

        reviewService.createReview(food, member, request.getContent(), request.getSpicyLevel());

        int reviewCount = reviewService.getReviewsByUser(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return ResponseEntity.created(URI.create("/reviews/users/" + member.getId()))
                .body(Map.of("message", "후기 작성 완료"));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReview(@PathVariable("reviewId") Long reviewId,
                                            @Valid @RequestBody ReviewUpdateRequest request) {
        Review review = reviewService.getReviewByReview(reviewId);
        reviewService.updateReview(reviewId, request.getContent(), request.getSpicyLevel());

        return ResponseEntity.ok().body(Map.of("message","후기 수정 완료"));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId) {
        //리뷰 삭제 실행
        Review review = reviewService.getReviewByReview(reviewId);
        Member member = review.getMember();
        reviewService.deleteReview(reviewId);
        //전체 리뷰 리스트 갱신
        List<Review> allReviews = reviewService.getReviews();
        //사용자의 리뷰 갱신
        int reviewCount = reviewService.getReviewsByUser(member.getId()).size();
        memberService.updateBadge(member, reviewCount);

        return ResponseEntity.ok().body(Map.of(
                "message","후기 삭제 완료",
                "allReviews", allReviews));
    }


    @PostMapping("/upvote")
    public ResponseEntity<?> addUpvote(@Valid @RequestBody ReviewVoteRequest request) {
        Review review = reviewService.getReviewByReview(request.getReviewId());
        Member member = memberService.getUser(request.getMemberId());
        reviewLikeService.addUpvote(review, member);
        return ResponseEntity.ok().body(Map.of("message", "리뷰 좋아요 추가 완료"));
    }

    @PostMapping("/downvote")
    public ResponseEntity<?> addDownvote(@Valid @RequestBody ReviewVoteRequest request) {
        Review review = reviewService.getReviewByReview(request.getReviewId());
        Member member = memberService.getUser(request.getMemberId());
        reviewLikeService.addDownvote(review, member);
        return ResponseEntity.ok().body(Map.of("message", "리뷰 싫어요 추가 완료"));
    }

    @DeleteMapping("/upvote")
    public ResponseEntity<?> removeUpvote(@Valid @RequestBody ReviewVoteRequest request) {
        Review review = reviewService.getReviewByReview(request.getReviewId());
        Member member = memberService.getUser(request.getMemberId());
        reviewLikeService.deleteUpvote(review, member);
        return ResponseEntity.ok().body(Map.of("message", "리뷰 좋아요 삭제 완료"));
    }

    @DeleteMapping("/downvote")
    public ResponseEntity<?> removeDownvote(@Valid @RequestBody ReviewVoteRequest request) {
        Review review = reviewService.getReviewByReview(request.getReviewId());
        Member member = memberService.getUser(request.getMemberId());
        reviewLikeService.deleteDownvote(review, member);
        return ResponseEntity.ok().body(Map.of("message", "리뷰 싫어요 삭제 완료"));
    }
}