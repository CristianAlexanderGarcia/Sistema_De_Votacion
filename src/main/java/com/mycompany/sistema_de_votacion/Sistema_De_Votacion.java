/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_de_votacion;

/**
 *
 * @author Alex
 */import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sistema_De_Votacion {

  // Variables para almacenar la fecha y hora de inicio de la votación, así como su duración en minutos
private static String fechaVotacion = "";       // Almacena la fecha de inicio de la votación.
private static String horaVotacion = "";        // Almacena la hora de inicio de la votación.
private static int duracionMinutos = 0;         // Almacena la duración de la votación en minutos.

// Variable para almacenar los votos registrados durante la votación
private static ArrayList<String> votos = new ArrayList<>();  // Almacena los votos de los votantes durante la votación.

    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
         String nuevaContraseña = "cadena";
        String infoCandidatos = """
                                Nombre: Carlos L\u00f3pez| Edad: 38 a\u00f1os| Profesi\u00f3n: Ingeniero| Pa\u00eds: Espa\u00f1a| CUI: 0928437
                                Nombre: Juan Mart\u00ednez| Edad: 50 a\u00f1os| Profesi\u00f3n: M\u00e9dico| Pa\u00eds: M\u00e9xico| CUI: 4389jklad
                                Nombre: Andr\u00e9s Garc\u00eda| Edad: 42 a\u00f1os| Profesi\u00f3n: Empresario| Pa\u00eds: Guatemala| CUI: nckjd1234
                                Nombre: Rafael Torres| Edad: 36 a\u00f1os| Profesi\u00f3n: Profesor| Pa\u00eds: Guatemala| CUI: lka67cswews
                                Nombre: Alejandro Vargas| Edad: 44 a\u00f1os| Profesi\u00f3n: Abogado| Pa\u00eds: Guatemala| CUI: 145231mnhzzxc
                                Nombre: Mar\u00eda P\u00e9rez| Edad: 35 a\u00f1os| Profesi\u00f3n: Periodista| Pa\u00eds: Guatemala| CUI: xzdhcxzl3412a
                                Nombre: Ana Silva| Edad: 48 a\u00f1os| Profesi\u00f3n: Empresaria| Pa\u00eds: M\u00e9xico| CUI: 68sbncADS197334
                                Nombre: Carolina Rodr\u00edguez| Edad: 40 a\u00f1os| Profesi\u00f3n: M\u00e9dica| Pa\u00eds: Guatemala| CUI: CKJBK898465sc
                                Nombre: Laura Fern\u00e1ndez| Edad: 37 a\u00f1os| Profesi\u00f3n: Profesora| Pa\u00eds: Guatemala| CUI: JGyya6754s
                                Nombre: Valentina Torres| Edad: 41 a\u00f1os| Profesi\u00f3n: Abogada| Pa\u00eds: Guatemala| CUI: sjdfjh2873GS
                                Nombre: Valentina Torres | Edad: 41 años | Profesión: Abogada | País: Guatemala | CUI: sjdfjh2873GS
                                Nombre: Carlos García | Edad: 34 años | Profesión: Ingeniero Civil | País: El Salvador | CUI: 02384712534
                                Nombre: Ana López | Edad: 28 años | Profesión: Médica | País: Honduras | CUI: 8765432190
                                Nombre: Diego Martínez | Edad: 45 años | Profesión: Arquitecto | País: Guatemala | CUI: 1234567890123
                                Nombre: Rosa Pérez | Edad: 39 años | Profesión: Contadora | País: El Salvador | CUI: 23456789123
                                Nombre: Juan Rivera | Edad: 31 años | Profesión: Profesor | País: Honduras | CUI: 7654321987
                                Nombre: Laura Méndez | Edad: 36 años | Profesión: Psicóloga | País: Guatemala | CUI: 9876543210123
                                Nombre: Martín Torres | Edad: 29 años | Profesión: Ingeniero de Sistemas | País: El Salvador | CUI: 45678901234
                                Nombre: Sofía Gómez | Edad: 33 años | Profesión: Odontóloga | País: Honduras | CUI: 3456789123
                                Nombre: Luis Rodríguez | Edad: 40 años | Profesión: Contador | País: Guatemala | CUI: 5678901234567""";
         
        // INGRESO DE DATOS
        System.out.println("SISTEMA DE VOTACIÓN \n ¡BIENVENIDOS!");

        int contraseMaestra;
        do {
            System.out.println("Ingrese Su Contraseña Para Comenzar:");
            String entradaUsuario = scan.nextLine();
            
            if (entradaUsuario.trim().isEmpty()) {
                break;
            }
            try {
                contraseMaestra = Integer.parseInt(entradaUsuario);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Ingrese un número válido.");
            }
        } while (true);


        // ESTAS VARIABLES CONTENDRÁN LOS DATOS DEL USUARIO
        String nombre, ape, contraseña, correo, sex, pro, fecha, resi, depa, muni, pais;
        int cui, edad;
long inicioVotacion = 0;
        
        // Registro de usuario
        System.out.println("REGISTRO DE USUARIO");
        System.out.println("Seleccione su tipo de usuario:");
        System.out.println("1. Administrador");
        System.out.println("2. Auditor");
        System.out.println("3. Votante");
        System.out.println("4. Registrador de Votantes");
        System.out.println("5. Eliminar Usuario");
        System.out.println("6. Registrar Fallecimiento");
        System.out.println("7. Configurar Inicio de Votación");
        int opcion = scan.nextInt();
        scan.nextLine();
       
        // Verificamos si el archivo ya existe
        File archivo = new File("archivo_texto.txt");
        boolean archivoExiste = archivo.exists();

        try (FileWriter nuevo = new FileWriter(archivo, true);
     BufferedWriter bw = new BufferedWriter(nuevo)) {

    if (!archivoExiste) {
        // Agregar los títulos al archivo original
        bw.write("CUI|NOMBRES|APELLIDOS|CONTRASEÑA|SEXO|FECHA DE NACIMIENTO|CARRERA|CORREO|RESIDENCIA|DEPARTAMENTO|MUNICIPIO|PAIS|EDAD|TIPO\n");
    }

    // Verificamos si el archivo de copia ya tiene títulos
    File archivoCopia = new File("archivo_texto_copia.txt");
    boolean copiaExiste = archivoCopia.exists();

    try (FileWriter fw = new FileWriter(archivoCopia, true);
         BufferedWriter copia = new BufferedWriter(fw)) {

        if (!copiaExiste) {
            // Agregar los títulos al archivo de copia
            copia.write("CUI|NOMBRES|APELLIDOS|CONTRASEÑA|SEXO|FECHA DE NACIMIENTO|CARRERA|CORREO|RESIDENCIA|DEPARTAMENTO|MUNICIPIO|PAIS|EDAD|TIPO\n");
        }
    
boolean salir = false; // Inicialmente, no deseamos salir del programa

while (!salir) {
        while (true) {
    mostrarMenuPrincipal();
    opcion = scan.nextInt();
    scan.nextLine(); // Consume el salto de línea después de la entrada numérica

               switch (opcion) {
    case 1:
        // Administrador
        System.out.println("1. | Crear Administrador");
        System.out.println("2. | Modificar Administrador");
        int adminOption = scan.nextInt();
        scan.nextLine();

       switch (adminOption) {
case 1:
    while (true) {
        System.out.println("INGRESE SUS DATOS COMO ADMINISTRADOR");
        System.out.println("1. | Ingrese Su CUI");
        cui = scan.nextInt();
        int cuiAdmin = cui;
        scan.nextLine(); // Limpiar el buffer del teclado
        System.out.println("2. | Ingrese Sus Nombres");
        nombre = scan.nextLine();
        System.out.println("3. | Ingrese Sus Apellidos");
        ape = scan.nextLine();
        System.out.println("4. | Ingrese su contraseña");
        contraseña = scan.nextLine();
        System.out.println("5. | Ingrese Su Sexo");
        sex = scan.nextLine();
        System.out.println("6. | Ingrese Su Fecha De Nacimiento (Formato: YYYY-MM-DD)");
        fecha = scan.nextLine();
        System.out.println("7. | Ingrese Su Profesión");
        pro = scan.nextLine();
        System.out.println("8. | Ingrese Su Correo Electrónico");
        correo = scan.nextLine();
        System.out.println("9. | Agregue Su Residencia");
        resi = scan.nextLine();
        System.out.println("10. | Agregue Su Departamento");
        depa = scan.nextLine();
        System.out.println("11. | Agregue Su Municipio");
        muni = scan.nextLine();
        System.out.println("12. | Agregue Su País");
        pais = scan.nextLine(); // Consume la nueva línea
        System.out.println("13. | Ingrese Su Edad");
        edad = scan.nextInt();
        scan.nextLine(); // Consume la nueva línea después de la edad

         // Validación de edad
                    if (edad < 18) {
                        System.out.println("No puedes ingresar porque eres menor de edad.");
                    } else {
                        String tipoUsuario = "Administrador";
                        String line = cui + "|" + nombre + "|" + ape + "|" + contraseña + "|" + sex + "|" + fecha + "|" + pro + "|" + correo + "|" + resi + "|" + depa + "|" + muni + "|" + pais + "|" + edad + "|" + tipoUsuario;
                        bw.write(line + "\n");
                        copia.write(line + "\n");
                        System.out.println("Usuario creado exitosamente como " + tipoUsuario);

                        // Solicitar contraseña al usuario
                        System.out.println("Por favor, ingrese su contraseña para acceder:");
                        String contraseñaIngresada = scan.nextLine();

                  if (contraseñaIngresada.equals(contraseña)) {
    // Contraseña correcta para el administrador
    System.out.println("¡Bienvenido, " + nombre + "!");   
    // Generar nueva contraseña combinando CUI, nombre, apellido, contraseña y país
    nuevaContraseña = cui + nombre + ape + contraseña + pais;
    nuevaContraseña = nuevaContraseña.replaceAll("\\s", ""); // Eliminar espacios en blanco

    // la nueva contraseña tenga exactamente 16 caracteres
    if (nuevaContraseña.length() > 16) {
        nuevaContraseña = nuevaContraseña.substring(0, 16);
    } else if (nuevaContraseña.length() < 16) {
        while (nuevaContraseña.length() < 16) {
            char randomChar = (char) (Math.random() * 26 + 'a'); // Genera una letra aleatoria
            nuevaContraseña += randomChar;
        }
    }
    System.out.println("Tu nueva contraseña es: " + nuevaContraseña);
// Declaración de una lista de candidatos
ArrayList<String> listaCandidatos = new ArrayList<>(Arrays.asList(infoCandidatos.split("\n")));
        //Menu de gestion
        while (true) {
            System.out.println("Opciones:");
            System.out.println("1. Ver a los candidatos");
            System.out.println("2. Modificar candidatos");
            System.out.println("3. Eliminar candidatos");
            System.out.println("4. Gestionar los partidos");
            System.out.println("5. Volver al menú principal");
            int adminOpciones = scan.nextInt();
            scan.nextLine(); // Consumir el salto de línea

            switch (adminOpciones) {
                case 1:
                    // Mostrar la lista de candidatos
                    for (int i = 0; i < listaCandidatos.size(); i++) {
                        System.out.println("Candidato no." + (i + 1) + ":");
                        System.out.println(listaCandidatos.get(i));
                        System.out.println();
                    }
                    break;
                case 2: //Verificar la contraseña del administrador
                    System.out.println("Por favor, ingrese la contraseña de 16 caracteres para continuar:");
                    String contraseñaConfirmar = scan.nextLine();

                    if (contraseñaConfirmar.length() == 16 && contraseñaConfirmar.equals(nuevaContraseña)) {
                        System.out.println("Por favor, seleccione el número del candidato que desea modificar (1, 2, 3, etc.):");
                        int candidatoAModificar = scan.nextInt();
                        scan.nextLine(); // Consumir el salto de línea

                        if (candidatoAModificar > 0 && candidatoAModificar <= listaCandidatos.size()) {
                            System.out.println("Candidato no." + candidatoAModificar + ":");
                            System.out.println(listaCandidatos.get(candidatoAModificar - 1));
                            System.out.println("Modifique al candidato (para cancelar y volver al menú presione 0):");

                            // Leer la nueva información del candidato
                            String nuevaInformación = scan.nextLine();

                            if (!nuevaInformación.equals("0")) {
                                listaCandidatos.set(candidatoAModificar - 1, nuevaInformación);
                                System.out.println("Los datos del candidato han sido modificados.");
                            } else {
                                System.out.println("No se realizaron modificaciones.");
                            }
                        } else {
                            System.out.println("Número de candidato no válido.");
                        }
                    } else {
                        System.out.println("La contraseña ingresada no es válida. No se pueden realizar modificaciones.");
                    }
                    break;
                 case 3: // Eliminar candidatos
                           //Verificacion de la contraseña del administrador 
    System.out.println("Por favor, ingrese la contraseña de 16 caracteres para continuar:");
    contraseñaConfirmar = scan.nextLine();

    if (contraseñaConfirmar.length() == 16 && contraseñaConfirmar.equals(nuevaContraseña)) {
        System.out.println("Por favor, seleccione el número del candidato que desea eliminar (1, 2, 3, etc.):");

        // Mostrar la lista de candidatos
        for (int i = 0; i < listaCandidatos.size(); i++) {
            System.out.println("Candidato no." + (i + 1) + ": " + listaCandidatos.get(i));
        }

        int candidatoAEliminar = scan.nextInt();
        scan.nextLine(); // Consumir el salto de línea

        if (candidatoAEliminar > 0 && candidatoAEliminar <= listaCandidatos.size()) {
            listaCandidatos.remove(candidatoAEliminar - 1);
            System.out.println("Candidato eliminado exitosamente.");
        } else {
            System.out.println("Número de candidato no válido.");
        }
    } else {
        System.out.println("La contraseña ingresada no es válida. No se pueden realizar eliminaciones.");
    }
    break;
case 4: // Formar y Eliminar partidos políticos
    // Verificación de la contraseña del administrador
    ArrayList<String> listaPartidosPoliticos = new ArrayList<>();
    System.out.println("Por favor, ingrese la contraseña de 16 caracteres para continuar:\n");
    contraseñaConfirmar = scan.nextLine();

    if (contraseñaConfirmar.length() == 16 && contraseñaConfirmar.equals(nuevaContraseña)) {
        ArrayList<String> candidatosParaPartido = new ArrayList<>();
        int candidatoSeleccionado;
        String nuevoPartidoNombre;
        String nuevoPartidoInfo;
        // Selección de candidatos, para formar el partido político
        do {
            System.out.println("Seleccione a los candidatos para formar el partido político:");
            mostrarCandidatos(listaCandidatos);
            System.out.println("Ingrese el número del candidato que desea seleccionar (0 para finalizar):");
            candidatoSeleccionado = scan.nextInt();
            scan.nextLine(); // Consumir el salto de línea

            if (candidatoSeleccionado >= 1 && candidatoSeleccionado <= listaCandidatos.size()) {
                candidatosParaPartido.add(listaCandidatos.get(candidatoSeleccionado - 1));
            } else if (candidatoSeleccionado != 0) {
                System.out.println("Número de candidato no válido.");
            }
        } while (candidatoSeleccionado != 0);
        // Nombrar al partido creado y añadirle una descripción
        if (candidatosParaPartido.size() > 0) {
            System.out.println("Ingrese el nombre del nuevo partido político:");
            nuevoPartidoNombre = scan.nextLine();
            System.out.println("Proporcione información sobre el nuevo partido político:");
            nuevoPartidoInfo = scan.nextLine();

            // Comprueba si el partido ya existe en la lista de partidos
            boolean partidoExistente = false;
            for (String partido : listaPartidosPoliticos) {
                if (partido.startsWith(nuevoPartidoNombre)) {
                    partidoExistente = true;
                    break;
                }
            }

            if (!partidoExistente) {
                // Crear una cadena con los candidatos que conforman el partido
                StringBuilder candidatosPartido = new StringBuilder();
                for (String candidato : candidatosParaPartido) {
                    candidatosPartido.append(candidato).append("\n");
                }

                // Agregar la información del nuevo partido a la lista de partidos políticos
                String partidoNuevo = nuevoPartidoNombre + "\n" + nuevoPartidoInfo + "\n" + candidatosPartido.toString();
                listaPartidosPoliticos.add(partidoNuevo);

                // Guardar la información del partido en el archivo
                try (BufferedWriter partidoWriter = new BufferedWriter(new FileWriter("partidos.txt", true))) {
                    partidoWriter.write(partidoNuevo);
                    partidoWriter.newLine(); // Añade un salto de línea después de cada partido
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Partido político creado y guardado exitosamente.");
            } else {
                System.out.println("El partido político ya existe en la lista.");
            }
        } else {
            System.out.println("No se han seleccionado candidatos para formar el partido.");
        }
    } else {
        System.out.println("La contraseña ingresada no es válida. No se pueden formar/eliminar partidos políticos.");
    }
    break;  

case 5:
    // Salir de este bucle cuando el usuario selecciona "Volver al menú principal"
    return;
  }
        }
                  }
                    }
        }   
                             case 2:
                                System.out.println("Modificar Administrador");
                                System.out.println("Ingrese el CUI del usuario que desea modificar:");
                                int cuiAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el número de campo a modificar (2-13):");
                                int campoAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el nuevo valor:");
                                String nuevoValor = scan.nextLine();

                                if (campoAModificar >= 2 && campoAModificar <= 13) {
                                    // Lógica para modificar el archivo
                                    boolean campoModificado = modificarCampoAdmin(archivo, archivoCopia, cuiAModificar, campoAModificar, nuevoValor);
                                    if (campoModificado) {
                                        System.out.println("Campo modificado exitosamente.");
                                    } else {
                                        System.out.println("No se encontró ningún usuario con ese CUI.");
                                    }
                                } else {
                                    System.out.println("Número de campo no válido.");
                                }
                                break;

                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                        break;    
               case 2: // Auditor
    
    System.out.println("1. | Crear Auditor");
    System.out.println("2. | Modificar Auditor");
    int auditorOpcion = scan.nextInt();
    scan.nextLine(); // Limpiar el buffer del teclado

    switch (auditorOpcion) {
        case 1:
           System.out.println("INGRESE SUS DATOS COMO ADMINISTRADOR");
        System.out.println("1. | Ingrese Su CUI");
        cui = scan.nextInt();
        int cuiAudi = cui;
        scan.nextLine(); // Limpiar el buffer del teclado
        System.out.println("2. | Ingrese Sus Nombres");
        nombre = scan.nextLine();
        System.out.println("3. | Ingrese Sus Apellidos");
        ape = scan.nextLine();
        System.out.println("4. | Ingrese su contraseña");
        contraseña = scan.nextLine();
        System.out.println("5. | Ingrese Su Sexo");
        sex = scan.nextLine();
        System.out.println("6. | Ingrese Su Fecha De Nacimiento (Formato: YYYY-MM-DD)");
        fecha = scan.nextLine();
        System.out.println("7. | Ingrese Su Profesión");
        pro = scan.nextLine();
        System.out.println("8. | Ingrese Su Correo Electrónico");
        correo = scan.nextLine();
        System.out.println("9. | Agregue Su Residencia");
        resi = scan.nextLine();
        System.out.println("10. | Agregue Su Departamento");
        depa = scan.nextLine();
        System.out.println("11. | Agregue Su Municipio");
        muni = scan.nextLine();
        System.out.println("12. | Agregue Su País");
        pais = scan.nextLine(); // Consume la nueva línea
        System.out.println("13. | Ingrese Su Edad");
        edad = scan.nextInt();
        scan.nextLine(); // Consume la nueva línea después de la edad

         // Validación de edad
                    if (edad < 18) {
                        System.out.println("No puedes ingresar porque eres menor de edad.");
                    } else {
                        String tipoUsuario = "Administrador";
                        String line = cui + "|" + nombre + "|" + ape + "|" + contraseña + "|" + sex + "|" + fecha + "|" + pro + "|" + correo + "|" + resi + "|" + depa + "|" + muni + "|" + pais + "|" + edad + "|" + tipoUsuario;
                        bw.write(line + "\n");
                        copia.write(line + "\n");
                        System.out.println("Usuario creado exitosamente como " + tipoUsuario);

                        // Solicitar contraseña al usuario
                        System.out.println("Por favor, ingrese su contraseña para acceder:");
                        String contraseñaIngresada = scan.nextLine();

                  if (contraseñaIngresada.equals(contraseña)) {
    // Contraseña correcta para el administrador
    System.out.println("¡Bienvenido, " + nombre + "!");   
    // Generar nueva contraseña combinando CUI, nombre, apellido, contraseña y país
    nuevaContraseña = cui + nombre + ape + contraseña + pais;
    nuevaContraseña = nuevaContraseña.replaceAll("\\s", ""); // Eliminar espacios en blanco

    // Asegurarse de que la nueva contraseña tenga exactamente 16 caracteres
    if (nuevaContraseña.length() > 16) {
        nuevaContraseña = nuevaContraseña.substring(0, 16);
    } else if (nuevaContraseña.length() < 16) {
        while (nuevaContraseña.length() < 16) {
            char randomChar = (char) (Math.random() * 26 + 'a'); // Genera una letra aleatoria
            nuevaContraseña += randomChar;
        }
    }
    System.out.println("Tu nueva contraseña es: " + nuevaContraseña);
                // Mostrar los resultados de los votos
                if (!votos.isEmpty()) {
                    System.out.println("Resultados de la votación:");
                    for (String voto : votos) {
                        // Procesa cada voto y muestra la información
                        System.out.println("Voto: " + voto);
                    }
                } else {
                    System.out.println("No hay votos registrados todavía.");
                }
            } else {
                // Contraseña incorrecta
                System.out.println("Contraseña incorrecta. No puede acceder a los resultados.");
            }
            break;
    }
    break;
                            case 2:
                                System.out.println("Modificar Auditor");
                                System.out.println("Ingrese el CUI del usuario que desea modificar:");
                                int cuiAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el número de campo a modificar (2-13):");
                                int campoAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el nuevo valor:");
                                String nuevoValor = scan.nextLine();

                                if (campoAModificar >= 2 && campoAModificar <= 13) {
                                    // Lógica para modificar el archivo
                                    boolean campoModificado = modificarCampoAuditor(archivo, archivoCopia, cuiAModificar, campoAModificar, nuevoValor);
                                    if (campoModificado) {
                                        System.out.println("Campo modificado exitosamente.");
                                    } else {
                                        System.out.println("No se encontró ningún usuario con ese CUI.");
                                    }
                                } else {
                                    System.out.println("Número de campo no válido.");
                                }
                                break;

                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                        break;
             case 3:  // Votante
                       
                        System.out.println("1. | Crear Votante");
                        System.out.println("2. | Modificar Votante");
                        int votanteOpcion = scan.nextInt();
                        scan.nextLine(); // Limpiar el buffer del teclado

                        switch (votanteOpcion) {
                            case 1:
                                System.out.println("INGRESE SUS DATOS COMO VOTANTE");
                                System.out.println("1. | Ingrese Su CUI");
        cui = scan.nextInt();
        scan.nextLine(); // Limpiar el buffer del teclado
        System.out.println("2. | Ingrese Sus Nombres");
        nombre = scan.nextLine();
        System.out.println("3. | Ingrese Sus Apellidos");
        ape = scan.nextLine();
        System.out.println("4. | Ingrese su contraseña");
        contraseña = scan.nextLine();
        System.out.println("5. | Ingrese Su Sexo");
        sex = scan.nextLine();
        System.out.println("6. | Ingrese Su Fecha De Nacimiento (Formato: YYYY-MM-DD)");
        fecha = scan.nextLine();
        System.out.println("7. | Ingrese Su Profesión");
        pro = scan.nextLine();
        System.out.println("8. | Ingrese Su Correo Electrónico");
        correo = scan.nextLine();
        System.out.println("9. | Agregue Su Residencia");
        resi = scan.nextLine();
        System.out.println("10. | Agregue Su Departamento");
        depa = scan.nextLine();
        System.out.println("11. | Agregue Su Municipio");
        muni = scan.nextLine(); 
        System.out.println("12. | Agregue Su País");
        pais = scan.nextLine(); // Consume la nueva línea
        System.out.println("13. | Ingrese Su Edad");
        edad = scan.nextInt();
        scan.nextLine(); // Consume la nueva línea después de la edad

     // Validación de edad
                    if (edad < 18) {
                        System.out.println("No puedes ingresar porque eres menor de edad.");
                    } else {
                        String tipoUsuario = "Votante";
                        String line = cui + "|" + nombre + "|" + ape + "|" + contraseña + "|" + sex + "|" + fecha + "|" + pro + "|" + correo + "|" + resi + "|" + depa + "|" + muni + "|" + pais + "|" + edad + "|" + tipoUsuario;
                        bw.write(line + "\n");
                        copia.write(line + "\n");
                        System.out.println("Usuario creado exitosamente como " + tipoUsuario);
            // Solicitar contraseña al usuario
                        System.out.println("Por favor, ingrese su contraseña para acceder:");
                        String contraseñaIngresada = scan.nextLine();

                  if (contraseñaIngresada.equals(contraseña)) {
    // Contraseña correcta para el administrador
    System.out.println("¡Bienvenido, " + nombre + "!");   
    // Generar nueva contraseña combinando CUI, nombre, apellido, contraseña y país
    nuevaContraseña = cui + nombre + ape + contraseña + pais;
    nuevaContraseña = nuevaContraseña.replaceAll("\\s", ""); // Eliminar espacios en blanco

    // Asegurarse de que la nueva contraseña tenga exactamente 16 caracteres
    if (nuevaContraseña.length() > 16) {
        nuevaContraseña = nuevaContraseña.substring(0, 16);
    } else if (nuevaContraseña.length() < 16) {
        while (nuevaContraseña.length() < 16) {
            char randomChar = (char) (Math.random() * 26 + 'a'); // Genera una letra aleatoria
            nuevaContraseña += randomChar;
        }
    }
    System.out.println("Tu nueva contraseña es: " + nuevaContraseña);
   
     ArrayList<String> candidatos = new ArrayList<>();
        ArrayList<String> votos = new ArrayList<>();
        ArrayList<String> partidos = cargarPartidosDesdeArchivo("partidos.txt");
        // Logica para Votar por los partidos politicos
        System.out.println("¡BIENVENIDO AL SISTEMA DE VOTACIÓN!");
        System.out.println("Decida por quién votar\n");

        mostrarPartidosConCandidatos(partidos, candidatos);
        System.out.println("Por favor, elija el número del partido al que desea votar (o Q para salir):");
        String voto = scan.nextLine();

        if (voto.equalsIgnoreCase("Q")) {
            System.out.println("Gracias por participar en la votación.");
        } else {
            int votoInt = Integer.parseInt(voto);
            if (votoInt >= 1 && votoInt <= partidos.size()) {
                String partidoElegido = partidos.get(votoInt - 1);
                System.out.println("Ha votado por el partido: " + partidoElegido);
                votos.add(partidoElegido); // Almacenar el voto
            } 
        }
    }
            // Volver al menú principal
            break;        
        }
                            case 2:
                                System.out.println("Modificar Votante");
                                System.out.println("Ingrese el CUI del usuario que desea modificar:");
                                int cuiAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el número de campo a modificar (2-13):");
                                int campoAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el nuevo valor:");
                                String nuevoValor = scan.nextLine();

                                if (campoAModificar >= 2 && campoAModificar <= 13) {
                                    // Lógica para modificar el archivo
                                    boolean campoModificado = modificarCampoVotante(archivo, archivoCopia, cuiAModificar, campoAModificar, nuevoValor);
                                    if (campoModificado) {
                                        System.out.println("Campo modificado exitosamente.");
                                    } else {
                                        System.out.println("No se encontró ningún usuario con ese CUI.");
                                    }
                                } else {
                                    System.out.println("Número de campo no válido.");
                                }
                                break;

                            default:
                                System.out.println("Opción no válida.");
                                
                             }    
                                break;

                    case 4: // Registrador de Votantes
                        
                        System.out.println("1. | Crear Registrador de Votantes");
                        System.out.println("2. | Modificar Registrador de Votantes");
                        int registradorOpcion = scan.nextInt();
                        scan.nextLine(); // Limpiar el buffer del teclado

                        switch (registradorOpcion) {
                            case 1:
                                System.out.println("INGRESE SUS DATOS COMO REGISTRADOR DE VOTANTES");
                                System.out.println("1. | Ingrese Su CUI");
        cui = scan.nextInt();
        scan.nextLine(); // Limpiar el buffer del teclado
        System.out.println("2. | Ingrese Sus Nombres");
        nombre = scan.nextLine();
        System.out.println("3. | Ingrese Sus Apellidos");
        ape = scan.nextLine();
        System.out.println("4. | Ingrese su contraseña");
        contraseña = scan.nextLine();
        System.out.println("5. | Ingrese Su Sexo");
        sex = scan.nextLine();
        System.out.println("6. | Ingrese Su Fecha De Nacimiento (Formato: YYYY-MM-DD)");
        fecha = scan.nextLine();
        System.out.println("7. | Ingrese Su Profesión");
        pro = scan.nextLine();
        System.out.println("8. | Ingrese Su Correo Electrónico");
        correo = scan.nextLine();
        System.out.println("9. | Agregue Su Residencia");
        resi = scan.nextLine();
        System.out.println("10. | Agregue Su Departamento");
        depa = scan.nextLine();
        System.out.println("11. | Agregue Su Municipio");
        muni = scan.nextLine(); 
        System.out.println("12. | Agregue Su País");
        pais = scan.nextLine(); // Consume la nueva línea
        System.out.println("13. | Ingrese Su Edad");
        edad = scan.nextInt();
        scan.nextLine(); // Consume la nueva línea después de la edad

       // Validación de edad
                    if (edad < 18) {
                        System.out.println("No puedes ingresar porque eres menor de edad.");
                    } else {
                        String tipoUsuario = "Registrador de Votantes";
                        String line = cui + "|" + nombre + "|" + ape + "|" + contraseña + "|" + sex + "|" + fecha + "|" + pro + "|" + correo + "|" + resi + "|" + depa + "|" + muni + "|" + pais + "|" + edad + "|" + tipoUsuario;
                        bw.write(line + "\n");
                        copia.write(line + "\n");
                        System.out.println("Usuario creado exitosamente como " + tipoUsuario);

            // Solicitar contraseña al usuario
            System.out.println("Por favor, ingrese su contraseña para acceder:");
            String contraseñaIngresada = scan.nextLine();

            // Verificar contraseña
            if (contraseñaIngresada.equals(contraseña)) {
                // Contraseña correcta
                System.out.println("¡Bienvenido, " + nombre + "!");
                // Generar nueva contraseña combinando CUI, nombre, apellido, contraseña y país
    nuevaContraseña = cui + nombre + ape + contraseña + pais;
    nuevaContraseña = nuevaContraseña.replaceAll("\\s", ""); // Eliminar espacios en blanco

    // Asegurarse de que la nueva contraseña tenga exactamente 16 caracteres
    if (nuevaContraseña.length() > 16) {
        nuevaContraseña = nuevaContraseña.substring(0, 16);
    } else if (nuevaContraseña.length() < 16) {
        while (nuevaContraseña.length() < 16) {
            char randomChar = (char) (Math.random() * 26 + 'a'); // Genera una letra aleatoria
            nuevaContraseña += randomChar;
        }
    }
    System.out.println("Tu nueva contraseña es: " + nuevaContraseña);
            } else {
                // Contraseña incorrecta
                System.out.println("Contraseña incorrecta. No puede acceder a las actividades.");
            }
        }
        break;

                            case 2:
                                System.out.println("Modificar Registrador de Votantes");
                                System.out.println("Ingrese el CUI del usuario que desea modificar:");
                                int cuiAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el número de campo a modificar (2-13):");
                                int campoAModificar = scan.nextInt();
                                scan.nextLine(); // Limpiar el buffer del teclado

                                System.out.println("Ingrese el nuevo valor:");
                                String nuevoValor = scan.nextLine();

                                if (campoAModificar >= 2 && campoAModificar <= 13) {
                                    // Lógica para modificar el archivo
                                    boolean campoModificado = modificarCampoRegistrador(archivo, archivoCopia, cuiAModificar, campoAModificar, nuevoValor);
                                    if (campoModificado) {
                                        System.out.println("Campo modificado exitosamente.");
                                    } else {
                                        System.out.println("No se encontró ningún usuario con ese CUI.");
                                    }
                                } else {
                                    System.out.println("Número de campo no válido.");
                                }
                                break;

                            default:
                                System.out.println("Opción no válida.");
                                break;
                        }
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
case 5:
    // Eliminar Usuario
    System.out.println("Eliminar Usuario");

    // Solicitar la contraseña del registrador de votantes
    System.out.println("Ingrese la contraseña de 16 caracteres del Registrador de Votantes:");
    String ContraseñaRegistrador = scan.nextLine();

    // Verificar la contraseña del registrador de votantes
    if (ContraseñaRegistrador.equals(nuevaContraseña)) {
        System.out.println("Contraseña correcta. Por favor, continúe.");

        System.out.println("Ingrese el CUI del usuario que desea eliminar:");
        int cuiUsuarioAEliminar = scan.nextInt();
        scan.nextLine(); // Limpiar el buffer del teclado
// funsion que buscara al usuario que se desee eliminar 
        boolean usuarioEliminado = eliminarUsuarioPorCUI(archivo, archivoCopia, cuiUsuarioAEliminar);

        if (usuarioEliminado) {
            System.out.println("Usuario eliminado exitosamente.");
        } else {
            System.out.println("No se encontró ningún usuario con ese CUI.");
        }
    } else {
        System.out.println("Contraseña incorrecta. No puede eliminar un usuario.");
    }
    break;
         case 6: // Registrar Fallecimiento
    
    System.out.println("Registrar Fallecimiento");

    // Solicitar la contraseña del registrador de votantes
    System.out.println("Ingrese la contraseña de 16 caracteres del Registrador de Votantes:");
    String contraseñaRegistrador = scan.nextLine();

    // Verificar la contraseña del registrador de votantes
    if (contraseñaRegistrador.equals(nuevaContraseña)) {
        System.out.println("Contraseña correcta. Por favor, continúe.");
        
        System.out.println("Ingrese el CUI del usuario que falleció:");
        int cuiFallecido = scan.nextInt();
        scan.nextLine(); // Limpiar el buffer del teclado

        System.out.println("Ingrese la fecha de fallecimiento (YYYY-MM-DD):");
        String fechaFallecimiento = scan.nextLine();
// Buscara al usuario que fallecio en ambos archivos
        boolean fallecimientoRegistrado = registrarFallecimiento(archivo, archivoCopia, cuiFallecido, fechaFallecimiento);

        if (fallecimientoRegistrado) {
            System.out.println("Fallecimiento registrado exitosamente.");
        } else {
            System.out.println("No se encontró ningún usuario con ese CUI.");
        }
    } else {
        System.out.println("Contraseña incorrecta. No puede registrar un fallecimiento.");
    }
    break;

 case 7: // Configurar Horario de Votación como Administrador

    System.out.println("Configurar Votación como Administrador");
    System.out.println("¡Bienvenido, Administrador!");

    String startDate;
    while (true) {
        // Solicitar y validar la fecha de inicio de la votación
        System.out.println("Por favor, ingrese la fecha de inicio de la votación (dd/MM/yyyy):");
        startDate = scan.nextLine();

        if (isValidDate(startDate)) {
            break; // Fecha válida, salir del bucle
        } else {
            System.out.println("Formato de fecha incorrecto. Intente de nuevo.");
        }
    }

    String startTime;
    while (true) {
        // Solicitar y validar la hora de inicio de la votación
        System.out.println("Por favor, ingrese la hora de inicio de la votación (hh:mm a):");
        startTime = scan.nextLine();

        if (isValidTime(startTime)) {
            break; // Hora válida, salir del bucle
        } else {
            System.out.println("Formato de hora incorrecto. Intente de nuevo.");
        }
    }

    final String finalStartDate = startDate; // Variable final para usar dentro de la función lambda
    final String finalStartTime = startTime; // Variable final para usar dentro de la función lambda

    int durationMinutes;
    while (true) {
        // Solicitar y validar la duración de la votación en minutos
        System.out.println("Por favor, ingrese la duración de la votación en minutos:");
        try {
            durationMinutes = Integer.parseInt(scan.nextLine());
            break; // Valor válido, salir del bucle
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un número válido.");
        }
    }

    String startDateTime = startDate + " " + startTime;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a", Locale.US);
    LocalDateTime startTimeParsed = LocalDateTime.parse(startDateTime, formatter);

    LocalDateTime endTime = startTimeParsed.plusMinutes(durationMinutes); // Sumar minutos al tiempo de inicio

    System.out.println("Fecha de inicio de la votación establecida: " + startTimeParsed.format(formatter));
    System.out.println("Duración de la votación: " + durationMinutes + " minutos \n");

    // Configurar el cronómetro para finalizar la votación
    try {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            System.out.println("¡La votación ha terminado!");

            // Mostrar la fecha y hora de finalización
            System.out.println("Finalizó el: " + endTime.format(formatter));

            // Volver al menú principal
            mostrarMenuPrincipal();
            executorService.shutdown();
        }, durationMinutes, TimeUnit.MINUTES);
    } catch (Exception e) {
        System.out.println("Error al configurar el cronómetro.");
    }
    break;
               }
               }

  } // ACABA EL CICLO WHILE   
            }
        }
    
    } // AQUI TERMINA PUBLIC ESTATIC VOID MAID

   // Métodos para modificar campos específicos según el tipo de usuario
private static boolean modificarCampoAdmin(File archivo, File archivoCopia, int cuiAModificar, int campoAModificar, String nuevoValor) {
    try {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(archivoCopia, true);
        BufferedWriter copia = new BufferedWriter(fw);

        String linea;
        boolean usuarioEncontrado = false;
        ArrayList<String> lineasModificadas = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            if (!linea.startsWith("CUI")) {
                String[] datos = linea.split("\\|");
                if (datos.length == 14) {
                    int cui = Integer.parseInt(datos[0]);
                    if (cui == cuiAModificar) {
                        usuarioEncontrado = true;
                        datos[campoAModificar - 1] = nuevoValor;
                        lineasModificadas.add(String.join("|", datos));
                    } else {
                        copia.write(linea + "\n");
                    }
                }
            } else {
                copia.write(linea);
                copia.newLine();
            }
        }

        br.close();

        if (usuarioEncontrado) {
            for (String modifiedLine : lineasModificadas) {
                copia.write(modifiedLine + "\n");
            }
            copia.close();

            Files.move(archivoCopia.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return true;
        } else {
            copia.close();
            return false;
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        return false;
    }
}

private static boolean modificarCampoAuditor(File archivo, File archivoCopia, int cuiAModificar, int campoAModificar, String nuevoValor) {
    try {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(archivoCopia, true);
        BufferedWriter copia = new BufferedWriter(fw);

        String linea;
        boolean usuarioEncontrado = false;
        ArrayList<String> lineasModificadas = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            if (!linea.startsWith("CUI")) {
                String[] datos = linea.split("\\|");
                if (datos.length == 14) {
                    int cui = Integer.parseInt(datos[0]);
                    if (cui == cuiAModificar) {
                        usuarioEncontrado = true;
                        datos[campoAModificar - 1] = nuevoValor;
                        lineasModificadas.add(String.join("|", datos));
                    } else {
                        copia.write(linea + "\n");
                    }
                }
            } else {
                copia.write(linea);
                copia.newLine();
            }
        }

        br.close();

        if (usuarioEncontrado) {
            for (String modifiedLine : lineasModificadas) {
                copia.write(modifiedLine + "\n");
            }
            copia.close();

            Files.move(archivoCopia.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return true;
        } else {
            copia.close();
            return false;
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        return false;
    }
}

private static boolean modificarCampoVotante(File archivo, File archivoCopia, int cuiAModificar, int campoAModificar, String nuevoValor) {
     try {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(archivoCopia, true);
        BufferedWriter copia = new BufferedWriter(fw);

        String linea;
        boolean usuarioEncontrado = false;
        ArrayList<String> lineasModificadas = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            if (!linea.startsWith("CUI")) {
                String[] datos = linea.split("\\|");
                if (datos.length == 14) {
                    int cui = Integer.parseInt(datos[0]);
                    if (cui == cuiAModificar) {
                        usuarioEncontrado = true;
                        datos[campoAModificar - 1] = nuevoValor;
                        lineasModificadas.add(String.join("|", datos));
                    } else {
                        copia.write(linea + "\n");
                    }
                }
            } else {
                copia.write(linea);
                copia.newLine();
            }
        }

        br.close();

        if (usuarioEncontrado) {
            for (String modifiedLine : lineasModificadas) {
                copia.write(modifiedLine + "\n");
            }
            copia.close();

            Files.move(archivoCopia.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return true;
        } else {
            copia.close();
            return false;
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        return false;
    }
}

private static boolean modificarCampoRegistrador(File archivo, File archivoCopia, int cuiAModificar, int campoAModificar, String nuevoValor) {
     try {
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(archivoCopia, true);
        BufferedWriter copia = new BufferedWriter(fw);

        String linea;
        boolean usuarioEncontrado = false;
        ArrayList<String> lineasModificadas = new ArrayList<>();

        while ((linea = br.readLine()) != null) {
            if (!linea.startsWith("CUI")) {
                String[] datos = linea.split("\\|");
                if (datos.length == 14) {
                    int cui = Integer.parseInt(datos[0]);
                    if (cui == cuiAModificar) {
                        usuarioEncontrado = true;
                        datos[campoAModificar - 1] = nuevoValor;
                        lineasModificadas.add(String.join("|", datos));
                    } else {
                        copia.write(linea + "\n");
                    }
                }
            } else {
                copia.write(linea);
                copia.newLine();
            }
        }

        br.close();

        if (usuarioEncontrado) {
            for (String modifiedLine : lineasModificadas) {
                copia.write(modifiedLine + "\n");
            }
            copia.close();

            Files.move(archivoCopia.toPath(), archivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return true;
        } else {
            copia.close();
            return false;
        }
    } catch (IOException ex) {
        ex.printStackTrace();
        return false;
    }
}
// Función para eliminar un usuario por CUI
public static boolean registrarFallecimiento(File archivoOriginal, File archivoCopia, int cuiFallecido, String fechaFallecimiento) {
    // Crear listas para almacenar líneas del archivo original y copia
    List<String> lineasOriginales = new ArrayList<>();
    List<String> lineasCopia = new ArrayList<>();

    try (BufferedReader lectorOriginal = new BufferedReader(new FileReader(archivoOriginal));
         BufferedReader lectorCopia = new BufferedReader(new FileReader(archivoCopia))) {
        String linea;
        // Leer y almacenar todas las líneas del archivo original
        while ((linea = lectorOriginal.readLine()) != null) {
            lineasOriginales.add(linea);
        }

        // Leer y almacenar todas las líneas del archivo copia
        while ((linea = lectorCopia.readLine()) != null) {
            lineasCopia.add(linea);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false; // Error al leer los archivos
    }

    // Buscar el usuario con el CUI proporcionado en ambas listas
    int indiceFallecido = -1;
    for (int i = 1; i < lineasOriginales.size(); i++) {
        String[] campos = lineasOriginales.get(i).split("\\|");
        int cui = Integer.parseInt(campos[0]);
        if (cui == cuiFallecido) {
            indiceFallecido = i;
            break;
        }
    }

    // Si se encontró el usuario, actualizar la información de fallecimiento
    if (indiceFallecido != -1) {
        String lineaFallecido = lineasOriginales.get(indiceFallecido);
        String nuevaLineaFallecido = lineaFallecido + "|Falleció el " + fechaFallecimiento + ".";

        // Actualizar la línea en ambas listas
        lineasOriginales.set(indiceFallecido, nuevaLineaFallecido);
        lineasCopia.set(indiceFallecido, nuevaLineaFallecido);

        // Actualizar el archivo original con las líneas restantes
        try (BufferedWriter escritorOriginal = new BufferedWriter(new FileWriter(archivoOriginal));
             BufferedWriter escritorCopia = new BufferedWriter(new FileWriter(archivoCopia))) {
            // Escribir las líneas actualizadas en el archivo original
            for (String linea : lineasOriginales) {
                escritorOriginal.write(linea + "\n");
            }

            // Escribir las líneas actualizadas en el archivo copia
            for (String linea : lineasCopia) {
                escritorCopia.write(linea + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error al escribir los archivos
        }

        return true; // Información de fallecimiento actualizada exitosamente
    }

    return false; // No se encontró el usuario con el CUI proporcionado
}

    private static boolean verificarContraseña(int cuiUsuarioAEliminar, String contraseña, File archivo) {
    try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = lector.readLine()) != null) {
            if (!linea.startsWith("CUI")) {
                String[] datos = linea.split("\\|");
                if (datos.length == 14) {
                    int cui = Integer.parseInt(datos[0]);
                    String contraseñaAlmacenada = datos[3]; // Suponiendo que la contraseña está en la cuarta posición

                    if (cui == cuiUsuarioAEliminar && contraseña.equals(contraseñaAlmacenada)) {
                        return true; // La contraseña es correcta
                    }
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return false; // Usuario no encontrado o contraseña incorrecta
}
public static boolean eliminarUsuarioPorCUI(File archivoOriginal, File archivoCopia, int cuiUsuarioAEliminar) {
    // Crear listas para almacenar líneas del archivo original y copia
    List<String> lineasOriginales = new ArrayList<>();
    List<String> lineasCopia = new ArrayList<>();

    try (BufferedReader lectorOriginal = new BufferedReader(new FileReader(archivoOriginal));
         BufferedReader lectorCopia = new BufferedReader(new FileReader(archivoCopia))) {
        String linea;
        // Leer y almacenar todas las líneas del archivo original
        while ((linea = lectorOriginal.readLine()) != null) {
            lineasOriginales.add(linea);
        }

        // Leer y almacenar todas las líneas del archivo copia
        while ((linea = lectorCopia.readLine()) != null) {
            lineasCopia.add(linea);
        }
    } catch (IOException e) {
        e.printStackTrace();
        return false; // Error al leer los archivos
    }

    // Buscar el usuario con el CUI proporcionado en ambas listas
    int indiceUsuarioAEliminar = -1;
    for (int i = 1; i < lineasOriginales.size(); i++) {
        String[] campos = lineasOriginales.get(i).split("\\|");
        int cui = Integer.parseInt(campos[0]);
        if (cui == cuiUsuarioAEliminar) {
            indiceUsuarioAEliminar = i;
            break;
        }
    }

    // Si se encontró el usuario, eliminarlo de ambas listas
    if (indiceUsuarioAEliminar != -1) {
        lineasOriginales.remove(indiceUsuarioAEliminar);
        lineasCopia.remove(indiceUsuarioAEliminar);

        // Actualizar el archivo original con las líneas restantes
        try (BufferedWriter escritorOriginal = new BufferedWriter(new FileWriter(archivoOriginal));
             BufferedWriter escritorCopia = new BufferedWriter(new FileWriter(archivoCopia))) {
            // Escribir las líneas actualizadas en el archivo original
            for (String linea : lineasOriginales) {
                escritorOriginal.write(linea + "\n");
            }

            // Escribir las líneas actualizadas en el archivo copia
            for (String linea : lineasCopia) {
                escritorCopia.write(linea + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Error al escribir los archivos
        }

        return true; // Usuario eliminado exitosamente
    }

    return false; // No se encontró el usuario con el CUI proporcionado
}

// Función para validar el formato de fecha (dd/MM/yyyy)
private static boolean isValidDate(String date) {
    return date.matches("\\d{2}/\\d{2}/\\d{4}");
}

// Función para validar el formato de hora (hh:mm a)
private static boolean isValidTime(String time) {
    return time.matches("\\d{2}:\\d{2} [APap][Mm]");
}

 // Función para mostrar el menú principal
    public static void mostrarMenuPrincipal() {
        System.out.println("REGISTRO DE USUARIO");
        System.out.println("Seleccione su tipo de usuario:");
        System.out.println("1. Administrador");
        System.out.println("2. Auditor");
        System.out.println("3. Votante");
        System.out.println("4. Registrador de Votantes");
        System.out.println("5. Eliminar Usuario");
        System.out.println("6. Registrar Fallecimiento");
        System.out.println("7. Salir del Programa");
        System.out.print("Seleccione una opción: ");
    }
    // Funcion para mostrar a los candidatos
public static void mostrarCandidatos(ArrayList<String> listaCandidatos) {
    System.out.println("LISTADO DE CANDIDATOS:");
    for (int i = 0; i < listaCandidatos.size(); i++) {
        System.out.println("Candidato no." + (i + 1) + ":");
        System.out.println(listaCandidatos.get(i) + "\n");
    }
}
// Método para cargar partidos políticos desde el archivo
    public static ArrayList<String> cargarPartidosDesdeArchivo(String nombreArchivo) {
        ArrayList<String> partidos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            StringBuilder partidoActual = new StringBuilder();
            while ((linea = br.readLine()) != null) {
                if (linea.isEmpty()) {
                    partidos.add(partidoActual.toString());
                    partidoActual.setLength(0); // Limpiar el StringBuilder
                } else {
                    partidoActual.append(linea).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return partidos;
    }

   public static void mostrarPartidosConCandidatos(ArrayList<String> partidos, ArrayList<String> candidatos) {
        int numeroPartido = 1;
        for (String partido : partidos) {
            System.out.println("Partido " + numeroPartido + ": " + partido);
            numeroPartido++;
        }
        int numeroCandidato = 1;
        for (String candidato : candidatos) {
            System.out.println(numeroCandidato + ". " + candidato);
            numeroCandidato++;
        }
   }
public static ArrayList<String> cargarPartidosDesdearchivo(String nombreArchivo) {
    ArrayList<String> partidos = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
        String linea;
        while ((linea = br.readLine()) != null) {
            partidos.add(linea);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    
    return partidos;
}


} //AQUI TERMINA EL PROGRAMA
