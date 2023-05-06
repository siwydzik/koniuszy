package pl.wad.koniuszy.mongo.lines;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BestLineRepository extends MongoRepository<BestLine, String> {
    void deleteByGdId(String gdId);
    BestLine findByGdIdAndPosition(String gdId, long position);
}
