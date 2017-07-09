package com.example.akhil.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import bsh.EvalError;
import bsh.Interpreter;

public class MainActivity extends AppCompatActivity {
    private TextView outputBox;
    private TextView computeBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputBox = (TextView) findViewById(R.id.outputText);
        computeBox = (TextView) findViewById(R.id.computeText);
    }

    public void onClick(View view) {
        Button clickedButton = (Button) view;
        String clickedButtonName = clickedButton.getText().toString();
        if (clickedButtonName.equals(outputBox.getText().toString().substring(outputBox.getText().toString().length() - 2, outputBox.getText().toString().length() - 1))) {
            return;
        }
        Set<String> names = new HashSet<>();
        names.add(getString(R.string.multiplyString));
        names.add(getString(R.string.minusString));
        names.add(getString(R.string.plusString));
        if (names.contains(clickedButtonName)) {
            outputBox.setText(outputBox.getText() + " " + clickedButtonName + " ");
        } else {
            outputBox.setText(outputBox.getText() + "" + clickedButtonName);
        }
        if (!outputBox.getText().equals("  ")) {
            compute(view);
        }
    }

    public void deleteLastChar(View view) {
        if (outputBox.getText().equals("  ")) {
            return;
        }
        String deleteLastChar = outputBox.getText().toString().substring(0, outputBox.getText().toString().length() - 1);
        outputBox.setText(deleteLastChar);
    }

    public void compute(View view) {
        System.out.println("Equasdlkfjalskdjflkasjdf;ljasd;lfdj;lasdjkf");
        Interpreter interpreter = new Interpreter();
        try {
            interpreter.eval("result=" + outputBox.getText().toString());
            String result = interpreter.get("result").toString();
            System.out.println(result);
            computeBox.setText(result);
        } catch (EvalError evalError) {
            evalError.printStackTrace();
        }
    }
}
