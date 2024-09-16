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
 public class CourseQueries {
     private static Connection connection;
     private static PreparedStatement addCourse;
     private static PreparedStatement getCourseList;
     private static ResultSet resultSet;
     
     public static void addCourse(CourseEntry course)
     {
         connection = DBConnection.getConnection();
         try
         {
             addCourse = connection.prepareStatement("insert into app.course (coursecode, description) values (?, ?)");//column to pull from in paratheses
             addCourse.setString(1, course.getCourseCode());
             addCourse.setString(2, course.getDescription());
             addCourse.executeUpdate();
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         
     }
     
     public static ArrayList<String> getAllCourseCodes()
     {
         connection = DBConnection.getConnection();
         ArrayList<String> courses = new ArrayList<String>();
         try
         {
             getCourseList = connection.prepareStatement("select coursecode from app.course order by coursecode");
             resultSet = getCourseList.executeQuery();
             
             while(resultSet.next())
             {
                 courses.add(resultSet.getString(1));
             }
         }
         catch(SQLException sqlException)
         {
             sqlException.printStackTrace();
         }
         return courses;
         
     }
      
 }