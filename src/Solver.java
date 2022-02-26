import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
	
	public HashMap<Integer, Character> spec = new HashMap<Integer, Character>();
    public ArrayList<Character> nowhere = new ArrayList<Character>();
    public ArrayList<Character> somewhere = new ArrayList<Character>();
    public HashMap<Integer, Character> notHere = new HashMap<>();
	public Problem problem = new Problem();

	public static void main(String[] args) throws Exception
	{
		int count = 0;
		int tot = 0;
		int smalls = 0;
		for (int x = 0; x<20000; x++)
		{
			int d = go();
			if (d!=-1)
			{
				tot+=d;
				count++;
			}
			else
			{
				smalls++;
			}
		}
		System.out.println("Average of " + (double)tot/count + " tries with " + smalls + " smalls");
	}
	public static int go() throws Exception
	{
		String answer = "";
		
		boolean first = true;
		InputStream stream= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-answers-alphabetical.txt");
		
		
		//File directory = new File("./");
		// System.out.println(directory.getAbsolutePath());
		
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

	    ArrayList<String> wordsleft = new ArrayList<>();
	    String line = reader.readLine();
	    while (line != null) { 
	    	wordsleft.add(line); 
	    	line = reader.readLine(); 
	    }
	    Solver solver = new Solver();
	    boolean notDone = true;
	    int totalCount = 0;
	    while (notDone)
	    {
	    	totalCount++;
	    	if (wordsleft.size()==0)
	    	{	
	    		System.out.println("SMALL");
	    		return -1;
	    	}
	    	
	    	String guess = "";
	    	if (first)
	    	{
	    		guess = "salet";
	    		first = false;
	    	}
	    	else
	    	{
		    	for (String possible: wordsleft)
		    	{
		    		double min = 100000000;
		    		double now = solver.getLeft(possible, wordsleft);
		    		if (min>now)
		    		{
		    			min = now;
		    			guess = possible;
		    		}
		    	}
	    	}
	    	
	    	
		    String response = solver.guess(guess);
		    
		   //
		    /* System.out.println("Guess " + guess);
		    System.out.println("Best Guess " + guess);
		    System.out.println("Answer " + solver.problem.getAnswer());
		    System.out.println("Reponse " + response);
		    System.out.println("Size: " + wordsleft.size());*/
		    
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
			wordsleft.remove(guess);
		    
		    
		    solver.inputData(response, guess);
		    
		    wordsleft.removeIf(x -> solver.nonconform(x));
		   /* for (Character s: solver.nowhere)
		    {
		    	System.out.println("no "+ s);
		    }
		    for (Character s: solver.somewhere)
		    {
		    	System.out.println("so "+ s);
		    }
		    for (int s: solver.spec.keySet())
		    {
		    	System.out.println("sp "+ solver.spec.get(s));
		    }
		    for (int s: solver.notHere.keySet())
		    {
		    	System.out.println("nh "+ solver.notHere.get(s));
		    }*/
		    solver.empty();
		    
		    

	    }
	    return-1;
	    
	   
	   // System.out.println("HOHohoHOHOO\n\\n\\n\n\\n\n");
	   // wordsleft.removeIf(x -> solver.nonconform(x));
	    /*for (String jo: wordsleft) {
	    	System.out.println(jo);
	    }*/

	}
	
	public double getLeft(String guess, ArrayList<String> info)
	{
		double count = 0;
		for (String jo: info)
		{
			if ((jo.indexOf(guess.charAt(0))!=-1)||(jo.indexOf(guess.charAt(1))!=-1)||(jo.indexOf(guess.charAt(2))!=-1)||(jo.indexOf(guess.charAt(3))!=-1)||(jo.indexOf(guess.charAt(4))!=-1))
			{
				count++;
			}
		}
		return count;
	}
	
	public void inputData(String response, String guess)
	{
		ArrayList<Character> put = new ArrayList<>();
	    for (int x = 0; x<response.length(); x++)
	    {
	    	if (response.charAt(x)=='w')
	    	{
	    		if (!nowhere.contains(guess.charAt(x)) && !put.contains(guess.charAt(x)))
	    		{
	    			nowhere.add(guess.charAt(x));
	    			put.add(guess.charAt(x));
	    		}
	    	}
	    	else if (response.charAt(x)=='g')
	    	{
	    		spec.put(x, guess.charAt(x));
	    		put.add(guess.charAt(x));
	    	}
	    	else if (!put.contains(guess.charAt(x)))
	    	{
	    		somewhere.add(guess.charAt(x));
	    		notHere.put(x, guess.charAt(x));
	    		
	    		put.add(guess.charAt(x));
	    	}
	    }
	}
	
	public String guess(String guess)
	{
		return problem.inquire(guess);
	}
	
	public boolean nonconform(String x)
	{
		
		 /* public HashMap<Integer, Character> spec = new HashMap<Integer, Character>();
    public ArrayList<Character> nowhere = new ArrayList<Character>();
    public ArrayList<Character> somewhere = new ArrayList<Character>();
    public HashMap<Integer, Character> notHere = new HashMap<>();*/
		 
		for (Integer jo: notHere.keySet())
		{
			if (x.charAt(jo)==(notHere.get(jo)))
			{
				return true;
			}
		}
		for (Integer jo: spec.keySet())
		{
			if (x.charAt(jo)!=(spec.get(jo)))
			{
				return true;
			}
		} 
		for (Character somewhereChar: somewhere)
		{
			if (x.indexOf(somewhereChar)==-1)
			{
				return true;
			}
		}
		for (Character dooo: nowhere)
		{
			if (x.indexOf(dooo)!=-1)
			{
				return true;
			}
		}
		return false;
	}
	public void empty()
	{
		spec = new HashMap<Integer, Character>();
	    nowhere = new ArrayList<Character>();
	    somewhere = new ArrayList<Character>();
	    notHere = new HashMap<>();
	}
}
