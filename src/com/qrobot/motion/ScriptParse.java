package com.qrobot.motion;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author v_watershao
 *
 */
public class ScriptParse {

	/**
	 * 资源路径
	 */
	private String path;
	// private String resource;
	/**
	 * 命令列表
	 */
	private List<Node> command = null;
	
	private HashMap<String, TimeLine> timelineMap = null;
	
//	private static ScriptParse m_scriptParse = null;
//	
//	public static ScriptParse getInstance(QrobotJniService qrobotJniService,String path, String resource){
//		if(m_scriptParse == null)
//			m_scriptParse = new ScriptParse(qrobotJniService, path, resource);
//		return m_scriptParse;
//	}
	

	public ScriptParse(String path, String resource) {
		this.path = path;
		initMap();
		initCommand(resource);
	}

	private void initMap() {
		timelineMap = new HashMap<String, TimeLine>();

		timelineMap.put(key_timeline_vhead, new TimeLine());
		timelineMap.put(key_timeline_hhead, new TimeLine());
		timelineMap.put(key_timeline_lwing, new TimeLine());
		timelineMap.put(key_timeline_rwing, new TimeLine());
		timelineMap.put(key_timeline_heart, new TimeLine());
		timelineMap.put(key_timeline_face, new TimeLine());
	}

	private void initCommand(String resource) {
		List<String[]> cmds = checkToItem(resource);
		command = new ArrayList<Node>();

		String[] resetTimeLineKey = new String[]{key_timeline_hhead,
				key_timeline_vhead, key_timeline_lwing, key_timeline_rwing};

		for (String[] keys : cmds) {
			TimeLine tl = timelineMap.get(CMD_TO_TIMELINE_MAP.get(keys[0]));
			if (tl == null) {
				String s = keys[0];
				if (s.startsWith(key_cmd_reset)) {
					Motor.resetHead();
					Motor.resetWing();
					syncTimeLine(resetTimeLineKey);
				} else if (s.startsWith(key_cmd_sync)) {
					String[] featureKey = getFeatureKey(s);
					syncTimeLine(featureKey);
				} else if (s.startsWith(key_cmd_sleep)) {
					String[] featureKeys = getFeatureKey(s);
					int time = Integer.parseInt(keys[1]);
					for (String fk : featureKeys) {
						TimeLine tl2 = timelineMap.get(fk);
						tl2.addTime(time);
					}
				}
			} else {
				command.add(tl.resolve(keys[0], keys[1], keys[2]));
			}
		}
	}

	/**
	 * 存储资源的每一项命令数组到列表
	 * @param resource
	 * @return
	 */
	private List<String[]> checkToItem(String resource) {
		String[] cmds = resource.split(RETURN_KEY);
		int length = cmds.length;
		String[][] keys = new String[length][];
		for (int i = 0; i < length; i++) {
//			System.out.println("cmd"+i+cmds[i]+"$$:"+keys[i][0]+"&"+keys[i][1]+"&"+keys[i][2]);
//			System.out.println("cmd"+i+cmds[i]+"$$:");
			keys[i] = getKeys(cmds[i]);
		}
		List<String[]> list =addAllItemToList(keys, 1, length);
		return list;
	}

	/**
	 * 把所有的资源项添加到列表
	 * @param keys
	 * @param start
	 * @param end
	 * @return
	 */
	int m=0;
	boolean isSub = false;
	boolean isSubStart = false;
	int subEnd = 0;
	private List<String[]> addAllItemToList(String[][] keys, int start, int end) {
		List<String[]> list = new ArrayList<String[]>();
		List<String[]> sublist = new ArrayList<String[]>();
		
		m++;
		int subStart = 0;
		
//		System.out.println("construction1:"+m);
		for (int i = start; i < end; i++) {
			//System.out.println("i:"+i);
			String keyStart = keys[i][0];
			if (keyStart.startsWith(key_cmd_repeatbegin)) {
				int t = Integer.parseInt(keys[i][1]);
				int j = i;
				while (j<end-1) {
					j++;
//System.out.println(t+"j::"+j+"subend:"+subEnd+"size:"+sublist.size());
					String keyEnd = keys[j][0];
					if (keyEnd.startsWith(key_cmd_repeatend)) {
							break;
					}
					if (keyEnd.startsWith(key_cmd_repeatbegin)) {
						subStart = j;
						isSub =false;
						isSubStart = true;
//						System.out.println("subStart:"+subStart);
						sublist = addAllItemToList(keys, subStart, end);
//						isSubStart = false;
						isSub = true;	
						j = subEnd;

					}
				}
				
				for (int j2 = 0; j2 < t; j2++) {
					
					if (isSub) {
						for (int k = i+1; k < subStart; k++) {
								list.add(keys[k]);
							}
						list.addAll(sublist);
						for (int k = subEnd+1; k < j; k++) {
							list.add(keys[k]);
						}
//						isSub = false;
					} else {
						for (int k = i+1; k < j; k++) {
							list.add(keys[k]);
						}

					}
				}
				if (isSubStart) {
					subEnd = j;
					isSubStart = false;
					return list;
					
				}
				i = j;
//				System.out.println("i:;"+i);
//				while (true) {
//					j--;
//					String keyEnd = keys[j][0];
//					if (keyEnd.startsWith(key_cmd_repeatend)) {
//						break;
//					}
//				}
//				List<String[]> sublist = addAllItemToList(keys, i + 1, j - 1);
//				for (int k = 0; k < t; k++) {
//					list.addAll(sublist);
//				}
//				i = j;
			} else {
				list.add(keys[i]);
			}
		}
		return list;
	}

