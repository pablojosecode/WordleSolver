import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver2 {
	
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
		int[] put = new int[15];
		double total = 0;
		double runs = 10;
		
		for (int x = 0; x<runs; x++)
		{
			System.out.println(runs-x);
			int d = go();
			total +=d;
			put[d]++;
		}
		for (int x = 1; x<put.length; x++)
		{
			System.out.println(x + " " + put[x]);
		}
		System.out.println("Average of " + (double)total/runs + " guesses.");
	}
	
	public static int go() throws Exception
	{
		String answer = "";
		
		boolean first = true;
		
		

	    ArrayList<String> wordsleft = new ArrayList<>();
	    InputStream stream= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-answers-alphabetical.txt");

	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

	    String line = reader.readLine();
	    while (line != null) { 
	    	wordsleft.add(line); 
	    	line = reader.readLine(); 
	    }
	    
	    Solver2 solver = new Solver2();
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
	    	if (wordsleft.size()==0)
	    	{
	    		String ans = wordsleft.get(0);
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
	    		int d = -1;
		    	for (String jo: wordsleft)
		    	{
		    		int count = 0;
		    		for (String words: wordsleft)
		    		{
		    			count += solver.narrow(jo, words, wordsleft); //get the average length minimized by jo for all remaining stuffs.
		    		}
		    		
		    		if (count>d)
		    		{
		    			d = count;
		    			guess = jo;
		    		}
		    	}
	    	}
	    	
	    	System.out.println("Best Guess " + guess);
		    System.out.println("Answer " + solver.problem.getAnswer());
		    
		    System.out.println("Size: " + wordsleft.size());
		    String response = solver.guess(guess);
		    System.out.println("Reponse " + response);
		    
		   //
		    
		    
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
		    
		    
		    
		    solver.inputData(response, guess);
		    
		    wordsleft.removeIf(x -> solver.nonconform(x));
		  
		    solver.empty();
			wordsleft.remove(guess);

		    

	    }
	    return-1;
	}
	
	public int narrow(String guess, String answer, ArrayList<String> allWords)
	{
		int ans = 0;
		
		Problem temp = new Problem(answer);
		String inquiry = temp.inquire(guess);
	    inputData(inquiry, guess);
	    ArrayList<String> clone = (ArrayList<String>) allWords.clone();
	    clone.removeIf(x -> nonconform(x));
	    
		empty();
		return allWords.size()-clone.size();
	}
	
	
	
	public int getDiff()
	{
		return 0;
		
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
			if (x.indexOf(dooo)!=-1 && !spec.containsValue(dooo))
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
