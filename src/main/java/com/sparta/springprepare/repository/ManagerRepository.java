package com.sparta.springprepare.repository;

import com.sparta.springprepare.dto.ManagerRequestDto;
import com.sparta.springprepare.entity.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ManagerRepository {
    private final JdbcTemplate jdbcTemplate;

    // 데이터 조회 결과를 객체의 각 파트에 변환
    private RowMapper<Manager> managerRowMapper() {
        return((rs, rowNum) -> {
            Manager manager = new Manager();
            manager.setManagerId(rs.getLong("managerId"));
            manager.setManagerName(rs.getString("managerName"));
            manager.setEmail(rs.getString("email"));
            manager.setRegDate(rs.getTimestamp("regDate").toLocalDateTime());
            manager.setModDate(rs.getTimestamp("modDate").toLocalDateTime());
            return manager;
        });
    }

    // 관리자 등록
    public Long save(ManagerRequestDto managerRequestDto) {
        String sql = "INSERT INTO Manager (managerName, email, regDate, modDate) " +
                "VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql, managerRequestDto.getManagerName(), managerRequestDto.getEmail());

        String idQuery = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(idQuery, Long.class);
        return id;
    }

    // 저장된 관리자 특정 Id값을 통해 내용 가져오기
    public Manager findById(Long managerId) {
        String sql = "SELECT * FROM Manager WHERE managerId = ?";
        return jdbcTemplate.queryForObject(sql, managerRowMapper(), managerId);
    }
}
