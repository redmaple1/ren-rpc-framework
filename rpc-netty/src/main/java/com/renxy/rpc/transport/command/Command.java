package com.renxy.rpc.transport.command;

/**
 * @author renxiaoya
 * @date 2021-01-25
 **/
public class Command {

    protected Header header;

    private byte[] payload;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }

    public Command(Header header, byte[] payload) {
        this.header = header;
        this.payload = payload;
    }
}
