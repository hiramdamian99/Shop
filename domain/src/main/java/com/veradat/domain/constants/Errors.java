
package com.veradat.domain.constants;

public class Errors {
	
	private Errors() {
		throw new IllegalStateException("NO se puede tener una instancia de esta clase");
	}
	
	public static final String ERROR_FILTER_MESSAGE = "Se ha ingresado un filtro incorrecto";
	public static final String TIME_SCALE_FILTER_ERROR = "Se ha ingresado la escala de tiempo incorrecta";
	public static final String FIRST_FILTER_ERROR = "Se ha ingresado el primer filtro incorrecto";
	public static final String SECOND_FILTER_ERROR = "Se ha ingresado el segundo filtro incorrecto";
	public static final String THIRD_FILTER_ERROR = "Se ha ingresado el tercer filtro incorrecto";
	public static final String FOURTH_FILTER_ERROR = "Se ha ingresado el cuarto filtro incorrecto";
	public static final String AREA_FILTER_ERROR = "Se ha ingresado el filtro de área incorrecto";
	public static final String ORIGIN_FILTER_ERROR = "Se ha ingresado el filtro de origen incorrecto";
	public static final String PERSON_TYPE_FILTER_ERROR = "Se ha ingresado el filtro de persona incorrecto";
	public static final String HITS_FILTER_ERROR = "Se ha ingresado el filtro de Hits incorrecto";
	public static final String NEED_ALL_FILTER_ERROR = "Se necesitan ingresar todos los filtros";
	public static final String FILTER_COMBINATION_ERROR = "Se ha ingresado una combinación del filtros incorrecta";
	public static final String NOT_FOUND_INFO = "No se encontro información con los filtros especificados";
	public static final String CRIME_ERROR = "Se ha ingresado un tipo de crimen incorrecto";
	public static final String GROUP_FILTER_ERROR = "Se ha ingresado un filtro de agrupamiento incorrecto";
	public static final String NEED_CRIME_ERROR = "Es necesario especificar el tipo de crimen";
	public static final String NEED_CRIME_TIPOLOGIES_ERROR = "Es necesario especificar las tipologias de crimen";
	public static final String NUM_OF_TIPOLOGIES_ERROR = "Solo se permiten una cantidad de tipologias de crimen entres 5 y 10";
	public static final String NEED_FILTER = "Es necesario ingresar el filtro ";
	public static final String NEED_TIME_ZONE = "Es necesario ingresar la zona horaria";
	public static final String NEED_OPERARTION_AREA = "Es necesaria el area de operación";
	public static final String NEED_TIME_SCALE = "Es necesaria la escala de tiempo";
}
