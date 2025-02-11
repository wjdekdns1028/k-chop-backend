package core.backend.domain;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @ManyToOne
    @JoinColumn(name="food_id", nullable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @Lob // 긴 문자열을 저장할 때 사용
    @Column(columnDefinition = "TEXt", nullable = false)
    private String content;

    private Integer rating;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    private Integer upvote;

    private Integer downvote;

    
    @Column(nullable = false)
    private int spicyLevel; // 1~5단계 매운맛 평가
}
