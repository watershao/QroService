package com.qrobot.motion;

import java.io.File;

public class ScriptResouce {

	private static final String CACHE_ROOT_PATH = "/sdcard/qrobot";
	private static final String IMAGE_PATH = CACHE_ROOT_PATH + "/image2";
	public static final String SYS_IMAGE_PATH = "/mnt/sdcard/qrobot/motion/sys/";

	private static final void mkdirs(String path) {
		File f = new File(path);
		f.mkdirs();
	}

	public static final String getImageRoot() {
		mkdirs(IMAGE_PATH);
		return IMAGE_PATH;
	}

	public static final String getImagePath(String name) {
		return getImageRoot() + "/" + name + ".bmp";
	}

	public static final String[] AOMAN = {"aoman_a", "aoman_b"};
	public static final String[] BAIYAN = {"baiyan_a", "baiyan_b"};
	public static final String[] BISHI_LEFT = {"bishi_left"};
	public static final String[] BISHI_RIGHT = {"bishi_right"};
	public static final String[] CAHAN = {"cahan"};
	public static final String[] CRY_LEFT = {"Cry_left_a", "Cry_left_b"};
	public static final String[] CRY_RIGHT = {"Cry_right_a", "Cry_right_b"};
	public static final String[] DAKU_LEFT = {"daku_left"};
	public static final String[] DAKU_RIGHT = {"daku_right"};
	public static final String[] DEYI_LEFT = {"deyi_left"};
	public static final String[] DEYI_RIGHT = {"deyi_right"};
	public static final String[] FADAI_LEFT = {"fadai_left_a", "fadai_left_b"};
	public static final String[] FADAI_RIGHT = {"fadai_right_a",
			"fadai_right_b"};
	public static final String[] FAINT = {"faint_a", "faint_b", "faint_c",
			"faint_d", "faint_e", "faint_f"};
	public static final String[] FANU_LEFT = {"fanu_left_a", "fanu_left_b"};
	public static final String[] FANU_RIGHT = {"fanu_right_a", "fanu_right_b"};
	public static final String[] GUZHANG_LEFT = {"guzhang_left"};
	public static final String[] GUZHANG_RIGHT = {"guzhang_right"};
	public static final String[] HAQIAN_LEFT = {"haqian_left_a",
			"haqian_left_b", "haqian_left_c"};
	public static final String[] HAQIAN_RIGHT = {"haqian_right_a",
			"haqian_right_b", "haqian_right_c"};
	public static final String[] HUAIXIAO = {"huaixiao_a", "huaixiao_b",
			"huaixiao_c", "huaixiao_d"};
	public static final String[] JINGKONG_LEFT = {"jinkong_left_a",
			"jinkong_left_b"};
	public static final String[] JINGKONG_RIGHT = {"jinkong_right_a",
			"jinkong_right_b"};
	public static final String[] KELIAN = {"kelian_a", "kelian_b"};
	public static final String[] KOUBI_LEFT = {"koubi_left"};
	public static final String[] KOUBI_RIGHT = {"koubi_right"};
	public static final String[] LIUHAN = {"liuhan_a", "liuhan_b", "liuhan_c",
			"liuhan_d"};
	public static final String[] LOVE = {"Love_a", "Love_b"};
	public static final String[] MI_LEFT = {"mi_left_a", "mi_left_b"};
	public static final String[] MI_RIGHT = {"mi_right_a", "mi_right_b"};
	public static final String[] MONEY = {"money"};
	public static final String[] NORMAL_LEFT = {"Normal_left"};
	public static final String[] NORMAL_RIGHT = {"Normal_right"};
	public static final String[] OPEN = {"open1", "open2", "open3", "open4",
			"open5", "open6", "open7", "open8", "open9", "open10", "open11",
			"open12", "open14"};
	public static final String[] POWEROFF = {"powerOff_a", "poweroff_b",
			"poweroff_c"};
	public static final String[] QINQIN = {"qinqin"};
	public static final String[] QIUDALE = {"qiudale_a", "qiudale_b"};
	public static final String[] SAD_LEFT = {"sad_left_a", "sad_left_b"};
	public static final String[] SAD_RIGHT = {"sad_right_a", "sad_right_b"};
	public static final String[] SHUI = {"shui_a", "shui_b", "shui_c"};
	public static final String[] SLEEPY_LEFT = {"sleepy_left_a",
			"sleepy_left_b", "sleepy_left_c"};
	public static final String[] SLEEPY_RIGHT = {"sleepy_right_a",
			"sleepy_right_b", "sleepy_right_c"};
	public static final String[] XIA_LEFT = {"xia_left"};
	public static final String[] XIA_RIGHT = {"xia_right"};
	public static final String[] XIAO = {"xiao_a", "xiao_b"};
	public static final String[] ZAIJIAN = {"zaijian"};
	public static final String[] ZHOUMA_LEFT = {"zhouma_left"};
	public static final String[] ZHOUMA_RIGHT = {"zhouma_right"};
	public static final String[] CAIGE = {"猜歌"};
	public static final String[] GUPIAOMOSHI = {"股票模式_a", "股票模式_b", "股票模式_c",
			"股票模式_d"};
	public static final String[] LISTEN = {"监听1", "监听2", "监听3", "监听4", "监听5"};
	public static final String[] VOICE_NET = {"声控上网_a", "声控上网_b"};
	public static final String[] SHICI = {"诗词模式"};
	public static final String[] SEARCH_LEFT = {"搜索模式_left"};
	public static final String[] SEARCH_RIGHT = {"搜索模式_right"};
	public static final String[] CHILD = {"腾讯儿童"};
	public static final String[] MUSIC = {"音乐模式_a", "音乐模式_b"};
	public static final String[] KNOWLEGE = {"知识问答_a", "知识问答_b"};

	public static final String[][] IMAGE_LIST = {AOMAN, BAIYAN, BISHI_LEFT,
			BISHI_RIGHT, CAHAN, CRY_LEFT, CRY_RIGHT, DAKU_LEFT, DAKU_RIGHT,
			DEYI_LEFT, DEYI_RIGHT, FADAI_LEFT, FADAI_RIGHT, FAINT, FANU_LEFT,
			FANU_RIGHT, GUZHANG_LEFT, GUZHANG_RIGHT, HAQIAN_LEFT, HAQIAN_RIGHT,
			HUAIXIAO, JINGKONG_LEFT, JINGKONG_RIGHT, KELIAN, KOUBI_LEFT,
			KOUBI_RIGHT, LIUHAN, LOVE, MI_LEFT, MI_RIGHT, MONEY, NORMAL_LEFT,
			NORMAL_RIGHT, OPEN, POWEROFF, QINQIN, QIUDALE, SAD_LEFT, SAD_RIGHT,
			SHUI, SLEEPY_LEFT, SLEEPY_RIGHT, XIA_LEFT, XIA_RIGHT, XIAO,
			ZAIJIAN, ZHOUMA_LEFT, ZHOUMA_RIGHT, CAIGE, GUPIAOMOSHI, VOICE_NET,
			SHICI, SEARCH_LEFT, SEARCH_RIGHT, CHILD, MUSIC, KNOWLEGE};
}
