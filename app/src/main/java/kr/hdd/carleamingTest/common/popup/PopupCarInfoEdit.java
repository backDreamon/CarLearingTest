package kr.hdd.carleamingTest.common.popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.database.AllDataDBsetting;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.Utils;

public class PopupCarInfoEdit extends Dialog implements OnClickListener {

    private static final String TAG = "PopupCarInfoEdit";

    private Context mContext = null;

    private EditText mCarName = null;
    private EditText mVehicleNumber = null;
    private EditText mCarNumber = null;
    private EditText mCarModel = null;

    private Spinner mSpinner = null;
    private ArrayAdapter adapter = null;

    private Button mBtnOk = null;
    private Button mBtnCancel = null;

    private AllDataContentData mDBData = null;

    private String mSpinnerValue = null;

    private Handler mHandler = null;

//	private AutoDBAsycTask mAllDataDBAsycTask = null;
//	private CommonUpdateAsycTask mAutoDBUpdateAsycTask = null;

    private boolean mOilTypeFlag = true;
    private boolean mTextTypeFlag = true;

    public PopupCarInfoEdit(Context context, Handler handler) {
        super(context);
//		setContentView(R.layout.car_info_edit_dialog);
        mContext = context;
        mHandler = handler;
        mDBData = new AllDataContentData();

//		mAllDataDBAsycTask = new AutoDBAsycTask(mContext, mStopAsyncTaskHandler);
//		mAllDataDBAsycTask.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.car_info_edit_dialog);
        getWindow().setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT, android.view.WindowManager.LayoutParams.MATCH_PARENT);

        mCarName = (EditText) findViewById(R.id.edit_car_name);  //차량 이름
        mVehicleNumber = (EditText) findViewById(R.id.edit_vehicle_number); //차대번호
        mCarNumber = (EditText) findViewById(R.id.edit_car_number); //차량번호
        mCarModel = (EditText) findViewById(R.id.edit_car_model);  //연식

        mBtnOk = (Button) findViewById(R.id.btn_ok);
        mBtnCancel = (Button) findViewById(R.id.btn_cancel);

        mBtnOk.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mSpinner = (Spinner) findViewById(R.id.edit_spinner);  //유종
        mSpinner.setOnItemSelectedListener(itemlistener);
        adapter = ArrayAdapter.createFromResource(mContext, R.array.arrays_oiltype, android.R.layout.simple_selectable_list_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mUiHandler.sendEmptyMessage(0);

    }

    TextWatcher textwatcherlistener = new TextWatcher() {

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() > 10) {
                AlertDialog.Builder gsDialog = new AlertDialog.Builder(mContext);
                gsDialog.setTitle("알림");
                gsDialog.setMessage("더이상 입력 할 수 없습니다.");
                gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String string = s.toString();
                        string.substring(0, s.length() - 1);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    TextWatcher textwatcherlenght6listener = new TextWatcher() {

        @Override
        public void onTextChanged(final CharSequence s, int start, int before, int count) {
            if (s.length() > 6) {
                AlertDialog.Builder gsDialog = new AlertDialog.Builder(mContext);
                gsDialog.setTitle("알림");
                gsDialog.setMessage("더이상 입력 할 수 없습니다.");
                gsDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String string = s.toString();
                        string.substring(0, s.length() - 1);
                        dialog.dismiss();
                    }
                }).create().show();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private OnItemSelectedListener itemlistener = new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {

            mSpinnerValue = mSpinner.getSelectedItem().toString();
            CarLLog.v(TAG, "SpinnerValue : " + mSpinnerValue);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub

        }
    };

//	public String getSpinnerValue(){
//		return mSpinnerValue;
//	}

    private final Handler mUiHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (MainApplication.getmUserCarName() != null) {
                mCarName.setText(MainApplication.getmUserCarName());

            }
            if (MainApplication.getCar_vehicle_number() != null) {
                mVehicleNumber.setText(MainApplication.getCar_vehicle_number());

            }
            if (MainApplication.getCar_number() != null) {
                mCarNumber.setText(MainApplication.getCar_number());

            }
            if (MainApplication.getCar_model() >= 0) {
                mCarModel.setText(MainApplication.getCar_model() + "");

            }

            mSpinner.setAdapter(adapter);

            String oilType = MainApplication.getOil_type();

