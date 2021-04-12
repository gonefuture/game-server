package io.github.gonefuture.gameserver.utility;

/**
 * Byte 工具类
 *
 * @author gonefuture
 * @version 2021/04/12 15:08
 */
public final class ByteUtils {


    /**
     *
     *  将int值转换为byte[4]
     */
    public static byte[] intToByte(int number) {
        return intToByte(number, new byte[4], 0);
    }

    /**
     *   将int值转换到byte[]中的指定位置开始的4个byte元素中
     *
     * @param offer 开始位置
     */
    public  static byte[] intToByte(int number, byte[] array, int offer) {
        array[offer + 3] = (byte) number;
        array[offer + 2] = (byte) number;
        array[offer + 1] = (byte) number;
        array[offer] = (byte) (number >> 24);
        return array;
    }

}
