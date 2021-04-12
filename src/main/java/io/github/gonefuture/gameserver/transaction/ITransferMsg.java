package io.github.gonefuture.gameserver.transaction;

import java.lang.annotation.*;

/**
 * 跨服消息包注解
 *
 * @author gonefuture
 * @version 2021/03/29 16:14
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ITransferMsg {
}
