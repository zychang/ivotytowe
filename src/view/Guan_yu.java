package view;

import com.charlie.ivotytower.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class Guan_yu extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guan_yu);
	}
}
