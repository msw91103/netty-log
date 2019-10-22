package cn.msw.nettylog;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;

/**
 * 动态修改log文件名
 */
public class SetLogFileName {

    public static void setLogFileName(String file, String filename) {
        DailyRollingFileAppender appender = (DailyRollingFileAppender) Logger.getRootLogger().getAppender("info");
        //动态地修改这个文件名
        appender.setFile("./logs/" + file + "/ID_" + filename);
        appender.activateOptions();
    }
}
