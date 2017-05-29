//package com.lsjr.zizisteward.coustom;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.graphics.drawable.Drawable;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.widget.EditText;
//
//import com.lsjr.zizisteward.R;
//
//public class LineEditText extends EditText {
//    private Paint mPaint;
//    private int color;
//    public static final int STATUS_FOCUSED = 1;
//    public static final int STATUS_UNFOCUSED = 2;
//    public static final int STATUS_ERROR = 3;
//    private int status = 2;
//    private Drawable del_btn;
//    private Drawable del_btn_down;
//    private int focusedDrawableId = R.drawable.user_select;// 默认的
//    private int unfocusedDrawableId = R.drawable.user;
//    private int errorDrawableId = R.drawable.user_error;
//    Drawable left = null;
//    private Context mContext;
//    public LineEditText(Context context) {
//        super(context);
//        mContext = context;
//        init();
//    }
//    public LineEditText(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        init();
//    }
//    public LineEditText(Context context, AttributeSet attrs, int defStryle) {
//        super(context, attrs, defStryle);
//        mContext = context;
//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.lineEdittext, defStryle, 0);
//        focusedDrawableId = a.getResourceId(
//                R.styleable.lineEdittext_drawableFocus, R.drawable.user_select);
//        unfocusedDrawableId = a.getResourceId(
//                R.styleable.lineEdittext_drawableUnFocus, R.drawable.user);
//        errorDrawableId = a.getResourceId(
//                R.styleable.lineEdittext_drawableError, R.drawable.user_error);
//        a.recycle();
//        init();
//    }
//    /**
//     * 2014/7/31
//     *
//     * @author Aimee.ZHANG
//     */
//    private void init() {
//        mPaint = new Paint();
//        // mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStrokeWidth(3.0f);
//        color = Color.parseColor("#bfbfbf");
//        setStatus(status);
//        del_btn = mContext.getResources().getDrawable(R.drawable.del_but_bg);
//        del_btn_down = mContext.getResources().getDrawable(R.drawable.del_but_bg_down);
//        addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
//                    int arg3) {
//            }
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1,
//                    int arg2, int arg3) {
//            }
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                setDrawable();
//            }
//        });
//        setDrawable();
//    }
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        mPaint.setColor(color);
//        canvas.drawLine(0, this.getHeight() - 1, this.getWidth(),
//                this.getHeight() - 1, mPaint);
//    }
//    // 删除图片
//    private void setDrawable() {
//        if (length() < 1) {
//            setCompoundDrawablesWithIntrinsicBounds(left, null, del_btn, null);
//        } else {
//            setCompoundDrawablesWithIntrinsicBounds(left, null, del_btn_down,null);
//        }
//    }
//    // 处理删除事件
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (del_btn_down != null && event.getAction() == MotionEvent.ACTION_UP) {
//            int eventX = (int) event.getRawX();
//            int eventY = (int) event.getRawY();
//            Log.e("eventXY", "eventX = " + eventX + "; eventY = " + eventY);
//            Rect rect = new Rect();
//            getGlobalVisibleRect(rect);
//            rect.left = rect.right - 50;
//            if (rect.contains(eventX, eventY))
//            setText("");
//        }
//        return super.onTouchEvent(event);
//    }
//    public void setStatus(int status) {
//        this.status = status;
//
//        if (status == STATUS_ERROR) {
//            try {
//                left = getResources().getDrawable(errorDrawableId);
//            } catch (Resources.NotFoundException e) {
//                e.printStackTrace();
//            }
//            setColor(Color.parseColor("#f57272"));
//        } else if (status == STATUS_FOCUSED) {
//            try {
//                left = getResources().getDrawable(focusedDrawableId);
//            } catch (Resources.NotFoundException e) {
//                e.printStackTrace();
//            }
//            setColor(Color.parseColor("#5e99f3"));
//        } else {
//            try {
//                left = getResources().getDrawable(unfocusedDrawableId);
//            } catch (Resources.NotFoundException e) {
//                e.printStackTrace();
//            }
//            setColor(Color.parseColor("#bfbfbf"));
//        }
//        if (left != null) {
////          left.setBounds(0, 0, 30, 40);
////          this.setCompoundDrawables(left, null, null, null);
//            setCompoundDrawablesWithIntrinsicBounds(left,null,del_btn,null);
//        }
//        postInvalidate();
//    }
//    public void setLeftDrawable(int focusedDrawableId, int unfocusedDrawableId,
//            int errorDrawableId) {
//        this.focusedDrawableId = focusedDrawableId;
//        this.unfocusedDrawableId = unfocusedDrawableId;
//        this.errorDrawableId = errorDrawableId;
//        setStatus(status);
//    }
//    @Override
//    protected void onFocusChanged(boolean focused, int direction,
//            Rect previouslyFocusedRect) {
//        super.onFocusChanged(focused, direction, previouslyFocusedRect);
//        if (focused) {
//            setStatus(STATUS_FOCUSED);
//        } else {
//            setStatus(STATUS_UNFOCUSED);
//        }
//    }
//    @Override
//    protected void finalize() throws Throwable {
//        super.finalize();
//    };
//    public void setColor(int color) {
//        this.color = color;
//        this.setTextColor(color);
//        invalidate();
//    }
//}