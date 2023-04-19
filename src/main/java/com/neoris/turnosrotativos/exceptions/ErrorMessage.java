package com.neoris.turnosrotativos.exceptions;

//Al principio habia creado una manera de mandar las excepciones donde tenia que escribir un mensaje en cada
//caso. Me puse a buscar en internet como evitar hacer eso y me encontre con esta manera, definir los mensajes en
// esta clase y luego en cada excepcion llamo a ErrorMessage.(y el mensaje)

public class ErrorMessage {
    public static final String HS_TRABAJADAS_REQUIRED = "Horas trabajadas es obligatorio";
    public static final String HS_TRABAJADAS_NOT_REQUIRED =
            "El concepto ingresado no requiere el ingreso de horas trabajadas";
    public static final String EMPLOYEE_NOT_FOUND = "No se encontró el empleado con Id: ";
    public static final String EMPLOYEE_NOT_EXIST = "No existe el empleado ingresado";
    public static final String CONCEPT_NOT_FOUND = "No existe el concepto ingresado.";
    public static final String EXISTING_DOCUMENT = "Ya existe un empleado con el documento ingresado.";
    public static final String EXISTING_EMAIL = "Ya existe un empleado con el email ingresado.";
    public static final String DOCUMENT_NOT_NULL = "El número de documento no puede estar vacio.";
    public static final String HORAS_NORMAL = "El rango de horas que se puede cargar para este concepto es de 6 - 8";
    public static final String HORAS_EXTRA = "El rango de horas que se puede cargar para este concepto es de 2 - 6";
    public static final String EMPLOYEE_WITH_FREE_DAY = "El empleado ingresado cuenta con un día libre en esa fecha.";
    public static final String EMPLOYEE_WITH_SAME_CONCEPT =
            "El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.";
    public static final String NO_MORE_THAN_12_HOURS_DAY =
            "El empleado no puede cargar más de 12 horas trabajadas en un día.";
    public static final String NO_FREE_DAY_IN_A_WORK_DAY =
            "El empleado no puede cargar Dia Libre si cargo un turno previamente para la fecha ingresada";
    public static final String NO_MORE_THAN_48_HOURS_A_WEEK = "El empleado ingresado supera las 48 horas semanales.";
    public static final String NO_MORE_THAN_3_EXTRA_TURNS_A_WEEK =
            "El empleado ingresado ya cuenta con 3 turnos extra esta semana.";
    public static final String NO_MORE_THAN_5_NORMAL_TURNS_A_WEEK =
            "El empleado ingresado ya cuenta con 5 turnos normales esta semana.";
    public static final String NO_MORE_THAN_2_FREE_DAYS_A_WEEK = "El empleado no cuenta con más días libres esta semana.";
}
