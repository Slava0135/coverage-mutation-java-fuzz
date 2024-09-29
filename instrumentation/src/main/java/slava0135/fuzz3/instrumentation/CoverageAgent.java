package slava0135.fuzz3.instrumentation;

import org.objectweb.asm.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.HashSet;

public class CoverageAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className.startsWith("slava0135/fuzz3/") && !className.contains("instrumentation")) {
                    return transformClass(className, classfileBuffer);
                }
                return null;
            }
        });
    }

    private static byte[] transformClass(String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    @Override
                    public void visitLineNumber(int line, Label start) {
                        super.visitLineNumber(line, start);
                        mv.visitLdcInsn(name);
                        mv.visitLdcInsn(Integer.toString(line));
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "slava0135/fuzz3/instrumentation/CoverageTracker", "logCoverage", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    }
                };
            }
        };
        var cv2 = new ClassVisitor(Opcodes.ASM9) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                return new MethodVisitor(Opcodes.ASM9) {
                    final String methodName = name;
                    @Override
                    public void visitMethodInsn(final int opcode, final String owner, final String name, final String descriptor, final boolean isInterface) {
                        var v = CallGraph.graph.get(methodName);
                        if (v == null) {
                            v = new HashSet<String>();
                        }
                        v.add(name);
                        CallGraph.graph.put(methodName, v);
                    }
                };
            }
        };
        cr.accept(cv, 0);
        cr.accept(cv2, 0);
        return cw.toByteArray();
    }
}