            if (oilType != null) {
                switch (oilType) {
                    case MainApplication.OIL_DISEL:
                        mSpinner.setSelection(0);
                        break;
                    case MainApplication.OIL_GASOL:
                        mSpinner.setSelection(1);
                        break;
                    case MainApplication.OIL_LPG:
                        mSpinner.setSelection(2);
                        break;
                    default:
                        break;
                }

					/*if(MainApplication.getOil_type().equals(MainApplication.OIL_DISEL)){
                        mSpinner.setSelection(1);
					} else{
						mSpinner.setSelection(0);
					}*/
            }

//				mSpinner.setOnItemSelectedListener(itemlistener);

            if (mTextTypeFlag) {

                mCarName.addTextChangedListener(textwatcherlistener);
                mVehicleNumber.addTextChangedListener(textwatcherlistener);
                mCarNumber.addTextChangedListener(textwatcherlistener);
                mCarModel.addTextChangedListener(textwatcherlenght6listener);
            }

            mTextTypeFlag = true;
            return false;
        }
    });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                mTextTypeFlag = false;

                if (mCarName.getText().toString().length() == 0) {

                } else if (mVehicleNumber.getText().toString().length() == 0) {

                } else if (mCarNumber.getText().toString().length() == 0) {

                } else if (mCarModel.getText().toString().length() == 0) {

                }

                if (mSpinnerValue == null) {

//				if(MainApplication.getOil_type() == null){
//					MainApplication.setOil_type(MainApplication.OIL_GASOL);
//				}

                    mSpinnerValue = MainApplication.getOil_type();
                }
                CarLLog.v(TAG, "333 mSpinnerValue : " + mSpinnerValue);

                String carmodel = mCarModel.getText().toString();
                if (carmodel == null || carmodel.length() <= 0)
                    carmodel = "0";

                MainApplication.setmUserCarName(mCarName.getText().toString());
                MainApplication.setCar_vehicle_number(mVehicleNumber.getText().toString());
                MainApplication.setCar_model(Integer.parseInt(carmodel));
                MainApplication.setCar_number(mCarNumber.getText().toString());
                MainApplication.setOil_type(mSpinnerValue);

                new AllDataDBsetting(mContext, MainApplication.getUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(),
                        MainApplication.getCar_number(), MainApplication.getCar_model(), MainApplication.getOil_type(), MainApplication.getMileage(),
                        MainApplication.getAuto_time(), MainApplication.getOil(), MainApplication.getAuto_coolant(), MainApplication.getFuel_consumption(),
                        MainApplication.getStart_date(), MainApplication.getFuel_efficienct(), MainApplication.getDrive_time(), MainApplication.getDrive_down_speed(),
                        MainApplication.getDrive_up_speed(), MainApplication.getIdle(), MainApplication.getDrive(), MainApplication.getEngine_oil(), MainApplication.getTire(),
                        MainApplication.getBrake_lining(), MainApplication.getAircon_filter(), MainApplication.getWiper(), MainApplication.getEquip_coolant(), MainApplication.getTransmission_oil(),
                        MainApplication.getBattery(), Utils.Date(), MainApplication.getEngine_oil_day(), MainApplication.getTire_day(), MainApplication.getBrake_lining_day(),
                        MainApplication.getAircon_filter_day(), MainApplication.getWiper_day(), MainApplication.getEquip_coolant_day(), MainApplication.getTransmission_oil_day(),
                        MainApplication.getBattery_day(), MainApplication.getSava_mileage());

                Message msg = mHandler.obtainMessage();
                msg.obj = mCarName.getText().toString();//mSpinnerValue;
                mHandler.sendMessage(msg);
                break;

            case R.id.btn_cancel:
                break;

        }
        dismiss();
    }

    /**
     * 업데이트 AsycTask
     *
     */
