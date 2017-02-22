package com.epam.lab.mentoring.classloader.jar;
/*
import com.epam.lab.mentoring.classloader.ExtensionLoader;
import org.clapper.util.classutil.*;
import org.clapper.util.io.*;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
*/

/**
 * Created by Chucke on 22.02.2017.
 */
public class Stub {
    /*
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionLoader.class);

    private LinkedHashMap<String, File> placesToSearch = new LinkedHashMap<>();
    private Map<String, ClassInfo> foundClasses = new LinkedHashMap<>();
    int findClasses(Collection<org.clapper.util.classutil.ClassInfo> classes, ClassFilter filter) {
        int total = 0;
        this.foundClasses.clear();
        Iterator i = this.placesToSearch.values().iterator();

        String className;
        while(i.hasNext()) {
            File classInfo = (File)i.next();
            className = classInfo.getPath();
            LOGGER.info("Finding classes in [{}].", className);
            if(this.isJar(className)) {
                this.processJar(className, this.foundClasses);
            } else {
                this.processDirectory(classInfo, this.foundClasses);
            }
        }

        LOGGER.info("Loaded [{}] classes.", this.foundClasses.size());
        i = this.foundClasses.values().iterator();

        while(true) {
            while(i.hasNext()) {
                org.clapper.util.classutil.ClassInfo clz = (org.clapper.util.classutil.ClassInfo)i.next();
                className = clz.getClassName();
                String locationName = clz.getClassLocation().getPath();
                LOGGER.debug("Looking at [{}] ({}).", locationName, className);
                if(filter != null && !filter.accept(clz, this)) {
                    LOGGER.debug("Filter rejected [{}].", className);
                } else {
                    LOGGER.debug("Filter accepted [{}].", className);
                    ++total;
                    classes.add(clz);
                }
            }

            LOGGER.info("Returning [{}] total classes.", total);
            this.foundClasses.clear();
            return total;
        }
    }

    private boolean add(File file) {
        boolean added = false;
        if(ClassUtil.fileCanContainClasses(file)) {
            String absPath = file.getAbsolutePath();
            if(this.placesToSearch.get(absPath) == null) {
                this.placesToSearch.put(absPath, file);
                if(this.isJar(absPath)) {
                    this.loadJarClassPathEntries(file);
                }
            }
            added = true;
        }
        return added;
    }

    private boolean isJar(String fileName) {
        return fileName.toLowerCase().endsWith(".jar");
    }

    private void processJar(String jarName, Map<String, org.clapper.util.classutil.ClassInfo> foundClasses) {
        JarFile jar = null;

        try {
            jar = new JarFile(jarName);
            File ex = new File(jarName);
            this.processOpenZip(jar, ex, new ClassInfoClassVisitor(foundClasses, ex));
        } catch (IOException exc) {
            LOGGER.error("Can't open jar file [{}].", jarName, exc);
        } finally {
            try {
                if(jar != null) {
                    jar.close();
                }
            } catch (IOException exc) {
                LOGGER.error("Can't close [{}].", jarName, exc);
            }

            jar = null;
        }

    }

    private void processOpenZip(ZipFile zip, File zipFile, ClassVisitor classVisitor) {
        String zipName = zipFile.getPath();
        Enumeration e = zip.entries();

        while(e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry)e.nextElement();
            if(!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".class")) {
                try {
                    LOGGER.debug("Loading [{}] ({}).", zipName, entry.getName());
                    this.loadClassData(zip.getInputStream(entry), classVisitor);
                } catch (IOException exc) {
                    LOGGER.error("Can't open [{}] in zip file [{}].", entry.getName(), zipName, exc);
                } catch (ClassUtilException exc) {
                    LOGGER.error("Can't open [{}] in zip file [{}].", entry.getName(), zipName, exc);
                }
            }
        }

    }

    private void loadClassData(InputStream is, ClassVisitor classVisitor) throws ClassUtilException {
        try {
            ClassReader ex = new ClassReader(is);
            ex.accept(classVisitor, 0);
        } catch (Exception exc) {
            throw new ClassUtilException("Unable to load class from open input stream", exc);
        }
    }

    private void loadJarClassPathEntries(File jarFile) {
        try {
            JarFile ex = new JarFile(jarFile);
            Manifest manifest = ex.getManifest();
            if(manifest != null) {
                Map map = manifest.getEntries();
                Attributes attrs = manifest.getMainAttributes();
                Set keys = attrs.keySet();
                Iterator i = keys.iterator();

                while(true) {
                    Object key;
                    String value;
                    do {
                        if(!i.hasNext()) {
                            return;
                        }

                        key = i.next();
                        value = (String)attrs.get(key);
                    } while(!key.toString().equals("Class-Path"));

                    String jarName = ex.getName();
                    LOGGER.debug("Adding Class-Path from jar [{}].", jarName);
                    StringBuilder buf = new StringBuilder();

                    String element;
                    for(StringTokenizer tok = new StringTokenizer(value); tok.hasMoreTokens(); buf.append(element)) {
                        buf.setLength(0);
                        element = tok.nextToken();
                        String parent = jarFile.getParent();
                        if(parent != null) {
                            buf.append(parent);
                            buf.append(File.separator);
                        }
                    }

                    element = buf.toString();
                    LOGGER.debug("From [{}] : [{}].", jarName, element);
                    this.add(new File(element));
                }
            }
        } catch (IOException exc) {
            LOGGER.error("I/O error processing jar file [{}].", jarFile.getPath(), exc);
        }
    }

    private void processDirectory(File dir, Map<String, org.clapper.util.classutil.ClassInfo> foundClasses) {
        RecursiveFileFinder finder = new RecursiveFileFinder();
        RegexFileFilter nameFilter = new RegexFileFilter("\\.class$", FileFilterMatchType.FILENAME);
        AndFileFilter fileFilter = new AndFileFilter(nameFilter, new FileOnlyFilter());
        ArrayList files = new ArrayList();
        finder.findFiles(dir, fileFilter, files);
        ClassInfoClassVisitor classVisitor = new ClassInfoClassVisitor(foundClasses, dir);
        Iterator i = files.iterator();

        while(i.hasNext()) {
            File f = (File) i.next();
            String path = f.getPath();
            LOGGER.debug("Loading [{}].", f.getPath());
            FileInputStream is = null;

            try {
                is = new FileInputStream(f);
                this.loadClassData(is, classVisitor);
            } catch (ClassUtilException | IOException exc) {
                LOGGER.error("Can't open [{}].", path, exc);
            } finally {
                if(is != null) {
                    try {
                        is.close();
                    } catch (IOException exc) {
                        LOGGER.error("Can't close InputStream for [{}].", path, exc);
                    }
                }
            }
        }

    }
    */
}
