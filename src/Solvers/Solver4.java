package Solvers;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Solver4 {
	
	public HashMap<Integer, Character> spec = new HashMap<Integer, Character>();
    public ArrayList<Character> nowhere = new ArrayList<Character>();
    public ArrayList<Character> somewhere = new ArrayList<Character>();
    public HashMap<Integer, Character> notHere = new HashMap<>();
	public Problem problem = new Problem();
	public String answer;
	
	public Solver4()
	{
		
	}
	
	public Solver4(String jo)
	{
		problem = new Problem(jo);
	} 
	
	public int go() throws Exception
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
	    	double count = 0;
	    	String guess = "doodo";
	    	if (first)
	    	{
	    		guess = "salet";
	    		first = false;
	    	}
	    	else
	    	{
	    		double d = -1;
		    	for (String jo: wordsleft)
		    	{
		    		System.out.println("CURRENT LOOKY " + jo);

		    		count=0;
		    		for (String words: wordsleft)
		    		{
		    			count += this.fuller(jo, words, wordsleft); //get the average length minimized by jo for all remaining stuffs.
		    		}
		    		
		    		if (count>d)
		    		{
		    			d = count;
		    			guess = jo;
		    		}
		    	}
		    	if (d==-1)
		    	{
		    		System.out.println("HEEYA " + count);
		    	}
	    	}
	    	System.out.println("Guess is  " + guess + "\n");
		    String response = this.guess(guess);
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
		    
		    
		    
		    this.inputData(response, guess);
		    
		    wordsleft.removeIf(x -> this.nonconform(x));

		    this.empty();
			wordsleft.remove(guess);
	    }
	    return-1;
	}
	
	public int go2(String answer, ArrayList<String> left) throws Exception
	{
		

	    ArrayList<String> wordsleft = (ArrayList<String>)left.clone();
	    
	    
	    boolean notDone = true;
	    int totalCount = 0;
	    while (notDone)
	    {
	    	totalCount++;
	    	if (wordsleft.size()==0)
	    	{	
	    		return -1;
	    	}
	    	if (wordsleft.size()==1)
	    	{
	    		
	    		String ans = wordsleft.get(0);
	    		return totalCount;
	    	}
	    	double count = 0;
	    	String guess = "doodo";
	    	
	    		double d = -1;
		    	for (String jo: wordsleft)
		    	{
		    		count=0;
		    		for (int x = 0; x<wordsleft.size(); x++)
				    {
				    	String words = wordsleft.get(x);
				    	count += this.full(jo, words, wordsleft);
				    }
		    		
		    		
		    		if (count>d)
		    		{
		    			d = count;
		    			guess = jo;
		    		}
		    	}
		    	
	    	
		    String response = this.guess(guess);
		    
		    
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
		    
		    
		    
		    this.inputData(response, guess);
		    wordsleft.removeIf(x -> this.nonconform(x));
			wordsleft.remove(guess);
		    this.empty();
	    }
	    return-1;
	}

	
	public double fuller(String guess, String answer, ArrayList<String> words) throws Exception
	{
		ArrayList<String> words2 = narrow2(guess, answer, words);
		
		return 100-(double)go2(answer, words2);
	}
	
	public double full(String guess, String answer, ArrayList<String> allWords)
	{
		ArrayList<String> jo = narrow2(guess, answer, allWords);
		if (jo.size()==0)
		{
			return 2147483647;
		}
		double count = 0;
		for (String d: jo)
		{
			count+=(allWords.size())-narrow2(d, answer, jo).size();
		}
		
		return count/jo.size();
	}
	
	public ArrayList<String>  narrow2(String guess, String answer, ArrayList<String> allWords)
	{
		int ans = 0;
		String inquiry = inquireHere(guess, answer);
	    inputData(inquiry, guess);
	    ArrayList<String> clone = (ArrayList<String>) allWords.clone();
	    clone.removeIf(x -> nonconform(x));
	    this.empty();
	    return clone;
	}
	
	public String inquireHere(String guess, String answer)
	{
			StringBuilder output = new StringBuilder();
			ArrayList<Character> copy = (ArrayList<Character>) problem.convertStringToCharList(answer);

			
			LinkedHashMap<Character, String> guessQuality = new LinkedHashMap<>();

			for (int a = 0; a<5; a++)
			{
				char now =guess.charAt(a);
				if (a<copy.size() && now==copy.get(a))
				{
					output.append('g');
					copy.set(a,' ');
				}
				else if (copy.contains(now) && guess.charAt(copy.indexOf(now))!=now)
				{
					
					output.append('y');
					copy.set(copy.indexOf(now),' ');
				}
				else
				{
					output.append('w');
				}
				
			}
			return output.toString();
			
		
	}

	
	public int narrow(String guess, String answer, ArrayList<String> allWords)
	{
		
		String inquiry = inquireHere(guess, answer);
	    inputData(inquiry, guess);
	    ArrayList<String> clone = (ArrayList<String>) allWords.clone();
	    clone.removeIf(x -> nonconform(x));
	    
		this.empty();
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
		if (guess.length()==0)
		{
			System.out.println("ZERO ERROR");
		}
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
			if (x.indexOf(dooo)!=-1 && !spec.containsValue(dooo))
			{
				return true;
			}
		}
		return false;
	}
	public boolean nonconform2(String x)
	{
		for (Integer jo: notHere.keySet())
		{
			if (x.charAt(jo)==(notHere.get(jo)))
			{
				if (x.equals(answer))
				{
					System.out.println("HOOOP " + x);
					printData();
				}
				return true;
			}
		}
		for (Integer jo: spec.keySet())
		{
			if (x.charAt(jo)!=(spec.get(jo)))
			{
				if (x.equals(answer))
				{
					System.out.println("HOOOP " + x);
					printData();
				}
				return true;
			}
		} 
		for (Character somewhereChar: somewhere)
		{
			if (x.indexOf(somewhereChar)==-1)
			{
				if (x.equals(answer))
				{
					System.out.println("HOOOP " + x);
					printData();
				}
				return true;
			}
		}
		for (Character dooo: nowhere)
		{
			if (x.indexOf(dooo)!=-1 && !spec.containsValue(dooo))
			{
				if (x.equals(answer))
				{
					System.out.println("HOOOP " + x);
					printData();
				}
				return true;
			}
		}
		return false;
	}
	public void printData()
	{
		for (Character s: nowhere)
	    {
	    	System.out.println("no "+ s);
	    }
	    for (Character s: somewhere)
	    {
	    	System.out.println("so "+ s);
	    }
	    for (int s: spec.keySet())
	    {
	    	System.out.println("sp "+ spec.get(s));
	    }
	    for (int s: notHere.keySet())
	    {
	    	System.out.println("nh "+ notHere.get(s));
	    }
		
	}
	public void empty()
	{
		this.spec.clear();
	    this.nowhere.clear();
	    this.somewhere.clear();
	    this.notHere.clear();
	}
}