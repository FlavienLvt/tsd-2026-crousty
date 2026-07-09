import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void shouldReturnAFor93Points() {
        Rating rating = new Rating();
        assertEquals('A', rating.determineResultGrade(93));
    }

    @Test
    void shouldReturnBFor85Points() {
        Rating rating = new Rating();
        assertEquals('B', rating.determineResultGrade(85));
    }

    @Test
    void shouldReturnEFor61Points() {
        Rating rating = new Rating();
        assertEquals('E', rating.determineResultGrade(61));
    }

    @Test
    void shouldReturnFFor60Points() {
        Rating rating = new Rating();
        assertEquals('F', rating.determineResultGrade(60));
    }

    @Test
    void shouldReturnFFor0Points() {
        Rating rating = new Rating();
        assertEquals('F', rating.determineResultGrade(0));
    }



    @Test
    void shouldThrowExceptionForNegativePoints() {
        Rating rating = new Rating();
        assertThrows(IllegalArgumentException.class, () -> {
            rating.determineResultGrade(-1);
        });
    }

    @Test
    void shouldThrowExceptionForMoreThan100Points() {
        Rating rating = new Rating();
        assertThrows(IllegalArgumentException.class, () -> {
            rating.determineResultGrade(101);
        });
    }



    @Test
    @Disabled("Demonstrates a failing assertion — included for educational purposes only")
    void intentionalFailingTestDemo() {
        Rating rating = new Rating();
        assertEquals('A', rating.determineResultGrade(75));
    }
}