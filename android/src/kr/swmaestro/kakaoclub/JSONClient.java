package kr.swmaestro.kakaoclub;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

import android.R.bool;
import android.R.integer;
import android.os.AsyncTask;
import android.text.format.DateFormat;


public class JSONClient
{
	// 서버로부터 글자를 가져옴
	public static String GetStringFromServer(String targeturl, String sendstr)
	{
		URL url = null;
		HttpURLConnection urlConn = null;
		BufferedInputStream buf = null;
		
		try
		{
			url = new URL(targeturl);
			
			urlConn = (HttpURLConnection) url.openConnection();
			
			buf = new BufferedInputStream(urlConn.getInputStream());
			
			BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
			
			String line = null;
			String result = "";
			
			while ((line = bufreader.readLine()) != null)
			{
				result += line;
			}
			
			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return e.toString();
		}
		finally
		{
			urlConn.disconnect();
		}
	}
	
	// 서버로부터 바이너리 데이터를 가져옴
	public static byte[] GetByteFromServer(String inputurl)
	{
		byte[] result = new byte[10 * 1024 * 1024];
		
		return result;
	}
	
	// 가입 및 로그인 판정 
	public static String MainCheckLogin(String usertel)
	{
		/*
		 String Json = "[{\"Product\" : \"Mouse\", \"Maker\":\"Samsung\", \"Price\":23000},"
               + "{\"Product\" : \"KeyBoard\", \"Maker\":\"LG\", \"Price\":12000},"
               + "{\"Product\":\"HDD\", \"Maker\":\"Western Digital\", \"Price\"156000}]";
		try{
		   String result = "";
		   JSONArray ja = new JSONArray(Json);
		   for (int i = 0; i < ja.length(); i++){
		      JSONObject order = ja.getJSONObject(i);
		      result += "product: " + order.getString("Product") + ", maker: " + order.getString("Maker") +
		                  ", price: " + order.getInt("Price") + "\n";
		   }
		}
		catch (JSONException e){ ;}
		 */
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"senttime\": \"" + System.currentTimeMillis() + "\","
				+ "\"device\": \"android\""
				+ "}";
		
		// 서버에 쿼리를 전송
		String result = GetStringFromServer("url", query);	// join, login, fail
		result = "join";
		
		return result;
	}
	
	public static String MainJoin(String usertel, String username)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"username\": \"" + username + "\","
				+ "\"senttime\": \"" + System.currentTimeMillis() + "\","
				+ "\"device\": \"android\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String MainGetGrouplist(String usertel, int startindex, int listcount)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"startindex\": " + Integer.toString(startindex) + ","
				+ "\"listcount\": " + Integer.toString(listcount)
				+ "}";
		
		String result = GetStringFromServer("url", query);	// JSON
		result =
				"["
				+ "{"
				+ "\"groupid\": \"1234\","
				+ "\"groupname\": \"SWMaestro 그룹\","
				+ "\"new\": 6"
				+ "},"
				+ "{"
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"ASDF 그룹\","
				+ "\"new\": 5"
				+ "},"
				+ "{"
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"S 그룹\","
				+ "\"new\": 5"
				+ "},"
				+ "{"
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"A 그룹\","
				+ "\"new\": 5"
				+ "},"
				+ "{"
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"DF 그룹\","
				+ "\"new\": 0"
				+ "},"
				+ "{"
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"F 그룹\","
				+ "\"new\": 0"
				+ "},"
				+ "{"
				+ "\"groupid\": \"9012\","
				+ "\"groupname\": \"dx 그룹\","
				+ "\"new\": 0"
				+ "}"
				+ "]";
		
