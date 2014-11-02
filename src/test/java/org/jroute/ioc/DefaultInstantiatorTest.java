package org.jroute.ioc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

public class DefaultInstantiatorTest {


    public static class A {

    }

    public static class B {

    }

    public static class C {

        private final A a;
        private final B b;

        public C(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A getA() {
            return a;
        }

        public B getB() {
            return b;
        }
    }


    @Test
    public void shouldCreateClassWithTheGivenConstructorParameters() throws Exception {
        // given
        Map<Class<?>, Object> instances = new HashMap<>();
        A a = new A();
        instances.put(A.class, a);
        B b = new B();
        instances.put(B.class, b);

        // when
        C c = new DefaultInstantiator(instances).newInstance(C.class);

        //then
        assertThat(c.getA(), sameInstance(a));
        assertThat(c.getB(), sameInstance(b));
    }
}