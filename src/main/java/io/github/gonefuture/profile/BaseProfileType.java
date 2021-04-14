package io.github.gonefuture.profile;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * 性能监控类型定义
 *
 * @author gonefuture
 * @version 2021/04/13 17:18
 */
public abstract class BaseProfileType<PK, Column extends Enum<Column> & IProColumn>  {

    /**
     *  监控描述
     */
    private String desc;

    /**
     *  监控字段定义enum
     */
    private Class<Column> columnClz;

    /**
     *  监控信息
     */
    private Profile<PK, Column> profile;

    /**
     * 单条性能信息线程缓存，用于优化性能
     */
    private FastThreadLocal<ProRow<PK, Column>> proRow = new FastThreadLocal<ProRow<PK, Column>>() {
        @Override
        protected ProRow<PK, Column> initialValue() throws Exception {
            int length = columnClz.getEnumConstants().length;
            return new ProRow<>(length);
        }
    };

    /**
     *  自定义输出格式
     */
    protected abstract Pro
}
