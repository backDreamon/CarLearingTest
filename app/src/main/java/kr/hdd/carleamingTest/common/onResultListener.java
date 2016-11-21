package kr.hdd.carleamingTest.common;

import android.content.Intent;

public interface onResultListener {
	void onSendResult(int requestCode, int resultCode, Intent data);
}
