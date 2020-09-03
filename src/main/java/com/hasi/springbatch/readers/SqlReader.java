package com.hasi.springbatch.readers;

import com.hasi.springbatch.domain.Billionaire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SqlReader implements RowMapper<Billionaire> {
    Logger logger = LoggerFactory.getLogger(SqlReader.class);

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcCursorItemReader<Billionaire> cursorItemReader() throws Exception {
        JdbcCursorItemReader<Billionaire> reader = new JdbcCursorItemReader<>();

        reader.setSql("select firstName, lastName, career from billionaires order by lastName, firstName");
        reader.setDataSource(this.dataSource);
        reader.setRowMapper(this::mapRow);
        return reader;
    }

    @Override
    public Billionaire mapRow(ResultSet resultSet, int i) throws SQLException {
        Billionaire billionaire =  new Billionaire(resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("career"));
        logger.info("read from DB {}", billionaire);
        return billionaire;
    }
}
