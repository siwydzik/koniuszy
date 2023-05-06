package pl.wad.koniuszy.mongo.lines;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "gamedefinitions")
public class GameDefinition {
    @MongoId
    private String id;
    private int x;
    private int y;
    private int sheeps;
    private int woolfs;
    private String victoryAlgorithmId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSheeps() {
        return sheeps;
    }

    public void setSheeps(int sheeps) {
        this.sheeps = sheeps;
    }

    public int getWoolfs() {
        return woolfs;
    }

    public void setWoolfs(int woolfs) {
        this.woolfs = woolfs;
    }

    public String getVictoryAlgorithmId() {
        return victoryAlgorithmId;
    }

    public void setVictoryAlgorithmId(String victoryAlgorithmId) {
        this.victoryAlgorithmId = victoryAlgorithmId;
    }
}
