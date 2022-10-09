package ch14.draft2.marshaler;

public class StringArgumentMarshaler extends ArgumentMarshaler {
    private String stringValue;

    @Override
    public void set(String string) {
        this.stringValue = string;
    }

    @Override
    public Object get() {
        return stringValue;
    }
}
