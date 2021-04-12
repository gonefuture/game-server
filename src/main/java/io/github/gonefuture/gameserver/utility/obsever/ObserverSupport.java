package io.github.gonefuture.gameserver.utility.obsever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * 观察者支持器
 *
 * @author gonefuture
 * @version 2021/03/24 12:27
 */
public class ObserverSupport<L extends IObserver> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObserverSupport.class);

    /**
     *  监听的实际观察者接口
     */
    private Class<L> lClass;

    /**
     *  观察者封装体列表
     */
    private List<Observer<L>> observers;


    public ObserverSupport(Class<L> lClass) {
        this.lClass = lClass;
        this.observers = new CopyOnWriteArrayList<>();
    }

    /**
     *  绑定观察者
     */
    public Observer<L> attach(L o, int version, ObserverInvalidType invalidType) {
        Observer<L> observer = new Observer<>(this, o, version, invalidType);
        observers.add(observer);
        return observer;
    }

    /**
     *  触发支持器中
     */
}
