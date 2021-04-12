package io.github.gonefuture.gameserver.utility.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 临时int id生成器,如果id达到{@link Integer#MAX_VALUE},则重置为起始值
 *
 * @author gonefuture
 * @version 2021/04/08 18:19
 */
public enum IntId {
    /**
     *  技能使用序号
     */
    USE_SKILL_SEQ(1),

    /**
     *  生物id
     */
    CREATURE(1),

    /**
     *  队伍
     */
    TEAM(1),

    /**
     *  跨服事务id
     */
    TRANSACTION(1);

    private int start;

    private final AtomicInteger id;


    IntId(int start) {
        this.start = start;
        this.id = new AtomicInteger(start);
    }

    /**
     *  获取一个id
     */

    public int take() {
        int next = id.getAndIncrement();
        // 双重检测防止超过 Integer.MAX_VALUE时,id为非正数的情况
        if (next <= 0) {
            synchronized (id) {
                if (id.get() <= 0) {
                    id.set(start);
                }
                next = id.getAndIncrement();
            }
        }
        return next;
    }
}
