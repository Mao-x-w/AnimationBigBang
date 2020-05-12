package com.weknowall.cn.wuwei.utils.rxjava;

/**
 * User: laomao
 * Date: 2018-11-26
 * Time: 15-32
 * <p>
 * 1.Observable需要传递一个OnSubscribe对象，并保存为成员变量，以便调用该对象当中的方法
 * 2.OnSubscribe是一个接口，它的未实现方法是call(Subscriber<? super T> subscribe),因此调用该方法，需要传递一个Subscriber对象
 * 3.Observable中有一个subscribe方法，用于将被观察者和观察者连到一起，需要传递一个Subscriber对象，在这个方法中调用了
 * OnSubscriber的call方法，即我们在call中写的逻辑开始执行
 * <p>
 * Observable在创建的时候需要传递个OnSubscribe对象，这个对象是我们在使用的时候传递进去的，它是一个接口，内部有一个
 * 未实现的方法，call(Subscriber<? super T> subscriber)
 */

public class Observable<T> {

    private OnSubscribe<T> mOnSubscribe;

    private Observable(OnSubscribe<T> onSubscribe) {
        mOnSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe) {
        return new Observable<T>(onSubscribe);
    }

    public void subscribe(Subscriber<? super T> subscriber) {
        subscriber.onStart();
        mOnSubscribe.call(subscriber);
    }

    /**
     * map转换，目的是实现数据转换，可以转换类型，或者是转换该类型下的数据
     * 1.创建一个新的Observable对象，这个对象是转换后的，这个时候新的Observable中就有一个OnSubscribe对象，它的call方法
     * 中有subscriber对象，是我们在具体使用时通过subscribe传递的
     * 2.然后再调用当前Observable的subscribe方法，创建一个Subscriber，在onNext中，进行数据转换。由于是在当前的Observable
     * 中调用的subscribe方法，所以能够得到当前Observable对应的数据，从而进行转换，转换得到的数据通过1中的subscriber的onNext传给下层
     * 3.转换类是Transformer,是个接口，定义了call方法，将T转换为R，在2中的onNext中调用Transformer的call方法，实现转换
     * 4.我们在调用map的时候去实现Transformer，实现转换的具体逻辑
     *
     * @param transformer
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Transformer<? super T, ? extends R> transformer) {
        return create(new OnSubscribe<R>() {
            @Override
            public void call(Subscriber<? super R> subscriber) {
                Observable.this.subscribe(new Subscriber<T>() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onNext(T t) {
                        subscriber.onNext(transformer.call(t));
                    }
                });
            }
        });
    }

    public <R> Observable<R> map1(Transformer<? super T, ? extends R> trTransformer) {
        return create(new MapOnSubscribe<T, R>(this, this, trTransformer));
    }

    /**
     * subscribeOn是用来指定调用这个方法之前的call方法执行的线程，如果调用了多个subscribeOn，只有第一个有效（其实这种说法不太严格
     * 两个subscribeOn之间的是执行在后面那个subscribeOn所指定的线程，不过没意义）
     * 1.创建一个新的Observable对象
     * 2.创建一个新的OnSubscribe对象，并传递给Observable
     * 3.在新的OnSubscribe中的call方法中，进行切换线程
     * 4.调用当前Observable的OnSubscribe对象的call方法，即在新线程中调用了该call方法，所以实现了线程切换
     * 5.由于是调用当前Observable中OnSubscribe中的call方法，所以该方法中所有执行代码都是运行在新线程中
     *
     * @param scheduler
     * @return
     */
    public Observable<T> subscribeOn(Scheduler scheduler) {
        return create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();

                scheduler.createWorker().schedule(new Runnable() {
                    @Override
                    public void run() {
                        Observable.this.mOnSubscribe.call(subscriber);
                    }
                });
            }
        });
    }

    /**
     * ObserverOn是用来指定调用observerOn之后的Subscriber中的onComplete()、onError()、onNext()方法所执行的线程
     *
     * 1.创建一个新的Observable
     * 2.创建一个新的OnSubscribe对象，并传递给Observable
     * 3.在新的OnSubscribe中的call方法中调用当前Observable中的OnSubscribe中的call方法
     * 4.给3中的call方法传递新的Subscriber对象
     * 5.并在该Subscriber中的onComplete()、onError()、onNext()方法中切换线程，并调用传递给2中的call方法的Subscriber的对应方法
     * 这样就实现了observerOn后面的执行在新线程中了
     *
     * @param scheduler
     * @return
     */
    public Observable<T> observerOn(Scheduler scheduler) {
        return create(new OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                subscriber.onStart();

                Scheduler.Worker worker = scheduler.createWorker();
                Observable.this.mOnSubscribe.call(new Subscriber<T>() {
                    @Override
                    public void onComplete() {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onComplete();
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable t) {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onError(t);
                            }
                        });
                    }

                    @Override
                    public void onNext(T t) {
                        worker.schedule(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onNext(t);
                            }
                        });
                    }
                });
            }
        });
    }

    public interface OnSubscribe<T> {
        void call(Subscriber<? super T> subscriber);
    }

    public interface Transformer<T, R> {
        R call(T t);
    }
}
