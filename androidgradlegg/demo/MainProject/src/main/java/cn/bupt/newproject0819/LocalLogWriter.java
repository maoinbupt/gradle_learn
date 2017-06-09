package cn.bupt.newproject0819;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by GaoFeng on 2017.03.20.
 */
public class LocalLogWriter {
    protected LinkedBlockingDeque<String> localLogQueue = new LinkedBlockingDeque(5000);
    private LocalLogWriter.FileAppendTask task;
    private boolean queueStatus = true;
    private Object lock = new Object();

    public LocalLogWriter() {
    }

    public void start() {
        this.task = new LocalLogWriter.FileAppendTask();
        Thread thread = new Thread(this.task);
        thread.setName("LocalLogWriter");
        thread.start();
    }

    public void writeToLocal(String log) {
        boolean flag = this.localLogQueue.offer(log);
        this.task.notifyTask();
        if(!flag) {
            try {
                Object e = this.lock;
                synchronized(this.lock) {
                    this.queueStatus = false;
                    this.lock.wait();
                    this.writeToLocal(log);
                }
            } catch (InterruptedException var5) {
                var5.printStackTrace();
            }
        }

    }

    private final class FileAppendTask implements Runnable {
        private boolean isActive = true;
        private Object obj = new Object();

        public FileAppendTask() {
        }

        public void run() {
            while(this.isActive) {
                ArrayList copyed = new ArrayList();
                int size = LocalLogWriter.this.localLogQueue.size();
                if(size > 0) {
                    LocalLogWriter.this.localLogQueue.drainTo(copyed, 200);
                    if(copyed != null && copyed.size() > 0) {
                        File file = this.getLogFile();
                        if(file != null) {
                            BufferedWriter e = null;

                            try {
                                e = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

                                for(int i = 0; i < copyed.size(); ++i) {
                                    String log = (String)copyed.get(i);
                                    e.write((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date()) + ":" + log + "\n");
                                }
                            } catch (Exception var21) {
                                ;
                            } finally {
                                try {
                                    if(e != null) {
                                        e.close();
                                    }
                                } catch (IOException var17) {
                                    ;
                                }

                            }
                        }
                    }
                }

                if(!LocalLogWriter.this.queueStatus) {
                    synchronized(LocalLogWriter.this.lock) {
                        LocalLogWriter.this.queueStatus = true;
                        LocalLogWriter.this.lock.notifyAll();
                    }
                }

                this.isActive = LocalLogWriter.this.localLogQueue.size() > 0;
                if(!this.isActive) {
                    Object var23 = this.obj;
                    synchronized(this.obj) {
                        try {
                            this.isActive = false;
                            this.obj.wait();
                        } catch (InterruptedException var18) {
                            var18.printStackTrace();
                        }
                    }
                }
            }

        }

        private File getLogFile() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH");
            String time = format.format(new Date());
            return LogUtils.getLogFile("applog-" + time + ".txt");
        }

        public void notifyTask() {
            if(!this.isActive) {
                Object var1 = this.obj;
                synchronized(this.obj) {
                    this.isActive = true;
                    this.obj.notifyAll();
                }
            }

        }
    }
}
