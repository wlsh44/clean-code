package ch14.draft2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

class ArgsDraft2Test {

    @Test
    void basicTest() {
        try {
            String[] args = new String[5];
            args[0] = "-l";
            args[1] = "-p";
            args[2] = "123";
            args[3] = "-d";
            args[4] = "hello world";
            Args arg = new Args("l,p#,d*", args);

            int p = arg.getInteger('p');
            boolean l = arg.getBoolean('l');
            String s = arg.getString('d');

            assertThat(p).isEqualTo(123);
            assertThat(l).isTrue();
            assertThat(s).isEqualTo("hello world");
        } catch (Exception e) {
        }
    }

    @Test
    @DisplayName("지정 안 한 인자")
    void errorTest_unexpectedArgument() throws Exception {
        try {
            String[] args = new String[1];
            args[0] = "-a";

            Args arg = new Args("l,p#,d*", args);
            boolean valid = arg.isValid();

            assertThat(arg.has('a')).isFalse();
            assertThat(valid).isFalse();
            assertThat(arg.errorMessage()).isEqualTo("Argument(s) -a unexpected.");
        } catch (ParseException e) {
        }
    }

    @Test
    @DisplayName("정수에 문자")
    void errorTest_invalidInteger() throws Exception {
        try {
            String[] args = new String[2];
            args[0] = "-p";
            args[1] = "asdf";

            Args arg = new Args("l,p#,d*", args);
            boolean valid = arg.isValid();

            assertThat(valid).isFalse();
            assertThat(arg.errorMessage()).isEqualTo("Argument -p expects an integer but was asdf");
        } catch (ParseException e) {
        }
    }

    @Test
    @DisplayName("정수 인자 없음")
    void errorTest_missingInteger() throws Exception {
        try {
            String[] args = new String[1];
            args[0] = "-p";

            Args arg = new Args("l,p#,d*", args);
            boolean valid = arg.isValid();

            assertThat(valid).isFalse();
            assertThat(arg.errorMessage()).isEqualTo("Could not find integer parameter for -p");
        } catch (ParseException e) {
        }
    }

    @Test
    @DisplayName("문자 인자 없음")
    void errorTest_missingString() throws Exception {
        try {
            String[] args = new String[1];
            args[0] = "-d";

            Args arg = new Args("l,p#,d*", args);
            boolean valid = arg.isValid();

            assertThat(valid).isFalse();
            assertThat(arg.errorMessage()).isEqualTo("Could not find string parameter for -d");
        } catch (ParseException e) {
        }
    }
}