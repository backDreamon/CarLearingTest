package kr.hdd.carleamingTest.activity.data;


public class VideoData {
	
	public String file_name;
	public long startDate;
	public long endDate;
	public String isEvent;
	
	public VideoData(){}
	
	public VideoData(VideoData data){
		
		this.file_name = data.file_name;
		this.startDate = data.startDate;
		this.endDate = data.endDate;
		this.isEvent = data.isEvent;
	}
	
	
	public void setFileName(String file_name) {
		this.file_name = file_name;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	
	public void setEvent(String event) {
		this.isEvent = event;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return (", file_name : " + file_name
				+ ", startDate : " + startDate
				+ ", endDate : " + endDate
				+ ", isEvent : " + isEvent);
	}

}
