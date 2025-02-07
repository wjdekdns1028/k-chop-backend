package core.backend.jpamapping;

import core.backend.domain.Food;
import core.backend.domain.Like;
import core.backend.domain.Member;
import core.backend.repository.FoodRepository;
import core.backend.repository.LikeRepository;
import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForCalendar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JpaMappingTest {
    @Autowired FoodRepository foodRepository;
    @Autowired LikeRepository likeRepository;

    @Test
    @DisplayName("test")
    void test() throws Exception {
        //given
        Food food = new Food();
        food.setId(1L);
        food.setName("Test Food");
        foodRepository.save(food);

        for (int i = 0; i < 1; i++) {
            Like like = new Like();
            like.setFood(food);
            System.out.println("System.identityHashCode(like) = " + System.identityHashCode(like));
            likeRepository.save(like);
        }

        // when
        List<Like> likes = food.getLikes();

        // then
        assertNotNull(food);
        assertEquals(1, likes.size());
        assertTrue(likes.stream().allMatch(like -> like.getFood().equals(food)));
    }
}