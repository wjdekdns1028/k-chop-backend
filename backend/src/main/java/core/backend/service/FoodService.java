package core.backend.service;

import core.backend.domain.Food;
import core.backend.repository.FoodRepository;
import core.backend.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;

    //csv데이터를 읽고 db에 저장
    public void saveFoodsFromCsv(MultipartFile file){
        if(file.isEmpty()){
            throw new RuntimeException("CSV파일이 비어 있습니다.");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))){
            List<Food> foodList = br.lines()
                    .skip(1)  // 첫번째 줄 건너뛰기
                    .map(line -> {
                        String[] data = line.split(",");

                        if(data.length < 6 || data[1].trim().isEmpty()){
                            System.out.println("잘못된 데이터 건너뜀: " + Arrays.toString(data));
                            return null;
                        }

                        String name = data[1].trim();
                        Optional<Food> existingFood = foodRepository.findByName(name);
                        
                        if(existingFood.isPresent()){
                            System.out.println("이미 존재하는 음식 건너뜀: " + name);
                            return null;
                        }

                        //scoville 값이 유효한 숫자인지 검사
                        Integer scoville = null;
                        try {
                            if(!data[3].trim().isEmpty()){
                                scoville = Integer.parseInt(data[3].trim());
                            }
                        }catch (NumberFormatException e){
                            System.out.println("잘못된 scoville 값: " + data[3].trim());
                        }

                        //Food 객체 생성
                        Food food = Food.builder()
                                .category(data[0].trim())
                                .name(data[1].trim())
                                .englishName(data[2].trim())
                                .scoville(scoville)
                                .imgUrl(data[4].trim())
                                .description(data[5].trim())
                                .build();

                        System.out.println("저장할 데이터: " + food); // 디버깅 로그 추가
                        return food;

                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            //데이터 저장
            if(!foodList.isEmpty()) {
                foodRepository.saveAll(foodList);
                System.out.println("총 저장된 데이터 개수: " + foodList.size()); // 몇 개 저장됐는지 확인
                }else {
                System.out.println("저장할 데이터가 없습니다.");
                }
            } catch (Exception e){
                throw new RuntimeException("CSV 파일을 읽는 중 오류 발생", e);
        }
    }

    //특정 음식 상세 조회
    public Food getFoodDetail(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new IllegalArgumentException("음식이 존재하지 않습니다."));
    }

    //카테고리별 음식 리스트 조회, 정렬 기능(최신, 인기순)
    public List<Food> getFoods(String category, String sort){
        List<Food> foods = (category != null) ? foodRepository.findByCategory(category) : foodRepository.findAll();

       //좋아요 기능 개발 전이라 기본 정렬을 최신순으로 설정
        foods.sort(Comparator.comparing(Food::getId).reversed());

        // 좋아요 기능 개발 후에 적용할 코드 (현재는 주석 처리)
//        if ("popular".equalsIgnoreCase(sort)) {
//            foods.sort(Comparator.comparingInt(food -> food.getHearts().size()));
//            Collections.reverse(foods); // 내림차순 정렬 (좋아요 개수 기준)
//        } else {
//            foods.sort(Comparator.comparing(Food::getId).reversed()); // 최신순 정렬 (ID 기준 내림차순)
//        }

        return foods;
    }

    //음식 검색(이름 또는 카테고리)
    public List<Food> searchFoods(String query){
        return foodRepository.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
    }
}
