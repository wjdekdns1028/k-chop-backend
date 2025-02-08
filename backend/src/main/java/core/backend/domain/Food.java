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

    private String name;

    @Lob // 필드 타입 String일 시 CLOB으로 매핑
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private Integer scoville;

    private String sort;

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
