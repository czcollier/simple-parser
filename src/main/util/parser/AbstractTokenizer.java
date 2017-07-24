package util.parser;

import java.io.IOException;
import java.io.Reader;

public abstract class AbstractTokenizer extends Scanner {
    protected static final int WHITESPACE = 0x1;
    protected static final int ALPHA = 0x2;
    protected static final int NUMERIC = 0x4;

    protected static final int ALNUM = ALPHA | NUMERIC;

    protected CharacterSet charSet = new CharacterSet();
    protected Reader reader;

    protected abstract void setupCharacters();
    protected abstract void nextToken();

    private boolean compressWhitespace = true;

    public AbstractTokenizer(Reader reader) {
        this.reader = reader;
        setupBasicCharacters();
        setupCharacters();
        init();
    }

    public final void init() {
        if (peekc < 0)
            read();
    }

    @Override
    public final int read() {
        try {
            int r = reader.read();
            if (r == -1)
                r = CharacterSet.EOF;
            return peekc = r;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String readWhitespace() {
        if (compressWhitespace) {
            eatWhitespace();
            return isEOF() ? null : " ";
        }

        return readFor(WHITESPACE);
    }

    protected void eatWhitespace() {
        eatFor(WHITESPACE);
    }

    protected void eatWord() {
        eatFor(~WHITESPACE);
    }

    protected String readWord() {
        return readFor(~WHITESPACE);
    }

    protected String readTimes(int count) {
        for (int i = 0; i < count; i++) {
            if (isEOF()) break;

            buf.append(peekc);
            read();
        }

        return buf.done();
    }

    public int eatTimes(int count) {
        int i;
        for (i = 0; i < count; i++) {
            if (isEOF()) break;
            read();
        }

        return i;
    }

    @Override
    public boolean isClass(int charClass) {
        return charSet.isClass(peekc, charClass);
    }

    @Override
    public boolean is(int chr) {
        return peekc == chr;
    }

    @Override
    public boolean isEOF() {
        return peekc == CharacterSet.EOF;
    }

    @Override
    public int getCharClass() {
        return charSet.getClass(peekc);
    }

    protected void addCharClass(int low, int high, int charClass) {
        charSet.addClass(low, high, charClass);
    }
    protected void addToCharClass(int pos, int charClass) {
        charSet.addToClass(pos, charClass);
    }
    
    protected void setupBasicCharacters() {
        charSet.addClass(0, ' ', WHITESPACE);
        charSet.addClass(' ' + 1, '0' - 1, ALPHA);
        charSet.addClass('0', '9', NUMERIC);
        charSet.addClass('9' + 1, 255, ALPHA);
    }
}
