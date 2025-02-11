package core.backend.service;

import java.util.List;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.exception.CustomException;
import core.backend.exception.ErrorCode;
import core.backend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewByReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
    }

    public List<Review> getReviewsByUser(Long userId) {
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

    @Transactional
    public void deleteReview(Long reviewId) {
        if (reviewRepository.findById(reviewId).isPresent())
            reviewRepository.deleteById(reviewId);
        else
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }
}
