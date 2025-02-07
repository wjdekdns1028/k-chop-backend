package core.backend.domain;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "members")
public class Member {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(unique = true, nullable = false) // 중복 방지, 필수값 설정
    private String email;

    private String name;

    @Column(nullable = false) // 필수값 설정
    private String password;

//    private RoleType role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String nationality;

    @Column(updatable = false) // 회원가입 시 자동 생성(수정 불가)
    private LocalDateTime createDate;

    private String badge;
    private String photoUrl;

    //cascade = CascadeType.ALL, orphanRemoval = true -> 회원 삭제 시 리뷰와 좋아요 함께 삭제되는 코드
    @OneToMany(mappedBy ="member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
    // Null Pointer Exception 방지를 위해 초기화
    @OneToMany(mappedBy ="member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Heart> hearts = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.createDate = LocalDateTime.now();
    }
}
