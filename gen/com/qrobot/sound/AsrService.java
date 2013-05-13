/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\QroService\\src\\com\\qrobot\\sound\\AsrService.aidl
 */
package com.qrobot.sound;
public interface AsrService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qrobot.sound.AsrService
{
private static final java.lang.String DESCRIPTOR = "com.qrobot.sound.AsrService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qrobot.sound.AsrService interface,
 * generating a proxy if needed.
 */
public static com.qrobot.sound.AsrService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qrobot.sound.AsrService))) {
return ((com.qrobot.sound.AsrService)iin);
}
return new com.qrobot.sound.AsrService.Stub.Proxy(obj);
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
case TRANSACTION_setResponseTimeout:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setResponseTimeout(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSpeechTimeout:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setSpeechTimeout(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSensitivity:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setSensitivity(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_changScenceFileString:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.changScenceFileString(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_changScenceFileByteArray:
{
data.enforceInterface(DESCRIPTOR);
byte[] _arg0;
_arg0 = data.createByteArray();
int _result = this.changScenceFileByteArray(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_changScenceFilePath:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.changScenceFilePath(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_beginLexicon:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
int _result = this.beginLexicon(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addLexiconItem:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _arg1;
_arg1 = data.readInt();
int _result = this.addLexiconItem(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_endLexicon:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.endLexicon();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopRecognize:
{
data.enforceInterface(DESCRIPTOR);
this.stopRecognize();
reply.writeNoException();
return true;
}
case TRANSACTION_startRecognize:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.startRecognize(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_isAsrFree:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isAsrFree();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_saveRecord:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _result = this.saveRecord(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_startCloudAsr:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.startCloudAsr();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_startFileRecord:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.startFileRecord(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopRecord:
{
data.enforceInterface(DESCRIPTOR);
this.stopRecord();
reply.writeNoException();
return true;
}
case TRANSACTION_startFileAsr:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _arg2;
_arg2 = (0!=data.readInt());
int _result = this.startFileAsr(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.qrobot.sound.AsrService
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
	 * 用户反应超时参数设置，预置为3000 毫秒
	 * @param milTimes 取值为 1000～5000，0为关闭超时单位:毫秒
	 */
@Override public void setResponseTimeout(int timeout) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(timeout);
mRemote.transact(Stub.TRANSACTION_setResponseTimeout, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 语音超时参数设置，预置为4000 毫秒
	 * @param milTimes 取值为 1000～20000，0为关闭超时，单位:毫秒
	 */
@Override public void setSpeechTimeout(int milTimes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(milTimes);
mRemote.transact(Stub.TRANSACTION_setSpeechTimeout, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 设置敏感度
	 * @param mode 0-5 值越大敏感度越高
	 */
@Override public void setSensitivity(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_setSensitivity, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 根据字符串创建相应的文法网络
	 * @param s
	 * @return 0,创建文法网络成功并存储;
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
@Override public int changScenceFileString(java.lang.String s) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(s);
mRemote.transact(Stub.TRANSACTION_changScenceFileString, _data, _reply, 0);
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
	 * 分步调用模式创建文法网络的一个词典单元，开始
	 * <p>提供一种辅助的创建文法单元的接口，用户先调用EsrBeginLexicon，然后逐次调用EsrAddLexiconItem，最后调用EsrEndLexicon完成词典单元创建。
	 *	这套接口仅限于创建词典单元，不能够创建文法网络单元。支持创建空词典，即不调用 EsrAddLexiconItem。
	 * @param lexiconName 词典名称，name 字符串内容长度限制为1 至8 字符，字符串必须由英文字母和数字组成
	 * @param isPersonName 是否作为人名特殊处理
	 * @return  0,成功；3,参数错误;
	 * <p>11,错误的调用，状态非法;
	 * <p>17,词典单元名称非法;
	 */
@Override public int changScenceFileByteArray(byte[] buffer) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeByteArray(buffer);
mRemote.transact(Stub.TRANSACTION_changScenceFileByteArray, _data, _reply, 0);
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
	 * 根据场景路径创建相应的文法网络
	 * @param path 文件路径
	 * @return 0,创建文法网络成功并存储;
	 * <p> -1,文件不存在
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
@Override public int changScenceFilePath(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_changScenceFilePath, _data, _reply, 0);
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
	 * 分步调用模式创建文法网络的一个词典单元，开始
	 * <p>提供一种辅助的创建文法单元的接口，用户先调用EsrBeginLexicon，然后逐次调用EsrAddLexiconItem，最后调用EsrEndLexicon完成词典单元创建。
	 *	这套接口仅限于创建词典单元，不能够创建文法网络单元。支持创建空词典，即不调用 EsrAddLexiconItem。
	 * @param lexiconName 词典名称，name 字符串内容长度限制为1 至8 字符，字符串必须由英文字母和数字组成
	 * @param isPersonName 是否作为人名特殊处理
	 * @return  0,成功；3,参数错误;
	 * <p>11,错误的调用，状态非法;
	 * <p>17,词典单元名称非法;
	 */
@Override public int beginLexicon(java.lang.String lexiconName, boolean isPersonName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(lexiconName);
_data.writeInt(((isPersonName)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_beginLexicon, _data, _reply, 0);
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
	 * 添加词条,如果多次加入相同 ID 和相同词条文本，则在建词典文法时只会保留一条。
	 * @param word 词条文本，UTF-16 字符串，大小端必须与应用平台相同。词条文本长度不应超过40 字符，若超过将进行截断，
	 * 					只处理前40 字符。
	 * @param id 本命令词用户指定 ID。ID 必须在0～2的32次方（不包括2的32次方）的范围内。
	 * @return 0，词条添加成功；3，参数错误；
	 * <p>7,WorkSpcae 内存不足，无法再进行添加（最后一条没有添加）
	 * <p>11,错误的调用，状态非法
	 */
@Override public int addLexiconItem(java.lang.String word, int id) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(word);
_data.writeInt(id);
mRemote.transact(Stub.TRANSACTION_addLexiconItem, _data, _reply, 0);
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
	 * 完成分步模式创建词典单元。
	 * @return 0,创建成功并存储;3,参数错误；
	 * <p>7,内存不足；
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败
	 * <p>11,错误的调用，状态非法
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
@Override public int endLexicon() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_endLexicon, _data, _reply, 0);
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
	 * 停止识别
	 */
@Override public void stopRecognize() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopRecognize, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 开始识别
	 */
@Override public boolean startRecognize(java.lang.String key, java.lang.String sceneName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(key);
_data.writeString(sceneName);
mRemote.transact(Stub.TRANSACTION_startRecognize, _data, _reply, 0);
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
	 * 判断识别模块是否空闲
	 */
@Override public boolean isAsrFree() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isAsrFree, _data, _reply, 0);
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
	 * 是否保存录音
	 */
@Override public boolean saveRecord(boolean isSave) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isSave)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_saveRecord, _data, _reply, 0);
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
	 * 开启云识别
	 */
@Override public boolean startCloudAsr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startCloudAsr, _data, _reply, 0);
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
	 * 开始录音，并保存
	 * @param audioFileName 保存的录音文件名称,默认为/mnt/sdcard/qrobot/record/voice.pcm
	 * @return
	 */
@Override public int startFileRecord(java.lang.String audioFileName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(audioFileName);
mRemote.transact(Stub.TRANSACTION_startFileRecord, _data, _reply, 0);
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
	 * 停止录音
	 */
@Override public void stopRecord() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopRecord, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	 * 开始录音文件识别，目前仅能使用pcm文件
	 * @param sceneName 场景文件名称
	 * @param audioFileName 录音文件名称
	 * @param openCloudAsr 是否开启云识别
	 * @return
	 */
@Override public int startFileAsr(java.lang.String sceneName, java.lang.String audioFileName, boolean openCloudAsr) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(sceneName);
_data.writeString(audioFileName);
_data.writeInt(((openCloudAsr)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_startFileAsr, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_setResponseTimeout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setSpeechTimeout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setSensitivity = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_changScenceFileString = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_changScenceFileByteArray = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_changScenceFilePath = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_beginLexicon = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_addLexiconItem = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_endLexicon = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_stopRecognize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_startRecognize = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_isAsrFree = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_saveRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_startCloudAsr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_startFileRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_stopRecord = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_startFileAsr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
}
/**
	 * 用户反应超时参数设置，预置为3000 毫秒
	 * @param milTimes 取值为 1000～5000，0为关闭超时单位:毫秒
	 */
public void setResponseTimeout(int timeout) throws android.os.RemoteException;
/**
	 * 语音超时参数设置，预置为4000 毫秒
	 * @param milTimes 取值为 1000～20000，0为关闭超时，单位:毫秒
	 */
public void setSpeechTimeout(int milTimes) throws android.os.RemoteException;
/**
	 * 设置敏感度
	 * @param mode 0-5 值越大敏感度越高
	 */
public void setSensitivity(int mode) throws android.os.RemoteException;
/**
	 * 根据字符串创建相应的文法网络
	 * @param s
	 * @return 0,创建文法网络成功并存储;
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
public int changScenceFileString(java.lang.String s) throws android.os.RemoteException;
/**
	 * 分步调用模式创建文法网络的一个词典单元，开始
	 * <p>提供一种辅助的创建文法单元的接口，用户先调用EsrBeginLexicon，然后逐次调用EsrAddLexiconItem，最后调用EsrEndLexicon完成词典单元创建。
	 *	这套接口仅限于创建词典单元，不能够创建文法网络单元。支持创建空词典，即不调用 EsrAddLexiconItem。
	 * @param lexiconName 词典名称，name 字符串内容长度限制为1 至8 字符，字符串必须由英文字母和数字组成
	 * @param isPersonName 是否作为人名特殊处理
	 * @return  0,成功；3,参数错误;
	 * <p>11,错误的调用，状态非法;
	 * <p>17,词典单元名称非法;
	 */
public int changScenceFileByteArray(byte[] buffer) throws android.os.RemoteException;
/**
	 * 根据场景路径创建相应的文法网络
	 * @param path 文件路径
	 * @return 0,创建文法网络成功并存储;
	 * <p> -1,文件不存在
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
public int changScenceFilePath(java.lang.String path) throws android.os.RemoteException;
/**
	 * 分步调用模式创建文法网络的一个词典单元，开始
	 * <p>提供一种辅助的创建文法单元的接口，用户先调用EsrBeginLexicon，然后逐次调用EsrAddLexiconItem，最后调用EsrEndLexicon完成词典单元创建。
	 *	这套接口仅限于创建词典单元，不能够创建文法网络单元。支持创建空词典，即不调用 EsrAddLexiconItem。
	 * @param lexiconName 词典名称，name 字符串内容长度限制为1 至8 字符，字符串必须由英文字母和数字组成
	 * @param isPersonName 是否作为人名特殊处理
	 * @return  0,成功；3,参数错误;
	 * <p>11,错误的调用，状态非法;
	 * <p>17,词典单元名称非法;
	 */
public int beginLexicon(java.lang.String lexiconName, boolean isPersonName) throws android.os.RemoteException;
/**
	 * 添加词条,如果多次加入相同 ID 和相同词条文本，则在建词典文法时只会保留一条。
	 * @param word 词条文本，UTF-16 字符串，大小端必须与应用平台相同。词条文本长度不应超过40 字符，若超过将进行截断，
	 * 					只处理前40 字符。
	 * @param id 本命令词用户指定 ID。ID 必须在0～2的32次方（不包括2的32次方）的范围内。
	 * @return 0，词条添加成功；3，参数错误；
	 * <p>7,WorkSpcae 内存不足，无法再进行添加（最后一条没有添加）
	 * <p>11,错误的调用，状态非法
	 */
public int addLexiconItem(java.lang.String word, int id) throws android.os.RemoteException;
/**
	 * 完成分步模式创建词典单元。
	 * @return 0,创建成功并存储;3,参数错误；
	 * <p>7,内存不足；
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败
	 * <p>11,错误的调用，状态非法
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
public int endLexicon() throws android.os.RemoteException;
/**
	 * 停止识别
	 */
public void stopRecognize() throws android.os.RemoteException;
/**
	 * 开始识别
	 */
public boolean startRecognize(java.lang.String key, java.lang.String sceneName) throws android.os.RemoteException;
/**
	 * 判断识别模块是否空闲
	 */
public boolean isAsrFree() throws android.os.RemoteException;
/**
	 * 是否保存录音
	 */
public boolean saveRecord(boolean isSave) throws android.os.RemoteException;
/**
	 * 开启云识别
	 */
public boolean startCloudAsr() throws android.os.RemoteException;
/**
	 * 开始录音，并保存
	 * @param audioFileName 保存的录音文件名称,默认为/mnt/sdcard/qrobot/record/voice.pcm
	 * @return
	 */
public int startFileRecord(java.lang.String audioFileName) throws android.os.RemoteException;
/**
	 * 停止录音
	 */
public void stopRecord() throws android.os.RemoteException;
/**
	 * 开始录音文件识别，目前仅能使用pcm文件
	 * @param sceneName 场景文件名称
	 * @param audioFileName 录音文件名称
	 * @param openCloudAsr 是否开启云识别
	 * @return
	 */
public int startFileAsr(java.lang.String sceneName, java.lang.String audioFileName, boolean openCloudAsr) throws android.os.RemoteException;
}
