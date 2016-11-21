package kr.hdd.carleamingTest.util;

import android.content.Context;

public class ModeOneSendMessageUtill {
	
	private static ModeOneSendMessageUtill mInstance = null;
	
	public static ModeOneSendMessageUtill getInstance(Context context){
		if(mInstance == null){
			mInstance = new ModeOneSendMessageUtill();
		}
		return mInstance;
	}
	
	public String setPidVauleSend(String pid, boolean flag){
		if(flag){
			return pid;
		} else {
			return "01"+pid;
		}
	}
	
	
	public String setPidRealTime(int btindex){
			
			String pid = null;
			
			switch (btindex) {
			case 0 :
				pid = "ATZ";
				break;
			case 1 :
				pid = "0100";
				break;
			case 2 :
				pid = "0120";
				break;
			case 3 :
				pid = "0140";
				break;
			case 4 :
				pid = "0160";
				break;
			case 5 :
				pid = "0180";
				break;
			case 6 :
				pid = "0120";
				break;
			case 7 :
				pid = "0101";
				break;
			case 8 :
				pid = "0105";
				break;
			case 9 :
				pid = "010D";
				break;
			case 10 :
				pid = "0104";
				break;
			case 11 :
				pid = "010C";
				break;
			case 12 :
				pid = "015E";
				break;
			default :
				pid = "0101";
				break;
				
			}
				
				return pid;
		}
	
