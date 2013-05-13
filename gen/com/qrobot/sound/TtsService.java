/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\workspace\\QroService\\src\\com\\qrobot\\sound\\TtsService.aidl
 */
package com.qrobot.sound;
public interface TtsService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.qrobot.sound.TtsService
{
private static final java.lang.String DESCRIPTOR = "com.qrobot.sound.TtsService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.qrobot.sound.TtsService interface,
 * generating a proxy if needed.
 */
public static com.qrobot.sound.TtsService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.qrobot.sound.TtsService))) {
return ((com.qrobot.sound.TtsService)iin);
}
return new com.qrobot.sound.TtsService.Stub.Proxy(obj);
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
case TRANSACTION_isAudioFree:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isAudioFree();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playTts:
{
data.enforceInterface(DESCRIPTOR);
com.qrobot.sound.TtsData _arg0;
if ((0!=data.readInt())) {
_arg0 = com.qrobot.sound.TtsData.CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
boolean _result = this.playTts(_arg0);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_stopAudio:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.stopAudio();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playMedia:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.playMedia(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playPraviteMediaForName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.playPraviteMediaForName(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_playPraviteMediaForId:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.playPraviteMediaForId(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.qrobot.sound.TtsService
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
	 * 播放是否空闲
	 */
@Override public boolean isAudioFree() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isAudioFree, _data, _reply, 0);
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
	 * 播放合成文本
	 */
@Override public boolean playTts(com.qrobot.sound.TtsData data) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((data!=null)) {
_data.writeInt(1);
data.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_playTts, _data, _reply, 0);
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
	 * 停止播放
	 */
@Override public boolean stopAudio() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopAudio, _data, _reply, 0);
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
	 * 播放本地媒体文件
	 */
@Override public boolean playMedia(java.lang.String fileName, java.lang.String broadcastKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(fileName);
_data.writeString(broadcastKey);
mRemote.transact(Stub.TRANSACTION_playMedia, _data, _reply, 0);
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
	 * 按照名字播放私有媒体文件
	 */
@Override public boolean playPraviteMediaForName(java.lang.String name, java.lang.String broadcastKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(broadcastKey);
mRemote.transact(Stub.TRANSACTION_playPraviteMediaForName, _data, _reply, 0);
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
	 * 按照id播放私有媒体文件
	 */
@Override public boolean playPraviteMediaForId(int id, java.lang.String broadcastKey) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(id);
_data.writeString(broadcastKey);
mRemote.transact(Stub.TRANSACTION_playPraviteMediaForId, _data, _reply, 0);
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
static final int TRANSACTION_isAudioFree = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_playTts = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_stopAudio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_playMedia = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_playPraviteMediaForName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_playPraviteMediaForId = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
}
/**
	 * 播放是否空闲
	 */
public boolean isAudioFree() throws android.os.RemoteException;
/**
	 * 播放合成文本
	 */
public boolean playTts(com.qrobot.sound.TtsData data) throws android.os.RemoteException;
/**
	 * 停止播放
	 */
public boolean stopAudio() throws android.os.RemoteException;
/**
	 * 播放本地媒体文件
	 */
public boolean playMedia(java.lang.String fileName, java.lang.String broadcastKey) throws android.os.RemoteException;
/**
	 * 按照名字播放私有媒体文件
	 */
public boolean playPraviteMediaForName(java.lang.String name, java.lang.String broadcastKey) throws android.os.RemoteException;
/**
	 * 按照id播放私有媒体文件
	 */
public boolean playPraviteMediaForId(int id, java.lang.String broadcastKey) throws android.os.RemoteException;
}
