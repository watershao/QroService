package com.qrobot.service;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import android.os.RemoteException;
import android.util.Log;

import com.qrobot.motion.BaseMotion;
import com.qrobot.motion.Eye;
import com.qrobot.motion.MotionScript;
import com.ritech.qrobot.MSG_CODE;
import com.ritech.qrobot.QrobotJni;
import com.ritech.qrobot.TchkeyCallback;

public class QrobotJniAidlService extends Service {

	private static final String tag = "QrobotJniAidlService:";
	
	private static QrobotJni qrobotJni = null;
	
	private static MotionScript motionScript = null;

	public static final String TCHKEY_ACTION = "com.qrobot.service.TchkeyJniAidlService";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(tag, "onCreate");
		if (qrobotJni == null) {
			qrobotJni = new QrobotJni();
		}
		if (motionScript == null) {
			motionScript = new MotionScript();
		}
		
		BaseMotion.setQrobotJni(qrobotJni);
		if (!qrobotJni.isInit()) {
			qrobotJni.init();
		}
		QrobotJni.setTchkeyCallback(callback);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy");
//		onBind(new Intent(TCHKEY_ACTION));
//		Log.d(tag, "reOnBind");
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(tag, "onBind");
		return stub;
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.d(tag, "onRebind");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(tag, "onUnbind");
//		onBind(new Intent(TCHKEY_ACTION));
//		Log.d(tag, "reUnBind");
//		QrobotJni.destoryDriver();
		return super.onUnbind(intent);
	}
	
	private TchkeyCallback callback = new TchkeyCallback() {
		
		@Override
		public void touchFinish(int resultCode, int featureCode) {
			// TODO Auto-generated method stub
			Log.d(tag, "touchFinish: resultCode = " + resultCode+"featureCode:"+featureCode);
			if (!(resultCode == 1||resultCode==2||resultCode==4||resultCode==8||resultCode==16)) {
				return;
			}
			Intent intent = new Intent(TCHKEY_ACTION);
			intent.putExtra(MSG_CODE.RESULT_CODE, resultCode);
			intent.putExtra(MSG_CODE.FEATURE_CODE, featureCode);

			QrobotJniAidlService.this.sendBroadcast(intent);
		}
	};

	public QrobotJniService.Stub stub = new QrobotJniService.Stub() {
		
		@Override
		public int init() throws RemoteException {
			if (qrobotJni == null) {
				qrobotJni = new QrobotJni();
			}
			return qrobotJni.init();
		}

		@Override
		public void setHeartLed(int led, int level) throws RemoteException {
			// TODO Auto-generated method stub
			QrobotJni.setHeartLed(led, level);
		}

		@Override
		public void setLcdDisplay(int eye, byte[] data, int offset, int len,
				int fst_frame) throws RemoteException {
			// TODO Auto-generated method stub
			QrobotJni.setLcdDisplay(eye, data, offset, len, fst_frame);
		}

		@Override
		public void setEye(int eye, String eyePath) throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotJni == null) {
				qrobotJni = new QrobotJni();
			}
			qrobotJni.setEye(eye, eyePath);
			
		}

		@Override
		public void setMotor(int motor, int mode, int speed, int pos_time,
				int trap_flag) throws RemoteException {
			// TODO Auto-generated method stub
			QrobotJni.setMotor(motor, mode, speed, pos_time, trap_flag);
		}

		@Override
		public void setHeadMotor(int motor, int mode, int speed,
				int position, int trap_flag) throws RemoteException {
			// TODO Auto-generated method stub
			QrobotJni.setHeadMotor(motor, mode, speed, position, trap_flag);
		}

		@Override
		public void setSwingMotor(int motor, int mode, int speed,
				int time, int trap_flag) throws RemoteException {
			// TODO Auto-generated method stub
			QrobotJni.setSwingMotor(motor, mode, speed, time, trap_flag);
		}

		@Override
		public int getMotorPos(int motor) throws RemoteException {
			// TODO Auto-generated method stub
			return QrobotJni.getMotorPos(motor);
		}

		@Override
		public boolean isInit() throws RemoteException {
			// TODO Auto-generated method stub
			return QrobotJni.isInit();
		}

		@Override
		public void setEyeResource(int eye, byte[] data) throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotJni == null) {
				qrobotJni = new QrobotJni();
			}
			qrobotJni.setEyeResource(eye, data);
		}

		@Override
		public void showSysImage(int count, int num) throws RemoteException {
			// TODO Auto-generated method stub
			Eye.showSysImage(count, num);
		}

		@Override
		public void resetEye() throws RemoteException {
			// TODO Auto-generated method stub
			Eye.resetEye();
		}

		@Override
		public void showEye(int eye, int count, String eyePath)
				throws RemoteException {
			// TODO Auto-generated method stub
			Eye.showEye(eye, count, eyePath);
		}

		@Override
		public void setScriptPath(String path, int type) throws RemoteException {
			// TODO Auto-generated method stub
			if (motionScript == null) {
				motionScript = new MotionScript();
			}
			motionScript.setScriptPath(path, type);
		}

		@Override
		public void stopScriptParse(int type) throws RemoteException {
			// TODO Auto-generated method stub
			if (motionScript == null) {
				motionScript = new MotionScript();
			}
			motionScript.stopScriptParse(type);
		}

		@Override
		public boolean isScriptRun() throws RemoteException {
			// TODO Auto-generated method stub
			if (motionScript == null) {
				motionScript = new MotionScript();
			}
			return motionScript.isScriptRun();
		}

		@Override
		public boolean isEyeScriptRun() throws RemoteException {
			// TODO Auto-generated method stub
			if (motionScript == null) {
				motionScript = new MotionScript();
			}
			return motionScript.isEyeScriptRun();
		}

		@Override
		public boolean isMotionScriptRun() throws RemoteException {
			// TODO Auto-generated method stub
			if (motionScript == null) {
				motionScript = new MotionScript();
			}
			return motionScript.isMotionScriptRun();
		}
	};
}
