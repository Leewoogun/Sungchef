# D208 Sixth Sense

- 서비스명 : 성식당 (SungChef)



# Git Flow

- master : 제품으로 출시될 수 있는 브랜치
- develop : 다음 출시 버전을 개발하는 브랜치
- feature : 기능을 개발하는 브랜치
- release : 이번 출시 버전을 준비하는 브랜치
- hotfix : 출시 버전에서 발생한 버그를 수정 하는 브랜치

| 출처 : [우린 Git-flow를 사용하고 있어요 | 우아한형제들 기술블로그](https://techblog.woowahan.com/2553/)

- develop 변경사항을 feature로 가져오기 (Optional)

- develop 브랜치에 upstream/feature-user 브랜치를 merge 합니다

- upstream/feature-user 기능이 merge된 develop를 upstream에 push 합니다

- 출시 담당자는 QA를 시작하기 위해 먼저 release 브랜치를 생성하고 upstream에 push하여 release 브랜치를 공유

- QA 중 버그 수정하기

- release 브랜치에서 버그 티켓에 대한 브랜치를 생성합니다

- 버그를 수정합니다

- 작업 브랜치에 버그 수정 사항을 커밋합니다

- 작업 브랜치를 origin에 push 합니다

- Github에서 bfm-101_bug_login_id_max_length 브랜치를 release-1.0.0에 merge 하는 Pull Request를 생성합니다

- 동료에게 리뷰 승인을 받은 후 자신의 Pull Request를 merge 합니다.

### 앱 출시

release 브랜치를 최신 상태로 갱신합니다

release 브랜치를 develop 브랜치에 merge 합니다

develop 브랜치를 upstream에 push 합니다

release 브랜치를 master 브랜치에 merge 합니다

1.0.0 태그를 추가합니다

master 브랜치와 1.0.0 태그를 upstream에 push 합니다

## 작업을 할 때 지켜야할 서로 간의 약속

저희는 작업을 할 때 지켜야 할 몇 가지 약속이 있습니다.

1. 작업을 시작하기 전에 JIRA 티켓을 생성합니다.
2. 하나의 티켓은 되도록 하나의 커밋으로 합니다.
3. 커밋 그래프는 최대한 단순하게 가져갑니다.
4. 서로 공유하는 브랜치의 커밋 그래프는 함부로 변경하지 않습니다.
5. 리뷰어에게 꼭 리뷰를 받습니다.
6. 자신의 Pull Request는 스스로 merge 합니다.

처음에는 master와 develop 브랜치가 존재합니다. 물론 develop 브랜치는 master에서부터 시작된 브랜치입니다. develop 브랜치에서는 상시로 버그를 수정한 커밋들이 추가됩니다. 새로운 기능 추가 작업이 있는 경우 develop 브랜치에서 feature 브랜치를 생성합니다. feature 브랜치는 언제나 develop 브랜치에서부터 시작하게 됩니다. 기능 추가 작업이 완료되었다면 feature 브랜치는 develop 브랜치로 merge 됩니다. develop에 이번 버전에 포함되는 모든 기능이 merge 되었다면 QA를 하기 위해 develop 브랜치에서부터 release 브랜치를 생성합니다. QA를 진행하면서 발생한 버그들은 release 브랜치에 수정됩니다. QA를 무사히 통과했다면 release 브랜치를 master와 develop 브랜치로 merge 합니다. 마지막으로 출시된 master 브랜치에서 버전 태그를 추가합니다.

## Git Convention

### 포맷

```
type: subject

body
```

#### type

- 하나의 커밋에 여러 타입이 존재하는 경우 상위 우선순위의 타입을 사용한다.
- fix: 버스 픽스
- feat: 새로운 기능 추가
- refactor: 리팩토링 (버그픽스나 기능추가없는 코드변화)
- docs: 문서만 변경
- style: 코드의 의미가 변경 안 되는 경우 (띄어쓰기, 포맷팅, 줄바꿈 등)
- test: 테스트코드 추가/수정
- chore: 빌드 테스트 업데이트, 패키지 매니저를 설정하는 경우 (프로덕션 코드 변경 X)

#### subject

- 제목은 50글자를 넘지 않도록 한다.
- 개조식 구문 사용
    - 중요하고 핵심적인 요소만 간추려서 (항목별로 나열하듯이) 표현
- 마지막에 특수문자를 넣지 않는다. (마침표, 느낌표, 물음표 등)

#### body (optional)

- 각 라인별로 balled list로 표시한다.
    - 예시) - AA
- 가능하면 한줄당 72자를 넘지 않도록 한다.
- 본문의 양에 구애받지 않고 최대한 상세히 작성
- “어떻게” 보다는 “무엇을" “왜” 변경했는지 설명한다.

## Additional Requirement

각 PR의 요구사항과 더불어, 해당 명세를 **반드시** 만족해야 합니다.

- 매 Step 마다 테스트 코드를 작성해보세요.
  - 커버리지 80% 이상을 맞추지 못할 경우, PR이 제한됩니다.
- Code Smell을 최소화 하세요.
- Code Convention 을 사용하여 코드를 작성해 주세요.
- 여기서는 **네이버 핵 데이 컨벤션**을 사용합니다.
    - 출처: https://naver.github.io/hackday-conventions-java/