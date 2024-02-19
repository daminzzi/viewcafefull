package com.ssafy.ViewCareFull.domain.condition.service;


import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionResponseDto;
import com.ssafy.ViewCareFull.domain.report.dto.NumOfConditions;
import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.condition.repository.ConditionRepository;
import com.ssafy.ViewCareFull.domain.gallery.exception.NoMatchCaregiverException;
import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import java.time.LocalDate;
import java.util.Comparator;
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
  private final UsersService usersService;
  private final UserLinkService userLinkService;

  @Transactional
  public HttpStatus saveOrUpdate(SecurityUsers securityUsers, ConditionRequestDto requestDto) {
    Users user = securityUsers.getUser();
    Optional<Conditions> object = conditionRepository.findByUserAndDate(user, requestDto.getToDate());
    if (object.isPresent()) {
      Conditions conditions = object.get();
      conditions.updateCondition(requestDto.getConditionType());
      return HttpStatus.OK;
    }
    conditionRepository.save(requestDto.toEntity(user));
    return HttpStatus.CREATED;
  }

  public List<ConditionResponseDto> getCondition(SecurityUsers securityUsers, LocalDate start, LocalDate end) {
    Users user = securityUsers.getUser();
    CaregiverIdDto caregiverDto = userLinkService.getCareGiverIdFromOtherUser(user.getId())
        .orElseThrow(NoMatchCaregiverException::new);
    Users caregiver = usersService.getUser(caregiverDto.getCaregiverId());
    List<Conditions> conditionsList = conditionRepository.findByUserAndDateBetween(caregiver, start, end);
    Map<LocalDate, ConditionResponseDto> map = getLocalDateConditionResponseDtoMap(conditionsList, start, end);
    return List.copyOf(map.values()).stream().sorted(Comparator.comparing(ConditionResponseDto::getDate)).toList();
  }

  private static Map<LocalDate, ConditionResponseDto> getLocalDateConditionResponseDtoMap(
      List<Conditions> conditionsList, LocalDate start, LocalDate end) {
    Map<LocalDate, ConditionResponseDto> map = new HashMap<>();
    for (LocalDate date = start; date.isBefore(end.plusDays(1)); date = date.plusDays(1)) {
      map.put(date, new ConditionResponseDto(date));
    }
    for (Conditions conditions : conditionsList) {
      ConditionResponseDto responseDto = map.get(conditions.getDate());
      map.put(conditions.getDate(), responseDto.updateData(conditions));
    }
    return map;
  }

  public NumOfConditions cntCondition(long id, LocalDate start, LocalDate end) {
    return new NumOfConditions(conditionRepository.findAllByUserDateBetween(id, start, end));
  }
}
