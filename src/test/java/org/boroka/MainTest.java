package org.boroka;

import org.junit.Test;

public class MainTest {


    public void hello() {
        System.out.println("Hello");
    }

    @Test
    public void osszeg() throws Exception {
        Runnable r = this::hello;
        r.run();
    }
}