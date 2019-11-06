import lombok.Data;

/**
 * @author hzhang1
 * @date 2019-11-06
 * @description 自定义链表
 * @Version 1.0
 */
@Data
public class LinkedNodeList<T> {

  /**
   * 链表长度
   */
  private int size;

  /**
   * 链表头节点
   */
  transient Node<T> first;

  /**
   * 链表尾节点
   */
  transient Node<T> last;

  /**
   * 初始化链表
   */
  LinkedNodeList() {
    size = 0;
    last = new Node(null, null,  null);
    first = last;
  }

  /**
   * 添加节点
   *
   * @param item 节点值
   */
  public boolean add(T item) {

    Node<T> newNode = new Node<T>(last, null, item);
    if (size == 0) {
      first = newNode;
    }

    last.next = newNode;
    last = newNode;

    size++;
    return Boolean.TRUE;
  }

  /**
   * 打印链表
   */
  public void print() {
    for (Node<T> node = getFirst(); node != null; node = node.next) {
      System.out.print(node.item + " ");
    }
    System.out.println();
  }

  /**
   * 反转链表
   */
  public void reserve() {

    Node<T> head = getFirst();
    if (head == null || head.next == null) {
      return;
    }

    Node<T> currentNode = null, nextNode = null, tempNode = null;
    for (currentNode = head; currentNode != null && currentNode.next != null; currentNode = currentNode.next) {
      nextNode = currentNode.next;
      if (currentNode == head) {
        currentNode.next = nextNode.next;
        nextNode.next = currentNode;
        head = nextNode;
        tempNode = currentNode;
      } else {
        tempNode.next = nextNode;
        currentNode.next = nextNode.next;
        nextNode.next = currentNode;
        tempNode = currentNode;
      }
    }
    setFirst(head);
  }

  /**
   * 链表节点
   *
   * @param <T> 节点元素
   */
  private static class Node<T> {

    /**
     * 上一个节点
     */
    Node<T> prev;

    /**
     * 下一个节点
     */
    Node<T> next;

    /**
     * 节点元素
     */
    T item;

    public Node(Node<T> prev, Node<T> next, T item) {
      super();
      this.prev = prev;
      this.next = next;
      this.item = item;
    }
  }
}
