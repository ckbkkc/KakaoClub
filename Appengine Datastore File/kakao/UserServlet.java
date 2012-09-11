package KAKAO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.List;
import java.servlet.*;
import java.servlet.http.*;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImageService;
import com.google.appengine.api.images.ImageServiceFactory;
import com.google.appengine.api.images.Transform
import org.apache.commons.codec.binary.Base64; 
<!--http://commons.apache.org/codec/  코덱필요함 다운로드후,클래스 경로에 포함-->
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.util.Stream;

import KAKAO.PMF;

@SuppressWarning("serial")
public class UserServlet extends HttpServlet {
	<!-- http://도메인.user/photo는 로그인한 사용자 정보에 있는 이미지를 출력
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		if (req.getPathInfo().toString().equals("/photo/")){
			getPhoto(req, resp);
		}
	}
	-->
	<!-- 아래와 같이 이런식으로 쿼리 불러와서 실행할 수 있어.. 이메일 정보 업데이트
	PersistenceManager pm = PMF.get().getPersistenceManager();
	Query query = pm.newQuery(KAKAOUser.class);
	query.setFilter("email == emailParam");
	query.declareParameters("String emailParam");
	Boolean exist = false;
	try {
		List<ShopUserBean> u = (List<ShopUserBean>) query.execute(user.getEmail());
		req.setAttribute("user", u.get(0));
	} finally {
		query.closeAll();
	}
	<!-- 이미지 or 파일업로드-->
	try{
	ServletFileUpload upload = new ServletFileUpload();
	FileItemIterator iterator = upload.getItemIterator(req);
	while (iterator.hasNext()) {
		FileItemStream item = iterator.next();
		InputStream istream = item.openStream();
		int len;
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			while ((len = istream.read(buffer, 0, buffer.length)) != -1)
				oStream.write(buffer, 0, len);

		ImagesService imagesService = ImageServiceFactory.getImagesService();
		Image oldImage = ImagesServiceFactory.makeImage(oStream.toByteArray());
		Transform resize = ImagesServiceFactory.makeResize(100, 200);<!--이미지사이즈 조정가능-->
		Image newImage = imagesService.applyTransform(resize, oldImage);

		byte[] newImageData = newImage.getImageData();
		Blob photo file = new Blob(newImageData);
		KAKAOUser.setPhotofile(photofile);
	} catch (Exception ex) {
		throw new IOException();
	}
	
	try{
		pm.makePersistent(KAKAOUser);
	} finally {
		pm.close();
	}
	return;
	-->
}