	/**
	 * 获取特征命令关键词
	 * @param cmd
	 * @return
	 */
	private String[] getFeatureKey(String cmd) {
		int start = cmd.indexOf(':') + 1;
		return cmd.substring(start).split(";");
	}

	private void syncTimeLine(String[] keys) {
		int length = keys.length;
		
		TimeLine[] tls = new TimeLine[length];
		int max = 0;
		for (int i = 0; i < length; i++) {
			tls[i] = timelineMap.get(keys[i]);
//			System.out.println("lenghth:"+timelineMap.get(keys[i]));
			max = Math.max(max, tls[i].time);
		}
		for (int i = 0; i < length; i++) {
			tls[i].setTime(max);
		}
	}

	private static final int COMMAND_ITEM_COUNT = 3;

	/**
	 * 将命令以及参数封装成数组
	 * @param cmd
	 * @return
	 */
	private String[] getKeys(String cmd) {
		String[] result = new String[COMMAND_ITEM_COUNT];
		int itemIdx = 0;
		int cmdIdx = 0;
		int cmdLength = cmd.length();
		boolean isStart = false;
		int start = 0;
		int end = 0;

		while (cmdIdx < cmdLength) {
			char c = cmd.charAt(cmdIdx);
//			System.out.println(cmdLength+":"+cmdIdx+"####"+c);
			if (c == ' ' || c == '\t') {
				if (isStart) {
					isStart = false;
					end = cmdIdx;
					//防止行尾有空格或者制表符
					if (itemIdx > COMMAND_ITEM_COUNT-1) {
						break;
					}
					result[itemIdx] = cmd.substring(start, end);
					itemIdx++;
				}
			} else {
				if (!isStart) {
					isStart = true;
					start = cmdIdx;
				}
			}
			
			if (cmdIdx == cmdLength-1) {
				//防止行尾有空格或者制表符
				if (itemIdx > COMMAND_ITEM_COUNT-1) {
					break;
				}
				result[itemIdx] = cmd.substring(start);
				itemIdx++;
				break;
			}
			cmdIdx++;
		}
		return result;
	}

	public boolean isRun() {
		return isRun;
	}

	/**
	 * 启动运动脚本线程
	 */
	public void start() {
		if (!isRun) {
			isRun = true;
			new Thread(sportThread).start();
		}
	}

	/**
	 * 实现运动脚本Runnable接口
	 */
	private Runnable sportThread = new Runnable() {

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			//Log.d("ScriptParse", " ScriptParse thread is Run: " + command.size());
			for (Node node : command) {
				long nowTime = System.currentTimeMillis();
				long sleepTime = node.actionTime - (nowTime - startTime);
				if (sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
//						System.out.println("#####:sleepTime"+sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//Log.d("ScriptParse", " is Run node sleepTime: " + sleepTime);
				if (isStop) {
					break;
				}
				node.exe();
//				System.out.println("#####:node.exe");
			}

			isStop = false;
			isRun = false;
		}
	};

	private boolean isRun = false;
	private boolean isStop = false;

	/**
	 * 停止运动脚本线程
	 */
	public void stop() {
		isStop = true;
	}

	private class TimeLine {

		int time = 0;
		// String key;

		// TimeLine(String key) {
		// this.key = key;
		// }

		void addTime(int t) {
			time += t;
		}

		void setTime(int t) {
			time = t;
		}

		Node resolve(String key, String value1, String value2) {
			Command cmd = CMD_MAP.get(key);
			//System.out.println("cmd:"+cmd);
			// Node node = new Node(this.time, value1, value2, cmd, this);
			Node node = new Node(this.time, value1, value2, cmd);
			this.time += cmd.actionTime(value1, value2);
			return node;
		}
	}

