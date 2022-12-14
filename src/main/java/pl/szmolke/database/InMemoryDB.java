package pl.szmolke.database;

import pl.szmolke.model.Match;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryDB {

    public static List<Match> MATCHES = new ArrayList<>(
            Arrays.asList(
                    Match.builder()
                            .homeTeam("Mexico")
                            .guestTeam("Canada")
                            .homeScore(0)
                            .guestScore(5)
                            .createDate(LocalDateTime.now().minusMinutes(10))
                            .build(),
                    Match.builder()
                            .homeTeam("Spain")
                            .guestTeam("Brazil")
                            .homeScore(10)
                            .guestScore(2)
                            .createDate(LocalDateTime.now().minusMinutes(8))
                            .build(),
                    Match.builder()
                            .homeTeam("Germany")
                            .guestTeam("France")
                            .homeScore(2)
                            .guestScore(2)
                            .createDate(LocalDateTime.now().minusMinutes(6))
                            .build(),
                    Match.builder()
                            .homeTeam("Uruguay")
                            .guestTeam("Italy")
                            .homeScore(6)
                            .guestScore(6)
                            .createDate(LocalDateTime.now().minusMinutes(4))
                            .build(),
                    Match.builder()
                            .homeTeam("Argentina")
                            .guestTeam("Australia")
                            .homeScore(3)
                            .guestScore(1)
                            .createDate(LocalDateTime.now().minusMinutes(2))
                            .build()
            )
    );
}
