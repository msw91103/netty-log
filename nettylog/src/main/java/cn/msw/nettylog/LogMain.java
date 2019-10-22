package cn.msw.nettylog;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.File;

public class LogMain {
    public static void main(String[] args) throws InterruptedException {
        String log4jFile;
        long log4jPeriod = 60 * 1000;
        if (!File.separator.equals("\\")) {
            log4jFile = "./config/log4j/log4j.xml";
        } else {
            log4jFile = LogServer.class.getResource("/").getPath() + "log4j.properties";
        }
        //在linux下能够运行
//            DOMConfigurator.configureAndWatch(log4jFile, log4jPeriod);
//            new LogServer(Integer.parseInt(args[0])).bind();

        //在windows下能运行
        PropertyConfigurator.configure(log4jFile);
        new LogServer(8080).bind();

    }
}
