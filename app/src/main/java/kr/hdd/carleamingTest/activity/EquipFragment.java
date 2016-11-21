package kr.hdd.carleamingTest.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kr.hdd.carleamingTest.MainApplication;
import kr.hdd.carleamingTest.R;
import kr.hdd.carleamingTest.activity.data.AllDataContentData;
import kr.hdd.carleamingTest.common.popup.PopupCarInfoEdit;
import kr.hdd.carleamingTest.common.popup.PopupEquipKM;
import kr.hdd.carleamingTest.database.DBAdapter;
import kr.hdd.carleamingTest.database.DBAdapter.All_Date_content;
import kr.hdd.carleamingTest.util.CarLLog;
import kr.hdd.carleamingTest.util.Utils;

public class EquipFragment extends Fragment implements OnClickListener {

    private View view = null;

    private static final String TAG = EquipFragment.class.getSimpleName();

    private RelativeLayout mRlBox01 = null;
    private RelativeLayout mRlBox02 = null;
    private RelativeLayout mRlBox03 = null;
    private RelativeLayout mRlBox04 = null;
    private RelativeLayout mRlBox05 = null;
    private RelativeLayout mRlBox06 = null;
    private RelativeLayout mRlBox07 = null;
    private RelativeLayout mRlBox08 = null;

    private View resetView_01 = null;
    private View resetView_02 = null;
    private View resetView_03 = null;
    private View resetView_04 = null;
    private View resetView_05 = null;
    private View resetView_06 = null;
    private View resetView_07 = null;
    private View resetView_08 = null;

    private TextView mTitleDriveCount1 = null;
    private TextView mTitleDriveCount2 = null;
    private TextView mTxBox01 = null;
    private TextView mTxBox02 = null;
    private TextView mTxBox03 = null;
    private TextView mTxBox04 = null;
    private TextView mTxBox05 = null;
    private TextView mTxBox06 = null;
    private TextView mTxBox07 = null;
    private TextView mTxBox08 = null;
    private TextView mTitleCarName = null;

    private Button mBtnBack = null;
    private Button mBtnReset = null;
    private Button mBtnCarInfo = null;

//	private double mdbMileage = 0;
//	private double mdbSaveMileage = 0;

    private double mBox01 = 0;
    private double mBox02 = 0;
    private double mBox03 = 0;
    private double mBox04 = 0;
    private double mBox05 = 0;
    private double mBox06 = 0;
    private double mBox07 = 0;
    private double mBox08 = 0;
    private double mTitleDriveCountHap = 0;

    //	private String mCutDbMileage = "0";
    private String mBox01_date = null;
    private String mBox02_date = null;
    private String mBox03_date = null;
    private String mBox04_date = null;
    private String mBox05_date = null;
    private String mBox06_date = null;
    private String mBox07_date = null;
    private String mBox08_date = null;
    private String mCarName = null;

    private AllDBAsycTask mAllDataDBAsycTask = null;

    private ArrayList<AllDataContentData> mArrAlldata = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.equip_layout, null);

        mArrAlldata = new ArrayList<AllDataContentData>();

        mTitleCarName = (TextView) view.findViewById(R.id.equip_title_car_name);
        mBtnCarInfo = (Button) view.findViewById(R.id.equip_car_info_btn);

        mTitleDriveCount1 = (TextView) view.findViewById(R.id.equip_title_drive_count1);  //년 주행량 시작 거리
        mTitleDriveCount2 = (TextView) view.findViewById(R.id.equip_title_drive_count2);  //년 주행량 시작 끝

        mRlBox01 = (RelativeLayout) view.findViewById(R.id.equip_box_01_layout);
        mRlBox02 = (RelativeLayout) view.findViewById(R.id.equip_box_02_layout);
        mRlBox03 = (RelativeLayout) view.findViewById(R.id.equip_box_03_layout);
        mRlBox04 = (RelativeLayout) view.findViewById(R.id.equip_box_04_layout);
        mRlBox05 = (RelativeLayout) view.findViewById(R.id.equip_box_05_layout);
        mRlBox06 = (RelativeLayout) view.findViewById(R.id.equip_box_06_layout);
        mRlBox07 = (RelativeLayout) view.findViewById(R.id.equip_box_07_layout);
        mRlBox08 = (RelativeLayout) view.findViewById(R.id.equip_box_08_layout);

        resetView_01 = view.findViewById(R.id.equip_box_01_click_layout);
        resetView_02 = view.findViewById(R.id.equip_box_02_click_layout);
        resetView_03 = view.findViewById(R.id.equip_box_03_click_layout);
        resetView_04 = view.findViewById(R.id.equip_box_04_click_layout);
        resetView_05 = view.findViewById(R.id.equip_box_05_click_layout);
        resetView_06 = view.findViewById(R.id.equip_box_06_click_layout);
        resetView_07 = view.findViewById(R.id.equip_box_07_click_layout);
        resetView_08 = view.findViewById(R.id.equip_box_08_click_layout);

        mTxBox01 = (TextView) view.findViewById(R.id.equip_box_01_text);  //엔진오일
        mTxBox02 = (TextView) view.findViewById(R.id.equip_box_02_text);  //타이어
        mTxBox03 = (TextView) view.findViewById(R.id.equip_box_03_text);  //브레이크라이닝
        mTxBox04 = (TextView) view.findViewById(R.id.equip_box_04_text);  //에어컨 필터
        mTxBox05 = (TextView) view.findViewById(R.id.equip_box_05_text);  //와이퍼
        mTxBox06 = (TextView) view.findViewById(R.id.equip_box_06_text);  //냉각수
        mTxBox07 = (TextView) view.findViewById(R.id.equip_box_07_text);  //오토미션오일
        mTxBox08 = (TextView) view.findViewById(R.id.equip_box_08_text);  //배터리

        mRlBox01.setOnClickListener(this);
        mRlBox02.setOnClickListener(this);
        mRlBox03.setOnClickListener(this);
        mRlBox04.setOnClickListener(this);
        mRlBox05.setOnClickListener(this);
        mRlBox06.setOnClickListener(this);
        mRlBox07.setOnClickListener(this);
        mRlBox08.setOnClickListener(this);
        mBtnCarInfo.setOnClickListener(this);

        mAllDataDBAsycTask = new AllDBAsycTask(getActivity(), mStopAsyncTaskHandler);
