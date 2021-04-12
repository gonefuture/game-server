package io.github.gonefuture.gameserver.rpc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记某个字段需要注入远程接口调用（为系统单例实现，不包含任务远程信息）
 * 方法返回值为Void 数据只会单向发送
 * 返回值为RpcResponseFuture可以直接得到Future
 * 返回其他值会同步等待
 *
 * @author gonefuture
 * @version 2021/03/23 10:23
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcConsumer {
}
