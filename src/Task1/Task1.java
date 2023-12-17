package Task1;


import java.util.Scanner;

public class Task1 {

    double A, B, eps, h;
    int count;
    double[] segments;


    Task1(double A, double B, double eps, int N) {
        this.A = A;
        this.B = B;
        this.eps = eps;
        this.h = (B - A) / N;
        this.segments = new double[N];
    }
    double f(double x) throws Exception {
        if(x < this.A || x > this.B) throw new Exception();
        return Math.pow(2,-x) - Math.sin(x);

    }

    double fDerivative(double x) throws Exception {
        if(x < this.A || x > this.B) throw new Exception();
        return -Math.log(2) * Math.pow(2, -x) - Math.cos(x);
    }

    double fSecondDerivative(double x) throws Exception {
        if(x < this.A || x > this.B) throw new Exception();
        return Math.pow(Math.log(2),2) * Math.pow(2, -x) + Math.sin(x);
    }

    /**
     Метод для отделения корней.
     Если функция меняет свой знак на концах отрезка, то мы добавляем этот отрезок на рассмотрение при дальнейшем решении
     так как там есть корень.
     */
    int rootSeparation() throws Exception {
        System.out.println("----------------------------------------------------------\n");
        System.out.println("ОТДЕЛЕНИЕ КОРНЕЙ");
        double x1 = this.A, x2 = x1 + this.h;
        double y1 = f(x1);
        int count = 0;
        System.out.println("Отрезки перемены знака функции: \n");
        while (x2 <= this.B){
            double y2 = f(x2);
            if(y1 * y2 <= 0){
                this.segments[count] = x1;
                System.out.println("[" + x1 + ", " + x2 + "]\n");
                count++;
            }
            x1 = x2;
            x2 = x1 + h;
            y1 = y2;
        }
        if(count == 0){
            System.out.println("При выбранном N функция не меняет знак");
        }
        System.out.println("Количество корней: " + count);
        System.out.println("----------------------------------------------------------\n");
        this.count = count;
        return count;
    }

    void rootClarify() throws Exception {
        double a, b;
        for(int i = 0; i < this.count; i++){
            a = segments[i];
            b = a + this.h;
            System.out.println("Рассмотрим отрезок: [" + a + ", " + b + "]");
            this.bisectionMethod(a, b);
            this.newtonMethod(a, b);
            this.modifiedNewtonMethod(a,b);
            this.secantMethod(a, b);
        }
    }


    /**
     Метод бисекций.
     Делим отрезок пополам, пока его длина не будет меньше 2*Epsilon.
     Как только мы достигли этого условия, берем X, который находится на середине получившегося отрезка.
     */
    void bisectionMethod(double a, double b) throws Exception {
        System.out.println("----------------------------------------------------------\n");
        System.out.println("МЕТОД БИСЕКЦИЙ\n");
        double center, res, d;
        int count = 0;
        while((b-a) > 2 * this.eps){
           center = (a+b)/2;
           if (f(a) * f(center) <= 0)
               b = center;
           else
               a = center;
           count++;
        }
        res = (a+b)/2;
        d = (b-a)/2;

        System.out.println("- Количество шагов: " + count);
        System.out.println("- Приближенное решение: x = " + res);
        System.out.println("- Длина последнего отрезка: " + d);
        System.out.println("- Модуль невязки: " + Math.abs(f(res) - 0));
        System.out.println("----------------------------------------------------------\n");
    }

    /**
     Реализация метода Ньютона.
     */
    void newtonMethod(double a, double b) throws Exception {
        int count = 0;
        double previous = (a+b)/2, current;

        System.out.println("----------------------------------------------------------\n");
        System.out.println("МЕТОД НЬЮТОНА\n");

        if(fDerivative(a) * fDerivative(b) < 0 || fSecondDerivative(a) * fSecondDerivative(a) < 0)
            System.out.println("Теорема о сходимости не работает, так как знак производной функции меняется на концах заданного отрезка\n");

        while (fSecondDerivative(previous) * f(previous) <= 0 || fDerivative(previous) == 0)
            previous = Math.random() * (b-a) + a;

        current = previous - f(previous) / fDerivative(previous);

        while (Math.abs(current - previous) >= this.eps) {
            previous = current;
            current = previous - f(previous) / fDerivative(previous);
            count++;
        }

        System.out.println("- Количество шагов: " + count);
        System.out.println("- Приближенное решение: x = " + current);
        System.out.println("- |x(m) - x(m-1)| = " + Math.abs(current-previous));
        System.out.println("- Модуль невязки: " + Math.abs(f(current) - 0));
        System.out.println("----------------------------------------------------------");

    }

