package ch14.draft2.marshaler;

import ch14.draft2.ArgsException;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {
    private Integer integerValue;

    @Override
    public void set(String integer) throws ArgsException {
        try {
            this.integerValue = Integer.parseInt(integer);
        } catch (NumberFormatException e) {
            throw new ArgsException();
        }
    }

    @Override
    public Object get() {
        return this.integerValue;
    }
}
