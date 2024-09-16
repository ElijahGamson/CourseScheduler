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
public class MultiTableQueries {
    private static Connection connection;
    private static PreparedStatement getDescriptionList;
    private static PreparedStatement getScheduledStudents;
    private static PreparedStatement getWaitlistedStudents;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> descriptions = new ArrayList<ClassDescription>();
        try
        {
            getDescriptionList = connection.prepareStatement("select app.classentry.courseCode, description, seats from app.classentry, app.course where semester = ? and app.classentry.courseCode = app.course.courseCode order by app.classentry.courseCode");
            getDescriptionList.setString(1,semester);
            resultSet = getDescriptionList.executeQuery();
            
            while(resultSet.next())
            {
                ClassDescription newDescription = new ClassDescription(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
                descriptions.add(newDescription);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return descriptions;
        
    }
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> scheduledStudents = new ArrayList<StudentEntry>();
        try
        {
            getScheduledStudents = connection.prepareStatement("select app.schedule.studentid, firstname, lastname from app.student, app.schedule where app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = 'S' and app.schedule.studentid = app.student.studentid order by app.schedule.timestamp");
            getScheduledStudents.setString(1, semester);
            getScheduledStudents.setString(2, courseCode);
            resultSet = getScheduledStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry scheduled = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                scheduledStudents.add(scheduled);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudents;
        
    }
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> waitlistedStudents = new ArrayList<StudentEntry>();
        try
        {
            getWaitlistedStudents = connection.prepareStatement("select app.schedule.studentid, firstname, lastname from app.student, app.schedule where app.schedule.semester = ? and app.schedule.coursecode = ? and app.schedule.status = 'W' and app.schedule.studentid = app.student.studentid order by app.schedule.timestamp");
            getWaitlistedStudents.setString(1, semester);
            getWaitlistedStudents.setString(2, courseCode);
            resultSet = getWaitlistedStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry waitlisted = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                waitlistedStudents.add(waitlisted);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
        
    }
}