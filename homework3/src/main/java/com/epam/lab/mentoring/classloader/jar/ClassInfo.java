package com.epam.lab.mentoring.classloader.jar;

import org.clapper.util.classutil.ClassUtilException;
import org.clapper.util.classutil.FieldInfo;
import org.clapper.util.classutil.MethodInfo;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class ClassInfo extends EmptyVisitor {
    int ASM_CR_ACCEPT_CRITERIA = 0;
    private int modifier = 0;
    private String className = null;
    private String superClassName = null;
    private String[] implementedInterfaces = null;
    private File locationFound = null;
    private Set<FieldInfo> fields = new HashSet<>();
    private Set<MethodInfo> methods = new HashSet<>();

    public ClassInfo(File classFile) throws ClassUtilException {
        try {
            ClassReader ex = new ClassReader(new FileInputStream(classFile));
            ex.accept(this, ASM_CR_ACCEPT_CRITERIA);
        } catch (IOException exc) {
            String msg = String.format("Unable to load file [%s].", classFile.getPath());
            throw new ClassUtilException(msg, exc);
        }
    }

    public ClassInfo(InputStream is) throws ClassUtilException {
        try {
            ClassReader ex = new ClassReader(is);
            ex.accept(this, ASM_CR_ACCEPT_CRITERIA);
        } catch (IOException exc) {
            throw new ClassUtilException("Unable to load class from open input stream", exc);
        }
    }

    ClassInfo(String name, String superClassName, String[] interfaces, int asmAccessMask, File location) {
        this.setClassFields(name, superClassName, interfaces, asmAccessMask, location);
    }

    public String getClassName() {
        return this.className;
    }

    public String getSuperClassName() {
        return this.superClassName;
    }

    public String[] getInterfaces() {
        return this.implementedInterfaces;
    }

    public int getModifier() {
        return this.modifier;
    }

    public File getClassLocation() {
        return this.locationFound;
    }

    public Set<FieldInfo> getFields() {
        return this.fields;
    }

    public Set<MethodInfo> getMethods() {
        return this.methods;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        if((this.modifier & 1) != 0) {
            buf.append("public ");
        }

        if((this.modifier & 1024) != 0) {
            buf.append("abstract ");
        }

        if((this.modifier & 512) != 0) {
            buf.append("interface ");
        } else {
            buf.append("class ");
        }

        buf.append(this.className);
        String sep = " ";
        if(this.implementedInterfaces.length > 0) {
            buf.append(" implements");
            String[] arr = this.implementedInterfaces;
            int len = arr.length;

            for(int i = 0; i < len; ++i) {
                String intf = arr[i];
                buf.append(sep);
                buf.append(intf);
            }
        }

        if(this.superClassName != null && !this.superClassName.equals("java.lang.Object")) {
            buf.append(sep);
            buf.append("extends ");
            buf.append(this.superClassName);
        }

        return buf.toString();
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.setClassFields(name, superName, interfaces, access, (File)null);
    }

    public FieldVisitor visitField(int access, String name, String description, String signature, Object value) {
        this.fields.add(new FieldInfo(access, name, description, signature, value));
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String description, String signature, String[] exceptions) {
        this.methods.add(new MethodInfo(access, name, description, signature, exceptions));
        return null;
    }

    private String translateInternalClassName(String internalName) {
        return internalName.replaceAll("/", ".");
    }

    private void setClassFields(String name, String superClassName, String[] interfaces, int asmAccessMask, File location) {
        this.className = this.translateInternalClassName(name);
        this.locationFound = location;
        if(superClassName != null && !superClassName.equals("java/lang/Object")) {
            this.superClassName = this.translateInternalClassName(superClassName);
        }

        if(interfaces != null) {
            this.implementedInterfaces = new String[interfaces.length];

            for(int i = 0; i < interfaces.length; ++i) {
                this.implementedInterfaces[i] = this.translateInternalClassName(interfaces[i]);
            }
        }

        this.modifier = this.convertAccessMaskToModifierMask(asmAccessMask);
    }

    private int convertAccessMaskToModifierMask(int asmAccessMask) {
        int modifier = 0;
        if((asmAccessMask & 16) != 0) {
            modifier |= 16;
        }

        if((asmAccessMask & 256) != 0) {
            modifier |= 256;
        }

        if((asmAccessMask & 512) != 0) {
            modifier |= 512;
        }

        if((asmAccessMask & 1024) != 0) {
            modifier |= 1024;
        }

        if((asmAccessMask & 2) != 0) {
            modifier |= 2;
        }

        if((asmAccessMask & 4) != 0) {
            modifier |= 4;
        }

        if((asmAccessMask & 1) != 0) {
            modifier |= 1;
        }

        if((asmAccessMask & 8) != 0) {
            modifier |= 8;
        }

        if((asmAccessMask & 2048) != 0) {
            modifier |= 2048;
        }

        if((asmAccessMask & 32) != 0) {
            modifier |= 32;
        }

        if((asmAccessMask & 128) != 0) {
            modifier |= 128;
        }

        if((asmAccessMask & 64) != 0) {
            modifier |= 64;
        }

        return modifier;
    }

    public File getFileLocation() {
        return locationFound;
    }
}
