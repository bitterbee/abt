package com.netease.libs.abtestbase;

import android.content.Context;
import android.text.TextUtils;

import com.netease.libs.abtestbase.model.ABTestUICase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyl06 on 2018/7/31.
 */

public class ABTestFileUtil {

    private static String getABTestUIFilePath(Context context) {
        File dir = context.getCacheDir();
        if (dir == null) {
            return null;
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "abtest_ui.txt");
        return file.getAbsolutePath();
    }

    public static List<ABTestUICase> readUiCases(Context context) {
        String path = getABTestUIFilePath(context);
        if (TextUtils.isEmpty(path)) {
            return new ArrayList<>();
        }
        String json = read(new File(path));
        return JsonUtil.parseArray(json, ABTestUICase.class);
    }

    public static boolean writeUiCases(Context context, List<ABTestUICase> cases) {
        try {
            String path = getABTestUIFilePath(context);
            ABTestFileUtil.writeToFile(path, JsonUtil.toJSONString(cases));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

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
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file, false);

            int inputLength;
            final byte[] buffer = new byte[16 * 1024];
            while (-1 != (inputLength = in.read(buffer))) {
                out.write(buffer, 0, inputLength);
                out.flush();
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static String read(File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            return read(is, "UTF-8");
        } catch (IOException e) {
            ABLog.e(e);
        }

        return null;
    }

    public static String read(InputStream is) {
        return read(is, "UTF-8");
    }

    public static String read(InputStream is, String charset) {
        if (is == null) {
            return null;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            int bufSize = 128;
            byte[] buf = new byte[bufSize];
            int count = -1;
            while ((count = is.read(buf, 0, bufSize)) > 0) {
                os.write(buf, 0, count);
            }
            os.flush();
            return new String(os.toByteArray(), charset);
        } catch (IOException e) {
            ABLog.e(e);
        } catch (Exception e) {
            ABLog.e(e);
        } finally {
            dispose(is);
            dispose(os);
        }

        return null;
    }
}
