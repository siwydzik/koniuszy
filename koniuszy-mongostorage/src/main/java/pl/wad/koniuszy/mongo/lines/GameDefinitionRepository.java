package pl.wad.koniuszy.mongo.lines;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameDefinitionRepository extends MongoRepository<GameDefinition, String> {
}
