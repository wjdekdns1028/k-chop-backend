package core.backend.service;

import java.util.List;
import java.util.stream.Collectors;
import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.dto.review.ReviewDto;
import core.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review getReviewByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
    }

    public List<ReviewDto> getReviewsByUserId(Long userId) {
        List<Review> reviewList = reviewRepository.findAllById(List.of(userId));
        if (reviewList.isEmpty()) {
            throw new IllegalArgumentException("해당 사용자의 후기가 존재하지 않습니다.");
        }
        return reviewList.stream()
                .map(review -> new ReviewDto(review.getId(), review.getFood().getId(), review.getContent()))
                .collect(Collectors.toList());
    }

    public Review createReview(Food food, Member member) {
        Review review = Review.builder()
                .food(food)
                .member(member)
                .build();
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public Review updateReview(Long reviewId, String content) {
        return reviewRepository.findById(reviewId)
                .map(review-> {
                    review.setContent(content); // Dirty Checking by JPA
                    return review;
                }).orElseThrow(() -> new IllegalArgumentException("리뷰가 존재하지 않습니다."));
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(
                        review -> reviewRepository.delete(review),
                        () -> { throw new IllegalArgumentException("리뷰가 존재하지 않습니다."); }
                );
    }
}
