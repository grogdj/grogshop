/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.drools.evaluators;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.drools.core.base.BaseEvaluator;
import org.drools.core.base.ValueType;
import org.drools.core.base.evaluators.EvaluatorCache;
import org.drools.core.base.evaluators.EvaluatorDefinition;
import org.drools.core.base.evaluators.Operator;
import org.drools.core.common.InternalFactHandle;
import org.drools.core.common.InternalWorkingMemory;
import org.drools.core.rule.VariableRestriction.ObjectVariableContextEntry;
import org.drools.core.rule.VariableRestriction.VariableContextEntry;
import org.drools.core.spi.Evaluator;
import org.drools.core.spi.FieldValue;
import org.drools.core.spi.InternalReadAccessor;

/**
 *
 * @author grogdj
 */
public class InRangeEvaluatorDefinition implements EvaluatorDefinition {

    protected static final String inrangeOp = "inrange";

    public static Operator INRANGE;
    public static Operator NOT_INRANGE;

    private static String[] SUPPORTED_IDS;

    {
        init();
    }

    static void init() {
        if (SUPPORTED_IDS == null) {
            INRANGE = Operator.addOperatorToRegistry(inrangeOp, false);
            NOT_INRANGE = Operator.addOperatorToRegistry(inrangeOp, true);
            SUPPORTED_IDS = new String[]{inrangeOp};
        }
    }

    private EvaluatorCache evaluators = new EvaluatorCache() {
        private static final long serialVersionUID = 510l;

        {
            addEvaluator(ValueType.OBJECT_TYPE, INRANGE, InRangeEvaluator.INSTANCE);
            addEvaluator(ValueType.ARRAY_TYPE, INRANGE, InRangeEvaluator.INSTANCE);
            addEvaluator(ValueType.DOUBLE_TYPE, INRANGE, InRangeEvaluator.INSTANCE);
            addEvaluator(ValueType.NUMBER_TYPE, INRANGE, InRangeEvaluator.INSTANCE);
        }
    };

    public String[] getEvaluatorIds() {
        return SUPPORTED_IDS;
    }

    public boolean isNegatable() {
        return true;
    }

    public Evaluator getEvaluator(ValueType vt, String string, boolean bln, String string1, Target target, Target target1) {
        return this.evaluators.getEvaluator(vt,
                Operator.determineOperator(string,
                        bln));

    }

    public Evaluator getEvaluator(ValueType vt, String string, boolean bln, String string1) {
        return this.getEvaluator(vt,
                string,
                bln,
                string1,
                Target.FACT,
                Target.FACT);
    }

    public Evaluator getEvaluator(ValueType vt, Operator oprtr, String string) {
        return this.evaluators.getEvaluator(vt,
                oprtr);
    }

    public Evaluator getEvaluator(ValueType vt, Operator oprtr) {
        return this.evaluators.getEvaluator(vt,
                oprtr);
    }

    public boolean supportsType(ValueType vt) {
        return this.evaluators.supportsType(vt);
    }

    public Target getTarget() {
        return Target.BOTH;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(evaluators);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        evaluators = (EvaluatorCache) in.readObject();
    }

    private static boolean inrange(Object o1, Object o2) {
        Double amount = (Double) o1;
        Double[] priceRange = (Double[]) o2;
        System.out.println("Amount = " + amount + " >  priceRange[0] - priceRange[1]: " + (priceRange[0] - priceRange[1]));
        return amount >= priceRange[0] - priceRange[1];
    }

    /*  *********************************************************
     *           Evaluator Implementations
     *  *********************************************************
     */
    public static class InRangeEvaluator extends BaseEvaluator {

        private static final long serialVersionUID = 510l;
        public final static Evaluator INSTANCE = new InRangeEvaluator();

        {
            InRangeEvaluatorDefinition.init();
        }

        public InRangeEvaluator() {
            super(ValueType.OBJECT_TYPE,
                    INRANGE);
        }

        public boolean evaluate(InternalWorkingMemory workingMemory,
                final InternalReadAccessor extractor,
                final InternalFactHandle handle1, final FieldValue handle2) {
            final Object value1 = (Object) extractor.getValue(workingMemory, handle1.getObject());
            final Object value2 = (Object) handle2.getValue();

            return inrange(value1, value2);
        }

        public boolean evaluateCachedRight(InternalWorkingMemory workingMemory,
                final VariableContextEntry context, final InternalFactHandle left) {
            final Object value = (Object) ((ObjectVariableContextEntry) context).right;

            return inrange(value, (Object) context.declaration.getExtractor().getValue(workingMemory, left.getObject()));
        }

        public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory,
                final VariableContextEntry context, final InternalFactHandle rightHandle) {
            final Object value = (Object) context.extractor.getValue(workingMemory, rightHandle.getObject());

            return inrange(value, (Object) ((ObjectVariableContextEntry) context).left);
        }

        public boolean evaluate(InternalWorkingMemory workingMemory,
                final InternalReadAccessor extractor1,
                final InternalFactHandle handle1,
                final InternalReadAccessor extractor2, final InternalFactHandle handle2) {
            final Object value1 = extractor1.getValue(workingMemory, handle1.getObject());
            final Object value2 = extractor2.getValue(workingMemory, handle2.getObject());

            return inrange((Object) value1, (Object) value2);
        }

        public String toString() {
            return "arrays matches percentage";
        }
    }

}
