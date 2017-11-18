/**
*@author Matschieu 
*/

public class NullMatrixException extends Exception {

	String message;
	
	public NullMatrixException() { message = "Erreur : parametre Matrice null"; }
	public NullMatrixException(String msg) { message = msg; }
	
	public String toString() { return message; }
	
}