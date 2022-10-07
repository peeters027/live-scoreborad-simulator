package pl.szmolke.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Match {

    private String homeTeam;

    private String guestTeam;

    private int homeScore;

    private int guestScore;
}
