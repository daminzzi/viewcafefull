package com.ssafy.ViewCareFull.domain.condition.service;


import com.ssafy.ViewCareFull.domain.condition.dto.ConditionRequestDto;
import com.ssafy.ViewCareFull.domain.condition.dto.ConditionResponseDto;
import com.ssafy.ViewCareFull.domain.condition.entity.Conditions;
import com.ssafy.ViewCareFull.domain.condition.repository.ConditionRepository;
import com.ssafy.ViewCareFull.domain.gallery.exception.NoMatchCaregiverException;
import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
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
  private final UsersService usersService;
  private final UserLinkService userLinkService;

  @Transactional
  public HttpStatus saveOrUpdate(SecurityUsers securityUsers, ConditionRequestDto requestDto) {
    Users user = securityUsers.getUser();
    Optional<Conditions> object = conditionRepository.findByUserAndDateAndTime(user, requestDto.getDay(),
        requestDto.getTimeType());
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
    Map<LocalDate, ConditionResponseDto> map = getLocalDateConditionResponseDtoMap(conditionsList);
    return List.copyOf(map.values());
  }

  private static Map<LocalDate, ConditionResponseDto> getLocalDateConditionResponseDtoMap(
      List<Conditions> conditionsList) {
    Map<LocalDate, ConditionResponseDto> map = new HashMap<>();
    for (Conditions conditions : conditionsList) {
      if (map.containsKey(conditions.getDate())) {
        ConditionResponseDto dto = map.get(conditions.getDate());
        dto.updateData(conditions);
        continue;
      }
      map.put(conditions.getDate(), new ConditionResponseDto(conditions));
    }
    return map;
  }
}
