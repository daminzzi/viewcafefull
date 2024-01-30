package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.repository.UserLinkRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLinkService {

  private final UserLinkRepository userLinkRepository;

  public Optional<CaregiverIdDto> getCareGiverIdFromOtherUser(Long userId) {
    Optional<UserLink> caregiver = userLinkRepository.findLinkByCaregiverId(userId);
    if (caregiver.isPresent()) {
      return Optional.of(new CaregiverIdDto(caregiver.get()));
    }
    Optional<UserLink> hospital = userLinkRepository.findLinkByHospitalId(userId);
    return hospital.map(CaregiverIdDto::new);
  }

}
