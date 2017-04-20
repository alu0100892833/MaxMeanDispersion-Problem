/**
 * Asignatura:  Diseño y Análisis de Algoritmos
 * Práctica:    Presentación de Trabajo
 * E-mail:      alu0100881677@ull.edu.es alu0100892833@ull.edu.es alu0100893267@ull.edu.es
 * Fecha        16/3/2017
 * Programa:    Este fichero contiene la definción de la clase Matriz que define una estructura
 * 			    de datos que almacena matrices, la clase ha sido creada especialmente para matrices
 *		        con las que trabaja el algoritmo Strassen, por lo que tiene ciertas restricciones en cuanto
 * 			    a dimensiones y demás.
 * @author      Guillermo Esquivel González, Eduardo de la Paz González, Óscar Darias Plasencia
 * @version     1.0.0
 */
package alu0100892833.daa.Matrix;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.*;
import java.io.FileNotFoundException;

public class Matrix {

    /*******************************
     *********** ATTRIBUTES ********
     *************************/
    private ArrayList<Integer> matriz;
    private int m = 0;
    private int n = 0;




    /**
     * Constructor copia de un objeto de tipo Matriz
     * @param Matriz matriz que será copiada
     */
    public Matrix(Matrix auxiliar) {
        this.m = auxiliar.getM();
        this.n = auxiliar.getN();

        matriz = new ArrayList(auxiliar.getSize());

        for(int i = 0; i < auxiliar.getSize(); i++){
            matriz.add(auxiliar.getValor(i));
        }

    }

    public Matrix(int rows, int cols) {
        this.m = rows;
        this.n = cols;

        matriz = new ArrayList(m * n);
        for(int i = 0; i < m * n; i++){
            matriz.add(0);
        }
    }


    /**
     * Getter del numero de filas de la matriz
     * @return int
     */
    public int getM(){
        return this.m;
    }

    /**
     * Getter del numero de columnas que tiene la matriz
     * @return int
     */
    public int getN(){
        return this.n;
    }

    /**
     * Setter del número de filas la matriz
     * @param int
     */
    public void setM(int m){
        this.m = m;
    }

    /**
     * Setter del número de columnas la matriz
     * @param int
     */
    public void setN(int n){
        this.n = n;
    }

    /**
     * Getter del numero de elementos de la matriz
     * @return int
     */
    public int getSize(){
        return matriz.size();
    }

    /**
     * Getter de la posición dentro del vector de un posición i,j virtual
     * @param int i posición de la fila
     * @param int j posición de la columna
     * @return int
     */
    private int getPos(int i, int j){
        return (i * this.n + j);
    }

    /**
     * Getter del valor de un elemento de la matriz
     * @param int indez indice dentro del vector del valor que queremos coger
     * @return int
     */
    private int getValor(int index){
        return(matriz.get(index));
    }

    /**
     * Getter del valor de un elemento i,j virtual
     * @param int i posición de la fila
     * @param int j posición de la columna
     * @return int
     */
    public int get(int i, int j) {
        return(matriz.get(getPos(i, j)));
    }

    /**
     * Setter del valor de un elemento i,j virtual
     * @param int i posición de la fila
     * @param int j posición de la columna
     * @param int valor
     */
    public void set(int i, int j, int valor) {
        matriz.set(getPos(i, j), valor);
    }

    /**
     * Formateo de la salida por pantalla de un instancia de la clase
     * @return String salida por pantalla
     */
    public String toString() {
        String salida = "";
        salida = salida + this.getM() + " x " + this.getN() + "\n";

        for(int i = 0; i < this.getM(); i++){
            for(int j = 0; j < this.getN(); j++){
                salida = salida + matriz.get(getPos(i, j)) + " ";
            }
            salida += "\n";
        }
        return salida;

    }



}
