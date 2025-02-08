package core.backend.controller;

import core.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import core.backend.domain.Member;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final MemberRepository memberRepository;

    //현재 로그인한 사용자 프로필 조회
    @GetMapping("/me")
    public Member getUserProfile(@AuthenticationPrincipal UserDetails userDetails) {
        return memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    //현재 로그인한 사용자 프로필 수정
    @PutMapping("/me")
    public Member updateUserProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Member updatedMember) {

        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        //수정할 수 있는 필드만 업데이트
        if (updatedMember.getName() != null) member.setName(updatedMember.getName());
        if (updatedMember.getPhotoUrl() != null) member.setPhotoUrl(updatedMember.getPhotoUrl());
        if (updatedMember.getNationality() != null) member.setNationality(updatedMember.getNationality());

        return memberRepository.save(member);
    }
}
