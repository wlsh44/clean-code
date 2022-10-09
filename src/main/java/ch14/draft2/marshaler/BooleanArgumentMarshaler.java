package ch14.draft2.marshaler;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {
    private boolean booleanValue = false;

    @Override
    public void set(String s) {
        this.booleanValue = true;
    }

    @Override
    public Object get() {
        return this.booleanValue;
    }

}
