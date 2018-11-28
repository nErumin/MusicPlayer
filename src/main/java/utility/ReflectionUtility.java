package utility;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtility {
    public static List<Class<?>> getClassesInPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();

        try (ScanResult scanResult = new ClassGraph().enableClassInfo()
                                                     .whitelistPackages(packageName)
                                                     .scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                classes.add(classInfo.loadClass());
            }
        }

        return classes;
    }

    private ReflectionUtility() {

    }
}
