package com.qrobot.motion;

import java.util.Map;

public class ItemMotion {

	private String MotionPath;
	private String VoicePath;
	private String TextPath;
	private String EyeShowPath;
	
	public ItemMotion(String voice,String text,String motion,String eye) {
		// TODO Auto-generated constructor stub
		InitItem(voice,text,motion,eye);
	}
	public ItemMotion(Map<String,String> map)
	{
		if (map != null && !map.isEmpty())
		{
			this.VoicePath   = map.get("voice");
			this.TextPath    = map.get("text");
	    	this.MotionPath  = map.get("motion");
	    	this.EyeShowPath = map.get("eye"); 
		}    	   	
	}
    private void InitItem(String voice,String text,String motion,String eye)
    {
    	this.VoicePath   = voice;
    	this.TextPath    = text;
    	this.MotionPath  = motion;
    	this.EyeShowPath = eye;    	
    }
    
    /**
     * 获取声音路径
     * @return
     */
    public String getV()
    {
    	return this.VoicePath;
    }
    
    /**
     * 获取文本路径
     * @return
     */
    public String getT()
    {
    	return this.TextPath;
    }
    
    /**
     * 获取运动脚本路径
     * @return
     */
    public String getM()
    {
    	return this.MotionPath;
    }
    
    /**
     * 获取眼睛显示路径
     * @return
     */
    public String getE()
    {
    	return this.EyeShowPath;
    }
    
    /**
     * 声音路径是否存在
     * @return
     */
    public boolean isV()
    {
    	return isEmpty(this.VoicePath);
    }
    
    /**
     * 文本路径是否存在
     * @return
     */
    public boolean isT()
    {
    	return isEmpty(this.TextPath);
    }
    
    /**
     * 运动脚本路径是否存在
     * @return
     */
    public boolean isM()
    {
    	return isEmpty(this.MotionPath);
    }
    
    /**
     * 眼睛显示路径是否存在
     * @return
     */
    public boolean isE()
    {
    	return isEmpty(this.EyeShowPath);
    }
    private boolean isEmpty(String t)
    {
    	if (t == null || t.length() ==0)
    		return false;
    	return true;
    }
}
