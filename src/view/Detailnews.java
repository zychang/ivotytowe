package view;

import com.charlie.ivotytower.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class Detailnews extends Activity{
	
	private TextView newsName;
	private TextView newsDate;
	private TextView newsTitle;
	private TextView newsContent;
	private ImageView newsImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news);
		
		newsName =(TextView) findViewById(R.id.details_author);
		newsDate =(TextView) findViewById(R.id.details_time);
		newsTitle =(TextView) findViewById(R.id.details_title);
		newsContent =(TextView) findViewById(R.id.details_content);
		
		Intent intent = getIntent();
		String title = intent.getStringExtra("title");
		String content = intent.getStringExtra("content");
		String name = intent.getStringExtra("name");
		String date = intent.getStringExtra("date");
		
	//	byte buff[] = intent.getByteArrayExtra("bitmap");
	//	Bitmap bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
		
		newsName.setText(name);
		newsDate.setText(date);
		newsTitle.setText(title);
		newsContent.setText(content);
		
	//	newsImage.setImageBitmap(bitmap);
		
	}
}
