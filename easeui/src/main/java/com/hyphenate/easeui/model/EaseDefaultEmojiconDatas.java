package com.hyphenate.easeui.model;

import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojicon.Type;
import com.hyphenate.easeui.utils.EaseSmileUtils;

public class EaseDefaultEmojiconDatas {
    
    private static String[] emojis = new String[]{
        EaseSmileUtils.ee_1,
        EaseSmileUtils.ee_2,
        EaseSmileUtils.ee_3,
        EaseSmileUtils.ee_4,
        EaseSmileUtils.ee_5,
        EaseSmileUtils.ee_6,
        EaseSmileUtils.ee_7,
        EaseSmileUtils.ee_8,
        EaseSmileUtils.ee_9,
        EaseSmileUtils.ee_10,
        EaseSmileUtils.ee_11,
        EaseSmileUtils.ee_12,
        EaseSmileUtils.ee_13,
        EaseSmileUtils.ee_14,
        EaseSmileUtils.ee_15,
        EaseSmileUtils.ee_16,
        EaseSmileUtils.ee_17,
        EaseSmileUtils.ee_18,
        EaseSmileUtils.ee_19,
        EaseSmileUtils.ee_20,
        EaseSmileUtils.ee_21,
        EaseSmileUtils.ee_22,
        EaseSmileUtils.ee_23,
        EaseSmileUtils.ee_24,
        EaseSmileUtils.ee_25,
        EaseSmileUtils.ee_26,
        EaseSmileUtils.ee_27,
        EaseSmileUtils.ee_28,
        EaseSmileUtils.ee_29,
        EaseSmileUtils.ee_30,
        EaseSmileUtils.ee_31,
        EaseSmileUtils.ee_32,
        EaseSmileUtils.ee_33,
        EaseSmileUtils.ee_34,
        EaseSmileUtils.ee_35,
       
    };
    
    private static int[] icons = new int[]{
        R.drawable.ee_e,
        R.drawable.ee_2e,
        R.drawable.ee_3e,
        R.drawable.ee_4e,
        R.drawable.ee_5e,
        R.drawable.ee_6e,
        R.drawable.ee_7e,
        R.drawable.ee_8e,
        R.drawable.ee_9e,
        R.drawable.ee_10e,
        R.drawable.ee_11e,
        R.drawable.ee_12e,
        R.drawable.ee_13e,
        R.drawable.ee_14e,
        R.drawable.ee_15e,
        R.drawable.ee_16e,
        R.drawable.ee_17e,
        R.drawable.ee_18e,
        R.drawable.ee_19e,
        R.drawable.ee_20e,
        R.drawable.ee_21e,
        R.drawable.ee_22e,
        R.drawable.ee_23e,
        R.drawable.ee_24e,
        R.drawable.ee_25e,
        R.drawable.ee_26e,
        R.drawable.ee_27e,
        R.drawable.ee_28e,
        R.drawable.ee_29e,
        R.drawable.ee_30e,
        R.drawable.ee_31e,
        R.drawable.ee_32e,
        R.drawable.ee_33e,
        R.drawable.ee_34e,
        R.drawable.ee_35e,
    };
    
    
    private static final EaseEmojicon[] DATA = createData();
    
    private static EaseEmojicon[] createData(){
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for(int i = 0; i < icons.length; i++){
            datas[i] = new EaseEmojicon(icons[i], emojis[i], Type.NORMAL);
        }
        return datas;
    }
    
    public static EaseEmojicon[] getData(){
        return DATA;
    }
}
