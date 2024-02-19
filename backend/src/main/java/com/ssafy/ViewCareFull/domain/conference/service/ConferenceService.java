package com.ssafy.ViewCareFull.domain.conference.service;

import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfo;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceInfoSummaryDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceReservationDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceStateDto;
import com.ssafy.ViewCareFull.domain.conference.dto.ConferenceTodayListDto;
import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.error.ConferenceErrorCode;
import com.ssafy.ViewCareFull.domain.conference.error.exception.ConferenceException;
import com.ssafy.ViewCareFull.domain.conference.repository.ConferenceRepository;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
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
      if (conferenceReservationDto.chkApply(userLink.getGuardian())) {
        if (userLink.getGuardian().getId().equals(securityUser.getUser().getId())) {
          conference.linkApplicationUsers(userLink);
        }
        conference.addReservationList(userLink);
      }
    }
    conference.updatePermissionState("A");
    conferenceRepository.save(conference);
  }


  @Transactional
  public void updateConferencePermissionState(SecurityUsers securityUser, Long id,
      ConferenceStateDto conferenceStateDto) {

    Conference conference = conferenceRepository.findById(id)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
    conference.updatePermissionState(conferenceStateDto.getConferenceState());
  }

  @Transactional
  public void deleteConference(Long id) {
    if (!conferenceRepository.existsById(id)) {
      throw new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE);
    }
    conferenceRepository.deleteById(id);
  }

  public ConferenceTodayListDto getConferenceList(SecurityUsers securityUser, String type,
      LocalDate startDate, LocalDate endDate, int page, String order) {

    Pageable pageable;
    if ("early".equals(order)) {
      pageable = PageRequest.of(page - 1, 10);
    } else {
      pageable = PageRequest.of(page - 1, 10, Sort.by(Direction.DESC, "id"));
    }

    Users user = securityUser.getUser();

    if ("app".equals(type) && "Guardian".equals(user.getUserType())) {
      List<ConferenceInfo> reservedConferenceList = getConferenceListByGuardian(user.getId(), startDate, endDate,
          pageable).map(ConferenceInfo::of).getContent();

      List<ConferenceInfo> todayConferenceInfoList = ConferenceInfo.toList(
          conferenceRepository.findAllByGuardianIdAndPermissionState(
              user.getId(),
              PermissionType.A,
              LocalDate.now(ZoneId.of("Asia/Seoul")),
              LocalTime.now(ZoneId.of("Asia/Seoul"))));

      return new ConferenceTodayListDto(reservedConferenceList, todayConferenceInfoList);

    } else if ("per".equals(type) && "Hospital".equals(user.getUserType())) {
      List<ConferenceInfo> reservedConferenceList =
          getConferenceListByHospital(
              user.getId(),
              startDate,
              endDate,
              pageable)
              .map(ConferenceInfo::of)
              .getContent();

      return new ConferenceTodayListDto(reservedConferenceList, null);

    } else if ("tar".equals(type) && "Caregiver".equals(user.getUserType())) {
      List<ConferenceInfo> reservedConferenceList =
          getConferenceListByCaregiver(
              user.getId(),
              startDate,
              endDate,
              pageable)
              .map(ConferenceInfo::of)
              .getContent();

      List<ConferenceInfo> todayConferenceInfoList = ConferenceInfo.toList(
          conferenceRepository.findAllByCaregiverIdAndPermissionState(
              user.getId(),
              PermissionType.A,
              LocalDate.now(ZoneId.of("Asia/Seoul")),
              LocalTime.now(ZoneId.of("Asia/Seoul"))));

      return new ConferenceTodayListDto(reservedConferenceList, todayConferenceInfoList);
    }
    throw new ConferenceException(ConferenceErrorCode.INVALID_TYPE);
  }

  public Page<Conference> getConferenceListByCaregiver(Long caregiverId, LocalDate startDate, LocalDate endDate,
      Pageable pageable) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByCaregiverIdBetweenDate(caregiverId, startDate,
          endDate, pageable);
    }
    return conferenceRepository.findAllByCaregiverId(caregiverId, pageable);
  }


  public Page<Conference> getConferenceListByHospital(Long hospitalId, LocalDate startDate, LocalDate endDate,
      Pageable pageable) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByHospitalIdBetweenDate(hospitalId, startDate, endDate, pageable);
    }
    return conferenceRepository.findAllByHospitalId(hospitalId, pageable);
  }


  public Page<Conference> getConferenceListByGuardian(Long guardianId, LocalDate startDate, LocalDate endDate,
      Pageable pageable) {
    if (startDate != null && endDate != null) {
      return conferenceRepository.findAllByGuardianIdBetweenDate(guardianId, startDate,
          endDate, pageable);
    }
    return conferenceRepository.findAllByGuardianId(guardianId, pageable);
  }


  public int countNewReservation(SecurityUsers securityUser) {
    return conferenceRepository.countByHospitalIdAndPermissionState(securityUser.getUser().getId(), PermissionType.S);
  }

  public ConferenceInfoSummaryDto getMainConferenceList(SecurityUsers securityUser) {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "conferenceTime"));
    int newConferenceCnt = countNewReservation(securityUser);
    List<ConferenceInfo> todayConferenceList =
        getConferenceListByHospital(
            securityUser.getUser().getId(),
            LocalDate.now(ZoneId.of("Asia/Seoul")),
            LocalDate.now(ZoneId.of("Asia/Seoul")),
            pageable)
            .map(ConferenceInfo::of)
            .getContent();
    return new ConferenceInfoSummaryDto(newConferenceCnt, todayConferenceList);
  }


  public Conference getConferenceById(Long conferenceId) {
    return conferenceRepository.getConferenceById(conferenceId)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
  }

  public Conference getConferenceByRoomName(String RoomName) {
    return conferenceRepository.getConferenceByRoomName(RoomName)
        .orElseThrow(() -> new ConferenceException(ConferenceErrorCode.NOT_FOUND_CONFERENCE));
  }

  @Transactional
  public void updateConferenceStartTime(Conference conference, LocalDateTime localDateTime) {
    conference.getConferenceRoom().startConference(localDateTime);
  }

  @Transactional
  public void updateConferenceEndTime(Conference conference, LocalDateTime localDateTime) {
    conference.getConferenceRoom().endConference(localDateTime);
  }
}
