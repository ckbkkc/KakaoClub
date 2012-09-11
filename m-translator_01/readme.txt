ver 0.1

<Google AppEngine api site>

가장 최신글이 api 호출시 보여집니다. 

수정해줘야 할곳. 
	1. app.yaml : (Line 1) application: 웹에서 등록한 자신의 앱엔진 이름
	2. settings.py : (Line 102~) STATIC_PATH 내 도메인명들을 자신의 앱엔진 도메인명으로. 

처음 설치시 모르는 부분은 http://memy80.blog.me/100129803708 의 링크들 참조.

- memy80@gmail.com 
- memy80.blog.me


테스트 도메인 : http://m-translator.appspot.com


차후 수정할것
	1. 페이징 처리 - 현재는 필요없음. 가장 최신글 하나만 불러오므로.
	2. 한글 깨짐 문제 - API 호출시(http://m-translator.appspot.com/api/notice/ ) 한글 깨짐.
	3. 관리자 1인만 글 쓰기 가능하도록.
	4. 다언어 지원문제


API 호출 후 리턴값
{ "data": [ { "content": "\uc548\ub155\ud558\uc138\uc694. \ubc18\uac11\uc2b5\ub2c8\ub2e4.", "title": "\uacf5\uc9c0\uc0ac\ud56d\uc785\ub2c8\ub2e4.", "version": "1.1" } ], 
"request": "Notice", 
"result": "Success" }

data - 가장 최신 공지사항 글의 제목, 내용, 버전
request - API 여러개를 만든다던지 할때 쓸 여유값
result - 정상적으로 호출후 값 반환되었을 경우 "Success" 반환됨. "Failure"반환될경우 data도 안넘어옴. 


-------------------------------------------------------------------------------------
맘대로 써도, 수정해도 됩니다. 크리에이티브 커먼즈 어쩌고 모릅...;;; 
그냥 요 리드미만..... 블로그 조회수랑 댓글좀;;
