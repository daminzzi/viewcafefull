package com.ssafy.ViewCareFull.domain.gallery.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.ViewCareFull.DatabaseCleanup;
import com.ssafy.ViewCareFull.domain.helper.UserRegisterHelper;
import com.ssafy.ViewCareFull.domain.users.security.jwt.JwtAuthenticationFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@DisplayName("갤러리 컨트롤러 Test")
@ActiveProfiles("test")
class GalleryControllerTest {

  private MockMvc mockMvc;
  @Autowired
  private DatabaseCleanup databaseCleanup;
  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;
  @Autowired
  private UserRegisterHelper userRegisterHelper;


  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    databaseCleanup.execute();
    userRegisterHelper.execute(context);
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .addFilter(jwtAuthenticationFilter)
        .build();
  }

//  @Nested
//  @DisplayName("갤러리 저장 테스트")
//  class SaveImageTest {
//
//    @Test
//    @DisplayName("[성공] 갤러리 저장 테스트")
//    void saveImage() throws Exception {
//      String accessToken = userRegisterHelper.getCaregiverAccessToken();
//      MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test".getBytes());
//      MockMultipartFile meta = new MockMultipartFile("meta", "meta.json", "application/json",
//          "{\"test\":\"test\"}".getBytes());
////      mockMvc.perform(RestDocumentationRequestBuilders.multipart("/gallery")
//      mockMvc.perform(RestDocumentationRequestBuilders.multipart("/gallery")
//              .file(image)
//              .file(meta)
//              .header("Authorization", accessToken)
//              .header("Content-Type", "multipart/form-data"))
//          .andExpect(status().isCreated())
//          .andDo(document(
//              "갤러리 저장",
//              RequestDocumentation.relaxedRequestParts(
//                  RequestDocumentation.partWithName("image").description("이미지 파일"),
//                  RequestDocumentation.partWithName("meta").description("메타 정보")
//              )
//          ));
//    }
//  }

  @Nested
  @DisplayName("갤러리 조회 테스트")
  class GetImageTest {

    @Test
    @DisplayName("[성공] 갤러리 조회 테스트")
    void getImage() throws Exception {
      String accessToken = userRegisterHelper.getCaregiverAccessToken();
      mockMvc.perform(RestDocumentationRequestBuilders.get("/gallery")
              .queryParam("page", "1")
              .header("Authorization", accessToken))
          .andExpect(status().isOk())
          .andDo(document(
              "갤러리 조회",
              RequestDocumentation.queryParameters(
                  RequestDocumentation.parameterWithName("page").description("페이지 번호")
              )
          ));
    }
  }


}