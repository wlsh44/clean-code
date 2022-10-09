package ch14.draft2;

import ch14.draft2.marshaler.ArgumentMarshaler;
import ch14.draft2.marshaler.BooleanArgumentMarshaler;
import ch14.draft2.marshaler.DoubleArgumentMarshaler;
import ch14.draft2.marshaler.IntegerArgumentMarshaler;
import ch14.draft2.marshaler.StringArgumentMarshaler;

import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;

import static ch14.draft2.ErrorCode.INVALID_ARGUMENT_NAME;
import static ch14.draft2.ErrorCode.UNEXPECTED_ARGUMENT;

public class Args {
    private String schema;
    private List<String> argsList;
    private Map<Character, ArgumentMarshaler> marshaler = new HashMap<>();
    private Set<Character> argsFound = new HashSet<>();
    private Iterator<String> currentArgument;

    public Args(String schema, String[] argsList) throws ArgsException {
        this.schema = schema;
        this.argsList = List.of(argsList);
        parse();
    }

    private void parse() throws ArgsException {
        parseSchema();
        parseArguments();
    }

    private boolean parseArguments() throws ArgsException {
        for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
            String arg = currentArgument.next();
            parseArgument(arg);
        }
        return true;
    }

    private void parseArgument(String arg) throws ArgsException {
        if (arg.startsWith("-")) {
            parseElements(arg);
        }
    }

    private void parseElements(String arg) throws ArgsException {
        for (int i = 1; i < arg.length(); i++) {
            parseElement(arg.charAt(i));
        }
    }

    private void parseElement(char argChar) throws ArgsException {
        if (setArgument(argChar)) {
            argsFound.add(argChar);
        } else {
            throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
        }
    }

    private boolean setArgument(char argChar) throws ArgsException {
        ArgumentMarshaler m = marshaler.get(argChar);
        if (m == null) {
            return false;
        }
        try {
            if (m instanceof BooleanArgumentMarshaler) {
                m.set(currentArgument);
            } else if (m instanceof StringArgumentMarshaler) {
                m.set(currentArgument);
            } else if (m instanceof IntegerArgumentMarshaler) {
                m.set(currentArgument);
            } else if (m instanceof DoubleArgumentMarshaler) {
                m.set(currentArgument);
            }
        } catch (ArgsException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
        return true;
    }

    private boolean parseSchema() throws ArgsException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                String trimmedElement = element.trim();
                parseSchemaElement(trimmedElement);
            }
        }
        return true;
    }

    private void parseSchemaElement(String element) throws ArgsException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);

        if (isBooleanSchemaElement(elementTail)) {
            marshaler.put(elementId, new BooleanArgumentMarshaler());
        } else if (isStringSchemaElement(elementTail)) {
            marshaler.put(elementId, new StringArgumentMarshaler());
        } else if (isIntegerSchemaElement(elementTail)) {
            marshaler.put(elementId, new IntegerArgumentMarshaler());
        } else if (isDoubleSchemaElement(elementTail)) {
            marshaler.put(elementId, new DoubleArgumentMarshaler());
        } else {
            throw new ArgsException(ErrorCode.INVALID_FORMAT, elementId, elementTail);
        }
    }

    private boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isDoubleSchemaElement(String elementTail) {
        return elementTail.equals("##");
    }

    private void validateSchemaElementId(char elementId) throws ArgsException {
        if (!Character.isLetter(elementId)) {
            throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, schema);
        }
    }

    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0) {
            return "-[" + schema + "]";
        } else {
            return "";
        }
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshaler.get(arg);
        try {
            return am == null ? "" : (String) am.get();
        } catch (ClassCastException e) {
            return "";
        }
    }

    public int getInteger(char arg) {
        ArgumentMarshaler am = marshaler.get(arg);
        try {
            return am == null ? 0 : (Integer) am.get();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public double getDouble(char arg) {
        ArgumentMarshaler am = marshaler.get(arg);
        try {
            return am == null ? 0 : (Double) am.get();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public boolean getBoolean(char arg) {
        ArgumentMarshaler am = marshaler.get(arg);
        boolean b = false;
        try {
            b = am != null && (Boolean) am.get();
        } catch (ClassCastException e) {
            b = false;
        }
        return b;
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }
}
