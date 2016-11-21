package kr.hdd.carleamingTest.model;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.database.CommonUpdateAsycTask;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.SupportedPidUtill;
import kr.hdd.carleamingTest.util.Utils;

public class BluetoothReadData extends Service {

    private static final String TAG = BluetoothReadData.class.getSimpleName();

    public static int mBackValue = 0;
    private double mKmCount = 0;
    private double mELoad = 0;
    private double mOilWonTotal = 0;
    private double mSaveMilegae = MainApplication.getSava_mileage();
    private double mSaveOil = MainApplication.getFuel_consumption();
    private double mOilPriceTotal = MainApplication.getOil();
    private int mSaveTime = MainApplication.getDrive_time();

    //	private int SpeedTemp = 0;
    private int mTimeTemp = 0, SpeedTemp = 0, mCool = 0;

    private CommonUpdateAsycTask mAutoDBUpdateAsycTask = null; //DB 저장

    private Timer mTimer = null;
    private TimerTask mTimerTask = null;

    private BluetoothService mBluetoothService = null;
    private Handler mHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {
                case BluetoothService.STATE_CONNECTED:
                    MainApplication.IsBtFlag = true;

                    mTimerTask = new TimerTask() {

                        @Override
                        public void run() {
//						CarLLog.w(TAG, "mBluetoothService: "+ mBluetoothService);
                            mBackValue++;
                            MainApplication.TIME = String.valueOf(mBackValue);
                            SupportedPidUtill.getInstance().setSupportedPid("time", String.valueOf(mBackValue));

//						CarLLog.i(TAG,"mBackValue : "+mBackValue);
                            double hour = (double) 1 / (double) 3600;
                            SupportedPidUtill pidUtill = SupportedPidUtill.getInstance();
                            List<String> speedDatas = pidUtill.getSupportedPid("410D");
                            if (speedDatas != null && speedDatas.size() > 0) {
                                int kmc = Integer.parseInt(speedDatas.get(speedDatas.size() - 1), 16);
//							CarLLog.w(TAG, "kmc: "+ kmc);
                                mKmCount += ((double) kmc) * hour;
                                MainApplication.setMileage(mKmCount);
                                MainApplication.setSava_mileage(mSaveMilegae + mKmCount);
//							CarLLog.i(TAG, "555 mKmCount: "+ mKmCount);
//							CarLLog.i(TAG, "555 getSava_mileage: "+ MainApplication.getSava_mileage());

                                MainApplication.setEngine_oil(MainApplication.getEngine_oil() + ((double) kmc) * hour);
                                MainApplication.setTire(MainApplication.getTire() + ((double) kmc) * hour);
                                MainApplication.setBrake_lining(MainApplication.getBrake_lining() + ((double) kmc) * hour);
                                MainApplication.setAircon_filter(MainApplication.getAircon_filter() + ((double) kmc) * hour);
                                MainApplication.setWiper(MainApplication.getWiper() + ((double) kmc) * hour);
                                MainApplication.setEquip_coolant(MainApplication.getEquip_coolant() + ((double) kmc) * hour);
                                MainApplication.setTransmission_oil(MainApplication.getTransmission_oil() + ((double) kmc) * hour);
                                MainApplication.setBattery(MainApplication.getBattery() + ((double) kmc) * hour);

                                int SpeedUpInteger = SpeedTemp + 3;
                                int SpeedDownInteger = SpeedTemp - 3;

                                if (mTimeTemp != BluetoothReadData.mBackValue) {

                                    if (kmc >= SpeedUpInteger) {
                                        MainApplication.setDrive_up_speed();
                                    } else if (kmc <= SpeedDownInteger) {
                                        MainApplication.setDrive_down_speed();
                                    } else if (MainApplication.RPM_TEMP <= 900 && kmc == 0) {
                                        MainApplication.setIdle();
                                    } else {
                                        MainApplication.setDrive();
                                    }
//								CarLLog.w(TAG, "33333 getDrive_down_speed: " + MainApplication.getDrive_down_speed());
                                }
                                SpeedTemp = kmc;
                                MainApplication.setDrive_time(mSaveTime + mBackValue);
                            }

                            List<String> ELoadDatas = pidUtill.getSupportedPid("4104");
                            if (ELoadDatas != null && ELoadDatas.size() > 0) {
//							double eLoad = Integer.parseInt(ELoadDatas.get(ELoadDatas.size()-1), 16)* 100.0/ 255.0* hour;
//							int eLoad = Integer.parseInt(ELoadDatas.get(ELoadDatas.size()-1), 16);

                                int mTempCool = (int) MainApplication.getAuto_coolant();
                                int Enginedisplacement = 1;
                                int RpmTemp = MainApplication.RPM_TEMP;
                                double calcLoad = Integer.parseInt(ELoadDatas.get(ELoadDatas.size() - 1), 16) * 100. / 255.;
                                double avg = 0;
//							CarLLog.i(TAG, "555 calcLoad: "+ calcLoad);

                                if (MainApplication.getOil_type().equalsIgnoreCase(MainApplication.OIL_GASOL)) {
                                    if (mTempCool <= 55) {
                                        avg = 0.001 * 0.004 * 4 * 1.35 * Enginedisplacement * RpmTemp * 60 * calcLoad / 20;
//									avgconsumption.add((0.001*0.004*4*1.35*Enginedisplacement*rpmval*60*calcLoad/20));
                                    } else if (mTempCool > 55) {
                                        avg = 0.001 * 0.003 * 4 * 1.35 * Enginedisplacement * RpmTemp * 60 * calcLoad / 20;
//									avgconsumption.add((0.001*0.003*4*1.35*Enginedisplacement*rpmval*60*calcLoad/20));
                                    }
                                } else// if(Enginetype==1)
                                {
                                    if (mTempCool <= 55) {
                                        avg = 0.001 * 0.004 * 4 * Enginedisplacement * RpmTemp * 60 * calcLoad / 20;
//									avgconsumption.add((0.001*0.004*4*Enginedisplacement*rpmval*60*calcLoad/20));
                                    } else if (mTempCool > 55) {
                                        avg = 0.001 * 0.003 * 4 * Enginedisplacement * RpmTemp * 60 * calcLoad / 20;
//									avgconsumption.add((0.001*0.003*4*Enginedisplacement*rpmval*60*calcLoad/20));
                                    }
                                }
//							CarLLog.i(TAG, "555 avg: "+ avg);
//							CarLLog.i(TAG, "555 eLoad: "+ eLoad);
//							mELoad += (soil* 0.02* 4)* 1000./ 3600.* 5.;
                                mELoad += avg * hour;
                                MainApplication.setFuel_consumption(mSaveOil + mELoad);
//							CarLLog.w(TAG, "555 getOil_type: "+ MainApplication.getOil_type());
//							CarLLog.i(TAG, "555 getFuel_consumption: "+ MainApplication.getFuel_consumption());

                                double tOilWon = avg * Utils.getPrice(MainApplication.getOil_type()) * hour;
                                mOilWonTotal += tOilWon;
//							CarLLog.w(TAG, "555 tOilWon: "+ tOilWon);
//							CarLLog.w(TAG, "555 mOilWonTotal: "+ mOilWonTotal);
                                MainApplication.setOil(mOilPriceTotal + mOilWonTotal);
//							MainApplication.setAuto_time((int) mOilWonTotal);

                            }
                        }
                    };

                    mTimer = new Timer();
                    mTimer.schedule(mTimerTask, 1000, 1000);

                    Toast.makeText(getApplicationContext(), "연결 되었습니다.", Toast.LENGTH_SHORT).show();
                    break;

                case BluetoothService.STATE_LISTEN:
                    Toast.makeText(getApplicationContext(), "연결 끊김.", Toast.LENGTH_SHORT).show();

//				CarLLog.e(TAG, "444 mBluetoothService: "+ mBluetoothService);
                    if (mBluetoothService != null)
                        ServiceStop();

                    break;
                case BluetoothService.VIEW_REFLUSH:  //View 갱신
//				CarLLog.w(TAG, "BluetoothService.VIEW_REFLUSH");

                    Bundle bundle = msg.getData();
                    String dataId = bundle.getString("RESULT_TYPE");
                    String data = bundle.getString("RESULT_VALUE");
                    if (dataId != null && !dataId.equals("")) {
                        Intent broadcastIntent = new Intent(MainApplication.RECIVER.RECIVER);
                        broadcastIntent.putExtra(MainApplication.RECIVER.DATAID, dataId);
                        broadcastIntent.putExtra(MainApplication.RECIVER.DATA, data);
                        broadcastIntent.putExtra(MainApplication.RECIVER.DRIVE_TIME_RECIVER, mBackValue + "");
                        broadcastIntent.putExtra(MainApplication.RECIVER.KM_RECIVER, mKmCount);
                        broadcastIntent.putExtra(MainApplication.RECIVER.TOTAL_OIL, mELoad);
                        broadcastIntent.putExtra(MainApplication.RECIVER.OIL_PRICE, mOilWonTotal);
                        sendBroadcast(broadcastIntent);
                    }
                    break;
            }

