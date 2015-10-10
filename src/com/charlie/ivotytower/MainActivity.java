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
import org.json.JSONArray;
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
	public ArrayList<ApkEntity> apk_list;	
	private Slindingmenu mleftMenu;
	private static boolean isExit = false;
	
	List<ApkEntity> news = new ArrayList<ApkEntity>();
	
	public ApkEntity[] newsObjects = new ApkEntity[10];	
	
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
	 * �������ͼƬ�õ�����ض�������
	 */
	private ImageView im;
	private Bitmap bitmap;
	
	/*
	 * �ж��Ƿ��˳���Handler
	 */
	public  Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 0:
				isExit = false;
			case 2:
				showList(apk_list);
			case 0x1122:			
		//		im = (ImageView) findViewById(R.id.item_image);			
		//		im.setImageBitmap(bitmap);
		//		newsObjects[0].setApk_im(bitmap);
		//		Log.d("Image", "ui�߳�showͼƬ");
		//		showList(apk_list);
			}			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);	
		
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
			
		sendRequestWithHttpUrlConnection();
		getImage();
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
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
				
				mHandler.sendEmptyMessage(0x1122);	
			}
		}).start();
	}
	
	
	private void sendRequestWithHttpUrlConnection() {
		//�����߳���������������
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {				
					HttpClient httpClient = new DefaultHttpClient();
					//ָ�����ʵķ�������ַ�ǵ��Ա���
					HttpGet httpget = new HttpGet("http://10.0.2.2/wordpress/?json=get_recent_posts");
					HttpResponse httpResponse = httpClient.execute(httpget);
					if(httpResponse.getStatusLine().getStatusCode() == 200 ){
						//�������Ӧ���ɹ���
						HttpEntity entity = httpResponse.getEntity();
						String response = EntityUtils.toString(entity, "utf-8");
						parseJSONWithGSON(response);						
					}									
				} catch (Exception e) {
					e.printStackTrace();
				}	
				mHandler.sendEmptyMessage(2);
			}			
		}).start();	
	}
	
	protected void parseJSONWithGSON(String jsonData) {
		try {			
			JSONArray posts = new JSONObject(jsonData).getJSONArray("posts");						
			for(int j=0; j<posts.length();j++){
				ApkEntity newsO = new ApkEntity();
				JSONObject newsObj = posts.getJSONObject(j);
				JSONObject author = posts.getJSONObject(j).getJSONObject("author");
							
				String id = newsObj.getString("id");
				String title = newsObj.getString("title");
				String content = newsObj.getString("content");
				String date = newsObj.getString("date");
				String imageurl = newsObj.getString("thumbnail");				
				String name = author.getString("name");
												
				newsO.setId(id);
				newsO.setDate(date);
				newsO.setContent(content);
				newsO.setTitle(title);
				newsO.setName(name);
		//		newsO.setImageUrl(imageurl);				
				newsObjects[j]=newsO;
			}
			getImage();
			setData();																
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
		for(int i=0 ; i<newsObjects.length; i++){
			
			ApkEntity entity = new ApkEntity();	
			
			entity.setTitle(""+newsObjects[i].getTitle());
			entity.setContent(""+newsObjects[i].getContent());
			entity.setName(" "+newsObjects[i].getName());
			entity.setDate(" "+newsObjects[i].getDate());
			entity.setCategory("�ɳ��");
			entity.setApk_im(bitmap);
			apk_list.add(entity);
		}	
	}
	
/*	
	private void setImage() {
		// TODO Auto-generated method stub
		Log.d("Image", "����ͼƬ1");
		apk_list = new ArrayList<ApkEntity>();		
		Log.d("Image", "����ͼƬ2");
		for(int i=0 ; i<newsObjects.length; i++){			
			ApkEntity entity = new ApkEntity();					
			entity.setApk_im(bitmap);
			apk_list.add(entity);
		}	
	}
	*/
	
	/*
	 * д��ˢ������
	 */
	public void setReflashData(){
		for(int i=0; i<2; i++){
			ApkEntity entity = new ApkEntity();
			entity.setTitle(" "+newsObjects[i].getTitle());
			entity.setContent(" "+newsObjects[i].getContent());
			entity.setName(" "+newsObjects[i].getName());
			entity.setDate(" "+newsObjects[i].getDate());
			entity.setCategory("ְ������");
	//		entity.setApk_im(newsObjects[0].getApk_im());
			apk_list.add(0, entity);
		}
	}
	/*
	 * ֪ͨˢ��
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
				//��ȡ��������
				setReflashData();
				//֪ͨ������ʾ
				showList(apk_list);
				//֪ͨlistViewˢ���������
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
			Toast.makeText(getApplicationContext(), "�ٴε���˳�����������", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}else {
			this.finish();
		}
	}	
}
