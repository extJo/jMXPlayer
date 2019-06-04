/**
 * jMXPlayer, a GUI to IEEE PAR1599 (MX) data
 * Copyright © 2010 Riccardo Attilio Galli <riccardo@sideralis.org>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package src.Model;

import java.util.ArrayList;
import java.awt.Rectangle;
import java.awt.Dimension;

public class RTree {
	protected int capacity;

	protected Node root;

	// costruttore dell'albero, imposta la sua capacità

	public static void main(String[] args) {

		System.out.println("" + "  0123456789");
		System.out.println("");
		System.out.println("0 00XXX00000");
		System.out.println("1 00XXX000XX");
		System.out.println("2 00000000XX");
		System.out.println("3 00000000XX");
		System.out.println("4 XXXXX000XX");
		System.out.println("5 XXXXX00000");

		RTree tree = new RTree(4);
		tree.insertNewElement(new Rectangle(2, 0, 3, 2));
		tree.insertNewElement(new Rectangle(8, 1, 2, 4));
		tree.insertNewElement(new Rectangle(0, 4, 5, 2));

		ArrayList res;

		System.out.println("Search in point (3,1)");
		res = tree.foundRect(tree.getRoot(), 3, 1);
		for (int i = 0; i < res.size(); i++) {
			System.out.println(((Rectangle) res.get(i)).x);
			System.out.println(((Rectangle) res.get(i)).y);
			System.out.println(((Rectangle) res.get(i)).width);
			System.out.println(((Rectangle) res.get(i)).height);
			System.out.println();
		}

		System.out.println("Search in point (6,2)");
		res = tree.foundRect(tree.getRoot(), 6, 2);
		for (int i = 0; i < res.size(); i++) {
			System.out.println(((Rectangle) res.get(i)).x);
			System.out.println(((Rectangle) res.get(i)).y);
			System.out.println(((Rectangle) res.get(i)).width);
			System.out.println(((Rectangle) res.get(i)).height);
			System.out.println();
		}

	}

	public RTree(int c) {
		capacity = c;
		root = null;
	}

	// inserisce il nuovo rettangolo r nell'albero

	public void insertNewElement(Rectangle r) {
		Node choosedLeaf; // foglia dove inserire
		Node newNode; // eventuale nuovo nodo creato dalla split

		if (root == null) {

			// se root è null devo creare il primo nodo

			root = new Node(capacity);
			root.insertNewRectangle(r, null);
			root.setParent(null);
		}

		else {
			// scelgo la foglia
			choosedLeaf = ChooseLeaf(r);

			// inserisco il rettangolo nella foglia
			newNode = choosedLeaf.insertNewRectangle(r, null);

			if (newNode != null) {
				// ho creato un nuovo nodo sistemo entrambi
				AdjustTree(choosedLeaf, newNode);
			} else {
				// nessun nodo nuovo, aggiorno solo i padri della foglia cambiata
				if (choosedLeaf.getParent() != null) {
					AdjustTree(choosedLeaf);
				}
			}
		}
	}

	// prendo in input un nodo, calcola la bounded box di
	// tutti i rettangoli di quel nodo e aggiorna il padre del nodo in
	// modo che contenga la bounded box aggiornata
	protected void AdjustTree(Node n) {
		Node parent = n.getParent();
		int indexP = n.getIndex();
		Rectangle rectTmp, tmp1;
		rectTmp = n.getRect(0);

		// calcolo in rectTmp la nuova bounded box del nodo
		for (int i = 1; i < n.numRect(); i++) {
			if (n.getRect(i) != null)
				rectTmp = rectTmp.union(n.getRect(i));
		}

		// aggiorno la bounded box nel padre del nodo
		parent.setRect(indexP, rectTmp);

		if (parent.getParent() != null) {
			// risalgo nell'albero per impostare anche tutti i predecessori
			AdjustTree(parent);
		}
	}

	// sistema l'albero quando un inserimento causa la creazione di un nuovo nodo

	protected void AdjustTree(Node n1, Node n2) {
		// n1 è il nodo vecchio
		// n2 è il nodo appena creato

		Node res;
		Node parent = n1.getParent(); // calcolo il padre del vecchio nodo
		Rectangle rectTmp1, rectTmp2, r;

		int indexP;

		// lavoro su n1, calcolo la bounded box

		rectTmp1 = n1.getRect(0);

		for (int i = 1; i < n1.numRect(); i++) {
			rectTmp1 = rectTmp1.union(n1.getRect(i));
		}

		// lavoro su n2, calcolo la bounded box
		rectTmp2 = n2.getRect(0);

		for (int i = 1; i < n2.numRect(); i++) {
			rectTmp2 = rectTmp2.union(n2.getRect(i));
		}

		// se sono risalito fin su creo una nuova radice
		if (parent == null) {
			res = new Node(this.capacity);
			root = res;

			// inserisco nella nuova radice i nodi
			root.insertNewRectangle(rectTmp1, n1);

			root.insertNewRectangle(rectTmp2, n2);

			// e sistemo i valori della radice
			root.setParent(null);
			root.setIndex(-1);
		}

		// altrimenti continuo a sistemar l'albero
		else {
			// aggiorno il rettangolo già presente per comprendere i nuovi rettangoli
			// il suo indice non cambia

			indexP = n1.getIndex();
			parent.setRect(indexP, rectTmp1);

			// inserisco il nuovo rettangolo creato nel nodo padre
			// devo a questo punto settare l'indice del nodo che dipende da questo
			// nuovo rettangolo

			res = parent.insertNewRectangle(rectTmp2, n2);

			// se ho ancora uno split devo risistemare l'albero aggiungengo il
			// nuovo nodo
			if (res != null) {
				AdjustTree(parent, res);
			}

			// altrimenti aggiorno le bounded box che mi precedono

			else if (parent.getParent() != null) {
				AdjustTree(parent);
			}

		}

	}

	// sceglie la foglia dove inserire il nuovo rettangolo r

	protected Node ChooseLeaf(Rectangle r) {

		Node N = root;

		Node choosed;

		while (!(N.isLeaf())) {

			// scelgo il figlio su cui andare nella ricerca di r

			choosed = chooseChild(N, r);

			N = choosed;

		}

		return N;

	}

	// sceglie il nodo di un determinato livello l

	// dove inserire il nuovo rettangolo r,

	// c è il livello corrente

	protected Node ChooseLeaf(Rectangle r, int l, int c) {

		Node N = root;

		Node choosed;

		// finché non raggiungo il livello che cerco -

		// i due livelli devono essere uguali

		while (l != c) {

			choosed = chooseChild(N, r);

			N = choosed;

			c++;

		}

		return N;

	}

	// sceglie il figlio del nodo più adatto in cui discendere per inserire un

	// nuovo nodo

	// r è il nuovo rettangolo da inserire

	protected Node chooseChild(Node Main_node, Rectangle r) {

		Node childChoosed;

		Rectangle rectTmp, rectMin;

		Dimension size1, size2, minSize;

		double area, area1, area2, minArea;

		int min = 0; // indice del figlio scelto per inserire il rettangolo

		// inizializzo i valori in base al primo figlio

		rectTmp = r.union(Main_node.getRect(0));

		// calcolo la dimensione del rettangolo iniziale

		size1 = (Main_node.getRect(0)).getSize();

		area1 = size1.getHeight() * size1.getWidth();

		// calcolo la dimensione del rettangolo una volta inserito quello nuovo

		size2 = rectTmp.getSize();

		area2 = size2.getHeight() * size2.getWidth();

		minArea = area2 - area1; // calcolo l'area minima iniziale

		min = 0; // e l'indice del rattangolo di tale area minima

		// poi verifico che non ce ne siano altri piu' adatti

		for (int i = 1; i < Main_node.numRect(); i++) {

			rectTmp = r.union(Main_node.getRect(i));

			size1 = (Main_node.getRect(i)).getSize();

			area1 = size1.getHeight() * size1.getWidth();

			size2 = rectTmp.getSize();

			area2 = size2.getHeight() * size2.getWidth();

			// rectTmp e' il rettangolo che contiene i due rettangoli

			area = area2 - area1;

			if (area < minArea) {

				minArea = area;

				min = i;

			}

		}

		return Main_node.getChild(min);

	}

	// ritorna la radice dell'albero

	public Node getRoot() {

		return root;

	}

	// verifica se l'albero è vuoto

	public int isEmpty() {

		int res = 0;

		if (root == null) {

			res = 1;

		}

		else {

			res = 0;

		}

		return res;

	}

	// restituisce la lista dei rettangoli dell'albero

	public ArrayList creaLista2(Node n) {

		ArrayList al = new ArrayList();

		if (n == null)
			al = null;

		else {

			for (int i = 0; i < n.numRect(); i++) {

				if (n.getChild(i) != null) {

					al.addAll(creaLista2(n.getChild(i)));

					al.add(n.getRect(i));

				}

				else if (n.rectangle[i] != null) {

					al.add(n.getRect(i));

				}

			}

			System.out.println("4");

		}

		return al;

	}

	// stampa la lista dei nodi dell'albero con il loro livello

	public ArrayList creaLista4(Node n, int l) {

		ArrayList al = new ArrayList();

		Rectangle r;

		if (n == null)
			al = null;

		else {

			for (int i = 0; i < n.numRect(); i++) {

				if (n.getChild(i) != null) {

					al.addAll(creaLista4(n.getChild(i), l + 1));

					r = n.getRect(i);

					System.out.println("\n" + r.getX() + "," + r.getY() + "," + r.getHeight() + "," + r.getWidth()
							+ " - livello:" + l);

				}

				else if (n.rectangle[i] != null) {

					r = n.getRect(i);

					System.out.println("\n" + r.getX() + "," + r.getY() + "," + r.getHeight() + "," + r.getWidth()
							+ " - livello:" + l);

				}

			}

		}

		return al;

	}

	public void creaLista5(Node n, int l) {

		Rectangle r;

		if (n == null) {
		}

		else {

			for (int i = 0; i < n.numRect(); i++) {

				if (n.getChild(i) != null) {

					creaLista5(n.getChild(i), l + 1);

				}

				else if (n.rectangle[i] != null) {

					System.out.println("\nLivello : " + l + " padre :" + n.getIndex());

				}

			}

		}

	}

	// trova il rettangolo che contiene il punto di coordinata (x,y)

	public ArrayList foundRect(Node n, int x, int y) {

		ArrayList al = new ArrayList();

		if (n == null)
			al = null;

		else {

			for (int i = 0; i < n.numRect(); i++) {

				if (n.getChild(i) != null) {

					// cerco nei figli il punto, se vi è contenuto

					if (((Rectangle) n.getRect(i)).contains(x, y)) {

						al.addAll(foundRect(n.getChild(i), x, y));

					}

				}

				else if (n.rectangle[i] != null) {

					// controllo se la foglia contiene il punto

					if (((Rectangle) n.getRect(i)).contains(x, y)) {

						al.add(n.getRect(i));

					}

				}

			}

		}

		return al;

	}

	// restituisce la lista dei rettangoli di un certo livello dell'albero

	public ArrayList creaLista2(Node n, int l, int c) {

		ArrayList al = new ArrayList();

		if (n == null)
			al = null;

		else {

			for (int i = 0; i < n.numRect(); i++) {

				if (n.getChild(i) != null) {

					al.addAll(creaLista2(n.getChild(i), l, c + 1));

					if (l == c)
						al.add(n.getRect(i));

				}

				else if (n.rectangle[i] != null) {

					if (l == c)
						al.add(n.getRect(i));

				}

			}

		}

		return al;

	}

	// cancella il rettangolo r dall'albero

	public void deleteElement(Rectangle r) {

		ArrayList listaNodi, listaLivelli, tmp;

		Integer livello = new Integer(1);

		Node padre;

		double c;

		int l;

		listaNodi = new ArrayList(); // lista dei nodi da reinserire

		listaLivelli = new ArrayList(); // corrispondenti livelli dei nodi da reinserire

		tmp = new ArrayList();

		c = capacity;

		c = Math.ceil(c / 2);

		Node leaf;

		// se l'albero è vuoto non cancello niente

		if (root == null) {

			listaNodi = null;

			listaLivelli = null;

		}

		else {

			// cerco la fogli dove c'e' il rettangolo, con annesso livello

			root.FindLeaf(r, livello, tmp);

			// se non ho trovato niente la foglia è null

			if (tmp.isEmpty())
				leaf = null;

			else {

				livello = (Integer) tmp.get(1);

				leaf = (Node) tmp.get(0);

			}

			// una volta trovata la foglia la elimino

			if (leaf != null) {

				for (int i = 0; i < leaf.numRect(); i++) {

					if (r.equals(leaf.getRect(i))) {

						// cancella il rettangolo

						leaf.delRectangle(i);

						// risistemo il nodo foglia spostando l'ultimo rattangolo

						// nel posto vuoto

						// in questo caso non ho figli da spostare!

						leaf.setRect(i, leaf.getRect(leaf.numRect()));

						leaf.setRect(leaf.numRect(), null);

						// if(leaf.getChild(i)!=null)(leaf.getChild(i)).setIndex(i);

						// controllo se devo risistemare tutto

						// perché sono rimasti pochi rettangoli

						if (leaf.numRect() < c && leaf != root) {

							// cancello la bounded box presente nel padre

							// i parametri che passo alla AdjustTree sono:

							// 1- lista di ritorno dei nodi da reinserire

							// 2- lista di ritorno dei livelli dei corrispondenti nodi da reinserire

							// 3- nodo eliminato

							// 4- livello di partenza: radice = 1

							// questa AdjustTree estrae tutti i sottoalberi che hanno

							// pochi rettangoli nella radice

							AdjustTree(listaNodi, listaLivelli, leaf, livello);

							// ora reinserisco gli elementi della mia lista

							insertOldElement(listaNodi, listaLivelli);

						}

						else {

							// devo solo riaggiornare il padre

							if (leaf.getParent() != null)
								AdjustTree(leaf);

						}

					}

				}

			}

			// se la radice ha solo un figlio...

			if (root.numRect() == 1 && root.getChild(0) != null) {

				// ...il figlio diventa la nuova radice

				creaLista4(root, 1);

				root = root.getChild(0);

				root.setParent(null);

				root.setIndex(-1);

			}

			if (root.numRect() == 0) {

				root = null;

			}

		}

	}

	// prende dalle liste tutti gli elementi che sono stati eliminati

	// e li va a inserire di nuovo nell'albero

	public void insertOldElement(ArrayList ln, ArrayList ll) {

		Node n, child, choosedLeaf, newNode;

		Rectangle rectTmp;

		int l;

		newNode = null;

		choosedLeaf = null;

		rectTmp = null;

		// scorro tutti i nodi da reinserire

		for (int i = ln.size() - 1; i > (-1); i--) {

			n = (Node) ln.get(i);

			l = ((Integer) ll.get(i)).intValue();

			// per ogni nodo estraggo tutti i rettangoli contenuti e li inserisco

			for (int k = 0; k < capacity; k++) {

				rectTmp = n.getRect(k);

				child = n.getChild(k);

				if (rectTmp != null) {

					// scelgo la foglia dove inserirlo

					choosedLeaf = ChooseLeaf(rectTmp, l, 1);

					// se c'e' il rettangolo lo inserisco nella posizione scelta

					newNode = choosedLeaf.insertNewRectangle(rectTmp, child);

					// ora verifico se si sono creati dei nuovi nodi

					if (newNode != null) {

						// Nuovo nodo da inserire

						AdjustTree(choosedLeaf, newNode);

					}

					// altrimenti aggiorno solo i predecessori

					else {

						if (choosedLeaf.getParent() != null) {

							// Nessun nuovo nodo da inserire

							AdjustTree(choosedLeaf);

						}

					}

				}

			}

		}

	}

	// è chiamata appena tolta la foglia trovata e il nodo padre ha pochi

	// elementi

	// prende in input un nodo, lo aggiunge a quelli da reinserire

	public void AdjustTree(ArrayList ln, ArrayList ll, Node node, Integer livello) {

		Node padre_node;

		double Capacity;

		int indiceDelPadre;

		Capacity = capacity;

		Capacity = Math.ceil(Capacity / 2); // mi serve per sapere quanto è pieno il nodo

		// aggiungo la foglia ridotta agli elementi da reinserire

		ln.add(node);

		ll.add(livello);

		indiceDelPadre = node.getIndex();

		// sono sicuro che n non è root, cancello il riferimento all'albero

		padre_node = node.getParent();

		padre_node.delRectangle(indiceDelPadre);

		// ora padre.numRect() è diminuito di uno e punta al rettangolo

		// da inserire nel buco che si è creato nell'array di nodi

		// risistemo il padre, sia il rettangolo che il nodo figlio

		// spostando l'ultimo elemento in quello appena cancellato

		padre_node.setRect(node.getIndex(), padre_node.getRect(padre_node.numRect()));

		padre_node.setChild(node.getIndex(), padre_node.getChild(padre_node.numRect()));

		padre_node.setRect(padre_node.numRect(), null);

		padre_node.setChild(padre_node.numRect(), null);

		// imposto l'indice del figlio

		if (padre_node.getChild(indiceDelPadre) != null)
			(padre_node.getChild(indiceDelPadre)).setIndex(indiceDelPadre);

		// se anche il padre ha pochi rettangoli...

		if (padre_node.numRect() < Capacity && padre_node != root) {

			// ...cancello la bounded box presente nel padre

			// e salgo ancora per sistemare l'albero

			AdjustTree(ln, ll, padre_node, new Integer(livello.intValue() - 1));

		}

		else {

			// altrimenti aggiorno solamente le bounded box dei predecessori

			if (padre_node.getParent() != null)
				AdjustTree(padre_node);

		}

	}

}

