package io.github.gonefuture.rpc;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;

/**
 * Rpc请求
 *
 * @author gonefuture
 * @version 2021/03/16 12:00
 */

@ProtobufClass
// @SocketPacket
public class RpcRequest {


    @Protobuf
    private int methodUid;

    @Protobuf
    private String className;

    @Protobuf
    private String methodName;

    @Protobuf
    private String methodDesc;

    @Protobuf
    private byte[] argBytes;

    @Protobuf
    long requestId;



}
