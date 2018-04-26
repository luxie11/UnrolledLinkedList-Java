package Unrolled_Linked_List;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Unrolled Linked List ADT
 * @author Lukas Matjušaitis
 * @param <E> duomenu klasė ar elemento tipas
 */
public class UnrolledLinkedList<E extends Comparable<E>> implements UnrolledLinkedListInterface<E> {
    
    private int capacity;               //Mazgo talpa, įprastao nustyta yra 6
    private int sizeList = 0;           //Elementu kiekis esantis unrolledLinkedList
    private Node firstNode;             //Pradinis mazgo kreipinys
    private Node lastNode;              //Pabaigos mazgo kreipinys
    
    /**
     * Kontruktorius, kuriame nurodoma talpa, jei netenkinama įprasta = 6
     * @param capacity - talpa
     */
    public UnrolledLinkedList(int capacity){
        this.capacity = capacity;
        firstNode = new Node();
        lastNode = firstNode;
    }
    
    /**
     * Tuščias konstruktorius su default talpa lygia 6;
     */
    public UnrolledLinkedList(){
        this(6);
    }
    
    /**
     * Gražinama talpa Unrolled Linked List
     * @return talpa
     */
    @Override
    public int size(){
        return sizeList;
    }
   
    /**
     * Įterpimo metodas
     * @param element - elemento duomenys, ar klasės objektas
     * @return true atlikus įterpimą
     */
    @Override
    public boolean add(E element){
        addIntoNode(lastNode, element, lastNode.numElements);
        return true;
    }
    
