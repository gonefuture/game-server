package io.github.gonefuture.gameserver.utility.obsever;

import java.lang.ref.WeakReference;

/**
 * 观察者封装类
 *
 * @author gonefuture
 * @version 2021/03/23 18:33
 */
public class Observer<L extends IObserver> {
    private int version;

    /**
     *  观察者所处的ObserverSupport
     */
    private WeakReference<ObserverSupport<L>> supportWeakReference;

    /**
     *  观察者具体逻辑处理体
     */
    private L o;

    private ObserverInvalidType invalidType;

    Observer(ObserverSupport<L> support, L o, int version, ObserverInvalidType invalidType) {
        this.supportWeakReference = new WeakReference<>(support);
        this.o = o;
        this.version = version;
        this.invalidType = invalidType;
    }

}
