![dangjang](https://github.com/user-attachments/assets/0b65609e-0fa3-4372-8946-ebb8c17ee812)

<div align=center>

  # Dangjang Backend API Server

</div>

## 목차

- [1. 서비스 개요](#1-서비스-개요)
    - [1-1. 서비스 소개](#1-1-서비스-소개)
    - [1-2. 주요 기능](#1-2-주요-기능)
    - [1-3. 개발 환경](#1-3-개발-환경)
- [2. 서비스 설계](#2-서비스-설계)
    - [2-1. use case](#2-1-use-case)
    - [2-2. ERD](#2-2-erd)
    - [2-3. CI/CD 파이프라인](#2-3-cicd-파이프라인)
    - [2-4. AWS 아키텍처](#2-4-aws-아키텍처)
- [3. 서비스 성과](#3-서비스-성과)

## 1. 서비스 개요

### 1-1. 서비스 소개

[당장 - 당뇨 관리, 혈당, 식단, 기록, 체중, 운동 - Google Play 앱](https://play.google.com/store/apps/details?id=com.dangjang.android&hl=ko-KR)

당장은 당뇨를 편리하게 기록하고 개선할 수 있도록 가이드를 제공하는 서비스입니다. 혈당, 체중, 운동, 식단 관리에 대한 건강 가이드를 통해 사용자의 생활 습관 관리를 돕고, 당뇨 관리를 조금 더 편하게 할 수 있는 사회를 만들고자 합니다.

### 1-2. 주요 기능

![feature](https://github.com/user-attachments/assets/e2fb3fe5-95ff-4772-8a2d-cbb8c42885fc)


► 건강 데이터 자동 기록

Google Health Connect 연동을 통해 스마트 워치, 삼성 헬스, 디바이스 내 건강 데이터(혈당, 체중, 운동, 칼로리)를 자동으로 불러옵니다.

► 맞춤 건강 가이드 제공

내분비내과 교수와 협업한 내용을 바탕으로 기록된 건강 데이터에 대해 가이드를 제공합니다.

► 건강 차트

주 단위의 건강 데이터 변화를 차트로 시각화해서 제공합니다. 혈당, 체중, 운동, 칼로리 변화 추이를 한 눈에 확인할 수 있습니다.

► 포인트 제공

당뇨를 관리하며 포인트를 얻을 수 있고, 쌓인 포인트로 기프티콘 교환이 가능합니다.

### 1-3. 개발 환경
- **Android**: Kotlin, OkHttp3, Retrofit2, Google Health Connect API, FCM, MVVM, Clean Architecture, Multi Module, Hilt, Coroutine, Flow, Databinding, ViewModel, Navigation
- **Backend**: Java, Spring Boot, JPA, MySQL, MongoDB, Redis, AWS, Docker, SonarQube, Github Actions

## 2. 서비스 설계

### 2-1. use case

![use case](https://github.com/user-attachments/assets/6cc5fb13-3bd4-4974-b02e-d5645c0d904c)

### 2-2. ERD

![erd](https://github.com/user-attachments/assets/4046e584-f252-4625-a0c3-bf7cbe567ccb)

### 2-3. CI/CD 파이프라인

![server_ci_cd_workflow](https://github.com/co-niverse/dangjang-devops/assets/101033262/1235a308-c1c3-4a85-b15d-1bc149013d54)

### 2-4. AWS 아키텍처

![aws_architecture_v4.0](https://github.com/co-niverse/dangjang-devops/assets/101033262/9afde7ac-3b69-44c1-a56b-a2fbb3950ac4)

## 3. 서비스 성과

![service result](https://github.com/user-attachments/assets/4e824365-7864-4ecb-952b-c63a93798b23)

DAU 10명 , WAU 220명 , MAU 570명 성과를 달성하였습니다.
