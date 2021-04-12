package io.github.gonefuture.gameserver.rpc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记远程调用方法唯一ID(全局) 用于减少方法调用时数据包大小
 *
 * @author gonefuture
 * @version 2021/03/23 10:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcMethod {

    /**
     *  远程方法调用 唯一ID 不能为0
     */
    int value();
}
