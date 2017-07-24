package util.parser;

/**
 * Implements a table in which to store information about groupings of characters
 * within a character set.  For example, the ASCII character set has 128 different
 * characters.  Characters 0-32 are non-alphanumeric, special characters and thus
 * could be grouped as "special" or "whitespace" or something else indicative of
 * their common use.  Likewise, characters 30-39 are the Arabic numerals and can
 * be grouped as such.
 * <p>
 * Each entry in the table can be associated with a given numeric constant that
 * represents the class (group) of the character for that entry.  Numeric constants
 * are up to the user of this class to determine.
 * <p>
 * Use {@link #addClass} to add a range of characters to a class.  Use
 * {@link #addToClass} to add a single character to a class.
 *
 * @author Chris Collier
 */
public class CharacterSet {

    public static final int EOF = -2;
    public static final int DEFAULT_TABLE_SIZE = 256;
    private int[] _table;

    public CharacterSet() {
        _table = new int[DEFAULT_TABLE_SIZE];
    }

    public CharacterSet(int size) {
        _table = new int[size];
    }

    public int getClass(int c) {
        return _table[c];
    }

    public boolean isClass(int c, int charClass) {
        if (c == EOF) {
            return charClass == EOF;
        }

        if (c >= _table.length) {
            return false;
        }

        return (_table[c] & charClass) != 0;
    }

    public boolean isNotClass(int c, int charClass) {
        if (c == EOF) {
            return charClass == EOF;
        }

        if (c >= _table.length) {
            return true;
        }

        return (_table[c] & charClass) == 0;
    }

    public void addClass(int low, int high, int charClass) {
        if (low > high || outOfBounds(low) || outOfBounds(high)) {
            throw new IllegalArgumentException("invalid table index");
        }

        for (int i = low; i <= high; i++) {
            _table[i] = charClass;
        }
    }

    public void addToClass(int pos, int charClass) {
        if (outOfBounds(pos)) {
            throw new IllegalArgumentException("invalid table index");
        }

        _table[pos] = charClass;
    }

    private boolean outOfBounds(int idx) {
        return (idx < 0 || idx >= _table.length);
    }
}
