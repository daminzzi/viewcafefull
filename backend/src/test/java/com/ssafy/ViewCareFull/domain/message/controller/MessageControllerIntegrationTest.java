package com.ssafy.ViewCareFull.domain.message.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.ViewCareFull.domain.message.entity.Message;
import com.ssafy.ViewCareFull.domain.message.repository.MessageRepository;
import com.ssafy.ViewCareFull.domain.message.service.MessageService;
import com.ssafy.ViewCareFull.domain.users.entity.user.Guardian;
import com.ssafy.ViewCareFull.domain.users.service.UsersService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MessageControllerIntegrationTest {

  private MockMvc mockMvc;
  @Autowired
  private MessageService messageService;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private UsersService usersService;

  @BeforeEach
  void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
        .build();
  }

  @Test
  @DisplayName("[성공] getMessages")
  void getMessages() throws Exception {
    Guardian guardian = Guardian.builder()
        .domainId("ssafy")
        .build();
    Message message = Message.builder()
        .toId(guardian)
        .content("test")
        .build();
    messageRepository.save(message);
    mockMvc.perform(RestDocumentationRequestBuilders.get("/msg")
            .queryParam("page", "0")
            .queryParam("keyword", "test")
        )
        .andExpect(status().isOk())
        .andDo(document("getMessages"));
  }


}