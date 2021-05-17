package io.github.gonefuture.utility;

import java.util.concurrent.TimeUnit;

/**
 * 时间工具
 *
 * @author gonefuture
 * @version 2021/04/13 11:48
 */
public class DateUtils {


    /**
     * 将长度时间转换为xx天xx小时xx秒xx毫秒
     * @param rangeTime 时长
     * @param timeUnit 时间单位
     * @return 时间表示
     */
    public static String formatRangeTime(long rangeTime, TimeUnit timeUnit) {
        rangeTime =  timeUnit.toMillis(rangeTime);
        StringBuilder builder = new StringBuilder();
        // todo
        return null;
    }
}
