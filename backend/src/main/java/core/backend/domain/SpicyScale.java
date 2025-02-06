package core.backend.domain;

import jakarta.persistence.*;

@Entity
public class SpicyScale {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="spicyscale_id")
    private Long id;

    private String name;

    private Integer scoville;

    private String country;
}
