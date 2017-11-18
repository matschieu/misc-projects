/**
*@author Matschieu 
*/

public class WriteFileException extends Exception {

	private String message;

	public WriteFileException() { message = "Erreur : impossible d'écrire dans le fichier"; }
	public WriteFileException(String file) { message = "Erreur : impossible d'écrire dans le fichier" + file; }

	public String toString() { return message; }

}
