package core.backend.service;

import java.util.Optional;
import core.backend.domain.Member;
import core.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    userId, name, nationality, profile_photo
//    public

    public Member getMember(Long id){
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new IllegalArgumentException("Member not found by id: " + id);
        }
    }
}
