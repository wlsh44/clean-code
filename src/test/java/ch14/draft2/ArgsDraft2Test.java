package ch14.draft2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static ch14.draft2.ErrorCode.INVALID_ARGUMENT_NAME;
import static ch14.draft2.ErrorCode.INVALID_DOUBLE;
import static ch14.draft2.ErrorCode.INVALID_FORMAT;
import static ch14.draft2.ErrorCode.INVALID_INTEGER;
import static ch14.draft2.ErrorCode.MISSING_DOUBLE;
import static ch14.draft2.ErrorCode.MISSING_INTEGER;
import static ch14.draft2.ErrorCode.MISSING_STRING;
import static ch14.draft2.ErrorCode.UNEXPECTED_ARGUMENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    void errorTest_unexpectedArgument() {
        String[] args = new String[1];
        args[0] = "-a";

        assertThatThrownBy(() ->new Args("l,p#,d*", args))
        .isInstanceOf(ArgsException.class)
        .hasMessage(UNEXPECTED_ARGUMENT.getErrorMessage('a'));
    }

    @Test
    @DisplayName("정수에 문자")
    void errorTest_invalidInteger() {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "asdf";

        assertThatThrownBy(() ->new Args("l,p#,d*", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(INVALID_INTEGER.getErrorMessage('p', args[1]));
    }

    @Test
    @DisplayName("정수 인자 없음")
    void errorTest_missingInteger() {
        String[] args = new String[1];
        args[0] = "-p";

        assertThatThrownBy(() ->new Args("l,p#,d*", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(MISSING_INTEGER.getErrorMessage('p'));
    }

    @Test
    @DisplayName("문자 인자 없음")
    void errorTest_missingString() {
        String[] args = new String[1];
        args[0] = "-d";

        assertThatThrownBy(() ->new Args("l,p#,d*", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(MISSING_STRING.getErrorMessage('d'));
    }

    @Test
    @DisplayName("double 테스트")
    void doubleTest() throws ArgsException {
        String[] args = new String[2];
        args[0] = "-a";
        args[1] = "123.45";

        Args arg = new Args("a##", args);

        assertThat(arg.cardinality()).isEqualTo(1);
        assertThat(arg.has('a')).isTrue();
        assertThat(arg.getDouble('a')).isEqualTo(123.45);
    }

    @Test
    @DisplayName("double에 문자")
    void errorTest_invalidDouble() {
        String[] args = new String[2];
        args[0] = "-a";
        args[1] = "asdf";

        assertThatThrownBy(() ->new Args("a##", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(INVALID_DOUBLE.getErrorMessage('a', args[1]));
    }

    @Test
    @DisplayName("double 인자 없음")
    void errorTest_missingDouble() {
        String[] args = new String[1];
        args[0] = "-a";

        assertThatThrownBy(() ->new Args("a##", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(MISSING_DOUBLE.getErrorMessage('a'));
    }

    @Test
    @DisplayName("잘못된 인자 이름")
    void errorTest_invalidArgumentName() {
        String[] args = new String[2];
        args[0] = "-1";
        args[1] = "123.2";

        assertThatThrownBy(() ->new Args("1##", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(INVALID_ARGUMENT_NAME.getErrorMessage('1', "1##"));
    }

    @Test
    @DisplayName("잘못된 포맷")
    void errorTest_invalidFormat() {
        String[] args = new String[2];
        args[0] = "-a";
        args[1] = "123.2";

        assertThatThrownBy(() ->new Args("a??", args))
                .isInstanceOf(ArgsException.class)
                .hasMessage(INVALID_FORMAT.getErrorMessage('a', "??"));
    }
}