package wishlist.repository;

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
        return jdbc.query(sql, memberRowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        return jdbc.query(sql, memberRowMapper, username)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        return jdbc.query(sql, memberRowMapper, email)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public int insertMember(Member member) {
        String sql = """
                INSERT INTO member (username, password, name, email)
                VALUES (?, ?, ?, ?)
                """;
        return jdbc.update(
                sql,
                member.getUsername(),
                member.getPassword(),
                member.getName(),
                member.getEmail()
        );
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
