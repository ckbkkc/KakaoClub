package kr.swmaestro.kakaoclub;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        initialize();
    }
    
    // http://isulnara.com/tt/227
    private void initialize()
    {
    	Handler handler = new Handler()
		{
		    @Override
		    public void handleMessage(Message msg)
		    {
		        finish();    // 액티비티 종료
		    }
		};

        handler.sendEmptyMessageDelayed(0, 1500);    // ms, 3초후 종료시킴
    }
}