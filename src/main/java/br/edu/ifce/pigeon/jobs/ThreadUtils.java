package br.edu.ifce.pigeon.jobs;

public final class ThreadUtils {
    public static void cpuBoundWait(int ms) {
        boolean flag = true;

        long last = System.currentTimeMillis();
        while (flag) {
            if (System.currentTimeMillis() - last >= ms) {
                flag = false;
            }
        }
    }
}
