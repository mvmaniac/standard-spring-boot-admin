# Standard Spring Boot Admin

### 1. 스프링 시큐리티 - Spring Boot 기반으로 개발하는 Spring Security 강의 실습 예제 기반

* [스프링 시큐리티 - Spring Boot 기반으로 개발하는 Spring Security](https://www.inflearn.com/course/%EC%BD%94%EC%96%B4-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0# "스프링 시큐리티 - Spring Boot 기반으로 개발하는 Spring Security") 참고

### 2. 차이점

* Spring Boot 2, Gradle 6 기반
* EditorConfig 설정 추가
* 최대한 이해한 부분만 적용함

### 3. TODO

* ddl-auto 를 create 해서 할 경우 SecurityMetadataSource 에서 데이터 싱크가 안맞는 문제 확인
* 위 문제와 마찬가지로 getResourcesByMethod 를 실행하는 경우 데이터가 안들어가는 문제 확인
* @AuthenticationPrincipal 값이 null 인 현상 or AccountContext 가 아닌 Account로 나오는 이유
* 로그인 한 사용자의 getDetails 값이 null 이라서 WebAuthenticationDetails 로 변환할 수 없는 부분 
* /api 이하 url로 DB 연동하여 인가처리
* ajax 를 통한 로그인 성공시 이전 URL 이동 처리
