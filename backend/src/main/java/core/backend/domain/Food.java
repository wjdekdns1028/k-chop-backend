package core.backend.domain;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 음식 이름 (한글명)

    @Column(nullable = false, unique = true)
    private String englishName; // 영어 이름

    @Lob // 필드 타입 String일 시 CLOB으로 매핑
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Min(0)
    private Integer scoville;

    @Column(nullable = false)
    private String category; // 음식 카테고리 (sort->category 수정)

    @Column(nullable = false)
    private String imgUrl;
}
