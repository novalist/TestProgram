import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hzhang1
 * @date 2019-11-06
 * @description 顺序线程打印
 * @Version 1.0
 */
public class TestSequenceThread2 {

  /**
   * 线程数量
   */
  private final static int threadNums = 3;

  /**
   * 阈值
   */
  private final static int threshold = 100;

  /**
   * 输出数
   */
  private AtomicInteger count = new AtomicInteger(0);

  /**
   * 内存锁
   */
  private Lock lock = new ReentrantLock();
  Condition condition = lock.newCondition();

  /**
   * 顺序打印
   *
   * @param i 线程序号
   */
  public void print(int i) {
    try {
      lock.lock();
      if (count.get() % threadNums == i) {
        System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
        condition.signalAll();
      } else {
        condition.await();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  /**
   * 创建线程
   */
  public void createdThread() {
    for (int i = 0; i < threadNums; i++) {
      new Thread(new MyRunnable(i)).start();
    }
  }

  public static void main(String[] args) {
    TestSequenceThread2 testSequenceThread2 = new TestSequenceThread2();
    testSequenceThread2.createdThread();
  }

  class MyRunnable implements Runnable {

    private int i;

    MyRunnable(int i) {
      this.i = i;
    }

    public void run() {
      while (count.get() < threshold) {
        print(i);
      }
    }
  }
}