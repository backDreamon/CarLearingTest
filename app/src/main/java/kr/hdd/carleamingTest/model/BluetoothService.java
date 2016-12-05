package kr.hdd.carleamingTest.model;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import kr.hdd.carleamingTest.model.OBD_Manager.OBD_TimeOut;
import kr.hdd.carleamingTest.util.CarLLog;

public class BluetoothService implements OBD_TimeOut {
    // Debugging
    private static final String TAG = "BluetoothService";

    // Intent request code
    public static final int REQUEST_CONNECT_DEVICE = 1;
    public static final int REQUEST_ENABLE_BT = 2;

    // RFCOMM Protocol
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothAdapter btAdapter;

    private Activity mActivity;
    private Handler mHandler;

    private ConnectThread mConnectThread; // 변수명 다시
    private ConnectedThread mConnectedThread; // 변수명 다시

    private OBD_Manager mObd_Manager = null;

    public String address;
    private int mState;
    // 상태를 나타내는 상태 변수
    public static final int STATE_NONE = 0; // we're doing nothing
    public static final int STATE_LISTEN = 1; // now listening for incoming connections
    public static final int STATE_CONNECTING = 2; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 3; // now connected to a remote device
    public static final int VIEW_REFLUSH = 4;



    // Constructors
    public BluetoothService() {
        mState = STATE_NONE;

        // BluetoothAdapter 얻기
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothService(Handler handler) {
        this();
        mHandler = handler;
        mObd_Manager = new OBD_Manager();
        mObd_Manager.setObdTimeOutLinstener(BluetoothService.this);
    }

    public BluetoothService(Activity activity, Handler handler) {
        this(handler);
        mActivity = activity;
    }

    /**
     * Check the Bluetooth support
     *
     * @return boolean
     */
    public boolean getDeviceState() {
        CarLLog.i(TAG, "Check the Bluetooth support");

        if (btAdapter == null) {
            CarLLog.d(TAG, "Bluetooth is not available");

            return false;

        } else {
            CarLLog.d(TAG, "Bluetooth is available");

            return true;
        }
    }



    /**
     * after scanning and get device info
     *
     * @param data
     */
    public void getDeviceInfo(Intent data) {
        // Get the device MAC address
        address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        // BluetoothDevice device = btAdapter.getRemoteDevice(address);
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        CarLLog.d(TAG, "Get Device Info \n" + "address : " + address);

        connect(device);
    }

    // Bluetooth 상태 set
    private synchronized void setState(int state) {
        CarLLog.d(TAG, "setState() " + mState + " -> " + state);
        mState = state;
        if (mHandler != null)
            mHandler.sendEmptyMessage(state);
    }

    // Bluetooth 상태 get
    public synchronized int getState() {
        return mState;
    }

    public synchronized void start() {
        CarLLog.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread == null) {

        } else {
            mConnectThread.cancel();
            mConnectThread.interrupt();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread == null) {

        } else {
            mConnectedThread.cancel();
            mConnectedThread.interrupt();
            mConnectedThread = null;
        }
    }

    // ConnectThread 초기화 device의 모든 연결 제거
    public synchronized void connect(BluetoothDevice device) {
        CarLLog.d(TAG, "connect to: " + device);

        // Cancel any thread attempting to make a connection
        if (mState == STATE_CONNECTING) {
            if (mConnectThread == null) {

            } else {
                mConnectThread.cancel();
                mConnectThread.interrupt();
                mConnectThread = null;
            }
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread == null) {

        } else {
            mConnectedThread.cancel();
            mConnectedThread.interrupt();
            mConnectedThread = null;
        }

        // Start the thread to connect with the given device
        mConnectThread = new ConnectThread(device);

        mConnectThread.start();
        setState(STATE_CONNECTING);
    }

    // ConnectedThread 초기화
    public synchronized void connected(BluetoothSocket socket,
                                       BluetoothDevice device) {
        CarLLog.d(TAG, "connected");

        // Cancel the thread that completed the connection
        if (mConnectThread == null) {

        } else {
            mConnectThread.cancel();
            mConnectThread.interrupt();
            mConnectThread = null;
        }

        // Cancel any thread currently running a connection
        if (mConnectedThread == null) {

        } else {
            mConnectedThread.cancel();
            mConnectedThread.interrupt();
            mConnectedThread = null;
        }

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();

        setState(STATE_CONNECTED);
    }

    // 모든 thread stop
    public synchronized void stop() {
        CarLLog.d(TAG, "stop");

        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread.interrupt();
            mConnectThread = null;
        }

        if (mConnectedThread != null) {
            mConnectedThread.cancel();
            mConnectedThread.interrupt();
            mConnectedThread = null;
        }

        if (mObd_Manager != null) {
            mObd_Manager.cancel();
        }

