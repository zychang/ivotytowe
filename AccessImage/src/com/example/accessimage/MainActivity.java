package com.example.accessimage;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView myImg;
	private Handler myHandler;
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myImg=(ImageView)findViewById(R.id.MyImg);
		myHandler=new Handler()
		{
			public void handleMessage(Message msg)
			{
				if(msg.what==0x1122)
				{
					myImg.setImageBitmap(bitmap);
				}
			};
		};
		new Thread()
		{
			public void run()
			{
				URL url;
				try {
					url = new URL("http://10.0.2.2/wordpress/wp-content/uploads/2015/07/6df50c7a14d6e381_mr1436434724315-150x150.jpg");
					
				
				InputStream is=url.openStream();
				bitmap=BitmapFactory.decodeStream(is);
				is.close();
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				myHandler.sendEmptyMessage(0x1122);
							
			};
			
		}.start();
	}

	
	

}