//	public class CommonUpdateAsycTask extends
//			AsyncTask<Void, Void, AllDataContentData> {
//
//		private String userId = null;
//		private String car_Name = null;
//		private String car_vehicle_number  = null;
//		private String car_number = null; 
//		private int car_model = 0;
//		private String oil_type = null;
//		private double mileage = 0; 
//		private int auto_time = 0; 
//		private double oil = 0; 
//		private double auto_coolant = 0; 
//		private double fuel_consumption = 0;
//		private String start_date = null; 
//		private double fuel_efficienct = 0; 
//		private int drive_time = 0; 
//		private int drive_down_speed = 0; 
//		private int drive_up_speed = 0; 
//		private int idle = 0; 
//		private int drive = 0;
//		private double engine_oil = 0; 
//		private double tire = 0; 
//		private double brake_lining = 0; 
//		private double aircon_filter = 0; 
//		private double wiper = 0; 
//		private double equip_coolant = 0; 
//		private double transmission_oil = 0; 
//		private double battery = 0; 
//		private String enging_oil_day = null;
//		private String tire_day = null; 
//		private String brake_lining_day = null;
//		private String aircon_filter_day = null;
//		private String wiper_day = null;
//		private String equip_coolant_day = null;
//		private String transmission_oil_day = null;
//		private String battery_day = null;
//		private String day = null;
//		private double save_mileage = 0;
//
//		/**
//		 * @param context
//		 * @param stopHandler
//		 */
//		public CommonUpdateAsycTask(Context context, 
//				String userid, String carName, double enging_oil, double tire,
//				String car_vehicle_number, String car_number, int car_model, String oil_type,
//				double mileage, int auto_time, double oil, double auto_coolant, double fuel_consumption,
//				String start_date, double fuel_efficienct, int drive_style_time, int down_speed,
//				int up_speed, int idle, int drive_style_drive,
//				double brake_lining, double aircon_filter, double wiper, 
//				double equip_coolant, double transmission_oil, double battery, 
//				String enging_oil_day, String tire_day, String brake_lining_day, String aircon_filter_day,
//				String wiper_day, String equip_coolant_day, String transmission_oil_day, String battery_day, String day, double save_mileage) {
//			
//			this.userId = userid;
//			this.car_Name = carName;
//			this.car_vehicle_number  = car_vehicle_number;
//			this.car_number = car_number; 
//			this.car_model = car_model;
//			this.oil_type = oil_type;
//			this.mileage = mileage; 
//			this.auto_time = auto_time; 
//			this.oil = oil; 
//			this.auto_coolant = auto_coolant; 
//			this.fuel_consumption = fuel_consumption;
//			this.start_date = start_date; 
//			this.fuel_efficienct = fuel_efficienct; 
//			this.drive_time = drive_style_time; 
//			this.drive_down_speed = down_speed; 
//			this.drive_up_speed = up_speed; 
//			this.idle = idle; 
//			this.drive = drive_style_drive;
//			this.engine_oil = enging_oil; 
//			this.tire = tire; 
//			this.brake_lining = brake_lining; 
//			this.aircon_filter = aircon_filter; 
//			this.wiper = wiper; 
//			this.equip_coolant = equip_coolant; 
//			this.transmission_oil = transmission_oil; 
//			this.battery = battery; 
//			this.enging_oil_day = enging_oil_day;
//			this.tire_day = tire_day; 
//			this.brake_lining_day = brake_lining_day;
//			this.aircon_filter_day = aircon_filter_day;
//			this.wiper_day = wiper_day;
//			this.equip_coolant_day = equip_coolant_day;
//			this.transmission_oil_day = transmission_oil_day;
//			this.battery_day = battery_day;
//			this.day = day;
//			this.save_mileage = save_mileage;
//
//		}
//
//		@Override
//		protected AllDataContentData doInBackground(Void... params) {
//			AllDataContentData auto = null;
//			try {
//
//				DBAdapter db = new DBAdapter(mContext);
//				db.open();
//
//				db.getAll_DataManager().updateAll_Data(MainApplication.getdbId(), userId, car_Name, engine_oil, 
//														tire, car_vehicle_number, car_number, 
//														car_model, oil_type, mileage, auto_time, 
//														oil, auto_coolant, fuel_consumption, 
//														start_date, fuel_efficienct, drive_time, 
//														drive_down_speed, drive_up_speed, idle, 
//														drive, brake_lining, aircon_filter, wiper, 
//														equip_coolant, transmission_oil, battery, 
//														enging_oil_day, tire_day, brake_lining_day, aircon_filter_day,
//														wiper_day, equip_coolant_day, transmission_oil_day, battery_day,
//														day, save_mileage);
//
//				db.close();
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return auto;
//		}
//
//		@Override
//		protected void onPostExecute(AllDataContentData result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
////			loadingstart.onDismiss();
//			onCancelled();
//
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// Things to be done before execution of long running operation. For
//			// example showing ProgessDialog
//		}
//
//		@Override
//		protected void onProgressUpdate(Void... values) {
//			// Things to be done while execution of long running operation is in
//			// progress. For example updating ProgessDialog
//		}
//
//		@Override
//		protected void onCancelled() {
//			// TODO Auto-generated method stub
//			super.onCancelled();
//		}
//	}

    /**
     * item db
     * **/
