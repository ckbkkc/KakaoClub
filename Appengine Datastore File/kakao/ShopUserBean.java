<!--데이터 스토어 사용자 정보를 관리하는 ShopUserBean을 저장하고 쿼리한다.
몇몇 어노테이션을 사용하여 자바빈즈를 만든다고 생각하면 된닷.-->

package KAKAO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.datasotre.Key;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
<!--JDO를 이용해 데이터스토에 저장하거나 읽어 올 수 있는 클래스임을 선언-->
public class ShopUserBean{
	@PrimaryKey  
	<!--데이터스토어의 키를 저장하는 속성 선언-->
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	<!--그 클래스 안에서 데이터 스토어에 저장되어야 하는 데이터 필드는 @Persistent
	어노테이션을 이용하여 선언해야 한다. 만약 저장할 필요가 없는 속성이면
	@NotPersistent 로 선언하면 된닷-->
	private Key key;

	@Persistent
	private User user;

	@Persistent
	private String email;

	@Persistent
	private String nickname;

	<!--데이터 스토어 사용자 정보를 관리하는 ShopUserBean을 저장하고 쿼리한다.
	몇몇 어노테이션을 사용하여 자바빈즈를 만든다고 생각하면 된닷.-->


	public ShopUserBean(User user_info, String email, String nickname) {
		this.user = user_info;
		this.email = email;
		this.nickname = nickname;
	}
	
	public Key getKey() {
		return this.key;
	}

	public User getUser() {
		return this.user;
	}

	public String getNickname() {
		return this.nickname;
	}

	public String getEmail() {
		return this.email;
	}

	public void setUser(User userInfo) {
		this.user = userInfo;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

