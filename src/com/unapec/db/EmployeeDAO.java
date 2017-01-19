/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unapec.db;

import com.unapec.entities.Employee;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Jeremy Lopez
 */
public class EmployeeDAO {
    
    private final DbContext db = new DbContext("localhost", "orcl", "1521", "iatos" , "iatos");        
    
    public ArrayList<Employee> getEmployees() throws ClassNotFoundException, SQLException {        
        Connection connection = db.connect();
        ArrayList<Employee> employees = null;
                
        if(connection != null) {
            ResultSet result = db.getData("SELECT * FROM EMPLEADOS");
            employees = new ArrayList<>();
            
            while(result.next()) {
                Employee employee = new Employee();
                employee.setCedula(result.getString("CEDULA_EMPLEADO"));
                employee.setNombre(result.getString("NOMBRE"));
                employee.setApellido(result.getString("APELLIDO"));
                employee.setFechaNacimiento(result.getString("FECHA_NACIMIENTO"));
                employee.setTipoNomina(result.getString("TIPO_NOMINA"));
                employee.setFechaIngreso(result.getDate("FECHA_INGRESO"));
                employee.setSueldo(result.getString("SUELDO"));
                
                employees.add(employee);
            }
        }
        connection.close();
        return employees;
    }
    
    public String getLote() throws ClassNotFoundException, SQLException {        
        Connection connection = db.connect();
        String lote = null;
                
        if(connection != null) {
            ResultSet result = db.getData("SELECT LOTE_ARCHIVO_UNAPEC.nextval LOTE FROM DUAL");
            lote = new String();
            
            while(result.next()) {
                lote = result.getString("LOTE");
            }
        }
        connection.close();
        return lote;
    }
    
    public void insertPaysheetFile(String lote, String fechaGeneracion, String fechaPago, String user) throws SQLException, ClassNotFoundException {
        Connection connection = db.connect();
        String sql = "INSERT INTO ARCHIVOS_PAGO_NOMINA (LOTE_ARCHIVO, FECHA_GENERACION, FECHA_PAGO_NOMINA, USUARIO, ESTADO) VALUES ("+ lote+", TO_DATE('"+fechaGeneracion+"', 'dd/mm/yyyy'),TO_DATE('"+fechaPago+"', 'dd/mm/yyyy') , '"+ user+"', 'N')";
   
        int valor = db.insertData(sql);
    
        
        connection.close();
    }
    
    public void insertEmployeePaysheet(String cedula, String mesAplicacion, String monto, String lote) throws SQLException, ClassNotFoundException {
        Connection connection = db.connect();
        String sql = "INSERT INTO NOMINAS_EMPLEADOS (CEDULA_EMPLEADO, MES_APLICACION, MONTO_A_PAGAR, LOTE_ARCHIVO) Values ('"+cedula+"', '"+mesAplicacion+"', '"+monto+"', '"+lote+"')";
        System.out.println(sql);
        int valor = db.insertData(sql);
        
        connection.close();
    }
}
