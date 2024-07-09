package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Timestamp;

public interface FearAndGreedRepository extends MongoRepository<FearAndGreedRepository.FearAndGreed, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class FearAndGreed extends MongoData<FearAndGreed> {
        private String rating;
        private double score;

        @Override
        public FearAndGreed toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }
}
