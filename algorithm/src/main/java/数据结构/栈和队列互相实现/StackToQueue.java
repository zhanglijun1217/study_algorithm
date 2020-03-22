package 数据结构.栈和队列互相实现;

import java.util.Stack;
import java.util.stream.Stream;

/**
 * Created by zlj on 2020/3/18.
 * 两个栈 实现 队列
 *
 * 思路：
 * 1.入栈：直接压栈进入stackOne
 * 2.出栈：
 *      （1）判断stackOne是否为空，如果不为空，则将stackOne中的数据导入stackTwo。取出stackTwo弹栈的元素
 *      （2）判断stackTwo是否为空，如果不为空，重复（1）操作
 *
 */
public class StackToQueue<T> {

    Stack<T> stackOne = new Stack<>();
    Stack<T> stackTwo = new Stack<>();

    /**
     * 入队
     * @param data
     */
    private void push(T data) {
        stackOne.push(data);
    }

    /**
     * 出队列
     * @return
     */
    private T pop() {
        if (stackOne.empty() && stackTwo.empty())
            return null; // 容错 栈的pop方法如果没有元素了会报错
        // 其实加不加这个判断相当于Queue接口中poll（做了容错） 和 remove(没做容错) 两个方法的区别

        while (!stackOne.isEmpty()) {
            // 弹栈到stackTwo
            stackTwo.push(stackOne.pop());
        }

        // 这时弹栈 stackTwo中的元素
        T stackTwoFirstEmt = stackTwo.pop();

        // 如果出队列的时候元素都在stackTwo
        while(!stackTwo.isEmpty()) {
            // 数据倒回stackOne 以便下一次pop的时候 再利用栈的特性 实现队列出队的顺序
            stackOne.push(stackTwo.pop());
        }

        return stackTwoFirstEmt;
    }

    public static void main(String[] args) {
        StackToQueue<Integer> stackToQueue = new StackToQueue<>();

        // 队列入队
        Stream.iterate(1, i -> i+1).limit(10).forEach(stackToQueue::push);

        // 队列出队
        for(int i = 0; i< 10000; i++) {
            Integer pop = stackToQueue.pop();
            if (pop == null) break;
            System.out.println("队列出队：" + pop);
            try {
                Thread.sleep(200);
            } catch (Exception e) {

            }

        }
    }

}
