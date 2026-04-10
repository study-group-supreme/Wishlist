package Wishlist.repository;

import Wishlist.model.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbc;

    public MemberRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    private final RowMapper<Member> memberRowMapper = (rs, rowMapper) -> {
        Member m = new Member();
        m.setUsername(rs.getString("username"));
        m.setPassword(rs.getString("password"));
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        return m;
    };

    public Member findById(int id){
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbc.queryForObject(sql, memberRowMapper, id);
    }

    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        return jdbc.queryForObject(sql, memberRowMapper, username);
    }

    public Member findByEmail(String email) {
        String sql = "SELECT * FROM member WHERE email = ?";
        return jdbc.queryForObject(sql, memberRowMapper, email);
    }
}
