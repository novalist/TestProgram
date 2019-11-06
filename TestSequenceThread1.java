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
public class TestSequenceThread1 {

  /**
   * 线程数
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
   * 三个线程相互联动
   */
  private Lock lock = new ReentrantLock();
  Condition condition1 = lock.newCondition();
  Condition condition2 = lock.newCondition();
  Condition condition3 = lock.newCondition();


  public void print() {

    new Thread(new Runnable() {

      public void run() {
        while (count.get() < threshold) {
          try {
            lock.lock();
            while (count.get() % threadNums != 0) {
              condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            if (count.get() < threshold) {
              condition2.signal();
            } else {
              return;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }

      }
    }).start();

    new Thread(new Runnable() {

      public void run() {
        while (count.get() < threshold) {
          try {
            lock.lock();
            while (count.get() % threadNums != 1) {
              condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            if (count.get() < threshold) {
              condition3.signal();
            } else {
              return;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }

      }
    }).start();

    new Thread(new Runnable() {

      public void run() {
        while (count.get() < threshold) {
          try {
            lock.lock();
            while (count.get() % threadNums != 2) {
              condition3.await();
            }

            System.out.println(Thread.currentThread().getName() + ":" + count.getAndIncrement());
            if (count.get() < threshold) {
              condition1.signal();
            } else {
              return;
            }
          } catch (InterruptedException e) {
            e.printStackTrace();
          } finally {
            lock.unlock();
          }
        }

      }
    }).start();
  }

  public static void main(String[] args) {
    TestSequenceThread1 testSequenceThread1 = new TestSequenceThread1();
    testSequenceThread1.print();
  }
}