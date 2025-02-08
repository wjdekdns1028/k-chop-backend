//package core.backend.jpamapping;
//
//import core.backend.domain.Food;
//import core.backend.domain.Heart;
//import core.backend.domain.Member;
//import core.backend.domain.RoleType;
//import core.backend.repository.FoodRepository;
//import core.backend.repository.HeartRepository;
//import core.backend.repository.MemberRepository;
//import jakarta.transaction.Transactional;
//import org.hibernate.validator.internal.constraintvalidators.bv.time.pastorpresent.PastOrPresentValidatorForCalendar;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.stereotype.Component;
//import java.util.ArrayList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class JpaMappingTest {
//    @Autowired FoodRepository foodRepository;
//    @Autowired HeartRepository heartRepository;
//    @Autowired MemberRepository memberRepository;
//
//    @Test
//    @DisplayName("test")
//    void test() throws Exception {
//        //given // TODO : member객체가 없어서 에러
//        Food food = new Food();
//        food.setName("Test Food");
//        foodRepository.save(food);
//        Member member = new Member();
//        member.setEmail("test@test.com");
//        member.setPassword("test");
//        member.setRole(RoleType.ROLE_USER);
//        member.setNationality("Test Nationality");
//        memberRepository.save(member);
//
//        for (int i = 0; i < 3; i++) {
//            Heart heart = new Heart();
//            heart.setFood(food);
//            heart.setMember(member);
//            food.getHearts().add(heart);
//            System.out.println("System.identityHashCode(Heart) = " + System.identityHashCode(heart));
//            saveHeart(heart);
//        }
//        final Food updatedfood = foodRepository.findById(food.getId()).orElseThrow();
//        // when
//        List<Heart> hearts = updatedfood.getHearts();
//
//        // then
//        assertNotNull(food);
//        assertEquals(1, hearts.size());
//        assertTrue(hearts.stream().allMatch(heart -> heart.getFood().equals(updatedfood)));
//    }
//    public void saveHeart(Heart heart) {
//        if (heartRepository.existsByFoodAndMember(heart.getFood(), heart.getMember())) {
//            throw new IllegalArgumentException("This food and member combination already exists.");
//        }
//        heartRepository.save(heart);
//    }
//}