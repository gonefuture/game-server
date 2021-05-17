package io.github.gonefuture.gameserver.object;

import io.github.gonefuture.gameserver.object.knowlist.KnowList;

/**
 * @author gonefuture  gonefuture@qq.com
 * @version 1.00
 * @since 2021/03/12 10:42
 * Description:  世界唯一对象
 */
public abstract class AbstractObject {

    /**
     * 配置id
     */
    private int objectKey;


    protected long objectId;

    /**
     *  对象id的包装类型
     */
    protected Long objId;

    public AbstractObject(long objectId) {
        this.objectId = objectId;
    }
}
