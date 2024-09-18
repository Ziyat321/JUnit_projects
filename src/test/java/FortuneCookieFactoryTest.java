import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class FortuneCookieFactoryTest {
    public FortuneCookieFactory factory;

    @BeforeEach
    void setup(){
        factory = new FortuneCookieFactory(
                new FortuneConfig(true),
                List.of("Good"),
                List.of("Bad")
        );
    }

    @Test
    public void shouldIncrementCountByOneAfterOneCookieBaked(){

        factory.bakeFortuneCookie();
        int cookieNumberNext = factory.getCookiesBaked();
        int expectedIncrement = 1;

        assertEquals(expectedIncrement, cookieNumberNext);
    }

    @Test
    public void shouldIncrementCountByTwoAfterTwoCookiesBaked (){

        int cookNumber = 2;
        for (int i = 0; i < cookNumber; i++) {
            factory.bakeFortuneCookie();
        }
        int cookieNumberNext = factory.getCookiesBaked();

        assertEquals(cookNumber, cookieNumberNext);
    }

    @Test
    public void shouldSetCounterToZeroAfterResetCookieCreatedCall(){
        int expectedNumber = 0;
        factory.resetCookiesCreated();

        assertEquals(expectedNumber, factory.getCookiesBaked());
    }
}
