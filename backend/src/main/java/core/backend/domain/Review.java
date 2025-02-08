package core.backend.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="food_id")
    @NonNull
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @NotNull
    private Member member;

    private String content;

    private Integer rating;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Integer upvote;

    private Integer downvote;
}
