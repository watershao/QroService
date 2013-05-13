package com.qrobot.service;

import android.app.Service;
import android.content.Intent;

import android.os.IBinder;

import android.os.RemoteException;
import android.util.Log;

import com.qrobot.sensor.QrobotSensor;

public class QrobotSensorAidlService extends Service {

	private static final String tag = "QrobotSensorAidlService:";
	
	private static QrobotSensor qrobotSensor = null;

	public static final String SENSOR_ACTION = "com.qrobot.service.QrobotSensorAidlService";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(tag, "onCreate");
		qrobotSensor = new QrobotSensor();
		int init = qrobotSensor.initSensorSystem();
		Log.w(tag, "initSensorSystem:"+init);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy");
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
		return super.onUnbind(intent);
	}
	

	public QrobotSensorService.Stub stub = new QrobotSensorService.Stub() {


		@Override
		public void irVolumeUp() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irVolumeUp();
		}

		@Override
		public void irVolumeDown() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irVolumeDown();
		}

		@Override
		public void irChanelUp() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irChanelUp();
		}

		@Override
		public void irChanelDown() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irChanelDown();
		}

		@Override
		public void irShutdown() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irShutdown();
		}

		@Override
		public void irSetup() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irSetup();
		}

		@Override
		public void irNumKey(int num) throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.irNumKey(num);
		}

		@Override
		public float tempRead() throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			return qrobotSensor.tempRead();
		}

		@Override
		public void setRTCType(int typeCode) throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.setRTCType(typeCode);
		}

		@Override
		public void lktRegistration(byte[] id_code, int address)
				throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			qrobotSensor.lktRegistration(id_code, address);
		}

		@Override
		public byte[] lktCertification(int address) throws RemoteException {
			// TODO Auto-generated method stub
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			return qrobotSensor.lktCertification(address);
		}

		@Override
		public byte[] lktID() throws RemoteException {
			// TODO Auto-generated method stub
			
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			
			return qrobotSensor.lktID();
		}

		@Override
		public byte[] lktEncryption(byte[] random, byte algorithm_type)
				throws RemoteException {
			// TODO Auto-generated method stub
			
			if (qrobotSensor == null) {
				qrobotSensor = new QrobotSensor();
			}
			return qrobotSensor.lktEncryption(random, algorithm_type);
		}
		
	};
}