//	private AllDataContentData AllDataDB(){
//
//		DBAdapter db = new DBAdapter(mContext); 
//		db.open();
//		AllDataContentData auto = null;
//
//		Cursor cursor = db.getAll_DataManager().getAll_All_Data();
//
//		if (cursor.moveToFirst())
//		{
//			do {          
//				auto = new AllDataContentData();
//
//				int idIndex = cursor.getColumnIndex(All_Date_content.KEY_ROWID);
//
//				int userIdIndex = cursor.getColumnIndex(All_Date_content.KEY_All_USERID);
//				auto.setUserId(cursor.getString(userIdIndex));
//				
//				int car_NameIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NAME);
//				auto.setCar_Name(cursor.getString(car_NameIndex));
//				
//				int car_vehicle_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_VEHICLE_NUMBER);
//				auto.setCar_vehicle_number(cursor.getString(car_vehicle_numberIndex));
//				
//				int car_numberIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_NUMBER);
//				auto.setCar_number(cursor.getString(car_numberIndex));
//				
//				int car_modelIndex = cursor.getColumnIndex(All_Date_content.KEY_All_CAR_MODEL);
//				auto.setCar_model(cursor.getInt(car_modelIndex));
//				
//				int oil_typeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL_TYPE);
//				auto.setOil_type(cursor.getString(oil_typeIndex));
//				
//				int mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_MILEAGE);
//				auto.setMileage(cursor.getDouble(mileageIndex));
//				
//				int auto_timeIndex =cursor.getColumnIndex(All_Date_content.KEY_All_TMIE);
//				auto.setAuto_time(cursor.getInt(auto_timeIndex));
//				
//				int oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_OIL);
//				auto.setOil(cursor.getDouble(oilIndex));
//				
//				int auto_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AUTO_DIAGNOSIS_COOLANT);
//				auto.setAuto_coolant(cursor.getDouble(auto_coolantIndex));
//				
//				int fuel_consumptionIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_CONSUMPTION);
//				auto.setFuel_consumption(cursor.getDouble(fuel_consumptionIndex));
//				
//				int start_dateIndex = cursor.getColumnIndex(All_Date_content.KEY_All_START_DATE);
//				auto.setStart_date(cursor.getString(start_dateIndex));
//				
//				int fuel_efficienctIndex = cursor.getColumnIndex(All_Date_content.KEY_All_FUEL_EFFICIENCT);
//				auto.setFuel_efficienct(cursor.getDouble(fuel_efficienctIndex));
//				
//				int drive_timeIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_TIME);
//				auto.setDrive_time(cursor.getInt(drive_timeIndex));
//				
//				int drive_down_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_DOWN_SPEEd);
//				auto.setDrive_down_speed(cursor.getInt(drive_down_speedIndex));
//				
//				int drive_up_speedIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE_UP_SPEEd);
//				auto.setDrive_up_speed(cursor.getInt(drive_up_speedIndex));
//				
//				int idleIndex = cursor.getColumnIndex(All_Date_content.KEY_All_IDLE);
//				auto.setIdle(cursor.getInt(idleIndex));
//				
//				int driveIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DRIVE);
//				auto.setDrive(cursor.getInt(driveIndex));
//				
//				int engine_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL);
//				auto.setEngine_oil(cursor.getDouble(engine_oilIndex));
//				
//				int tireIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE);
//				auto.setTire(cursor.getDouble(tireIndex));
//				
//				int brake_liningIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING);
//				auto.setBrake_lining(cursor.getDouble(brake_liningIndex));
//				
//				int aircon_filterIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER);
//				auto.setAircon_filter(cursor.getDouble(aircon_filterIndex));
//				
//				int wiperIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER);
//				auto.setWiper(cursor.getDouble(wiperIndex));
//				
//				int equip_coolantIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT);
//				auto.setEquip_coolant(cursor.getDouble(equip_coolantIndex));
//				
//				int transmission_oilIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL);
//				auto.setTransmission_oil(cursor.getDouble(transmission_oilIndex));
//				
//				int batteryIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY);
//				auto.setBattery(cursor.getDouble(batteryIndex));
//				
//				int engine_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_ENGINE_OIL_DAY);
//				auto.setEngine_oil_day(cursor.getString(engine_oil_dayIndex));
//				
//				int tire_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TIRE_DAY);
//				auto.setTire_day(cursor.getString(tire_dayIndex));
//				
//				int brake_lining_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BRAKE_LINING_DAY);
//				auto.setBrake_lining_day(cursor.getString(brake_lining_dayIndex));
//				
//				int aircon_filter_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_AIRCON_FILTER_DAY);
//				auto.setAircon_filter_day(cursor.getString(aircon_filter_dayIndex));
//				
//				int wiper_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_WIPER_DAY);
//				auto.setWiper_day(cursor.getString(wiper_dayIndex));
//				
//				int equip_coolant_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_EQUIP_COOLANT_DAY);
//				auto.setEquip_coolant_day(cursor.getString(equip_coolant_dayIndex));
//				
//				int transmission_oil_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_TRANSMISSION_OIL_DAY);
//				auto.setTransmission_oil_day(cursor.getString(transmission_oil_dayIndex));
//				
//				int battery_dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_BATTERY_DAY);
//				auto.setBattery_day(cursor.getString(battery_dayIndex));
//				
//				int dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DAY);
//				auto.setDay(cursor.getString(dayIndex));
//
//			} while (cursor.moveToNext());
//		}
//
//		cursor.close();
//
//		db.close();
//
//		return auto;
//	}

