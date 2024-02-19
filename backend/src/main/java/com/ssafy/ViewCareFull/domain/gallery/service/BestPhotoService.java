package com.ssafy.ViewCareFull.domain.gallery.service;

import com.ssafy.ViewCareFull.domain.conference.entity.Conference;
import com.ssafy.ViewCareFull.domain.conference.service.ConferenceService;
import com.ssafy.ViewCareFull.domain.gallery.dto.BestPhotoDto;
import com.ssafy.ViewCareFull.domain.gallery.dto.ConferenceBestPhotoResponse;
import com.ssafy.ViewCareFull.domain.gallery.entity.BestPhoto;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.repository.BestPhotoRepository;
import com.ssafy.ViewCareFull.domain.gcp.service.GcpService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BestPhotoService {

  private final BestPhotoRepository bestPhotoRepository;
  private final GalleryService galleryService;
  private final ConferenceService conferenceService;
  private final GcpService gcpService;
  @Value("${file.server.url}")
  private String fileServerUrl;

  @Transactional
  public void writeBestPhoto(SecurityUsers securityUsers, Map<String, Object> params, String sessionId)
      throws IOException {
    if (params == null || !params.containsKey("base64Data")) {
      throw new IllegalArgumentException("base64Data parameter is required");
    }
    Conference conference = conferenceService.getConferenceByRoomName(sessionId);
    String conferenceId = String.valueOf(conference.getId());
    String base64Image = params.get("base64Data").toString();
    MultipartFile imageFile = convertBase64ToMultipartFile(base64Image, conferenceId);
    Image image = galleryService.saveImage(securityUsers, imageFile);
    int score = gcpService.detectFace(fileServerUrl + image.getImageName());
    bestPhotoRepository.save(new BestPhoto(image, conference, score));
  }

  public ConferenceBestPhotoResponse getBestPhoto(String conferenceId) {
    List<BestPhotoDto> bestPhotoList = bestPhotoRepository.findByConferenceId(Long.valueOf(conferenceId))
        .stream()
        .map(this::convertBestPhotoDto)
        .toList();
    Conference conference = conferenceService.getConferenceById(Long.valueOf(conferenceId));
    return new ConferenceBestPhotoResponse(conferenceId, conference.getCaregiver().getId().toString(), bestPhotoList);
  }

  private BestPhotoDto convertBestPhotoDto(BestPhoto bestPhoto) {
    Long imageId = bestPhoto.getImage().getId();
    String imageUrl = galleryService.getImageUrl(imageId);
    return BestPhotoDto.builder()
        .imageId(imageId.toString())
        .score(bestPhoto.getScore())
        .imageUrl(imageUrl)
        .build();
  }

  public MultipartFile convertBase64ToMultipartFile(String base64String, String conferenceId) throws IOException {
    // Base64 문자열을 바이트 배열로 디코딩
    byte[] decodedBytes = Base64.getDecoder().decode(base64String);

    // 파일 이름으로 conferenceId와 현재 시간을 사용
    LocalDateTime currentTime = LocalDateTime.now(); // 현재 시간을 가져옴
    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // 날짜와 시간을 ISO 8601 형식으로 포맷
    String formattedDateTime = currentTime.format(formatter); // 현재 시간을 포맷에 맞게 변환

    // 임시 파일 객체 생성
    File tempFile = File.createTempFile(conferenceId + formattedDateTime, ".jpg");
    // 임시 파일 저장
    FileUtils.writeByteArrayToFile(tempFile, decodedBytes);

    // MultipartFile로 변환
    // 파라미터 이름
    // 파일명
    // 파일 타입 - null 지정 시 자동 추론 - 필요한 경우 image/jpeg, image/png 등으로 지정
    // 파일 데이터 - 임시 파일 읽어와서 활용
    MultipartFile multipartFile = new MockMultipartFile("file", tempFile.getName(), "image/jpeg",
        FileUtils.readFileToByteArray(tempFile));
    // 임시 파일 삭제
    if (tempFile.exists()) {
      tempFile.delete();
    }
    return multipartFile;
  }

  @Transactional
  public void deleteNonBestPhoto(Long conferenceId) {
    List<BestPhoto> bestPhotoList = bestPhotoRepository.findByConferenceId(conferenceId);
    int size = bestPhotoList.size();
    for (int i = 0; i < size; i++) {
      BestPhoto bestPhoto = bestPhotoList.get(i);
      Image image = bestPhoto.getImage();
      if (i > 2 || bestPhoto.getScore() < 60) {
        log.info("delete non best photo image id : " + image.getId());
        bestPhotoRepository.deleteById(bestPhoto.getId());
        galleryService.deleteImage(image);
      }
    }
  }
}
