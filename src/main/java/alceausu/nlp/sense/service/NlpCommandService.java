package alceausu.nlp.sense.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import alceausu.nlp.sense.entities.NlpCommand;

public interface NlpCommandService {
	NlpCommand save(NlpCommand post);
	NlpCommand findOne(String id);
    Iterable<NlpCommand> findAll();

    Page<NlpCommand> findByTagsName(String tagName, PageRequest pageRequest);
}
