package cn.msw.nettylog;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基带日志回传Handler
 */
public class LogServerHandler extends ChannelInboundHandlerAdapter {
    private static Logger log = Logger.getLogger(LogServerHandler.class);
    // 存储连接和用户信息的map，键为连接编号，值为用户名
    private Map<String, String> userMap = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端已经连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String msgStr = new String(msg.toString().getBytes());
        String id = ctx.channel().id().asShortText();
        // 如果msg以name为前缀，则设置当前连接的用户名
        if (msgStr.startsWith("id")) {
            String name = "";
            if (msgStr.length() > 2) {
                name = msgStr.substring(2, 6);
                SetLogFileName.setLogFileName(name, name);
                log.info(msgStr);
            }
            userMap.put(id, name);
        } else {
            String fileName = userMap.get(id);
            SetLogFileName.setLogFileName(fileName, fileName);
            log.info(msgStr);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
