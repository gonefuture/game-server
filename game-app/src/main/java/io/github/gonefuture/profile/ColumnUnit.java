package io.github.gonefuture.profile;

import io.github.gonefuture.utility.DateUtils;
import io.github.gonefuture.utility.FileHelper;

import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

/**
 * 列记录数据
 *
 * @author gonefuture
 * @version 2021/04/12 16:39
 */
public enum ColumnUnit {

    /**
     * 无
     */
    SIMPLE(""),

    /**
     *  纳秒转毫秒
     */
    NANO_TO_MS("ms") {
        @Override
        public long transferValue(long input) {
            return input / 10000;
        }

        @Override
        public String convertValue(long input) {
            return String.format("%.2f", input / 100F);
        }
    },

    /**
     *  文件大小
     */
    FILE_ZIE("byte") {
        @Override
        public String convertValue(long input) {
            return FileHelper.formatFileSize(input);
        }
    },

    /**
     *  格式化后的时间长度
     */
    FORMAT_RANGE_TIME("") {
        @Override
        public long transferValue(long input) {
            return input / 10000;
        }

        @Override
        public String convertValue(long input) {
            return DateUtils.formatRangeTime(input / 100, TimeUnit.MILLISECONDS);
        }
    },


    ;

    /**
     *  列 单位的名称
     */
    private final String unitName;

    ColumnUnit(String unitName) {
        this.unitName = unitName;
    }


    /**
     *  数值转换
     * @param input 输入数据
     * @return  输出数据
     */
    public long transferValue(long input) {
        return input;
    }

    /**
     *  数值输出格式化转换
     * @param input 输入数据
     * @return 输出用于展示的数据
     */
    public String convertValue(long input) {
        return String.valueOf(input);
    }

    public String getUnitName() {
        return unitName;
    }

}
