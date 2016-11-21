package kr.hdd.carleamingTest.activity.data;

import android.os.Parcel;
import android.os.Parcelable;


public class FileData implements Parcelable {
	
	public String file_path;
	public String file_name;
	public boolean isDir;
	
	public FileData(){}
	
	public FileData(FileData data){
		
		this.file_name = data.file_name;
		this.isDir = data.isDir;

	}
	
	public FileData(Parcel in){
		readToParcel(in);
	}
	
	public void readToParcel(Parcel in) {
		file_path = in.readString();
		file_name = in.readString();
		isDir = intToBoolen(in.readInt());
	}
	
	private boolean intToBoolen(int val){
		return val != 0;
	}
	
	private int booleanToInt(boolean bool){
		if (bool) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (", file_name : " + file_name
				+ ", isDir : " + isDir);
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(file_path);
		dest.writeString(file_name);
		dest.writeInt(booleanToInt(isDir));
		
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

		@Override
		public Object createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new FileData(source);
		}

		@Override
		public Object[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FileData[size];
		}
		
	};

}
