package fr.clem.tennisscore.model;


import lombok.Builder;
import lombok.Data;


/***
 * This class represents the result of a tennis game for players A and B.
 * The results are numeric values that need to be converted according to the tennis rules:
 *  0 = "0"
 *  1 = "15"
 *  2 = "30"
 *  3 = "40"
 *  4 = "advantage"
 *  5 = "win"
 */
@Data
@Builder
public class GameResultModel implements Cloneable {


    private int scorePlayerA;
    private int scorePlayerB;

    @Override
    public GameResultModel clone() {
        return GameResultModel.builder()
                .scorePlayerA(this.scorePlayerA)
                .scorePlayerB(this.scorePlayerB)
                .build();
    }

}
