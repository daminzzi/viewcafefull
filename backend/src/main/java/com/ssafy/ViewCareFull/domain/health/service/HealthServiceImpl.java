package com.ssafy.ViewCareFull.domain.health.service;

import com.ssafy.ViewCareFull.domain.health.dto.HealthInfo;
import com.ssafy.ViewCareFull.domain.health.entity.Health;
import com.ssafy.ViewCareFull.domain.health.error.HealthErrorCode;
import com.ssafy.ViewCareFull.domain.health.error.exception.HealthException;
import com.ssafy.ViewCareFull.domain.health.repository.HealthRepository;
import com.ssafy.ViewCareFull.domain.users.error.UserErrorCode;
import com.ssafy.ViewCareFull.domain.users.error.exception.UsersException;
import com.ssafy.ViewCareFull.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthServiceImpl implements HealthService {

  private final HealthRepository healthRepository;
  private final UsersRepository usersRepository;

  @Override
  @Transactional
  public void saveHealthInfo(String domainId, HealthInfo healthInfo) {
    // TODO: 연결정보CRUD 완료시 등록한사람(병원)과 입소자 연결이 있는지 체크하기
    // 건강정보를 등록하려는 입소자가 서비스에 등록된 사용자인지 체크
    usersRepository.findByDomainId(domainId)
        .orElseThrow(() -> new UsersException(UserErrorCode.NOT_FOUND_USERID));
    healthRepository.save(new Health(domainId, healthInfo));
  }

  @Override
  @Transactional
  public void deleteHealthInfo(String id) {
    Long healthId = Long.parseLong(id);
    // 삭제 하고자 하는 건강정보가 있는지 체크
    if (!healthRepository.existsById(healthId)) {
      throw new HealthException(HealthErrorCode.NOT_FOUND_HEALTH_ID);
    }
    healthRepository.deleteById(healthId);
  }

  @Override
  @Transactional
  public void updateHealthInfo(String id, HealthInfo healthInfo) {
    Long healthId = Long.parseLong(id);
    // 수정 하고자 하는 id의 건강정보가 있는지 체크
    Health updateHealth = healthRepository.findById(healthId)
        .orElseThrow(() -> new HealthException(HealthErrorCode.NOT_FOUND_HEALTH_ID));
    updateHealth.update(id, healthInfo);
  }

}
