/**
*@author Matschieu 
*/

public class NoSystemException extends Exception {

	String message;
	
	public NoSystemException() {
		message = "Erreur : aucun système (matrice + vecteur) à sauvegarder";
	}
	
	public String toString() { return message; }
	
}