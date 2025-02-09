package core.backend.controller;

import core.backend.domain.Food;
import core.backend.repository.FoodRepository;
import core.backend.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;
    private final FoodRepository foodRepository;

    //음식 csv 파일 업로드, db저장
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file")MultipartFile file) {
        foodService.saveFoodsFromCsv(file);
        return ResponseEntity.ok("CSV 데이터가 성공적으로 저장되었습니다.");
    }

    //음식 리스트 조회(전체 조회)
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods(){
        List<Food> foods = foodRepository.findAll();
        return ResponseEntity.ok(foods);
    }

    //음식 검색(이름, 카테고리)
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFoods(@RequestParam String query){
        List<Food> searchResults = foodRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
        return ResponseEntity.ok(searchResults);
    }

    //음식 상세 조회
    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodDetail(@PathVariable Long foodId){
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식을 찾을 수 없습니다."));
        return ResponseEntity.ok(food);
    }
}
