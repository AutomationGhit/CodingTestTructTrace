
package base;

public class Test {
	


		
		
		public void print(Object o)
		{
			System.out.println("Hello");
		}
		
		public void print(String s)
		{
			System.out.println("String");
		}
		
public static void main(String[] args) {
			
			String str = new String("5");
			
			System.out.println(10+20+str);
			
			Test t = new Test();
			t.print(null);
		}
		
	}



