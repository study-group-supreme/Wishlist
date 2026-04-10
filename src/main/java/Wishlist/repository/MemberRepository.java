package Wishlist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    private final JdbcTemplate jdbc;

    public MemberRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }
}
