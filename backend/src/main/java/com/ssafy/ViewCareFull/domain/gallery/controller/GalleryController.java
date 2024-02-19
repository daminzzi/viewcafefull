package com.ssafy.ViewCareFull.domain.gallery.controller;

import com.ssafy.ViewCareFull.domain.gallery.dto.GalleryResponseDto;
import com.ssafy.ViewCareFull.domain.gallery.service.GalleryService;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gallery")
@RequiredArgsConstructor
public class GalleryController {

  private final GalleryService galleryService;

  @PostMapping
  public ResponseEntity<String> saveImage(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestPart("image") MultipartFile image) {
    System.out.println(image.getOriginalFilename() + "======================================================");
    galleryService.saveImage(securityUsers, image);
    return ResponseEntity.created(null).body("success");
  }

  @GetMapping
  public ResponseEntity<GalleryResponseDto> getGallery(
      @AuthenticationPrincipal SecurityUsers securityUsers,
      @RequestParam("page") int page) {
    Pageable pageable = PageRequest.of(page - 1, 10);
    return ResponseEntity.ok(galleryService.getGallery(securityUsers, pageable));
  }

}
