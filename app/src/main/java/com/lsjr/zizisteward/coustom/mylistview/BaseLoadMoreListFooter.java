
package com.lsjr.zizisteward.coustom.mylistview;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * 点击加载更多列表尾
 */
public abstract class BaseLoadMoreListFooter extends LinearLayout {
	private State state; // 状态
	private View contentView; // 内容视图

	public BaseLoadMoreListFooter(Context context) {
		super(context);
		setClickable(true);
		setLayoutParams(new AbsListView.LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		addView(contentView = onGetContentView(),
				new LinearLayout.LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
		toggleToNormalState();
	}

	/**
	 * 切换到正常状态
	 */
	public void toggleToNormalState() {
		setState(State.NOMRAL);
		onToggleToNormalState();
	}

	/**
	 * 切换到加载中状态
	 */
	public void toggleToLoadingState() {
		setState(State.LOADING);
		onToggleToLoadingState();
	}

	/**
	 * 获取内容视图
	 *
	 * @return
	 */
	public abstract View onGetContentView();

	/**
	 * 当切换到正常状态
	 */
	public abstract void onToggleToNormalState();

	/**
	 * 当切换到加载中状态
	 */
	public abstract void onToggleToLoadingState();
	/**
	 * 当切换到加载中状态
	 */
	public abstract void onToggleToLoadAllState();

	/**
	 * 状态
	 */
	public enum State {
		/**
		 * 正常状态
		 */
		NOMRAL,
		/**
		 * 加载中状态
		 */
		LOADING,

		/**
		 * 加载完成
		 */
		LOADAll;
	}

	/**
	 * 获取状态
	 *
	 * @return 状态
	 */
	public State getState() {
		return state;
	}

	/**
	 * 设置状态
	 *
	 * @param state
	 *            状态
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 获取内容视图
	 *
	 * @return 内容视图
	 */
	public View getContentView() {
		return contentView;
	}

	/**
	 * 设置内容视图
	 *
	 * @param contentView
	 *            内容视图
	 */
	public void setContentView(View contentView) {
		this.contentView = contentView;
	}
}