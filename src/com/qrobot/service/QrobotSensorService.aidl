package com.qrobot.service;

interface QrobotSensorService{
	
	/**
	 * 设置遥控器遥控对象的类型
	 * @param typeCode 1,tv;2,dvd
	 */
	void setRTCType(in int typeCode);
	
	/**
	 * 红外控制音量调大
	 */
	void irVolumeUp();
	
	/**
	 * 红外控制音量调小
	 */
	void irVolumeDown();

	/**
	 * 红外控制频道调大
	 */
	void irChanelUp();	

	/**
	 * 红外控制频道调小
	 */
	void irChanelDown();
	
	/**
	 * 红外控制关机
	 */
	void irShutdown();
	
	/**
	 * 红外控制设置
	 */
	void irSetup();	
	
	/**
	 * 红外控制数字按键
	 * @param num 0-9
	 */
	void irNumKey(in int num);	
	
	/**
	 * 获取温度
	 * @return 温度值
	 */
	float tempRead();	
	
	/**
	 * 往加密芯片指定地址写入注册码 
	 * @param id_code 18个字节的注册码
	 * @param address 语音ID 1;
				<p> 机器人ID 2;
				<p> 验证码前18位：3;
				<p> 验证码后18为：4;
				默认为机器人id
	 */
	void lktRegistration(in byte[] id_code, in int address);
	
	/**
	 * 从加密芯片指定地址读出注册码 
	 * @param address 语音ID 1;
				<p> 机器人ID 2;
				<p> 验证码前18位：3;
				<p> 验证码后18为：4;
				默认为机器人id
	 * @return 注册码
	 */
	byte[] lktCertification(in int address);
	
	/**
	 * 获取加密芯片ID号
	 * @return ID号
	 */
	byte[] lktID();
	
	/**
	 * 加密认证：将随机码加密后和低10位ID一同返回 
	 * @param  随机码random[], 加密方式algorithm_type
	 * @return 注册码
	 */
	byte[] lktEncryption(in byte[] random, in byte algorithm_type);
		
}