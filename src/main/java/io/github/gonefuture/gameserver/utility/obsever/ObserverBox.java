package io.github.gonefuture.gameserver.utility.obsever;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 观察者信息
 *
 * @author gonefuture
 * @version 2021/03/24 12:20
 */
public class ObserverBox<BaseL extends IObserver> {

    private AtomicInteger version = new AtomicInteger(1);

    /**
     *  观察基础接口
     */
    private Class<BaseL> baseLClass;

    /**
     *  观察者集合<观察事件类型，观察者集合>
     */
    private Map<Class<? extends BaseL>, ObserverSupport<?>> observers = new ConcurrentHashMap<>();

    public ObserverBox(Class<?> baseLClass) {
        this.baseLClass = (Class<BaseL>) baseLClass;
    }

    /**
     *  绑定永久性观察者，观察者的生命后期由外部自己管理，注意在必要情况下手动移除
     */
    public <L extends BaseL, O extends L> Observer<L> attachForever(Class<L> lClass, O observer) {
        return attach(lClass, ObserverInvalidType.ONE_OFF, observer);
    }


    /**
     *  绑定自定义失效方式的观察者，手动调用{@link #tryRemoveInvalid(ObserverInvalidType)} 移除指定类型的观察者
     */
    public <L extends BaseL, O extends L> Observer<L> attach(Class<L> lClass, ObserverInvalidType invalidType, O observer) {
        ObserverSupport<L> observerSupport = getOrCreateSupport(lClass);
        int version = this.version.getAndIncrement();
        return observerSupport.attach(observer, version, invalidType);
    }

    /**
     * 获取或创建指定观察者事件接口对应的观察者支持器
     */
    protected <L extends BaseL> ObserverSupport<L> getOrCreateSupport(Class<L> lClass) {
        return (ObserverSupport<L>) observers.computeIfAbsent(lClass, ObserverSupport::new);
    }


}