	public String setPidSend(int btindex){
		
		String pid = null;
		
		switch (btindex) {
		case 0 :
			pid = "ATZ";
			break;
		case 1 :
			pid = "0100";
			break;
		case 2 :
			pid = "0120";
			break;
		case 3 :
			pid = "0140";
			break;
		case 4 :
			pid = "0160";
			break;
		case 5 :
			pid = "0180";
			break;
		case 6 :
			pid = "0120";
			break;
		case 7 :
			pid = "0101";
			break;
		case 8 :
			pid = "0121";
			break;
		case 9 :
			pid = "0131";
			break;
		case 10 :
			pid = "0104";
			break;
		case 11 :
			pid = "0105";
			break;
		case 12 :
			pid = "010F";
			break;
		case 13 :
			pid = "0107";
			break;
		case 14 :
			pid = "0108";
			break;
		case 15 :
			pid = "0109";
			break;
		case 16 :
			pid = "010A";
			break;
		case 17 :
			pid = "010B";
			break;
		case 18 :
			pid = "010C";
			break;
		case 19 :
			pid = "010D";
			break;
		case 20 :
			pid = "010E";
			break;
		case 21 :
			pid = "015E";
			break;
		case 22 :
			pid = "0110";
			break;
		case 23 :
			pid = "0111";
			break;
		case 24 :
			pid = "0112";
			break;
		case 25 :
			pid = "0113";
			break;
		case 26 :
			pid = "0114";
			break;
		case 27 :
			pid = "0115";
			break;
		case 28 :
			pid = "0116";
			break;
		case 29 :
			pid = "0117";
			break;
		case 30 :
			pid = "0118";
			break;
		case 31 :
			pid = "0119";
			break;
		case 32 :
			pid = "011A";
			break;	
		case 33 :
			pid = "011B";
			break;
		case 34 :
			pid = "011C";
			break;
		case 35 :
			pid = "011D";
			break;
		case 36 :
			pid = "011E";
			break;
		case 37 :
			pid = "011F";
			break;
		case 38 :
			pid = "0102";
			break;
		case 39 :
			pid = "0122";
			break;
		case 40 :
			pid = "0123";
			break;
		case 41 :
			pid = "0124";
			break;
		case 42 :
			pid = "0125";
			break;
		case 43 :
			pid = "0126";
			break;
		case 44 :
			pid = "0127";
			break;
		case 45 :
			pid = "0128";
			break;
		case 46 :
			pid = "0129";
			break;
		case 47 :
			pid = "012A";
			break;
		case 48 :
			pid = "012B";
			break;
		case 49 :
			pid = "012C";
			break;
		case 50 :
			pid = "012D";
			break;
		case 51 :
			pid = "012E";
			break;
		case 52 :
			pid = "012F";
			break;
		case 53 :
			pid = "012F";
			break;
		case 54 :
			pid = "0130";
			break;
		case 55 :
			pid = "0103";
			break;
		case 56 :
			pid = "0132";
			break;
		case 57 :
			pid = "0133";
			break;
		case 58 :
			pid = "0134";
			break;
		case 59 :
			pid = "0135";
			break;
		case 60 :
			pid = "0136";
			break;
		case 61 :
			pid = "0137";
			break;
		case 62 :
			pid = "0138";
			break;
		case 63 :
			pid = "0139";
			break;
		case 64 :
			pid = "013A";
			break;
		case 65 :
			pid = "013B";
			break;
		case 66 :
			pid = "013C";
			break;
		case 67 :
			pid = "013D";
			break;
		case 68 :
			pid = "013E";
			break;
		case 69 :
			pid = "013F";
			break;
		case 70 :
			pid = "0141";
			break;
		case 71 :
			pid = "0142";
			break;
		case 72 :
			pid = "0143";
			break;
		case 73 :
			pid = "0144";
			break;
		case 74 :
			pid = "0145";
			break;
		case 75 :
			pid = "0146";
			break;
		case 76 :
			pid = "0147";
			break;
		case 77 :
			pid = "0148";
			break;
		case 78 :
			pid = "0149";
			break;
		case 79 :
			pid = "014A";
			break;
		case 80 :
			pid = "014B";
			break;
		case 81 :
			pid = "014C";
			break;
		case 82 :
			pid = "014D";
			break;	
		case 83 :
			pid = "014E";
			break;
		case 84 :
			pid = "014F";
			break;
		case 85 :
			pid = "0150";
			break;
		case 86 :
			pid = "0151";
			break;
		case 87 :
			pid = "0152";
			break;
		case 88 :
			pid = "0153";
			break;
		case 89 :
			pid = "0154";
			break;
		case 90 :
			pid = "0155";
			break;
		case 91 :
			pid = "0156";
			break;
		case 92 :
			pid = "0157";
			break;
		case 93 :
			pid = "0158";
			break;
		case 94 :
			pid = "0159";
			break;
		case 95 :
			pid = "015A";
			break;
		case 96 :
			pid = "015B";
			break;
		case 97 :
			pid = "015C";
			break;
		case 98 :
			pid = "015D";
			break;
		case 99 :
			pid = "0106";
			break;
		case 100 :
			pid = "015F";
			break;
		case 101 :
			pid = "0161";
			break;
		case 102 :
			pid = "0162";
			break;
		case 103 :
			pid = "0163";
			break;
		case 104 :
			pid = "0164";
			break;
		case 105 :
			pid = "0165";
			break;
		case 106 :
			pid = "0166";
			break;
		case 107 :
			pid = "0167";
			break;
		case 108 :
			pid = "0168";
			break;
		case 109 :
			pid = "0169";
			break;
		case 110 :
			pid = "016A";
			break;
		case 111 :
			pid = "016B";
			break;
		case 112 :
			pid = "016C";
			break;
		case 113 :
			pid = "016D";
			break;
		case 114 :
			pid = "016E";
			break;
		case 115 :
			pid = "016F";
			break;
		case 116 :
			pid = "0170";
			break;
		case 117 :
			pid = "0171";
			break;
		case 118 :
			pid = "0172";
			break;
		case 119 :
			pid = "0173";
			break;
		case 120 :
			pid = "0174";
			break;
		case 121 :
			pid = "0175";
			break;
		case 122 :
			pid = "0176";
			break;
		case 123 :
			pid = "0177";
			break;
		case 124 :
			pid = "0178";
			break;
		case 125 :
			pid = "0179";
			break;
		case 126 :
			pid = "017A";
			break;
		case 127 :
			pid = "017B";
			break;
		case 128 :
			pid = "017C";
			break;
		case 129 :
			pid = "017D";
			break;
		case 130 :
			pid = "017E";
			break;
		case 131 :
			pid = "017F";
			break;
		case 132 :
			pid = "0181";
			break;
		case 133 :
			pid = "0182";
			break;
		case 134 :
			pid = "0183";
			break;
		case 135 :
			pid = "0184";
			break;
		case 136 :
			pid = "0185";
			break;
		case 137 :
			pid = "0186";
			break;
		case 138 :
			pid = "0187";
			break;
		default :
			pid = "03";
			break;
			
		}
			
			return pid;
	}

}
