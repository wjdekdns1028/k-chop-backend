package core.backend.service;

import core.backend.domain.Food;
import core.backend.domain.Member;
import core.backend.domain.Review;
import core.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public Review createReview(Food food, Member member) {
        Review review = Review.builder()
                .food(food)
                .member(member)
                .build();
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }
}
