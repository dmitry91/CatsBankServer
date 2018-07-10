package com.catsbank.db.dao;

import com.catsbank.db.entities.Cat;
import com.catsbank.db.mapper.CatRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatsRepository {

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    public CatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addCat(String text, byte[] photo) {
        return jdbcTemplate.update("INSERT INTO cats(text, photo) VALUES (?,?)", text, photo);
    }

    public int updCat(int id, String text, byte[] photo) {
        return jdbcTemplate.update("update cats set text = ?, photo = ? where id = ?",text, photo, id);
    }
    public int updCat(int id, String text) {
        return jdbcTemplate.update("update cats set text = ? where id = ?",text, id);
    }

    public List<Cat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM cats",
                (rs, rowNum) -> new Cat(rs.getInt("id"),
                        rs.getString("text"),
                        rs.getBytes("photo"))
        );
    }

    public Cat findById(int id) {
        return (Cat) jdbcTemplate.queryForObject(
                "SELECT * FROM cats WHERE id = ?",
                new Object[]{id},
                new CatRowMapper());
    }

    public void deleteById(int id){
        jdbcTemplate.execute("DELETE FROM cats WHERE id="+id);
    }
}
