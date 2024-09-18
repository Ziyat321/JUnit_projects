import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class FortuneCookieControllerTest {

    @Test
    public void shouldReturnPositiveFortune(){
        FortuneCookieFactory factory = new FortuneCookieFactory(
                new FortuneConfig(true),
                List.of("Good"),
                List.of("Bad")
        );
        FortuneCookieController factoryController = new FortuneCookieController(factory);

        FortuneCookie cookie = factoryController.tellFortune();

        assertEquals("Good", cookie.getFortuneText());
    }

    @Test
    public void shouldReturnNegativeFortune(){
        FortuneCookieFactory factory = new FortuneCookieFactory(
                new FortuneConfig(false),
                List.of("Good"),
                List.of("Bad")
        );
        FortuneCookieController factoryController = new FortuneCookieController(factory);

        FortuneCookie cookie = factoryController.tellFortune();

        assertEquals("Bad", cookie.getFortuneText());
    }
}
