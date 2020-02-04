package ca.uqac.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplay;

    private enum Action{
        NUMBER,
        OPERATOR,
        FRACTION
    }

    private enum Operation{
        EQUALS,
        SUM,
        MINUS,
        DIVIDE,
        MULTIPLY
    }

    private double mLeftNumber = 0;
    private double mRightNumber = 0;
    private Stack<Double> mPreviousNumbers = new Stack<>();
    private Action mCurrAction = Action.NUMBER;
    private Operation mCurrOperation = Operation.EQUALS;
    private double mCurrFraction = 10;

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
        mPreviousNumbers.push(mRightNumber);
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
        else if(mCurrAction == Action.FRACTION){
            mRightNumber = mRightNumber + number / mCurrFraction;
            mCurrFraction *= 10;
        }
        else{
            mCurrAction = Action.NUMBER;
            mRightNumber = number;
        }
        displayIt();
    }

    public void hitOperation(View v){
        mPreviousNumbers.clear();
        calcEquals();
        switch(v.getId()){

            // operations with left and right
            case R.id.btnOpSum: mCurrOperation = Operation.SUM; break;
            case R.id.btnOpMinus: mCurrOperation = Operation.MINUS; break;
            case R.id.btnOpDivide: mCurrOperation = Operation.DIVIDE; break;
            case R.id.btnOpMultiply: mCurrOperation = Operation.MULTIPLY; break;

            // straight forward operations
            case R.id.btnOpSquareRoot:
                mRightNumber = Math.sqrt(mRightNumber);
                displayIt();
                mPreviousNumbers.clear();
                return; // not break
            case R.id.btnOpPercentage:
                mRightNumber /= 100d;
                displayIt();
                mPreviousNumbers.clear();
                return; // not break
            case R.id.btnOp1x:
                mRightNumber = 1d / mRightNumber;
                displayIt();
                mPreviousNumbers.clear();
                return; // not break
        }
        mCurrAction = Action.OPERATOR;
    }

    public void hitEquals(View v){
        calcEquals();
        mCurrOperation = Operation.EQUALS;
        mCurrAction = Action.OPERATOR;
    }

    public void hitPoint(View v){
        mCurrFraction = 10;
        mCurrAction = Action.FRACTION;
    }

    public void hitClear(View v){
        switch(v.getId()){
            case R.id.btnOpBackspace:
                if(!mPreviousNumbers.empty()) {
                    mRightNumber = mPreviousNumbers.pop();
                    displayIt();
                }
                break;
            case R.id.btnOpC:
                mLeftNumber = 0;
                mCurrOperation = Operation.EQUALS;
                // break; no break here
            case R.id.btnOpCE:
                mCurrAction = Action.NUMBER;
                mCurrFraction = 10;
                mRightNumber = 0;
                mPreviousNumbers.clear();
                displayIt();
                break;
        }
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
        displayIt();
    }

    private void displayIt(){
        if(mRightNumber == (int) mRightNumber){
            mDisplay.setText(String.valueOf((int) mRightNumber));
        }
        else {
            mDisplay.setText(String.valueOf(mRightNumber));
        }

    }
}
