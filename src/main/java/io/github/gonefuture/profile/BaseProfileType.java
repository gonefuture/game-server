package io.github.gonefuture.profile;

import io.netty.util.concurrent.FastThreadLocal;

import java.io.PrintStream;

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





    public BaseProfileType(String desc, Class<Column> columnClz) {
        profile = new Profile<PK, Column>(columnClz);
        this.desc = desc;
        proRow = new FastThreadLocal<ProRow<PK, Column>>() {
            @Override
            protected ProRow<PK, Column> initialValue() throws Exception {
                int length = columnClz.getEnumConstants().length;
                return new ProRow<>(length);
            }
        };
    }



    /**
     * 单条性能信息线程缓存，用于优化性能
     */
    private FastThreadLocal<ProRow<PK, Column>> proRow = null;


    /**
     *  自定义输出格式
     */
    protected abstract ProPrinter<PK, Column> createPrinter(Profile<PK,Column> profile);

    public String getDesc() {
        return desc;
    }

    public Profile<PK, Column> getProfile() {
        return profile;
    }

    /**
     * 创建一行需要记录的信息，填充完毕后调用{@link ProRow#submit()} 进行提交
     *
     */
    public ProRow<PK, Column> createRow(PK pk) {
        ProRow<PK, Column> proRow = this.proRow.get();
        ProRowInfo<PK, Column> proRowInfo = profile.getOrCreateRowInfo(pk);
        proRow.reset(pk, proRowInfo);
        return proRow;
    }

    /**
     * 性能统计信息
     */
    public Profile<PK, Column> columnProfile(boolean reset) {
        return profile.collect(reset);
    }

    /**
     *  输出
     */
    public void printAndRest(PrintStream printStream, boolean reset) {
        Profile<PK, Column> collect = profile.collect(reset);
        ProPrinter<PK, Column> printer = createPrinter(collect);
        printer.printTable(collect, printStream);
    }



}
