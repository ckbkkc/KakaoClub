package kr.swmaestro.kakaoclub;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class GroupMainActivity extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);
        
        initActivity();
        setupTabs();
    }
    
    private void initActivity() {
    	ImageView btn_back = (ImageView)findViewById(R.id.group_main_titlebar_btn_back);
    	
    	btn_back.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				finish();
				
				return false;
			}
		});
    	
    }
    
    private void setupTabs() {
    	TabHost tabs = getTabHost();
    	TabHost.TabSpec spec = null;
    	Intent intent = null;
    	
    	// 그룹정보 탭 추가
    	spec = tabs.newTabSpec("tab_info");
    	intent = new Intent(this, GroupSubInfoActivity.class);
    	intent.putExtra("mode", "tab_info");
    	intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
    	
    	spec.setContent(intent);
    	spec.setIndicator("그룹정보");
    	tabs.addTab(spec);
    	
    	// 게시판 탭 추가
    	spec = tabs.newTabSpec("tab_square");
    	intent = new Intent(this, GroupSubSquareActivity.class);
    	spec.setContent(intent);
    	spec.setIndicator("광장");
    	tabs.addTab(spec);
    	
    	// 채팅 탭 추가
    	spec = tabs.newTabSpec("tab_chat");
    	intent = new Intent(this, GroupSubChatActivity.class);
    	spec.setContent(intent);
    	spec.setIndicator("채팅");
    	tabs.addTab(spec);
    	
    	// 일정 탭 추가
    	spec = tabs.newTabSpec("tab_schedule");
    	intent = new Intent(this, GroupSubScheduleActivity.class);
    	spec.setContent(intent);
    	spec.setIndicator("일정");
    	tabs.addTab(spec);
    	
    	tabs.setCurrentTab(0);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
}