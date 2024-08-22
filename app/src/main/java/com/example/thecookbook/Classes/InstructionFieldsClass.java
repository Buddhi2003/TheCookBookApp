package com.example.thecookbook.Classes;

import android.widget.EditText;

public class InstructionFieldsClass {
    private EditText InstructionDetail;
    private EditText InstructionTime;

    public InstructionFieldsClass(EditText instructionDetail, EditText instructionTime) {
        InstructionDetail = instructionDetail;
        InstructionTime = instructionTime;
    }

    public EditText getInstructionDetail() {
        return InstructionDetail;
    }

    public void setInstructionDetail(EditText instructionDetail) {
        InstructionDetail = instructionDetail;
    }

    public EditText getInstructionTime() {
        return InstructionTime;
    }

    public void setInstructionTime(EditText instructionTime) {
        InstructionTime = instructionTime;
    }
}
