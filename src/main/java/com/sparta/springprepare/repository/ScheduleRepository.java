package com.sparta.springprepare.repository;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Manager;
import com.sparta.springprepare.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    // 데이터 조회 결과를 우리가 원하는 객체로 쉽게 변환하기 위해 사용
    // 참고 : https://innovation123.tistory.com/69
    private RowMapper<Schedule> scheduleRowMapper() {
        return((rs, rowNum) -> {
            Schedule schedule = new Schedule();
            Manager manager = new Manager();
            schedule.setScheduleId(rs.getLong("scheduleId"));
            schedule.setContents(rs.getString("contents"));
            schedule.setPassword(rs.getString("password"));

            manager.setManagerId(rs.getLong("managerId"));
            manager.setManagerName(rs.getString("managerName"));
            manager.setEmail(rs.getString("email"));
            manager.setRegDate(rs.getTimestamp("regDate").toLocalDateTime());
            manager.setModDate(rs.getTimestamp("modDate").toLocalDateTime());
            schedule.setManager(manager);

            schedule.setRegDate(rs.getTimestamp("regDate").toLocalDateTime());
            schedule.setModDate(rs.getTimestamp("modDate").toLocalDateTime());
            schedule.setDeleteStatus(rs.getBoolean("deleteStatus"));
            return schedule;
        });
    }

    // 할 일 등록
    public Long save(ScheduleDto scheduleDto) {
        String sql = "INSERT INTO Schedule (contents, password, managerId, regDate, modDate, deleteStatus) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE)";
        jdbcTemplate.update(sql, scheduleDto.getContents(), scheduleDto.getPassword(), scheduleDto.getManagerId());

        // 최근 마지막에 삽입된 scheduleId값 가져오기 -> 작성할 때 등록된 정보를 반환받기 위해서
        // Long타입으로 직접 변환하려고 했으나 오류 발생,
        // 해결 -> queryForObject를 통해 단일값을 가져오면서 Long타입으로 가져오도록 지시
        String idQuery = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(idQuery, Long.class);
        return id;
    }

    // 저장된 일 특정 Id값을 통해 내용 가져오기, + Manager 값들 가져오기 위해서 Join 실행
    public Schedule findById(Long scheduleId) {
        String sql = "SELECT s.*, m.* FROM Schedule s "+
        "JOIN Manager m ON s.managerId = m.managerId WHERE s.scheduleId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, scheduleRowMapper(), scheduleId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // 전체 일정 가져오기, + Manager 값들 가져오기 위해서 Join 실행
    // 동적 쿼리 참고 : https://rlaehddnd0422.tistory.com/93
    public List<Schedule> getList(Long managerId, String modDate, int pageNumber, int pageSize) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT s.*, m.* FROM Schedule s " +
                "JOIN Manager m ON s.managerId = m.managerId WHERE s.deleteStatus = FALSE";

        // 값이 있다면 sql 문자열에 추가로 붙이기
        if(managerId != null) {
            sql += " AND s.managerId = ?";
            params.add(managerId);
        }
        if(modDate != null) {
            sql += " AND DATE(s.modDate) = DATE(?)";  // 날짜 부분만 필터링 시분초까지 입력받지 말자..
            params.add(modDate);
        }

        // sql의 Limit과 OFFSET을 통해서 페이지네이션을 구현
        // 참고 : https://velog.io/@yoonuk/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-Pagination%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%98%EB%8A%94-SQL
        int offset = pageNumber * pageSize;
        sql += " LIMIT ? OFFSET ?";
        params.add(pageSize);
        params.add(offset);

        return jdbcTemplate.query(sql, params.toArray(), scheduleRowMapper());
    }

    // 일정 수정 -> JdbcTemplate update 메서드 사용 시 실행 후 영향을 받은 행의 수를 반환
    // 참고 : https://preamtree.tistory.com/91
    public int update(ScheduleDto scheduleDto) {
        String sql = "UPDATE Schedule SET contents = ?, managerId = ?, modDate = CURRENT_TIMESTAMP " +
                "WHERE scheduleId = ? AND password = ?";
        return jdbcTemplate.update(sql, scheduleDto.getContents(), scheduleDto.getManagerId(), scheduleDto.getScheduleId(), scheduleDto.getPassword());
    }

    // 일정 삭제
    public int delete(ScheduleDto scheduleDto) {
        String sql = "UPDATE Schedule SET deleteStatus = TRUE WHERE ScheduleId = ? AND password = ?";
        return jdbcTemplate.update(sql, scheduleDto.getScheduleId(), scheduleDto.getPassword());
    }

    // 일정 삭제 체크(이미 삭제가 된건지 아닌건지 확인)
    // Boolean.class 타입으로 매핑되어 반환 true인지 false인지, true이면 삭제되었다는 것을 의미
    public boolean isDelete(ScheduleDto scheduleDto) {
        String sql = "SELECT deleteStatus FROM Schedule WHERE scheduleId = ?";
        Boolean isDelete = jdbcTemplate.queryForObject(sql, Boolean.class, scheduleDto.getScheduleId());
        return isDelete;
    }
}
