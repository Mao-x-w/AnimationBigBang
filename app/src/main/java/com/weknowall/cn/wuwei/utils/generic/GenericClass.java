package com.weknowall.cn.wuwei.utils.generic;

/**
 * User: laomao
 * Date: 2018-12-04
 * Time: 15-48
 */

public class GenericClass<T> {

    private T mT;

    public GenericClass(T t) {
        mT = t;
        GenericInterface<String> hshhs = getGInterface("hshhs");
    }

    public T getT() {
        return mT;
    }

    public void setT(T t) {
        mT = t;
    }

    public <R> GenericInterface<R> getGInterface(R r){
        return new CClass<R>();
    }

    public interface GenericInterface<T>{
        void doSomething(T t);
    }

    public class AClass implements GenericInterface<String>{

        @Override
        public void doSomething(String s) {

        }
    }

    public class BClass implements GenericInterface{

        @Override
        public void doSomething(Object o) {

        }
    }

    public class CClass<P> implements GenericInterface<P>{

        @Override
        public void doSomething(P t) {

        }
    }

    public class DClass<P,Q> implements GenericInterface<P>{

        private Q mQ;

        public DClass(Q q) {
            mQ = q;
        }

        @Override
        public void doSomething(P p) {

        }

        public Q getSomething(){
            return mQ;
        }
    }
}
