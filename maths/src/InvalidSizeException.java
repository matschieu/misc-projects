/**
*@author Matschieu 
*/

public class InvalidSizeException extends Exception {

	String message;
	
	public InvalidSizeException() {
		message = "Erreur : taille de la matrice et du vecteur trop grande (>" + Project.MATRIX_MAX_SIZE + ")";
	}
	
	public String toString() { return message; }
	
}