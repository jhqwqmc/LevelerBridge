package cn.gtemc.levelerbridge.api.util;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Throwable;
}
