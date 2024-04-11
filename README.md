# 성식당
![성식당_로고_github](/uploads/13685f7d0b381bc668ee1268f67e50f2/성식당_로고_github.jpg)

## 프로젝트명 (서비스명) : 성식당 (sungchef)

### 😋 3가지 추천 알고리즘으로 집밥 메뉴를 추천받아보세요

### 🧑‍🍳 검색어 자동완성과 함께 메뉴를 검색해보세요

### 🔥 영수증 OCR 기반으로 냉장고에 재료를 등록해보세요

### 🍴 단계별로 레시피를 들으며 요리해보세요

## 개발 환경

### Android

IDE
- Android Studio Hedgehog 2023.1.1 Patch2

Version
- kotlin: 1.9.0
- JDK : jbr-17 jetbrains Runtime version 17.0.7

### Server

IDE
- IntelliJ IDEA 2023.3.2
- IntelliJ Community Edition 2023.3

Version
- Java17
- Gradle 8.6
- SpringBoot 3.2.3

### Data analysis

IDE
- Visual Studio Code 1.85.1

Version
- python 3.8.10
- hadoop 3.2.4
- spark 3.2.4-bin-hadoop3.2
- zeppelin 0.10.1
- zookeeper 3.8.4
- Django 4.2.11

## 아키텍처
![아키텍처](/uploads/52a7c1681d58c759272b907ddd314e4c/아키텍처.JPG)

## ERD 다이어그램
![ERD_다이어그램](/uploads/e20c4bb61cf3eb7f53a9b259469ef4b3/ERD_다이어그램.JPG)

## 기술 스택
![기술_스택](/uploads/8c37e64fa77e5b2fde28fba494265cb0/기술_스택.JPG)

## 추천 시스템 

### 로그 기반 음식 추천
각 음식 마다 “ 요리방식, 메인재료, 부재료1, 부재료2, 부재료3 … “의 형태에서 같은 것이 얼마나 있는지를 기준으로 유사도를 계산한다. 

그런데 이렇게 되면 메인재료와 요리방식이 겹치는 음식과 부재료 2개(예를 들면 소금, 후추)가 같은 음식의 유사도를 같게 된다. 그래서 각 단어에 가중치가 있어야 된다고 생각했다. 

이를 위해서 모든 단어를 TF-IDF 변환해주었다. TF-IDF는 단어들마다 중요한 정도에 따라서 가중치를 부여하는 방법이다. 

모든 문서에서 등장하는 단어는, 중요도가 낮으며 특정 문서에서만 자주 등장하는 단어는 중요도가 높다.
이를 이용해서 요리방식, 메인재료, 부재료들 각각에 가중치를 주어 추천 할 수 있게 되었다.

### 유저 설문 기반 음식 추천 (유저 기반 협업 필터링)
협업 필터링은 제품에 대한 유저의 평가나 행동에 의존하여 추천하게 된다. 

새로운 유저의 경우 제품을 이용한 기록, 평점, 좋아요 등 행동에 대한 기록이 전혀 없기 때문에 협업 필터링 추천시스템은 cold start 문제에 매우 민감하게 반응한다. 

이를 해결하기 위해 회원가입시 유저에게 취향 설문조사를 받았다. 그리고 처음 서비스가 배포되었을 때 유저 데이터가 충분히 쌓이지 않아 비슷한 유저를 찾을 수 없는 문제가 있다. 

이를 해결하기 위해 음식 커뮤니티(만개의 레시피)의 유저와 그 유저의 음식 후기 데이터를 크롤링하여 이 문제를 해결하여 유저기반 협업 필터링의 추천을 구현하였다.

### 냉장고 재료 기반 음식 추천
기본은 첫번째의 로그 기반 추천과 매우 비슷하다. 각 음식은 “ 재료1, 재료2, 재료3 … “ 형태로 유저 냉장고 재료도 “재료1, 재료2, 재료3 ….” 형태로 만들어주고 냉장고의 재료와 음식의 재료 간의 유사도가 가장 높은 음식을 추천해준다. 

이를 위해 모든 음식의 모든 재료에 대한 전처리를 할 필요가 있었다. “감자”, “감자 작은것”, “감자 (중)”, “중간 크기 감자”, “감자中”, “중간크기 감자”를 모두 감자로 통일해 주었다.

