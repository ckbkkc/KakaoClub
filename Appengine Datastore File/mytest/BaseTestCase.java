package mytest;

import java.io.File;
import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;
import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import junit.framework.TestCase;

public class BaseTestCase extends TestCase {
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
		ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")){});
		<!-- ApiProxy클래스 호출, 그래서 테스트환경을 적용하여 ApiProxy가 개발 서버로 호출되는 방법으로 테스트 환경을 꾸미는 것이다. -->
		ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
		proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY,
							Boolean.TRUE.toString());
		<!-- 개발 서버용 로컬 데이터 스토어를 사용하도록 설정 -->
	}

	@Override
	public void tearDown() throws Exception {
	<!-- 테스트에서 사용한 데이터들을 모두 지우고 정리하는 메서드,실행환경도 원래대로 되돌림 -->
		ApiProxyLocalImpl proxy = (ApiProxyLocalImpl) ApiProxy.getDelegate();
		LocalDatastoreService datastoreService
			= (LocalDatastoreService)proxy.getService("datastore_v3");
		datastoreService.clearProfiles();
		ApiProxy.setDelegate(null);
		ApiProxy.setEnvironmentForCurrentThread(null);
		super.tearDown();
	}
}