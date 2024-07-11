package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Timestamp;

public interface SPXRepository extends MongoRepository<SPXRepository.StandardAndPoor500, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class StandardAndPoor500 extends MongoData<StandardAndPoor500> {
        private String price;
        private String priceChange;
        private String priceChangePercent;

        @Override
        public StandardAndPoor500 toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }
}
