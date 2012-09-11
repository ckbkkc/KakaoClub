package KAKAO;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datasotre.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.datasotre.Blob;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class KAKAOUser {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private User user;

	@Persistent
	private String email;

	@Persistent
	private String nickname;

	@Persistent
	private String user;

	@Persistent
	private Integer apicount;

	@Persistent
	private Blob photofile;

	public Blob getPhotofile() {
		return photofile;
	}
}