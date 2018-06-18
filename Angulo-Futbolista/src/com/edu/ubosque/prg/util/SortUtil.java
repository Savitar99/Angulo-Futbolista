package com.edu.ubosque.prg.util;

	import java.util.Arrays;

	public class SortUtil {

	    static class Equipo implements Comparable<Equipo> {

	        public String nombre;
	        public int puntos;

	        public Equipo(String nombre, int puntos) {
	            this.nombre = nombre;
	            this.puntos = puntos;
	        }

	        @Override
	        public int compareTo(Equipo o) {
	            if (puntos < o.puntos) {
	                return -1;
	            }
	            if (puntos > o.puntos) {
	                return 1;
	            }
	            return 0;
	        }
	    }
	    
	    static void imprimeArrayEquipos(Equipo[] array) {
	        for (int i = 0; i < array.length; i++) {
	            System.out.println((i+1) + ". " + array[i].nombre + " - Puntos: " + array[i].puntos);
	        }
	    }

	    public static void main(String[] args) {
	        Equipo[] arrayEquipos = new Equipo[5];
	        arrayEquipos[0] = new Equipo("Rusia", 22);
	        arrayEquipos[1] = new Equipo("Arabia Saudi", 52);
	        arrayEquipos[2] = new Equipo("Egipto", 27);
	        arrayEquipos[3] = new Equipo("Uruguay", 25);
	     

	        System.out.println("Array sin ordenar por altura");
	        imprimeArrayEquipos(arrayEquipos);
	        // Ordeno el array de personas por altura (de menor a mayor).
	        Arrays.sort(arrayEquipos);
	        System.out.println("Array ordenado por altura");
	        imprimeArrayEquipos(arrayEquipos);
	    }
	}


