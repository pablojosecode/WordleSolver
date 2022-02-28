import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Problem {
	
	public String answer;
	
	public Problem()
	{
		this.answer = getAnAnswer();
	}
	
	public Problem(String answer)
	{
		this.answer = answer;
	}
	public Problem (int index)
	{
		this.getAnAnswer(index);
	}
	
	public String getAnAnswer(int index)
	{
		String randomWord="";
		try{
			
			File file = new File("../WordleSolver/wordle-answers-alphabetical.txt");
			InputStream stream= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-answers-alphabetical.txt");
			
			
			//File directory = new File("./");
			// System.out.println(directory.getAbsolutePath());
			
		    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		    String line = reader.readLine();
		    List<String> words = new ArrayList<String>();
		    while(line != null) {
		        String[] wordsLine = line.split(" ");
		        for(String word : wordsLine) {
		            words.add(word);
		        }
		        line = reader.readLine();
		    }

		    Random rand = new Random(System.currentTimeMillis());
		     randomWord = words.get(index);
		     System.out.println("THIS SIZE IS " + words.size());
		} catch (Exception e) {
			System.out.println("ERROR");
		    // Handle this
		}
		return randomWord;
		
	}
	
	public String getAnAnswer()
	{
		String randomWord="";
		try{
			
			File file = new File("../WordleSolver/wordle-answers-alphabetical.txt");
			InputStream stream= Thread.currentThread().getContextClassLoader().getResourceAsStream("wordle-answers-alphabetical.txt");
			
			
			//File directory = new File("./");
			// System.out.println(directory.getAbsolutePath());
			
		    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		    String line = reader.readLine();
		    List<String> words = new ArrayList<String>();
		    while(line != null) {
		        String[] wordsLine = line.split(" ");
		        for(String word : wordsLine) {
		            words.add(word);
		        }
		        line = reader.readLine();
		    }

		    Random rand = new Random(System.currentTimeMillis());
		     randomWord = words.get(rand.nextInt(words.size()));
		} catch (Exception e) {
			System.out.println("ERROR");
		    // Handle this
		}
		return randomWord;
		
	}
	public String getAnswer()
	{
		return answer;
	}
	
	public String inquire(String guess)
	{
			StringBuilder output = new StringBuilder();
			
			
		
			ArrayList<Character> copy = (ArrayList<Character>) convertStringToCharList(answer);

			
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
	
	public static List<Character>
    convertStringToCharList(String str)
    {
  
      // Create an empty List of character
      List<Character> chars = str
  
      // Convert to String to IntStream
      .chars()
  
      // Convert IntStream to Stream<Character>
      .mapToObj(e -> (char)e)
  
      // Collect the elements as a List Of Characters
      .collect(Collectors.toList());
  
      // return the List
      return chars;
    }

}
