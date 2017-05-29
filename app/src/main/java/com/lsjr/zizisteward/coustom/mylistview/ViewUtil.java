/*
 * Copyright (C) 2013 www.418log.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lsjr.zizisteward.coustom.mylistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.Editable;
import android.text.Selection;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * The Class ViewUtil.
 */
public class ViewUtil {

	public static void setAbsListViewHeight(AbsListView absListView,
			int lineNumber, int verticalSpace) {

		int totalHeight = getAbsListViewHeight(absListView, lineNumber,
				verticalSpace);
		ViewGroup.LayoutParams params = absListView.getLayoutParams();
		params.height = totalHeight;
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		absListView.setLayoutParams(params);
	}


	public static int getAbsListViewHeight(AbsListView absListView,
			int lineNumber, int verticalSpace) {
		int totalHeight = 0;
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		absListView.measure(w, h);
		ListAdapter mListAdapter = absListView.getAdapter();
		if (mListAdapter == null) {
			return totalHeight;
		}

		int count = mListAdapter.getCount();
		if (absListView instanceof ListView) {
			for (int i = 0; i < count; i++) {
				View listItem = mListAdapter.getView(i, null, absListView);
				listItem.measure(w, h);
				totalHeight += listItem.getMeasuredHeight();
			}
			if (count == 0) {
				totalHeight = verticalSpace;
			} else {
				totalHeight = totalHeight
						+ (((ListView) absListView).getDividerHeight() * (count - 1));
			}

		} else if (absListView instanceof GridView) {
			int remain = count % lineNumber;
			if (remain > 0) {
				remain = 1;
			}
			if (mListAdapter.getCount() == 0) {
				totalHeight = verticalSpace;
			} else {
				View listItem = mListAdapter.getView(0, null, absListView);
				listItem.measure(w, h);
				int line = count / lineNumber + remain;
				totalHeight = line * listItem.getMeasuredHeight() + (line - 1)
						* verticalSpace;
			}

		}
		return totalHeight;

	}


	public static void measureView(View v) {
		if (v == null) {
			return;
		}
		int w = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		int h = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		v.measure(w, h);
	}

	/**
	 * 
	 * @param screenWidth
	 *            the screen width
	 * @param screenHeight
	 *            the screen height
	 * @param textSize
	 *            the text size
	 * @return the int
	 */
	public static int resizeTextSize(int screenWidth, int screenHeight,
			int textSize) {
		float ratio = 1;
		try {
			float ratioWidth = (float) screenWidth / 480;
			float ratioHeight = (float) screenHeight / 800;
			ratio = Math.min(ratioWidth, ratioHeight);
		} catch (Exception e) {
		}
		return Math.round(textSize * ratio);
	}

	/**
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 * @throws
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		System.out.println("SCALE======================"+"scale");
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 
	 *
	 * @param context
	 * @param pxValue
	 * @return
	 * @throws
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	
	/**
	 * @param context ������
	 * @param orientation ����
	 * @param width ��
	 * @param height ��
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		return linearLayout;
	}
	
	/**
	 * 
	 * @param context ������
	 * @param orientation ����
	 * @param width ��
	 * @param height ��
	 * @param weight Ȩ��
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height, int weight){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
		return linearLayout;
	}
	
	/**
	 * @param listView
	 */
	public static void setListViewHeightByAllChildrenViewHeight(ListView listView) {  
		ListAdapter listAdapter = listView.getAdapter();   
	    if (listAdapter != null) {  
	    	int totalHeight = 0;  
	    	for (int i = 0; i < listAdapter.getCount(); i++) {  
	    		View listItem = listAdapter.getView(i, null, listView);  
	    		listItem.measure(0, 0);  
	    		totalHeight += listItem.getMeasuredHeight();  
	    	}  
	    	
	    	ViewGroup.LayoutParams params = listView.getLayoutParams();  
	    	params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	    	((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
	    	listView.setLayoutParams(params); 
	    }  
    }
	
	/**
	 * @param context ������
	 * @param view ��������ͼ
	 * @param hintContent ��ʾ����
	 */
	public static void setLongClickHint(final Context context, View view, final String hintContent){
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(context, hintContent, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
	}
	
	/**
	 * @param context ������
	 * @param view ��������ͼ
	 * @param hintContentId ��ʾ���ݵ�ID
	 */
	public static void setLongClickHint(final Context context, View view, final int hintContentId){
		setLongClickHint(context, view, context.getString(hintContentId));
	}
	
	/**
	 * @param view ��������ͼ
	 * @param newHeight �µĸ߶�
	 */
	public static void setViewHeight(View view, int newHeight){
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();  
		layoutParams.height = newHeight; 
		view.setLayoutParams(layoutParams);
	}
	
	/**
	 * @param view ��������ͼ
	 * @param increasedAmount ���Ӷ���
	 */
	public static void addViewHeight(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = view.getLayoutParams();  
		headerLayoutParams.height += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * @param view ��������ͼ
	 * @param newWidth �µĿ��
	 */
	public static void setViewWidth(View view, int newWidth){
		ViewGroup.LayoutParams headerLayoutParams = view.getLayoutParams();  
		headerLayoutParams.width = newWidth; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * @param view ��������ͼ
	 * @param increasedAmount ���Ӷ���
	 */
	public static void addViewWidth(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = view.getLayoutParams();  
		headerLayoutParams.width += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutBottomMargin(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).bottomMargin;
	}
	
	/**
	 * @param linearLayout
	 * @param newBottomMargin
	 */
	public static void setLinearLayoutBottomMargin(LinearLayout linearLayout, int newBottomMargin) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)linearLayout.getLayoutParams();
		lp.bottomMargin = newBottomMargin;
		linearLayout.setLayoutParams(lp);
	}
	
	/**
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutHiehgt(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).height;
	}
	
	/**
	 * @param editText
	 */
	public static final void setEditTextSelectionToEnd(EditText editText){
		Editable editable = editText.getEditableText();
		Selection.setSelection(editable, editable.toString().length());
	}
	
	/**
	 * @param view
	 * @return
	 */
	public static final View measure(View view){
		ViewGroup.LayoutParams p = view.getLayoutParams();
	    if (p == null) {
	        p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
	    }
	    int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
	    int lpHeight = p.height;
	    int childHeightSpec;
	    if (lpHeight > 0) {
	    	childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
	    } else {
	        childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
	    }
	    view.measure(childWidthSpec, childHeightSpec);
	    return view;
	}
	
	/**
	 * @param view
	 * @return
	 */
	public static final int getMeasuredHeight(View view){
	    return measure(view).getMeasuredHeight();
	}
	
	/**
	 * @param view
	 * @return
	 */
	public static final int getMeasuredWidth(View view){
	    return measure(view).getMeasuredWidth();
	}
	
	/**
	 * @param view1
	 * @param view2
	 * @return
	 */
	public static final Rect getRelativeRect(View view1, View view2){
		Rect childViewGlobalRect = new Rect();
		Rect parentViewGlobalRect = new Rect();
		view1.getGlobalVisibleRect(childViewGlobalRect);
		view2.getGlobalVisibleRect(parentViewGlobalRect);
		return new Rect(childViewGlobalRect.left - parentViewGlobalRect.left, childViewGlobalRect.top - parentViewGlobalRect.top, childViewGlobalRect.right - parentViewGlobalRect.left, childViewGlobalRect.bottom - parentViewGlobalRect.top);
	}
	
	/**
	 * @param viewTreeObserver
	 * @param onGlobalLayoutListener
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static final void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener){
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN){
			viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
		}else{
			viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
		}
	}
	
}
