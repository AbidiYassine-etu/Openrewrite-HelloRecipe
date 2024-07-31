package bootiful.openrewrite;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.openrewrite.*;
import org.openrewrite.java.JavaIsoVisitor;
import org.openrewrite.java.JavaTemplate;
import org.openrewrite.java.tree.J;


@Value
@EqualsAndHashCode(callSuper = false)
public class SayHelloRecipe extends Recipe {

//    @Option(displayName = "Hello from FooBar",
//    description = "This is a test",
//    example = "a,b,C")
//    @NonNull
    final String fqn = "com.example.demo.MyClass";

    public SayHelloRecipe() {
    }

    @Override
    public String getDisplayName() {
        return "Say Hello Recipe";
    }

    @Override
    public String getDescription() {
        return "This is a test .";
    }

    @Override
    public TreeVisitor<?, ExecutionContext> getVisitor() {
        return new SayHelloVisitor();
    }

    private class SayHelloVisitor extends JavaIsoVisitor<ExecutionContext> {
        private final String method ="""
                public String hello() {
                    return "Hello, from #{}";
                }
                """;

        private final JavaTemplate template = JavaTemplate.builder(this.method).build();


        @Override
        public J.ClassDeclaration visitClassDeclaration(
                J.ClassDeclaration classDecl,
                ExecutionContext executionContext) {


            var isCandidate = (classDecl.getType() == null) ||
                    (!classDecl.getType().getFullyQualifiedName().equals(fqn));

            if(!isCandidate) {
                //alll clear
                var cursor = new Cursor(getCursor(),classDecl.getBody());
                var lastStatementJavaCoordinates = classDecl.getBody()
                        .getCoordinates()
                        .lastStatement();



                return classDecl.withBody(
                        this.template.apply(cursor,lastStatementJavaCoordinates,fqn)
                );
            }


            var hasExistingHelloMethod = classDecl.getBody()
                        .getStatements()
                        .stream()
                        .filter(statement -> statement instanceof J.MethodDeclaration )
                        .map(J.MethodDeclaration.class::cast)
                        .anyMatch(methodDeclaration -> methodDeclaration.getName().getSimpleName().equals("hello"));


                if (hasExistingHelloMethod) {
                    return classDecl;
                }



                //alll clear
            var cursor = new Cursor(getCursor(),classDecl.getBody());
            var lastStatementJavaCoordinates = classDecl.getBody()
                    .getCoordinates()
                    .lastStatement();



            return classDecl.withBody(
                    this.template.apply(cursor,lastStatementJavaCoordinates,fqn)
            );
        }
    }
}
