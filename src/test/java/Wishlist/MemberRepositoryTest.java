package Wishlist;

import Wishlist.model.Member;
import Wishlist.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findById_returnsCorrectMember() {
        Member member = memberRepository.findById(1);

        assertThat(member.getUsername()).isEqualTo("Shaz");
    }

    @Test
    void findByUsername_returnsCorrectMember() {
        Member member = memberRepository.findByUsername("Shaz");

        assertThat(member.getName()).isEqualTo("August Skipper");
    }

    @Test
    void findByEmail_returnsCorrectMember() {
        Member member = memberRepository.findByEmail("goegl12@gmail.com");

        assertThat(member.getName()).isEqualTo("Andreas Jensen");
    }

    @Test
    void insert_addsNewMember() {
        Member newMember = new Member();
        newMember.setName("Gabriella");
        newMember.setUsername("bella");
        newMember.setPassword("pw");
        newMember.setEmail("bella@example.com");

        int rows = memberRepository.insert(newMember);
        assertThat(rows).isEqualTo(1);

        Member fetched = memberRepository.findByUsername("bella");
        assertThat(fetched.getName()).isEqualTo("Gabriella");
    }
}