	private class Node {

		int actionTime;
		String value1, value2;
		Command cmd;
		// TimeLine timeLine;

		// Node(int actionTime, String value1, String value2, Command cmd,
		// TimeLine timeLine) {
		Node(int actionTime, String value1, String value2, Command cmd) {
			super();
			this.actionTime = actionTime;
			this.value1 = value1;
			this.value2 = value2;
			this.cmd = cmd;
			// this.timeLine = timeLine;
		}

		void exe() {
			cmd.exe(ScriptParse.this, value1, value2);
//			System.out.println("cmd::::"+value1+"value2:"+value2);
		}
	}

	/**
	 * 定义脚本命令接口
	 * @author v_watershao
	 *
	 */
	private interface Command {

		int actionTime(String cmd1, String cmd2);

		void exe(ScriptParse ss, String cmd1, String cmd2);
	}

	private static final int WING_RANGE_TIME = 20;
	private static final String RETURN_KEY = "\r\n";

	private static final String key_timeline_vhead = "vhead";
	private static final String key_timeline_hhead = "hhead";
	private static final String key_timeline_lwing = "lwing";
	private static final String key_timeline_rwing = "rwing";
	private static final String key_timeline_heart = "heart";
	private static final String key_timeline_face = "eye";

	private static final String key_cmd_heart = "heart";
	private static final String key_cmd_eye = "eye";
	private static final String key_cmd_leye = "leye";
	private static final String key_cmd_reye = "reye";
	private static final String key_cmd_beye = "beye";
	private static final String key_cmd_vhead = "vhead";
	private static final String key_cmd_hhead = "hhead";
	private static final String key_cmd_lwingup = "lwingup";
	private static final String key_cmd_lwingdown = "lwingdown";
	private static final String key_cmd_rwingup = "rwingup";
	private static final String key_cmd_rwingdown = "rwingdown";
	private static final String key_cmd_vheadorg = "vheadorg";
	private static final String key_cmd_hheadorg = "hheadorg";
	private static final String key_cmd_lwingorg = "lwingorg";
	private static final String key_cmd_rwingorg = "rwingorg";

	private static final String key_cmd_reset = "reset";
	private static final String key_cmd_sync = "sync";
	private static final String key_cmd_sleep = "sleep";
	private static final String key_cmd_repeatbegin = "repeatbegin";
	private static final String key_cmd_repeatend = "repeatend";

	private static final String[] COMMOND_KEYS = {key_cmd_heart, key_cmd_eye,
			key_cmd_leye, key_cmd_reye, key_cmd_beye, key_cmd_vhead,
			key_cmd_hhead, key_cmd_lwingup, key_cmd_lwingdown, key_cmd_rwingup,
			key_cmd_rwingdown, key_cmd_vheadorg, key_cmd_hheadorg,
			key_cmd_lwingorg, key_cmd_rwingorg};

	private static final String[] TIMELINE_KEYS = {key_timeline_heart,
			key_timeline_face, key_timeline_face, key_timeline_face,
			key_timeline_face, key_timeline_vhead, key_timeline_hhead,
			key_timeline_lwing, key_timeline_lwing, key_timeline_rwing,
			key_timeline_rwing, key_timeline_vhead, key_timeline_hhead,
			key_timeline_lwing, key_timeline_rwing};

	private static final HashMap<String, Command> CMD_MAP;
	private static final HashMap<String, String> CMD_TO_TIMELINE_MAP;

	/**
	 * 获取RGB的各个值
	 * @param value
	 * @param start
	 * @param end
	 * @return
	 */
	private static final int getColor(String value, int start, int end) {
		String s = value.substring(start + 1, end);
		return Integer.valueOf(s);
	}

	/**
	 * 显示眼睛图片
	 * @param ss
	 * @param eye 显示那个眼睛
	 * @param cmd1  显示次数
	 * @param cmd2  显示路径
	 */
//	private static final void showEye(ScriptParse ss, int eye, String cmd1,
//			String cmd2) {
//		String fileName;
//		if (cmd2.indexOf(".") == -1) {
////			if (cmd2.indexOf(File.separator) != -1) {
//			fileName = ScriptResouce.SYS_IMAGE_PATH+cmd2+".bin";
//		} else {
//			fileName = ss.path + File.separator + cmd2;
//		}
////		Eye.showEyeFromFile(id, fileName, Integer.valueOf(cmd1));
////		System.out.println("id:"+eye+"@cmd1:"+cmd1+fileName);
//		Eye.showEye(eye, Integer.valueOf(cmd1), fileName);
//	}
	
	//private static boolean m_showSysEye = false;
	private static boolean m_showEye = false;
	
