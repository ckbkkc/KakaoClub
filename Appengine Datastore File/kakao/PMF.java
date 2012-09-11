<!--JDO인터페이스를 사용할때 persistenceManagerFactory객체가 적어도
하나 필요하다. JDO로 데이터스토어와 연동이 필요할 때마다 사용하게 될 것이야-->
package KAKAO;

import javax.jdo.JDOHelper;
import javax.jdo.persistenceManagerFactory;

public final class PMF {
	private static final PersistenceManagerFactory pmfInstance =
		JDOHelper.getPersistenceManagerFactory("transactions-optional");

	private PMF() {}
	
	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}