    /**
     * Tikrinama ar tuščias listas
     * @return true/false
     */
    @Override
    public boolean isEmpty(){
        return sizeList == 0;
    }
    
    
    /**
     * Gražinamas objektų masyvas
     * @return - masyvas
     */
    @Override 
    public Object[] toArray(){
        Object[] array = new Object[sizeList];
        int index = 0;
        Node node = firstNode;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                array[index++] = node.elementas[i];
            }
            node = node.nextNode;
        }
    return array;
    }
    
    /**
     * Šalinimo metodas, kuris alliekamas
     * nurodžius norimą elementą išmesti
     * @param element - objektas/elementas
     * @return null
     */
    @Override
    public boolean remove(E element){
        Node node = firstNode;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                if(element.equals(node.elementas[i])){
                    node.elementas[i] = null;
                    node.numElements--;
                }
                if(node.elementas[i] == null){
                    node.elementas[i] = node.elementas[i + 1];
                    node.elementas[i + 1] = null;
                }
            }
            if(node.numElements < capacity / 2)
                merge(node);
            node = node.nextNode;
        }
        sizeList--;
        return true;
    }
    
    /**
     * Isvalomas listas
     */
    @Override
    public void clear(){
        Node node = firstNode;
        while(node != null){
            node.nextNode = null;
            node.previousNode = null;
            node.elementas = null;
            node.numElements = 0;
            node = node.nextNode;
        }
        firstNode = null;
        sizeList = 0;
    }
    
    /**
     * Gražinamas nurodytas elementas
     * @param index - indeksas
     * @return - elemento/objekto reikšmė
     */
    @Override
    public Object get(int index){
        Node node = firstNode;
        int counter = 0;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                if(index == counter)
                    return node.elementas[i];
                else{
                    counter++;
                }
            }
            node = node.nextNode;
        }
        return null;
    }

    /**
     * Tikrinama sąlyga ar yra elementas/objektas LinkedList
     * @param element - elementas
     * @return - true/false
     */
    @Override
    public boolean contains(E element){
        Node node = firstNode;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                if(node.elementas[i].equals(element))
                    return true;
            }
            node = node.nextNode;
        }
        return false;
    }
    
    /**
     * Pakeičiamas elementas, nurodytame indekse nurodytu elementu
     * @param index - indeksas
     * @param element - objektas/elementas
     * @return - elementas/objektas
     */
    @Override
    public E set(int index, E element){
        if(index < 0 || index > sizeList - 1){
            throw new IndexOutOfBoundsException("Index is out of bounds");
        }
        int counter = 0;
        Node node = firstNode;
        while(counter <= index - node.numElements){
            counter = counter + node.numElements;
            node = node.nextNode;
        }
        node.elementas[index - counter] = element;
        return element;
    }
    
    /**
     * Gražinamas indeksas, nurodyto elemento
     * @param element - elementas
     * @return - indeksas LinkedList elemento
     */
    @Override
    public int indexOf(E element){
        Node node = firstNode;
        int counter = 0;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                if(node.elementas[i].equals(element))
                    return i + counter;
            }
            counter = counter + node.numElements;
            node = node.nextNode;
        }
        return -1;
    }
    
    /**
     * Spausdinami duomenys listo
     */
    @Override
    public void println(){
        int eilNr = 0;
        Node node = firstNode;
        while(node != null){
            for(int i = 0; i < node.numElements; i++){
                String data = String.format("%3d: %s", eilNr++, node.elementas[i].toString());
                Print.oun(data);
            }
            node = node.nextNode;
        }
        Print.oun("Bendras elementų kiekis yra " + sizeList);
    }
    
    /**
     * Spausdinami duomenys listo, su nurodyta antraste
     * @param title - antraste
     */
    @Override
    public void println(String title){
        Print.oun("|============" + title + "============|");
        println();
        Print.oun("|===========End of the list===========|");
    }
    
    
    
    /**
     * PAPILDOMAS VEIKSMAS
     * Skirtas įterpti elementą į mazgą ar puse elementų
     * perrašyti į naują mazgą ir įterpti elementą naujame mazge
     * @param n - mazgas
     * @param element - elementas
     * @param number  - įterpimo indeksas/vieta
     */
    private void addIntoNode(Node n, E element, int number){
        //TIKRINAMA SALYGA AR MAZGAS YRA PERPILDYTAS, JEI TAIP PUSE ELEMENTU
        //PERRASOME I NAUJA MAZGA IR PERKABINAME RODYKLES.
        if(n.numElements == capacity){
            Node newNodeInformation = new Node();
            int elementCapacity = capacity / 2;
            int startIndex = capacity - elementCapacity;
            for(int i = 0; i < elementCapacity; i++){
                newNodeInformation.elementas[i] = n.elementas[startIndex + i];
                n.elementas[startIndex + i] = null;
            }
            newNodeInformation.numElements = elementCapacity;
            n.numElements = n.numElements - elementCapacity;
            
            //RODYKLIU PERKABINIMAS, NAUJAS MAZGAS ITERPIAMAS I SARASA
            n.nextNode = newNodeInformation;
            newNodeInformation.previousNode = n;
            if(n == lastNode){
                lastNode = newNodeInformation;
            }
            //Tikrinama sąlyga ar norimo įterpti numeris
            //yra didesnis už mazge esančių elementų skaičių
            //ir perkambinami duomenis ankstesniam mazgui ir randamas indexas
            if(number > n.numElements){
                n = newNodeInformation;
                number = number - n.numElements;
            }
        }
        n.elementas[number] = element;
        n.numElements++;
        sizeList++;
    }  
  
    /**
     * PAPILDOMAS VEIKSMAS, NAUDOJAMAS REMOVE 
     * Veiksmas skirtas sujungti du mazgus,
     * jei mazgo elementų skaičius sumažėja perpus (capacity/2)
     * duomenys perrašomi į prieš tai buvusį mazgą 
     * arba užpildomi elementais iš sekančio mazgo
     * @param n - suliejamas mazgas
     */
    private void merge(Node n){
        Node nodeNext = n.nextNode;
        for(int i = 0; i < nodeNext.numElements; i++){
                n.elementas[n.numElements + i] = nodeNext.elementas[i];
                nodeNext.elementas[i] = null;
        }
        n.numElements = n.numElements + nodeNext.numElements;
        n.nextNode = nodeNext.nextNode;
        if(nodeNext == lastNode)
            lastNode = n;
    }
    
    /**
     * Rikiavimo metodas, naudojant masyvą
     */
    public void sortJava(){
        Object[] array = this.toArray();
        Arrays.sort(array);
	int index = 0;
        Node n = firstNode;
        while(n != null){
            for(int i = 0; i < n.numElements; i++){
                n.elementas[i] = array[index++];
            }
            n = n.nextNode;
        }
    }
    
    /**
     * Rikiavimo metodas, pagal pasirinktą comparatorių
     * @param comparator - nurodoma rkiavimo tipas
     */
    public void sortJava(Comparator<E> comparator){
        Object[] array = this.toArray();
        Arrays.sort(array, (Comparator) comparator);
	int index = 0;
        Node n = firstNode;
        while(n != null){
            for(int i = 0; i < n.numElements; i++){
                n.elementas[i] = array[index++];
            }
            n = n.nextNode;
        }
    }
 
    /**
     * Mazgo klasė
     * @param <E> priskiriamas objektas ar duomenu klasė
     */   
    public class Node<E> {
    
    protected Node<E> nextNode;                 //Rodyklė į sekantį mazgą
    protected Node<E> previousNode;             //Rodyklė į prieštai buvusį mazgą
    protected int numElements = 0;              //Elementų skaičius viename mazge
    protected Object[] elementas;               //Masyvas elementų, esančių viename mazge
    
    /**
     * Konstruktorius su priskirta talpa
     */
    public Node(){
        elementas = new Object[capacity];
    }
    
    /**
     * Konstruktorius su nurodytu numerius
     * @param element - elementas
     * @param numberInsertedElement - nurodomas indeksas įterpimo
     */
    public void Node(E element, int numberInsertedElement){
        elementas[numberInsertedElement] = element;
    }   
  }
}
