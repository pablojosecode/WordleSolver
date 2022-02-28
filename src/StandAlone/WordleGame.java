package StandAlone;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.print.DocFlavor.URL;

public class WordleGame {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_BLACK = "\u001b[30m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\033[4;36m";
    
    public static final String ANSI_WHITE = "\u001b[37m";
    

    private InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
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
	
	
	public static void main(String[] args)
	{
		WordleGame jope = new WordleGame();
		
		System.out.println(ANSI_YELLOW
                + "This text is yellow"
                + ANSI_RESET);
		System.out.println("BEGIN PLAYING");
		
		String  answer = jope.getAnAnswer();
		if (answer.equals(""))
		{
			answer = "pilot";
		}
		String currentGuess;
		StringBuilder output;
		int num = 1;
		
		
		
		System.out.println("answer is *****");// + answer);
		boolean notSolved = true;
		Scanner jo = new Scanner(System.in);
		
		while (notSolved)
		{
			System.out.println("Guess: ");
			
			
			currentGuess = jo.next().replaceAll("[^a-z]","");
			Path pathy = Path.of("wordle-allowed-guesses.txt");
			Path pathy2 = Path.of("wordle-answers-alphabetical.txt");


			try {
				while (!(new Scanner(new File(pathy.toString())).useDelimiter("\\Z").next().contains(currentGuess)||new Scanner(new File(pathy2.toString())).useDelimiter("\\Z").next().contains(currentGuess))) {
				     System.out.println("PICK A VALID WORD");
				     currentGuess = jo.next();

				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			while(currentGuess.length()!=5)
			{
				System.out.println("Make sure it's 5 letters long.");
				currentGuess = jo.next().replaceAll("[^a-z]","");
			}
			
			if (currentGuess.equals(answer))
			{
				notSolved = false;
				System.out.println("YOU WON! AWESOME, only took you " + num + " tries");
			}
			else
			{
				ArrayList<Character> copy = (ArrayList<Character>) convertStringToCharList(answer);
				output = new StringBuilder();
				String[][] guessQualitee = new String[6][2];
				
				LinkedHashMap<Character, String> guessQuality = new LinkedHashMap<>();

				for (int a = 0; a<currentGuess.length(); a++)
				{
					char now =currentGuess.charAt(a);
					if (a<copy.size() && now==copy.get(a))
					{
						guessQuality.put(now, ANSI_GREEN);
						guessQualitee[a][0]=Character.toString(now);
						guessQualitee[a][1]=ANSI_GREEN;
						copy.set(a,' ');
					}
					else if (copy.contains(now) && currentGuess.charAt(copy.indexOf(now))!=now)
					{
						guessQualitee[a][0]=Character.toString(now);
						guessQualitee[a][1]=ANSI_YELLOW;
						guessQuality.put(now, ANSI_YELLOW);
						copy.set(copy.indexOf(now),' ');
					}
					else
					{
						guessQualitee[a][0]=Character.toString(now);
						guessQualitee[a][1]=ANSI_WHITE;
						guessQuality.put(now, ANSI_WHITE);
					}
					
				}
				
				
				System.out.print("OUTPUT: ");
				for (int x = 0; x<5; x++)
				{
					System.out.print(guessQualitee[x][1] + guessQualitee[x][0] + ANSI_RESET);
				}
				System.out.println("\n");
			}
			num++;
			
		}
		jo.close();
		ASCIIArt artGen = new ASCIIArt();
        
        System.out.println(ANSI_GREEN);
        try {
			artGen.printTextArt("Thanks", ASCIIArt.ART_SIZE_MEDIUM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println();
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
