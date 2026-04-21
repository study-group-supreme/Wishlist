package wishlist.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import wishlist.exception.BadRequestException;
import wishlist.exception.DatabaseOperationException;
import wishlist.exception.NotFoundException;
import wishlist.repository.MemberRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    void getById_throwsBadRequest_whenIdInvalid() {
        assertThrows(BadRequestException.class, () -> memberService.getById(0));
    }

    @Test
    void getById_throwsNotFound_whenMemberMissing() {
        when(memberRepository.findById(5))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(NotFoundException.class, () -> memberService.getById(5));
    }

    @Test
    void getById_throwsDatabaseOperationException_whenDbFails() {
        when(memberRepository.findById(1))
                .thenThrow(new org.springframework.dao.DataAccessResourceFailureException("DB down"));

        assertThrows(DatabaseOperationException.class, () -> memberService.getById(1));
    }
}
