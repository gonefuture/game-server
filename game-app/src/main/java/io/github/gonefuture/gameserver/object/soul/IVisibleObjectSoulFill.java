package io.github.gonefuture.gameserver.object.soul;

import io.github.gonefuture.gameserver.object.AbstractVisibleObject;
import io.github.gonefuture.gameserver.object.controller.AbstractVisibleObjectController;

/**
 * 填充可见单位各种控制器
 *
 * @author gonefuture
 * @version 2021/03/12 10:52
 */
public interface IVisibleObjectSoulFill extends IObjectSoulFill {
    /**
     *  获取配置key
     */
    int getObject();

    /**
     *  创建可见单位的控制器
     */
    AbstractVisibleObjectController createController(AbstractVisibleObject visibleObject);


}
