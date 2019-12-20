import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;



public class WordPuzzle 
{
	private int row = 0, column = 0;
	private String [][] wordGrid;
	public static int wordCount = 0;
	public static Set<String> wordSet = new HashSet<>();
	
	public WordPuzzle(int x, int y) 
	{
		row = x;
		column = y;
		Random random = new Random();
		wordGrid = new String[row][column];
		for (int i = 0; i < row ; i++) {
			for (int j = 0 ; j < column ; j++)
			{
				wordGrid[i][j] =  Character.toString((char) (random.nextInt(26) + 97));
			}
		}
		
		System.out.println("Word Puzzle Created is :");		
		for (int i = 0; i < row ; i++) 
		{		
			StringBuffer s = new StringBuffer();
			for (int j = 0; j < column; j++) 
			{
				s.append(wordGrid[i][j] + " ");
			}
			System.out.println(s);
		}		
	}
	
	public void findWordsInTheGrid(MyHashTable<String> myHashTable, boolean enhancement)
	{		
		for (int i = 0 ; i < row ; i++ ) 
		{
			for (int j = 0; j < column ; j++) 
			{	
				StringBuffer horizontal = new StringBuffer();
				for (int k = j; k < column; k++) 
				{
					horizontal.append(wordGrid[i][k]);					
					if (!findWordsInTheGrid(horizontal, myHashTable, enhancement)) 
						break;
				}				
			}
		}
		
		for (int i = 0 ; i < row ; i++ ) 
		{
			for (int j = column -1 ; j >= 0; j--) 
			{	
				StringBuffer reverseHorizontal = new StringBuffer();
				for (int k = j ; k >=0 ; k--) 
				{
					reverseHorizontal.append(wordGrid[i][k]);					
					if (!findWordsInTheGrid(reverseHorizontal, myHashTable, enhancement)) 
						break;
				}				
			}
		}
		
		for (int i = 0; i < column ; i ++) 
		{
			for (int j = 0 ; j < row ; j++)
			{	
				StringBuffer vertical = new StringBuffer();
				for (int k = j ; k < row ; k++ ) 
				{
					vertical.append(wordGrid[k][i]);					
					if (!findWordsInTheGrid(vertical, myHashTable, enhancement)) 
						break;
				}
			}				
		}		

		
		for (int i = 0; i < column ; i++) 
		{
			for (int j = row -1 ; j >= 0; j--) 
			{
				StringBuffer reverseVertical = new StringBuffer();
				for (int k = j ; k >= 0 ; k--) 
				{
					reverseVertical.append(wordGrid[k][i]);					
					if (!findWordsInTheGrid(reverseVertical, myHashTable, enhancement)) 
						break;			
				}
			}
		}
		
		int r, ri;
		for(int i = 0 ; i < row; i++ )
		{			
			ri = i;
			for (int j = 0 ; j <= i ; j++) 
			{	
				StringBuffer rowUpDiagonal = new StringBuffer();
				r = ri - j;
				for (int k = j ; k < column && r >=0 ; k++) 
				{
					rowUpDiagonal.append(wordGrid[r][k]);
					r--;
					if(rowUpDiagonal.length()!=1) {
						if (!findWordsInTheGrid(rowUpDiagonal, myHashTable, enhancement))
						{
							break;
						}
					}
				}
			}
		}
		
		for (int i = 1; i < column; i++) 
		{			
			ri = row - 1;
			for (int j = 0 ; j < column-i; j++) 
			{	
				StringBuffer colUpDiaganol = new StringBuffer();
				r = ri - j;
				for (int k = i+j; k < column && r >=0; k++ ) 
				{
					colUpDiaganol.append(wordGrid[r][k]);
					r--;
					if(colUpDiaganol.length()!=1) {
						if (!findWordsInTheGrid(colUpDiaganol, myHashTable, enhancement))
						{
							break;
						}
					}
				}
			}
		}
		
		
		for(int i = 0 ; i < row; i++ ) 
		{
			StringBuffer rowDownDiagonal = new StringBuffer();
			ri = 0;
			for (int j = 0 ; j <= i ; j++) 
			{				
				r = ri + j;
				for (int k = i-j ; k >= 0 && r <row ; k--) {
					rowDownDiagonal.append(wordGrid[r][k]);
					r++;
					if (!findWordsInTheGrid(rowDownDiagonal, myHashTable, enhancement)) 
					{
						break;
					}
				}
			}				
		}
		
		
		for (int i = 1; i < column; i++) 
		{			
			ri = i;
			for (int j = 0 ; j < column-i; j++) 
			{		
				StringBuffer colDownDiagonal = new StringBuffer();
				r = ri + j;
				for (int k = column -1-j; k >0 && r < row; k-- )
				{
					colDownDiagonal.append(wordGrid[r][k]);
					r++;					
					if (!findWordsInTheGrid(colDownDiagonal, myHashTable, enhancement))
					{
						break;
					}
				}
			}
		}
		
		
		for(int i = 0 ; i < row; i++ ) 
		{			
			ri = row - 1 - i;
			for (int j = 0 ; j <= i ; j++) 
			{				
				r = ri + j;
				for (int k = j ; k < column && r < row ; k++) 
				{
					StringBuffer downRowDiagonal = new StringBuffer();
					downRowDiagonal.append(wordGrid[r][k]);
					r++;
					if (!findWordsInTheGrid(downRowDiagonal, myHashTable, enhancement))
					{
							break;
					}					
				}
			}
		}
		
		
		for (int i = 1; i < column; i++) 
		{
			ri = 0;
			for (int j = 0 ; j < column-i; j++) 
			{
				r = ri + j;
				for (int k = i+j; k < column && r < row; k++ ) 
				{		
					StringBuffer downColDiagonal = new StringBuffer();
					downColDiagonal.append(wordGrid[r][k]);
					r++;					
					if (!findWordsInTheGrid(downColDiagonal, myHashTable, enhancement))
					{
						break;						
					}
				}
			}
		}
		
		
		for(int i = 0 ; i < row; i++ ) 
		{
			ri = row - 1;
			for (int j = 0 ; j <= i ; j++) 
			{	StringBuffer upRowDiagonal = new StringBuffer();			
				r = ri - j;
				for (int k = i-j ; k >= 0 && r >= 0 ; k--) {
					upRowDiagonal.append(wordGrid[r][k]);
					r--;					
					if (!findWordsInTheGrid(upRowDiagonal, myHashTable, enhancement)) {
						break;
					}					
				}
			}
		}
		
		
		for (int i = 1; i < column; i++)
		{
			ri = row - 1 -i;
			for (int j = 0 ; j < column-i; j++) 
			{	StringBuffer upColDiagonal = new StringBuffer();			
				r = ri - j;
				for (int k = column -1-j; k >=0 && r >= 0 ; k-- ) 
				{
					upColDiagonal.append(wordGrid[r][k]);
					r--;					
					if (!findWordsInTheGrid(upColDiagonal, myHashTable, enhancement)) 
					{
							break;
					}					
				}
			}
		}
		System.out.println("Number of words found: " + wordCount);		
	}
	
