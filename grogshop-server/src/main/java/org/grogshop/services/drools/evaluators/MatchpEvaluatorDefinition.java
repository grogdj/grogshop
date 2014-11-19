/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grogshop.services.drools.evaluators;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
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
 * @author salaboy
 */
public class MatchpEvaluatorDefinition implements EvaluatorDefinition{

    protected static final String   matchpOp = "matchp";

    public static Operator          MATCHP;
    public static Operator          NOT_MATCHP;
    
    
    
    private static String[]         SUPPORTED_IDS;

    { init(); }

    static void init() {
        if ( SUPPORTED_IDS == null ) {
            MATCHP = Operator.addOperatorToRegistry(matchpOp, false );
            NOT_MATCHP = Operator.addOperatorToRegistry(matchpOp, true );
            SUPPORTED_IDS = new String[] { matchpOp };
        }
    }
    
    private EvaluatorCache evaluators = new EvaluatorCache() {
        private static final long serialVersionUID = 510l;
        {
            addEvaluator( ValueType.OBJECT_TYPE,        MATCHP,         MatchPercentageEvaluator.INSTANCE );
        }
    };
    
    public String[] getEvaluatorIds() {
        return SUPPORTED_IDS;
    }

    public boolean isNegatable() {
        return true;
    }

    public Evaluator getEvaluator(ValueType vt, String string, boolean bln, String string1, Target target, Target target1) {
        return this.evaluators.getEvaluator( vt,
                                             Operator.determineOperator( string,
                                                                         bln ) );
        
    }

    public Evaluator getEvaluator(ValueType vt, String string, boolean bln, String string1) {
        return this.getEvaluator( vt,
                                  string,
                                  bln,
                                  string1,
                                  Target.FACT,
                                  Target.FACT );
    }

    public Evaluator getEvaluator(ValueType vt, Operator oprtr, String string) {
        return this.evaluators.getEvaluator( vt,
                                             oprtr );
    }

    public Evaluator getEvaluator(ValueType vt, Operator oprtr) {
        return this.evaluators.getEvaluator( vt,
                                             oprtr );
    }

    public boolean supportsType(ValueType vt) {
        return this.evaluators.supportsType( vt );
    }

    public Target getTarget() {
        return Target.BOTH;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(evaluators);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        evaluators  = (EvaluatorCache)in.readObject();
    }
    
    private static boolean matchPercentage(List list1, List list2){
        System.out.println("List 1 : "+list1);
        System.out.println("List 2 : "+list2);
        for(Object o : list1){
            if(!list2.contains(o)){
                return false;
            }
        }
        
        for(Object o : list2){
            if(!list1.contains(o)){
                return false;
            }
        }
        
        
        return true;
    }
    
    
    /*  *********************************************************
     *           Evaluator Implementations
     *  *********************************************************
     */
    public static class MatchPercentageEvaluator extends BaseEvaluator {

        private static final long     serialVersionUID = 510l;
        public final static Evaluator INSTANCE         = new MatchPercentageEvaluator();

        {
            MatchpEvaluatorDefinition.init();
        }

        public MatchPercentageEvaluator() {
            super( ValueType.ARRAY_TYPE,
                   MATCHP );
        }

        public boolean evaluate(InternalWorkingMemory workingMemory,
                                final InternalReadAccessor extractor,
                                final InternalFactHandle handle1, final FieldValue handle2) {
            final List value1 = (List) extractor.getValue( workingMemory, handle1.getObject() );
            final List value2 = (List) handle2.getValue();

            return matchPercentage(value1,value2);
        }

        public boolean evaluateCachedRight(InternalWorkingMemory workingMemory,
                                           final VariableContextEntry context, final InternalFactHandle left) {
            final List value = (List) ((ObjectVariableContextEntry) context).right;

            return matchPercentage( value, (List) context.declaration.getExtractor().getValue( workingMemory, left.getObject() ) );
        }

        public boolean evaluateCachedLeft(InternalWorkingMemory workingMemory,
                                          final VariableContextEntry context, final InternalFactHandle rightHandle) {
            final List value = (List) context.extractor.getValue( workingMemory, rightHandle.getObject() );

            return matchPercentage(value, (List) ((ObjectVariableContextEntry) context).left );
        }

        public boolean evaluate(InternalWorkingMemory workingMemory,
                                final InternalReadAccessor extractor1,
                                final InternalFactHandle handle1,
                                final InternalReadAccessor extractor2, final InternalFactHandle handle2) {
            final Object value1 = extractor1.getValue( workingMemory, handle1.getObject() );
            final Object value2 = extractor2.getValue( workingMemory, handle2.getObject() );

            return matchPercentage( (List) value1, (List) value2 );
        }

        public String toString() {
            return "arrays matches percentage";
        }
    }
    
    
    
    
    
}
