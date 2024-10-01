package slava0135.fuzz3.mutation;

import java.io.File;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;

public class TriangleCalculatorAnalyzer {
    public static void main(String[] args) {
        try {
            var pathToSrc = "src/main/java/slava0135/fuzz3/mutation/TriangleCalculator.java";
            var file = new File(pathToSrc);
            var compilationUnit = StaticJavaParser.parse(file);
            var returnMutator = new ArithmeticMutator(-1);
            var methods = compilationUnit.findAll(MethodDeclaration.class);
            var method = methods.get(0);
            var analyzer = new MuFunctionAnalyzer(method, compilationUnit, returnMutator, compilationUnit.toString(),
                    pathToSrc);
            analyzer.generateMutants();
            for (var mutant : analyzer.mutants) {
                System.out.println(mutant.src);
                System.out.println("%".repeat(80));
            }
            var oracleName = "slava0135.fuzz3.mutation.TriangleCalculatorTests";
            var score = analyzer.executeMutants(oracleName);
            System.out.println("Score for " + oracleName + " = " + score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
