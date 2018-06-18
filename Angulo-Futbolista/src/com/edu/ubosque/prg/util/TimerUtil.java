package com.edu.ubosque.prg.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar; 

/** 
* 
* @author Bosco Garita 
*/ 
public class TimerUtil { 

/** 
* @param args the command line arguments 
*/ 
public static void main(String[] args) { 
// Creo la variable fecha1 con la fecha de hoy y luego 
// a la fecha2 le asigno el mismo valor. 
Date fecha1 = Calendar.getInstance().getTime(); 
Calendar fecha2 = Calendar.getInstance(); 

// Ahora le sumo un día a la fecha2 
fecha2.set(Calendar.DAY_OF_MONTH, fecha2.get(Calendar.DAY_OF_MONTH)-4); 
fecha2.set(Calendar.SECOND,0);
fecha2.set(Calendar.MILLISECOND,0);
fecha2.set(Calendar.HOUR,0);
fecha2.set(Calendar.MINUTE,0);

fecha1.setHours(0);
fecha1.setMinutes(0);
fecha1.setSeconds(0);
fecha1.setDate(fecha1.getDate()-1);


// Despliego el valor de ambas variables 
System.out.println(fecha1); 
System.out.println(fecha2.getTime()); 


// Comparo las fechas y despliego el resultado 
//switch (fecha2.compareTo(fecha1)){ 
//case 1: 
//System.out.println("La fecha2 es mayor"); 
//break; 
//case 0: 
//System.out.println("Las fechas son iguales"); 
//break; 
//case -1: 
//System.out.println("La fecha2 es menor"); 
//break; 
//} 
//} 
}
}