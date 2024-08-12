package com.sparta.springprepare.repository;

import com.sparta.springprepare.dto.ScheduleDto;
import com.sparta.springprepare.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
            schedule.setScheduleId(rs.getLong("scheduleId"));
            schedule.setContents(rs.getString("contents"));
            schedule.setPassword(rs.getString("password"));
            schedule.setManager(rs.getString("manager"));
            schedule.setRegDate(rs.getTimestamp("regDate").toLocalDateTime());
            schedule.setModDate(rs.getTimestamp("modDate").toLocalDateTime());
            schedule.setDeleteStatus(rs.getBoolean("deleteStatus"));
            return schedule;
        });
    }

    // 할 일 등록
    public Long save(ScheduleDto scheduleDto) {
        String sql = "INSERT INTO Schedule (contents, password, manager, regDate, modDate, deleteStatus) " +
                "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, FALSE)";
        jdbcTemplate.update(sql, scheduleDto.getContents(), scheduleDto.getPassword(), scheduleDto.getManager());

        // 최근 마지막에 삽입된 scheduleId값 가져오기 -> 작성할 때 등록된 정보를 반환받기 위해서
        // Long타입으로 직접 변환하려고 했으나 오류 발생,
        // 해결 -> queryForObject를 통해 단일값을 가져오면서 Long타입으로 가져오도록 지시
        String idQuery = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(idQuery, Long.class);
        return id;
    }

    // 저장된 일 특정 Id값을 통해 내용 가져오기
    public Schedule findById(Long scheduleId) {
        String sql = "SELECT * FROM Schedule WHERE scheduleId = ?";
        return jdbcTemplate.queryForObject(sql, scheduleRowMapper(), scheduleId);
    }

    // 전체 일정 가져오기
    public List<Schedule> getList() {
        String sql = "SELECT * FROM Schedule WHERE deleteStatus = FALSE";
        return jdbcTemplate.query(sql, scheduleRowMapper());
    }

    // 일정 수정 -> JdbcTemplate update 메서드 사용 시 실행 후 영향을 받은 행의 수를 반환
    // 참고 : https://preamtree.tistory.com/91
    public int update(ScheduleDto scheduleDto) {
        String sql = "UPDATE Schedule SET contents = ?, manager = ?, modDate = CURRENT_TIMESTAMP " +
                "WHERE scheduleId = ? AND password = ?";
        return jdbcTemplate.update(sql, scheduleDto.getContents(), scheduleDto.getManager(), scheduleDto.getScheduleId(), scheduleDto.getPassword());
    }

    // 일정 삭제
    public int delete(ScheduleDto scheduleDto) {
        String sql = "UPDATE Schedule SET deleteStatus = TRUE WHERE ScheduleId = ? AND password = ?";
        return jdbcTemplate.update(sql, scheduleDto.getScheduleId(), scheduleDto.getPassword());
    }

}
