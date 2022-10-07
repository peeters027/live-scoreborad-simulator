import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.szmolke.model.Match;
import pl.szmolke.service.ScoreboardService;

class ScoreboardServiceTest {

    private final ScoreboardService scoreboardService = new ScoreboardService();

    @Test
    void itShouldStartMatchWithInitialScore() {
        
        // given
        String homeTeam = "Poland";
        String guestTeam = "Germany";

        // when
        Match startedMatch = scoreboardService.startMatch(homeTeam, guestTeam);

        // then
        Assertions.assertEquals(homeTeam, startedMatch.getHomeTeam());
        Assertions.assertEquals(guestTeam, startedMatch.getGuestTeam());
        Assertions.assertEquals(0, startedMatch.getHomeScore());
        Assertions.assertEquals(0, startedMatch.getGuestScore());
    }
}
