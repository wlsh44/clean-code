package ch14.draft2.marshaler;

import ch14.draft2.ArgsException;

public abstract class ArgumentMarshaler {
    public abstract void set(String s) throws ArgsException;
    public abstract Object get();
}
