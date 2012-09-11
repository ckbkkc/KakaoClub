package kr.swmaestro.kakaoclub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	startActivity(new Intent(this, SplashActivity.class));
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initialize();
                
        TextView openGroup = (TextView)findViewById(R.id.groupOpen);
        openGroup.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent groupIntent = new Intent(getApplicationContext(), GroupMainActivity.class);
				startActivity(groupIntent);
			}
		});
        
        TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
        String usertel = telManager.getLine1Number();
        
//        if (JSONClient.MainCheckLogin(usertel) == "join")    
//        	Toast.makeText(getApplicationContext(), "가입해야 합니다.", Toast.LENGTH_LONG).show();
        
        String url = "http://stargt.com";
        new GetStringFromServerT().execute(url);
    }
    
    private void initialize()
    {
        InitializationRunnable init = new InitializationRunnable();
        new Thread(init).start();
    }
    
    class InitializationRunnable implements Runnable
    {
        public void run()
        {
            // 여기서부터 초기화 작업 처리
            // do_something
        }
    }
    
    private class GetStringFromServerT extends AsyncTask<String, Void, String>
    {
	    @Override
	    protected String doInBackground(String... param)
	    {
	        String result = null;
	     
	        try
	        {
	        	result = JSONClient.GetStringFromServer(param[0], param[1]);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	         
	        return result;
	    }
	    
	    @Override
	    protected void onPostExecute(String result) {
		    //super.onPostExecute(result);      
		    try
		    {
		    	EditText et = (EditText)findViewById(R.id.testedit);
		        et.setText(result);
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace();
		    }
		}
	}
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}