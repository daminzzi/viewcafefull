package com.ssafy.ViewCareFull.domain.conference.service;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfo;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfoListDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfoSummaryDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceTodayListDto;
import com.ssafy.ViewCareFull.domain.conference.dto.TodayConferenceInfo;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;
import com.ssafy.ViewCareFull.domain.conference.repository.ConferenceRepository;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (userLink.getGuardian().getId().equals(securityUser.getUser().getId())) {
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
    if(!conferenceRepository.existsById(id)){
      throw new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE);
    }
    conferenceRepository.deleteById(id);
  }

  public ConferenceTodayListDto getConferenceList(SecurityUsers securityUser, String type,
      LocalDate startDate, LocalDate endDate, String order, Pageable pageable) {

    Users user = securityUser.getUser();

    if ("app".equals(type) && "Guardian".equals(user.getUserType())) {
      ConferenceInfoListDto conferenceInfoListDto = ConferenceInfo.toList(
          getConferenceListByGuardian(user.getId(), startDate, endDate, pageable).getContent(), order);

      List<TodayConferenceInfo> todayConferenceInfoList = conferenceRepository.findAllByGuardianIdAndPermissionState(
              user.getId(), PermissionType.A, LocalDate.now())
          .orElseGet(Collections::emptyList)
          .stream()
          .map(TodayConferenceInfo::new).toList();

      return new ConferenceTodayListDto(conferenceInfoListDto, todayConferenceInfoList);

    } else if ("per".equals(type) && "Hospital".equals(user.getUserType())) {
      ConferenceInfoListDto conferenceListDto = ConferenceInfo.toList(
          getConferenceListByHospital(user.getId(), startDate, endDate),
          order);

      return new ConferenceTodayListDto(conferenceListDto, null);
    }
    throw new ConferenceException(ConferenceErrorCode.INVALID_TYPE);
  }

  public List<Conference> getConferenceListByHospital(Long hospitalId, LocalDate startDate, LocalDate endDate) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByHospitalIdBetweenDate(hospitalId, startDate.atStartOfDay(),
          endDate.atTime(LocalTime.MAX)).orElseGet(Collections::emptyList);
    }
    return conferenceRepository.findAllByHospitalId(hospitalId).orElseGet(Collections::emptyList);
  }

  public Page<Conference> getConferenceListByGuardian(Long guardianId, LocalDate startDate, LocalDate endDate,
      Pageable pageable) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByGuardianIdBetweenDate(guardianId, startDate.atStartOfDay(),
          endDate.atTime(LocalTime.MAX), pageable);
    }
    return conferenceRepository.findAllByGuardianId(guardianId, pageable);
  }


  public int countNewReservation(SecurityUsers securityUser) {
    return conferenceRepository.countByHospitalIdAndPermissionState(securityUser.getUser().getId(), PermissionType.S);
  }

  public ConferenceInfoSummaryDto getMainConferenceList(SecurityUsers securityUser) {
    int newConferenceCnt = countNewReservation(securityUser);
    List<TodayConferenceInfo> todayConferenceList =
        getConferenceListByHospital(securityUser.getUser().getId(), LocalDate.now(), LocalDate.now())
            .stream()
            .map((TodayConferenceInfo::new)).toList();
    return new ConferenceInfoSummaryDto(newConferenceCnt, todayConferenceList);
  }


  public Conference getConferenceById(Long conferenceId) {
    return conferenceRepository.getConferenceById(conferenceId)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
  }
}
