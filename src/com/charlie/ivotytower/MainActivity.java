package com.charlie.ivotytower;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;





import view.ApkEntity;
import view.Deguo;
import view.Guan_iv;
import view.Guan_yu;
import view.Huodong;
import view.MyAdapter;
import view.Nvs;
import view.ReFlashListView;
import view.Shij;
import view.Slindingmenu;
import view.Student;
import view.Zhic;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements view.ReFlashListView.IReflashListener{
	ArrayList<ApkEntity> apk_list;
	private Slindingmenu mleftMenu;
	private static boolean isExit = false;
	
	List<ApkEntity> news = new ArrayList<ApkEntity>();
	
	ApkEntity newsObject = new ApkEntity();
	
	MyAdapter adapter;
	ReFlashListView listView;
	
	private TextView text_8;
	private TextView text_7;
	private TextView text_6;
	private TextView text_5;
	private TextView text_4;
	private TextView text_3;
	private TextView text_2;
	private TextView text_1;
	private TextView text_0;
	
	/*
	 * 网络加载图片用到的相关对象声明
	 */
	private ImageView im;
	private Bitmap bitmap;
	
	/*
	 * 判断是否退出的Handler
	 */
	public  Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				isExit = false;
			case 0x1122:			
		//		im = (ImageView) findViewById(R.id.item_image);
			
		//		im.setImageBitmap(bitmap);
				newsObject.setApk_im(bitmap);
			}			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);	
		setData();
		showList(apk_list);
		
		mleftMenu = (Slindingmenu) findViewById(R.id.id_menu);
		text_8 = (TextView) findViewById(R.id.yu);
		text_7 = (TextView) findViewById(R.id.guany);
		text_6 = (TextView) findViewById(R.id.deg);
		text_5 = (TextView) findViewById(R.id.huod);
		text_4 = (TextView) findViewById(R.id.nvs);
		text_3 = (TextView) findViewById(R.id.xues);
		text_2 = (TextView) findViewById(R.id.shij);
		text_1 = (TextView) findViewById(R.id.zhic);
		text_1 = (TextView) findViewById(R.id.shouy);
		
		
		
		getImage();
		sendRequestWithHttpUrlConnection();
	}
	
	public void getImage(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				URL url;
				try {
					url = new URL("http://10.0.2.2/wordpress/wp-content/uploads/2015/07/6df50c7a14d6e381_mr1436434724315-150x150.jpg");			
				
					InputStream it_is=url.openStream();
					bitmap=BitmapFactory.decodeStream(it_is);
				it_is.close();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				mHandler.sendEmptyMessage(0x1122);	
			}
		}).start();
	}
	
	
	
	private void sendRequestWithHttpUrlConnection() {
		//开启线程来发起网络请求
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {				
					HttpClient httpClient = new DefaultHttpClient();
					//指定访问的服务器地址是电脑本机
					HttpGet httpget = new HttpGet("http://10.0.2.2/wordpress/?json=get_post&post_id=30");
					HttpResponse httpResponse = httpClient.execute(httpget);
					if(httpResponse.getStatusLine().getStatusCode() == 200 ){
						//请求和响应都成功了
						Log.d("MainaActivity", "请求网站成功");
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						parseJSONWithGSON(response);
					}
									
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}			
		}).start();	
	}

	
	
	protected void parseJSONWithGSON(String jsonData) {
		try {
			
			JSONObject post = new JSONObject(jsonData).getJSONObject("post");	
			JSONObject author = new JSONObject(jsonData).getJSONObject("post").
					getJSONObject("author");
			
			
					
			String id = post.getString("id");
			String title = post.getString("title");
			String content = post.getString("content");
			String date = post.getString("date");
			String imageurl = post.getString("thumbnail");			
			String name = author.getString("name");	
			
			newsObject.setId(id);
			newsObject.setTitle(title);
			newsObject.setContent(content);
			newsObject.setDate(date);
			newsObject.setName(name);
			newsObject.setImageUrl(imageurl);
			
																
		} catch (Exception e) {	
			e.printStackTrace();
		}		
	}
	
	private void showList(ArrayList<ApkEntity> apk_list) {
		// TODO Auto-generated method stub
		if( adapter== null){
			listView = (ReFlashListView) findViewById(R.id.shou_item);
			listView.setInterface(this);
			adapter = new MyAdapter(this, apk_list);
			listView.setAdapter(adapter);			
		}else{
			adapter.onDateChange(apk_list);
		}
	}

	private void setData() {
		// TODO Auto-generated method stub
		apk_list = new ArrayList<ApkEntity>();
		for(int i=0 ; i<10 ; i++){
			ApkEntity entity = new ApkEntity();
			entity.setTitle(""+newsObject.getTitle());
			entity.setContent(""+newsObject.getContent());
			entity.setName(""+newsObject.getName());
			entity.setDate(""+newsObject.getDate());
			entity.setCategory("活动沙龙");
			apk_list.add(entity);
		}
	}
	
	/*
	 * 写入刷新数据
	 */
	public void setReflashData(){
		for(int i=0; i<2; i++){
			ApkEntity entity = new ApkEntity();
			entity.setTitle(""+newsObject.getTitle());
			entity.setContent(""+newsObject.getContent());
			entity.setName(""+newsObject.getName());
			entity.setDate(""+newsObject.getDate());
			entity.setCategory("职场攻略");
			entity.setApk_im(newsObject.getApk_im());
			apk_list.add(0, entity);
		}
	}
	/*
	 * 通知刷新
	 * @see view.ReFlashListView.IReflashListener#onReflash()
	 */
	@Override
	public void onReflash() {
		// TODO Auto-generated method stub
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//获取最新数据
				setReflashData();
				//通知界面显示
				showList(apk_list);
				//通知listView刷新数据完毕
				listView.reflashComplete();
			}
			
		}, 2000);
	}
	
	
	public void toggleMenu(View view)
	{
		mleftMenu.toggle();
	}
	public void click_8(View view8)
	{
		Intent intent = new Intent(this,Guan_yu.class);
		startActivity(intent);
	}
	public void click_7(View view8)
	{
		Intent intent = new Intent(this,Guan_iv.class);
		startActivity(intent);
	}
	public void click_6(View view8)
	{
		Intent intent = new Intent(this,Deguo.class);
		startActivity(intent);
	}
	public void click_5(View view8)
	{
		Intent intent = new Intent(this,Huodong.class);
		startActivity(intent);
	}
	public void click_4(View view8)
	{
		Intent intent = new Intent(this,Nvs.class);
		startActivity(intent);
	}
	public void click_3(View view8)
	{
		Intent intent = new Intent(this,Student.class);
		startActivity(intent);
	}
	public void click_2(View view8)
	{
		Intent intent = new Intent(this,Shij.class);
		startActivity(intent);
	}
	public void click_1(View view8)
	{
		Intent intent = new Intent(this,Zhic.class);
		startActivity(intent);
	}
	public void click_0(View view8)
	{
		mleftMenu.closeMenu();
	}
	
	public boolean onKeyDown(int keycode, KeyEvent event){
		if(keycode == KeyEvent.KEYCODE_BACK){
			exit();
			return true;
		}
		return super.onKeyDown(keycode, event);
	}
	private void exit(){
		if(!isExit){
			isExit = true;
			Toast.makeText(getApplicationContext(), "再次点击退出象牙塔国际", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}else {
			this.finish();
		}
	}

	
	
}
