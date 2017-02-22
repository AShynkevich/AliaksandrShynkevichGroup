package com.epam.lab.mentoring.classloader.jar;

import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import java.io.File;
import java.util.Map;

public class ClassInfoClassVisitor extends EmptyVisitor {
    private Map<String, ClassInfo> foundClasses;
    private File location;
    private ClassInfo currentClass = null;

    public ClassInfoClassVisitor(Map<String, ClassInfo> foundClasses, File location) {
        this.foundClasses = foundClasses;
        this.location = location;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        ClassInfo classInfo = new ClassInfo(name, superName, interfaces, access, this.location);
        this.foundClasses.put(classInfo.getClassName(), classInfo);
        this.currentClass = classInfo;
    }

    public FieldVisitor visitField(int access, String name, String description, String signature, Object value) {
        assert this.currentClass != null;

        if(signature == null) {
            signature = description + " " + name;
        }

        return this.currentClass.visitField(access, name, description, signature, value);
    }

    public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
        assert this.currentClass != null;

        if(signature == null) {
            signature = name + description;
        }

        return this.currentClass.visitMethod(access, name, description, signature, exceptions);
    }

    public File getClassLocation() {
        return this.location;
    }
}
