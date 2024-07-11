package toy.slick.repository.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.sql.Timestamp;

public interface IXICRepository extends MongoRepository<IXICRepository.NasdaqComposite, String> {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class NasdaqComposite extends MongoData<NasdaqComposite> {
        private String price;
        private String priceChange;
        private String priceChangePercent;

        @Override
        public NasdaqComposite toMongoData(String _id) {
            this._id = _id;
            this._timestamp = new Timestamp(System.currentTimeMillis());

            return this;
        }
    }
}
