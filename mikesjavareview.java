public class mikesjavareview 
{
	public static void main(String [] args)
	{
		System.out.println("Hello There!");
		arrayMethod();
	}
	
	public static void arrayMethod()
	{
		int [] mike = new int[15];
		int i = 0;
		for(; i < 15; i++)
		{
		 mike[i] = i;
		 System.out.println(mike[i]);
		}
	}
	
}