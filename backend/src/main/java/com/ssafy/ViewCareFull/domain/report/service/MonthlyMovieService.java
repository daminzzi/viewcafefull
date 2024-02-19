package com.ssafy.ViewCareFull.domain.report.service;

import com.ssafy.ViewCareFull.domain.ffmpeg.service.FFmpegService;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.service.GalleryService;
import com.ssafy.ViewCareFull.domain.gallery.utils.ImageUtil;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonthlyMovieService {

  private final GalleryService galleryService;
  private final FFmpegService ffmpegService;

  @Value("${video.outputPath}")
  private String videoOutputPath;

  @Transactional
  public String createMonthlyMovie(SecurityUsers securityUsers, Integer year, Integer month) {
    List<Image> notInMealImage = galleryService.getNotInMealImageWithMonth(year, month, securityUsers.getUser());
    List<String> imagePaths = new ArrayList<>();
    for (Image image : notInMealImage) {
      String imagePath = galleryService.getImagePath(image);
      String newImagePath = videoOutputPath + getNewImageName(image);
      ImageUtil.resizeImage(imagePath, newImagePath);
      List<String> fadePaths = ImageUtil.applyFadeEffect(newImagePath, newImagePath, 25);
      List<String> reversedImagePaths = new ArrayList<>();
      for (int i = fadePaths.size() - 1; i >= 0; i--) {
        reversedImagePaths.add(fadePaths.get(i));
      }
      imagePaths.addAll(fadePaths);
      for (int i = 0; i < 25 * 2; i++) { // 2초간 이미지 반복
        imagePaths.add(newImagePath);
      }
      imagePaths.addAll(reversedImagePaths);
    }
    String videoName = UUID.randomUUID().toString();
    try {
      return ffmpegService.buildCommand(imagePaths, videoName);
    } catch (Exception e) {
      log.error("비디오 생성 실패");
      throw new RuntimeException(e);
    }
  }

  private static String getNewImageName(Image image) {
    int index = image.getImageName().lastIndexOf(".");
    return image.getImageName().substring(0, index) + ".jpg";
  }

}