//		mAllDataDBAsycTask = new AllDBAsycTask(getActivity(), null);
        mAllDataDBAsycTask.execute();
//		mUiHandler.sendEmptyMessage(0);

        return view;
    }

    private Handler mUiHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

//			if(mArrAlldata != null){
//				for(int i=0; i<mArrAlldata.size(); i++){
//					mTitleDriveCountHap += mArrAlldata.get(i).getMileage();
//				}
//			}

//			mTitleDriveCountHap = mdbSaveMileage;
            mTitleDriveCountHap = MainApplication.getSava_mileage();
            double starM = 0;

            CarLLog.i(TAG, "mArrAlldata: " + mArrAlldata.size());
            if (mArrAlldata.size() > 0 && Integer.parseInt(mArrAlldata.get(0).getDay().substring(0, 4)) != Integer.parseInt(Utils.YearDate())) {
                starM = mArrAlldata.get(0).mileage;

            } else {
                starM = 0;
//				mTitleDriveCount1.setText("0");
            }

            mTitleDriveCount1.setText(String.format("%.1f", starM));
            mTitleDriveCount2.setText(String.format("%.1f", mTitleDriveCountHap - starM));

            mBox01 = MainApplication.getEngine_oil();
            mBox02 = MainApplication.getTire();
            mBox03 = MainApplication.getBrake_lining();
            mBox04 = MainApplication.getAircon_filter();
            mBox05 = MainApplication.getWiper();
            mBox06 = MainApplication.getEquip_coolant();
            mBox07 = MainApplication.getTransmission_oil();
            mBox08 = MainApplication.getBattery();

            setEngineGraphLayout(mBox01);
            setAirconBrGraphLayout(mBox03, false);
            setAirconBrGraphLayout(mBox04, true);
            setAutoTGraphLayout(mBox02, false, true);
            setAutoTGraphLayout(mBox07, true, false);
            setAutoTGraphLayout(mBox05, false, false);
            setBatteryCoolGraphLayout(mBox08, true);
            setBatteryCoolGraphLayout(mBox06, false);

            return false;
        }
    });

