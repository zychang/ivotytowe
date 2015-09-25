package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

import com.charlie.ivotytower.MainActivity;
import com.charlie.ivotytower.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.webkit.WebIconDatabase.IconListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


@SuppressWarnings("unused")
public  class ReFlashListView extends ListView implements OnScrollListener{
	View header;			//���������ļ�
	int headerHeight;
	int firstVisibleItme;			//��ǰ��һ���ɼ���Item��λ��
	int scrollState;				// listview ��ǰ����״̬
	boolean isRemark;            //��ǣ���ǰ����listview������µ�
	int startY;              //����ʱ��Yֵ
	
	int state;             //���嵱ǰ״̬
	final int none = 0;		//����״̬
	final int pull = 1;		//��ʾ����״̬
	final int relese = 2;	//��ʾ�ͷ�״̬
	final int reflashing = 3;	//ˢ��״̬
	IReflashListener iReflashListener; //ˢ�����ݽӿ�

	public ReFlashListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initview(context);
	}
	public ReFlashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initview(context);
	}

	public ReFlashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initview(context);
	}
	
	/**
	 * ��ʼ�����棬��Ӷ������ֵ�ListView
	 * @param context
	 */
	private void initview(Context context) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(this);
	}
	/**
	 * ֪ͨ��������ռ�õĿ���
	 * @param header2
	 */
	private void measureView(View header2) {
		// TODO Auto-generated method stub
		ViewGroup.LayoutParams p = header2.getLayoutParams();
		if(p == null){
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if(tempHeight > 0){
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		}else{
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		header2.measure(width, height);
	}
	
	/**
	 * ����header ���� �ϱ߾�
	 * @param topPadding
	 */
	private void topPadding(int topPadding){
		header.setPadding(header.getPaddingLeft(), topPadding,
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItme = firstVisibleItem;
	}
	
	public boolean onTouchEvent(MotionEvent ev){
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:			
			if(firstVisibleItme ==0 ){
				isRemark = true;
				startY = (int) ev.getY();			
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if( state == relese ){
				state = reflashing;
				//������������
				reflashViewByState();
				iReflashListener.onReflash();
			}else if(state == pull){
				state = none;
				isRemark = false;
				reflashViewByState();				
			}
			break;		
		}
		return super.onTouchEvent(ev);		
	}
	
	private void onMove(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(!isRemark){
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY-startY;
		int topPadding = space - headerHeight;
		switch(state){
		case none:
			if (space > 0){
				state = pull;
				reflashViewByState();
			}
			break;
		case pull:
			topPadding(topPadding);
			if(space > headerHeight + 30 && scrollState 
					== SCROLL_STATE_TOUCH_SCROLL){
			state = relese;
			reflashViewByState();
			}
			break;
		case relese:
			topPadding(topPadding);
			if(space < headerHeight + 30 ){
				state = pull;
				reflashViewByState();
			}else if(space < 0){
				state = none;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
	}
	/**
	 * ���ݵ�ǰ״̬���ı������ʾ
	 */
	private void reflashViewByState() {
		// TODO Auto-generated method stub
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arror = (ImageView) header.findViewById(R.id.arror);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
		RotateAnimation anim = new RotateAnimation(0, 180, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		switch(state){
		case none:
			arror.clearAnimation();
			topPadding(-headerHeight);
			break;
			
		case pull:
			arror.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("��������ˢ��");
			arror.clearAnimation();
			arror.setAnimation(anim1);
			break;
			
		case relese:
			arror.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("�ɿ�����ˢ��");
			arror.clearAnimation();
			arror.setAnimation(anim);
			break;
			
		case reflashing:
			arror.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("����ˢ��...");
			arror.clearAnimation();
			break;		
		}
	}	
	/**
	 * ��ȡ��������
	 */
	public void reflashComplete(){
		state = none;
		isRemark = false;
		reflashViewByState();
		TextView lastupdatetime =  (TextView) header.findViewById(R.id.update_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
	}
	
	public void setInterface(IReflashListener iReflashListener){
		this.iReflashListener = iReflashListener;
	}
	
	/**
	 * ˢ�����ݽӿ�
	 * @author Changyuan
	 *
	 */
	public interface IReflashListener {
		public void onReflash();			
	}
}











