package cn.hiboot.framework.research.asm;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author DingHao
 * @since 2019/4/3 17:34
 */
public class AsmDemo {

    static class AsmVisitor extends ClassVisitor {

        public AsmVisitor() {
            this(Opcodes.ASM7);
        }

        public AsmVisitor(int api) {
            super(api);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public void visitSource(String source, String debug) {
            super.visitSource(source, debug);
        }

        @Override
        public ModuleVisitor visitModule(String name, int access, String version) {
            return super.visitModule(name, access, version);
        }

        @Override
        public void visitNestHost(String nestHost) {
            super.visitNestHost(nestHost);
        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {
            super.visitOuterClass(owner, name, descriptor);
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
            return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
        }

        @Override
        public void visitAttribute(Attribute attribute) {
            super.visitAttribute(attribute);
        }

        @Override
        public void visitNestMember(String nestMember) {
            super.visitNestMember(nestMember);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            super.visitInnerClass(name, outerName, innerName, access);
        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            return super.visitField(access, name, descriptor, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
        }
    }

    @Test
    public void classReader() throws IOException {
        ClassReader classReader = new ClassReader(getClass().getResourceAsStream("/cn/hiboot/TestBase.class"));
        classReader.accept(new AsmVisitor(),ClassReader.SKIP_DEBUG);
    }


    /**
     * asm 操作字节码
     */
    @Test
    public void classWriter() {
        ClassWriter classWriter = new ClassWriter(0);
        String className = "cn/hiboot/frame/asm/HelloWorld";
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);

        MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        initVisitor.visitCode();
        initVisitor.visitVarInsn(Opcodes.ALOAD, 0);
        initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "V()",false);
        initVisitor.visitInsn(Opcodes.RETURN);
        initVisitor.visitMaxs(1, 1);
        initVisitor.visitEnd();

        MethodVisitor helloVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "sayHello", "()V;", null, null);
        helloVisitor.visitCode();
        System.out.println();
        helloVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        helloVisitor.visitLdcInsn("hello world!");
        helloVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V",false);
        helloVisitor.visitInsn(Opcodes.RETURN);
        helloVisitor.visitMaxs(1, 1);
        helloVisitor.visitEnd();

        classWriter.visitEnd();

        byte[] code = classWriter.toByteArray();

        try (FileOutputStream output = new FileOutputStream(Paths.get("/IDEAProject/frame-demo/target/classes/cn/hiboot/frame/asm/HelloWorld.class").toFile())) {
            output.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
