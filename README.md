# 로그인/회원가입 기능 프로젝트
## 실행 방법
- 로컬 DB를 사용하기 때문에 프로젝트는 Java 11로 맞추고 실행해주시면 됩니다.
## 구현 스펙
- Java 11
- Spring Boot 2.7.5
- Gradle 7.6
## 기능 설명
### 1. 회원가입
- 번호 인증이 되어야 회원가입이 가능하게 구현했습니다.
- 해당 과제에서는 인증이 되었다는 값을 프론트에 의존할 수 없어서 다음과 같이 구현했습니다.
  - 사용자의 핸드폰 번호를 암호화하여 회원가입 당시 암호화된 핸드폰 번호도 입력받았습니다.
  - 이것의 문제점은 암호화된 핸드폰 번호를 계속해서 사용할 수 있다는 점입니다.
### 2. 번호 인증 발송(회원가입)
- 유저의 핸드폰 번호를 입력 받아 유효성 검증을 완료한 후, 인증번호를 리턴해줍니다.
- 6자리의 숫자를 랜덤으로 추출하여 리턴해주었습니다.
- 번호 발송 기능은 여러 외부 API가 존재합니다.
- 그렇기 때문에 전략 패턴을 사용하여 여러 외부 API를 사용하게 되더라도 메인 로직에서는 책임을 가져가지 않게 했습니다.

### 3. 번호 인증 발송(비밀번호 재설정)
- 유저의 핸드폰 번호를 입력 받아 유효성 검증을 완료한 후, 인증번호를 리턴해줍니다.
- 6자리의 숫자를 랜덤으로 추출하여 리턴해주었습니다.
- 위 회원가입과 다른 점은 이 기능에서는 휴대폰 번호가 users 테이블에 존재해야 합니다.

### 4. 번호 인증
- 제가 예전 개발에서는 번호 인증 책임을 프론트에 넘겼었습니다.
- 백엔드에서 구현 방법을 생각해보다가 redis를 떠올렸습니다.
- key를 핸드폰 번호, value를 인증번호로 가져갔습니다.
- 허나 M1 환경에서 EmbeddedRedis를 사용하려면 세팅 제약이 심해서 Map으로 대체하였습니다.

### 5. 로그인
- 이메일과 비밀번호로 로그인하게 구현했습니다.
- 로그인에 성공하면 accessToken을 리턴해줍니다.

### 6. 내 정보 조회
- accessToken과 SecurityContextHolder를 사용하여 자신의 정보만 볼 수 있게금 구현했습니다.
