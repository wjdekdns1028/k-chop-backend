package core.backend.domain;

import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_id")
    private Long id;

    private String name;

//    @Column(columnDefinition = "TEXT") // TODO(민우) : 뭔지 찾아보기
    private String description;

    private Integer scoville;

    private String sort;

    private String imgUrl;

    @OneToMany(mappedBy = "food", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @OneToMany(mappedBy = "food", cascade = CascadeType.REMOVE)
    private List<Like> likes;

    @Version
    private Long version;
}
