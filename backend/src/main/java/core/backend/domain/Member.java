package core.backend.domain;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
    @JsonIgnore // api응답에서 비밀번호 필드 제외
    private String password;

//    private RoleType role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String nationality;

    @Column(updatable = false) // 회원가입 시 자동 생성(수정 불가)
    @CreationTimestamp // 쿼리 Insert 시 현재시간 저장
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private BadgeType badge;
    private String photoUrl;

/*    //cascade = CascadeType.ALL, orphanRemoval = true -> 회원 삭제 시 리뷰와 좋아요 함께 삭제되는 코드
//    @OneToMany(mappedBy ="member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
    // Null Pointer Exception 방지를 위해 초기화
//    @OneToMany(mappedBy ="member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Heart> hearts = new ArrayList<>();
*/
}
