package kr.hdd.carleamingTest.model;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.util.CarLLog;

import static kr.hdd.carleamingTest.MainActivity.mBtnBluetooth;

/**
 * Created by back on 2016-12-06.
 */

public class BTBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = BTBroadcastReceiver.class.getSimpleName();

    static int connect = 0;
    static int disConnect = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        CarLLog.e(TAG, "BT On Receive()");

        String action = intent.getAction();

        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    mBtnBluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.main_btn_bluetooth_off));
                    CarLLog.e(TAG, "ACTION_STATE_CHANGED -> STATE OFF" );
                    break;
                case BluetoothAdapter.STATE_ON:
                    mBtnBluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.main_btn_bluetooth_off));
                    CarLLog.e(TAG, "ACTION_STATE_CHANGED -> STATE ON");
                    break;
            }

        } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            connect++;
            //Toast.makeText(context, "Bluetooth Device Connected", Toast.LENGTH_LONG).show();
            CarLLog.e(TAG, "ACTION_ACL_CONNECTED : " + connect);
            mBtnBluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.main_btn_bluetooth_on));

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            disConnect++;
            //Toast.makeText(context, "Bluetooth Device Disconnected", Toast.LENGTH_LONG).show();
            CarLLog.e(TAG, "ACTION_ACL_DISCONNECTED : " + disConnect);
            mBtnBluetooth.setBackground(ContextCompat.getDrawable(context, R.drawable.main_btn_bluetooth));
        }
    }
}
