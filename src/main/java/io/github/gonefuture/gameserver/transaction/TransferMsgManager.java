package io.github.gonefuture.gameserver.transaction;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import io.github.gonefuture.gameserver.transaction.packet.TransferMsgPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * 跨服信息传输 序列化、反序列化
 *
 * @author gonefuture
 * @version 2021/04/08 10:39
 */
@Component
public class TransferMsgManager implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferMsgManager.class);

    private static TransferMsgManager instance;

    public TransferMsgManager() {
        instance = this;
    }

    public static TransferMsgManager self() {
        return instance;
    }

    private Map<Class, Integer> classIndexMap;
    private Map<Integer, Codec> codecMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 获取有注释的bean
        String[] beanNamesForType = applicationContext.getBeanNamesForAnnotation(ITransferMsg.class);
        Arrays.sort(beanNamesForType);

        codecMap = new HashMap<>(beanNamesForType.length);
        classIndexMap = new HashMap<>(beanNamesForType.length);
        for (int i=0; i < beanNamesForType.length; i++) {
            String className = beanNamesForType[i];
            Class<?> type = applicationContext.getType(className);
            // 过滤接口注解
            if (type.isInterface()) {
                continue;
            }
            Codec codec = ProtobufProxy.create(type);
            codecMap.put(i, codec);
            classIndexMap.put(type, i);
        }
    }

    /**
     *  打包信息
     */
    public MsgData buildMsg(Object msg) {
        Integer index = classIndexMap.get(msg.getClass());
        byte[] data = encode(index, msg);
        return MsgData.valueOF(index, data);
    }

    private byte[] encode(Integer index, Object msg) {
        Codec codec = codecMap.get(index);
        if (codec == null) {
            throw new RuntimeException(String.format("not found msg codec with %s, please add @Protebug to it !", msg.getClass()));
        }
        try {
            return codec.encode(msg);
        } catch (IOException e) {
            throw new RuntimeException("msg encode fail" + msg.getClass(), e);
        }
    }


    private <Msg> Msg decode(int index, byte[] data) {
        Codec codec = codecMap.get(index);
        if (codec == null) {
            throw new RuntimeException("not found codec with event" + index);
        }
        try {
            return (Msg) codec.decode(data);
        } catch (IOException e) {
            throw new RuntimeException("msg decode fail" + codec.getClass(), e);
        }
    }

    /**
     * 解析消息
     */
    public <Msg> Msg parseMsg(MsgData msgData) {
        return decode(msgData.getIndex(), msgData.getData());
    }

    public TransferMsgPacket buildPacket(int packetId, Object msg) {
        Integer index = classIndexMap.get(msg.getClass());
        if (index == null) {
            throw new NullPointerException(msg.getClass()+"not has @ITransferMsg");

        }
        byte[] data = encode(index, msg);
        return TransferMsgPacket.valueOf(packetId, index, data);
    }



}
