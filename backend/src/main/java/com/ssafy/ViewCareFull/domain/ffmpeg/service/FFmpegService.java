package com.ssafy.ViewCareFull.domain.ffmpeg.service;


import com.ssafy.ViewCareFull.domain.ffmpeg.exception.VideoCreateFailException;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.service.GalleryService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FFmpegService {

  private final GalleryService galleryService;
  // FFmpeg 실행 파일 경로
  @Value("${ffmpeg.path}")
  private String ffmpegPath;
  // 비디오 파일이 저장될 경로
  @Value("${video.outputPath}")
  private String videoOutputPath;
  // 오디오 파일이 저장된 경로
  @Value("${image.inputPath}")
  private String imageInputPath;
  // 오디오 파일이 저장된 경로
  @Value("${audio.inputPath}")
  private String audioInputPath;

  public String createImageToVideo(Long caregiverId, LocalDateTime start, LocalDateTime end)
      throws IOException, InterruptedException {
    List<String> imageUrlList = new ArrayList<>();
    List<Image> bestPhotoImageList = galleryService.getBestPhotoImageByCaregiverIdBetweenDate(caregiverId, start, end);
    for (Image image : bestPhotoImageList) {
      imageUrlList.add(imageInputPath + image.getImageName());
    }
    List<Image> noneMealImageList = galleryService.getNoneMealImageByCaregiverIdBetweenDate(caregiverId, start, end);
    for (Image image : noneMealImageList) {
      imageUrlList.add(imageInputPath + image.getImageName());
    }
    return buildCommand(imageUrlList, caregiverId + "_" + start + "_" + end);
  }

  public String buildCommand(List<String> imageUrls, String outputName) throws IOException, InterruptedException {
    // 이미지를 비디오로 변환하는 FFmpeg 명령어 생성
    StringBuilder ffmpegCommandBuilder = new StringBuilder();
    int framrate = 25; // 초당 프레임 수 설정 - 현재 25fps로만 동작함 원인 불명
    int imageSec = 4; // 이미지가 보여지는 시간 설정
    int audioStartSec = 0; // 오디오 시작 시간 설정
    log.info("FFmpeg path: " + ffmpegPath);
    ffmpegCommandBuilder
        .append(ffmpegPath)
        .append(" -framerate " + framrate); // 초당 프레임 수 설정
    for (String imageUrl : imageUrls) {
      ffmpegCommandBuilder.append(" -i ").append(imageUrl); // 각각의 이미지 사용 설정
    }
    ffmpegCommandBuilder.append(" -i " + audioInputPath + "audio.mp3") // 오디오 사용 설정
        .append(" -filter_complex \""); // 여러개의 이미지를 하나로 결합하는 필터 사용
    for (int i = 0; i < imageUrls.size(); i++) {
      StringBuilder append = ffmpegCommandBuilder.append("[").append(i).append(":v]") // index 번째 이미지 필터 설정
          .append("loop=" + imageSec * framrate) // loop/framerate 초 만큼 각 이미지가 재생된다
          .append(":size=1") // 반복할 프레임 수 설정 - 1프레임만 반복
          .append(":start=0") // 반복 시작 시점 설정 - 0초부터 시작
          .append(",scale=1920:1080")// 이미지 크기 설정 - 1920x1080으로 설정 (반드시 짝수로)
          .append(",setsar=1") // 화면 비율 설정 - 1:1로 설정
          .append("[v").append(i).append("]; "); // index 번째 이미지 필터 설정
    }
    for (int i = 0; i < imageUrls.size(); i++) {
      ffmpegCommandBuilder.append("[v").append(i).append("]"); // 사용할 이미지 필터 명시
    }
    ffmpegCommandBuilder.append("concat=n=") // 이미지 필터 결합
        .append(imageUrls.size()) // 이미지 개수 설정
        .append(":v=1") // 비디오로 출력 설정
        .append(":a=0") // 오디오로 출력 미설정
        .append("[v]; ") // 이미지 결합 설정
        .append("[" + imageUrls.size() + ":a]") // 오디오 사용 설정
        .append("atrim=start=" + audioStartSec) // 오디오 시작 시간 설정
        .append(":end=" + (imageSec * imageUrls.size() + audioStartSec)) // 오디오 종료 시간 설정
        .append(",aloop=loop=1") // 오디오 반복 설정
        .append(":size=1") // 오디오 반복 횟수 설정
        .append(":start=0") // 오디오 처음부터 시작
        .append(",asetpts=PTS-STARTPTS[a_trimmed]; ") // 오디오 타임 스탬프 조정
        .append("[a_trimmed]adelay=0|0[a]\"") // 오디오 딜레이 설정
        .append(" -map \"[v]\"") // 이미지 결합 사용 설정
        .append(" -map \"[a]\"") // 오디오 결합 사용 설정
        .append(" -y ") // 저장 시 자동 덮어쓰기 설정
        .append(videoOutputPath + outputName + ".mp4"); // 저장 경로 설정

    String ffmpegCommand = ffmpegCommandBuilder.toString(); // FFmpeg 명령어 완성

    log.info("FFmpeg command: " + ffmpegCommand); // 생성된 명령어 로그 출력
    return executeCommand(ffmpegCommand, outputName); // 명령어를 시스템 커맨드로 실행
  }

  private String executeCommand(String command, String outputName) throws InterruptedException, IOException {
    String[] commands = new String[]{"bash", "-c", command};
    ProcessBuilder processBuilder = new ProcessBuilder(commands);
    log.info("processBuilderCommand : " + processBuilder.command().toString());
    Process process = processBuilder.start();

    String line;

    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    while (errorReader.ready() && (line = errorReader.readLine()) != null) {
      log.error(line);
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    while (reader.ready() && (line = reader.readLine()) != null) {
      log.info(line);
    }

    int exitCode = process.waitFor();
    if (exitCode == 0) {
      log.info("Exited with create video success code: 0");
      return videoOutputPath + outputName + ".mp4";
    } else {
      log.info("Exited with error code: " + exitCode);
      throw new VideoCreateFailException();
    }
  }

  public void buildCommand(List<String> imageUrls) throws IOException, InterruptedException {
    // 이미지를 비디오로 변환하는 FFmpeg 명령어 생성
    File fileList = new File("filelist.txt");
    try (PrintWriter out = new PrintWriter(new FileWriter(fileList))) {
      for (String imageUrl : imageUrls) {
        out.println("file '" + imageUrl + "'");
      }
    }

    int framerate = 25; // 초당 프레임 수 설정
    String audioStartSec = "0"; // 오디오 시작 시간 설정

    // FFmpeg 명령어 생성
    String ffmpegCommand = String.format(
        "%s -f concat -safe 0 -i %s -i %s -vf \"fps=%d,scale=480:270\" -c:v libx264 -pix_fmt yuv420p -r %d -c:a aac -shortest -y %s",
        ffmpegPath, fileList.getAbsolutePath(), audioInputPath + "audio.mp3", framerate, framerate,
        videoOutputPath + "video.mp4");

    log.info("FFmpeg command: " + ffmpegCommand);
    executeCommand(ffmpegCommand, "video"); // 명령어를 시스템 커맨드로 실행
  }

  public void onlyTest() {
    try {
      buildCommand(List.of(
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_0.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_10.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_20.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_20.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_10.jpg",
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_0.jpg"), "videoTest"
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
