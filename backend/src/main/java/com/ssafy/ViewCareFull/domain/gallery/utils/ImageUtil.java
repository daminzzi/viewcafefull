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

  public static void main(String[] args) {
    String inputImagePath = "C:\\Users\\Keesung\\Desktop\\test\\test.jpg";
    String outputImagePath = "C:\\Users\\Keesung\\Desktop\\test\\test_resized.jpg";
    resizeImage(inputImagePath, outputImagePath);
    applyFadeEffect(inputImagePath, outputImagePath, 25);
  }

  public static List<String> applyFadeEffect(String inputImagePath, String outputImagePath, int steps) {
    List<String> imagePaths = new ArrayList<>();
    try {
      File inputFile = new File(inputImagePath);
      BufferedImage originalImage = ImageIO.read(inputFile);

      int width = originalImage.getWidth();
      int height = originalImage.getHeight();

      for (int i = 0; i < steps; i++) {
        BufferedImage fadedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = fadedImage.createGraphics();

        // 원본 이미지 복사
        g2d.drawImage(originalImage, 0, 0, null);

        // 페이드 효과 적용을 위한 검은색 테두리 두께 계산
        int borderThickness = (int) ((double) (steps - i) / steps * Math.min(width, height) / 2);

        // 검은색 테두리 적용
        g2d.setColor(Color.BLACK);
        // 상단 테두리
        g2d.fillRect(0, 0, width, borderThickness);
        // 하단 테두리
        g2d.fillRect(0, height - borderThickness, width, borderThickness);
        // 왼쪽 테두리
        g2d.fillRect(0, borderThickness, borderThickness, height - 2 * borderThickness);
        // 오른쪽 테두리
        g2d.fillRect(width - borderThickness, borderThickness, borderThickness, height - 2 * borderThickness);

        g2d.dispose();

        // 변형된 이미지 저장
        File outputFile = new File(outputImagePath.replace(".jpg", "_" + i + ".jpg"));
        imagePaths.add(outputFile.getAbsolutePath());
        ImageIO.write(fadedImage, "jpg", outputFile);
      }
    } catch (IOException e) {
      e.printStackTrace();
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
          .outputFormat("jpg")
          .toFile(new File(outputImagePath));
    } catch (IOException e) {
      log.error("이미지 리사이징 실패", e);
      throw new RuntimeException("이미지 리사이징 실패");
    }
  }

}
