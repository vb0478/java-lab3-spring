package hello.storage;

import hello.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setFirst_name(rs.getString("first_name"));
            user.setLast_name(rs.getString("last_name"));
            user.setAge(rs.getInt("age"));
            user.setSex(rs.getString("sex"));
            user.setCountry(rs.getString("country"));
            return user;
        }
    }

    public List< User > findAll() {
        return jdbcTemplate.query("select * from users", new UserRowMapper());
    }

    public Optional < User > findById(long id) {
        return Optional.of(jdbcTemplate.queryForObject("select * from users where id=?", new Object[] {
                        id
                },
                new BeanPropertyRowMapper< User >(User.class)));
    }

    public int deleteById(long id) {
        return jdbcTemplate.update("delete from users where id=?", new Object[] {
                id
        });
    }

    public int insert(User user) {
        return jdbcTemplate.update("insert into users (id, first_name, last_name, age, sex, country) " + "values(?, ?, ?, ?, ?, ?)",
                new Object[] {
                        user.getId(), user.getFirst_name(), user.getLast_name(), user.getAge(), user.getSex(), user.getCountry()
                });
    }

    public int update(User user) {
        return jdbcTemplate.update("update users " + " set first_name = ?, last_name = ?, age = ?, sex = ?, country = ? " + " where id = ?",
                user.getFirst_name(), user.getLast_name(), user.getAge(), user.getSex(), user.getCountry(), user.getId());
    }
}
