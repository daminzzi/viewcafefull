package com.ssafy.ViewCareFull.domain.conference.service;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;
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
  public void reserveConference(ConferenceReservationDto conferenceReservationDto) {
    conferenceReservationDto.makeApplicationIds();

    List<UserLink> userLinkByCaregiver = userLinkService.getGuardianListByCaregiver(
        conferenceReservationDto.getTargetId());

    Conference conference = Conference.of(userLinkByCaregiver.get(0), conferenceReservationDto.getConferenceDate(),
        conferenceReservationDto.getConferenceTime());

    for (UserLink userLink : userLinkByCaregiver) {
      if (chkApply(conferenceReservationDto.getApplicationIds(), userLink.getGuardian())) {
        conference.addReservationList(userLink);
      }
    }
    conferenceRepository.save(conference);
  }

  private boolean chkApply(Set<String> applicationIds, Guardian guardian) {
    return applicationIds.contains(guardian.getDomainId());
  }


  @Transactional
  public void updateConferencePermissionState(Long id, ConferenceStateDto conferenceStateDto) {
    Conference conference = conferenceRepository.findById(id)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
    conference.updatePermissionState(conferenceStateDto);
  }
}
