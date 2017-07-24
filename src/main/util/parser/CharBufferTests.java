package util.parser;

import java.nio.CharBuffer;

public class CharBufferTests {
    public static void main(String[] args) {
        CharBuffer cb = CharBuffer.allocate(100);
        cb.put("christopher");
        cb.rewind();
        println("1: " + cb.toString());
        cb.clear();
        cb.put('z');
        cb.rewind();
        println("2: " + cb.toString());
    }

    private static void println(String str) {
        System.out.println(str);
    }
}
