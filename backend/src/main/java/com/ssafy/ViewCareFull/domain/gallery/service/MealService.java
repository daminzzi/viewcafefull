package com.ssafy.ViewCareFull.domain.gallery.service;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.gallery.dto.DayMealListResponse;
import com.ssafy.ViewCareFull.domain.gallery.dto.MealImageDto;
import com.ssafy.ViewCareFull.domain.gallery.entity.Image;
import com.ssafy.ViewCareFull.domain.gallery.entity.Meal;
import com.ssafy.ViewCareFull.domain.gallery.repository.MealRepository;
import com.ssafy.ViewCareFull.domain.users.dto.CaregiverIdDto;
import com.ssafy.ViewCareFull.domain.users.security.SecurityUsers;
import com.ssafy.ViewCareFull.domain.users.service.UserLinkService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealService {

  private final MealRepository mealRepository;
  private final GalleryService galleryService;
  private final UserLinkService userLinkService;

  @Transactional
  public void createMeal(SecurityUsers securityUsers, LocalDate day, String time, MultipartFile image) {
    Image savedImage = galleryService.saveImage(securityUsers, image);
    Meal meal = new Meal(securityUsers.getUser(), day, TodayType.findByKorean(time), savedImage);
    mealRepository.save(meal);
  }

  public DayMealListResponse getDayMealList(SecurityUsers securityUsers, LocalDate day) {
    CaregiverIdDto caregiverIdDto = userLinkService.getCareGiverIdFromOtherUser(securityUsers.getUser().getId())
        .orElseThrow();
    List<MealImageDto> mealList = mealRepository.findByCaregiverAndDay(caregiverIdDto.getCaregiverId(), day).stream()
        .map(this::convertMealToMealImageDto)
        .toList();
    return new DayMealListResponse(day, mealList);
  }

  private MealImageDto convertMealToMealImageDto(Meal meal) {
    String imageUrl = galleryService.getImageUrl(meal.getImage().getId());
    String time = meal.getMealType().getKorean();
    return MealImageDto.builder()
        .url(imageUrl)
        .time(time)
        .build();
  }

  public List<Meal> getMealListWithIdDate(String domainId, LocalDate date) {
    return mealRepository.findByDomainIdAndDate(domainId, date);
  }
}
