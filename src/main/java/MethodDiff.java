import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.*;

public class MethodDiff {

    class ClassPair {
        final ClassOrInterfaceDeclaration mClazz;
        final String mName;

        ClassPair(ClassOrInterfaceDeclaration c, String n) {
            mClazz = c;
            mName = n;
        }
    }

    public static <N extends Node> List<N> getChildNodesNotInClass(Node n, Class<N> clazz) {
        List<N> nodes = new ArrayList<>();
        for (Node child : n.getChildNodes()) {
            if (child instanceof ClassOrInterfaceDeclaration) {
                continue;
            }
            if (clazz.isInstance(child)) {
                nodes.add(clazz.cast(child));
            }
            nodes.addAll(getChildNodesNotInClass(child, clazz));
        }
        return nodes;
    }

    private List<ClassPair> getClasses(Node n, String parents, boolean inMethod) {
        List<ClassPair> pairList = new ArrayList<>();
        for (Node child : n.getChildNodes()) {
            if (child instanceof ClassOrInterfaceDeclaration) {
                ClassOrInterfaceDeclaration c = (ClassOrInterfaceDeclaration) child;
                String cName = parents + c.getNameAsString();
                if (!inMethod) {
                    pairList.add(new ClassPair(c, cName));
                    pairList.addAll(getClasses(c, cName + "$", inMethod));
                }
            } else if (child instanceof MethodDeclaration || child instanceof ConstructorDeclaration) {
                pairList.addAll(getClasses(child, parents, true));
            } else {
                pairList.addAll(getClasses(child, parents, inMethod));
            }
        }
        return pairList;
    }

    private List<ClassPair> getClasses(String code) {
        try {
            CompilationUnit cu = JavaParser.parse(code);
            return getClasses(cu, "", false);
        } catch (Exception parseException) {
            return null;
        }
    }

    private static String getMethodNameWithoutArgs(String className, MethodDeclaration methodDeclaration) {
        String nameWithoutArg = className + methodDeclaration.getModifiers().toString() + methodDeclaration.getType().toString() + methodDeclaration.getName();
        return nameWithoutArg;
    }

    private static String getMethodFullDeclaration(MethodDeclaration methodDeclaration) {
        return methodDeclaration.getDeclarationAsString(true, true, true);
    }

    public static List<Changes> diffMethodsParameters(String file1, String file2) {

        MethodDiff methodDiff = new MethodDiff();

        List<Changes> changeList = new ArrayList<>();
        List<ClassPair> classList = methodDiff.getClasses(file1);

        if (classList != null) {
            HashMap<String, Method> map = new HashMap<>();

            for (ClassPair classPair : classList) {

                List<MethodDeclaration> methodList = getChildNodesNotInClass(classPair.mClazz, MethodDeclaration.class);

                for (MethodDeclaration methodDeclaration : methodList) {

                    String nameWithoutArg = getMethodNameWithoutArgs(classPair.mName, methodDeclaration);
                    String name = getMethodFullDeclaration(methodDeclaration);
                    map.put(name, new Method(name, nameWithoutArg, name, methodDeclaration.getParameters()));
                }
            }

            classList = methodDiff.getClasses(file2);

            for (ClassPair classPair : classList) {

                List<MethodDeclaration> methodList = getChildNodesNotInClass(classPair.mClazz, MethodDeclaration.class);

                for (MethodDeclaration methodDeclaration : methodList) {

                    String name = getMethodFullDeclaration(methodDeclaration);

                    if (map.containsKey(name) && map.get(name).getNodeList().equals(methodDeclaration.getParameters())) {
                        map.remove(name);
                    } else {
                        String nameWithoutArg = getMethodNameWithoutArgs(classPair.mName, methodDeclaration);
                        Iterator it = map.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, Method> item = (Map.Entry<String, Method>) it.next();
                            if (item.getValue().getNameWithoutArgs().equals(nameWithoutArg)) {
                                if (methodDeclaration.getParameters().containsAll(item.getValue().getNodeList())
                                        && item.getValue().getNodeList().size() < methodDeclaration.getParameters().size()) {
                                    changeList.add(new Changes(item.getValue().getSignature(), getMethodFullDeclaration(methodDeclaration)));
                                    it.remove();
                                }
                            }
                        }
                    }
                }
            }
        }

        return changeList;
    }
}