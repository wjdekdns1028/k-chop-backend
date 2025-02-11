package core.backend.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="heart_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="food_id", nullable = false)
    private Food food;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;
}
