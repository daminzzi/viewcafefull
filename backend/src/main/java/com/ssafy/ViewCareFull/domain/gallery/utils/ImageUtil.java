package com.ssafy.ViewCareFull.domain.gallery.utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
public class ImageUtil {

  public static List<String> applyFadeEffect(String inputImagePath, String outputImagePath, int steps) {
    List<String> imagePaths = new ArrayList<>();
    try {
      File inputFile = new File(inputImagePath);
      BufferedImage originalImage = ImageIO.read(inputFile);

      int width = originalImage.getWidth();
      int height = originalImage.getHeight();

      for (int i = 0; i < steps; i++) {
        BufferedImage fadedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = fadedImage.createGraphics();

        // 원본 이미지 복사
        g2d.drawImage(originalImage, 0, 0, null);

        // 투명도 설정
        int alpha = (int) ((1 - (double) i / steps) * 255);
        Color fadeColor = new Color(0, 0, 0, alpha);

        // 이미지에 fade 효과 적용
        g2d.setColor(fadeColor);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();

        // 변형된 이미지 저장
        File outputFile = new File(outputImagePath.replace(".png", "_" + i + ".png"));
        imagePaths.add(outputFile.getAbsolutePath());
        ImageIO.write(fadedImage, "PNG", outputFile);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return imagePaths;
  }

  public static void resizeImage(String inputImagePath, String outputImagePath) {
    // 원하는 출력 비율
    int newWidth = 1920; // 예시 너비
    int newHeight = 1080; // 예시 높이 (16:9 비율에 맞춤)

    try {
      Thumbnails.of(new File(inputImagePath))
          .size(newWidth, newHeight)
          .outputFormat("png")
          .toFile(new File(outputImagePath));
    } catch (IOException e) {
      log.error("이미지 리사이징 실패", e);
      throw new RuntimeException("이미지 리사이징 실패");
    }
  }

}
