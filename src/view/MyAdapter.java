package view;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.charlie.ivotytower.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	ArrayList<ApkEntity> apk_list;
	LayoutInflater inflater;
	
	public MyAdapter(Context context,ArrayList<ApkEntity> apk_list){
		this.apk_list = apk_list;
		this.inflater = LayoutInflater.from(context);		
	}
	
	public void onDateChange(ArrayList<ApkEntity> apk_list){
		this.apk_list = apk_list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return apk_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return apk_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ApkEntity entity = apk_list.get(position);
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.listview_item, null);
			holder.name_tv = (TextView) convertView.findViewById(R.id.item_name);
			holder.date_tv = (TextView) convertView.findViewById(R.id.item_date);
			holder.title_tv = (TextView) convertView.findViewById(R.id.item_title);
			holder.content_tv = (TextView) convertView.findViewById(R.id.item_content);
			holder.category_tv = (TextView) convertView.findViewById(R.id.item_category);
			holder.image_tv = (ImageView) convertView.findViewById(R.id.item_image);
			convertView.setTag(holder);			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name_tv.setText(entity.getName());
		holder.title_tv.setText(entity.getTitle());
		holder.content_tv.setText(entity.getContent());	
		holder.date_tv.setText(entity.getDate());
		holder.category_tv.setText(entity.getCategory());
		holder.image_tv.setImageBitmap(entity.getApk_im());
		return convertView;
	}
	class ViewHolder{
		TextView name_tv;
		TextView date_tv;
		TextView title_tv;
		TextView content_tv;	
		TextView category_tv;
		ImageView image_tv;
	}
}
