// import java.time.LocalDate;
import java.time.LocalDate;
import java.util.ArrayList;
enum EnrollmentStatus{
    ENROLLED,
    DROPPED
}

class Course{
    private String courseId;
    private String courseName;
    private int available_seats;
    public Course(String courseId,String courseName,int available_seats){
        this.courseId=courseId;
        this.courseName=courseName;
        this.available_seats=available_seats;
    }
    public String getCourseId(){
        return  courseId;
    }
    public void decrement_seat_availibility(){
        available_seats--;
    }
    public void increament_seat_availibility(){
        available_seats++;
    }
    public boolean is_seats_available(){
        return available_seats>0;
    }
    public int get_no_seats_avail(){
        return available_seats;
    }
    public String getCourseName(){
        return courseName;
    }
    
}
class Student {
    private String studentId;
    private String studentName;
    private String branch;
    public Student(String studentId, String student_Name, String Branch){
        this.studentId=studentId;
        this.studentName=student_Name;
        this.branch=Branch;
    }
    public String get_StudentId(){
        return studentId;
    }
    public void change_studentBranch(String Branch){
        this.branch=Branch;
    }
    public String getStudentName(){
        return studentName;
    }
    public String getBranch(){
        return branch;
    }

    
}
class Enroll{
   private Student student;
   private Course course;
   private EnrollmentStatus status; //Enrolled,Completed,Droped
   private LocalDate enrolledDate;
    public Enroll(Student student,Course course){
        this.student=student;
        this.course=course;
        enrolledDate=LocalDate.now();
        status=EnrollmentStatus.ENROLLED;
    }
    
    public Student get_student(){
        return student;
    }
    public Course get_course(){
        return course;
    }
    public void markDropped(){
        this.status=EnrollmentStatus.DROPPED;
    }
    public EnrollmentStatus getStatus(){
        return status;
    }
    public LocalDate getEnrLocalDate(){
        return enrolledDate;
    }
    
    @Override
    public String toString(){
        return "Student Id :" +student.get_StudentId()
               +"\nStudentName :"+student.getStudentName()
               +"\nCourse Id :"+course.getCourseId()
               +"\nCourse : "+course.getCourseName()
               +"\nStatus : "+getStatus()
               +"\nEnrolledDate : "+getEnrLocalDate();  
    }
}
class University{
    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private ArrayList<Enroll> enrollment_List;
    public University(){
        students=new ArrayList<>();
        courses=new ArrayList<>();
        enrollment_List=new ArrayList<>();
    }

    public void addStudent(Student student){
        students.add(student);
    }
    public void addCourse(Course course){
        courses.add(course);
    }
    
    public boolean remove_from_EnrolledList(Enroll enrolled){
        if(enrollment_List.remove(enrolled)){
            return true;
        }
        return false;
    }
    
    public Student find_student(String student_id){
        for (Student student : students) {
            if(student.get_StudentId().equals(student_id)){
                return student;
            }
            }
            return null;
    }

public Course find_course(String course_id){
    for (Course course:courses){
        if(course.getCourseId().equals(course_id)){
            return course;
        }
    }
    return null;
}
public boolean addEnrollmentList(Enroll enrolled){
        return enrollment_List.add(enrolled);
    }
public Enroll find_enrollment(Student student,Course course){
    for (Enroll enroll : enrollment_List) {
        if(enroll.get_student().equals(student)){
            if(enroll.get_course().equals(course)){
                return enroll;
            }
        }
    } 
    return  null;
}
public void enroll_to_course(String studendId,String courseId){
     Student student=find_student(studendId);
     if(student==null){
        System.out.println("student not Found..!!");
        return;
     }
     Course course=find_course(courseId);
     if(course==null){
        System.out.println("This course not Available");
        return;
     }
     boolean seat_available=course.is_seats_available();
     if(seat_available==false){
        System.out.println("Seats Filled up");
        return;
     }
     
     Enroll is_registered=find_enrollment(student, course);
     if(is_registered!=null){
        System.out.println("This student Already Registered in this course ");
        return;
     }
     Enroll enroll_obj=new Enroll(student, course);
     boolean addToenroll=addEnrollmentList(enroll_obj);
     if(addToenroll){
        course.decrement_seat_availibility();
        System.out.println("Enrolled sucsessfullyy...Happy Learning");
        return;

     }
     System.out.println("Registration Failed...");


}
public void drop_course(String studentId,String courseId){
    Student student=find_student(studentId);
    if(student==null){
        System.out.println("Student not Exists");
        return;
    }
    Course course=find_course(courseId);
    if(course==null){
        System.out.println("Course not Existss");
        return;
    }
    Enroll enrol_obj=find_enrollment(student, course);
    if(enrol_obj==null){
        System.out.println("Not registered Previously");
        return;
    }
    if(enrol_obj.getStatus()==EnrollmentStatus.DROPPED){
        System.out.println("Already Dropped");
        return;
    }
    enrol_obj.markDropped();
    course.increament_seat_availibility();
    System.out.println("Cancilation Sucsessfully");

}
public void getDroppedStudents(){
    for (Enroll enroll : enrollment_List) {
        if(enroll.getStatus()==EnrollmentStatus.DROPPED){
            System.out.println(enroll);
            System.out.println();
        
        }
    }
}
public void getActiveEnrolledStudents(){
    for (Enroll enroll : enrollment_List) {
        if(enroll.getStatus()==EnrollmentStatus.ENROLLED){
            System.out.println(enroll);
            System.out.println("\n");
        }
    }
}

}

public class Course_Management
 {
  public static void main(String[] args){
    Student std1=new Student("901", "Mahi", "computers");
    Student std2= new Student("902", "Sangamesh", "Electronics");
    
    Course course1= new Course("JC47", "Java core", 20);
    Course course2=new Course("GA68", "GenAi", 15);
    Course course3=new Course("CC78", "Cloud_computing", 25);

    University university=new University();
    university.addCourse(course1);
    university.addCourse(course2);
    university.addCourse(course3);

    university.addStudent(std1);
    university.addStudent(std2);
   
    
    university.enroll_to_course("901", "CC78");
    university.enroll_to_course("901", "CC78");
    university.enroll_to_course("902", "GA68");
    university.enroll_to_course("902", "JC47");

    university.drop_course("901", "CC78");
    
    int seats=course3.get_no_seats_avail();
    System.out.println(seats);
    
    System.out.println("...Actively Erolled Students List...");
    university.getActiveEnrolledStudents();

    System.out.println("...DROPPED STUDENTS LIST...");
    university.getDroppedStudents();
  }
}
