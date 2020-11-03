package it.unibo.oop.lab.collections1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

/**
 * Example class using {@link java.util.List} and {@link java.util.Map}.
 * 
 */
public final class UseCollection {

	private static int INITIAL_LEN = 999;
	
    private UseCollection() {
    }

    /**
     * @param s
     *            unused
     */
    public static void main(final String... s) {
        
    	/*
         * 1) Create a new ArrayList<Integer>, and populate it with the numbers
         * from 1000 (included) to 2000 (excluded).
         */
    	final List<Integer> intArrList = new ArrayList<>();
    	((ArrayList<Integer>) intArrList).ensureCapacity(INITIAL_LEN);
    	for(int i=1000; i<2000; i++) {
    		intArrList.add(i);
    	}
        
    	/*
         * 2) Create a new LinkedList<Integer> and, in a single line of code
         * without using any looping construct (for, while), populate it with
         * the same contents of the list of point 1.
         */
    	final List<Integer> intLinkedList = new LinkedList<>(intArrList);
        
    	/*
         * 3) Using "set" and "get" and "size" methods, swap the first and last
         * element of the first list. You can not use any "magic number".
         * (Suggestion: use a temporary variable)
         */
    	int lastIndex = intArrList.size()-1;
    	for(int i=0; i<intArrList.size()/2; i++) {
    		int tmp = intArrList.get(lastIndex-i);
    		intArrList.set(lastIndex-i, intArrList.get(i));
    		intArrList.set(i,tmp);
    	}
    	
        /*
         * 4) Using a single for-each, print the contents of the arraylist.
         */
    	for(int i : intArrList) {
    		System.out.println(i);
    	}
        
    	/*
         * 5) Measure the performance of inserting new elements in the head of
         * the collection: measure the time required to add 100.000 elements as
         * first element of the collection for both ArrayList and LinkedList,
         * using the previous lists. In order to measure times, use as example
         * TestPerformance.java.
         */
    	long timeInsertionArray = System.nanoTime();
    	for(int i=0; i < 100_000; i++) {
    		intArrList.add(0, i);
    	}
    	timeInsertionArray = System.nanoTime() - timeInsertionArray;
    	System.out.println("Time of insertion in the array : " + timeInsertionArray);
    	
    	long timeInsertionLinkedList = System.nanoTime();
    	for(int i=0; i < 100_000; i++) {
    		intLinkedList.add(0, i);
    	}
    	timeInsertionLinkedList = System.nanoTime() - timeInsertionLinkedList;
    	System.out.println("Time of insertion in the linked list : " + timeInsertionLinkedList);
    	
        /*
         * 6) Measure the performance of reading 1000 times an element whose
         * position is in the middle of the collection for both ArrayList and
         * LinkedList, using the collections of point 5. In order to measure
         * times, use as example TestPerformance.java.
         */
    	
    	long timeReadingArray = System.nanoTime();
    	for(int i=0; i < 100_000; i++) {
    		intArrList.get(intArrList.size()/2);
    	}
    	timeReadingArray = System.nanoTime() - timeReadingArray;
    	System.out.println("Time of reading an element in the central position of the array : " + timeReadingArray);
    	
    	
    	long timeReadingLinkedList = System.nanoTime();
    	for(int i=0; i < 100_000; i++) {
    		intLinkedList.get(intLinkedList.size()/2);
    	}
    	timeReadingLinkedList = System.nanoTime() - timeReadingLinkedList;
    	System.out.println("Time of reading an element in the central position of the array : " + timeReadingLinkedList);
    	

        /*
         * 7) Build a new Map that associates to each continent's name its
         * population:
         * 
         * Africa -> 1,110,635,000
         * 
         * Americas -> 972,005,000
         * 
         * Antarctica -> 0
         * 
         * Asia -> 4,298,723,000
         * 
         * Europe -> 742,452,000
         * 
         * Oceania -> 38,304,000
         */
    	final Map<String,Long> continents = new HashMap<>();
    	continents.put("Africa", 1_110_635_000L);
    	continents.put("Americas", 972_005_000L);
    	continents.put("Antarctica", 0L);
    	continents.put("Asia", 4_298_723_000L);
    	continents.put("Europe", 742_452_000L);
    	continents.put("Oceania", 38_304_000L);
   
        /*
         * 8) Compute the population of the world
         */
    	long popCount = 0;
    	for(Long singlePop : continents.values()) {
    		popCount = popCount + singlePop;
    	}
    	System.out.println("Population count " + popCount);
    }
}
