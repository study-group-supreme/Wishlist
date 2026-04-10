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
        m.setEmail(rs.getString("email"));
        return m;
    };

    public Member findById(int id){
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbc.queryForObject(sql, memberRowMapper, id);
    }
}
