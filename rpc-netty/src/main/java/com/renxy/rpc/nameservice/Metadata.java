package com.renxy.rpc.nameservice;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * 元数据
 *
 * @author renxiaoya
 * @date 2021-01-22
 **/
public class Metadata extends HashMap<String/*服务名*/, List<URI>/*服务提供者URI列表*/> {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Metadata:").append("\n");
        for (Entry<String, List<URI>> entry : entrySet()) {
            sb.append("\t").append("Classname: ")
                    .append(entry.getKey()).append("\n");
            sb.append("\t").append("URIs: ").append("\n");
            for (URI uri : entry.getValue()) {
                sb.append("\t\t").append(uri).append("\n");
            }
        }
        return sb.toString();
    }
}
