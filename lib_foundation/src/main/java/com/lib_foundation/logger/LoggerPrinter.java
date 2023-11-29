package com.lib_foundation.logger;

import static android.util.Log.DEBUG;
import static com.lib_foundation.logger.Logger.ASSERT;
import static com.lib_foundation.logger.Logger.ERROR;
import static com.lib_foundation.logger.Logger.INFO;
import static com.lib_foundation.logger.Logger.VERBOSE;
import static com.lib_foundation.logger.Logger.WARN;
import static com.lib_foundation.logger.Utils.checkNotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

class LoggerPrinter implements Printer {

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * Provides one-time used tag for the log message
     */
    private final ThreadLocal<String> localTag = new ThreadLocal<>();

    private final List<LogAdapter> logAdapters = new ArrayList<>();

    @Override
    public Printer t(String tag) {
        if (tag != null) {
            localTag.set(tag);
        }
        return this;
    }

    @Override
    public void d(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        log(DEBUG, isSaveLocal, null, message, args);
    }

    @Override
    public void d(@NonNull Boolean isSaveLocal, @Nullable Object object) {
        log(DEBUG, isSaveLocal, null, Utils.toString(object));
    }

    @Override
    public void e(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        e(isSaveLocal, null, message, args);
    }

    @Override
    public void e(@NonNull Boolean isSaveLocal, @Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(ERROR, isSaveLocal, throwable, message, args);
    }

    @Override
    public void w(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        log(WARN, isSaveLocal, null, message, args);
    }

    @Override
    public void i(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        log(INFO, isSaveLocal, null, message, args);
    }

    @Override
    public void v(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        log(VERBOSE, isSaveLocal, null, message, args);
    }

    @Override
    public void wtf(@NonNull Boolean isSaveLocal, @NonNull String message, @Nullable Object... args) {
        log(ASSERT, isSaveLocal, null, message, args);
    }

    @Override
    public void json(@NonNull Boolean isSaveLocal, @Nullable String json) {
        if (Utils.isEmpty(json)) {
            d(isSaveLocal, "Empty/Null json content");
            return;
        }
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                String message = jsonObject.toString(JSON_INDENT);
                d(isSaveLocal, message);
                return;
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                String message = jsonArray.toString(JSON_INDENT);
                d(isSaveLocal, message);
                return;
            }
            e(isSaveLocal, "Invalid Json");
        } catch (JSONException e) {
            e(isSaveLocal, "Invalid Json");
        }
    }

    @Override
    public void xml(@NonNull Boolean isSaveLocal, @Nullable String xml) {
        if (Utils.isEmpty(xml)) {
            d(isSaveLocal, "Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(isSaveLocal, xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e(isSaveLocal, "Invalid xml");
        }
    }

    @Override
    public synchronized void log(int priority,
                                 @NonNull Boolean isSaveLocal,
                                 @Nullable String tag,
                                 @Nullable String message,
                                 @Nullable Throwable throwable) {
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }

        for (LogAdapter adapter : logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                adapter.log(priority, isSaveLocal, tag, message);
            }
        }
    }

    @Override
    public void clearLogAdapters() {
        logAdapters.clear();
    }

    @Override
    public void addAdapter(@NonNull LogAdapter adapter) {
        logAdapters.add(checkNotNull(adapter));
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int priority,
                                  @NonNull Boolean isSaveLocal,
                                  @Nullable Throwable throwable,
                                  @NonNull String msg,
                                  @Nullable Object... args) {
        checkNotNull(msg);

        String tag = getTag();
        String message = createMessage(msg, args);
        log(priority, isSaveLocal, tag, message, throwable);
    }

    /**
     * @return the appropriate tag based on local or global
     */
    @Nullable
    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return null;
    }

    @NonNull
    private String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }
}