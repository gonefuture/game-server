package io.github.gonefuture.utility;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 额外的文本工具
 *
 * @author gonefuture
 * @version 2021/4/26 20:54
 */
public class StringUtilsE {

    /**
     *  汉字编码的开始和结束
     */
    private static int start = Integer.valueOf("4e00", 16);
    private static int end = Integer.valueOf("9fa5", 16);


    /**
     *  计算中英文字符混合情况下，字符总数；一个汉字相当于2个字符长度
     * @param input 输入文本
     * @return 计算出的长度
     */
    public static int getLength(String input) {
        int length = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            length += (start <= c && end >= c) ? 2 : 1;
        }
        return length;
    }

    /**
     *  转换int数组，支持多种分割符号,{@link org.springframework.context.ConfigurableApplicationContext#CONFIG_LOCATION_DELIMITERS}
     * @param source 文本源
     * @return 整形数组
     */
    public static int[] toIntArray(String source) {
        String[] str = StringUtils.tokenizeToStringArray(source, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        int[] array = new int[str.length];
        for (int i=0; i < str.length; i++) {
            array[i] = Integer.parseInt(str[i]);
        }
        return array;
    }



}
