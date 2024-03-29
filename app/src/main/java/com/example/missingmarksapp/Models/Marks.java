package com.example.missingmarksapp.Models;

public class Marks {
    String studentName;
    String studentReg;
    String year;
    String marks;

    public Marks(String studentName, String studentReg, String year, String marks) {
        this.studentName = studentName;
        this.studentReg = studentReg;
        this.year = year;
        this.marks = marks;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentReg() {
        return studentReg;
    }

    public void setStudentReg(String studentReg) {
        this.studentReg = studentReg;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
