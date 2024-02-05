package com.ssafy.ViewCareFull.domain.gallery.service;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.gallery.entity.BestPhoto;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.exception.NoBestPhotoException;
import com.ssafy.ViewCareFull.domain.gallery.repository.BestPhotoRepository;
import com.ssafy.ViewCareFull.domain.gcp.service.GcpService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BestPhotoService {

  private final BestPhotoRepository bestPhotoRepository;
  private final GalleryService galleryService;
  private final ConferenceService conferenceService;
  private final GcpService gcpService;

  @Transactional
  public void writeBestPhoto(SecurityUsers securityUsers, MultipartFile imageFile, String conferenceId)
      throws IOException {
    Image image = galleryService.saveImage(securityUsers, imageFile);
    Conference conference = conferenceService.getConferenceById(Long.valueOf(conferenceId));
    int score = gcpService.detectFace(image.getImageUrl());
    BestPhoto bestPhoto = BestPhoto.createBestPhoto(image, conference, score);
    bestPhotoRepository.save(bestPhoto);
  }

  public List<BestPhoto> getBestPhoto(String conferenceId) {
    List<BestPhoto> bestPhotoList = bestPhotoRepository.findByConferenceId(Long.valueOf(conferenceId))
        .orElseThrow(() -> new NoBestPhotoException());
    return bestPhotoList;
  }
}
