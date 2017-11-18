/**
*@author Matschieu 
*/

public class NullVectorException extends Exception {

	String message;
	
	public NullVectorException() { message = "Erreur : paramètre Vecteur null"; }
	public NullVectorException(String msg) { message = msg; }
	
	public String toString() { return message; }
	
}