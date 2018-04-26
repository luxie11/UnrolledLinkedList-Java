package Unrolled_Linked_List;

/**
 * Unrolled linked list interface, naudojamas UnrolledLinkedList.java klasėje
 * @author Lukas Matjušaitis
 */
public interface UnrolledLinkedListInterface<E> {
    
    /**
     * Elemento įterpimo metodas
     * @param element-objekto ar elemento duomenys
     * @return - true(įterpus elementą)
     */
    boolean add(E element);
    
    /**
     * Tikrinama ar tuscias LinkedListas
     * @return true/false
     */
    boolean isEmpty();
    
    /**
     * Sukuriamas elementu masyvas
     * @return masyvas
     */
    Object[] toArray();
    
    /**
     * Salinimas is mazgo
     * @return true jei pasalinamas elementas
     */
    boolean remove(E element);
    
    /**
     * Istrinami visi elementai is listo
     */
    void clear();
    
    /**
     * Grazinamas objektas nurodyto indekso
     * @param index - nurodomas elemento indeksas
     * @return - objekto reiksme
     */
    Object get(int index);
    
    /**
     * Tikriname ar yra elementas liste
     * @param element - nurodomas objektas / elementas
     * @return true/false
     */
    boolean contains(E element);
    
    
    /**
     * Elemento pakeitimas, nurodytame indekse mažiau už sizeList - 1
     * @param index - indeksas, kuriame keičiame elementą
     * @param element - elementas
     * @return pakeistas elementas
     */
    E set(int index, E element);
    
    /**
     * Randamas indeksas liste, nurodyto elemento
     * @param element
     * @return 
     */
    int indexOf(E element);
    
    /**
     * Gražinama listo talpa
     * @return talpos skaičius
     */
    int size();
    
    /**
     * Spausdinami duomenys listo
     */
    void println();
    
    /**
     * Spausdinami duomenus listo, nurodžius atraštę
     * @param title - antraštė
     */
    void println(String title);
}
