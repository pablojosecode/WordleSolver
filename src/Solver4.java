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

	/*public static void main(String[] args) throws Exception
	{
		int[] answer = new int[12];
		int count = 0;
		int tot = 0;
		int smalls = 0;
		while (count<250)
		{
			System.out.println(count);
			int d = go();
			if (d!=-1)
			{
				if (d>11)
				{
					answer[11]++;
				}
				answer[d-1]++;
				tot+=d;
				count++;
			}
			else
			{
				smalls++;
			}
		}
		
		for (int x = 0; x<12; x++)
		{
			System.out.println(x + ": " + answer[x]);
		}
	}*/
	public static void main(String[] args) throws Exception
	{
		int count = 0;
		int tot = 0;
		int smalls = 0;
		int[] put = new int[15];
		double total = 0;
		double runs = 1000;
		Solver4 solver = new Solver4();
		LinkedHashMap<String, Integer> corpus = new LinkedHashMap<>();
	    
		for (int x = 200; x<200+runs; x++)
		{
			solver.problem.answer = solver.problem.getAnAnswer(x);
		    solver.answer = solver.problem.getAnswer();
		    System.out.println("REEREE ANSWER IS " + solver.answer);
			System.out.println(runs-x);
			int d = solver.go();
			corpus.put(solver.answer, d);
			total +=d;
			put[d]++;
		}
		for (int x = 1; x<put.length; x++)
		{
			System.out.println(x + " " + put[x]);
		}
		for (String word: corpus.keySet())
		{
			System.out.println(word + ": " + corpus.get(word));
		}
		System.out.println("Average of " + (double)total/runs + " guesses.");
	}
	
	public int go() throws Exception
	{
		String answer = "";
		
		boolean first = true;
		
		
		//File directory = new File("./");
		// System.out.println(directory.getAbsolutePath());

	    ArrayList<String> wordsleft = new ArrayList<>();
	    InputStream stream= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-answers-alphabetical.txt");

	    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

	    String line = reader.readLine();
	    while (line != null) { 
	    	wordsleft.add(line); 
	    	line = reader.readLine(); 
	    }
	    
	   /* InputStream stream2= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-allowed-guesses.txt");

	    BufferedReader reader2 = new BufferedReader(new InputStreamReader(stream2));

	    String line2 = reader2.readLine();
	    while (line2 != null) { 
	    	wordsleft.add(line2); 
	    	line2 = reader2.readLine(); 
	    }
	    */
	    
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
	    		//System.out.println("ANSWER IS " + ans);
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
	    		/*for (String possible: wordsleft)
		    	{
		    		double min = 100000000;
		    		double now = solver.getLeft(possible, wordsleft);
		    		if (min>now)
		    		{
		    			min = now;
		    			guess = possible;
		    		}
		    	}*/
	    	}
	    	System.out.println("Guess is  " + guess + "\n");
	    	/*
	    	System.out.println("Best Guess " + guess);
		    System.out.println("Answer " + this.problem.getAnswer());*/
		    
		    String response = this.guess(guess);
		   // System.out.println("Reponse " + response);
		    
		   //
		    
		    
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
		    
		    
		    
		    this.inputData(response, guess);
		    //System.out.println("Size: " + wordsleft.size());
		    /*if (wordsleft.size()<10)
		    {
		    	for (String word: wordsleft)
		    	{
		    		System.out.println(word);
		    	}
		    }*/
		    
		    wordsleft.removeIf(x -> this.nonconform(x));

		    //System.out.println("Size: " + wordsleft.size());
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
		    this.empty();
			wordsleft.remove(guess);

		    

	    }
	    return-1;
	    
	   
	   // System.out.println("HOHohoHOHOO\n\\n\\n\n\\n\n");
	   // wordsleft.removeIf(x -> solver.nonconform(x));
	    /*for (String jo: wordsleft) {
	    	System.out.println(jo);
	    }*/

	    
	}
	
	public int go2(String answer, ArrayList<String> left) throws Exception
	{
		
		boolean first = true;
		
		
		//File directory = new File("./");
		// System.out.println(directory.getAbsolutePath());

	    ArrayList<String> wordsleft = (ArrayList<String>)left.clone();
	   /* InputStream stream2= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-allowed-guesses.txt");

	    BufferedReader reader2 = new BufferedReader(new InputStreamReader(stream2));

	    String line2 = reader2.readLine();
	    while (line2 != null) { 
	    	wordsleft.add(line2); 
	    	line2 = reader2.readLine(); 
	    }
	    */
	    
	    boolean notDone = true;
	    int totalCount = 0;
	    while (notDone)
	    {
	    	totalCount++;
	    	if (wordsleft.size()==0)
	    	{	
	    		//System.out.println("SMALL " + answer);
	    		return -1;
	    	}
	    	if (wordsleft.size()==1)
	    	{
	    		
	    		String ans = wordsleft.get(0);
	    		//System.out.println("ANSWER IS " + ans);
	    		return totalCount;
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
		    		count=0;
		    		for (int x = 0; x<wordsleft.size(); x++)
				    {
				    	String words = wordsleft.get(x);
				    	count += this.full(jo, words, wordsleft); //get the average length minimized by jo for all remaining stuffs.
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
	    		/*for (String possible: wordsleft)
		    	{
		    		double min = 100000000;
		    		double now = solver.getLeft(possible, wordsleft);
		    		if (min>now)
		    		{
		    			min = now;
		    			guess = possible;
		    		}
		    	}*/
	    	}
	    	/*System.out.println("Guess " + guess);
	    	
	    	System.out.println("Best Guess " + guess);
		    System.out.println("Answer " + this.problem.getAnswer());*/
		    
		    String response = this.guess(guess);
		   // System.out.println("Reponse " + response);
		    
		   //
		    
		    
		    
		    if (response.equals("ggggg"))
		    {
		    	return totalCount;
		    }
		    
		    
		    
		    this.inputData(response, guess);
		    //System.out.println("Size: " + wordsleft.size());
		    /*if (wordsleft.size()<10)
		    {
		    	for (String word: wordsleft)
		    	{
		    		System.out.println(word);
		    	}
		    }*/
		    
		    wordsleft.removeIf(x -> this.nonconform(x));

		    //System.out.println("Size: " + wordsleft.size());
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
		    this.empty();
			wordsleft.remove(guess);

		    

	    }
	    return-1;
	    
	   
	   // System.out.println("HOHohoHOHOO\n\\n\\n\n\\n\n");
	   // wordsleft.removeIf(x -> solver.nonconform(x));
	    /*for (String jo: wordsleft) {
	    	System.out.println(jo);
	    }*/

	    
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
		
		Problem temp = new Problem(answer);
		String inquiry = temp.inquire(guess);
	    inputData(inquiry, guess);
	    ArrayList<String> clone = (ArrayList<String>) allWords.clone();
	    clone.removeIf(x -> nonconform(x));
	    this.empty();
	    return clone;
	}

	
	public int narrow(String guess, String answer, ArrayList<String> allWords)
	{
		int ans = 0;
		
		Problem temp = new Problem(answer);
		String inquiry = temp.inquire(guess);
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
		
		 /* public HashMap<Integer, Character> spec = new HashMap<Integer, Character>();
    public ArrayList<Character> nowhere = new ArrayList<Character>();
    public ArrayList<Character> somewhere = new ArrayList<Character>();
    public HashMap<Integer, Character> notHere = new HashMap<>();*/
		 
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