package io.github.gonefuture.utility;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * xml反序列化工具
 *
 * @author gonefuture
 * @version 2021/04/21 10:10
 */
public class XmlSerializer {

    /**
     *  写入数据到xml
     * @param list 对象集合
     * @param fos 输出流
     * @throws IOException io异常
     */
    public static void write(Collection<?> list, OutputStream fos) throws IOException {
        XMLEncoder encoder = new XMLEncoder(fos);
        for (Object object : list) {
            encoder.writeObject(object);
        }
        encoder.flush();
        encoder.close();
        fos.close();
    }


    /**
     *  从excel中读取数据
     * @param fis 输入流
     * @return 对象列表，需要y
     * @throws IOException io异常
     */
    public static List<?> read(InputStream fis) throws IOException {
        List<Object> objectList = new ArrayList<>();
        XMLDecoder decoder = new XMLDecoder(fis);
        Object object =  decoder.readObject();
        while (object != null) {
            objectList.add(object);
            try {
                object = decoder.readObject();
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }
        decoder.close();
        fis.close();
        return objectList;
    }
}
