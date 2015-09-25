package view;

import com.charlie.ivotytower.MainActivity;
import com.charlie.ivotytower.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Slindingmenu extends HorizontalScrollView{
	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;	
	private int mScreenWidth;
	
	private int mMenuWidth;
	
	private boolean once;
	private boolean isOpean;
	
	private int mMenuRighrPadding = 50;
	

	/**
	 * δʹ���Զ�������ʱ����
	 * @param context
	 * @param attrs
	 */
	public Slindingmenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		mScreenWidth = outMetrics.widthPixels;
		//��dpת��Ϊpx
	    mMenuRighrPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());
	}
	//������view�Ŀ�͸ߣ������Լ��Ŀ�͸�
	public void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		if(!once)
		{
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRighrPadding;
			mContent.getLayoutParams().width = mScreenWidth;
			once = true;
		}		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	//ͨ������ƫ������menu����
	public void onLayout(boolean changed,int l,int t,int r,int b){
				
		super.onLayout(changed, l, t, r, b);
		if(changed)
		{
			this.scrollTo(mMenuWidth, 0);		
		}		
	}
	
	public boolean onTouchEvent(MotionEvent ev){
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			//���������ߵĲ˵�
			int scrollX = getScrollX();
			
			if(scrollX > mMenuWidth / 2)
			{
				this.smoothScrollTo(mMenuWidth, 0);
				isOpean = false;
			}else
			{
				this.smoothScrollTo(0, 0);
				isOpean = true;
			}
			
			return true;
		}		
		return super.onTouchEvent(ev);
	}
	/**
	 * �򿪲˵�
	 */
	public void openMenu()
	{
		if(isOpean)return;
		this.smoothScrollTo(0, 0);
		isOpean = true;
	}
	/**
	 * �رղ˵�
	 */
	public void closeMenu()
	{
		if(!isOpean)return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpean = false;
	}
	/**
	 * �л��˵�
	 */
	public void toggle()
	{
		if(isOpean)
		{
			closeMenu();
		}else
		{
			openMenu();
		}		
	}
}


