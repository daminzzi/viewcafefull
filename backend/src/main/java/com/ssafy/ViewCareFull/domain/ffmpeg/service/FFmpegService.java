package com.ssafy.ViewCareFull.domain.ffmpeg.service;


import com.ssafy.ViewCareFull.domain.ffmpeg.exception.VideoCreateFailException;
import com.ssafy.ViewCareFull.domain.gallery.service.GalleryService;
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

  private final GalleryService galleryService;
  // FFmpeg 실행 파일 경로
  @Value("${ffmpeg.path}")
  private String ffmpegPath;
  // 비디오 파일이 저장될 경로
  @Value("${video.outputPath}")
  private String videoOutputPath;
  // 오디오 파일이 저장된 경로
  @Value("${audio.inputPath}")
  private String audioInputPath;

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
      return outputName + ".mp4";
    } else {
      log.info("Exited with error code: " + exitCode);
      throw new VideoCreateFailException();
    }
  }

  public String buildCommand(List<String> imageUrls, String videoName) throws IOException, InterruptedException {
    // 이미지를 비디오로 변환하는 FFmpeg 명령어 생성
    File fileList = new File("filelist.txt");
    try (PrintWriter out = new PrintWriter(new FileWriter(fileList))) {
      for (int i = 0; i < 25; i++) {
        out.println("file '" + videoOutputPath + "logo.jpg'");
      }
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
        videoOutputPath + videoName + ".mp4");

    log.info("FFmpeg command: " + ffmpegCommand);
    String result = executeCommand(ffmpegCommand, videoName);

    // remove imageUrls and filelist.txt
    for (String imageUrl : imageUrls) {
      File file = new File(imageUrl);
      file.delete();
    }
    log.info("server video url : " + result);
    return result;
  }
}