            return false;
        }
    });

    private void ServiceStop() {
        mBluetoothService.stop();
        mBluetoothService = null;
        CarLLog.e(TAG, "444 ServiceStop(): " + mBluetoothService);

        this.stopSelf();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        CarLLog.d(TAG, "service onCreate");
        mBluetoothService = new BluetoothService(mHandler);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent != null) {
            Intent result = (Intent) intent.getExtras().get("intentdata");
            if (result != null) mBluetoothService.getDeviceInfo(result);
        }

        return START_STICKY;
    }


    /**
     * Sends a message.
     *
     * @param message A string of text to send.
     */
    private void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mBluetoothService.getState() != BluetoothService.STATE_CONNECTED) {
            Toast.makeText(this, "R.string.not_connected", Toast.LENGTH_LONG).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            message = message + "\r";
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mBluetoothService.write(send);

            // Reset out string buffer to zero and clear the edit text field
//            mOutStringBuffer.setLength(0);
//            mOutEditText.setText(mOutStringBuffer);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CarLLog.d(TAG, "service onDestroy");

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mBluetoothService != null)
            ServiceStop();
//			mBluetoothService.stop();

        MainApplication.IsBtFlag = false;
        mBackValue = 0;

//			int DBTime = MainApplication.getAuto_time();
//			MainApplication.setAuto_time(DBTime + mBackValue);
//			mSaveMilegae = MainApplication.getSava_mileage();
//			CarLLog.i(TAG, "getDrive_down_speed: "+ MainApplication.getDrive_down_speed());

        mAutoDBUpdateAsycTask = new CommonUpdateAsycTask(getApplicationContext(), MainApplication.getmUserId(), MainApplication.getmUserCarName(),
                MainApplication.getEngine_oil(), MainApplication.getTire(), MainApplication.getCar_vehicle_number(),
                MainApplication.getCar_number(), MainApplication.getCar_model(), MainApplication.getOil_type(),
                MainApplication.getMileage(), MainApplication.getAuto_time(), MainApplication.getOil(),
                MainApplication.getAuto_coolant(), MainApplication.getFuel_consumption(), MainApplication.getStart_date(),
                MainApplication.getFuel_efficienct(), MainApplication.getDrive_time(), MainApplication.getDrive_down_speed(),
                MainApplication.getDrive_up_speed(), MainApplication.getIdle(), MainApplication.getDrive(), MainApplication.getBrake_lining(),
                MainApplication.getAircon_filter(), MainApplication.getWiper(), MainApplication.getEquip_coolant(), MainApplication.getTransmission_oil(),
                MainApplication.getBattery(), Utils.Date(), MainApplication.getEngine_oil_day(), MainApplication.getTire_day(), MainApplication.getBrake_lining_day(),
                MainApplication.getAircon_filter_day(), MainApplication.getWiper_day(), MainApplication.getEquip_coolant_day(), MainApplication.getTransmission_oil_day(),
                MainApplication.getBattery_day(), MainApplication.getSava_mileage());
        mAutoDBUpdateAsycTask.execute();

        Toast.makeText(getApplicationContext(), "연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();

        MainApplication.isConnectBT = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        CarLLog.d(TAG, "onBind");
        return null;
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainApplication.RECIVER.RECIVER);
        intentFilter.addAction(MainApplication.RECIVER.RPM_RECIVER);
        intentFilter.addAction(MainApplication.RECIVER.SPEED_RECIVER);
        intentFilter.addAction(MainApplication.RECIVER.DRIVE_TIME_RECIVER);
        intentFilter.addAction(MainApplication.RECIVER.ENGINE_LOAD_RECIVER);
        intentFilter.addAction(MainApplication.RECIVER.KM_RECIVER);
        return intentFilter;
    }
}
