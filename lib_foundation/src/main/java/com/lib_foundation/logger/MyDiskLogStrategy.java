package com.lib_foundation.logger;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.orhanobut.logger.LogStrategy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MyDiskLogStrategy implements LogStrategy {

    private final Handler handler;

    public MyDiskLogStrategy(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void log(int level, String tag, @NonNull String message) {
        handler.sendMessage(handler.obtainMessage(level, message));
        Log.println(level, tag, message);
    }

    static class WriteHandler extends Handler {

        private final String folder;
        private final int maxFileSize;

        WriteHandler(Looper looper, String folder, int maxFileSize) {
            super(looper);
            this.folder = folder;
            this.maxFileSize = maxFileSize;
        }

        @SuppressWarnings("checkstyle:emptyblock")
        @Override
        public void handleMessage(Message msg) {
            String content = (String) msg.obj;

            FileWriter fileWriter = null;
            File logFile = getLogFile(folder, "logs");

            try {
                fileWriter = new FileWriter(logFile, true);

                writeLog(fileWriter, content);

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
        private void writeLog(FileWriter fileWriter, String content) throws IOException {
            fileWriter.append(content);
        }

        private File getLogFile(String folderName, String fileName) {
            File folder = new File(folderName);
            if (!folder.exists()) {
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