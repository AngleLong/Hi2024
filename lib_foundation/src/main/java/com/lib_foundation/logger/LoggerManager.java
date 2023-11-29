package com.lib_foundation.logger;


import android.content.Context;

/**
 * 日志管理类
 * 该日志类可以把日志保存到本地,本地的路径见DiskLogStrategy
 * 保存到日志中的方法是结尾带xxxL的方法
 */
public class LoggerManager {
    public static void initLogger(Context context) {

        PrettyFormatStrategy prettyFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(3)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)
                .context(context)// (Optional) Hides internal method calls up to offset. Default 5
                .tag("MyLog======>")
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(prettyFormatStrategy));
    }

    public static void dL(String logStr) {
        Logger.d(true, logStr);
    }

    public static void d(String logStr) {
        Logger.d(false, logStr);
    }


    public static void eL(String logStr) {
        Logger.e(true, logStr);
    }

    public static void e(String logStr) {
        Logger.e(false, logStr);
    }

    public static void iL(String logStr) {
        Logger.i(true, logStr);
    }

    public static void i(String logStr) {
        Logger.i(false, logStr);
    }

    public static void vL(String logStr) {
        Logger.v(true, logStr);
    }

    public static void v(String logStr) {
        Logger.v(false, logStr);
    }

    public static void wL(String logStr) {
        Logger.v(true, logStr);
    }

    public static void w(String logStr) {
        Logger.v(false, logStr);
    }

    public static void jsonL(String logStr) {
        Logger.json(true, logStr);
    }

    public static void json(String logStr) {
        Logger.json(false, logStr);
    }
}
