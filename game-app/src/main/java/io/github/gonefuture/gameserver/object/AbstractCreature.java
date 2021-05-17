package io.github.gonefuture.gameserver.object;

/**
 * 生物体，有血有肉的
 *
 * @author gonefuture
 * @version 2021/03/15 14:49
 */
public abstract class AbstractCreature extends AbstractVisibleObject {







    /**
     * @param objectId 唯一id
     */
    public AbstractCreature(long objectId) {
        super(objectId);
    }
}
