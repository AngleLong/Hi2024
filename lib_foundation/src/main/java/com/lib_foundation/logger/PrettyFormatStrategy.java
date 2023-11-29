package com.lib_foundation.logger;

import static com.lib_foundation.logger.Utils.checkNotNull;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Draws borders around the given log message along with additional information such as :
 *
 * <ul>
 *   <li>Thread information</li>
 *   <li>Method stack trace</li>
 * </ul>
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Thread information
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class PrettyFormatStrategy implements FormatStrategy {

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 5;

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '┌';
    private static final char BOTTOM_LEFT_CORNER = '└';
    private static final char MIDDLE_CORNER = '├';
    private static final char HORIZONTAL_LINE = '│';
    private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
    private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    private final int methodCount;
    private final int methodOffset;
    private final boolean showThreadInfo;
    @NonNull
    private final LogStrategy logStrategy;
    @Nullable
    private final String tag;

    @NonNull
    private final Date date;
    @NonNull
    private final SimpleDateFormat dateFormat;

    private PrettyFormatStrategy(@NonNull Builder builder) {
        checkNotNull(builder);

        methodCount = builder.methodCount;
        methodOffset = builder.methodOffset;
        showThreadInfo = builder.showThreadInfo;
        logStrategy = builder.logStrategy;
        tag = builder.tag;
        date = builder.date;
        dateFormat = builder.dateFormat;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, @NonNull Boolean isSaveLocal, @Nullable String onceOnlyTag, @NonNull String message) {
        checkNotNull(message);

        String tag = formatTag(onceOnlyTag);

        logTopBorder(priority, isSaveLocal, tag);
        logHeaderContent(priority, isSaveLocal, tag, methodCount);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(priority, isSaveLocal, tag);
            }
            logContent(priority, isSaveLocal, tag, message);
            logBottomBorder(priority, isSaveLocal, tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(priority, isSaveLocal, tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(priority, isSaveLocal, tag, new String(bytes, i, count));
        }
        logBottomBorder(priority, isSaveLocal, tag);
    }

    private void logTimeContent(int logType, Boolean isSaveLocal, String tag) {

    }

    private void logTopBorder(int logType, @NonNull Boolean isSaveLocal, @Nullable String tag) {
        logChunk(logType, isSaveLocal, tag, TOP_BORDER);
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private void logHeaderContent(int logType, @NonNull Boolean isSaveLocal, @Nullable String tag, int methodCount) {
        if (isSaveLocal) {
            String timeFormat = dateFormat.format(date.getTime());
            logChunk(logType, true, tag, HORIZONTAL_LINE + " Time: " + timeFormat);
            logDivider(logType, true, tag);
        }

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (showThreadInfo) {
            logChunk(logType, isSaveLocal, tag, HORIZONTAL_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(logType, isSaveLocal, tag);
        }

        String level = "";

        int stackOffset = getStackOffset(trace) + methodOffset;

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(HORIZONTAL_LINE)
                    .append(' ')
                    .append(level)
                    .append(getSimpleClassName(trace[stackIndex].getClassName()))
                    .append(".")
                    .append(trace[stackIndex].getMethodName())
                    .append(" ")
                    .append(" (")
                    .append(trace[stackIndex].getFileName())
                    .append(":")
                    .append(trace[stackIndex].getLineNumber())
                    .append(")");
            level += "   ";
            logChunk(logType, isSaveLocal, tag, builder.toString());
        }
    }

    private void logBottomBorder(int logType, @NonNull Boolean isSaveLocal, @Nullable String tag) {
        logChunk(logType, isSaveLocal, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, @NonNull Boolean isSaveLocal, @Nullable String tag) {
        logChunk(logType, isSaveLocal, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, @NonNull Boolean isSaveLocal, @Nullable String tag, @NonNull String chunk) {
        checkNotNull(chunk);

        String[] lines = chunk.split(Objects.requireNonNull(System.getProperty("line.separator")));
        for (String line : lines) {
            logChunk(logType, isSaveLocal, tag, HORIZONTAL_LINE + " " + line);
        }
    }

    private void logChunk(int priority, @NonNull Boolean isSaveLocal, @Nullable String tag, @NonNull String chunk) {
        checkNotNull(chunk);

        logStrategy.log(priority, isSaveLocal, tag, chunk);
    }

    private String getSimpleClassName(@NonNull String name) {
        checkNotNull(name);

        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(@NonNull StackTraceElement[] trace) {
        checkNotNull(trace);

        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static class Builder {
        private static final int MAX_BYTES = 500 * 1024;

        int methodCount = 2;
        int methodOffset = 0;
        boolean showThreadInfo = true;
        @Nullable
        LogStrategy logStrategy;

        Context context;

        Date date;
        SimpleDateFormat dateFormat;

        @Nullable
        String tag = "PRETTY_LOGGER";

        private Builder() {
        }

        @NonNull
        public Builder methodCount(int val) {
            methodCount = val;
            return this;
        }

        @NonNull
        public Builder methodOffset(int val) {
            methodOffset = val;
            return this;
        }

        @NonNull
        public Builder showThreadInfo(boolean val) {
            showThreadInfo = val;
            return this;
        }

        @NonNull
        public Builder logStrategy(@Nullable LogStrategy val) {
            logStrategy = val;
            return this;
        }

        public Builder context(@Nullable Context context) {
            this.context = context;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull
        public Builder date(@Nullable Date val) {
            date = val;
            return this;
        }

        @NonNull
        public Builder dateFormat(@Nullable SimpleDateFormat val) {
            dateFormat = val;
            return this;
        }

        @NonNull
        public PrettyFormatStrategy build() {


            if (context == null) {
                throw new RuntimeException("日志框架没有初始化上下文");
            }

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
                Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper(), folder, MAX_BYTES);
                logStrategy = new DiskLogStrategy(handler);
            }

            return new PrettyFormatStrategy(this);
        }
    }

}
