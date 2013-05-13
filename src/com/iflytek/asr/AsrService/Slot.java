package com.iflytek.asr.AsrService;

import android.os.Bundle;

/**
 * Slot class
 */
public class Slot {

	public static final String KEY_ITEM_COUNT = "KEY_ITEM_COUNT";
	public static final String KEY_ITEM_IDS = "KEY_ITEM_IDS";
	public static final String KEY_ITEM_TEXTS = "KEY_ITEM_TEXTS";

	public final int mItemCount;
	public final int mItemIds[];
	public final String mItemTexts[];

	public Slot(int itemCount, int itemIds[], String itemTexts[]) {
		mItemCount = itemCount;
		mItemIds = itemIds;
		mItemTexts = itemTexts;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_ITEM_COUNT, mItemCount);
		bundle.putIntArray(KEY_ITEM_IDS, mItemIds);
		bundle.putStringArray(KEY_ITEM_TEXTS, mItemTexts);
		return bundle;
	}

	public static Slot create(Bundle bundle) {
		int count = bundle.getInt(KEY_ITEM_COUNT);
		String[] text = bundle.getStringArray(KEY_ITEM_TEXTS);
		int[] ids = bundle.getIntArray(KEY_ITEM_IDS);
		return new Slot(count, ids, text);
	}
}