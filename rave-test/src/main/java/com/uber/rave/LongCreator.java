package com.uber.rave;

import java.util.HashSet;
import java.util.Set;

/**
 * This class is creates valid and invalid permutations of a long argument based on the annotation
 * restrictions.
 */
public final class LongCreator extends ObjectCreator<Long> {

    Set<Long> validValues;

    public LongCreator(long... validValues) {
        this(new AnnotationSpecs.Builder().setLongDef(validValues).build());
    }

    public LongCreator(AnnotationSpecs spec) {
        validValues = new HashSet<>(spec.getValidLongValues().length);
        createValues(spec);
    }

    private void createValues(AnnotationSpecs spec) {
        if (spec.hasLongDef()) {
            createDefValues(spec);
            return;
        }
    }

    private void createDefValues(AnnotationSpecs spec) {
        for (long value : spec.getValidLongValues()) {
            addValidType(value);
            validValues.add(value);
        }
        long invalidValues = 0;
        boolean plusMinus = true;
        for (long value : spec.getValidLongValues()) {
            value += (plusMinus) ? -1 : 1;
            plusMinus = !plusMinus;
            if (!validValues.contains(value)) {
                addInvalidType(value);
                invalidValues++;
            }
        }
        long badValue = -1;
        if (invalidValues == 0) {
            while (invalidValues < 1) {
                if (!validValues.contains(badValue)) {
                    addInvalidType(badValue);
                    invalidValues++;
                    badValue++;
                }
            }
        }
    }
}
