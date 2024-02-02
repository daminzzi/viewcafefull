package com.ssafy.ViewCareFull.domain.conference.repository;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
