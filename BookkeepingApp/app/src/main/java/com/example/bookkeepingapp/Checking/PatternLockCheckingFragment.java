package com.example.bookkeepingapp.Checking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookkeepingapp.MainFunction.Activity_3;
import com.example.bookkeepingapp.PatternLockTool.PatternHelper;
import com.example.bookkeepingapp.R;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternIndicatorView;
import com.github.ihsg.patternlocker.PatternLockerView;

import java.util.List;

public class PatternLockCheckingFragment extends Fragment {
    private PatternLockerView patternLockerView;
    private PatternIndicatorView patternIndicatorView;
    private TextView textMsg;
    private PatternHelper patternHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.patternlockchecking_fragment, container, false);

        this.patternIndicatorView = (PatternIndicatorView) view.findViewById(R.id.pattern_indicator_view);
        this.patternLockerView = (PatternLockerView) view.findViewById(R.id.pattern_lock_view);
        this.textMsg = (TextView) view.findViewById(R.id.text_msg);

        this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
            @Override
            public void onStart(PatternLockerView view) {
            }

            @Override
            public void onChange(PatternLockerView view, List<Integer> hitList) {
            }

            @Override
            public void onComplete(PatternLockerView view, List<Integer> hitList) {
                boolean isError = !isPatternOk(hitList);
                view.updateStatus(isError);
                patternIndicatorView.updateState(hitList, isError);
                updateMsg();
            }

            @Override
            public void onClear(PatternLockerView view) {
                finishIfNeeded();
            }
        });

        this.textMsg.setText("绘制解锁图案");
        this.patternHelper = new PatternHelper();

        return view;
    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ?
                getResources().getColor(R.color.colorPrimary) :
                getResources().getColor(R.color.red));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish() && this.patternHelper.isOk()) {
                Activity_3.action(getContext());
                getActivity().finish();
        }
    }
}
