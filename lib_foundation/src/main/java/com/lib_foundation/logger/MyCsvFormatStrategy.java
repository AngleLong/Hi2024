package com.lib_foundation.logger;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import androidx.annotation.NonNull;

import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MyCsvFormatStrategy implements FormatStrategy {

    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String NEW_LINE_REPLACEMENT = "\n";
    private static final String SEPARATOR = "  ";

    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final LogStrategy logStrategy;
    private final String tag;

    private MyCsvFormatStrategy(Builder builder) {

        date = builder.date;
        dateFormat = builder.dateFormat;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, String onceOnlyTag, @NonNull String message) {

        String tag = formatTag(onceOnlyTag);

        date.setTime(System.currentTimeMillis());

        //格式化好的时间

        String builder =
                //日期
                dateFormat.format(date) +
                        SEPARATOR +

                        //日志标识，如果有的话打印
                        tag +
                        SEPARATOR +

                        //日志等级
                        Utils.logLevel(priority) +
                        SEPARATOR +

                        //信息
                        NEW_LINE_REPLACEMENT +
                        DOUBLE_DIVIDER +
                        NEW_LINE_REPLACEMENT +
                        message +
                        NEW_LINE_REPLACEMENT +
                        DOUBLE_DIVIDER +
                        NEW_LINE_REPLACEMENT;

        logStrategy.log(priority, tag, builder);
    }


    private String formatTag(String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static final class Builder {
        private static final int MAX_BYTES = 500 * 1024; // 500K averages to a 4000 lines per file

        Date date;
        SimpleDateFormat dateFormat;
        LogStrategy logStrategy;
        String tag = "PRETTY_LOGGER";

        Context context;

        private Builder() {
        }

        @NonNull
        public Builder date(Date val) {
            date = val;
            return this;
        }

        @NonNull
        public Builder dateFormat(SimpleDateFormat val) {
            dateFormat = val;
            return this;
        }

        @NonNull
        public Builder logStrategy(LogStrategy val) {
            logStrategy = val;
            return this;
        }

        @NonNull
        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        @NonNull
        public MyCsvFormatStrategy build() {
            if (date == null) {
                date = new Date();
            }
            if (dateFormat == null) {
                dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SSS", Locale.CHINA);
            }
            if (logStrategy == null) {
                String diskPath = Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsolutePath();
                String folder = diskPath + File.separatorChar + "logger";

                HandlerThread ht = new HandlerThread("AndroidFileLogger." + folder);
                ht.start();
                Handler handler = new MyDiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
                logStrategy = new MyDiskLogStrategy(handler);
            }
            return new MyCsvFormatStrategy(this);
        }
    }
}
