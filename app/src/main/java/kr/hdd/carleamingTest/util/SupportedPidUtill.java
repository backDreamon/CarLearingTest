package kr.hdd.carleamingTest.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupportedPidUtill {

    private static SupportedPidUtill mInstance = null;
    private Map<String, ArrayList<String>> mMaps = new HashMap<String, ArrayList<String>>();

    public static SupportedPidUtill getInstance() {
        if (mInstance == null) {
            mInstance = new SupportedPidUtill();
        }
        return mInstance;
    }

    public void setSupportedPid(String mode, String supportedpid) {
//		Log.v("", "mode: "+ mode);

        ArrayList<String> lists = mMaps.get(mode);
        if (lists == null) lists = new ArrayList<String>();

        lists.add(supportedpid);
        mMaps.put(mode, lists);

    }

    public List<String> getSupportedPid(String mode) {
        if (mMaps == null) {
            return null;
        }

        return mMaps.get(mode);
    }

    public void setListsAllClear() {
        mMaps.clear();
    }

}
//Map<String, List<String>> mMaps = new HashMap<String, List<String>>();
//List<String> supportedPids = new ArrayList<String>();
//
//String[] pids = supportedPID.toString().split(" ");
//for(String pid : pids){
//	supportedPids.add(pid);
//}
//
//mMaps.put("01"/*Mode*/, supportedPids);
//List<String> pidss = mMaps.get("01");