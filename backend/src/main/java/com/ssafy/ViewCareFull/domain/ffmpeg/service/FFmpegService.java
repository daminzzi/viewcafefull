package com.ssafy.ViewCareFull.domain.ffmpeg.service;


import com.ssafy.ViewCareFull.domain.ffmpeg.exception.VideoCreateFailException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

  // FFmpeg 실행 파일 경로
  @Value("${ffmpeg.path}")
  private String ffmpegPath;
  // 비디오 파일이 저장될 경로
  @Value("${video.outputPath}")
  private String videoOutputPath;
  // 오디오 파일이 저장된 경로
  @Value("${audio.inputPath}")
  private String audioInputPath;

  public void buildCommand(List<String> imageUrls) throws IOException, InterruptedException {
    // 이미지를 비디오로 변환하는 FFmpeg 명령어 생성
    StringBuilder ffmpegCommandBuilder = new StringBuilder();
    int framrate = 30; // 초당 프레임 수 설정
    int imageSec = 2; // 이미지가 보여지는 시간 설정
    String audioStartSec = "30"; // 오디오 시작 시간 설정
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
        .append(":end=" + (2 * imageUrls.size() + audioStartSec)) // 오디오 종료 시간 설정
        .append(",aloop=loop=1") // 오디오 반복 설정
        .append(":size=1") // 오디오 반복 횟수 설정
        .append(":start=0") // 오디오 처음부터 시작
        .append(",asetpts=PTS-STARTPTS[a_trimmed]; ") // 오디오 타임 스탬프 조정
        .append("[a_trimmed]adelay=0|0[a]\"") // 오디오 딜레이 설정
        .append(" -map \"[v]\"") // 이미지 결합 사용 설정
        .append(" -map \"[a]\"") // 오디오 결합 사용 설정
        .append(" -y ") // 저장 시 자동 덮어쓰기 설정
        .append(videoOutputPath + "video.mp4"); // 저장 경로 설정

    String ffmpegCommand = ffmpegCommandBuilder.toString(); // FFmpeg 명령어 완성

    log.info("FFmpeg command: " + ffmpegCommand); // 생성된 명령어 로그 출력
    executeCommand(ffmpegCommand); // 명령어를 시스템 커맨드로 실행
  }

  private void executeCommand(String command) throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(command); // 프로세스 빌더 인스턴스 생성
    String os = System.getProperty("os.name").toLowerCase();
    if (os.contains("win")) {
      processBuilder.command("cmd.exe", "/c", command);
    } else {
      processBuilder.command("bash", "-c", command);
    }
    Process process = processBuilder.start(); // 프로세스 실행

    // 명령어 실행 결과를 읽어오기 위한 BufferedReader 인스턴스 생성
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      log.info(line);
    }
    BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
    String errorLine;
    while ((errorLine = errorReader.readLine()) != null) {
      log.error(errorLine);
    }
    // 프로세스의 종료를 대기하고 종료 코드 출력
    int exitCode = process.waitFor();
    if (exitCode == 0) {
      log.info("Exited with create video success code: 0");
    } else {
      log.info("Exited with error code: " + exitCode);
      throw new VideoCreateFailException();
    }
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
          "C:\\Users\\Keesung\\Downloads\\test\\test_resized_0.jpg")
      );
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
