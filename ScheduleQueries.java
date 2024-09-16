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
 public class ScheduleQueries {
     private static Connection connection;
     private static PreparedStatement addSchedule;
     private static PreparedStatement getStudentSchedule;
     private static PreparedStatement getScheduledCount;
     private static PreparedStatement getWaitlistedStudents;
     private static PreparedStatement dropScheduleByCourse;
     private static PreparedStatement dropStudentSchedule;
     private static PreparedStatement updateSchedule;
     private static int count;
     private static ResultSet resultSet;
     
     public static void addScheduleEntry(ScheduleEntry schedule)
     {
         connection = DBConnection.getConnection();
         try
         {
             addSchedule = connection.prepareStatement("insert into app.schedule (semester, coursecode, studentid, status, timestamp) values (?, ?, ?, ?, ?)");//column to pull from in paratheses
             addSchedule.setString(1, schedule.getSemester());
             addSchedule.setString(2, schedule.getCourseCode());
             addSchedule.setString(3, schedule.getStudentID());
             addSchedule.setString(4, schedule.getStatus());
             addSchedule.setTimestamp(5, schedule.getTimestamp());
             addSchedule.executeUpdate();
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         
     }
     
     public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
     {
         connection = DBConnection.getConnection();
         ArrayList<ScheduleEntry> studentSchedules = new ArrayList<ScheduleEntry>();
         try
         {
             getStudentSchedule = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and studentid = ?");
             getStudentSchedule.setString(1, semester);
             getStudentSchedule.setString(2, studentID);
             resultSet = getStudentSchedule.executeQuery();
             
             while(resultSet.next())
             {
                 ScheduleEntry schedule = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                 studentSchedules.add(schedule);
             }
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         return studentSchedules;
         
     } 
     
     public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
         connection = DBConnection.getConnection();
         ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<ScheduleEntry>();
         try
         {
             getWaitlistedStudents = connection.prepareStatement("select semester, coursecode, studentid, status, timestamp from app.schedule where semester = ? and coursecode = ? and status = 'W' order by timestamp");
             getWaitlistedStudents.setString(1, semester);
             getWaitlistedStudents.setString(2, courseCode);
             resultSet = getWaitlistedStudents.executeQuery();
             
             while(resultSet.next())
             {
                 ScheduleEntry waitlisted = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                 waitlistedStudents.add(waitlisted);
             }
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         return waitlistedStudents;
         
     }
     
     public static int getScheduledStudentCount(String semester, String courseCode){
         connection = DBConnection.getConnection();
         count = 0;
         try
         {
             getScheduledCount = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ? and status = 'S'");
             getScheduledCount.setString(1, semester);
             getScheduledCount.setString(2, courseCode);
             resultSet = getScheduledCount.executeQuery();
             
             while(resultSet.next())
             {
                 count = resultSet.getInt(1);
             }
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         return count;
         
     }
     
     public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
         connection = DBConnection.getConnection();
         try
         {
             dropStudentSchedule = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ? and studentID = ?");
             dropStudentSchedule.setString(1, semester);
             dropStudentSchedule.setString(2, courseCode);
             dropStudentSchedule.setString(3, studentID);
             dropStudentSchedule.executeUpdate();
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
     }
     
     public static void dropScheduleByCourse(String semester, String courseCode){
         connection = DBConnection.getConnection();
         try
         {
             dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = ? and coursecode = ?");
             dropScheduleByCourse.setString(1, semester);
             dropScheduleByCourse.setString(2, courseCode);
             dropScheduleByCourse.executeUpdate();
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
     }
     
     public static void updateScheduleEntry(ScheduleEntry entry){
         connection = DBConnection.getConnection();
         try
         {
             updateSchedule = connection.prepareStatement("update app.schedule set status = 'S' where semester = ? and coursecode = ? and studentid = ? and status = 'W' and timestamp = ?");
             updateSchedule.setString(1, entry.getSemester());
             updateSchedule.setString(2, entry.getCourseCode());
             updateSchedule.setString(3, entry.getStudentID());
             updateSchedule.setTimestamp(4, entry.getTimestamp());
             updateSchedule.executeUpdate();
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
     }
       
 } 