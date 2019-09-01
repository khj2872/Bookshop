# Toy Project: Spring Boot와 Vue.js를 이용한 도서 쇼핑몰
처음 다뤄보는 기술 스택(Vue.js, JPA, Elastic Stack, AWS ···)들을 사용하여 만든 관리자-사용자 기반의 도서 쇼핑몰

## 목표
1. 프로젝트는 크게 API 서버, 클라이언트, 관리자 페이지로 구성한다.
2. API 서버와 관리자 페이지는 코드가 서로 교차 사용될 확률이 높기 때문에 분리해서 구현한다.
3. Spring Boot, Spring Data JPA·Elasticsearch 로 API 서버와 관리자 페이지를 구성한다.
4. 클라이언트단은 Vue.js를 활용하여 제작한다.
5. 관리자페이지는 Thymeleaf 템플릿 엔진을 이용한 SSR로 제작한다.
6. 완성된 프로젝트를 AWS EC2에 배포한다.
7. 6번까지의 과정이 끝나면 Travis-CI와 AWS S3, CodeDeploy를 자동 배포 시스템을 구축한다.
8. Nginx를 이용하여 Blue-Green 무중단 배포 시스템을 구축한다.
9. Elastic Stack을 이용해 Nginx의 Access-log를 분석한다.
10. 여러가지 기술 스택들을 경험해보는 것이 최종 목표이다.

## 주제
관리자-사용자 기반의 기본적인 기능들이 구현된 쇼핑몰을 구현한다.

## 구현
### API 서버
