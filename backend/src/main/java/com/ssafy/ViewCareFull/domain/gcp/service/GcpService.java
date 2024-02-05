package com.ssafy.ViewCareFull.domain.gcp.service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.ssafy.ViewCareFull.domain.gcp.error.GcpErrorCode;
import com.ssafy.ViewCareFull.domain.gcp.error.exception.GcpException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// @Transactional(readOnly = true)
public class GcpService {

  public int detectFace(String imgPath) throws IOException {
    // 파라미터로 받은 이미지 경로를 이용해 이미지 소스를 생성
    ImageSource imgSource = ImageSource.newBuilder().setImageUri(imgPath).build();
    // 이미지 소스를 이용해 이미지를 생성
    Image img = Image.newBuilder().setSource(imgSource).build();
    // 얼굴 인식 기능을 사용하기 위한 feature 생성
    Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();

    // 이미지와 feature를 이용해 얼굴 인식 요청 생성 - 이미지URL과 type이 담긴다. type은 기본적으로 Face Detecting(얼굴 인식)으로 설정된다
    AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
    // 얼굴 인식 요청을 위한 리스트
    List<AnnotateImageRequest> requests = new ArrayList<>();
    // 얼굴 인식 요청을 요청리스트에 담는다.
    // TODO:단일 요청,응답일 경우 요청,응답 리스트를 사용하지 않는 방법에 대해 공부 필요
    requests.add(request);

    // Google Cloud Vision API를 사용하기 위한 클라이언트 생성
    ImageAnnotatorClient client = ImageAnnotatorClient.create();

    // 분석 응답 정보를 받기 위한 응답 객체 생성
    BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
    // 응답 객체를 담기 위한 리스트 생성
    List<AnnotateImageResponse> responses = response.getResponsesList();
    // 단일 요청이므로 리스트의 첫번째 응답 객체가 해당 요청에 대한 응답임
    AnnotateImageResponse res = responses.get(0);

    // 응답이 비어있는 경우 얼굴인식 실패
    // TODO:저작권 사진 등 특정 사진에서 간혈적으로 발생. 정확하게 분석 실패 상황이 어떤 상황인지는 API 분석 필요
    if (res.toString().isEmpty()) {
      throw new GcpException(GcpErrorCode.FACE_DETECT_FAIL, "response is empty");
    }
    // API 사용에 에러가 발생하면 API가 응답하는 에러 메세지 전달
    if (res.hasError()) {
      throw new GcpException(GcpErrorCode.API_ERROR, res.getError().getMessage());
    }
    // 얼굴 인식이 성공하고 에러가 없는 경우 응답 정보를 점수 계산 메소드로 넘겨서 점수를 계산하고 결과를 리턴
    FaceAnnotation annotation = res.getFaceAnnotationsList().get(0); // 응답 정보의 얼굴 인식 정보 리스트
    int score = calculateScore(annotation); // 점수 계산 메소드로 점수 변환
    return score;
  }

  /*
   * 얼굴 인식 정보를 0~100점 사이의 점수로 변환한다
   * 행복, 슬픔, 분노, 놀람, 가려짐, 흐림, 모자 착용 7가지 항목은 1~5점 으로 표현 된다
   * 분석정확도는 0~1 사이의 소수로 표현된다
   * 각 7가지 항목에 가중치를 곱하고 합산한 뒤 분석 정확도를 곱한다. 이때 슬픔, 분노, 가려짐, 흐림은 마이너스 연산을 한다
   * 분석정확도를 곱한뒤 0~100점 사이 점수로 매핑한다
   */
  private static int calculateScore(FaceAnnotation annotation) {
    // 얼굴 인식 정보
    int joy = annotation.getJoyLikelihoodValue();
    int sorrow = annotation.getSorrowLikelihoodValue();
    int anger = annotation.getAngerLikelihoodValue();
    int surprise = annotation.getSurpriseLikelihoodValue();
    int underExposure = annotation.getUnderExposedLikelihoodValue();
    int blur = annotation.getBlurredLikelihoodValue();
    int hat = annotation.getHeadwearLikelihoodValue();
    double confidence = annotation.getDetectionConfidence();

    // 가중치
    double[] weights = {0.3, 0.2, 0.2, 0.1, 0.1, 0.1, 0};

    // 각 정보에 대한 점수와 가중치를 곱한 후 합산
    double weightedSum = (joy * weights[0] +
        (6 - sorrow) * weights[1] +
        (6 - anger) * weights[2] +
        surprise * weights[3] +
        (6 - underExposure) * weights[4] +
        (6 - blur) * weights[5] +
        hat * weights[6]) * confidence;

    // 최종 점수를 0에서 100 사이로 매핑
    return (int) Math.round(weightedSum * 20);
  }
}
