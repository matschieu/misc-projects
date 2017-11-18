Matschieu

APPLICATION DE RESOLUTION DE SYSTEMES LINEAIRES Ax = B

LANCER L’APPLICATION

L’application est écrite en Java, elle nécessite donc le Java Runtime Environment pour fonctionner.
Pour lancer l’application sous unix, exécutez dans un terminal la commande (en étant dans le répertoire 
dans lequel se trouve le fichier run) : "./run"  ou "./msyslin".

Remarque :
Si la machine virtuelle Java renvoie une erreur "java.lang.UnsupportedClassVersionError", il faut recompiler 
les classes avec la commande : "./compile" ou "make".

LIRE UN SYSTEME LINEAIRE

Pour lire un système linéaire du type Ax = B ou A est une matrice carrée et B un vecteur, plusieurs choix s’offrent 
à vous.

Le menu "fichier" permet de lire un fichier dans lequel se trouve la matrice A et le vecteur B en cliquant sur "ouvrir".
Le fichier doit obligatoirement être de la forme
N
A(1,1)	A(1,2)	...	A(1,N)		B(1)
A(2,1)	A(2,2)	...	A(2,N)		B(2)
...
A(N,1)	A(N,2)	...	A(N,N)		B(N)
où N est un entier représentant la taille de la matrice et du vecteur.

Le menu "système" permet de saisir la taille de la matrice puis les valeurs une par une en cliquant sur "saisir".
Pour générer une matrice aléatoire en fonction d’un taux de remplissage (le reste étant initialisé à 0), cliquez sur 
"aléatoire".
"A partir des formules" permet de remplir la matrice avec la formule A(i, j)=(a * i + b * j + c) / (d * i + e * j + f)
et le vecteur avec B(i)=(a * i + b) / (c * i + d).

Remarque :
Il est possible de générer un fichier contenant la matrice A et le vecteur B quelque soit la méthode de création utilisée. 
Pour cela, il faut utiliser "enregistrer" qui se trouve dans le menu "fichier". "Nouveau" permet d’effacer le système courant.

Remarque :
La zone du bas permet d’afficher tous les évènements (ouverture de fichier, saisie de matrice, résolution…) ainsi que les 
erreurs.

RESOLUTION D’UN SYSTEME LINEAIRE AX = B

Pour résoudre un système linéaire, il est possible d’utiliser la méthode de Gauss en sélectionnant "méthode de Gauss" dans 
le menu "résolution".
Il est aussi possible de résoudre le système avec la méthode de Jordan en sélectionnant "méthode de Jordan".
Pour les deux méthodes, il est possible d'utiliser trois approches possibles des méthodes.
Il est possible d'afficher la norme du vecteur d'erreur E = Ax - B dans le menu "résolution".

Remarque :
Dans le cas ou le système comprend une infinité de solutions pour une valeur du vecteur x, la valeur 0 est sélectionnée 
arbitrairement pour résoudre le système.
