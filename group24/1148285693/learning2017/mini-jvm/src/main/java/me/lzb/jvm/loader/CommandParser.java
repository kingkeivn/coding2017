package me.lzb.jvm.loader;

import me.lzb.common.utils.StringUtils;
import me.lzb.jvm.clz.ClassFile;
import me.lzb.jvm.cmd.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LZB on 2017/4/22.
 */
public class CommandParser {
    public static final String aconst_null = "01";
    public static final String new_object = "BB";
    public static final String lstore = "37";
    public static final String invokespecial = "B7";
    public static final String invokevirtual = "B6";
    public static final String getfield = "B4";
    public static final String putfield = "B5";
    public static final String getstatic = "B2";
    public static final String ldc = "12";
    public static final String dup = "59";
    public static final String bipush = "10";
    public static final String aload_0 = "2A";
    public static final String aload_1 = "2B";
    public static final String aload_2 = "2C";
    public static final String iload = "15";
    public static final String iload_1 = "1B";
    public static final String iload_2 = "1C";
    public static final String iload_3 = "1D";
    public static final String fload_3 = "25";

    public static final String voidreturn = "B1";
    public static final String ireturn = "AC";
    public static final String freturn = "AE";

    public static final String astore_1 = "4C";
    public static final String if_icmp_ge = "A2";
    public static final String if_icmple = "A4";
    public static final String goto_no_condition = "A7";
    public static final String iconst_0 = "03";
    public static final String iconst_1 = "04";
    public static final String istore_1 = "3C";
    public static final String istore_2 = "3D";
    public static final String iadd = "60";
    public static final String iinc = "84";


    private int index;

    private final char[] data;

    public CommandParser(String codes) {
        if (StringUtils.isBlank(codes)) {
            throw new RuntimeException("the orignal code is not correct!");
        }
        codes = codes.toUpperCase();
        data = codes.toCharArray();
    }

    private char[] nextChars(int nextLength) {
        char[] target = new char[nextLength];
        System.arraycopy(data, index, target, 0, nextLength);
        index = index + nextLength;
        return target;
    }

    private int nextCharsToInt(int nextLength) {
        return Integer.valueOf(String.valueOf(nextChars(nextLength)), 16).intValue();
    }

    private String nextCharsToString(int nextLength) {
        return String.valueOf(nextChars(nextLength));
    }

    private boolean hasNext() {
        return index < data.length;
    }


    public ByteCodeCommand[] parse(ClassFile clzFile) {

        List<ByteCodeCommand> cmds = new ArrayList<>();

        while (hasNext()) {
            String opCode = nextCharsToString(2);

            if (new_object.equals(opCode)) {
                NewObjectCmd cmd = new NewObjectCmd(clzFile, opCode);

                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));

                cmds.add(cmd);
            } else if (invokespecial.equals(opCode)) {
                InvokeSpecialCmd cmd = new InvokeSpecialCmd(clzFile, opCode);

                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));

                cmds.add(cmd);
            } else if (invokevirtual.equals(opCode)) {
                InvokeSpecialCmd cmd = new InvokeSpecialCmd(clzFile, opCode);

                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));

                cmds.add(cmd);
            } else if (getfield.equals(opCode)) {
                GetFieldCmd cmd = new GetFieldCmd(clzFile, opCode);
                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));

                cmds.add(cmd);
            } else if (getstatic.equals(opCode)) {
                GetStaticFieldCmd cmd = new GetStaticFieldCmd(clzFile, opCode);

                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));

                cmds.add(cmd);
            } else if (putfield.equals(opCode)) {
                PutFieldCmd cmd = new PutFieldCmd(clzFile, opCode);
                cmd.setOprand1(nextCharsToInt(2));
                cmd.setOprand2(nextCharsToInt(2));
                cmds.add(cmd);
            } else if (ldc.equals(opCode)) {
                LdcCmd cmd = new LdcCmd(clzFile, opCode);
                cmd.setOperand(nextCharsToInt(2));
                cmds.add(cmd);
            } else if (bipush.equals(opCode)) {
                BiPushCmd cmd = new BiPushCmd(clzFile, opCode);
                cmd.setOperand(nextCharsToInt(2));
                cmds.add(cmd);
            } else if (dup.equals(opCode) || aload_0.equals(opCode) || aload_1.equals(opCode) || aload_2.equals(opCode)
                || iload_1.equals(opCode) || iload_2.equals(opCode) || iload_3.equals(opCode)
                || fload_3.equals(opCode) || voidreturn.equals(opCode) || astore_1.equals(opCode)) {

                NoOperandCmd cmd = new NoOperandCmd(clzFile, opCode);
                cmds.add(cmd);
            } else {
                throw new RuntimeException("Sorry, the java instruction " + opCode + "has not been implement.");
            }
        }

        calcuateOffset(cmds);

        ByteCodeCommand[] result = new ByteCodeCommand[cmds.size()];
        cmds.toArray(result);
        return result;
    }

    private static void calcuateOffset(List<ByteCodeCommand> cmds) {

        int offset = 0;
        for (ByteCodeCommand cmd : cmds) {
            cmd.setOffset(offset);
            offset += cmd.getLength();
        }

    }

    private static class CommandIterator {
        String codes = null;
        int pos = 0;

        CommandIterator(String codes) {
            this.codes = codes;
        }

        public boolean hasNext() {
            return pos < this.codes.length();
        }

        public String next2CharAsString() {
            String result = codes.substring(pos, pos + 2);
            pos += 2;
            return result;
        }

        public int next2CharAsInt() {
            String s = this.next2CharAsString();
            return Integer.valueOf(s, 16).intValue();
        }

    }
}