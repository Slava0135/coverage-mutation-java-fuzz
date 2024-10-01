package slava0135.fuzz3.mutation;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.visitor.Visitable;

public class ArithmeticMutator extends Mutator {
    public ArithmeticMutator(int mutationLocation) {
        super(mutationLocation);
    }

    @Override
    public Node mutationVisit(Node node) {
        if (node instanceof BinaryExpr be) {
            var op = switch (be.getOperator()) {
                case PLUS -> Operator.MINUS;
                case MINUS -> Operator.PLUS;
                case DIVIDE -> Operator.MULTIPLY;
                case MULTIPLY -> Operator.DIVIDE;
                default -> throw new IllegalStateException();
            };
            return new BinaryExpr(be.getLeft(), be.getRight(), op);
        }
        throw new IllegalStateException();
    }

    @Override
    public Visitable visit(BinaryExpr n, Void arg) {
        return switch (n.getOperator()) {
            case PLUS, MINUS, DIVIDE, MULTIPLY -> mutableVisit(n);
            default -> super.visit(n, arg);
        };
    }
}
