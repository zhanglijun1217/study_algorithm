package 数据结构.栈和队列互相实现;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * Created by zlj on 2020/3/19.
 * 两个队列实现栈
 * 思路： 维护两个队列 Q1 和 Q2
 * （1）入栈：即为队列Q1中加入元素
 * （2）出栈： 关键就是保持队列q1和q2一直有一个为空
 *
 *          - 首先元素入q1，队列中为 [tail]xn->xn-1...->x1 [head]  这时将q1中的n-1个元素入q2 q2中元素: [tail] xn-1->xn-2...->x1 [head]
 *          - xn从q1中出队
 *          - 这时q1为空，q2有n-1个元素，重复第一步，只不过现在是将q2中的n-2个元素出队放入q1中，再将q2中的xn-1出队即可。
 *          - 重复操作直到 q1和q2都为空为止
 */
public class TwoQueueToStack<T> {

    // 这里用的Deque 虽然只是需要的是队列 先进先出（FIFO）的特性 但其实Deque既提供了stack的操作、又提供了queue的操作，也提供了对first和end的操作（LinkedList里面叫做head和tail）
    Queue<T> queue1 = new ArrayDeque<>();
    Queue<T> queue2 = new ArrayDeque<>();

    /**
     * 栈的入栈操作
     * @param element
     */
    void push(T element) {
        queue1.offer(element); // 比add更友好
    }


    /**
     * 栈的出栈操作
     *
     * @return
     */
    T pop() {

        if (!queue1.isEmpty()) {
            while (queue1.size() > 1) {
                // q1出队 入队q2
                queue2.offer(queue1.poll());
            }
            // q1的size是1了
            return queue1.poll();
        }

        if (!queue2.isEmpty()) {
            while (queue2.size() > 1) {
                queue1.offer(queue2.poll());
            }
            // q2的size是1了
            return queue2.poll();
        }

        return null;
    }

    int size() {
        if (!queue1.isEmpty()) {
            return queue1.size();
        } else if (!queue2.isEmpty()) {
            return queue2.size();
        }
        return 0;
    }

    /**
     * 实现一个只查看栈顶元素的操作
     * 思路也是先将q1的n-1个元素入队q2，这时将q1中的剩余元素peek出来，不是poll出来（元素不能删除），再将其也导入到q2中
     * // 注意这里如果直接用的是双端队列Deque 其实直接可以在不为空的队列中 peekLast = =
     */
    @SuppressWarnings("all")
    T top() {
        T top = null;
        if (!queue1.isEmpty()) {
          while (queue1.size() > 1) {
              queue2.offer(queue1.poll());
          }
          top = queue1.peek();
          // 再将其入队至q2
            queue2.offer(queue1.poll());
        }

        if (!queue2.isEmpty()) {
            while (queue2.size()>1) {
                queue1.offer(queue2.poll());
            }

            top = queue2.peek();
            queue1.offer(queue2.poll());
        }

        return top;
    }




    public static void main(String[] args) {

        TwoQueueToStack<Integer> twoQueueToStack = new TwoQueueToStack<>();

        // 压栈
        Stream.iterate(1, i -> i+1).limit(10).forEach(twoQueueToStack::push);

        // 查看栈顶的元素
        System.out.println("栈顶元素：" + twoQueueToStack.top());

        // 出栈
        int size = twoQueueToStack.size();
        for (int i = 0; i <size; i++) { // 注意这里不能写成 i < twoQueueToStack.size() 因为循环中的pop操作会减少stack中的元素
            System.out.println("元素出栈" + twoQueueToStack.pop());
        }
    }
}