        setState(STATE_NONE);
    }

    public void sendRequest() {
        String pid = mObd_Manager.getRequestPID();
        if (pid != null && !pid.equals("")) {
//    		if(pid.equalsIgnoreCase("03"))
//    			CarLLog.i(TAG, "KCH REQ:"+pid);
            this.write(pid.getBytes());
        }
    }

    @Override
    public void obdTimeOut() {
        BluetoothService.this.sendRequest();
    }

    // 값을 쓰는 부분(보내는 부분)
    public void write(byte[] out) { // Create temporary object
        ConnectedThread r; // Synchronize a copy of the ConnectedThread
        synchronized (this) {
            if (mState != STATE_CONNECTED)
                return;
            r = mConnectedThread;
        } // Perform the write unsynchronized r.write(out); }
        r.write(out);
    }

    // 연결 실패했을때
    private void connectionFailed() {
        setState(STATE_LISTEN);
    }

    // 연결을 잃었을 때
    private void connectionLost() {
//		CarLLog.e(TAG, "444 connectionLost()");
        setState(STATE_LISTEN);
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;

			/*
             * / // Get a BluetoothSocket to connect with the given
			 * BluetoothDevice try { // MY_UUID is the app's UUID string, also
			 * used by the server // code tmp =
			 * device.createRfcommSocketToServiceRecord(MY_UUID);
			 * 
			 * try { Method m = device.getClass().getMethod(
			 * "createInsecureRfcommSocket", new Class[] { int.class }); try {
			 * tmp = (BluetoothSocket) m.invoke(device, 15); } catch
			 * (IllegalArgumentException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } catch (IllegalAccessException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch
			 * (InvocationTargetException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 * 
			 * } catch (NoSuchMethodException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); } } catch (IOException e) { } /
			 */

            // 디바이스 정보를 얻어서 BluetoothSocket 생성
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);

            } catch (IOException e) {
                CarLLog.e(TAG, "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            CarLLog.i(TAG, "BEGIN mConnectThread");
            setName("ConnectThread");

            // 연결을 시도하기 전에는 항상 기기 검색을 중지한다.
            // 기기 검색이 계속되면 연결속도가 느려지기 때문이다.
            btAdapter.cancelDiscovery();

            // BluetoothSocket 연결 시도
            try {
                // BluetoothSocket 연결 시도에 대한 return 값은 succes 또는 exception이다.
                mmSocket.connect();
                CarLLog.d(TAG, "Connect Success");

            } catch (IOException e) {
                connectionFailed(); // 연결 실패시 불러오는 메소드
                CarLLog.d(TAG, "Connect Fail");

                // socket을 닫는다.
                try {
                    mmSocket.close();
                } catch (IOException e2) {
                    CarLLog.e(TAG, "unable to close() socket during connection failure", e2);
                }
                // 연결중? 혹은 연결 대기상태인 메소드를 호출한다.
                BluetoothService.this.start();
                return;
            }

            // ConnectThread 클래스를 reset한다.
            synchronized (BluetoothService.this) {
                mConnectThread = null;
            }

            // ConnectThread를 시작한다.
            connected(mmSocket, mmDevice);
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                CarLLog.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        private boolean isStoped = false;

        public ConnectedThread(BluetoothSocket socket) {
            CarLLog.d(TAG, "create ConnectedThread");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // BluetoothSocket의 inputstream 과 outputstream을 얻는다.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                CarLLog.e(TAG, "temp sockets not created", e);
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            CarLLog.i(TAG, "BEGIN mConnectedThread");
            BluetoothService.this.sendRequest();

            String readData = "";
            // Keep listening to the InputStream while connected
            while (!isStoped()) {
                try {
                    byte[] buffer = new byte[1024];
                    // InputStream으로부터 값을 받는 읽는 부분(값을 받는다)
                    int bytes = mmInStream.read(buffer, 0, buffer.length);

                    String s = new String(buffer, 0, bytes);
                    for (int i = 0; i < s.length(); i++) {
                        char x = s.charAt(i);
                        readData = readData + x;
                        if (x == '>'/*x == 0x3e*/) {
                            boolean isSupportPID = mObd_Manager.getOBDConverSupportData(readData);
                            if (!isSupportPID) mObd_Manager.getOBDConvertData(readData);

                            String resultType = mObd_Manager.getResultType();
                            int resultValue = mObd_Manager.getResultValue();

                            if (resultType != null && !resultType.equals("") && resultValue > -1) {
                                Message message = mHandler.obtainMessage();
                                message.what = VIEW_REFLUSH;
                                Bundle bundle = message.getData();
                                bundle.putString("RESULT_TYPE", resultType);
                                bundle.putString("RESULT_VALUE", resultValue + "");
                                message.setData(bundle);
                                mHandler.sendMessage(message);
                            }

                            BluetoothService.this.sendRequest();
                            readData = "";
                        }
                    }

                } catch (IOException e) {
                    CarLLog.e(TAG, "disconnected", e);
                    connectionLost();
                    break;
                }
            }
        }

        /**
         * Write to the connected OutStream.
         *
         * @param buffer The bytes to write
         */
        public void write(byte[] buffer) {
            try {
                // 값을 쓰는 부분(값을 보낸다)
                mmOutStream.write(buffer);

            } catch (IOException e) {
                CarLLog.e(TAG, "Exception during write", e);
            }
        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                CarLLog.e(TAG, "close() of connect socket failed", e);
            } finally {
                setStoped(true);
            }
        }

        public boolean isStoped() {
            return isStoped;
        }

        public void setStoped(boolean isStoped) {
            this.isStoped = isStoped;
        }

    }

}