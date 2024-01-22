import React, { useState, useEffect } from "react";
import { Page } from "../../components/familyhome/TabButton";
import TabButtonGroup from "../../components/familyhome/TabButtonGroup";
import Comment from "../../components/familyhome/Comment";
import TabView from "../../components/familyhome/TabView";
import getHealth from "../../services/health/getHealth";
import noImage from "../../assets/images/noImage.jpg";
import breakfast from "../../assets/images/breakfast.jpg";
import lunch from "../../assets/images/lunch.jpg";
import dinner from "../../assets/images/dinner.jpg";
import { MealObj, MedicineObj, HealthInfo } from "../../pages/family/Types";

function FamilyHome() {
  // const today: Date = new Date();

  // 더미 데이터 날짜로 설정
  const today: Date = new Date("2024-01-15");
  const year: number = today.getFullYear();
  const month: string = (today.getMonth() + 1).toString().padStart(2, "0");
  const day = today.getDate();

  function handleChangeTab(tab: Page): void {
    setTab(tab);
  }
  const [tab, setTab] = useState<Page>("sum");
  const [date, setDate] = useState<string>("");
  const [healthInfo, setHealthInfo] = useState<HealthInfo>({
    beforeArr: [],
    afterArr: [],
    lowArr: [],
    highArr: [],
    medicineObj: null,
    mealObj: null,
  });

  // 온마운트 시 오늘 날짜로 date 변경
  useEffect(() => {
    console.log("onmount");
    setDate(`${year}${month}${day}`);
  }, []);

  // date 변경 시 건강 정보 요청, 변경
  useEffect(() => {
    const health = getHealth("asdfsa", date);
    const before: Array<number> = [...health.before];
    const after: Array<number> = [...health.after];
    const low: Array<number> = [...health.low];
    const high: Array<number> = [...health.high];
    const medicine: MedicineObj = {
      breakfast: null,
      lunch: null,
      dinner: null,
    };
    const meal: MealObj = {
      breakfast: noImage,
      lunch: noImage,
      dinner: noImage,
    };

    health.medicine.forEach((item) => {
      switch (item.medicineType) {
        case "B":
          medicine.breakfast = item;
          break;
        case "L":
          medicine.lunch = item;
          break;
        case "D":
          medicine.dinner = item;
          break;
      }
    });

    health.meal.forEach((item) => {
      switch (item.type) {
        case "B":
          meal.breakfast = breakfast;
          break;
        case "L":
          meal.lunch = lunch;
          break;
        case "D":
          meal.dinner = dinner;
          break;
      }
    });

    setHealthInfo({
      beforeArr: before,
      afterArr: after,
      lowArr: low,
      highArr: high,
      medicineObj: medicine,
      mealObj: meal,
    });
  }, [date]);

  return (
    <div>
      <h1>Family Home</h1>
      <hr />
      <h2>하루 건강 정보</h2>
      <TabButtonGroup handleChangeTab={handleChangeTab} />
      <hr />
      <TabView tab={tab} healthInfo={healthInfo} />
      <hr />
      <Comment />
    </div>
  );
}

export default FamilyHome;
