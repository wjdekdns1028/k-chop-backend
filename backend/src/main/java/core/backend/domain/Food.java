package core.backend.domain;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 음식 이름

    @Lob // 필드 타입 String일 시 CLOB으로 매핑
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(nullable = false)
    private Integer scoville;

    @Column(nullable = false)
    private String category; // 음식 카테고리 (sort->category 수정)

    @Column(nullable = false)
    private String imgUrl;
/*
//    @OneToMany(mappedBy ="food", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();;
    // Null Pointer Exception 방지를 위해 초기화
//    @OneToMany(mappedBy = "food") // cascade = CascadeType.REMOVE
//    @OneToMany(mappedBy ="food", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Heart> hearts = new ArrayList<>();;
*/
}
