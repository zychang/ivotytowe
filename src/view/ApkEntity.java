package view;

import android.R.string;
import android.graphics.Bitmap;
import android.widget.ImageView;


public class ApkEntity {
	private String id;
	private String title;
	private String content;
	private String date;
	private String name;
	private String imageUrl;
	private String category;
	private Bitmap apk_im;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getApk_im() {
		return apk_im;
	}
	public void setApk_im(Bitmap apk_im) {
		this.apk_im = apk_im;
	}

}
