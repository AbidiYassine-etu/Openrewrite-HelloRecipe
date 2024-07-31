package bootiful.openrewrite;

import org.junit.jupiter.api.Test;
import org.openrewrite.test.RewriteTest;
import org.openrewrite.test.RecipeSpec;


import static org.openrewrite.java.Assertions.java;


class SayHelloRecipeTest implements RewriteTest {

    final String fqn = "com.example.demo.MyClass";

    @Override
    public void defaults(RecipeSpec spec) {
        spec.recipe(new SayHelloRecipe());
    }

    @Test
    void addHellotoFoobar() throws Exception {
        rewriteRun(java ("""
                    package hello;
     
                    class FooBar{ }
                """, """
                    package hello;
                    
                    class FooBar{
                        public String hello() {
                            return "Hello, from com.example.demo.MyClass";
                        } }
                """));
    }
}
