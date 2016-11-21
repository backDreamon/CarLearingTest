	package kr.hdd.carleamingTest.req;
	
	import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.SupportedPidUtill;
import android.content.Context;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
	
	public class PidConfig {
		private Context mContext = null;
	
		private static final String TAG = PidConfig.class.getSimpleName();
		private String mJson = null;
	
		public PidConfig(/*Context context*/){
			//		mContext = context;
			// Configure GSON
			final GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Pid.class, new PidSerialiser());
			gsonBuilder.setPrettyPrinting();
			final Gson gson = gsonBuilder.create();
	
			Date date = new Date();
			SimpleDateFormat SimpleDate = new SimpleDateFormat("yyyyMMddhhmmss");
	
//				    final PidData pidData = new PidData();
//				    pidData.setP0100("AA");
//				    pidData.setP0101("BB");
				    
			
//		    //테스트 
//			ArrayList<String> p0100 = new ArrayList<String>();
//			ArrayList<String> p0101 = new ArrayList<String>();
//			ArrayList<String> p0102 = new ArrayList<String>();
//	
//	
//			p0100.add("00ff");
//			p0100.add("00ee");
//			p0100.add("00ef");
//			p0101.add("01ff");
//			p0101.add("01ee");
//			p0101.add("01ef");
//			p0102.add("02ff");
//			p0102.add("02ee");
//			p0102.add("");
//		    
//		    final PidData pidData = new PidData();
//		    pidData.setP0100("00pid");
//		    pidData.setP0101("01pid");
//		    pidData.setP0102("02pid");
//		    
//		    final PidData pidData2 = new PidData();
//		    pidData2.setP0100("00pid2");
//		    pidData2.setP0101("01pid2");
//		    pidData2.setP0102("02pid2");
			
			List<PidData> arrpid = Lists.newArrayList(); 
			int size = 0;
			
			if(SupportedPidUtill.getInstance().getSupportedPid("410C") != null){
				size = SupportedPidUtill.getInstance().getSupportedPid("410C").size();
			}
	
			for(int i=0; i< (size > 0 ? size : 1) ; i++) {
				final PidData pidData = new PidData();
	
//		    	pidData.setP0100(p0100.get(i).toString());
				
				pidData.setUserId(MainApplication.getmUserId());
				pidData.setM_Date(SimpleDate.format(date));
	
				if(SupportedPidUtill.getInstance().getSupportedPid("4100") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4100").size() > i)
						pidData.setP0100(SupportedPidUtill.getInstance().getSupportedPid("4100").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4101") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4101").size() > i)
						pidData.setP0101(SupportedPidUtill.getInstance().getSupportedPid("4101").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4102") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4102").size() > i)
						pidData.setP0102(SupportedPidUtill.getInstance().getSupportedPid("4102").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4103") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4103").size() > i)
						pidData.setP0103(SupportedPidUtill.getInstance().getSupportedPid("4103").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4104") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4104").size() > i)
						pidData.setP0104(SupportedPidUtill.getInstance().getSupportedPid("4104").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4105") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4105").size() > i)
						pidData.setP0105(SupportedPidUtill.getInstance().getSupportedPid("4105").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4106") != null) {
					if(SupportedPidUtill.getInstance().getSupportedPid("4106").size() > i)
						pidData.setP0106(SupportedPidUtill.getInstance().getSupportedPid("4106").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4107") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4107").size() > i)
						pidData.setP0107(SupportedPidUtill.getInstance().getSupportedPid("4107").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4108") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4108").size() > i)
						pidData.setP0108(SupportedPidUtill.getInstance().getSupportedPid("4108").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4109") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4109").size() > i)
						pidData.setP0109(SupportedPidUtill.getInstance().getSupportedPid("4109").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410A").size() > i)
						pidData.setP010A(SupportedPidUtill.getInstance().getSupportedPid("410A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410B").size() > i)
						pidData.setP010B(SupportedPidUtill.getInstance().getSupportedPid("410B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410C").size() > i)
						pidData.setP010C(SupportedPidUtill.getInstance().getSupportedPid("410C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410D").size() > i)
						pidData.setP010D(SupportedPidUtill.getInstance().getSupportedPid("410D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410E").size() > i)
						pidData.setP010E(SupportedPidUtill.getInstance().getSupportedPid("410E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("410F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("410F").size() > i)
						pidData.setP010F(SupportedPidUtill.getInstance().getSupportedPid("410F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4110") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4110").size() > i)
						pidData.setP0110(SupportedPidUtill.getInstance().getSupportedPid("4110").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4111") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4111").size() > i)
						pidData.setP0111(SupportedPidUtill.getInstance().getSupportedPid("4111").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4112") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4112").size() > i)
						pidData.setP0112(SupportedPidUtill.getInstance().getSupportedPid("4112").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4113") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4113").size() > i)
						pidData.setP0113(SupportedPidUtill.getInstance().getSupportedPid("4113").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4114") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4114").size() > i)
						pidData.setP0114(SupportedPidUtill.getInstance().getSupportedPid("4114").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4115") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4115").size() > i)
						pidData.setP0115(SupportedPidUtill.getInstance().getSupportedPid("4115").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4116") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4116").size() > i)
						pidData.setP0116(SupportedPidUtill.getInstance().getSupportedPid("4116").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4117") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4117").size() > i)
						pidData.setP0117(SupportedPidUtill.getInstance().getSupportedPid("4117").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4118") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4118").size() > i)
						pidData.setP0118(SupportedPidUtill.getInstance().getSupportedPid("4118").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4119") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4119").size() > i)
						pidData.setP0119(SupportedPidUtill.getInstance().getSupportedPid("4119").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411A").size() > i)
						pidData.setP011A(SupportedPidUtill.getInstance().getSupportedPid("411A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411B").size() > i)
						pidData.setP011B(SupportedPidUtill.getInstance().getSupportedPid("411B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411C").size() > i)
						pidData.setP011C(SupportedPidUtill.getInstance().getSupportedPid("411C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411D").size() > i)
						pidData.setP011D(SupportedPidUtill.getInstance().getSupportedPid("411D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411E").size() > i)
						pidData.setP011E(SupportedPidUtill.getInstance().getSupportedPid("411E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("411F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("411F").size() > i)
						pidData.setP011F(SupportedPidUtill.getInstance().getSupportedPid("410F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4120") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4120").size() > i)
						pidData.setP0120(SupportedPidUtill.getInstance().getSupportedPid("4120").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4121") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4121").size() > i)
						pidData.setP0121(SupportedPidUtill.getInstance().getSupportedPid("4121").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4122") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4122").size() > i)
						pidData.setP0122(SupportedPidUtill.getInstance().getSupportedPid("4122").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4123") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4123").size() > i)
						pidData.setP0123(SupportedPidUtill.getInstance().getSupportedPid("4123").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4124") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4124").size() > i)
						pidData.setP0124(SupportedPidUtill.getInstance().getSupportedPid("4124").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4125") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4125").size() > i)
						pidData.setP0125(SupportedPidUtill.getInstance().getSupportedPid("4125").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4126") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4126").size() > i)
						pidData.setP0126(SupportedPidUtill.getInstance().getSupportedPid("4126").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4127") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4127").size() > i)
						pidData.setP0127(SupportedPidUtill.getInstance().getSupportedPid("4127").get(i).toString()); 
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4128") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4128").size() > i)
						pidData.setP0128(SupportedPidUtill.getInstance().getSupportedPid("4128").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4129") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4129").size() > i)
						pidData.setP0129(SupportedPidUtill.getInstance().getSupportedPid("4129").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412A").size() > i)
						pidData.setP012A(SupportedPidUtill.getInstance().getSupportedPid("412A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412B").size() > i)
						pidData.setP012B(SupportedPidUtill.getInstance().getSupportedPid("412B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412C").size() > i)
						pidData.setP012C(SupportedPidUtill.getInstance().getSupportedPid("412C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412D").size() > i)
						pidData.setP012D(SupportedPidUtill.getInstance().getSupportedPid("412D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412E").size() > i)
						pidData.setP012E(SupportedPidUtill.getInstance().getSupportedPid("412E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("412F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("412F").size() > i)
						pidData.setP012F(SupportedPidUtill.getInstance().getSupportedPid("412F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4130") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4130").size() > i)
						pidData.setP0130(SupportedPidUtill.getInstance().getSupportedPid("4130").get(i).toString());
				}	
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4131") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4131").size() > i)
						pidData.setP0131(SupportedPidUtill.getInstance().getSupportedPid("4131").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4132") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4132").size() > i)
						pidData.setP0132(SupportedPidUtill.getInstance().getSupportedPid("4132").get(i).toString());
				}	
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4133") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4133").size() > i)
						pidData.setP0133(SupportedPidUtill.getInstance().getSupportedPid("4133").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4134") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4134").size() > i)
						pidData.setP0134(SupportedPidUtill.getInstance().getSupportedPid("4134").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4135") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4135").size() > i)
						pidData.setP0135(SupportedPidUtill.getInstance().getSupportedPid("4135").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4136") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4136").size() > i)
						pidData.setP0136(SupportedPidUtill.getInstance().getSupportedPid("4136").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4137") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4137").size() > i)
						pidData.setP0137(SupportedPidUtill.getInstance().getSupportedPid("4137").get(i).toString()); 
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4138") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4138").size() > i)
						pidData.setP0138(SupportedPidUtill.getInstance().getSupportedPid("4138").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4139") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4139").size() > i)
						pidData.setP0139(SupportedPidUtill.getInstance().getSupportedPid("4139").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("413A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413A").size() > i)
						pidData.setP013A(SupportedPidUtill.getInstance().getSupportedPid("413A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("413B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413B").size() > i)
						pidData.setP013B(SupportedPidUtill.getInstance().getSupportedPid("413B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("413C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413C").size() > i)
						pidData.setP013C(SupportedPidUtill.getInstance().getSupportedPid("413C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("413D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413D").size() > i)
						pidData.setP013D(SupportedPidUtill.getInstance().getSupportedPid("413D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("3E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413E").size() > i)
						pidData.setP013E(SupportedPidUtill.getInstance().getSupportedPid("413E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("413F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("413F").size() > i)
						pidData.setP013F(SupportedPidUtill.getInstance().getSupportedPid("413F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4140") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4140").size() > i)
						pidData.setP0140(SupportedPidUtill.getInstance().getSupportedPid("4140").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4141") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4141").size() > i)
						pidData.setP0141(SupportedPidUtill.getInstance().getSupportedPid("4141").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4142") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4142").size() > i)
						pidData.setP0142(SupportedPidUtill.getInstance().getSupportedPid("4142").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4143") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4143").size() > i)
						pidData.setP0143(SupportedPidUtill.getInstance().getSupportedPid("4143").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4144") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4144").size() > i)
						pidData.setP0144(SupportedPidUtill.getInstance().getSupportedPid("4144").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4145") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4145").size() > i)
						pidData.setP0145(SupportedPidUtill.getInstance().getSupportedPid("4145").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4146") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4146").size() > i)
						pidData.setP0146(SupportedPidUtill.getInstance().getSupportedPid("4146").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4147") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4147").size() > i)
						pidData.setP0147(SupportedPidUtill.getInstance().getSupportedPid("4147").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4148") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4148").size() > i)
						pidData.setP0148(SupportedPidUtill.getInstance().getSupportedPid("4148").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4149") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4149").size() > i)
						pidData.setP0149(SupportedPidUtill.getInstance().getSupportedPid("4149").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414A").size() > i)
						pidData.setP014A(SupportedPidUtill.getInstance().getSupportedPid("414A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414B").size() > i)
						pidData.setP014B(SupportedPidUtill.getInstance().getSupportedPid("414B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414C").size() > i)
						pidData.setP014C(SupportedPidUtill.getInstance().getSupportedPid("414C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414D").size() > i)
						pidData.setP014D(SupportedPidUtill.getInstance().getSupportedPid("414D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414E").size() > i)
						pidData.setP014E(SupportedPidUtill.getInstance().getSupportedPid("414E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("414F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("414F").size() > i)
						pidData.setP014F(SupportedPidUtill.getInstance().getSupportedPid("414F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4150") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4150").size() > i)
						pidData.setP0150(SupportedPidUtill.getInstance().getSupportedPid("4150").get(i).toString());
				}	
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4151") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4151").size() > i)
						pidData.setP0151(SupportedPidUtill.getInstance().getSupportedPid("4151").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4152") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4152").size() > i)
						pidData.setP0152(SupportedPidUtill.getInstance().getSupportedPid("4152").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4153") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4153").size() > i)
						pidData.setP0153(SupportedPidUtill.getInstance().getSupportedPid("4153").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4154") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4154").size() > i)
						pidData.setP0154(SupportedPidUtill.getInstance().getSupportedPid("4154").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4155") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4155").size() > i)
						pidData.setP0155(SupportedPidUtill.getInstance().getSupportedPid("4155").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4156") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4156").size() > i)
						pidData.setP0156(SupportedPidUtill.getInstance().getSupportedPid("4156").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4157") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4157").size() > i)
						pidData.setP0157(SupportedPidUtill.getInstance().getSupportedPid("4157").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4158") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4158").size() > i)
						pidData.setP0158(SupportedPidUtill.getInstance().getSupportedPid("4158").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4159") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4159").size() > i)
						pidData.setP0159(SupportedPidUtill.getInstance().getSupportedPid("4159").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415A").size() > i)
						pidData.setP015A(SupportedPidUtill.getInstance().getSupportedPid("415A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415B").size() > i)
						pidData.setP015B(SupportedPidUtill.getInstance().getSupportedPid("415B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415C").size() > i)
						pidData.setP015C(SupportedPidUtill.getInstance().getSupportedPid("415C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415D").size() > i)
						pidData.setP015D(SupportedPidUtill.getInstance().getSupportedPid("415D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415E").size() > i)
						pidData.setP015E(SupportedPidUtill.getInstance().getSupportedPid("415E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("415F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("415F").size() > i)
						pidData.setP015F(SupportedPidUtill.getInstance().getSupportedPid("415F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4160") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4160").size() > i)
						pidData.setP0160(SupportedPidUtill.getInstance().getSupportedPid("4160").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4161") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4161").size() > i)
						pidData.setP0161(SupportedPidUtill.getInstance().getSupportedPid("4161").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4162") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4162").size() > i)
						pidData.setP0162(SupportedPidUtill.getInstance().getSupportedPid("4162").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4163") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4163").size() > i)
						pidData.setP0163(SupportedPidUtill.getInstance().getSupportedPid("4163").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4164") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4164").size() > i)
						pidData.setP0164(SupportedPidUtill.getInstance().getSupportedPid("4164").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4165") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4165").size() > i)
						pidData.setP0165(SupportedPidUtill.getInstance().getSupportedPid("4165").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4166") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4166").size() > i)
						pidData.setP0166(SupportedPidUtill.getInstance().getSupportedPid("4166").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4167") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4167").size() > i)
						pidData.setP0167(SupportedPidUtill.getInstance().getSupportedPid("4167").get(i).toString()); 
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4168") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4168").size() > i)
						pidData.setP0168(SupportedPidUtill.getInstance().getSupportedPid("4168").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4169") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4169").size() > i)
						pidData.setP0169(SupportedPidUtill.getInstance().getSupportedPid("4169").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416A").size() > i)
						pidData.setP016A(SupportedPidUtill.getInstance().getSupportedPid("416A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416B").size() > i)
						pidData.setP016B(SupportedPidUtill.getInstance().getSupportedPid("416B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416C").size() > i)
						pidData.setP016C(SupportedPidUtill.getInstance().getSupportedPid("416C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416D").size() > i)
						pidData.setP016D(SupportedPidUtill.getInstance().getSupportedPid("416D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416E").size() > i)
						pidData.setP016E(SupportedPidUtill.getInstance().getSupportedPid("416E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("416F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("416F").size() > i)
						pidData.setP016F(SupportedPidUtill.getInstance().getSupportedPid("416F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4170") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4170").size() > i)
						pidData.setP0170(SupportedPidUtill.getInstance().getSupportedPid("4170").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4171") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4171").size() > i)
						pidData.setP0171(SupportedPidUtill.getInstance().getSupportedPid("4171").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4172") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4172").size() > i)
						pidData.setP0172(SupportedPidUtill.getInstance().getSupportedPid("4172").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4173") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4173").size() > i)
						pidData.setP0173(SupportedPidUtill.getInstance().getSupportedPid("4173").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4174") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4174").size() > i)
						pidData.setP0174(SupportedPidUtill.getInstance().getSupportedPid("4174").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4175") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4175").size() > i)
						pidData.setP0175(SupportedPidUtill.getInstance().getSupportedPid("4175").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4176") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4176").size() > i)
						pidData.setP0176(SupportedPidUtill.getInstance().getSupportedPid("4176").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4177") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4177").size() > i)
						pidData.setP0177(SupportedPidUtill.getInstance().getSupportedPid("4177").get(i).toString()); 
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4178") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4178").size() > i)
						pidData.setP0178(SupportedPidUtill.getInstance().getSupportedPid("4178").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4179") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4179").size() > i)
						pidData.setP0179(SupportedPidUtill.getInstance().getSupportedPid("4179").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417A") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417A").size() > i)
						pidData.setP017A(SupportedPidUtill.getInstance().getSupportedPid("417A").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417B") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417B").size() > i)
						pidData.setP017B(SupportedPidUtill.getInstance().getSupportedPid("417B").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417C") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417C").size() > i)
						pidData.setP017C(SupportedPidUtill.getInstance().getSupportedPid("417C").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417D") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417D").size() > i)
						pidData.setP017D(SupportedPidUtill.getInstance().getSupportedPid("417D").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417E") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417E").size() > i)
						pidData.setP017E(SupportedPidUtill.getInstance().getSupportedPid("417E").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("417F") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("417F").size() > i)
						pidData.setP017F(SupportedPidUtill.getInstance().getSupportedPid("417F").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4180") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4180").size() > i)
						pidData.setP0180(SupportedPidUtill.getInstance().getSupportedPid("4180").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4181") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4181").size() > i)
						pidData.setP0181(SupportedPidUtill.getInstance().getSupportedPid("4181").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4182") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4182").size() > i)
						pidData.setP0182(SupportedPidUtill.getInstance().getSupportedPid("4182").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4183") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4183").size() > i)
						pidData.setP0183(SupportedPidUtill.getInstance().getSupportedPid("4183").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4184") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4184").size() > i)
						pidData.setP0184(SupportedPidUtill.getInstance().getSupportedPid("4184").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4185") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4185").size() > i)
						pidData.setP0185(SupportedPidUtill.getInstance().getSupportedPid("4185").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4186") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4186").size() > i)
						pidData.setP0186(SupportedPidUtill.getInstance().getSupportedPid("4186").get(i).toString());
				}
				
				if(SupportedPidUtill.getInstance().getSupportedPid("4187") != null){
					if(SupportedPidUtill.getInstance().getSupportedPid("4187").size() > i)
						pidData.setP0187(SupportedPidUtill.getInstance().getSupportedPid("4187").get(i));
				}	
	
				arrpid.add(pidData);
			}
	
			final Pid pid = new Pid();
			pid.setPidData(arrpid);
	
			mJson = gson.toJson(pid);
			CarLLog.d(TAG, "json : \n" + mJson);
	
	
//			//	    http://emflant.tistory.com/47
//				    final Pid pid = new Pid();
//			//	    pid.setUserId("xxx");
//				    pid.setPidData(new PidData[] { pidData });
//			
//				    mJson = gson.toJson(pid);
//				    CarLLog.d(TAG, "json : \n" + mJson);
		}
	
		//	public void dataloop(int i){
		//		
		//		String value = String.valueOf(i);
		//		
		//		if(value.length() < 1){
		//			value = "0"+value;
		//		}
		//		
		//		if(SupportedPidUtill.getInstance(context).getSupportedPid(value).toString() != null){
		//			pidData.setP0187(SupportedPidUtill.getInstance(context).getSupportedPid(value).toString());
		//		}
		//	}
	
		public String strHexValue(int i){
			String value = Integer.toHexString(i);
	
			if(value.length() <= 1){
				value = "0"+value;
			}
	
			return value.toUpperCase();
		}
	
		public String getJson(){
			return mJson;
		}
	}
