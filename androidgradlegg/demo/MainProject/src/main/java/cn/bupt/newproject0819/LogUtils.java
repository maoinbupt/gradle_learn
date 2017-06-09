package cn.bupt.newproject0819;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by GaoFeng on 2017.03.20.
 */
public class LogUtils {
    private static final String TAG = "LogUtils";
    private static boolean ENABLE_LOG = true;
    private static boolean enableLocalLog = false;
    private static LocalLogWriter localLogWriter;
    private static String LOG_DIR = Environment.getExternalStorageDirectory() + "/sohu/SohuVideo/trace/Logs";
    private static final int MAX_LOG_NUM = 20;
    private static boolean isDeleting = false;
    private static long lastDeleteTime = 0L;
    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    private static int MAX_LOG_SIZE = 2000;

    public LogUtils() {
    }

    public static boolean isDebug() {
        return ENABLE_LOG;
    }

    public static final void setDebugMode(boolean enabled) {
        ENABLE_LOG = enabled;
    }

    public static boolean isEnableLocalLog() {
        return enableLocalLog;
    }

    public static void setEnableLocalLog(boolean enable) {
        if(enable && localLogWriter == null) {
            localLogWriter = new LocalLogWriter();
            localLogWriter.start();
        }

        enableLocalLog = enable;
    }

    public static String getLocalLogDir() {
        return LOG_DIR;
    }

    public static void setLocalLogDir(String strLocalLogDir) {
        LOG_DIR = strLocalLogDir;
    }

    public static final void d(String tag, String msg) {
        if(ENABLE_LOG) {
            logOverSize(tag, msg);
        }

    }

    public static final void d(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.d(tag, msg, tr);
            logToLocal(tag + ": " + msg + "\n" + Log.getStackTraceString(tr));
        }

    }

    public static final void i(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.i(tag, msg);
            logToLocal(tag + ": " + msg);
        }

    }

    public static final void i(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.i(tag, msg, tr);
            logToLocal(tag + ": " + msg + "\n" + Log.getStackTraceString(tr));
        }

    }

    public static final void w(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.w(tag, msg);
            logToLocal(tag + ": " + msg);
        }

    }

    public static final void w(String tag, Throwable tr) {
        if(ENABLE_LOG) {
            Log.w(tag, tr);
            logToLocal(tag + ": " + Log.getStackTraceString(tr));
        }

    }

    public static final void w(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.w(tag, msg, tr);
            logToLocal(tag + ": " + msg + "\n" + Log.getStackTraceString(tr));
        }

    }

    public static final void e(Throwable e) {
        if(ENABLE_LOG) {
            Log.e("LogUtils", e.toString(), e);
            logToLocal(Log.getStackTraceString(e));
        }

    }

    public static final void e(String tag, String msg) {
        if(ENABLE_LOG) {
            Log.e(tag, msg);
            logToLocal(tag + ": " + msg);
        }

    }

    public static final void e(String tag, Throwable tr) {
        if(ENABLE_LOG) {
            Log.e(tag, "", tr);
            logToLocal(tag + ": " + Log.getStackTraceString(tr));
        }

    }

    public static final void e(String tag, String msg, Throwable tr) {
        if(ENABLE_LOG) {
            Log.e(tag, msg, tr);
            logToLocal(tag + ": " + msg + "\n" + Log.getStackTraceString(tr));
        }

    }

    public static final void p(Object obj) {
        if(ENABLE_LOG) {
            System.out.println(obj);
            logToLocal(obj != null?obj.toString():"null");
        }

    }

    public static final void p(String tag, Object obj) {
        if(ENABLE_LOG) {
            System.out.println(tag + ": " + obj);
            logToLocal(tag + ": " + (obj != null?obj.toString():"null"));
        }

    }

    public static String getTraceInfo() {
        if(!ENABLE_LOG) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            StackTraceElement[] stacks = (new Throwable()).getStackTrace();
            sb.append("[file:").append(stacks[1].getFileName()).append(",line:").append(stacks[1].getLineNumber()).append(",method:").append(stacks[1].getMethodName() + "];");
            return sb.toString();
        }
    }

    public static synchronized void logToLocal(String log) {
        if(ENABLE_LOG && enableLocalLog) {
            if(!TextUtils.isEmpty(log)) {
//                localLogWriter.writeToLocal(log);
//                deleteLocal();
            }
        }
    }

    public static synchronized void fileLog(String fileName, String log) {
        if(ENABLE_LOG && enableLocalLog) {
            if(!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(log)) {
                File file = getLogFile(fileName);
                if(file != null) {
                    BufferedWriter out = null;

                    try {
                        out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
                        out.append((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date()) + ":");
                        out.append(log + "\n");
                    } catch (Exception var13) {
                        ;
                    } finally {
                        try {
                            if(out != null) {
                                out.close();
                            }
                        } catch (IOException var12) {
                            ;
                        }

                    }

                }
            }
        }
    }


    public static boolean delLogFile(String fileName) {
        File file = new File(LOG_DIR + File.separator + fileName);
        boolean ret = false;

        try {
            ret = file.delete();
        } catch (Exception var4) {
            ;
        }

        return ret;
    }

    public static File getLogFile(String fileName) {
        File file = new File(LOG_DIR);
        if(!file.exists() && !file.mkdirs()) {
            return null;
        } else {
            file = new File(LOG_DIR + File.separator + fileName);
            if(file.exists()) {
                return file;
            } else {
                boolean ret = false;

                try {
                    ret = file.createNewFile();
                } catch (IOException var4) {
                    ;
                }

                return ret?file:null;
            }
        }
    }

    public static void printStackTrace(Throwable e) {
        if(ENABLE_LOG && e != null) {
            e(e);
        }

    }

    public static boolean canShow() {
        return ENABLE_LOG;
    }

    public static boolean canQAShow() {
        return false;
    }

    private static void logOverSize(String tag, String veryLongString) {
        if(tag != null && veryLongString != null) {
            for(int i = 0; i <= veryLongString.length() / MAX_LOG_SIZE; ++i) {
                int start = i * MAX_LOG_SIZE;
                int end = (i + 1) * MAX_LOG_SIZE;
                end = end > veryLongString.length()?veryLongString.length():end;
                Log.d(tag, veryLongString.substring(start, end));
            }

            logToLocal(tag + ": " + veryLongString);
        }
    }
}
