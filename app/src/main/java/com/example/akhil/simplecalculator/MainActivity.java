package com.example.akhil.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import bsh.EvalError;
import bsh.Interpreter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

  private EditText userInputBox;
  private TextView computeBox;
  private int currentComputeNum;
  private boolean continuousCompute;
  private ArrayList<String> previousCompute;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    currentComputeNum = 0;
    continuousCompute = false;
    previousCompute = new ArrayList<>();
    previousCompute.add("  ");
    userInputBox = (EditText) findViewById(R.id.userInputBox);
    computeBox = (TextView) findViewById(R.id.computeText);

    Button deleteButton = (Button) findViewById(R.id.deleteButton);
    deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        userInputBox.setText("");
        computeBox.setText("");
        return false;
      }
    });
    if (!continuousCompute) {
      computeBox.setVisibility(View.GONE);
      findViewById(R.id.divider).setVisibility(View.GONE);
    }
  }

  public void onNumberClick(View view) {
    Button clickedButton = (Button) view;
    String clickedButtonName = clickedButton.getText().toString();

    if (userInputBox.getText().length() > 2 && clickedButtonName
        .equals(userInputBox.getText().toString()
            .substring(userInputBox.getText().toString().length() - 2,
                userInputBox.getText().toString().length() - 1))) {
      return;
    }
    Set<String> names = new HashSet<>();
    names.add(getString(R.string.multiplyString));
    names.add(getString(R.string.minusString));
    names.add(getString(R.string.plusString));
    names.add(getString(R.string.divideString));
    if (names.contains(clickedButtonName)) {
      userInputBox.setText(userInputBox.getText() + " " + clickedButtonName + " ");
    } else {
      userInputBox.setText(userInputBox.getText() + "" + clickedButtonName);
    }
    if (!userInputBox.getText().equals("") && continuousCompute) {
      compute();
    }
  }

  public void deleteLastChar(View view) {
    if (userInputBox.getText().length() == 0) {
      return;
    }

    char lastChar = userInputBox.getText().toString().toCharArray()[
        userInputBox.getText().length() - 1];
    if (lastChar == ' ') {
      userInputBox.getText().delete(userInputBox.getText().length() - 2,
          userInputBox.getText().length());
    }
    userInputBox.getText().delete(userInputBox.getText().length() - 1,
        userInputBox.getText().length());
  }

  public void equalSignClick(View view) {
    compute();
    currentComputeNum++;
    previousCompute.add(userInputBox.getText().toString());
    userInputBox.setText(computeBox.getText());
    computeBox.setText("");
  }


  private void compute() {
    try {
      Interpreter interpreter = new Interpreter();
      interpreter.eval("result=(float)" + userInputBox.getText().toString());
      String result = interpreter.get("result").toString();
      computeBox.setText(result);
    } catch (EvalError evalError) {
      evalError.printStackTrace();
    }
  }

  public void setNextCompute(View view) {
    currentComputeNum++;
    if (currentComputeNum >= previousCompute.size()) {
      currentComputeNum = previousCompute.size() - 1;
    }
    userInputBox.setText(previousCompute.get(currentComputeNum));
  }

  public void setPreviousCompute(View view) {
    currentComputeNum--;
    if (currentComputeNum < 0) {
      currentComputeNum = 0;
    }
    userInputBox.setText(previousCompute.get(currentComputeNum));
  }
}