//	@Override
//	public void onPause() {
//		super.onPause();
//		CarLLog.v(TAG,"onPause");
//		
//		new AllDataDBsetting(getActivity(),  MainApplication.getmUserId(), MainApplication.getmUserCarName(), MainApplication.getCar_vehicle_number(), 
//				MainApplication.getCar_number(),  MainApplication.getCar_model(), MainApplication.getOil_type(), 
//				MainApplication.getMileage(), MainApplication.getAuto_time(),  MainApplication.getOil(), MainApplication.getAuto_coolant(), 
//				MainApplication.getFuel_consumption(), MainApplication.getStart_date(), MainApplication.getFuel_efficienct(), 
//				MainApplication.getDrive_time(), MainApplication.getDrive_down_speed(), MainApplication.getDrive_up_speed(), 
//				MainApplication.getIdle(), MainApplication.getDrive(), MainApplication.getEngine_oil(), MainApplication.getTire(), 
//				MainApplication.getBrake_lining(), MainApplication.getAircon_filter(), MainApplication.getWiper(), 
//				MainApplication.getEquip_coolant(), MainApplication.getTransmission_oil(), MainApplication.getBattery(), 
//				Utils.Date(), MainApplication.getEngine_oil_day(), MainApplication.getTire_day(), MainApplication.getBrake_lining_day(), 
//				MainApplication.getAircon_filter_day(), MainApplication.getWiper_day(), MainApplication.getEquip_coolant_day(),
//				MainApplication.getTransmission_oil_day(), MainApplication.getBattery_day(), MainApplication.getSava_mileage());
//	}


    private void setEngineGraphLayout(double number) {
        //엔진오일

        String box1 = String.format("%.1f", mBox01);
        View graphView = view.findViewById(R.id.equip_box_01_include);

        ImageView img1 = (ImageView) graphView.findViewById(R.id.equip_img_01);
        ImageView img2 = (ImageView) graphView.findViewById(R.id.equip_img_02);
        ImageView img3 = (ImageView) graphView.findViewById(R.id.equip_img_03);
        ImageView img4 = (ImageView) graphView.findViewById(R.id.equip_img_04);
        ImageView img5 = (ImageView) graphView.findViewById(R.id.equip_img_05);
        ImageView img6 = (ImageView) graphView.findViewById(R.id.equip_img_06);
        ImageView img7 = (ImageView) graphView.findViewById(R.id.equip_img_07);
        ImageView img8 = (ImageView) graphView.findViewById(R.id.equip_img_08);
        ImageView img9 = (ImageView) graphView.findViewById(R.id.equip_img_09);
        ImageView img10 = (ImageView) graphView.findViewById(R.id.equip_img_010);
        TextView text = (TextView) graphView.findViewById(R.id.equip_img_text);

        if (Double.parseDouble(box1) < 5000) {
            mTxBox01.setTextColor(getResources().getColor(R.color._5D5D5D));
            mTxBox01.setText("교환일(" + MainApplication.getEngine_oil_day() + ")");
            text.setTextColor(getResources().getColor(R.color._0E62AB));
        } else {
            mTxBox01.setTextColor(getResources().getColor(R.color._E97900));
            mTxBox01.setText("교환요망(" + String.format("%.1f", mBox01 - 5000) + "km 초과)");
            text.setTextColor(getResources().getColor(R.color._E97900));
        }

        text.setText(String.format("%.1f", mBox01) + "km");

        ImageView[] img_arr = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10};
        int[] drawable = {R.drawable.equip_graph_off, R.drawable.equip_graph_on};

        for (int i = 0; i < img_arr.length; i++) {

            if (number == 0.0) {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
            } else if (number <= 500) {
                if (i == 0) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 1000) {
                if (i <= 1) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 1500) {
                if (i <= 2) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 2000) {
                if (i <= 3) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 2500) {
                if (i <= 4) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 3000) {
                if (i <= 5) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 3500) {
                if (i <= 6) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 4000) {
                if (i <= 7) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 4500) {
                if (i <= 8) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 5000) {
                if (i <= 9) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                text.setText(mBox01 + "km");
                text.setTextColor(getActivity().getResources().getColor(R.color._E97900));
            }

        }

    }

    private Handler mHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (msg != null) {
                mCarName = (String) msg.obj;
                CarLLog.v(TAG, "mOilType : " + mCarName);
                mTitleCarName.setText(mCarName);
                MainApplication.setmUserCarName(mCarName);
            }

            return false;
        }
    });

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.equip_car_info_btn:
                PopupCarInfoEdit popup = new PopupCarInfoEdit(getActivity(), mHandler);
                popup.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                popup.show();
                break;

            case R.id.equip_box_01_layout:
                mRlBox01.setVisibility(View.GONE);
                resetView_01.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_01.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getEngine_oil_day() == null || MainApplication.getEngine_oil_day().equals("0")){
                PopupEquipKM kmEditpopup1 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox01_date = KmEdit;

                        if (mBox01_date.equals("")) {
                            mBox01_date = "0";
                        }

                        mBox01 = Double.parseDouble(mBox01_date);
                        setEngineGraphLayout(mBox01);
                        MainApplication.setEngine_oil(mBox01);

                        mRlBox01.setVisibility(View.VISIBLE);
                        resetView_01.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup1.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup1.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox01.setVisibility(View.VISIBLE);
                        resetView_01.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_01.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog1 = new AlertDialog.Builder(getActivity());
                        gsDialog1.setTitle("리셋 확인");
                        gsDialog1.setMessage("리셋 하시겠습니까?");
                        gsDialog1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mBox01 = 0;
                                MainApplication.setEngine_oil(mBox01);
                                MainApplication.setEngine_oil_day(Utils.JunmDate());
                                setEngineGraphLayout(mBox01);

                                mRlBox01.setVisibility(View.VISIBLE);
                                resetView_01.setVisibility(View.GONE);
                            }
                        });
                        gsDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox01.setVisibility(View.VISIBLE);
                                resetView_01.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_02_layout:
                mRlBox02.setVisibility(View.GONE);
                resetView_02.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_02.findViewById(R.id.equip_btn_back);

                //			if(MainApplication.getDay() == null){
