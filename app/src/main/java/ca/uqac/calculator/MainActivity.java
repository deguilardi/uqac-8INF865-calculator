package ca.uqac.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplay;

    private enum Action{
        NUMBER,
        OPERATOR
    }

    private enum Operation{
        EQUALS,
        SUM,
        MINUS,
        DIVIDE,
        MULTIPLY
    }

    private int mLeftNumber = 0;
    private int mRightNumber = 0;
    private Action mCurrAction = Action.NUMBER;
    private Operation mCurrOperation = Operation.EQUALS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI(){
        mDisplay = findViewById(R.id.display);
    }

    public void hitNumber(View v){
        int number = 0;
        switch(v.getId()){
            case R.id.btnNumber0: number = 0; break;
            case R.id.btnNumber1: number = 1; break;
            case R.id.btnNumber2: number = 2; break;
            case R.id.btnNumber3: number = 3; break;
            case R.id.btnNumber4: number = 4; break;
            case R.id.btnNumber5: number = 5; break;
            case R.id.btnNumber6: number = 6; break;
            case R.id.btnNumber7: number = 7; break;
            case R.id.btnNumber8: number = 8; break;
            case R.id.btnNumber9: number = 9; break;
        }
        if(mCurrAction == Action.NUMBER) {
            mRightNumber = mRightNumber * 10 + number;
        }
        else{
            mRightNumber = number;
        }
        mCurrAction = Action.NUMBER;
        mDisplay.setText(String.valueOf(mRightNumber));
    }

    public void hitOperation(View v){
        calcEquals();
        switch(v.getId()){
            case R.id.btnOpSum: mCurrOperation = Operation.SUM; break;
            case R.id.btnOpMinus: mCurrOperation = Operation.MINUS; break;
            case R.id.btnOpDivide: mCurrOperation = Operation.DIVIDE; break;
            case R.id.btnOpMultiply: mCurrOperation = Operation.MULTIPLY; break;
        }
        mCurrAction = Action.OPERATOR;
    }

    public void hitEquals(View v){
        calcEquals();
        mCurrOperation = Operation.EQUALS;
        mCurrAction = Action.OPERATOR;
    }

    private void calcEquals(){
        switch (mCurrOperation){
            case SUM: mLeftNumber += mRightNumber; break;
            case MINUS: mLeftNumber -= mRightNumber; break;
            case DIVIDE: mLeftNumber /= mRightNumber; break;
            case MULTIPLY: mLeftNumber *= mRightNumber; break;
            default: mLeftNumber = mRightNumber;
        }
        mRightNumber = mLeftNumber;
        mDisplay.setText(String.valueOf(mRightNumber));
    }
}
