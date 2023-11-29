package com.lib_foundation.logger;


import static com.lib_foundation.logger.Utils.checkNotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AndroidLogAdapter implements LogAdapter {

    @NonNull
    private final FormatStrategy formatStrategy;

    public AndroidLogAdapter() {
        this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
    }

    public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority,@NonNull Boolean isSaveLocal, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, isSaveLocal,tag, message);
    }

}
