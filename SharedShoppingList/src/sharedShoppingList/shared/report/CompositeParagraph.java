package sharedShoppingList.shared.report;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * In dieser Klasse werden einzelne Absätze, also <code>SimpleParagraph</code>-Objekte,
 * dargestellt. Diese werden in einer <code>ArrayList</code> als Unterabschnitte abgelegt
 * und verwaltet.
 * 
 * @author Nico Weiler
 * @version 1.0
 * @see sharedShoppingList.shared.report.Paragraph
 */

public class CompositeParagraph extends Paragraph implements Serializable {
	
	    
	    
	 private static final long serialVersionUID = 1L;
	    
	    
		/**
		 * Default Konstruktor.
		 */
	    public CompositeParagraph() {
	    	
	    }
	    
	    
	    /**
	     * Hier werden die Speicherorte der Unterabschnitte zur Verfügung gestellt.
	     */
	    private ArrayList<SimpleParagraph> subParagraphs = new ArrayList<SimpleParagraph>();

	   

	    /**
	     * Hier wird ein Unterabschnitt hinzugefügt.
	     * 
	     * @param sp, der zu hinzufügende Unterabschnitt wird übergeben.
	     */
	    public void addSubParagraph(SimpleParagraph sp) {
	       
	    	this.subParagraphs.add(sp);
	    }

	    
	    /**
	     * Hier wird ein Unterabschnitt entfernt.
	     * 
	     * @param sp, der zu entfernende Unterabschnitt wird übergeben.
	     */ 
	    public void removeSubParagraph(SimpleParagraph sp) {
	       
	    	this.subParagraphs.remove(sp);
	 
	    }

	    
	    /**
	     * Hier werden die ganzen Unterabschnitte ausgelesen.
	     * 
	     * @return <code>ArrayList</code> alle enthaltenen Unterabschnitte werden zurückgegeben.
	     */
	    public ArrayList<SimpleParagraph> getSubParagraphs() {
	       
	    	return this.subParagraphs;
	    }

	    
	    /**
	     * Hier wird die Anzahl der Unterabschnitte ausgelesen.
	     * 
	     * @return die Anzahl der Unterabschnitte wird zurückgegeben.
	     */
	    public int getSubParagraphsSize() {
	        
	    	return this.subParagraphs.size();
	    }

	    
	    /**
	     * Hier wird ein einzelner Unterabschnitt ausgelesen.
	     * 
	     * @return Der entsprechenden Unterabschnitt wird zurückgegeben.
	     */
	    public SimpleParagraph getSubParagraphByIndex(int i) {
	        
	    	return this.subParagraphs.get(i);
	    }

	    
	    /**
	     * Hier wird der <code>CompositeParagraph</code> in einen String umgewandelt.
	     * 
	     * @return Der Buffer in einen String umgewandelt wird, wird zurückgegeben.
	     */
	    public String toString() {
	        
	    	/**
	    	 * Es wird ein leerer Buffer angelegt, worin die ganzen sukzessive
	    	 * String-Repräsentationen der Unterabschnitte eingetragen werden.
	    	 *
	    	 */
	    	
	    	StringBuffer result = new StringBuffer();
	    	
	    	/**
	    	 * eine for-Schleife geht über alle Unterabschnitte durch
	    	 *  
	    	 */
	    	for(int i=0; i< this.subParagraphs.size(); i++) {
	    		
	    		SimpleParagraph sp = this.subParagraphs.get(i);
	    		
	    		
	    		
	    		/**
	    		 * Hier werden die jeweiligen Unterabschnitte in ein String 
	    		 * umgewandelt und anschließend an den StringBuffer gehängt.
	    		 * 
	    		 */
	    		result.append(sp.toString() + "\n");
	    	}
	    	
	    	
	    	/**
	    	 * Abschließend wird der Buffer in einen String umgewandelt und 
	    	 * zurückgegeben.
	    	 * 
	    	 */
	    	
	    	return result.toString();
	    }

}
