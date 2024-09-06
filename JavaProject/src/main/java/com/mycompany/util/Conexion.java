/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author hernan
 */
public class Conexion {

    private static Conexion instancia = null;
    private final Propiedades properties = new Propiedades();

    private Conexion() {
    }

    public static synchronized Conexion getInstance() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection conectar() throws SQLException {  
        Connection conexion = null;
        try {
            Properties propiedades = properties.cargarArchivoProperties();
            String user = propiedades.getProperty("conexion.user");
            String password = propiedades.getProperty("conexion.password");
            String url = propiedades.getProperty("conexion.url");
            
            conexion = DriverManager.getConnection(url, user, password);

            if (conexion != null) {
                System.out.println("Conexión exitosa");
            } else {
                System.err.println("Error en la conexión");
            }

        } catch (IOException error) {
            System.err.println("Error al cargar el archivo de propiedades: " + error.getMessage());
            throw new SQLException("No se pudo conectar a la base de datos", error);
        } 
        
        return conexion;  
    }

    public void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
