package com.ssafy.ViewCareFull.domain.medicine;

import com.ssafy.ViewCareFull.domain.common.entity.TodayType;
import com.ssafy.ViewCareFull.domain.user.entity.user.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
public class MedicineHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TodayType medicineType;

    @NotNull
    private LocalDate medicineDate;
}
