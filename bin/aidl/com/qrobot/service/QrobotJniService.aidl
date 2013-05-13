package com.qrobot.service;

interface QrobotJniService{
	
	/**
	 * 驱动是否初始化完成
	 * @return
	 */
	boolean isInit();
	
	/**
	 * 初始化驱动
	 */	
	int init();

	/**
	 * 设置心形灯
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */	
	void setHeartLed(in int led, in int level);

	/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 数据缓冲区
	 * @param offset 该数据在图片中的偏移位置
	 * @param len 数据长度(最大值：4096)
	 * @param fst_frame 	指示是否为该幅图片的第一段数据    0：不是    1：是
	 */	
	void setLcdDisplay(in int eye,in byte[] data,in int offset,in int len,in int fst_frame);

    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */	
	void setEye(in int eye, in String eyePath);
	
    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param count 显示次数
     * @param eyePath 图片路径(仅用于RGB565的bmp和bin图片文件)
     */	
	void showEye(in int eye,in int count, in String eyePath);	

    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
	void setEyeResource(in int eye,in byte[] data);
	
	/**
	 * 显示系统默认图片
	 * @param count 显示次数
	 * @param num 目前仅限于1-43
	 */
	void showSysImage(in int count, in int num);
	
	/**
	 * 初始化眼睛屏幕
	 */
	void resetEye();

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
	void setMotor(in int motor,in int mode,in int speed,in int pos_time,in int trap_flag);

    /**
     * 头部运动设置
     * @param motor 电机部位，0-头上下，1-头左右
     * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)
     * @param speed  速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
     * @param position 目标点，对于头部，指的是目标位置，范围：0~255
     * @param trap_flag  电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
     */	
	void setHeadMotor(in int motor,in int mode,in int speed,in int position,in int trap_flag);

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
	void setSwingMotor(in int motor,in int mode,in int speed,in int time,in int trap_flag);

	/**
	 * 电机部位查询
	 * @param motor 电机部位  0-头上下  1-头左右
	 * @return 	>0 - 成功   ，-1- 失败
	 */	
	int getMotorPos(in int motor);

	/**
	 * 设置脚本文件路径
	 * @param path 脚本文件路径
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
	void setScriptPath(in String path, in int type);
	
	/**
	 * 停止脚本解析
	 * @param type 0,为运动脚本。1，为眼睛脚本。2为所有脚本
	 */
	void stopScriptParse(in int type);
	
	/**
	 * 判断脚本解析是否正在进行
	 * @return
	 */
	boolean isScriptRun();
	
	/**
	 * 判断眼睛脚本解析是否正在进行
	 * @return
	 */
	boolean isEyeScriptRun();
	
	/**
	 * 判断运动脚本解析是否正在进行
	 * @return
	 */
	boolean isMotionScriptRun();
}