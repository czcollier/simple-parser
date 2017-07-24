package util.parser;

public abstract class Scanner {
    public abstract int read();
    public abstract int getCharClass();
    public abstract boolean isClass(int charClass);
    public abstract boolean is(int chr);
    public abstract boolean isEOF();

    protected static final int NEED_CHAR = Integer.MIN_VALUE;
    protected TokenBuffer buf = new TokenBuffer();

    protected int peekc = NEED_CHAR;
    
    protected String readFor(int charClass) {
        if (isEOF()) return null;

        if (!buf.isCleared()) buf.clear();

        while (isClass(charClass)) {
            buf.append(peekc);
            read();
        }

        if (buf.size() < 1)
            return null;

        return buf.done();
    }

    /**
     * Read for anything but <c>charClass</c>, provided input begins with
     * <tt>charClass</tt>
     */
    protected String readBetween(int charClass, boolean inclusive) {
        if (isEOF()) return null;

        String retval = null;

        if (isClass(charClass)) {
            int startClass = getCharClass();
            int startc = read();
            retval = readFor(~startClass);
            int endc = read();

            if (inclusive)
                retval = (char)startc + retval + (char)endc;
        }

        return retval;
    }

    protected String readBetween(int charClass) {
        return readBetween(charClass, false);
    }

    /**
     * Useful when we know the exact string to read for.  Reads characters
     * as long as input matches <tt>str</tt>.
     */
    protected boolean tryToRead(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (peekc < 0)
                return false;

            if (peekc == str.charAt(i)) {
                buf.append(peekc);
                read();
            }
            else
                return false;
        }

        return true;
    }

    /**
     * Eat characters as long as they are in <c>charClass</c>.
     */
    protected void eatFor(int charClass) {
        if (isEOF()) return;
        while (isClass(charClass))
            read();
    }
}
