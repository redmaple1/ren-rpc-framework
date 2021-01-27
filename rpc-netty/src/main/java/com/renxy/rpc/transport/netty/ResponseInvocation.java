package com.renxy.rpc.transport.netty;

import com.renxy.rpc.transport.InFlightRequests;
import com.renxy.rpc.transport.ResponseFuture;
import com.renxy.rpc.transport.command.Command;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author renxiaoya
 * @date 2021-01-27
 **/
@Slf4j
@ChannelHandler.Sharable
public class ResponseInvocation extends SimpleChannelInboundHandler<Command> {

    private final InFlightRequests inFlightRequests;

    public ResponseInvocation(InFlightRequests inFlightRequests) {
        this.inFlightRequests = inFlightRequests;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Command response) throws Exception {
        ResponseFuture future = inFlightRequests.remove(response.getHeader().getRequestId());
        if (null != future) {
            future.getFuture().complete(response);
        } else {
            log.warn("Drop response: {}", response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("Exception: ", cause);
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }
}
