//import MyHashTable.HashEntry;

//QuadraticProbing Hash table class
//
//CONSTRUCTION: an approximate initial size or default of 101
//
//******************PUBLIC OPERATIONS*********************
//bool insert( x )       --> Insert x
//bool remove( x )       --> Remove x
//bool contains( x )     --> Return true if x is present
//void makeEmpty( )      --> Remove all items


/**
* Probing table implementation of hash tables.
* Note that all "matching" is based on the equals method.
* @author Mark Allen Weiss
*/
public class MyHashTable<AnyType>
{
 /**
  * Construct the hash table.
  */
 public MyHashTable( )
 {
     this( DEFAULT_TABLE_SIZE );
 }

 /**
  * Construct the hash table.
  * @param size the approximate initial size.
  */
 public MyHashTable( int size )
 {
     allocateArray( size );
     doClear( );
 }

 /**
  * Insert into the hash table. If the item is
  * already present, do nothing.
  * @param x the item to insert.
  */
 public boolean insert( AnyType x, boolean isWord )
 {
         // Insert x as active
     int currentPos = findPos( x );
     
     if( isWord ( currentPos ))
     	return false;
     if( isActive( currentPos ) && !isWord )
         return false;
     if( array[ currentPos ] == null )
     {
         ++occupied;
	        array[ currentPos ] = new HashEntry<>( x, true, isWord );
	        theSize++;
	        
	            // Rehash; see Section 5.5
	        if( occupied > array.length / 2 )
	            rehash( );
     }
     else
     {
     	array[currentPos].isWord = true;
     } 
     
     return true;
 }

 /**
  * Expand the hash table.
  */
 public void rehash( )
 {
     HashEntry<AnyType> [ ] oldArray = array;

         // Create a new double-sized, empty table
     allocateArray( 2 * oldArray.length );
     occupied = 0;
     theSize = 0;

         // Copy table over
     for( HashEntry<AnyType> entry : oldArray )
         if( entry != null && entry.isActive )
             insert( entry.element, entry.isWord );
}

 /**
  * Method that performs quadratic probing resolution.
  * @param x the item to search for.
  * @return the position where the search terminates.
  */
 public int findPos( AnyType x )
 {
     int offset = 1;
     int currentPos = myhash( x );
     
     while( array[ currentPos ] != null &&
             !array[ currentPos ].element.equals( x ) )
     {
         currentPos += offset;  // Compute ith probe
         if( currentPos >= array.length )
             currentPos -= array.length;
     }
     
     return currentPos;
 }

 /**
  * Remove from the hash table.
  * @param x the item to remove.
  * @return true if item removed
  */
 public boolean remove( AnyType x )
 {
     int currentPos = findPos( x );
     if( isActive( currentPos ) )
     {
         array[ currentPos ].isActive = false;
         theSize--;
         return true;
     }
     else
         return false;
 }
 
 /**
  * Get current size.
  * @return the size.
  */
 public int size( )
 {
     return theSize;
 }
 
 /**
  * Get length of internal table.
  * @return the size.
  */
 public int capacity( )
 {
     return array.length;
 }

 /**
  * Find an item in the hash table.
  * @param x the item to search for.
  * @return the matching item.
  */
 public boolean contains( AnyType x )
 {
     int currentPos = findPos( x );
     return isActive( currentPos );
 }
 
 public boolean containsWord( AnyType x)
 {
 	int currentPos = findPos( x );
 	return isWord(currentPos);
 }

 /**
  * Return true if currentPos exists and is active.
  * @param currentPos the result of a call to findPos.
  * @return true if currentPos is active.
  */
 public boolean isActive( int currentPos )
 {
     return array[ currentPos ] != null && array[ currentPos ].isActive;
 }
 
 public boolean isWord( int currentPos )
 {
     return array[ currentPos ] != null && array[ currentPos ].isActive && array[currentPos].isWord;
 }

 /**
  * Make the hash table logically empty.
  */
 public void makeEmpty( )
 {
     doClear( );
 }

 public void doClear( )
 {
     occupied = 0;
     for( int i = 0; i < array.length; i++ )
         array[ i ] = null;
 }
 
 public int myhash( AnyType x )
 {
     int hashVal = x.hashCode( );

     hashVal %= array.length;
     if( hashVal < 0 )
         hashVal += array.length;

     return hashVal;
 }
 
 public static class HashEntry<AnyType>
 {
     public AnyType  element;   // the element
     public boolean isActive;  // false if marked deleted
     public boolean isWord;	//false if it is not a word in dictionary

     public HashEntry( AnyType e )
     {
         this( e, true, true );
     }

     public HashEntry( AnyType e, boolean a, boolean w )
     {
         element  = e;
         isActive = a;
         isWord = w;
     }
 }

 public static final int DEFAULT_TABLE_SIZE = 101;

 public HashEntry<AnyType> [ ] array; // The array of elements
 public int occupied;                 // The number of occupied cells
 public int theSize;                  // Current size

 /**
  * Internal method to allocate array.
  * @param arraySize the size of the array.
  */
 public void allocateArray( int arraySize )
 {
     array = new HashEntry[ nextPrime( arraySize ) ];
 }

 /**
  * Internal method to find a prime number at least as large as n.
  * @param n the starting number (must be positive).
  * @return a prime number larger than or equal to n.
  */
 public static int nextPrime( int n )
 {
     if( n % 2 == 0 )
         n++;

     for( ; !isPrime( n ); n += 2 )
         ;

     return n;
 }

 /**
  * Internal method to test if a number is prime.
  * Not an efficient algorithm.
  * @param n the number to test.
  * @return the result of the test.
  */
 public static boolean isPrime( int n )
 {
     if( n == 2 || n == 3 )
         return true;

     if( n == 1 || n % 2 == 0 )
         return false;

     for( int i = 3; i * i <= n; i += 2 )
         if( n % i == 0 )
             return false;

     return true;
 }

 public void print() 
	{		
		System.out.println("Dictionary Words:");
		for (HashEntry<AnyType> i : array ) 
		{
			if (i != null && i.isActive && i.isWord)
				System.out.println(i.element);	
		}
	}  
}
