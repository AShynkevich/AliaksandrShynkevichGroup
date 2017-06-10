package com.epam.lab.mentoring;

import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractExample {
    private static final String FORMULA = "(#{a} + #{b}) * (#{a} + #{b} * #{b} - #{a})";
    protected List<Pair<Integer, Integer>> input = Arrays.asList(
            new ImmutablePair<>(1, 2),
            new ImmutablePair<>(2, 2),
            new ImmutablePair<>(3, 1),
            new ImmutablePair<>(4, 2)
    );
    protected List<String> output = Arrays.asList("", "", "", "");

    public abstract void calculate();

    protected void calculate(int inputIndex) {
        Pair<Integer, Integer> pair = input.get(inputIndex);
        Evaluator eval = new Evaluator();
        eval.putVariable("a", pair.getLeft().toString());
        eval.putVariable("b", pair.getRight().toString());

        try {
            String result = eval.evaluate(FORMULA);
            Thread.sleep(3000);
            output.set(inputIndex, result);
        } catch (EvaluationException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected int getInputSize() {
        return input.size();
    }

    protected Pair<Integer, Integer> getByIndex(int index) {
        return input.get(index);
    }
}
