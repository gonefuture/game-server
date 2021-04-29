package io.github.gonefuture.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

/**
 * @author gonefuture
 * @version 2021/4/29 15:28
 */
public class RpcResponse {

    @Protobuf
    long reqId;

    @Protobuf
    private byte[] retBytes;

    @Protobuf
    private String error;


}
