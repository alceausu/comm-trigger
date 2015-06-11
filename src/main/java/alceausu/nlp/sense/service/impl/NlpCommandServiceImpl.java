package alceausu.nlp.sense.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import alceausu.nlp.sense.entities.NlpCommand;
import alceausu.nlp.sense.repository.NlpCommandRepository;
import alceausu.nlp.sense.service.NlpCommandService;

@Service
public class NlpCommandServiceImpl implements NlpCommandService{
    @Autowired
    private NlpCommandRepository nlpCommRepository;

    @Override
    public NlpCommand save(NlpCommand post) {
    	nlpCommRepository.save(post);
        return post;
    }

    @Override
    public NlpCommand findOne(String id) {
        return nlpCommRepository.findOne(id);
    }

    @Override
    public Iterable<NlpCommand> findAll() {
        return nlpCommRepository.findAll();
    }

    @Override
    public Page<NlpCommand> findByTagsName(String tagName, PageRequest pageRequest) {
        return nlpCommRepository.findByTagsName(tagName, pageRequest);
    }
}
