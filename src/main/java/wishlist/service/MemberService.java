package wishlist.service;

import org.springframework.stereotype.Service;
import wishlist.model.Member;
import wishlist.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member getById(int id){
        return memberRepository.findById(id);
    }

    public Member getByUsername(String username){
        return memberRepository.findByUsername(username);
    }

}
