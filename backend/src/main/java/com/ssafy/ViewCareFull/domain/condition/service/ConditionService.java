package com.ssafy.ViewCareFull.domain.condition.service;


import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionResponseDto;
import com.ssafy.ViewCareFull.domain.condition.entity.Condition;
import com.ssafy.ViewCareFull.domain.condition.repository.ConditionRepository;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ConditionService {

  private final ConditionRepository conditionRepository;

  @Transactional
  public HttpStatus saveOrUpdate(SecurityUsers securityUsers, ConditionRequestDto requestDto) {
    Users user = securityUsers.getUser();
    Optional<Condition> object = conditionRepository.findByUserAndDateAndTime(user, requestDto.getDay(),
        requestDto.getTimeType());
    if (object.isPresent()) {
      Condition condition = object.get();
      condition.updateCondition(requestDto.getConditionType());
      return HttpStatus.OK;
    }
    conditionRepository.save(requestDto.toEntity(user));
    return HttpStatus.CREATED;
  }

  public List<ConditionResponseDto> getCondition(SecurityUsers securityUsers, LocalDate start, LocalDate end) {
    Users user = securityUsers.getUser();
    Map<LocalDate, ConditionResponseDto> map = new HashMap<>();
    List<Condition> conditionList = conditionRepository.findByUserAndDateBetween(user, start, end);
    for (Condition condition : conditionList) {
      if (map.containsKey(condition.getDate())) {
        ConditionResponseDto dto = map.get(condition.getDate());
        dto.updateData(condition);
        continue;
      }
      map.put(condition.getDate(), new ConditionResponseDto(condition));
    }
    return List.copyOf(map.values());
  }
}
