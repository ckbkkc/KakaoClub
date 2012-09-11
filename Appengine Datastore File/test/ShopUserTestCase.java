package test;

import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import KAKAO.PMF;
import KAKAO.ShopUserBean;

public class ShopUserTestCase extends BaseTestCase {
	public void testDBPut() throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		ShopUserBean shopUser = new ShopUserBean(user, user.getEmail(), user.getNickname());
		<!-- ShopUserBean 자바빈즈 객체를 만들었다. -->
		PersistenceManager pm = PMF.get().getPersistenceManager();
		<!-- PMF클래스를 이용해서 PersistenceManager 인스턴스 생성 -->
		try{
			ShopUserBean result = pm.makePersistent(shopUser);
			<!-- makePersistent() 메서드를 이용하여 데이터스토어에 저장 -->
			assertEquals(result.getEmail(), "test@example.com");
			<!-- 결과 객체의 email을 점검 -->
		} finally {
			pm.close();
		}
		return;
	}

	<!-- 여기까지가 저장에 관한 내용이고 아래는 쿼리에 관한 내용이다. 
	     데이터를 쿼리하는데 쿼리 필터나 JDOQL을 사용한다. 여기선 쿼리필터를 사용한다.-->
	
	public void testDBGet () throws Exception {
	   UserService userService = UserServiceFactory.getUserService();
 	   User user = userService.getCurrentUser();

 	   ShopUserBean shopUser = new ShopUserBean(user, user.getEmail(), user.getNickname());
   	   PersistenceManager pm = PMF.get().getPersistenceManager();
    	   pm.makePersistent(shopUser);
	   <!-- 저장 -->

	   Query query = pm.newQuery(ShopUserBean.class);
	   query.setFiler("email == emailParam");
	   <!-- ShopUserBean 클래스 쿼리 인스턴스 생성 -->
       	   query.declareParameters("String emailParam");
           try {
        	List<ShopUserBean> results = 
            	(List<ShopUserBean>) query.execute("test@example.com");
        	assertEquals(1, reuslts.size());
        	assertEquals(results.get(0).getEmail(), "test@example.com");
    	   } finally {
        	query.closeAll();
        	pm.close();
    	   }
    	   return;
	}
	
	<!-- 여기부터는 데이터 수정 코드 -->
	public void testDBUpdate() throws Exception {
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    ShopUserBean shopUser = new ShopUserBean(user, user.getEmail(), user.getNickname());
	    PersistenceManager pm1 = PMF.get().getPersistenceManager();
	    pm1.makePersistent(shopUser);
	    Query q1 = pm1.newQuery(ShopUserBean.class);
	    q1.setFilter("email == emailParam");
	    q1.declareParameters("String emailParam");
	    try{
		List<ShopUserBean> results = 
		    (List<ShopUserBean>) q1.execute("test@example.com");
		assertEquals(results.get(0).getEmail(), "test@example.com");
		results.get(0).setEmail("test2@example.com");
		<!-- 쿼리결과를 email속성의 세터를 호출해서 이메일을 test2@example.com으로 바꿈 -->
	    } finally {
		q1.closeAll();
		pm1.close();
		<!-- 모든 변경된 내용은 close할때 반영된다. -->
	    }
	    
	    PersistenceManager pm2 = PMF.get().getPersistenceManager();
	    pm2.makePersistent(shopUser);
	    Query q2 = pm2.newQuery(ShopUserBean.class);
	    q2.setFilter("email == emailParam");
	    q2.declareParameter("String emailParam");
	    try {
		List<ShopUserBean> results = 
		    (List<ShopUserBean>) q2.execute("test2@example.com");
		assertEquals(results.get(0).getEmail(), "test2@example.com");
		<!-- test@~에서 test2@~로 바뀌었는지 점겸한다. -->
	    } finally {
		q2.closeAll();
	   	pm2.close();
	    }
	    return;
	}

	<!-- 엔티티 삭제에 관한 메서드 -->
	public void testDBDelete() throws Exception {
	    <!--여기부터-->
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    ShopUserBean shopUser = new ShopUserBean(user, user.getEmail(), user.getNickname());
	    PersistenceManager pm1 = PMF.get().getPersistenceManager();
	    pm1.makePersistent(shopUser);
	    try {
		ShopUserBean result = pm1.makePersistent(shopUser);
		assertEquals(result.getEmail(), "test@example.com");
	    } finally {
		pm1.close();
	    }

	    <!-- 여기까지 테스트용 ShopUserBean 엔티티를 저장했고 삭제하고자 하는 엔티티를 쿼리했다. -->
	    PersistenceManager pm2 = PMF.get().getPersistenceManager();
	    pm2.makePersistent(shopUser);
	    Query q2 = pm2.newQuery(ShopUserBean.class);
	    q2.setFilter("email == emailParam");
	    q2.declareParameters("String emailParam");
	    try {
		List<ShopUserBean> results = 
		    (List<ShopUserBean>) q2.execute("test2@example.com");
		assertEquals(results.size(), 1);
		pm2.deletePersistent(results.get(0));
		<!-- 바로 윗줄을 통과해야 삭제 된다. 레코드 한 번에 
             삭제하려면 deletePersistentAll(객체의리스트) -->
	    } finally {
		q2.closeAll();
		pm2.close();
	    }
	    
	    <!-- 아래는 삭제가 실행됐는지 점검하는 부분이다. 제대로 삭제되었다면 삭제한 엔티티를
		쿼리했을 때 결과가 없어야 한다. assert문에서 점검한다. -->
	    PersistenceManager pm3 = PMF.get().getPersistenceManager();
	    pm3.makePersistent(shopUser);
	    Query q3 = pm3.newQuery(ShopUserBean.class);
	    q3.setFilter("email == emailParam");
	    q3.declareParameters("string emailParam");
	    try {
		List<ShopUserBean> results = 
		    (List<ShopUserBean>) q3.execute("test2@example.com");
		assertEquals(0, results.size());
		<!-- 바로 윗줄을 통과해야 삭제 된다. 레코드 한 번에 
             삭제하려면 deletePersistentAll(객체의리스트) -->
	    } finally {
		q3.closeAll();
		pm3.close();
	    }	
	    return;
	}
}
<!-- 필요할 거 같은거 몇개 넣을게..
     java.util.Date  날짜와 시간을 다루는 데이터 자료형
     java.lang.String  유니코드 문자열을 저장 가능하며, 500 bytes이상되는 데이터 저장할 때 예외 발생
     com.google.appengine.api.datastore.PhoneNumber  전화번호 저장
     com.google.appengine.api.datastore.Key  데이터스토어에 기본키가 필요할 때 이 자료형 사용
     등등… 출처 : 구글앱엔진 시작하기(p.106)-->




