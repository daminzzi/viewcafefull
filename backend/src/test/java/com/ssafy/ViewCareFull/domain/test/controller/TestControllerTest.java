package com.ssafy.ViewCareFull.domain.test.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.ViewCareFull.domain.test.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@DisplayName("TestController 테스트")
class TestControllerTest {

  private MockMvc mockMvc;

  @Autowired
  private TestService testService;


  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @DisplayName("[성공] test - null")
  void test() throws Exception {
    mockMvc.perform(RestDocumentationRequestBuilders.get("/swagger/test"))
        .andExpect(status().isBadRequest())
        .andExpect(content().string("null"))
        .andDo(document("test"));

  }

  @Test
  @DisplayName("[성공] test - email")
  void test2() throws Exception {
    String text = "ssafy@ssafy.com";
    mockMvc.perform(RestDocumentationRequestBuilders.get("/swagger/test")
            .queryParam("text", text))
        .andExpect(status().isOk())
        .andExpect(content().string("email"))
        .andDo(document("email"));
  }

  @Test
  @DisplayName("[성공] test - notEmail")
  void test3() throws Exception {
    String text = "notEmail";
    mockMvc.perform(RestDocumentationRequestBuilders.get("/swagger/test")
            .queryParam("text", text))
        .andExpect(status().isNotFound())
        .andExpect(content().string("notEmail"))
        .andDo(document("notEmail"));
  }

}