package io.luan.jerry.common;

import io.luan.jerry.category.domain.PropertyOption;
import io.luan.jerry.common.enumeration.FlagEnumUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.EnumSet;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class FlagEnumTests {

    @Test
    public void required() {

        var set = EnumSet.of(PropertyOption.Required);
        var encoded = FlagEnumUtils.encode(set);
        System.out.println(set);
        System.out.println(encoded);

        var decoded = FlagEnumUtils.decode(encoded, PropertyOption.class);
        System.out.println(decoded);

    }

    @Test
    public void mutable() {

        var set = EnumSet.of(PropertyOption.Mutable);
        var encoded = FlagEnumUtils.encode(set);
        System.out.println(set);
        System.out.println(encoded);

        var decoded = FlagEnumUtils.decode(encoded, PropertyOption.class);
        System.out.println(decoded);

    }
    @Test
    public void encodeEmpty() {

        var set = EnumSet.noneOf(PropertyOption.class);
        var encoded = FlagEnumUtils.encode(set);
        System.out.println(set);
        System.out.println(encoded);

        var decoded = FlagEnumUtils.decode(encoded, PropertyOption.class);
        System.out.println(decoded);

    }
    @Test
    public void decode0() {

        var decoded = FlagEnumUtils.decode(0, PropertyOption.class);
        System.out.println(decoded);

    }

    @Test
    public void decodeNeg() {

        Assertions.assertThrows(RuntimeException.class, () -> {
            var decoded = FlagEnumUtils.decode(-1, PropertyOption.class);
            System.out.println(decoded);
        });

    }
}