//			if(MainApplication.getTire_day() == null || MainApplication.getTire_day().equals("0")){
                PopupEquipKM kmEditpopup2 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox02_date = KmEdit;

                        if (mBox02_date.equals("")) {
                            mBox02_date = "0";
                        }

                        mBox02 = Double.parseDouble(mBox02_date);
                        setAutoTGraphLayout(mBox02, false, true);
                        MainApplication.setTire(mBox02);


                        mRlBox02.setVisibility(View.VISIBLE);
                        resetView_02.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup2.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup2.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox02.setVisibility(View.VISIBLE);
                        resetView_02.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_02.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog2 = new AlertDialog.Builder(getActivity());
                        gsDialog2.setTitle("리셋 확인");
                        gsDialog2.setMessage("리셋 하시겠습니까?");
                        gsDialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mBox02 = 0;
                                MainApplication.setTire(mBox02);
                                MainApplication.setTire_day(Utils.JunmDate());
                                setAutoTGraphLayout(mBox02, false, true);

                                mRlBox02.setVisibility(View.VISIBLE);
                                resetView_02.setVisibility(View.GONE);

                            }
                        });
                        gsDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox02.setVisibility(View.VISIBLE);
                                resetView_02.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_03_layout:
                mRlBox03.setVisibility(View.GONE);
                resetView_03.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_03.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getBrake_lining_day() == null || MainApplication.getBrake_lining_day().equals("0")){
                PopupEquipKM kmEditpopup3 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox03_date = KmEdit;

                        if (mBox03_date.equals("")) {
                            mBox03_date = "0";
                        }

                        mBox03 = Double.parseDouble(mBox03_date);
                        setAirconBrGraphLayout(mBox03, false);
                        MainApplication.setBrake_lining(mBox03);


                        mRlBox03.setVisibility(View.VISIBLE);
                        resetView_03.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup3.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup3.show();
//			}


                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox03.setVisibility(View.VISIBLE);
                        resetView_03.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_03.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog3 = new AlertDialog.Builder(getActivity());
                        gsDialog3.setTitle("리셋 확인");
                        gsDialog3.setMessage("리셋 하시겠습니까?");
                        gsDialog3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mBox03 = 0;
                                MainApplication.setBrake_lining(mBox03);
                                MainApplication.setBrake_lining_day(Utils.JunmDate());
                                setAirconBrGraphLayout(mBox03, false);

                                mRlBox03.setVisibility(View.VISIBLE);
                                resetView_03.setVisibility(View.GONE);
                            }
                        });
                        gsDialog3.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox03.setVisibility(View.VISIBLE);
                                resetView_03.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_04_layout:
                mRlBox04.setVisibility(View.GONE);
                resetView_04.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_04.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getAircon_filter_day() == null || MainApplication.getAircon_filter_day().equals("0")){
                PopupEquipKM kmEditpopup4 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox04_date = KmEdit;

                        if (mBox04_date.equals("")) {
                            mBox04_date = "0";
                        }

                        mBox04 = Double.parseDouble(mBox04_date);
                        setAirconBrGraphLayout(mBox04, true);
                        MainApplication.setAircon_filter(mBox04);

                        mRlBox04.setVisibility(View.VISIBLE);
                        resetView_04.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup4.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup4.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox04.setVisibility(View.VISIBLE);
                        resetView_04.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_04.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog4 = new AlertDialog.Builder(getActivity());
                        gsDialog4.setTitle("리셋 확인");
                        gsDialog4.setMessage("리셋 하시겠습니까?");
                        gsDialog4.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mBox04 = 0;
                                MainApplication.setAircon_filter(mBox04);
                                MainApplication.setAircon_filter_day(Utils.JunmDate());
                                setAirconBrGraphLayout(mBox04, true);

                                mRlBox04.setVisibility(View.VISIBLE);
                                resetView_04.setVisibility(View.GONE);

                            }
                        });
                        gsDialog4.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox04.setVisibility(View.VISIBLE);
                                resetView_04.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_05_layout:
                mRlBox05.setVisibility(View.GONE);
                resetView_05.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_05.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getWiper_day() == null || MainApplication.getWiper_day().equals("0")){
                PopupEquipKM kmEditpopup5 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox05_date = KmEdit;

                        if (mBox05_date.equals("")) {
                            mBox05_date = "0";
                        }

                        mBox05 = Double.parseDouble(mBox05_date);
                        setAutoTGraphLayout(mBox05, false, false);
                        MainApplication.setWiper(mBox05);

                        mRlBox05.setVisibility(View.VISIBLE);
                        resetView_05.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup5.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup5.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox05.setVisibility(View.VISIBLE);
                        resetView_05.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_05.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog5 = new AlertDialog.Builder(getActivity());
                        gsDialog5.setTitle("리셋 확인");
                        gsDialog5.setMessage("리셋 하시겠습니까?");
                        gsDialog5.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBox05 = 0;
                                MainApplication.setWiper(mBox05);
                                MainApplication.setWiper_day(Utils.JunmDate());
                                setAutoTGraphLayout(mBox05, false, false);

                                mRlBox05.setVisibility(View.VISIBLE);
                                resetView_05.setVisibility(View.GONE);

                            }
                        });
                        gsDialog5.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox05.setVisibility(View.VISIBLE);
                                resetView_05.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_06_layout:
                mRlBox06.setVisibility(View.GONE);
                resetView_06.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_06.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getEquip_coolant_day() == null || MainApplication.getEquip_coolant_day().equals("0")){
                PopupEquipKM kmEditpopup6 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox06_date = KmEdit;

                        if (mBox06_date.equals("")) {
                            mBox06_date = "0";
                        }

                        mBox06 = Double.parseDouble(mBox06_date);
                        setBatteryCoolGraphLayout(mBox06, false);
                        MainApplication.setEquip_coolant(mBox06);

                        mRlBox06.setVisibility(View.VISIBLE);
                        resetView_06.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup6.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup6.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox06.setVisibility(View.VISIBLE);
                        resetView_06.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_06.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog6 = new AlertDialog.Builder(getActivity());
                        gsDialog6.setTitle("리셋 확인");
                        gsDialog6.setMessage("리셋 하시겠습니까?");
                        gsDialog6.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBox06 = 0;
                                MainApplication.setEquip_coolant(mBox06);
                                MainApplication.setEquip_coolant_day(Utils.JunmDate());
                                setBatteryCoolGraphLayout(mBox06, false);

                                mRlBox06.setVisibility(View.VISIBLE);
                                resetView_06.setVisibility(View.GONE);

                            }
                        });
                        gsDialog6.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox06.setVisibility(View.VISIBLE);
                                resetView_06.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_07_layout:
                mRlBox07.setVisibility(View.GONE);
                resetView_07.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_07.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getTransmission_oil_day() == null || MainApplication.getTransmission_oil_day().equals("0")){
                PopupEquipKM kmEditpopup7 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox07_date = KmEdit;

                        if (mBox07_date.equals("")) {
                            mBox07_date = "0";
                        }

                        mBox07 = Double.parseDouble(mBox07_date);
                        setAutoTGraphLayout(mBox07, true, false);
                        MainApplication.setTransmission_oil(mBox07);

                        mRlBox07.setVisibility(View.VISIBLE);
                        resetView_07.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup7.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup7.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox07.setVisibility(View.VISIBLE);
                        resetView_07.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_07.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog7 = new AlertDialog.Builder(getActivity());
                        gsDialog7.setTitle("리셋 확인");
                        gsDialog7.setMessage("리셋 하시겠습니까?");
                        gsDialog7.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBox07 = 0;
                                MainApplication.setTransmission_oil(mBox07);
                                MainApplication.setTransmission_oil_day(Utils.JunmDate());
                                setAutoTGraphLayout(mBox07, true, false);

                                mRlBox07.setVisibility(View.VISIBLE);
                                resetView_07.setVisibility(View.GONE);

                            }
                        });
                        gsDialog7.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox07.setVisibility(View.VISIBLE);
                                resetView_07.setVisibility(View.GONE);
                            }
                        })
                                .create().show();

                    }
                });
                break;

            case R.id.equip_box_08_layout:
                mRlBox08.setVisibility(View.GONE);
                resetView_08.setVisibility(View.VISIBLE);
                mBtnBack = (Button) resetView_08.findViewById(R.id.equip_btn_back);