		return result;
	}
	
	public static String MainCreateGroup(String usertel, String groupname, String members)
	{
		String query  =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"groupname\": \"" + groupname + "\","
				+ "\"members\":"
				+ "["
				+ members
				+ "],"
				+ "\"senttime\": " + "\"2012-01-08T19:05PM\""
				+ "}";
		
//		+ "\“01011111111\”,"
//		+ "\“01022222222\”,"
//		+ "\“01033333333\”,"
//		+ "\“01044444444\”"
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String GroupGetGroupInfo(String groupid, String usertel)
	{
		String query =
			"{"
			+ "\"groupid\": " + "\"" + groupid + "\","
			+ "\"usertel\": " + "\"" + usertel + "\""
			+ "}";
			
		String result = GetStringFromServer("url", query);	// JSON
		result =
				"{"
				+ "\"groupname\": \"" 
				+ "\"groupid\": \"5678\","
				+ "\"groupname\": \"A 그룹\","
				+ "\"new\": 5"
				+ "}";
		
		return result;
	}
	
	public static String GroupModifyGroupInfo(String groupid, String groupname, byte[] groupimage, String groupdesc)
	{
		String query = 
				"{"
				+ "\"groupid\": \"" + groupid + "\","
				+ "\"groupname\": \"" + groupname + "\","
				+ "\"groupimage\": \"" + groupimage + "\","
				+ "\"groupdesc\": \"" + groupdesc + "\","
				+ "\"modifytime\": \"" + "time" + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String GroupGetGroupMemberlist(String groupid, int startindex, int listcount)
	{
		String query =
				"{"
				+ "\"groupid\": \"" + groupid + "\","
				+ "\"startindex\": " + Integer.toString(startindex) + ","
				+ "\"listcount\": " + Integer.toString(listcount)
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SquareGetPostlist(String groupid, int startindex, int listcount)
	{
		String query =
				"{"
				+ "\"groupid\": \"" + groupid + ","
				+ "\"startindex\": " + Integer.toString(startindex) + ","
				+ "\"listcount\": " + Integer.toString(listcount)
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SquareGetPost(String postid, int sizelimit)
	{
		String query =
				"{"
				+ "\"postid\": \"" + postid + "\","
				+ "\"sizelimit\": " + Integer.toString(sizelimit)
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	// 글의 유형 enum
	enum POSTTYPE {PLAINTEXT, PHOTO, MOVIE, VOICE, FILE, POLL};
	
	public static String SquareSendPost(String usertel, String postcontent, POSTTYPE posttype, String typelinkid)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"postcontent\": \"" + postcontent + "\","
				+ "\"posttype\": \"" + posttype + "\","
				+ "\"posttime\": \"" + "time" + "\","
				+ "\"typelinkid\": \"" + typelinkid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		result = "success";
		
		return result;
	}
	
	public static String SendPhoto(String usertel, byte[] data)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"data\": \"" + data + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SendMovie(String usertel, byte[] data)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"data\": \"" + data + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SendFile(String usertel, byte[] data, String filename)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"data\": \"" + data + "\","
				+ "\"filename\": \"" + filename + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;	
	}
	
	public static String SendVoice(String usertel, byte[] data)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"data\": \"" + data + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SendPoll(String usertel, String pollsubject, String polllist, bool ismulti)
	{
		String polldata = "";//////////////////////////////////////////////////////////////////////////
		
		String query =
				"{"
				+ "\"pollsubject\": \"" + pollsubject + "\","
				+ "\"polllist\": [" + polllist + "],"
				+ "\"polldata\": [" + polldata + "],"
				+ "\"ismulti\": \"" + ismulti + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);
		
		return result;
	}
	
	public static String SquareDoPoll(String typelinkid, String usertel, int pollindex)
	{
		String query =
				"{"
				+ "\"typelinkid\": \"" + typelinkid + "\","
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"pollindex\": \"" + Integer.toString(pollindex) + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// impossible, success, fail
		result = "success";
		
		return result;
	}
	
	public static String RemoveData(String type, String typelinkid)
	{
		String query =
				"{"
				+ "\"type\": \"" + type + "\","
				+ "\"typelinkid\": \"" + typelinkid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static byte[] GetPhoto(String typelinkid)
	{
		byte[] result = new byte[10 * 1024 * 1024];
		
		//result = GetByteFromServer(/url + typelinkid)
		
		return result;
	}
	
	public static byte[] GetMovie(String typelinkid)
	{
		byte[] result = new byte[10 * 1024 * 1024];
		
		//result = GetByteFromServer(/url + typelinkid)
		
		return result;
	}
	
	public static byte[] GetFile(String typelinkid)
	{
		byte[] result = new byte[10 * 1024 * 1024];
		
		//result = GetByteFromServer(/url + typelinkid)
		// file name도 전송
		
		return result;
	}
	
	public static byte[] GetVoice(String typelinkid)
	{
		byte[] result = new byte[10 * 1024 * 1024];
		
		//result = GetByteFromServer(/url + typelinkid)
		
		return result;
	}
	
	public static String GetPoll(String typelinkid)
	{
		String query = "url + typelinkid";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String SquareModifyPost(String postid, String postcontent, POSTTYPE posttype, String typelinkid)
	{
		String query =
				"{"
				+ "\"postid\": \"" + postid + "\","
				+ "\"postcontent\": \"" + postcontent + "\","
				+ "\"posttype\": " + posttype.toString() + "\","
				+ "\"typelinkid\": \"" + typelinkid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String SquareRemovePost(String postid)
	{
		String result = GetStringFromServer("url", postid);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String SquareGetReplylist(String postid, int startindex, int listcount)
	{
		String query =
				"{"
				+ "\"postid\": \"" + postid + "\","
				+ "\"startindex\": " + Integer.toString(startindex) + ","
				+ "\"listcount\": " + Integer.toString(listcount)
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String SquareSendReply(String usertel, String replycontent, String postid)
	{
		String query =
				"{"
				+ "\"usertel\": \"" + usertel + "\","
				+ "\"replycontent\": \"" + replycontent + "\","
				+ "\"replytime\": \"" + "time" + "\","
				+ "\"postid\": \"" + postid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String SquareRemoveReply(String postid, String replyid)
	{
		String query =
				"{"
				+ "\"postid\": \"" + postid + "\","
				+ "\"replyid\": \"" + replyid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String ScheduleGetSchedule(String groupid)
	{
		String result = GetStringFromServer("url", groupid);		// JSON
		result =
				"["
					+ "{"
					+ "\"content\": \"" + "밥 약속" + "\","
					+ "\"from\": \"" + "2010-03-03T10:00PM" + "\","
					+ "\"to\": \"" + "2010-03-03T10:00PM" + "\""
					+ "},"
					+ "{"
					+ "\"content\": \"" + "치킨 약속" + "\","
					+ "\"from\": \"" + "2010-03-03T10:00PM" + "\","
					+ "\"to\": \"" + "2010-03-03T10:00PM" + "\""
					+ "}"
				+ "]";
		
		return result;
	}
	
	public static String ScheduleSendSchedule(String groupid, String usertel, String content, DateFormat starttime, DateFormat endtime)
	{
		String query =
				"{"
			    + "\"groupID\": \"" + groupid + "\","
			    + "\"usertel\": \"" + usertel + "\","
			    + "\"content\": \"" + content + "\","
			    + "\"from\": \"" + "2010-03-03T10:00PM" + "\","
			    + "\"to\": \"" + "2010-03-03T10:00PM" + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String ScheduleRemoveSchedule(String groupid, String scheduleid)
	{
		String query =
				"{"
				+ "\"groupid\": \"" + groupid + "\","
				+ "\"scheduleid\": \"" + scheduleid + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String ScheduleModifySchedule(String groupid, String scheduleid, String content, DateFormat starttime, DateFormat endtime)
	{
		String query =
				"{"
				+ "\"groupid\": \"" + groupid + "\","
				+ "\"scheduleid\": \"" + scheduleid + "\","
				+ "\"content\": \"" + content + "\","
				+ "\"starttime\": \"" + "1234" + "\","
				+ "\"endtime\": \"" + "1234" + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "success";
		
		return result;
	}
	
	public static String isJoinedUser(String usertel)
	{
		String result = GetStringFromServer("url", usertel);	// true, false
		result = "true";
		
		return result;
	}
	
	public static String GetUserInfo(String usertel)
	{
		String result = GetStringFromServer("url", usertel);	// true, false
		result =
			"{"
			+ "\"username\": \"" + "김이름" + "\","
			+ "\"userphoto\": \"" + "사진" + "\","
			+ "\"usercomment\": \"" + "안녕하세요 회원입니다!" + "\""
			+ "}";
		
		return result;
	}
	
	public static String GetModifyUserInfo(String targettel, String username, byte[] userphoto, String usercomment)
	{
		String query =
				"{"
				+ "\"targettel\": \"" + targettel + "\","
				+ "\"username\": \"" + username + "\","
				+ "\"userphoto\": \"" + "사진" + "\","
				+ "\"usercomment\": \"" + usercomment + "\","
				+ "\"senttime\": \"" + "날짜" + "\""
				+ "}";
		
		String result = GetStringFromServer("url", query);	// success, fail
		result = "successs";
		
		return result;
	}
}
