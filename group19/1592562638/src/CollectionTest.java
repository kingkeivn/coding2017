/*�������ƣ�
 * ԭ�ļ����ƣ�
 * Ҫ�㣺
 * 1. ʵ�ֻ��������ݽṹ�ࣺArrayList��LinkedList��Queue��Stack��Tree

 */
public class CollectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//����ArrayList
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
