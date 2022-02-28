package Solvers;

import java.util.LinkedHashMap;

public class Driver {

	public static void main(String[] args) throws Exception
	{
		
		int count = 0;
		int tot = 0;
		int smalls = 0;
		int[] put = new int[15];
		double total = 0;
		double runs = 5;
		Solver solver = new Solver();
		LinkedHashMap<String, Integer> corpus = new LinkedHashMap<>();
	    
		for (int x = 0; x<runs; x++)
		{
			solver.problem.answer = solver.problem.getAnAnswer(x);
		    solver.answer = solver.problem.getAnswer();
		    System.out.println("REEREE ANSWER IS " + solver.answer);
			System.out.println(x);
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
	
}
