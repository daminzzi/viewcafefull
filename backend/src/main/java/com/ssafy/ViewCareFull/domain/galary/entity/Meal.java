package com.ssafy.ViewCareFull.domain.galary.entity;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import jakarta.persistence.*;
import lombok.Getter;


import java.time.LocalDate;

@Entity
@Getter
public class Meal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private TodayType mealType;

    @Column
    private LocalDate mealDate;

}
