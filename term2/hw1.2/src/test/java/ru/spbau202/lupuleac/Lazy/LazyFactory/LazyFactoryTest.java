package ru.spbau202.lupuleac.Lazy.LazyFactory;

import org.junit.Before;
import org.junit.Test;
import ru.spbau202.lupuleac.Lazy.Lazy;

import static org.junit.Assert.*;

public class LazyFactoryTest {
    private volatile int cnt;
    @Before
    public void init() {
        cnt = 0;
    }

    @Test
    public void singleThreadLazy() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSingleThreadedLazy(() -> 10);
        assertEquals(10, (int)lazy.get());
    }

    @Test
    public void singleThreadLazyCallOnce() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSingleThreadedLazy(() -> {
            cnt++;
            return 0;
        });
        assertEquals(0, (int)lazy.get());
        assertEquals(1, cnt);
        assertEquals(0, (int)lazy.get());
        assertEquals(1, cnt);
    }

    @Test
    public void createMultiThreadedLazy() throws Exception {
        Lazy<Integer> lazy =  LazyFactory.createMultiThreadedLazy(() -> 5);
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                assertEquals(5, (int)lazy.get());
            }
        };
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i++){
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
        for(int i = 0; i < 10; i++){
            threads[i].join();
        }
    }

    @Test
    public void multiThreadLazyCallOnce() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createMultiThreadedLazy(() -> {
            cnt++;
            return 0;
        });
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                assertEquals(0, (int)lazy.get());
            }
        };
        Thread[] threads = new Thread[10];
        for(int i = 0; i < 10; i++){
            threads[i] = new Thread(runnable);
            threads[i].start();
        }
        for(int i = 0; i < 10; i++){
            threads[i].join();
        }
        assertEquals(1, cnt);
    }

}
