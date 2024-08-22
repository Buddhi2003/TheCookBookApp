package com.example.thecookbook.Classes;

public class SalaryClass {
    private int InfID;
    private int LastPaidLevel;
    private String LastPaidDate;
    private double LastPaidSalary;
    private double TotalSalary;

    public SalaryClass() {
    }

    public SalaryClass(int infID, int lastPaidLevel, String lastPaidDate, double lastPaidSalary, double totalSalary) {
        InfID = infID;
        LastPaidLevel = lastPaidLevel;
        LastPaidDate = lastPaidDate;
        LastPaidSalary = lastPaidSalary;
        TotalSalary = totalSalary;
    }

    public int getInfID() {
        return InfID;
    }

    public void setInfID(int infID) {
        InfID = infID;
    }

    public int getLastPaidLevel() {
        return LastPaidLevel;
    }

    public void setLastPaidLevel(int lastPaidLevel) {
        LastPaidLevel = lastPaidLevel;
    }

    public String getLastPaidDate() {
        return LastPaidDate;
    }

    public void setLastPaidDate(String lastPaidDate) {
        LastPaidDate = lastPaidDate;
    }

    public double getLastPaidSalary() {
        return LastPaidSalary;
    }

    public void setLastPaidSalary(double lastPaidSalary) {
        LastPaidSalary = lastPaidSalary;
    }

    public double getTotalSalary() {
        return TotalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        TotalSalary = totalSalary;
    }
}
