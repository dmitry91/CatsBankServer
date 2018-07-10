package com.catsbank.db.mapper;

import com.catsbank.db.entities.Cat;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CatRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        Cat cat = new Cat();
        cat.setId(resultSet.getInt("id"));
        cat.setText(resultSet.getString("text"));
        cat.setPhoto(resultSet.getBytes("photo"));
        return cat;
    }
}
