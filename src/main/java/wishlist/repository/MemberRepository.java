package wishlist.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import wishlist.exception.DuplicateMemberException;
import wishlist.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbc;

    public MemberRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
        Member m = new Member();
        m.setId(rs.getInt("id"));
        m.setUsername(rs.getString("username"));
        m.setPassword(rs.getString("password"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        return m;
    };

    public Member findById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbc.queryForObject(sql, memberRowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        try {
            return jdbc.queryForObject(sql, memberRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        try {
            return jdbc.queryForObject(sql, memberRowMapper, email);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    public int insertMember(Member member) {

        if (findByUsername(member.getUsername()) != null) {
            throw new DuplicateMemberException("Username exists");
        }
        if (findByEmail(member.getEmail()) != null) {
            throw new DuplicateMemberException("Email already registered");
        }
        String sql = "INSERT INTO member (username, password, name, email) VALUES (?, ?, ?, ?)";
        return jdbc.update(
                sql,
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getEmail());
    }

    public int updateMember(Member member) {
        String sql = """
                UPDATE member
                SET username = ?, password = ?, name = ?, email = ?
                WHERE id = ?
                """;
        return jdbc.update(
                sql,
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getId()
        );
    }

    public int deleteById(int memberId) {
        String sql = "DELETE FROM member WHERE id = ?";
        return jdbc.update(sql, memberId);
    }
}
