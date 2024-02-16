package com.ssafy.ViewCareFull.domain.helper;

import com.ssafy.ViewCareFull.domain.users.dto.LoginForm;
import com.ssafy.ViewCareFull.domain.users.entity.PermissionType;
import com.ssafy.ViewCareFull.domain.users.entity.UserLink;
import com.ssafy.ViewCareFull.domain.users.entity.user.Caregiver;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.entity.user.Hospital;
import com.ssafy.ViewCareFull.domain.users.repository.UserLinkRepository;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.context.WebApplicationContext;

@Service
@ActiveProfiles("test")
public class UserRegisterHelper {

  private final UserLinkRepository userLinkRepository;
  private final UsersRepository usersRepository;
  private final UsersService usersService;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserLinkService userLinkService;

  public UserRegisterHelper(UserLinkRepository userLinkRepository, UsersRepository usersRepository,
      UsersService usersService, JwtAuthenticationFilter jwtAuthenticationFilter, UserLinkService userLinkService) {
    this.userLinkRepository = userLinkRepository;
    this.usersRepository = usersRepository;
    this.usersService = usersService;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userLinkService = userLinkService;
  }

  public void execute(WebApplicationContext context) {
    PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
    Guardian guardian = Guardian.builder()
        .userName("guardian")
        .domainId("guardian")
        .password(passwordEncoder.encode("1234"))
        .build();
    usersRepository.save(guardian);
    Hospital hospital = Hospital.builder()
        .userName("hospital")
        .domainId("hospital")
        .password(passwordEncoder.encode("1234"))
        .build();
    usersRepository.save(hospital);
    Caregiver caregiver = Caregiver.builder()
        .userName("caregiver")
        .domainId("caregiver")
        .password(passwordEncoder.encode("1234"))
        .token("token")
        .hospital(hospital)
        .build();
    usersRepository.save(caregiver);
    UserLink userLink = UserLink.builder()
        .caregiver(caregiver)
        .guardian(guardian)
        .hospital(hospital)
        .agreement(PermissionType.A)
        .relationship("부모")
        .build();
    userLinkRepository.save(userLink);
  }

  public String getCaregiverAccessToken() {
    return "Bearer " + usersService.login(LoginForm.builder()
        .id("caregiver")
        .password("1234")
        .build()).getAccessToken();
  }

  public String getGuardianAccessToken() {
    return "Bearer " + usersService.login(LoginForm.builder()
        .id("guardian")
        .password("1234")
        .build()).getAccessToken();
  }

  public String getHospitalAccessToken() {
    return "Bearer " + usersService.login(LoginForm.builder()
        .id("hospital")
        .password("1234")
        .build()).getAccessToken();
  }

  public Caregiver getCaregiver() {
    return (Caregiver) usersRepository.findByDomainId("caregiver").get();
  }

  public Guardian getGuardian() {
    return (Guardian) usersRepository.findByDomainId("guardian").get();
  }

  public Hospital getHospital() {
    return (Hospital) usersRepository.findByDomainId("hospital").get();
  }

  public UserLink getUserLink() {
    return userLinkRepository.findLinkByCaregiverId(getCaregiver().getId()).get();
  }
}