//			if(MainApplication.getBattery_day() == null || MainApplication.getBattery_day().equals("0")){
                PopupEquipKM kmEditpopup8 = new PopupEquipKM(getActivity(), new Handler(new Handler.Callback() {

                    @Override
                    public boolean handleMessage(Message msg) {

                        String KmEdit = (String) msg.obj;
                        mBox08_date = KmEdit;

                        if (mBox08_date.equals("")) {
                            mBox08_date = "0";
                        }

                        mBox08 = Double.parseDouble(mBox08_date);
                        setBatteryCoolGraphLayout(mBox08, true);
                        MainApplication.setBattery(mBox08);

                        mRlBox08.setVisibility(View.VISIBLE);
                        resetView_08.setVisibility(View.GONE);

                        return false;
                    }
                }));
                kmEditpopup8.setCanceledOnTouchOutside(false);  //팝업 밖에 터치 했을 경우 팝업 사라지는 여부
                kmEditpopup8.show();
//			}

                mBtnBack.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mRlBox08.setVisibility(View.VISIBLE);
                        resetView_08.setVisibility(View.GONE);
                    }
                });

                mBtnReset = (Button) resetView_08.findViewById(R.id.equip_btn_reset);
                mBtnReset.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder gsDialog8 = new AlertDialog.Builder(getActivity());
                        gsDialog8.setTitle("리셋 확인");
                        gsDialog8.setMessage("리셋 하시겠습니까?");
                        gsDialog8.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                mBox08 = 0;
                                MainApplication.setBattery(mBox08);
                                MainApplication.setBattery_day(Utils.JunmDate());
                                setBatteryCoolGraphLayout(mBox08, true);

                                mRlBox08.setVisibility(View.VISIBLE);
                                resetView_08.setVisibility(View.GONE);

                            }
                        });
                        gsDialog8.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRlBox08.setVisibility(View.VISIBLE);
                                resetView_08.setVisibility(View.GONE);
                            }
                        })
                                .create().show();
                    }
                });
                break;

        }
    }

    private void setAirconBrGraphLayout(double number, boolean type) {
        //에어컨 필터 & 브레이크 라이닝 20000  true - 에어컨필터, false - 브레이크라이닝

        String box3And4 = null;

        if (type) {
            box3And4 = String.format("%.1f", mBox04);
        } else {
            box3And4 = String.format("%.1f", mBox03);
        }

        View graphView = null;

        if (type) {
            graphView = view.findViewById(R.id.equip_box_04_include);
        } else {
            graphView = view.findViewById(R.id.equip_box_03_include);
        }


        ImageView img1 = (ImageView) graphView.findViewById(R.id.equip_img_01);
        ImageView img2 = (ImageView) graphView.findViewById(R.id.equip_img_02);
        ImageView img3 = (ImageView) graphView.findViewById(R.id.equip_img_03);
        ImageView img4 = (ImageView) graphView.findViewById(R.id.equip_img_04);
        ImageView img5 = (ImageView) graphView.findViewById(R.id.equip_img_05);
        ImageView img6 = (ImageView) graphView.findViewById(R.id.equip_img_06);
        ImageView img7 = (ImageView) graphView.findViewById(R.id.equip_img_07);
        ImageView img8 = (ImageView) graphView.findViewById(R.id.equip_img_08);
        ImageView img9 = (ImageView) graphView.findViewById(R.id.equip_img_09);
        ImageView img10 = (ImageView) graphView.findViewById(R.id.equip_img_010);
        TextView text = (TextView) graphView.findViewById(R.id.equip_img_text);

        if (Double.parseDouble(box3And4) < 20000) {

            text.setTextColor(getResources().getColor(R.color._0E62AB));

            if (type) {
                mTxBox04.setText("교환일(" + MainApplication.getAircon_filter_day() + ")");
                mTxBox04.setTextColor(getResources().getColor(R.color._5D5D5D));
                text.setText(String.format("%.1f", mBox04) + "km");
            } else {
                mTxBox03.setText("교환일(" + MainApplication.getBrake_lining_day() + ")");
                mTxBox03.setTextColor(getResources().getColor(R.color._5D5D5D));
                text.setText(String.format("%.1f", mBox03) + "km");
            }

        } else {
            text.setTextColor(getResources().getColor(R.color._E97900));

            if (type) {
                mTxBox04.setTextColor(getResources().getColor(R.color._E97900));
                mTxBox04.setText("교환요망(" + String.format("%.1f", mBox04 - 20000) + "km 초과)");
                text.setText(String.format("%.1f", mBox04) + "km");
            } else {
                mTxBox03.setTextColor(getResources().getColor(R.color._E97900));
                mTxBox03.setText("교환요망(" + String.format("%.1f", mBox03 - 20000) + "km 초과)");
                text.setText(String.format("%.1f", mBox03) + "km");
            }

        }

        ImageView[] img_arr = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10};
        int[] drawable = {R.drawable.equip_graph_off, R.drawable.equip_graph_on};

        for (int i = 0; i < img_arr.length; i++) {

            if (number == 0.0) {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
            } else if (number <= 2000) {
                if (i == 0) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 4000) {
                if (i <= 1) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 6000) {
                if (i <= 2) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 8000) {
                if (i <= 3) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 10000) {
                if (i <= 4) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 12000) {
                if (i <= 5) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 14000) {
                if (i <= 6) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 16000) {
                if (i <= 7) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 18000) {
                if (i <= 8) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 20000) {
                if (i <= 9) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                if (type) {
                    text.setText(mBox04 + "km");
                } else {
                    text.setText(mBox03 + "km");
                }
                text.setTextColor(getActivity().getResources().getColor(R.color._E97900));
            }

        }

    }

    private void setAutoTGraphLayout(double number, boolean type, boolean type2) {
        //오토미션오일 & 타이어 40000  type-true - 오토미션오일, type-false - 타이어&와이퍼 , type2-true-타이어, type2-false-와이퍼

        String box275 = null;

        View graphView = null;
        //		View graphView = (View) view.findViewById(R.id.equip_box_03_include);
        if (type) {
            graphView = view.findViewById(R.id.equip_box_07_include);
            box275 = String.format("%.1f", mBox07);
        } else {
            if (type2) {
                graphView = view.findViewById(R.id.equip_box_02_include);
                box275 = String.format("%.1f", mBox02);
            } else {
                graphView = view.findViewById(R.id.equip_box_05_include);
                box275 = String.format("%.1f", mBox05);
            }
        }

        ImageView img1 = (ImageView) graphView.findViewById(R.id.equip_img_01);
        ImageView img2 = (ImageView) graphView.findViewById(R.id.equip_img_02);
        ImageView img3 = (ImageView) graphView.findViewById(R.id.equip_img_03);
        ImageView img4 = (ImageView) graphView.findViewById(R.id.equip_img_04);
        ImageView img5 = (ImageView) graphView.findViewById(R.id.equip_img_05);
        ImageView img6 = (ImageView) graphView.findViewById(R.id.equip_img_06);
        ImageView img7 = (ImageView) graphView.findViewById(R.id.equip_img_07);
        ImageView img8 = (ImageView) graphView.findViewById(R.id.equip_img_08);
        ImageView img9 = (ImageView) graphView.findViewById(R.id.equip_img_09);
        ImageView img10 = (ImageView) graphView.findViewById(R.id.equip_img_010);
        TextView text = (TextView) graphView.findViewById(R.id.equip_img_text);

        if (Double.parseDouble(box275) < 40000) {

            text.setTextColor(getResources().getColor(R.color._0E62AB));

            if (type) {
                mTxBox07.setTextColor(getResources().getColor(R.color._5D5D5D));
                mTxBox07.setText("교환일(" + MainApplication.getTransmission_oil_day() + ")");
                text.setText(String.format("%.1f", mBox07) + "km");
            } else {
                if (type2) {
                    mTxBox02.setTextColor(getResources().getColor(R.color._5D5D5D));
                    mTxBox02.setText("교환일(" + MainApplication.getTire_day() + ")");
                    text.setText(String.format("%.1f", mBox02) + "km");
                } else {
                    mTxBox05.setTextColor(getResources().getColor(R.color._5D5D5D));
                    mTxBox05.setText("교환일(" + MainApplication.getWiper_day() + ")");
                    text.setText(String.format("%.1f", mBox05) + "km");
                }
            }

        } else {

            text.setTextColor(getResources().getColor(R.color._E97900));

            if (type) {
                mTxBox07.setTextColor(getResources().getColor(R.color._E97900));
                mTxBox07.setText("교환요망(" + String.format("%.1f", mBox07 - 40000) + "km 초과)");
                text.setText(String.format("%.1f", mBox07) + "km");
            } else {
                if (type2) {
                    mTxBox02.setTextColor(getResources().getColor(R.color._E97900));
                    mTxBox02.setText("교환요망(" + String.format("%.1f", mBox02 - 40000) + "km 초과)");
                    text.setText(String.format("%.1f", mBox02) + "km");
                } else {
                    mTxBox05.setTextColor(getResources().getColor(R.color._E97900));
                    mTxBox05.setText("교환요망(" + String.format("%.1f", mBox05 - 40000) + "km 초과)");
                    text.setText(String.format("%.1f", mBox05) + "km");
                }
            }
        }

        ImageView[] img_arr = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10};
        int[] drawable = {R.drawable.equip_graph_off, R.drawable.equip_graph_on};

        for (int i = 0; i < img_arr.length; i++) {

            if (number == 0.0) {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
            } else if (number <= 4000) {
                if (i == 0) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 8000) {
                if (i <= 1) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 12000) {
                if (i <= 2) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 16000) {
                if (i <= 3) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 20000) {
                if (i <= 4) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 24000) {
                if (i <= 5) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 28000) {
                if (i <= 6) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 32000) {
                if (i <= 7) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 36000) {
                if (i <= 8) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 40000) {
                if (i <= 9) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                if (type) {
                    text.setText(mBox07 + "km");
                } else {
                    if (type2) {
                        text.setText(mBox02 + "km");
                    } else {
                        text.setText(mBox05 + "km");
                    }
                }
                text.setTextColor(getActivity().getResources().getColor(R.color._E97900));
            }

        }

    }

    private void setBatteryCoolGraphLayout(double number, boolean type) {
        //배터리 & 부동액 60000 or 2년  true - 배터리, false - 부동액

        String box86 = null;

        View graphView = null;
        //		View graphView = (View) view.findViewById(R.id.equip_box_03_include);
        if (type) {
            graphView = view.findViewById(R.id.equip_box_08_include);
            box86 = String.format("%.1f", mBox08);
        } else {
            graphView = view.findViewById(R.id.equip_box_06_include);
            box86 = String.format("%.1f", mBox06);
        }


        ImageView img1 = (ImageView) graphView.findViewById(R.id.equip_img_01);
        ImageView img2 = (ImageView) graphView.findViewById(R.id.equip_img_02);
        ImageView img3 = (ImageView) graphView.findViewById(R.id.equip_img_03);
        ImageView img4 = (ImageView) graphView.findViewById(R.id.equip_img_04);
        ImageView img5 = (ImageView) graphView.findViewById(R.id.equip_img_05);
        ImageView img6 = (ImageView) graphView.findViewById(R.id.equip_img_06);
        ImageView img7 = (ImageView) graphView.findViewById(R.id.equip_img_07);
        ImageView img8 = (ImageView) graphView.findViewById(R.id.equip_img_08);
        ImageView img9 = (ImageView) graphView.findViewById(R.id.equip_img_09);
        ImageView img10 = (ImageView) graphView.findViewById(R.id.equip_img_010);
        TextView text = (TextView) graphView.findViewById(R.id.equip_img_text);

        if (Double.parseDouble(box86) < 60000) {

            text.setTextColor(getResources().getColor(R.color._0E62AB));

            if (type) {
                mTxBox08.setTextColor(getResources().getColor(R.color._5D5D5D));
                mTxBox08.setText("교환일(" + MainApplication.getBattery_day() + ")");
                text.setText(String.format("%.1f", mBox08) + "km");
            } else {
                mTxBox06.setTextColor(getResources().getColor(R.color._5D5D5D));
                mTxBox06.setText("교환일(" + MainApplication.getEquip_coolant_day() + ")");
                text.setText(String.format("%.1f", mBox06) + "km");
            }

        } else {

            text.setTextColor(getResources().getColor(R.color._E97900));

            if (type) {
                mTxBox08.setTextColor(getResources().getColor(R.color._E97900));
                mTxBox08.setText("교환요망(" + String.format("%.1f", mBox08 - 60000) + "km 초과)");
                text.setText(String.format("%.1f", mBox08) + "km");
            } else {
                mTxBox06.setTextColor(getResources().getColor(R.color._E97900));
                mTxBox06.setText("교환요망(" + String.format("%.1f", mBox06 - 60000) + "km 초과)");
                text.setText(String.format("%.1f", mBox06) + "km");
            }

        }

        ImageView[] img_arr = {img1, img2, img3, img4, img5, img6, img7, img8, img9, img10};
        int[] drawable = {R.drawable.equip_graph_off, R.drawable.equip_graph_on};

        for (int i = 0; i < img_arr.length; i++) {

            if (number == 0.0) {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
            } else if (number <= 6000) {
                if (i == 0) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 12000) {
                if (i <= 1) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 18000) {
                if (i <= 2) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 24000) {
                if (i <= 3) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 30000) {
                if (i <= 4) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 36000) {
                if (i <= 5) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 42000) {
                if (i <= 6) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 48000) {
                if (i <= 7) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 54000) {
                if (i <= 8) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else if (number <= 60000) {
                if (i <= 9) {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                } else {
                    img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[0]));
                }
            } else {
                img_arr[i].setBackground(graphView.getResources().getDrawable(drawable[1]));
                if (type) {

                    text.setText(mBox08 + "km");
                } else {
                    text.setText(mBox06 + "km");
                }
                text.setTextColor(getActivity().getResources().getColor(R.color._E97900));
            }
        }
    }

    /**
     * item db
     **/
    private AllDataContentData AllDataDB() {

        DBAdapter db = new DBAdapter(getActivity());
        db.open();
        AllDataContentData auto = null;

        Cursor cursor = db.getAll_DataManager().getAll_All_Data();

        if (cursor.moveToFirst()) {
            do {
                auto = new AllDataContentData();

//				int idIndex = cursor.getColumnIndex(All_Date_content.KEY_ROWID);
//				auto.setdbId(cursor.getLong(idIndex));
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

                int dayIndex = cursor.getColumnIndex(All_Date_content.KEY_All_DAY);
                auto.setDay(cursor.getString(dayIndex));

//				int save_mileageIndex = cursor.getColumnIndex(All_Date_content.KEY_All_SAVE_MILEAGE);
//				auto.setSave_mileage(cursor.getDouble(save_mileageIndex));

                if (!auto.getDay().equals("0") && auto.getDay().substring(0, 4).equals(Utils.YearDate())) {
                    mArrAlldata.add(auto);
//					CarLLog.w(TAG, "AllDataContentData AllDataDB()");
                }

            } while (cursor.moveToNext());
        } else {
            //			saveDBsetting();
        }

        cursor.close();

        db.close();

        return auto;
    }

    private final Handler mStopAsyncTaskHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {

            if (mAllDataDBAsycTask != null && mAllDataDBAsycTask.getStatus() == AsyncTask.Status.FINISHED) {
                mAllDataDBAsycTask.cancel(true);
                mAllDataDBAsycTask = null;

                AllDataContentData auto = (AllDataContentData) msg.obj;

                if (msg.obj != null) {
//					if(auto.getdbId() >= 0){
//						MainApplication.setdbId(auto.getdbId());
//					}
//
//					if(auto.getCar_Name() != null){
//						MainApplication.setmUserCarName(auto.getCar_Name());
//					}
//
//					if(auto.getUserId() != null){
//						MainApplication.setmUserId(auto.getUserId());
//					}
//					if(auto.getCar_vehicle_number() != null){
//						MainApplication.setCar_vehicle_number(auto.getCar_vehicle_number());
//					}
//					if(auto.getCar_number() != null){
//						MainApplication.setCar_number(auto.getCar_number());
//					}
//					if(auto.getCar_model() >= 0){
//						MainApplication.setCar_model(auto.getCar_model());
//					}
//					if(auto.getOil_type() != null){
//						MainApplication.setOil_type(auto.getOil_type());
//					}
//					if(auto.getMileage() >= 0){
//						MainApplication.setMileage(auto.getMileage());
//					}
//					if(auto.getAuto_time() >= 0){
//						MainApplication.setAuto_time(auto.getAuto_time());
//					}
//					if(auto.getOil() >= 0){
//						MainApplication.setOil(auto.getOil());
//					}
//					if(auto.getAuto_coolant() >= 0){
//						MainApplication.setAuto_coolant(auto.getAuto_coolant());
//					}
//					if(auto.getFuel_consumption() >= 0){
//						MainApplication.setFuel_consumption(auto.getFuel_consumption());
//					}
//					if(auto.getStart_date() != null){
//						MainApplication.setStart_date(auto.getStart_date());
//					}
//					if(auto.getFuel_efficienct() >= 0){
//						MainApplication.setFuel_efficienct(auto.getFuel_efficienct());
//					}
//					if(auto.getDrive_time() >= 0){
//						MainApplication.setDrive_time(auto.getDrive_time());
//					}
//					if(auto.getDrive_down_speed() >= 0){
//						MainApplication.setDrive_down_speed(auto.getDrive_down_speed());
//					}
//					if(auto.getDrive_up_speed() >= 0){
//						MainApplication.setDrive_up_speed(auto.getDrive_up_speed());
//					}
//					if(auto.getIdle() >= 0){
//						MainApplication.setIdle(auto.getIdle());
//					}
//					if(auto.getDrive() >= 0){
//						MainApplication.setDrive(auto.getDrive());
//					}
//					if(auto.getEngine_oil() >= 0){
//						MainApplication.setEngine_oil(auto.getEngine_oil());
//					}
//					if(auto.getTire() >= 0){
//						MainApplication.setTire(auto.getTire());
//					}
//					if(auto.getBrake_lining() >= 0){
//						MainApplication.setBrake_lining(auto.getBrake_lining());
//					}
//					if(auto.getAircon_filter() >= 0){
//						MainApplication.setAircon_filter(auto.getAircon_filter());
//					}
//					if(auto.getWiper() >= 0){
//						MainApplication.setWiper(auto.getWiper());
//					}
//					if(auto.getEquip_coolant() >= 0){
//						MainApplication.setEquip_coolant(auto.getEquip_coolant());
//					}
//					if(auto.getTransmission_oil() >= 0){
//						MainApplication.setTransmission_oil(auto.getTransmission_oil());
//					}
//					if(auto.getBattery() >= 0){
//						MainApplication.setBattery(auto.getBattery());
//					}
//					if(auto.getEngine_oil_day() != null){
//						MainApplication.setEngine_oil_day(auto.getEngine_oil_day());
//					}
//					if(auto.getTire_day() != null){
//						MainApplication.setTire_day(auto.getTire_day());
//					}
//					if(auto.getBrake_lining_day() != null){
//						MainApplication.setBrake_lining_day(auto.getBrake_lining_day());
//					}
//					if(auto.getAircon_filter_day() != null){
//						MainApplication.setAircon_filter_day(auto.getAircon_filter_day());
//					}
//					if(auto.getWiper_day() != null){
//						MainApplication.setWiper_day(auto.getWiper_day());
//					}
//					if(auto.getEquip_coolant_day() != null){
//						MainApplication.setEquip_coolant_day(auto.getEquip_coolant_day());
//					}
//					if(auto.getTransmission_oil_day() != null){
//						MainApplication.setTransmission_oil_day(auto.getTransmission_oil_day());
//					}
//					if(auto.getBattery_day() != null){
//						MainApplication.setBattery_day(auto.getBattery_day());
//					}
                    if (auto.getDay() != null) {
                        MainApplication.setDay(auto.getDay());
                    }
//					if(auto.getSave_mileage() > 0){
//						MainApplication.setSava_mileage(auto.getSave_mileage());
//					}

                    mCarName = MainApplication.getmUserCarName();
                    if (mCarName != null && !"".equals(mCarName) && mCarName.length() > 0) {
                        mTitleCarName.setText(mCarName);
                    } else {
                        mTitleCarName.setText("차량이름을 입력해주세요.");
                    }
                    CarLLog.w(TAG, "mCarName: " + mCarName);

//					mdbMileage = MainApplication.getMileage();
//					mCutDbMileage = String.format("%.2f", mdbMileage);
//					mdbSaveMileage = MainApplication.getSava_mileage();

                    mUiHandler.sendEmptyMessage(0);
                }

            }

            return false;
        }
    });

    /**
     * item DB를 받아오는곳
     */
    public class AllDBAsycTask extends AsyncTask<Void, Void, AllDataContentData> {

        private Handler mStopHandler = null;

        /**
         * @param context
         * @param stopHandler
         */
        public AllDBAsycTask(Context context, Handler stopHandler) {
            mStopHandler = stopHandler;
        }

        @Override
        protected AllDataContentData doInBackground(Void... params) {
            AllDataContentData alldatainfo = null;
            try {
                // DB 받아오는 용도
                alldatainfo = AllDataDB();

                if (!isCancelled()) {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return alldatainfo;
        }


        @Override
        protected void onPostExecute(AllDataContentData result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            Message msg = mStopHandler.obtainMessage();
            msg.obj = result;
            msg.arg1 = MainApplication.handlerAuto;
            mStopHandler.sendMessage(msg);
            onCancelled();

        }

        @Override
        protected void onPreExecute() {
            // Things to be done before execution of long running operation. For
            // example showing ProgessDialog
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // Things to be done while execution of long running operation is in
            // progress. For example updating ProgessDialog
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();

            if (mStopHandler != null) {
                mStopHandler = null;
            }

        }
    }
}
