package pl.wad.koniuszy.mongo.lines;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class LinesMongoStorage {

    private BestLineRepository bestLineRepository;
    private GameDefinitionRepository gameDefinitionRepository;

    public LinesMongoStorage(BestLineRepository bestLineRepository, GameDefinitionRepository gameDefinitionRepository) {
        this.bestLineRepository = bestLineRepository;
        this.gameDefinitionRepository = gameDefinitionRepository;
    }

    @Transactional
    public void override(GameDefinition gameDefinition, List<BestLine> lines) {
        Optional<GameDefinition> existingGameDefinition = gameDefinitionRepository.findOne(Example.of(gameDefinition));
        if (existingGameDefinition.isPresent()) {
            bestLineRepository.deleteByGdId(existingGameDefinition.get().getId());
            gameDefinitionRepository.delete(existingGameDefinition.get());
        }

        gameDefinition = gameDefinitionRepository.save(gameDefinition);
        for (BestLine line : lines) {
            line.setGdId(gameDefinition.getId());
        }
        bestLineRepository.saveAll(lines);
    }

    public Optional<String> getGameDefinitionId(GameDefinition gameDefinition) {
        Optional<GameDefinition> existingGameDefinition = gameDefinitionRepository.findOne(Example.of(gameDefinition));
        return existingGameDefinition.map(GameDefinition::getId);
    }

    public Optional<Long> getBestLine(String gameDefId, long position) {
        return ofNullable(bestLineRepository.findByGdIdAndPosition(gameDefId, position)).map(BestLine::getLine);
    }
}
