package com.lib_foundation.logger;

import static com.lib_foundation.logger.Utils.checkNotNull;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DiskLogStrategy implements LogStrategy {

    @NonNull
    private final Handler handler;

    public DiskLogStrategy(@NonNull Handler handler) {
        this.handler = checkNotNull(handler);
    }

    @Override
    public void log(int level, @NonNull Boolean isSaveLocal, @Nullable String tag, @NonNull String message) {
        checkNotNull(message);

        Log.println(level, tag, message);

        if (isSaveLocal) {
            // do nothing on the calling thread, simply pass the tag/msg to the background thread
            handler.sendMessage(handler.obtainMessage(level, message));
        }
    }

    static class WriteHandler extends Handler {

        @NonNull
        private final String folder;
        private final int maxFileSize;

        WriteHandler(@NonNull Looper looper, @NonNull String folder, int maxFileSize) {
            super(checkNotNull(looper));
            this.folder = checkNotNull(folder);
            this.maxFileSize = maxFileSize;
        }

        @SuppressWarnings("checkstyle:emptyblock")
        @Override
        public void handleMessage(@NonNull Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, "logs");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content + "\n");

                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                if (fileWriter != null) {
                    try {
                        fileWriter.flush();
                        fileWriter.close();
                    } catch (IOException e1) { /* fail silently */ }
                }
            }
        }

        /**
         * This is always called on a single background thread.
         * Implementing classes must ONLY write to the fileWriter and nothing more.
         * The abstract class takes care of everything else including close the stream and catching IOException
         *
         * @param fileWriter an instance of FileWriter already initialised to the correct file
         */
        private void writeLog(@NonNull FileWriter fileWriter, @NonNull String content) throws IOException {
            checkNotNull(fileWriter);
            checkNotNull(content);

            fileWriter.append(content);
        }

        private File getLogFile(@NonNull String folderName, @NonNull String fileName) {
            checkNotNull(folderName);
            checkNotNull(fileName);

            File folder = new File(folderName);
            if (!folder.exists()) {
                //TODO: What if folder is not created, what happens then?
                folder.mkdirs();
            }

            int newFileCount = 0;
            File newFile;
            File existingFile = null;

            newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
            while (newFile.exists()) {
                existingFile = newFile;
                newFileCount++;
                newFile = new File(folder, String.format("%s_%s.csv", fileName, newFileCount));
            }

            if (existingFile != null) {
                if (existingFile.length() >= maxFileSize) {
                    return newFile;
                }
                return existingFile;
            }

            return newFile;
        }
    }
}