package com.ssafy.ViewCareFull.domain.users.service;

import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.dto.LinkRequestDto;
import com.ssafy.ViewCareFull.domain.users.dto.LinkUpdateRequestDto;
import com.ssafy.ViewCareFull.domain.users.dto.UserLinkListResponseDto;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserLinkNotMatchException;
import com.ssafy.ViewCareFull.domain.users.error.exception.UserTypeException;
import com.ssafy.ViewCareFull.domain.users.repository.UserLinkRepository;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserLinkService {

  private final UserLinkRepository userLinkRepository;
  private final UsersService usersService;

  public Optional<CaregiverIdDto> getCareGiverIdFromOtherUser(Long userId) {
    Optional<UserLink> caregiver = userLinkRepository.findFirstByCaregiver_Id(userId);
    if (caregiver.isPresent()) {
      return Optional.of(new CaregiverIdDto(caregiver.get()));
    }
    Optional<UserLink> guardian = userLinkRepository.findLinkByGuardianId(userId);
    return guardian.map(CaregiverIdDto::new);
  }

  public UserLinkListResponseDto getLinkList(SecurityUsers securityUsers, String type) {
    Users user = securityUsers.getUser();
    if (user.getUserType().equals("Guardian")) {
      if (type.equals("app")) {
        return new UserLinkListResponseDto(userLinkRepository.findCaregiverByGuardian(user.getId())
            .orElseThrow(UserLinkNotMatchException::new));
      }
      if (type.equals("tar")) {
        return new UserLinkListResponseDto(userLinkRepository.findAllGuardianByGuardian(user.getId())
            .orElseThrow(UserLinkNotMatchException::new));
      }
    } else if (user.getUserType().equals("Caregiver")) {
      return new UserLinkListResponseDto(userLinkRepository.findAllGuardianByCaregiver(user.getId())
          .orElseThrow(UserLinkNotMatchException::new));
    }
    throw new UserTypeException();
  }

  @Transactional
  public void addLink(SecurityUsers securityUsers, LinkRequestDto linkRequestDto) {
    Users user = securityUsers.getUser();
    if (!user.getUserType().equals("Guardian")) {
      throw new UserTypeException();
    }
    Guardian guardian = (Guardian) user;
    Caregiver caregiver = usersService.getByCaregiverToken(linkRequestDto.getTargetCode());
    Hospital hospital = caregiver.getHospital();
    userLinkRepository.save(UserLink.of(guardian, caregiver, hospital, linkRequestDto.getRelationship()));
  }

  public void updateLink(Long id, LinkUpdateRequestDto linkUpdateRequestDto) {
    UserLink userLink = userLinkRepository.findById(id).orElseThrow(UserLinkNotMatchException::new);
    userLink.updateAgreement(linkUpdateRequestDto.getAgreement());
  }

  public void deleteLink(Long id) {
    userLinkRepository.deleteById(id);
  }
}
