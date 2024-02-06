package com.ssafy.ViewCareFull.domain.ffmpeg.service;


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

  public void buildCommand(List<String> imageUrls) throws IOException, InterruptedException {
    // 이미지를 비디오로 변환하는 FFmpeg 명령어 생성
    StringBuilder ffmpegCommandBuilder = new StringBuilder();
    ffmpegCommandBuilder.append(
            ffmpegPath) // FFmpeg 실행 파일 경로
        .append(" -framerate 30"); // 초당 프레임 수 설정
    for (String imageUrl : imageUrls) {
      ffmpegCommandBuilder.append(" -i ").append(imageUrl); // 각각의 이미지 사용 설정
    }
    ffmpegCommandBuilder.append(" -filter_complex \""); // 여러개의 이미지를 하나로 결합하는 필터 사용
    for (int i = 0; i < imageUrls.size(); i++) {
      ffmpegCommandBuilder.append("[").append(i).append(":v]"); // index 번째 이미지 필터 설정
      ffmpegCommandBuilder.append("loop=60"); // loop/framerate 초 만큼 각 이미지가 재생된다
      ffmpegCommandBuilder.append(":size=1"); // 반복할 프레임 수 설정 - 1프레임만 반복
      ffmpegCommandBuilder.append(":start=0"); // 반복 시작 시점 설정 - 0초부터 시작
      ffmpegCommandBuilder.append(",scale=1024:1024"); // 이미지 크기 설정 - 반드시 짝수로 설정해야함
      ffmpegCommandBuilder.append(",setsar=1"); // 픽셀 샘플링 화면 비율 설정 - 1:1로 설정
      ffmpegCommandBuilder.append("[v").append(i).append("]; "); // index 번째 이미지 필터 설정
    }
    for (int i = 0; i < imageUrls.size(); i++) {
      ffmpegCommandBuilder.append("[v").append(i).append("]"); // 사용할 이미지 필터 명시
    }
    ffmpegCommandBuilder.append("concat=n=") // 이미지 필터 결합
        .append(imageUrls.size()) // 이미지 개수 설정
        .append(":v=1") // 비디오로 출력 설정
        .append(":a=0") // 오디오로 출력 미설정
        .append("[out]\"") // 이미지 결합 설정
        .append(" -map \"[out]\"") // 이미지 결합 사용 설정
        .append(" -y ") // 저장 시 자동 덮어쓰기 설정
        .append(videoOutputPath); // 저장 경로 설정

    String ffmpegCommand = ffmpegCommandBuilder.toString(); // FFmpeg 명령어 완성

    executeCommand(ffmpegCommand); // 명령어를 시스템 커맨드로 실행
  }

  private void executeCommand(String command) throws IOException, InterruptedException {
    ProcessBuilder processBuilder = new ProcessBuilder(); // 프로세스 빌더 인스턴스 생성
    processBuilder.command("bash", "-c", command); // 명령어 실행을 위한 명령어 및 인자 전달
    Process process = processBuilder.start(); // 프로세스 실행

    // 명령어 실행 결과를 읽어오기 위한 BufferedReader 인스턴스 생성
    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
    String line;
    while ((line = reader.readLine()) != null) {
      log.info(line);
    }

    // 프로세스의 종료를 대기하고 종료 코드 출력
    int exitCode = process.waitFor();
    log.info("Exited with error code: " + exitCode);
  }
}
