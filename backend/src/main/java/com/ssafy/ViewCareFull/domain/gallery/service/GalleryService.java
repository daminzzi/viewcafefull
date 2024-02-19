package com.ssafy.ViewCareFull.domain.gallery.service;

import com.ssafy.ViewCareFull.domain.gallery.dto.GalleryResponseDto;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.exception.FileSaveFailException;
import com.ssafy.ViewCareFull.domain.gallery.exception.NoMatchCaregiverException;
import com.ssafy.ViewCareFull.domain.gallery.repository.ImageRepository;
import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.entity.user.Users;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GalleryService {

  @Value("${file.path}")
  private String fileRealPath;
  private final ImageRepository imageRepository;
  private final UserLinkService userLinkService;
  @Value("${file.server.url}")
  private String fileServerUrl;

  @Transactional
  public Image saveImage(SecurityUsers securityUsers, MultipartFile image) {
    Users user = securityUsers.getUser();
    System.out.println("111111111111111111111111111111111");
    CaregiverIdDto caregiverIdDto = userLinkService.getCareGiverIdFromOtherUser(user.getId())
        .orElseThrow(NoMatchCaregiverException::new);
    System.out.println("222222222222222222222222222222222");
    String ext = getImageExtension(image);
    String fileName = UUID.randomUUID().toString() + ext;
    String saveLocation = fileRealPath + fileName;
    Image savedImage = imageRepository.save(new Image(saveLocation, fileName, caregiverIdDto));
    saveImageToDisk(image, saveLocation);
    return savedImage;
  }

  public List<Image> getNotInMealImageWithMonth(Integer year, Integer month, Users user) {
    return imageRepository.findAllNotInMealWithMonthAndUser(year, month, user.getId());
  }

  private void saveImageToDisk(MultipartFile image, String saveLocation) {
    try {
      image.transferTo(new File(saveLocation));
    } catch (IOException e) {
      log.error("saveImage: {}", e.getMessage());
      throw new FileSaveFailException();
    }
  }

  private String getImageExtension(MultipartFile image) {
    String ext = ".jpg";
    if (image.getOriginalFilename().lastIndexOf(".") != -1) {
      ext = image.getOriginalFilename().substring(image.getOriginalFilename().lastIndexOf("."));
    }
    return ext;
  }

  public GalleryResponseDto getGallery(SecurityUsers securityUsers, Pageable pageable) {
    Users user = securityUsers.getUser();
    CaregiverIdDto caregiverIdDto = userLinkService.getCareGiverIdFromOtherUser(user.getId())
        .orElseThrow(NoMatchCaregiverException::new);
    Page<Image> page = imageRepository.findAllByCaregiverId(caregiverIdDto.getCaregiverId(), pageable);
    return new GalleryResponseDto(page, fileServerUrl);
  }

  public Image getImage(Long imageId) {
    return imageRepository.findById(imageId).orElseThrow();
  }

  public String getImageUrl(Long imageId) {
    return fileServerUrl + imageRepository.findById(imageId).orElseThrow().getImageName();
  }

  public String getImageUrl(Image image) {
    return fileServerUrl + image.getImageName();
  }

  public String getImagePath(Long imageId) {
    return fileRealPath + imageRepository.findById(imageId).orElseThrow().getImageName();
  }

  public String getImagePath(Image image) {
    return fileRealPath + image.getImageName();
  }

  @Transactional
  public void deleteImage(Image image) {
    imageRepository.deleteById(image.getId());
  }

  public List<Image> getBestPhotoImageByCaregiverIdBetweenDate(Long caregiverId, LocalDateTime startDate,
      LocalDateTime endDate) {
    return imageRepository.findBestPhotoImageByCaregiverIdBetweenDate(caregiverId, startDate, endDate);
  }

  public List<Image> getNoneMealImageByCaregiverIdBetweenDate(Long caregiverId, LocalDateTime startDate,
      LocalDateTime endDate) {
    return imageRepository.findNoneMealImageByCaregiverIdBetweenDate(caregiverId, startDate, endDate);
  }
}
