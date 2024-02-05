package com.ssafy.ViewCareFull.domain.gallery.controller;

import com.ssafy.ViewCareFull.domain.gallery.entity.BestPhoto;
import com.ssafy.ViewCareFull.domain.gallery.service.BestPhotoService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/bestphoto")
@RequiredArgsConstructor
public class BestPhotoController {

  private final BestPhotoService bestPhotoService;

  @PostMapping("/{conferenceId}")
  public ResponseEntity<String> writeBestPhoto(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestPart("image") MultipartFile image,
      @PathVariable("conferenceId") String conferenceId) throws IOException {
    bestPhotoService.writeBestPhoto(securityUsers, image, conferenceId);
    return new ResponseEntity<>("create success", HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<BestPhoto>> getBestPhoto(
      @RequestParam("conferenceId") String coneferenceId) {
    return new ResponseEntity<>(bestPhotoService.getBestPhoto(coneferenceId), HttpStatus.OK);
  }
}