	private boolean findWordsInTheGrid(StringBuffer s, MyHashTable<String> myHashTable, boolean enhancement)
	{	 
		if( myHashTable.containsWord(s.toString()))
		{	
			if(wordSet.add(s.toString()))
			{
				if(enhancement) {
					for(int i=1; i<s.toString().length(); i++) {
						System.out.println("Prefix: " + s.toString().substring(0,i));
					}
				}
				wordCount++;
				System.out.println("Word: " + s);
			}
		} 
		else if(enhancement && !myHashTable.contains(s.toString())) 
		{
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) 
	{
		int r , c;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter row :");
		r = sc.nextInt();
		System.out.println("Enter column :");
		c = sc.nextInt();
		WordPuzzle puzzle = new WordPuzzle( r , c );
		sc.close();
		
		String file = "dictionary.txt"; // Change the path of the dictionary accordingly!!!
		MyHashTable<String> myNormalHashTable = new MyHashTable<>();
		MyHashTable<String> myEnhancedHashTable = new MyHashTable<>();
		String line = null;
		try 
		{
			long startTime = System.currentTimeMillis( );
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);	
			while ((line = bufferedReader.readLine()) != null) 
			{
				myNormalHashTable.insert(line, true);
			}
			bufferedReader.close();
			fileReader.close();
			long endTime = System.currentTimeMillis( );
			System.out.println("Time Elapsed to create a normal hash table for dictionary words: " + (endTime - startTime) + "ms");
			
			startTime = System.currentTimeMillis( );
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);	
			while ((line = bufferedReader.readLine()) != null) 
			{
				int count = 0;
				int len = line.length();
				StringBuilder word = new StringBuilder();
				while (count < len){
					word.append(line.charAt(count));
					if (count == len - 1)
						myEnhancedHashTable.insert(word.toString(), true);							
					else
						myEnhancedHashTable.insert(word.toString(), false);
					count++;
				}
				
			}
			bufferedReader.close();
			fileReader.close();
			endTime = System.currentTimeMillis( );
			System.out.println("Time Elapsed to create a enhanced hash table for dictionary words: " + (endTime - startTime) + "ms");
			System.out.println();
			System.out.println("FINDING THE WORDS IN THE GRID USING NORMAL SEARCH");
			long startTime3 = System.currentTimeMillis( );
			puzzle.findWordsInTheGrid(myNormalHashTable, false);
			long endTime3 = System.currentTimeMillis( );
			System.out.println( "Time Elapsed time for normal search is : " + (endTime3 - startTime3) );
			
			wordCount = 0;
			wordSet.clear();
			System.out.println();
			System.out.println("FINDING THE WORDS IN THE GRID USING ENHANCED SEARCH");
			long startTime2 = System.currentTimeMillis( );
			puzzle.findWordsInTheGrid(myEnhancedHashTable, true);
			long endTime2 = System.currentTimeMillis( );
			System.out.println( "Time Elapsed time for enhanced search is : " + (endTime2 - startTime2) );
				
			
		}
		catch (FileNotFoundException ex) 
		{
			System.out.println("unable to open file " + file + "");
		}
		catch(IOException ex) 
		{
			System.out.println("Error reading file " + file + "");
		}		
	}
}