package com.netease.tools.abtestuicreator.util;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zyl06 on 09/01/2018.
 */

public class FileUtil {

    /**
     * @param path    : the path where write to.
     * @param content : the content will be write.
     * @throws IOException : the exception.
     */
    public static void writeToFile(String path, String content)
            throws IOException {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(content)) {
            return;
        }

        writeToFile(path, new ByteArrayInputStream(content.getBytes()));
    }

    public static void writeToFile(String path, InputStream in)
            throws IOException {
        if (TextUtils.isEmpty(path) || in == null) {
            return;
        }

        File file = new File(path);
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);

            int inputLength;
            final byte[] buffer = new byte[16 * 1024];
            while (-1 != (inputLength = in.read(buffer))) {
                out.write(buffer, 0, inputLength);
                out.flush();
            }
            out.flush();
        } catch (IOException e) {
            LogUtil.e(e.toString());
        } finally {
            dispose(out);
        }
    }

    public static void dispose(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
