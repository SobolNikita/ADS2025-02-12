package by.it.group451001.drzhevetskiy.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m
 * время расчета должно быть не более 2 секунд
 */

import java.util.ArrayList;

public class FiboC {

    private long startTime = System.currentTimeMillis();

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        int n = 55555;
        int m = 1000;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    long fasterC(long n, int m) {
        //Интуитивно найти решение не всегда просто и
        //возможно потребуется дополнительный поиск информации
        long el1 = 0, el2 = 1;
        ArrayList<Long> l = new ArrayList<>();// остатки от деления
        l.add(0L);
        l.add(1L);
        boolean s = true;

        while (s) {
            long buf = el2;
            el2 += el1;
            el1 = buf;

            if (el1 % m == 0 && el2 % m == 1)
                s = false;
            else
                l.add(el2 % m);
        }

        n %= l.size() - 1;
        return l.get((int) n);
    }


}
