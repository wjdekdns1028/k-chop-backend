package core.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.dto.review.ReviewDto;
import core.backend.exception.CustomException;
import core.backend.exception.ErrorCode;
import core.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findAllByMemberId(userId);
    }

    public Review createReview(Food food, Member member, String content) {
        Review review = Review.builder()
                .food(food)
                .member(member)
                .content(content)
                .build();
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public Review updateReview(Long reviewId, String content) {
        return reviewRepository.findById(reviewId)
                .map(review-> {
                    review.setContent(content);
                    reviewRepository.save(review);
                    return review;
                }).orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public void deleteReview(Long reviewId) {
        if (reviewRepository.findById(reviewId).isPresent())
            reviewRepository.deleteById(reviewId);
        else
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }
}
