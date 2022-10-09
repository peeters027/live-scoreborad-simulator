package utils;

import pl.szmolke.model.Match;

import java.util.ArrayList;
import java.util.Arrays;

import static pl.szmolke.database.InMemoryDB.MATCHES;

public class TestHelper {

    public static void initInMemoryDb() {
        MATCHES = new ArrayList<>(Arrays.asList(
                Match.builder()
                        .homeTeam("Spain")
                        .guestTeam("Brazil")
                        .homeScore(10)
                        .guestScore(2)
                        .build(),
                Match.builder()
                        .homeTeam("Mexico")
                        .guestTeam("Canada")
                        .homeScore(0)
                        .guestScore(5)
                        .build()
        ));
    }
}