    /**
     Реализация модифицированного метода Ньютона.
     В отличие от обычного метода Ньютона, здесь при достижении условия выхода из цикла, мы фиксируем производную при подсчете корня.
     */
    void modifiedNewtonMethod(double a, double b) throws Exception {
        int count = 0;
        double previous = (a+b)/2, current;
        System.out.println("----------------------------------------------------------\n");
        System.out.println("МОДИФИЦИРОВАННЫЙ МЕТОД НЬЮТОНА\n");

        if(fDerivative(a) * fDerivative(b) < 0 || fSecondDerivative(a) * fSecondDerivative(a) < 0)
            System.out.println("Теорема о сходимости не работает, так как знак производной функции меняется на концах заданного отрезка\n");

        while (fSecondDerivative(previous) * f(previous) <= 0 || fDerivative(previous) == 0)
            previous = Math.random() * (b-a) + a;

        double constDerivative = fDerivative(previous);

        current = previous - f(previous) / constDerivative;

        while (Math.abs(current - previous) >= this.eps) {
            previous = current;
            current = previous - f(previous) / constDerivative;
            count++;
        }
        System.out.println("- Количество шагов: " + count);
        System.out.println("- Приближенное решение: x = " + current);
        System.out.println("- |x(m) - x(m-1)| = " + Math.abs(current-previous));
        System.out.println("- Модуль невязки: " + Math.abs(f(current) - 0));
        System.out.println("----------------------------------------------------------");
    }

    /**
     Реализация метода секущих.
     Отличие от метода Ньютона в том, что мы заменяем производную при подсчете корня ее приближенным значением по формулам численного дифференцирования.
     */
    void secantMethod(double a, double b) throws Exception {
        int count = 0;
        double previous = (a+b)/2, current;

        System.out.println("----------------------------------------------------------\n");
        System.out.println("МЕТОД СЕКУЩИХ\n");

        if(fDerivative(a) * fDerivative(b) < 0 || fSecondDerivative(a) * fSecondDerivative(a) < 0)
            System.out.println("Теорема о сходимости не работает, так как знак производной функции меняется на концах заданного отрезка\n");

        while (fSecondDerivative(previous) * f(previous) <= 0 || fDerivative(previous) == 0)
            previous = Math.random() * (b-a) + a;

        current =  Math.random() * (b-a) + a;

        while (Math.abs(current - previous) >= this.eps) {
            double temp = current;
            current = current - f(current) * (current - previous) / (f(current) - f(previous));
            previous = temp;
            count++;
        }
        System.out.println("- Количество шагов: " + count);
        System.out.println("- Приближенное решение: x = " + current);
        System.out.println("- |x(m) - x(m-1)| = " + Math.abs(current-previous));
        System.out.println("- Модуль невязки: " + Math.abs(f(current) - 0));
        System.out.println("----------------------------------------------------------");
    }


    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        double A = -5;
        double B = 10;
        double eps = Math.pow(10, -6);
        int n;

        System.out.println("Задание 1. Численные методы решения нелинейных уравнений \n");
        System.out.println("Вариант 2");
        System.out.println("Функция: f(x) = 2^(-x) - sin(x)");
        System.out.println("Отрезок: [" + A + ", " + B + "]");
        System.out.println("e = 10^(-6)");
        System.out.println("----------------------------------------------------------");


        boolean t = true;
        while (t) {
            System.out.println("Ввести другие параметры? Да - 1, нет - 0");
            if (input.nextInt() == 1) {
                System.out.println("Введите следующие параметры задачи:");

                System.out.println("Введите нижнюю границу");
                System.out.print("A = ");
                A = input.nextDouble();

                System.out.println("Введите верхнюю границу");
                System.out.print("B = ");
                B = input.nextDouble();

                System.out.println("Введите погрешность e = 10^(-*)");
                System.out.print("* = ");
                eps = Math.pow(10, input.nextDouble());
            }

            System.out.println("Введите количество разбиений начального отрезка");
            System.out.println("n:");
            n = input.nextInt();

            while (n < 2) {
                System.out.println("Значение n должно быть не меньше 2, повторите ввод");
                System.out.println("n = ");
                n = input.nextInt();
            }

            Task1 nonlinearEquation = new Task1(A, B, eps, n);

            int rootNum = nonlinearEquation.rootSeparation();

            if(rootNum == 0)
                System.out.println("корней нет");
            else
                nonlinearEquation.rootClarify();


            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;

        }


    }


}