//	private final Handler mStopAsyncTaskHandler = new Handler(new Handler.Callback() {
//		
//		@Override
//		public boolean handleMessage(Message msg) {
//			
//			if(mAllDataDBAsycTask != null && mAllDataDBAsycTask.getStatus() == AsyncTask.Status.FINISHED && msg.arg1 == MainApplication.handlerAuto) {
//				mAllDataDBAsycTask.cancel(true);
//				mAllDataDBAsycTask = null;	
//				
//				AllDataContentData auto = (AllDataContentData)msg.obj;
//				
//				if(auto.getCar_Name() != null){
//					mCarName.setText(auto.getCar_Name());
//					
//				} 
//				if(auto.getCar_vehicle_number() != null){
//					mVehicleNumber.setText(auto.getCar_vehicle_number());
//					
//				} 
//				if(auto.getCar_number() != null){
//					mCarNumber.setText(auto.getCar_number());
//					
//				} 
//				if(auto.getCar_model() >= 0){
//					mCarModel.setText(auto.getCar_model());
//					
//				} 
//				if(auto.getOil_type() != null){
//					if(auto.getOil_type().equals(MainApplication.OIL_DISEL)){
//						mSpinner.setSelection(1);
//					} else{
//						mSpinner.setSelection(0);
//					}
//				}
//				
//				mSpinner.setAdapter(adapter);
//				mSpinner.setOnItemSelectedListener(itemlistener);
//				
//			}
//			
//			return false;
//		}
//	});

    /**
     * item DB를 받아오는곳
     * */
//	public class AutoDBAsycTask extends AsyncTask<Void, Void, AllDataContentData>{
//
//		private Handler mStopHandler = null;
//
//		/**
//		 * @param context	 
//		 * @param stopHandler
//		 */
//		public AutoDBAsycTask(Context context, Handler stopHandler){
//			mStopHandler = stopHandler;
//		}
//
//		@Override
//		protected AllDataContentData doInBackground(Void... params) {
//			AllDataContentData alldatainfo = null;
//			try {
//				// DB 받아오는 용도			
//				alldatainfo = AllDataDB();
//
//
//				if(!isCancelled())
//				{
//
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return alldatainfo; 
//		}	
//
//
//		@Override
//		protected void onPostExecute(AllDataContentData result) {
//			// TODO Auto-generated method stub
//			super.onPostExecute(result);
//
//			Message msg = mStopHandler.obtainMessage();
//			msg.obj = (AllDataContentData)result;		
//			msg.arg1 = MainApplication.handlerAuto;	
//			mStopHandler.sendMessage(msg);				
//			onCancelled();
//
//		}
//
//		@Override
//		protected void onPreExecute() {
//			// Things to be done before execution of long running operation. For
//			// example showing ProgessDialog
//		}
//
//		@Override
//		protected void onProgressUpdate(Void... values) {
//			// Things to be done while execution of long running operation is in
//			// progress. For example updating ProgessDialog
//		}
//
//		@Override
//		protected void onCancelled() {
//			// TODO Auto-generated method stub
//			super.onCancelled();
//
//			if(mStopHandler != null){
//				mStopHandler = null;
//			}			
//
//		}
//	}

}
