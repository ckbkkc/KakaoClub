package mutest;

import static org.easymock.EasyMock.*;
import javax.servlet.http.*;

public class ExampleMockTestCase extends TestCase {
	public void testExample() throws Exception {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		<!--이 요청의 리턴 값으로 예상되는 인자와 그 값을 밑(expect)에서 지정-->

		expect(request.getParameter("email")).andReturn("test@example.com");
		expect(request.getParameter("nickname")).andReturn("John Doe");

		request.setAttribute("email", "test@example.com");
		request.setAttribute("nickname", "John Doe");
		<!--mock 객체를 테스트 하고자 여기서 인자와 값을 저장-->

		replay(request);

		assertEquals("tesst@example", request.getParameter("email"));
		assertEquals("John Doe", request.getParameter("nickname"));
		<!--위의 결과를 assert문으로 점검-->
		return;
	}
}