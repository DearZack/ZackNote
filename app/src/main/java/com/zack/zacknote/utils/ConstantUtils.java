package com.zack.zacknote.utils;

/**
 * Created by Zack Zhou on 2016/6/4.
 */
public class ConstantUtils {

    /**
     * 100
     * type
     */
    public static final int SHOW_NOTES = 101;
    public static final int SHOW_DELETED_NOTES = 102;
    public static final int SHOW_ABOUT_APP = 102;

    /**
     * 200
     * 请求码
     */
    public static final int CREATE_NOTE = 201;
    public static final int MODIFY_NOTE = 202;

    /**
     * 300
     * 返回码
     */
    public static final int CREATE_NOTE_SUCCEED = 301;
    public static final int MODIFY_NOTE_SUCCEED = 302;

    /**
     * 400
     * Handler
     */
    public static final int ADD_NOTE_SUCCEED = 401;
    public static final int SET_NOTE_TO_RECYCLE_BIN = 402;
    public static final int SET_NOTE_TO_NORMAL = 403;
    public static final int DELETE_NOTE_FOREVER = 404;

    /**
     * 500
     * PopupWindow type
     */
    public static final int SHOW_NOTE_NORMAL_PUPOP_WINDOW = 501;
    public static final int SHOW_NOTE_DELETED_PUPOP_WINDOW = 502;

}
