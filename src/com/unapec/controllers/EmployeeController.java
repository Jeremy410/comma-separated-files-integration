    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unapec.controllers;

import com.unapec.db.DbContext;
import com.unapec.db.EmployeeDAO;
import com.unapec.entities.Employee;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jeremy Lopez
 */
public class EmployeeController {   
    
    EmployeeDAO dao = new EmployeeDAO();
    
    public void createEmployeePaysheet(String cuentaDebito, Date fechaPago, Date mesAplicacion) throws ClassNotFoundException, SQLException, IOException, ParseException {
        ArrayList<Employee> employees = dao.getEmployees();
        String lote  = dao.getLote();
        int montoTotal = 0;
        int cantidadRegistros = 0;
        
        try{
            PrintWriter writer = new PrintWriter("employees-paysheet.txt", "UTF-8");
            // ENCABEZADO
            writer.println("E,01,21," + cuentaDebito + "," + getDate(fechaPago, null) + "," + getDate(null, null) + "," + lote);
             dao.insertPaysheetFile(lote, getDate(null, null), getDate(fechaPago, null), "USER");
            // DETALLE
            for (Employee employee : employees) {
                writer.println("D" + employee.getTipoNomina() + ","+ employee.getCedula() + "," +  getDate(employee.getFechaIngreso(), null) + "," + employee.getSueldo());
                dao.insertEmployeePaysheet(employee.getCedula(),  getDate(mesAplicacion, "MMyyyy")  , employee.getSueldo(), lote);
                montoTotal =+ Integer.parseInt(employee.getSueldo());
                cantidadRegistros++;
            }
            // SUMARIO
            writer.println("S," + montoTotal + "," + cantidadRegistros);          
            
            writer.close();
        } catch (IOException e) {
           
        }        
    }
    
    public String getDate(Date paramDate, String format) throws ParseException {
        String basicFormat = format == null ? "dd/MM/yyyy" : format;
        DateFormat dateFormat = new SimpleDateFormat(basicFormat);
        Date date;
                
        if(paramDate == null) {
            date = new Date();
        }
        else {     
            date = paramDate;
        }
        
        return dateFormat.format(date);
    }  
}