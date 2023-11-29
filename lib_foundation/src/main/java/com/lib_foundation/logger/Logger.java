package com.lib_foundation.logger;

import static com.lib_foundation.logger.Utils.checkNotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Logger {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    @NonNull
    private static Printer printer = new LoggerPrinter();

    private Logger() {
        //no instance
    }

    public static void printer(@NonNull Printer printer) {
        Logger.printer = checkNotNull(printer);
    }

    public static void addLogAdapter(@NonNull LogAdapter adapter) {
        printer.addAdapter(checkNotNull(adapter));
    }

    public static void clearLogAdapters() {
        printer.clearLogAdapters();
    }

    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(@Nullable String tag) {
        return printer.t(tag);
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, @NonNull Boolean isSaveLocal, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        printer.log(priority, isSaveLocal, tag, message, throwable);
    }

    public static void d(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.d(isSaveLocal, message, args);
    }

    public static void d(@NonNull Boolean isSaveLocal, @Nullable Object object) {
        printer.d(isSaveLocal, object);
    }

    public static void e(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.e(isSaveLocal, null, message, args);
    }

    public static void e(@NonNull Boolean isSaveLocal, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        printer.e(isSaveLocal, throwable, message, args);
    }

    public static void i(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.i(isSaveLocal, message, args);
    }

    public static void v(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.v(isSaveLocal, message, args);
    }

    public static void w(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.w(isSaveLocal, message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        printer.wtf(isSaveLocal, message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@NonNull Boolean isSaveLocal, @Nullable String json) {
        printer.json(isSaveLocal, json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@NonNull Boolean isSaveLocal, @Nullable String xml) {
        printer.xml(isSaveLocal, xml);
    }

}