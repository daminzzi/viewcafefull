package com.ssafy.ViewCareFull.domain.ffmpeg.service;


import com.ssafy.ViewCareFull.domain.ffmpeg.exception.VideoCreateFailException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    File fileList = new File("filelist.txt");
    try (PrintWriter out = new PrintWriter(new FileWriter(fileList))) {
      for (String imageUrl : imageUrls) {
        out.println("file '" + imageUrl + "'");
      }
    }

    int framerate = 30; // 초당 프레임 수 설정
    String audioStartSec = "30"; // 오디오 시작 시간 설정

    // FFmpeg 명령어 생성
    String ffmpegCommand = String.format(
        "%s -f concat -safe 0 -i %s -i %s -framerate %d -filter_complex \"[0:v]scale=1920:1080,setsar=1[v]; [1:a]atrim=start=%s,asetpts=PTS-STARTPTS[a]\" -map \"[v]\" -map \"[a]\" -y %s",
        ffmpegPath, fileList.getAbsolutePath(), audioInputPath + "audio.mp3", framerate, audioStartSec,
        videoOutputPath + "video.mp4");

    log.info("FFmpeg command: " + ffmpegCommand);
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
    log.info("Video create success");
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
