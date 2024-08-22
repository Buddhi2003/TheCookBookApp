package com.example.thecookbook.Classes;

public class InstructionClass {
    private int InstructionNumber;
    private String InstructionDetail;
    private int time;

    public InstructionClass() {
    }

    public InstructionClass(String instructionDetail, int time) {
        InstructionDetail = instructionDetail;
        this.time = time;
    }

    public InstructionClass(int instructionNumber, String instructionDetail, int time) {
        InstructionNumber = instructionNumber;
        InstructionDetail = instructionDetail;
        this.time = time;
    }


    public int getInstructionNumber() {
        return InstructionNumber;
    }

    public void setInstructionNumber(int instructionNumber) {
        InstructionNumber = instructionNumber;
    }

    public String getInstructionDetail() {
        return InstructionDetail;
    }

    public void setInstructionDetail(String instructionDetail) {
        InstructionDetail = instructionDetail;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
