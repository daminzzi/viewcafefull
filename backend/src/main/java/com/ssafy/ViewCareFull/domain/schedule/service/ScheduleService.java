package com.ssafy.ViewCareFull.domain.schedule.service;

import com.ssafy.ViewCareFull.domain.schedule.dto.ScheduleDto;
import com.ssafy.ViewCareFull.domain.schedule.dto.ScheduleListDto;
import com.ssafy.ViewCareFull.domain.schedule.entity.Schedule;
import com.ssafy.ViewCareFull.domain.schedule.repository.ScheduleRepository;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final UsersService usersService;

  public ScheduleListDto getScheduleList(String domainId) {
    Users hospital = usersService.getMatchingTypeUser(domainId, "Hospital");
    List<Schedule> findScheduleList = scheduleRepository.findAllById(hospital.getId());
    return new ScheduleListDto(findScheduleList.stream().map(ScheduleDto::of).toList());
  }

}
