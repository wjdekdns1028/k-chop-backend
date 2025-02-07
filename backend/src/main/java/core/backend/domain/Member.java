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

    // TODO(민우) : 회원가입할 때 spring security가 알아서 수정했었던 거 같은데 잘 모르겠음.
//    private RoleType role;
    private String role;

    private String nationality;

    private LocalDateTime createDate;

    private String badge;

    private String photoUrl;

    @OneToMany(mappedBy ="member")
    private List<Review> reviews;

    @OneToMany(mappedBy ="member")
    private List<Like> likes;
}
