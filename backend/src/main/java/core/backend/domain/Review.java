package core.backend.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name="food_id")

    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
    private Member member;

    private String content;

    private Integer rating;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    private Integer upvote;

    private Integer downvote;
}
