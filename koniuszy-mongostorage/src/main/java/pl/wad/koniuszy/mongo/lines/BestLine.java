package pl.wad.koniuszy.mongo.lines;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "bestlines")
public class BestLine {
    @MongoId
    private String id;
    private String gdId;
    // db.bestlines.createIndex( { "position" : 1 } )
    private long position;
    private long line;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGdId() {
        return gdId;
    }

    public void setGdId(String gdId) {
        this.gdId = gdId;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getLine() {
        return line;
    }

    public void setLine(long line) {
        this.line = line;
    }
}
