package com.iflytek.asr.AsrService;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

/**
 * RecognitionResult is a passive object that stores a single recognized query
 * and its search result.
 * 
 * Revisit and improve this class, reconciling the different types of actions
 * and the different ways they are represented. Maybe we should have a separate
 * result object for each type, and put them (type/value) in bundle? {@hide
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * }
 */
public class RecognitionResult {

	public static final String KEY_SENTENCE_ID = "KEY_SENTENCE_ID";
	public static final String KEY_CONFIDENCE = "KEY_CONFIDENCE";
	public static final String KEY_SLOT_COUNT = "KEY_SLOT_COUNT";
	public static final String KEY_SLOT_ITEM = "KEY_SLOT_";

	public RecognitionResult(int sentendId, int confidence, int slots) {
		mSentenceId = sentendId;
		mConfidence = confidence;
		mSlot = slots;
		mSlotList = new ArrayList<Slot>(mSlot);
	}

	public static RecognitionResult create(Bundle bundle) {
		int sen = bundle.getInt(KEY_SENTENCE_ID);
		int con = bundle.getInt(KEY_CONFIDENCE);
		int count = bundle.getInt(KEY_SLOT_COUNT);
		RecognitionResult result = new RecognitionResult(sen, con, count);
		for (int i = 0; i < count; i++) {
			result.addSlot(Slot.create(bundle.getBundle(KEY_SLOT_ITEM + i)));
		}
		return result;
	}

	public Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_SENTENCE_ID, mSentenceId);
		bundle.putInt(KEY_CONFIDENCE, mConfidence);
		bundle.putInt(KEY_SLOT_COUNT, mSlotList.size());
		for (int i = 0; i < mSlotList.size(); i++) {
			bundle.putBundle(KEY_SLOT_ITEM + i, mSlotList.get(i).getBundle());
		}
		return bundle;
	}

	/**
	 * Insert a slot to mSlotList
	 */
	public void addSlot(int itemCount, int itemIds[], String itemTexts[]) {
		addSlot(new Slot(itemCount, itemIds, itemTexts));
	}

	/**
	 * Insert a slot to mSlotList
	 */
	public void addSlot(Slot slot) {
		mSlotList.add(slot);
	}

	/**
	 * Sentence ID
	 */
	public final int mSentenceId;
	/**
	 * Score of Confidence 1-100
	 */
	public final int mConfidence;
	/**
	 * Slot count of this sentence
	 */
	public final int mSlot;
	/**
	 * Recognize result array of the slot list
	 */
	public final List<Slot> mSlotList;
}
