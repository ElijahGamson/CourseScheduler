/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author egams
 */
public class ClassQueries {
    private static Connection connection;
    private static PreparedStatement addClass;
    private static PreparedStatement getClassList;
    private static PreparedStatement getNumSeats;
    private static PreparedStatement dropClass;
    private static int num_seats;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry classentry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.classentry (semester, coursecode, seats) values (?, ?, ?)");//column to pull from in paratheses
            addClass.setString(1, classentry.getSemester());
            addClass.setString(2, classentry.getCourseCode());
            addClass.setInt(3, classentry.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> classes = new ArrayList<String>();
        try
        {
            getClassList = connection.prepareStatement("select coursecode from app.classentry where semester = ? order by coursecode");
            getClassList.setString(1, semester);
            resultSet = getClassList.executeQuery();
            
            while(resultSet.next())
            {
                classes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classes;
        
    } 
    
    public static int getClassSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        num_seats = 0;
        try
        {
            getNumSeats = connection.prepareStatement("select semester, coursecode, seats from app.classentry where semester = ? and coursecode = ?");
            getNumSeats.setString(1, semester);
            getNumSeats.setString(2, courseCode);
            resultSet = getNumSeats.executeQuery();
            
            while(resultSet.next())
            {
                    num_seats = resultSet.getInt(3);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return num_seats;
        
    }
    
    public static void dropClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropClass = connection.prepareStatement("delete from app.classentry where semester = ? and coursecode = ?");
            dropClass.setString(1, semester);
            dropClass.setString(2, courseCode);
            dropClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
}