	private static final void showEye(ScriptParse ss, int eye, String cmd1,
			String cmd2) {
		String fileName;
		System.out.println(">>>>>>>>>>>>id:"+eye+"@cmd1:"+cmd1+"cmd2:"+cmd2);
		int cmdIndex = cmd2.indexOf(".");
		if(!m_showEye){
			m_showEye = true;
			if (cmdIndex == -1) {
				//Log.d("ScriptParse", "Show showSysImage");
				Eye.showSysImage(Integer.valueOf(cmd1), Integer.valueOf(cmd2));
				//Log.d("ScriptParse", "Show showSysImage End");
			} else {
				//Log.d("ScriptParse", "Show showEye");
				fileName = ss.path + File.separator + cmd2;
				Eye.showEye(eye, Integer.valueOf(cmd1), fileName);
				//Log.d("ScriptParse", "Show showEye end");
			}
			m_showEye = false;
		}
//		Eye.showEyeFromFile(id, fileName, Integer.valueOf(cmd1));
//		System.out.println("id:"+eye+"@cmd1:"+cmd1+fileName);
//		Eye.showEye(eye, Integer.valueOf(cmd1), fileName);
	}


	static {

		CMD_TO_TIMELINE_MAP = new HashMap<String, String>();
		for (int i = 0; i < COMMOND_KEYS.length; i++) {
			CMD_TO_TIMELINE_MAP.put(COMMOND_KEYS[i], TIMELINE_KEYS[i]);
		}

		CMD_MAP = new HashMap<String, Command>();

		CMD_MAP.put(key_cmd_heart, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				if (cmd2==null) {
					cmd2="0";
				}
				return Integer.parseInt(cmd2);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				String color = cmd1;
				int a = color.indexOf('(');
				int b = color.indexOf(',', a);
				int c = color.indexOf(',', b+1);
				int d = color.indexOf(')', c);
				if (cmd2==null) {
					cmd2="0";
				}
//				Heart.setColor(getColor(color, a, b), getColor(color, b, c),
//						getColor(color, c, d));
//				System.out.println("color:"+cmd1+a+b+c);
				HeartLed.setColors(getColor(color, a, b), getColor(color, b, c),
					getColor(color, c, d),Integer.parseInt(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_eye, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 20;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				
				showEye(ss, Eye.EYE_ALL, cmd1, cmd2);
			}
		});
		CMD_MAP.put(key_cmd_leye, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 0;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				showEye(ss, Eye.EYE_LEFT, cmd1, cmd2);
			}
		});
		CMD_MAP.put(key_cmd_reye, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 0;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				showEye(ss, Eye.EYE_RIGHT, cmd1, cmd2);
			}
		});
		CMD_MAP.put(key_cmd_beye, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 0;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				showEye(ss, Eye.EYE_ALL, cmd1, cmd2);
			}
		});
		CMD_MAP.put(key_cmd_vhead, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 0;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setHeadVerticalPosition(Integer.valueOf(cmd1),
						Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_hhead, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return 0;
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setHeadHorizontalPosition(Integer.valueOf(cmd1),
						Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_lwingup, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return WING_RANGE_TIME * Integer.parseInt(cmd2);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setLeftWingUp(Integer.valueOf(cmd1),Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_lwingdown, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return WING_RANGE_TIME * Integer.parseInt(cmd2);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setLeftWingDown(Integer.valueOf(cmd1),Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_rwingup, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return WING_RANGE_TIME * Integer.parseInt(cmd2);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setRightWingUp(Integer.valueOf(cmd1),Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_rwingdown, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return WING_RANGE_TIME * Integer.parseInt(cmd2);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.setRightWingDown(Integer.valueOf(cmd1),Integer.valueOf(cmd2));
			}
		});
		CMD_MAP.put(key_cmd_vheadorg, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return Integer.parseInt(cmd1);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.resetHeadVertical(Integer.valueOf(cmd1));
			}
		});
		CMD_MAP.put(key_cmd_hheadorg, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return Integer.parseInt(cmd1);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.resetHeadHorizontal(Integer.valueOf(cmd1));
			}
		});
		CMD_MAP.put(key_cmd_lwingorg, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return Integer.parseInt(cmd1);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.resetLeftWing(Integer.valueOf(cmd1));
			}
		});
		CMD_MAP.put(key_cmd_rwingorg, new Command() {

			@Override
			public int actionTime(String cmd1, String cmd2) {
				return Integer.parseInt(cmd1);
			}

			@Override
			public void exe(ScriptParse ss, String cmd1, String cmd2) {
				Motor.resetRightWing(Integer.valueOf(cmd1));
			}
		});
	}
}
