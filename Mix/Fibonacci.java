import java.util.ArrayList;
import java.util.List;

public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(fib(1));
        System.out.println(fib(2));
        System.out.println(fib(3));
        System.out.println(fib(4));
        System.out.println(fib(5));
        System.out.println(fib(6));
        System.out.println(fib(7));
        System.out.println(fib(8));
        System.out.println(fib(9));

        System.out.println(fib2(1));
        System.out.println(fib2(2));
        System.out.println(fib2(3));
        System.out.println(fib2(4));
        System.out.println(fib2(5));
        System.out.println(fib2(6));
        System.out.println(fib2(7));
        System.out.println(fib2(8));
        System.out.println(fib2(9));
    }

    public static int fib(int n) {
        if(n<=2) return 1;
        List<Integer> numbers = new ArrayList<Integer>(n);
        numbers.add(0,1);
        numbers.add(1,1);
        for(int i = 2; i < n; i++) {
            numbers.add(i, numbers.get(i-1) + numbers.get(i-2));
        }
        return numbers.get(n-1);
    }
    public static int fib2(int n) {
        if(n<=2) {
            return 1;
        } else {
            return fib2(n-1) + fib2(n-2);
        }
    }
}
