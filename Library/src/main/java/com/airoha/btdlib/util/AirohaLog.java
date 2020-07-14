package com.airoha.btdlib.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Evonne.Hsieh on 2016/6/7.
 */
public class AirohaLog {
    private static AirohaLog ourInstance;
    private static FileOutputStream fos;

    private static boolean DBG = true;

    public static AirohaLog getInstance() {
        if (null == ourInstance) {
            ourInstance = new AirohaLog();
        }

        return ourInstance;
    }

    private AirohaLog() {fos = null;}

    public static void LogToFile(String log)
    {
        if(!DBG)
            return;

        try {
            File file = new File("/sdcard/AirohaBTD.log");
            fos = new FileOutputStream(file, true);
            if (!file.exists()) {
                file.createNewFile();
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            fos.write((timeStamp + ": ").getBytes());

            log = log +"\n";

            fos.write(log.getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
