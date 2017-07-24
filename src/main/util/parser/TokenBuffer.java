package util.parser;

/**
 * A simple buffer with which to collect characters.  Implemented
 * as an array of type <code>char[]</code> that doubles in size if its length
 * is exceeded by an <code>append</code> operation.
 *
 * @author Chris Collier
 * @version $Revision: 1.5 $ $Date: 2006/09/20 13:34:24 $
 */
public class TokenBuffer {
    public static final int DEFAULT_INITIAL_CAPACITY = 16;
    private char[] _buf;
    private int _size = 0;
    private boolean _cleared = true;
    private int initialCapacity;

    public TokenBuffer() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public TokenBuffer(int initialCapacity) {
        _buf = new char[initialCapacity];
        this.initialCapacity = initialCapacity;
    }

    public void append(int c) {
        append((char) c);
    }

    public int size() {
        return _size;
    }

    /**
     * Appends character <code>c</code> to the internal buffer and
     * increases the size of the buffer if necessary.
     *
     * @param c character to append to the buffer.
     */
    public void append(char c) {
        if (_size >= _buf.length) {
            grow();
        }
        _buf[_size++] = c;

        _cleared = false;
    }

    /**
     * Empties buffer and resets its size to zero.
     * <p>
     * <i>Implementation note:</i> if the buffer's length
     * is not bigger than the size it was initialized to,
     * it is cleared by setting all positions to '\0'. Otherwise
     * a new buffer array is allocated.  Hopefully we save some
     * memory allocation/garbage collection this way.
     */
    public void clear() {
        if (_buf.length > initialCapacity) {
            _buf = new char[initialCapacity];
        } else {
            for (int i = 0; i < _buf.length; i++) {
                _buf[i] = '\0';
            }
        }
        _size = 0;
        _cleared = true;
    }

    /**
     * Clears the buffer and returns the buffer's <code>String</code> representation
     * before the buffer was cleared.
     *
     * @return <code>String</code> representation of the buffer
     */
    public String done() {
        String retVal = toString();

        clear();

        return retVal;
    }

    /**
     * Returns a <code>String</code> representation of the internal buffer.
     * @return <code>String</code> representation of the internal buffer
     */
    @Override
    public String toString() {
        return String.copyValueOf(_buf, 0, _size);
    }

    private void grow() {
        char tmpBuf[] = new char[_buf.length * 2];

        System.arraycopy(_buf, 0, tmpBuf, 0, _buf.length);

        _buf = tmpBuf;
    }

    public boolean isCleared() {
        return _cleared;
    }
}

