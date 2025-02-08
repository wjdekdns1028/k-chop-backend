package core.backend.repository;

import core.backend.domain.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    //음식 이름으로 검색(부분 검색 가능)
    List<Food> findByNameContaining(String name);

    //특정 카테고리의 음식 목록 조회
    List<Food> findByCategory(String category);
}
