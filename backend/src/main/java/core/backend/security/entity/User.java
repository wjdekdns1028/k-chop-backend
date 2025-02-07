package core.backend.security.entity;

import jakarta.persistence.*;
import lombok.*;

import lombok.Getter;

import javax.management.relation.Role;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // UUID를 기본 키로 자동 생성
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String nationality;

    @Enumerated(EnumType.STRING) //  Enum값을 문자열로 DB에 저장
    private Role role;
}
