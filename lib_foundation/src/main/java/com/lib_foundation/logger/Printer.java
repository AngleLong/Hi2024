package com.lib_foundation.logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A proxy interface to enable additional operations.
 * Contains all possible Log message usages.
 */
public interface Printer {

    void addAdapter(@NonNull LogAdapter adapter);

    Printer t(@Nullable String tag);

    void d(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    void d(@NonNull Boolean isSaveLocal, @Nullable Object object);

    void e(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    void e(@NonNull Boolean isSaveLocal, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args);

    void w(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    void i(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    void v(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    void wtf(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args);

    /**
     * Formats the given json content and print it
     */
    void json(@NonNull Boolean isSaveLocal, @Nullable String json);

    /**
     * Formats the given xml content and print it
     */
    void xml(@NonNull Boolean isSaveLocal, @Nullable String xml);

    void log(int priority, @NonNull Boolean isSaveLocal, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable);

    void clearLogAdapters();
}
