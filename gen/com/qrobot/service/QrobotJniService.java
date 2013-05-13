/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\QroService\\src\\com\\qrobot\\service\\QrobotJniService.aidl
 */
package com.qrobot.service;
public interface QrobotJniService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qrobot.service.QrobotJniService
{
private static final java.lang.String DESCRIPTOR = "com.qrobot.service.QrobotJniService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qrobot.service.QrobotJniService interface,
 * generating a proxy if needed.
 */
public static com.qrobot.service.QrobotJniService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qrobot.service.QrobotJniService))) {
return ((com.qrobot.service.QrobotJniService)iin);
}
return new com.qrobot.service.QrobotJniService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_isInit:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isInit();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_init:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.init();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setHeartLed:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.setHeartLed(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_setLcdDisplay:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
byte[] _arg1;
_arg1 = data.createByteArray();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.setLcdDisplay(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_setEye:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
this.setEye(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_showEye:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
java.lang.String _arg2;
_arg2 = data.readString();
this.showEye(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_setEyeResource:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
byte[] _arg1;
_arg1 = data.createByteArray();
this.setEyeResource(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_showSysImage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.showSysImage(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_resetEye:
{
data.enforceInterface(DESCRIPTOR);
this.resetEye();
reply.writeNoException();
return true;
}
case TRANSACTION_setMotor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.setMotor(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_setHeadMotor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.setHeadMotor(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_setSwingMotor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
int _arg3;
_arg3 = data.readInt();
int _arg4;
_arg4 = data.readInt();
this.setSwingMotor(_arg0, _arg1, _arg2, _arg3, _arg4);
reply.writeNoException();
return true;
}
case TRANSACTION_getMotorPos:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.getMotorPos(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setScriptPath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
this.setScriptPath(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_stopScriptParse:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.stopScriptParse(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isScriptRun:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isScriptRun();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isEyeScriptRun:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isEyeScriptRun();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isMotionScriptRun:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isMotionScriptRun();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.qrobot.service.QrobotJniService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	 * 驱动是否初始化完成
	 * @return
	 */
@Override public boolean isInit() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isInit, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 初始化驱动
	 */
@Override public int init() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 设置心形灯
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */
@Override public void setHeartLed(int led, int level) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(led);
_data.writeInt(level);
mRemote.transact(Stub.TRANSACTION_setHeartLed, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 数据缓冲区
	 * @param offset 该数据在图片中的偏移位置
	 * @param len 数据长度(最大值：4096)
	 * @param fst_frame 	指示是否为该幅图片的第一段数据    0：不是    1：是
	 */
@Override public void setLcdDisplay(int eye, byte[] data, int offset, int len, int fst_frame) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eye);
_data.writeByteArray(data);
_data.writeInt(offset);
_data.writeInt(len);
_data.writeInt(fst_frame);
mRemote.transact(Stub.TRANSACTION_setLcdDisplay, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */
@Override public void setEye(int eye, java.lang.String eyePath) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eye);
_data.writeString(eyePath);
mRemote.transact(Stub.TRANSACTION_setEye, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param count 显示次数
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */
@Override public void showEye(int eye, int count, java.lang.String eyePath) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eye);
_data.writeInt(count);
_data.writeString(eyePath);
mRemote.transact(Stub.TRANSACTION_showEye, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
@Override public void setEyeResource(int eye, byte[] data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(eye);
_data.writeByteArray(data);
mRemote.transact(Stub.TRANSACTION_setEyeResource, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 显示系统默认图片
	 * @param count 显示次数
	 * @param num 目前仅限于1-43
	 */
@Override public void showSysImage(int count, int num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
_data.writeInt(num);
mRemote.transact(Stub.TRANSACTION_showSysImage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 初始化眼睛屏幕
	 */
@Override public void resetEye() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetEye, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 电机控制
	 * 
	 * @param motor 电机部位，0-头上下，1-头左右，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)，正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param pos_time 目标点，对于头部，指的是目标位置，范围：0~255
   	 *						 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
@Override public void setMotor(int motor, int mode, int speed, int pos_time, int trap_flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(motor);
_data.writeInt(mode);
_data.writeInt(speed);
_data.writeInt(pos_time);
_data.writeInt(trap_flag);
mRemote.transact(Stub.TRANSACTION_setMotor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * 头部运动设置
     * @param motor 电机部位，0-头上下，1-头左右
     * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)
     * @param speed  速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
     * @param position 目标点，对于头部，指的是目标位置，范围：0~255
     * @param trap_flag  电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
     */
@Override public void setHeadMotor(int motor, int mode, int speed, int position, int trap_flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(motor);
_data.writeInt(mode);
_data.writeInt(speed);
_data.writeInt(position);
_data.writeInt(trap_flag);
mRemote.transact(Stub.TRANSACTION_setHeadMotor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 翅膀电机控制
	 * 
	 * @param motor 电机部位，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param time 目标点， 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
@Override public void setSwingMotor(int motor, int mode, int speed, int time, int trap_flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(motor);
_data.writeInt(mode);
_data.writeInt(speed);
_data.writeInt(time);
_data.writeInt(trap_flag);
mRemote.transact(Stub.TRANSACTION_setSwingMotor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 电机部位查询
	 * @param motor 电机部位  0-头上下  1-头左右
	 * @return 	>0 - 成功   ，-1- 失败
	 */
@Override public int getMotorPos(int motor) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(motor);
mRemote.transact(Stub.TRANSACTION_getMotorPos, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 设置脚本文件路径
	 * @param path 脚本文件路径
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
@Override public void setScriptPath(java.lang.String path, int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_setScriptPath, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 停止脚本解析
	 * @param type 0,为运动脚本。1，为眼睛脚本。2为所有脚本
	 */
@Override public void stopScriptParse(int type) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(type);
mRemote.transact(Stub.TRANSACTION_stopScriptParse, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 判断脚本解析是否正在进行
	 * @return
	 */
@Override public boolean isScriptRun() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isScriptRun, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 判断眼睛脚本解析是否正在进行
	 * @return
	 */
@Override public boolean isEyeScriptRun() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isEyeScriptRun, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * 判断运动脚本解析是否正在进行
	 * @return
	 */
@Override public boolean isMotionScriptRun() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isMotionScriptRun, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_isInit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_init = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setHeartLed = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_setLcdDisplay = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_setEye = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_showEye = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_setEyeResource = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_showSysImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_resetEye = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_setMotor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_setHeadMotor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_setSwingMotor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getMotorPos = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setScriptPath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_stopScriptParse = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_isScriptRun = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_isEyeScriptRun = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_isMotionScriptRun = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
}
/**
	 * 驱动是否初始化完成
	 * @return
	 */
public boolean isInit() throws android.os.RemoteException;
/**
	 * 初始化驱动
	 */
public int init() throws android.os.RemoteException;
/**
	 * 设置心形灯
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */
public void setHeartLed(int led, int level) throws android.os.RemoteException;
/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 数据缓冲区
	 * @param offset 该数据在图片中的偏移位置
	 * @param len 数据长度(最大值：4096)
	 * @param fst_frame 	指示是否为该幅图片的第一段数据    0：不是    1：是
	 */
public void setLcdDisplay(int eye, byte[] data, int offset, int len, int fst_frame) throws android.os.RemoteException;
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */
public void setEye(int eye, java.lang.String eyePath) throws android.os.RemoteException;
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param count 显示次数
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */
public void showEye(int eye, int count, java.lang.String eyePath) throws android.os.RemoteException;
/**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
public void setEyeResource(int eye, byte[] data) throws android.os.RemoteException;
/**
	 * 显示系统默认图片
	 * @param count 显示次数
	 * @param num 目前仅限于1-43
	 */
public void showSysImage(int count, int num) throws android.os.RemoteException;
/**
	 * 初始化眼睛屏幕
	 */
public void resetEye() throws android.os.RemoteException;
/**
	 * 电机控制
	 * 
	 * @param motor 电机部位，0-头上下，1-头左右，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)，正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param pos_time 目标点，对于头部，指的是目标位置，范围：0~255
   	 *						 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
public void setMotor(int motor, int mode, int speed, int pos_time, int trap_flag) throws android.os.RemoteException;
/**
     * 头部运动设置
     * @param motor 电机部位，0-头上下，1-头左右
     * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)
     * @param speed  速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
     * @param position 目标点，对于头部，指的是目标位置，范围：0~255
     * @param trap_flag  电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
     */
public void setHeadMotor(int motor, int mode, int speed, int position, int trap_flag) throws android.os.RemoteException;
/**
	 * 翅膀电机控制
	 * 
	 * @param motor 电机部位，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param time 目标点， 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
public void setSwingMotor(int motor, int mode, int speed, int time, int trap_flag) throws android.os.RemoteException;
/**
	 * 电机部位查询
	 * @param motor 电机部位  0-头上下  1-头左右
	 * @return 	>0 - 成功   ，-1- 失败
	 */
public int getMotorPos(int motor) throws android.os.RemoteException;
/**
	 * 设置脚本文件路径
	 * @param path 脚本文件路径
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
public void setScriptPath(java.lang.String path, int type) throws android.os.RemoteException;
/**
	 * 停止脚本解析
	 * @param type 0,为运动脚本。1，为眼睛脚本。2为所有脚本
	 */
public void stopScriptParse(int type) throws android.os.RemoteException;
/**
	 * 判断脚本解析是否正在进行
	 * @return
	 */
public boolean isScriptRun() throws android.os.RemoteException;
/**
	 * 判断眼睛脚本解析是否正在进行
	 * @return
	 */
public boolean isEyeScriptRun() throws android.os.RemoteException;
/**
	 * 判断运动脚本解析是否正在进行
	 * @return
	 */
public boolean isMotionScriptRun() throws android.os.RemoteException;
}
