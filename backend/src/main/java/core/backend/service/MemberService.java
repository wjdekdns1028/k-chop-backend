package core.backend.service;

import core.backend.domain.BadgeType;
import core.backend.domain.Member;
import core.backend.repository.MemberRepository;
import core.backend.repository.ReviewRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member getUser(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }

    public void updateBadge(Member member, int reviewCount) {
        BadgeType badge;
        switch (reviewCount / 5) {
            case 1:
                badge = BadgeType.LEVEL_ONE;
                break;
            case 2:
                badge = BadgeType.LEVEL_TWO;
                break;
            case 3:
                badge = BadgeType.LEVEL_THREE;
                break;
            default:
                badge = BadgeType.LEVEL_FOUR;
        }
        member.setBadge(badge); // JPA - Dirty Checking
    }
}
