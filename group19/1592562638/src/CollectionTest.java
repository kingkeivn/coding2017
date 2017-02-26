/*范例名称：
 * 原文件名称：
 * 要点：
 * 1. 实现基本的数据结构类：ArrayList、LinkedList、Queue、Stack、Tree

 */
public class CollectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//测试ArrayList
		ArrayList_self<Name> arrayList1=new ArrayList_self<Name>();
		for(int i=0;i<13;i++){
			arrayList1.add(new Name("An"+i, "Chang"));
			if(i>6){
				arrayList1.set(i, new Name("Cheng"+i, "Chang"));
			}
			System.out.println(arrayList1.get(i));
		}
	}

}
