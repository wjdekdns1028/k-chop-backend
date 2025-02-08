package core.backend.service;

import core.backend.domain.Food;
import core.backend.repository.FoodRepository;
import core.backend.repository.ReviewRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;


    public Food getFood(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("음식이 존재하지 않습니다."));
    }
}
