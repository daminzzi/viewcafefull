package com.ssafy.ViewCareFull.domain.conference.service;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfo;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfoListDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;
import com.ssafy.ViewCareFull.domain.conference.repository.ConferenceRepository;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
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
  public void reserveConference(SecurityUsers securityUser, ConferenceReservationDto conferenceReservationDto) {

    conferenceReservationDto.makeApplicationIds();

    List<UserLink> userLinkByCaregiver = userLinkService.getGuardianListByCaregiver(
        conferenceReservationDto.getTargetId());

    Conference conference = Conference.createConference(conferenceReservationDto.getConferenceDate(),
        conferenceReservationDto.getConferenceTime());

    for (UserLink userLink : userLinkByCaregiver) {
      if (chkApply(conferenceReservationDto.getApplicationIds(), userLink.getGuardian())) {
        if (userLink.getGuardian().getId() == securityUser.getUser().getId()) {
          conference.linkApplicationUsers(userLink);
        }
        conference.addReservationList(userLink);
      }
    }
    conferenceRepository.save(conference);
  }

  private boolean chkApply(Set<String> applicationIds, Guardian guardian) {
    return applicationIds.contains(guardian.getDomainId());
  }


  @Transactional
  public void updateConferencePermissionState(SecurityUsers securityUser, Long id,
      ConferenceStateDto conferenceStateDto) {

    Conference conference = conferenceRepository.findById(id)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
    conference.updatePermissionState(conferenceStateDto);
  }

  @Transactional
  public void deleteConference(Long id) {
    if (!conferenceRepository.existsById(id)) {
      throw new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE);
    }
    conferenceRepository.deleteById(id);
  }

  public ConferenceInfoListDto getConferenceList(SecurityUsers securityUser, String type, String domainId,
      LocalDate startDate, LocalDate endDate, String order) {

    Users user = securityUser.getUser();

    if ("app".equals(type) && "Guardian".equals(user.getUserType())) {
      return ConferenceInfo.toList(
          getConferenceListByGuardian(user.getId(), startDate, endDate), order);
    } else if ("per".equals(type) && "Hospital".equals(user.getUserType())) {
      return ConferenceInfo.toList(getConferenceListByHospital(user.getId(), startDate, endDate),
          order);
    }
    throw new ConferenceException(ConferenceErrorCode.INVALID_TYPE);
  }

  public List<Conference> getConferenceListByHospital(Long hospitalId, LocalDate startDate, LocalDate endDate) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByHospitalIdBetweenDate(hospitalId, startDate.atStartOfDay(),
              endDate.atTime(LocalTime.MAX))
          .orElseGet(Collections::emptyList);
    }
    return conferenceRepository.findAllByHospitalId(hospitalId).orElseGet(Collections::emptyList);
  }

  public List<Conference> getConferenceListByGuardian(Long guardianId, LocalDate startDate, LocalDate endDate) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByGuardianIdBetweenDate(guardianId, startDate.atStartOfDay(),
              endDate.atTime(LocalTime.MAX))
          .orElseGet(Collections::emptyList);
    }
    return conferenceRepository.findAllByGuardianId(guardianId).orElseGet(Collections::emptyList);
  }

  public Conference getConferenceById(Long conferenceId) {
    return conferenceRepository.getConferenceById(conferenceId)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
  }
}
