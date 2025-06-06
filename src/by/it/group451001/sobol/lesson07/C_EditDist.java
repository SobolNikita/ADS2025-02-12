package by.it.group451001.sobol.lesson07;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Задача на программирование: расстояние Левенштейна
    https://ru.wikipedia.org/wiki/Расстояние_Левенштейна
    http://planetcalc.ru/1721/

Дано:
    Две данных непустые строки длины не более 100, содержащие строчные буквы латинского алфавита.

Необходимо:
    Решить задачу МЕТОДАМИ ДИНАМИЧЕСКОГО ПРОГРАММИРОВАНИЯ
    Итерационно вычислить алгоритм преобразования двух данных непустых строк
    Вывести через запятую редакционное предписание в формате:
     операция("+" вставка, "-" удаление, "~" замена, "#" копирование)
     символ замены или вставки

    Sample Input 1:
    ab
    ab
    Sample Output 1:
    #,#,

    Sample Input 2:
    short
    ports
    Sample Output 2:
    -s,~p,#,#,#,+s,

    Sample Input 3:
    distance
    editing
    Sample Output 2:
    +e,#,#,-s,#,~i,#,-c,~g,


    P.S. В литературе обычно действия редакционных предписаний обозначаются так:
    - D (англ. delete) — удалить,
    + I (англ. insert) — вставить,
    ~ R (replace) — заменить,
    # M (match) — совпадение.
*/


public class C_EditDist {

    String getDistanceEdinting(String one, String two) {
        int n = one.length();
        int m = two.length();
        int[][] dp = new int[n+1][m+1];
        // op[i][j] = операция, приведшая к dp[i][j]
        char[][] op = new char[n+1][m+1];
        // arg[i][j] = символ для вставки или замены
        char[][] arg = new char[n+1][m+1];

        // инициализация границ
        for (int i = 1; i <= n; i++) {
            dp[i][0] = i;
            op[i][0] = 'D';
            arg[i][0] = one.charAt(i-1);
        }
        for (int j = 1; j <= m; j++) {
            dp[0][j] = j;
            op[0][j] = 'I';
            arg[0][j] = two.charAt(j-1);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // удалить
                int best = dp[i-1][j] + 1;
                char bestOp = 'D';
                char bestArg = one.charAt(i-1);
                // вставить
                if (dp[i][j-1] + 1 < best) {
                    best = dp[i][j-1] + 1;
                    bestOp = 'I';
                    bestArg = two.charAt(j-1);
                }
                // замена или совпадение
                int cost = (one.charAt(i-1) == two.charAt(j-1)) ? 0 : 1;
                if (dp[i-1][j-1] + cost < best) {
                    best = dp[i-1][j-1] + cost;
                    bestOp = (cost == 0 ? 'M' : 'R');
                    bestArg = two.charAt(j-1);
                }
                dp[i][j] = best;
                op[i][j] = bestOp;
                arg[i][j] = bestArg;
            }
        }

        StringBuilder sb = new StringBuilder(new String());
        int i = n, j = m;
        while (i > 0 || j > 0) {
            switch (op[i][j]) {
                case 'D': // delete
                    sb.append(",").append(arg[i][j]).append("-");
                    i--;
                    break;
                case 'I': // insert
                    sb.append(",").append(arg[i][j]).append("+");
                    j--;
                    break;
                case 'R': // replace
                    sb.append(",").append(arg[i][j]).append("~");
                    i--; j--;
                    break;
                case 'M': // match
                    sb.append(",#");
                    i--; j--;
                    break;
            }
        }
        sb = sb.reverse();
        return sb.toString();
    }


    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_EditDist.class.getResourceAsStream("dataABC.txt");
        C_EditDist instance = new C_EditDist();
        Scanner scanner = new Scanner(stream);
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
        System.out.println(instance.getDistanceEdinting(scanner.nextLine(),scanner.nextLine()));
    }

}