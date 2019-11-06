/**
 * @author hzhang1
 * @date 2019-11-06
 * @description 链表反转
 * @Version 1.0
 */
public class TestLinkedList {

  private final static int num = 6;

  public static void main(String[] args) {

    LinkedNodeList linkedList = new LinkedNodeList();
    for (int i = 1; i <= num; i++) {
      linkedList.add(i);
    }

    System.out.println("Before:");
    linkedList.print();

    linkedList.reserve();

    System.out.println("After:");
    linkedList.print();

  }


}
