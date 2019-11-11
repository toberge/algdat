import java.util.Objects;

public class BitString {
    final long bits;
    final byte length;

    public BitString(long bits, byte length) {
        this.bits = bits;
        this.length = length;
    }

    // TODO method for joining byte[]

    // it SHOULD work
    public byte appendToByte(byte base, byte used) {
        long temp = bits >> (length - 8 - used); // shift away all else than what we append
        return (byte) (base | temp); // add in all things from our bits
    }

    // it works
    public byte splitByte(byte startIndex) {
        // System.out.println(new BitString(bits >> (length - 8 - startIndex), (byte) 64));
        return (byte) (bits >> (length - startIndex - 8)); // get that byte
    }

    // get last of them bits
    public byte getRemainder(byte remaining) {
        return (byte) (bits >> (length - remaining));
    }


    // default equals works fine
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitString bitString = (BitString) o;
        return bits == bitString.bits &&
                length == bitString.length;
    }

    @Override
    public String toString() {
        String base = Long.toBinaryString(bits);
        if (base.length() < length) {
            StringBuilder bstring = new StringBuilder();
            for (int i = 0; i < length - base.length(); i++) {
                bstring.append("0");
            }
            bstring.append(base);
            base = bstring.toString();
        }
        return base;
    }
}
