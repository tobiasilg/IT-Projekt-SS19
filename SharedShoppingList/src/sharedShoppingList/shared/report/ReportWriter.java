package sharedShoppingList.shared.report;

import java.io.Serializable;

/**
 * <p>
 * Mit Hilfe dieser Klasse, ist es möglich auf dem Client vom Server 
 * zur Verfügung gestellte <code>Report</code>-Objekte in einer für Menschen entsprechendes
 * Format zu lesen. 
 * </p>
 * <p>
 * Die Formate können hier beliebig sein. Methoden, die dafür Zuständig sind die Informationen
 * in das Zielformat zu überführen, werden in den Subklassen implementiert. Mit dieser Klasse 
 * werden nur die Signaturen für die Methoden deklariert, um die Quellinformationen prozessieren zu
 * können. 
 * </p>
 * 
 * @author Nico Weiler
 * @version 1.0
 * @see Report
 */

public abstract class ReportWriter implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Default Konstruktor.
	 */
	public ReportWriter() {
		
	}
	
	
	/**
	 * Übersetzen eines <code>AllArticles</code> - Reports in das 
	 * Zielformat.
	 * 
	 * @param r Der zu übersetzende Report wird übergeben.
	 */
	public abstract void process(AllListEntries r);
	

	/**
	 * Übersetzen eines <code>AllArticlesByStore</code>-Reports in das 
	 * Zielformat.
	 * 
	 * @param r Der zu übersetzende Report wird übergeben.
	 */
	public abstract void process(AllListEntriesByStore r);
	
	
	/**
	 * Übersetzen eines <code>AllArticlesByPeriod</code>-Reports in das
	 * Zielformat.
	 * 
	 * @param r Der zu übersetzende Report wird übergeben.
	 */
	public abstract void process(AllListEntriesByPeriod r);

}
