package alceausu.nlp.sense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import alceausu.nlp.sense.entities.NlpCommand;

public interface NlpCommandRepository extends ElasticsearchRepository<NlpCommand, String>{

    Page<NlpCommand> findByTagsName(String name, Pageable pageable);

}
