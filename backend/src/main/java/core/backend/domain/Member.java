package core.backend.domain;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String email;

    private String name;

    private String password;

//    private RoleType role;
    private String role;

    private String nationality;

    private LocalDateTime createDate;

    private String badge;

    private String photoUrl;

    @OneToMany(mappedBy ="member")
    private List<Review> reviews = new ArrayList<>();
    // Null Pointer Exception 방지를 위해 초기화
    @OneToMany(mappedBy ="member")
    private List<Heart> hearts = new ArrayList<>();
}
