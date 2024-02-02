package com.ssafy.ViewCareFull.domain.conference.service;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservation;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.repository.ConferenceRepository;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConferenceService {

  private final ConferenceRepository conferenceRepository;
  private final UserLinkService userLinkService;


  @Transactional
  public void reserveConference(ConferenceReservation conferenceReservation) {
    conferenceReservation.makeApplicationIds();

    List<UserLink> userLinkByCaregiver = userLinkService.getGuardianListByCaregiver(
        conferenceReservation.getPermissionId());

    for (UserLink userLink : userLinkByCaregiver) {
      if (chkApply(conferenceReservation.getApplicationIds(), userLink, userLink.getGuardian())) {
        conferenceRepository.save(Conference.of(userLink, conferenceReservation.getConferenceDate(),
            conferenceReservation.getConferenceTime()));
      }
    }
  }

  private boolean chkApply(Set<String> applicationIds, UserLink userLink, Guardian guardian) {
    return applicationIds.contains(guardian.getDomainId());
  }

}
