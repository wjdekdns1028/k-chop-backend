package core.backend.security.controller;

import core.backend.domain.RoleType;
import core.backend.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.backend.domain.Member;
import core.backend.repository.MemberRepository;

// 인증(회원가입, 로그인, 로그아웃) API 컨트롤러
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //회원가입 API
    @PostMapping("/signup")
    public String signup(@RequestBody Member member) {
        // 이미 존재하는 사용자 확인
        if (memberRepository.findByEmail(member.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        //비밀번호 암호화 후 회원 저장
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setRole(RoleType.ROLE_USER); //기본 권한 설정
        memberRepository.save(member);

        return "회원가입 성공";
    }

    //로그인 API(JWT 발급)
    @PostMapping("/login")
    public String login(@RequestBody Member member){
        Member foundMember = memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if(!passwordEncoder.matches(member.getPassword(), foundMember.getPassword())){
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(foundMember.getEmail(), foundMember.getRole().name());
    }

    //로그아웃 API(클라이언트에서 JWT 삭제)
    @PostMapping("/logout")
    public String logout(){
        return "로그아웃 성공";
    }
}
