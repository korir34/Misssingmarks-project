package com.example.missingmarksapp.Models;

public class StudentData {

    private String name;
    private String regNumber;
    private String unitName;
    private String unitCode;
    private String examDate;

    public StudentData(String name, String regNumber, String unitName, String unitCode,String examDate) {
        this.name = name;
        this.regNumber = regNumber;
        this.unitName = unitName;
        this.unitCode = unitCode;
        this.examDate = examDate;
    }

    public StudentData(String name, String regNumber, String unitName, String unitCode) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }
}
