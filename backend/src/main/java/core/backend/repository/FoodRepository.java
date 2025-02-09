package core.backend.repository;

import core.backend.domain.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByName(String name);
    List<Food> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(String name, String category);
}
